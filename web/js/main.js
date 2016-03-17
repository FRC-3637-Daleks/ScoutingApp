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
function preview() {
    var name = $('#name').val();
        $.ajax({url: "preview.html",
            data: {
                "name": name
            },
            statusCode: {
                400: function () {
                    console.log("Invalid request made for preview image");
                 },
                 404: function () {
                    console.log("Request for preview image could not be made");
                 },
                 500: function() {
                     console.log("Internal server error occued while maling request for preview image");
                 }
            },
            success: function (data) {
                console.log(data);
                var response = jQuery.parseJSON(data);
                if(response.code === 0) {
                    $('#preview-image').attr('src', response.image);
                } else {
                    console.log(response.error);
                }
            }
        });
}

$(document).ready(function() {
    
    $('#toTop').click(function() {
        scroll("#page-top");
    });
    
    $('#preview').click(function() {
        preview();
    });
});