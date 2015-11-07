package com.team3637.controller.io;

import com.team3637.service.*;
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
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.ServletContext;
import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

@Controller
public class IOController {

    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private MatchService matchService;
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
        exportTeam();
        exportTags();
        String zipFile = context.getRealPath("/") + "/export/bundle.zip";
        String[] srcFiles = {
                context.getRealPath("/") + "/export/schedule.csv",
                context.getRealPath("/") + "/export/matches.csv",
                context.getRealPath("/") + "/export/teams.csv",
                context.getRealPath("/") + "/export/tags.csv"
        };
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
    public ResponseEntity<?> importBundle(@RequestParam("file") MultipartFile file) {
        File inputDir = new File(context.getRealPath("/") + "/input");
        if (!inputDir.exists()) {
            inputDir.mkdir();
        }
        if (file.isEmpty())
            return new ResponseEntity<>("File was empty", HttpStatus.INTERNAL_SERVER_ERROR);
        try {
            byte[] buffer = file.getBytes();
            BufferedOutputStream stream = new BufferedOutputStream(new
                    FileOutputStream(new File(inputDir.getAbsolutePath() + "/bundle.zip")));
            stream.write(buffer);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        byte[] buffer = new byte[1024];
        try {
            ZipInputStream zis =
                    new ZipInputStream(new FileInputStream(inputDir.getAbsolutePath() + "/bundle.zip"));
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
        scheduleService.importCSV(inputDir + "/schedule.csv");
        matchService.importCSV(inputDir + "/matches.csv");
        teamService.importCSV(inputDir + "/teams.csv");
        tagService.importCSV(inputDir + "/tags.csv");
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

    private ResponseEntity<?> importCSV(Service service, String fileName, MultipartFile file) throws IOException {
        File inputDir = new File(context.getRealPath("/") + "/input");
        if (!inputDir.exists()) {
            inputDir.mkdir();
        }
        if (file.isEmpty())
            return new ResponseEntity<>("File was empty", HttpStatus.INTERNAL_SERVER_ERROR);
        byte[] buffer = file.getBytes();
        BufferedOutputStream stream = new BufferedOutputStream(new
                FileOutputStream(new File(inputDir.getAbsolutePath() + "/" + fileName)));
        stream.write(buffer);
        stream.close();
        scheduleService.importCSV(inputDir + fileName);
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
    public ResponseEntity<?> importSchedule(@RequestParam("file") MultipartFile file) throws IOException {
        return importCSV(scheduleService, "/schedule.csv", file);
    }

    @RequestMapping(value = "/matches.csv", method = RequestMethod.GET)
    @ResponseBody
    public String exportMatches() throws IOException {
        return getCSV(matchService, "matches.csv");
    }

    @RequestMapping(value = "/matches.csv", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> importMatches(@RequestParam("file") MultipartFile file) throws IOException {
        return importCSV(scheduleService, "/matches.csv", file);
    }

    @RequestMapping(value = "/teams.csv", method = RequestMethod.GET)
    @ResponseBody
    public String exportTeam() throws IOException {
        return getCSV(teamService, "teams.csv");
    }

    @RequestMapping(value = "/teams.csv", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> importTeam(@RequestParam("file") MultipartFile file) throws IOException {
        return importCSV(scheduleService, "/teams.csv", file);
    }

    @RequestMapping(value = "/tags.csv", method = RequestMethod.GET)
    @ResponseBody
    public String exportTags() throws IOException {
        return getCSV(tagService, "tags.csv");
    }

    @RequestMapping(value = "/tags.csv", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> importTags(@RequestParam("file") MultipartFile file) throws IOException {
        return importCSV(scheduleService, "/tags.csv", file);
    }
}
