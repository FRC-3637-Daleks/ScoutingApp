<#import "spring.ftl" as spring />
<html>
<head>
<style>

#categoryTable table{
	width:100%
}

#categoryTable td{
    padding-right:2.38em;
    padding-top:0.8em;
    padding-bottom:0.8em;
    padding-left:0.8em;
    border: 1px solid gray;
}

.sectionHeader {
    background-color:#53EA0C;
    font-weight:bold;
    text-align:center;
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
    <th><p style="color:white">Matches Played: ${matches}</p></th>
    <th><p style="color:white">Avg.Score: ${avgScore}</p></th>
    <th><p style="color:white">Our Score: ${ourScore}</p></th>
    <th><p style="color:white">Win/Lose Ratio: ${wins}:${losses}</p></th>
  </tr>
</table>
<table>
<#list matchStatistics as matchStatistic>
  <tr>
    <td>${matchStatistic.grouping}</td>    
    <td>${matchStatistic.category}</td>  
    <td>${matchStatistic.tag}</td>    
  </tr>
</#list>
</table>
<#assign grouping = ""> 
<#list matchStatistics as matchStatistic>
<#if grouping == "" || grouping != matchStatistic.grouping>
  <#assign grouping =  matchStatistic.grouping> 
  <div class="sectionHeader">${grouping}</div>
</#if>


</#list>


<div class="sectionHeader">Auton</div>

<table id="categoryTable">
    <tr>
    	<td>
    	<div class="cellTitle">Gears</div><table>
          <tr><td>Tag</td><td>Count</td></tr>
          <tr><td>Tag</td><td>Count</td></tr>
        </table>
    	</td>
    	<td>
    	<div class="cellTitle">Fuel</div><table>
          <tr><td>Tag</td><td>Count</td></tr>
          <tr><td>Tag</td><td>Count</td></tr>
        </table>
    	</td>
    	<td>
    	<div class="cellTitle">Baseline</div><table>
          <tr><td>Tag</td><td>Count</td></tr>
          <tr><td>Tag</td><td>Count</td></tr>
        </table>
    	</td>
    </tr>
    <tr>
        <th colspan = "3"><div class="columnTitle"><u><h1 style="font-size:150%;">Tele-Op</h1></u></div></th>
    </tr>
    <tr>
    	<td>
    	<div class="cellTitle">Gears</div><table>
          <tr><td>Tag</td><td>Count</td></tr>
          <tr><td>Tag</td><td>Count</td></tr>
        </table>
    	</td>
    	<td>
    	<div class="cellTitle">Fuel</div><table>
          <tr><td>Tag</td><td>Count</td></tr>
          <tr><td>Tag</td><td>Count</td></tr>
        </table>
    	</td>
    	<td>
    	<div class="cellTitle">Climb Rope</div><table>
          <tr><td>Tag</td><td>Count</td></tr>
          <tr><td>Tag</td><td>Count</td></tr>
        </table>
    	</td>
    </tr>
    <tr>
        <th colspan = "2"><div class="columnTitle"><u><h1 style="font-size:150%;">Team</h1></u></div></th>
    </tr>
    <tr>
    	<td>
    	<div class="cellTitle">Reliability</div><table>
          <tr><td>Tag</td><td>Count</td></tr>
          <tr><td>Tag</td><td>Count</td></tr>
        </table>
    	</td>
    	<td>
    	<div class="cellTitle">Effectiveness</div><table>
          <tr><td>Tag</td><td>Count</td></tr>
          <tr><td>Tag</td><td>Count</td></tr>
        </table>
    	</td>
    </tr>
</table>
</body>
</html>

