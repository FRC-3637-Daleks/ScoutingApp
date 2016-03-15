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
function setDeleteListener() {
    $('.delete').click(function () {
        if ($(this).hasClass('new')) {
            $(this).parent().parent().remove();
            num--;
        } else {
            if ($(this).parent().find('input[type=hidden]').val() == 'false') {
                console.log('Delete');
                $(this).parent().find('input[type=hidden]').val(true);
                $(this).removeClass('btn-danger');
                $(this).addClass('btn-warning ');
                $(this).attr('value', 'Undo');
            } else {
                console.log('Undo');
                $(this).parent().find('input[type=hidden]').val(false);
                $(this).removeClass('btn-warning ');
                $(this).addClass('btn-danger');
                $(this).attr('value', 'Delete');
            }
        }
    });
}

$(document).ready(function () {
    setDeleteListener();
});