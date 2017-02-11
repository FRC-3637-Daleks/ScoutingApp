<#import "spring.ftl" as spring />
<html>
<head>
<style>
#codexpl td{
    padding-right:2.38em;
    padding-top:0.8em;
    padding-bottom:0.8em;
    padding-left:0.8em;
    border: 1px solid gray;
}
#codexpl th{
    background-color:#53EA0C;
    font-weight:bold;
    padding-right:2.38em;
    padding-top:0.8em;
    padding-bottom:0.8em;
    padding-left:0.8em;
}

#codxpl th{
    background-color:#005EFF;
    font-weight:bold;
    padding:0.8em;
    border: 1px solid;
}
.cellTitle {text-align: center; height: 50px}
.columnTitle {text-align: center}
</style>
</head>
<body>
<table id="codxpl">
  <tr>
    <th><p style="color:white">Team: ${team}</p></th>
    <th><p style="color:white">Match: ${match}</p></th>
  </tr>
</table>

<table id="codexpl">
    <tr>
        <th><div class="columnTitle"><u><h1 style="font-size:150%;">Auton</h1></u></div></th>
        <th><div class="columnTitle"><u><h1 style="font-size:150%;">Tele-Op</h1></u></div></th>
        <th><div class="columnTitle"><u><h1 style="font-size:150%;">Team</h1></u></div></th>
    </tr>
    <tr>
        <td><div class="cellTitle">Gears</div><table>
          <tr><td>Tag</td><td>Count</td></tr>
          <tr><td>Tag</td><td>Count</td></tr>
        </table></td>
        <td><div class="cellTitle">Gears</div><table>
          <tr><td>Tag</td><td>Count</td></tr>
          <tr><td>Tag</td><td>Count</td></tr>
        </table></td>
        <td><div class="cellTitle">Reliability</div><table>
          <tr><td>Tag</td><td>Count</td></tr>
          <tr><td>Tag</td><td>Count</td></tr>
        </table></td>
    </tr>
    <tr>
        <td><div class="cellTitle">Fuel</div><table>
          <tr><td>Tag</td><td>Count</td></tr>
          <tr><td>Tag</td><td>Count</td></tr>
        </table></td>
        <td><div class="cellTitle">Fuel</div><table>
          <tr><td>Tag</td><td>Count</td></tr>
          <tr><td>Tag</td><td>Count</td></tr>
        </table></td>
        <td><div class="cellTitle">Effectiveness</div><table>
          <tr><td>Tag</td><td>Count</td></tr>
          <tr><td>Tag</td><td>Count</td></tr>
        </table></td>
    </tr>
    <tr>
        <td><div class="cellTitle">Baseline</div><table>
          <tr><td>Yes</td><td>No</td></tr>
        </table></td>
        <td><div class="cellTitle">Climb Rope</div><table>
          <tr><td>Tag</td><td>Count</td></tr>
        </table></td>
    </tr>
</table>
</body>
</html>

