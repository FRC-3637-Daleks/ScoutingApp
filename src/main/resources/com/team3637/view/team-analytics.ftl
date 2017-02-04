<#import "spring.ftl" as spring />
<html>
<head>
    <link rel="stylesheet" type="text/css" href="../angular-ui/ui-grid/ui-grid.min.css"> 
    <script type="text/javascript" src="../angular-1.5.5/angular.min.js"></script>
    <script type="text/javascript" src="../angular-1.5.5/angular-touch.min.js"></script>
    <script type="text/javascript" src="../angular-1.5.5/angular-animate.min.js"></script>
    <script type="text/javascript" src="../js/jquery.min.js"></script>
    <script src="../angular-ui/ui-grid/ui-grid.min.js"></script> 
    <script type="text/javascript" src="../js/team-analytics.js"></script>
    <style>
       .grid {
             width: 500px;
             height: 35px;
       }
</style>
</head>
<body>
<div ng-app="uigrid">
    <div ng-controller="MainCtrl">
        <div ui-grid="gridOptions" class="grid"  ui-grid-grouping ui-grid-selection></div>
    </div>
</div>
</body>
</html>

