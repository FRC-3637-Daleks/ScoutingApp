/*Team 3637 Scouting App - An application for data collection/analytics at FIRST competitions
 Copyright (C) 2016  Team 3637

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.team3637.controller.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.team3637.service.MatchService;
import com.team3637.service.MatchTagService;
import com.team3637.service.ScheduleService;
import com.team3637.service.Service;
import com.team3637.service.TagService;
import com.team3637.service.TeamService;

@Controller
public class IOController {

	@Autowired
	private ScheduleService scheduleService;
	@Autowired
	private MatchService matchService;
	@Autowired
	private MatchTagService matchTagService;
	@Autowired
	private TeamService teamService;
	@Autowired
	private TagService tagService;
	@Autowired
	private ServletContext context;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String io() {
		return "io";
	}

	@RequestMapping(value = "/bundle.zip", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> exportBundle() throws IOException {
		exportSchedule();
		exportMatches();
		exportMatchTags();
		exportTeamTags();
		exportTags();
		String zipFile = context.getRealPath("/") + "/export/bundle.zip";
		String[] srcFiles = { context.getRealPath("/") + "/export/schedule.csv",
				context.getRealPath("/") + "/export/matches.csv", context.getRealPath("/") + "/export/matchTags.csv",
				context.getRealPath("/") + "/export/teamTags.csv", context.getRealPath("/") + "/export/tags.csv" };
		try {
			byte[] buffer = new byte[1024];
			FileOutputStream fos = new FileOutputStream(zipFile);
			ZipOutputStream zos = new ZipOutputStream(fos);
			for (String srcFile1 : srcFiles) {
				File srcFile = new File(srcFile1);
				FileInputStream fis = new FileInputStream(srcFile);
				zos.putNextEntry(new ZipEntry(srcFile.getName()));
				int length;
				while ((length = fis.read(buffer)) > 0)
					zos.write(buffer, 0, length);
				zos.closeEntry();
				fis.close();
			}
			zos.close();
		} catch (IOException ioe) {
			System.out.println("Error creating zip file: " + ioe);
			return new ResponseEntity<>("Error creating zip file: " + ioe, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", context.getContextPath() + "/export/bundle.zip");
		return new ResponseEntity<byte[]>(null, headers, HttpStatus.FOUND);
	}

	@RequestMapping(value = "/bundle.zip", method = RequestMethod.POST)
	public ResponseEntity<?> importBundle(
			@RequestParam(value = "delete", required = false, defaultValue = "false") Boolean delete,
			@RequestParam("file") MultipartFile file) {
		File inputDir = new File(context.getRealPath("/") + "/input");
		if (!inputDir.exists()) {
			inputDir.mkdir();
		}
		if (file.isEmpty())
			return new ResponseEntity<>("File was empty", HttpStatus.INTERNAL_SERVER_ERROR);
		try {
			byte[] buffer = file.getBytes();
			BufferedOutputStream stream = new BufferedOutputStream(
					new FileOutputStream(new File(inputDir.getAbsolutePath() + "/bundle.zip")));
			stream.write(buffer);
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		byte[] buffer = new byte[1024];
		try {
			ZipInputStream zis = new ZipInputStream(new FileInputStream(inputDir.getAbsolutePath() + "/bundle.zip"));
			ZipEntry ze = zis.getNextEntry();
			while (ze != null) {
				String fileName = ze.getName();
				File newFile = new File(inputDir + File.separator + fileName);
				new File(newFile.getParent()).mkdirs();
				FileOutputStream fos = new FileOutputStream(newFile);
				int len;
				while ((len = zis.read(buffer)) > 0)
					fos.write(buffer, 0, len);
				fos.close();
				ze = zis.getNextEntry();
			}
			zis.closeEntry();
			zis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		scheduleService.importCSV(inputDir + "/schedule.csv", delete);
		matchService.importCSV(inputDir + "/matches.csv", delete);
		matchTagService.importCSV(inputDir + "/matchTags.csv", delete);
		teamService.importCSV(inputDir + "/teams.csv", delete);
		tagService.importCSV(inputDir + "/tags.csv", delete);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", context.getContextPath() + "/io/");
		return new ResponseEntity<byte[]>(null, headers, HttpStatus.FOUND);
	}

	private String getCSV(Service service, String file) throws IOException {
		File exportDirectory = new File(context.getRealPath("/") + "/export");
		if (!exportDirectory.exists())
			exportDirectory.mkdir();
		String filePath = exportDirectory.getAbsolutePath() + "/" + file;
		service.exportCSV(filePath);
		return new String(Files.readAllBytes(FileSystems.getDefault().getPath(filePath)));
	}

	private ResponseEntity<?> importCSV(Service service, String fileName, MultipartFile file, Boolean deleteValues)
			throws IOException {
		File inputDir = new File(context.getRealPath("/") + "/input");
		if (!inputDir.exists()) {
			inputDir.mkdir();
		}
		if (file.isEmpty())
			return new ResponseEntity<>("File was empty", HttpStatus.INTERNAL_SERVER_ERROR);
		byte[] buffer = file.getBytes();
		BufferedOutputStream stream = new BufferedOutputStream(
				new FileOutputStream(new File(inputDir.getAbsolutePath() + "/" + fileName)));
		stream.write(buffer);
		stream.close();
		service.importCSV(inputDir + fileName, deleteValues);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", context.getContextPath() + "/io/");
		return new ResponseEntity<byte[]>(null, headers, HttpStatus.FOUND);
	}

	@RequestMapping(value = "/schedule.csv", method = RequestMethod.GET)
	@ResponseBody
	public String exportSchedule() throws IOException {
		return getCSV(scheduleService, "schedule.csv");
	}

	@RequestMapping(value = "/schedule.csv", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> importSchedule(
			@RequestParam(value = "delete", required = false, defaultValue = "false") Boolean delete,
			@RequestParam("file") MultipartFile file) throws IOException {
		return importCSV(scheduleService, "/schedule.csv", file, delete);
	}

	@RequestMapping(value = "/matches.csv", method = RequestMethod.GET)
	@ResponseBody
	public String exportMatches() throws IOException {
		return getCSV(matchService, "matches.csv");
	}

	@RequestMapping(value = "/matchTags.csv", method = RequestMethod.GET)
	@ResponseBody
	public String exportMatchTags() throws IOException {
		return getCSV(matchTagService, "matchTags.csv");
	}

	@RequestMapping(value = "/matchData.zip", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> exportMatchData() throws IOException {
		exportMatches();
		exportMatchTags();
		String zipFile = context.getRealPath("/") + "/export/matchData.zip";
		String[] srcFiles = { context.getRealPath("/") + "/export/matches.csv",
				context.getRealPath("/") + "/export/matchTags.csv" };
		try {
			byte[] buffer = new byte[1024];
			FileOutputStream fos = new FileOutputStream(zipFile);
			ZipOutputStream zos = new ZipOutputStream(fos);
			for (String srcFile1 : srcFiles) {
				File srcFile = new File(srcFile1);
				FileInputStream fis = new FileInputStream(srcFile);
				zos.putNextEntry(new ZipEntry(srcFile.getName()));
				int length;
				while ((length = fis.read(buffer)) > 0)
					zos.write(buffer, 0, length);
				zos.closeEntry();
				fis.close();
			}
			zos.close();
		} catch (IOException ioe) {
			System.out.println("Error creating zip file: " + ioe);
			return new ResponseEntity<>("Error creating zip file: " + ioe, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", context.getContextPath() + "/export/matchData.zip");
		return new ResponseEntity<byte[]>(null, headers, HttpStatus.FOUND);
	}

	@RequestMapping(value = "/teamData.zip", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> exportTeamData() throws IOException {
		exportTeams();
		exportTeamTags();
		String zipFile = context.getRealPath("/") + "/export/teamData.zip";
		String[] srcFiles = { context.getRealPath("/") + "/export/teams.csv",
				context.getRealPath("/") + "/export/teamTags.csv" };
		try {
			byte[] buffer = new byte[1024];
			FileOutputStream fos = new FileOutputStream(zipFile);
			ZipOutputStream zos = new ZipOutputStream(fos);
			for (String srcFile1 : srcFiles) {
				File srcFile = new File(srcFile1);
				FileInputStream fis = new FileInputStream(srcFile);
				zos.putNextEntry(new ZipEntry(srcFile.getName()));
				int length;
				while ((length = fis.read(buffer)) > 0)
					zos.write(buffer, 0, length);
				zos.closeEntry();
				fis.close();
			}
			zos.close();
		} catch (IOException ioe) {
			System.out.println("Error creating zip file: " + ioe);
			return new ResponseEntity<>("Error creating zip file: " + ioe, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", context.getContextPath() + "/export/teamData.zip");
		return new ResponseEntity<byte[]>(null, headers, HttpStatus.FOUND);
	}

	@RequestMapping(value = "/matchData.zip", method = RequestMethod.POST)
	public ResponseEntity<?> importMatchData(
			@RequestParam(value = "delete", required = false, defaultValue = "false") Boolean delete,
			@RequestParam("file") MultipartFile file) {
		File inputDir = new File(context.getRealPath("/") + "/input");
		if (!inputDir.exists()) {
			inputDir.mkdir();
		}
		if (file.isEmpty())
			return new ResponseEntity<>("File was empty", HttpStatus.INTERNAL_SERVER_ERROR);
		try {
			byte[] buffer = file.getBytes();
			BufferedOutputStream stream = new BufferedOutputStream(
					new FileOutputStream(new File(inputDir.getAbsolutePath() + "/matchData.zip")));
			stream.write(buffer);
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		byte[] buffer = new byte[1024];
		try {
			ZipInputStream zis = new ZipInputStream(new FileInputStream(inputDir.getAbsolutePath() + "/matchData.zip"));
			ZipEntry ze = zis.getNextEntry();
			while (ze != null) {
				String fileName = ze.getName();
				File newFile = new File(inputDir + File.separator + fileName);
				new File(newFile.getParent()).mkdirs();
				FileOutputStream fos = new FileOutputStream(newFile);
				int len;
				while ((len = zis.read(buffer)) > 0)
					fos.write(buffer, 0, len);
				fos.close();
				ze = zis.getNextEntry();
			}
			zis.closeEntry();
			zis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		matchService.importCSV(inputDir + "/matches.csv", delete);
		matchTagService.importCSV(inputDir + "/matchTags.csv", delete);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", context.getContextPath() + "/io/");
		return new ResponseEntity<byte[]>(null, headers, HttpStatus.FOUND);
	}

	@RequestMapping(value = "/teamData.zip", method = RequestMethod.POST)
	public ResponseEntity<?> importTeamData(
			@RequestParam(value = "delete", required = false, defaultValue = "false") Boolean delete,
			@RequestParam("file") MultipartFile file) {
		File inputDir = new File(context.getRealPath("/") + "/input");
		if (!inputDir.exists()) {
			inputDir.mkdir();
		}
		if (file.isEmpty())
			return new ResponseEntity<>("File was empty", HttpStatus.INTERNAL_SERVER_ERROR);
		try {
			byte[] buffer = file.getBytes();
			BufferedOutputStream stream = new BufferedOutputStream(
					new FileOutputStream(new File(inputDir.getAbsolutePath() + "/teamData.zip")));
			stream.write(buffer);
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		byte[] buffer = new byte[1024];
		try {
			ZipInputStream zis = new ZipInputStream(new FileInputStream(inputDir.getAbsolutePath() + "/teamData.zip"));
			ZipEntry ze = zis.getNextEntry();
			while (ze != null) {
				String fileName = ze.getName();
				File newFile = new File(inputDir + File.separator + fileName);
				new File(newFile.getParent()).mkdirs();
				FileOutputStream fos = new FileOutputStream(newFile);
				int len;
				while ((len = zis.read(buffer)) > 0)
					fos.write(buffer, 0, len);
				fos.close();
				ze = zis.getNextEntry();
			}
			zis.closeEntry();
			zis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		teamService.importTeamsCSV(inputDir + "/teams.csv", delete);
		teamService.importCSV(inputDir + "/teamTags.csv", delete);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", context.getContextPath() + "/io/");
		return new ResponseEntity<byte[]>(null, headers, HttpStatus.FOUND);
	}

	@RequestMapping(value = "/matches.csv", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> importMatches(
			@RequestParam(value = "delete", required = false, defaultValue = "false") Boolean delete,
			@RequestParam("file") MultipartFile file) throws IOException {
		return importCSV(matchService, "/matches.csv", file, delete);
	}

	@RequestMapping(value = "/matchTags.csv", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> importMatchTags(
			@RequestParam(value = "delete", required = false, defaultValue = "false") Boolean delete,
			@RequestParam("file") MultipartFile file) throws IOException {
		return importCSV(matchTagService, "/matchTags.csv", file, delete);
	}

	@RequestMapping(value = "/teamTags.csv", method = RequestMethod.GET)
	@ResponseBody
	public String exportTeamTags() throws IOException {
		return getCSV(teamService, "teamTags.csv");
	}

	public String exportTeams() throws IOException {
		File exportDirectory = new File(context.getRealPath("/") + "/export");
		if (!exportDirectory.exists())
			exportDirectory.mkdir();
		String filePath = exportDirectory.getAbsolutePath() + "/teams.csv";
		teamService.exportTeamCSV(filePath);
		return new String(Files.readAllBytes(FileSystems.getDefault().getPath(filePath)));
	}

	@RequestMapping(value = "/teamTags.csv", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> importTeamTags(
			@RequestParam(value = "delete", required = false, defaultValue = "false") Boolean delete,
			@RequestParam("file") MultipartFile file) throws IOException {
		return importCSV(teamService, "/teamTags.csv", file, delete);
	}

	@RequestMapping(value = "/tags.csv", method = RequestMethod.GET)
	@ResponseBody
	public String exportTags() throws IOException {
		return getCSV(tagService, "tags.csv");
	}

	@RequestMapping(value = "/tags.csv", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> importTags(
			@RequestParam(value = "delete", required = false, defaultValue = "false") Boolean delete,
			@RequestParam("file") MultipartFile file) throws IOException {
		return importCSV(tagService, "/tags.csv", file, delete);
	}
}
