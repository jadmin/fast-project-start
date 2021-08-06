<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>Redis监控</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <!-- Bootstrap 3.3.7 -->
  <link rel="stylesheet" href="${appPath}/components/bootstrap/dist/css/bootstrap.min.css">

  <!-- Font Awesome -->
  <link rel="stylesheet" href="${appPath}/components/font-awesome/css/font-awesome.min.css">
  <!-- Ionicons -->
  <link rel="stylesheet" href="${appPath}/components/Ionicons/css/ionicons.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="${appPath}/css/AdminLTE.min.css">
  <!-- AdminLTE Skin -->
  <link rel="stylesheet" href="${appPath}/css/skins/${skin}.min.css">
  
  <link href="${appPath}/css/toastr.min.css" rel="stylesheet">
  
  <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->

  <!-- jQuery 3 -->
  <script src="${appPath}/components/jquery/dist/jquery.min.js"></script>
  
  <script type="text/javascript" src="${appPath}/js/toastr.min.js"></script>

</head>
<body class="hold-transition ${skin} sidebar-mini">
<div class="wrapper">
  
  <#include "/_header.ftl" />

  <!-- left side menu -->
  <#include "/_menu.ftl" />

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
        数据源监控
        <small></small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="${appPath}/console/index"><i class="fa fa-dashboard"></i> Home</a></li>
        <li><a href="#">系统监控</a></li>
        <li class="active">Redis监控</li>
      </ol>
    </section>

    <!-- Main content -->
    <section class="content">
      <div class="row">
      	<div class="col-md-6">
          <!-- interactive chart -->
          <div class="box">
            <div class="box-header with-border">
              <i class="fa fa-bar-chart-o"></i>
              <h3 class="box-title">Redis 内存实时占用（KB）</h3>
            </div>
            <div class="box-body">
              <div id="interactive" style="height: 300px;"></div>
            </div>
            <!-- /.box-body-->
            <div class="overlay" id="J_overlay_memory">
              <i class="fa fa-refresh fa-spin"></i>
            </div>
          </div>
          <!-- /.box -->

        </div>
        
        <div class="col-md-6">
          <div class="box">
            <div class="box-header with-border">
              <i class="fa fa-bar-chart-o"></i>
              <h3 class="box-title">Redis Key 实时数量（个）</h3>
            </div>
            <div class="box-body">
              <div id="keysize" style="height: 300px;"></div>
            </div>
            <!-- /.box-body-->
            <div class="overlay" id="J_overlay_keysize">
              <i class="fa fa-refresh fa-spin"></i>
            </div>
          </div>
        </div>
      </div>
      <!-- /.row -->
      <div class="row">
        <div class="col-xs-12">
          <div class="box">
            <div class="box-header">
              <h3 class="box-title">Redis 详细指标</h3>
            </div>
            <!-- /.box-header -->
            <div class="box-body table-responsive no-padding">
              <table class="table table-hover">
                <tbody>
                <tr>
                  <th>Key</th>
                  <th>Description</th>
                  <th>Value</th>
                </tr>
             <#list itemList as item>
             	<tr>
                  <td>${item.key!''}</td>
                  <td>${item.description!''}</td>
                  <td>${item.value!''}</td>
                </tr>
             </#list>
              </tbody></table>
            </div>
            <!-- /.box-body -->
          </div>
          <!-- /.box -->
        </div>
      </div>
    </section>
    <!-- /.content -->    
    
  </div>
  <!-- /.content-wrapper -->


	<#include "/_footer.ftl" />

</div>
<!-- ./wrapper -->


<!-- jQuery UI 1.11.4 -->
<script src="${appPath}/components/jquery-ui/jquery-ui.min.js"></script>
<!-- Resolve conflict in jQuery UI tooltip with Bootstrap tooltip -->
<!-- Bootstrap 3.3.7 -->
<script src="${appPath}/components/bootstrap/dist/js/bootstrap.min.js"></script>

<script src="${appPath}/js/common.js"></script>
<script src="${appPath}/js/ui.js"></script>

<#include "/_components.ftl" />

<!-- FastClick -->
<script src="${appPath}/components/fastclick/1.0.6/fastclick.min.js"></script>
<!-- AdminLTE App -->
<script src="${appPath}/js/adminlte.min.js"></script>
<script src="${appPath}/js/common.js"></script>

<!-- FLOT CHARTS -->
<script src="${appPath}/components/flot/jquery.flot.js"></script>
<script src="${appPath}/components/flot/jquery.flot.resize.js"></script>
<script src="${appPath}/components/flot/jquery.flot.pie.js"></script>
<script src="${appPath}/components/flot/jquery.flot.categories.js"></script>
<script src="${appPath}/components/flot/jquery.flot.time.js"></script>

<script>

toastr.options.positionClass = 'toast-top-center';

$(document).ready(function() {
	setInterval(plotMemory, 3000);
	setInterval(plotKeysize, 3000);
});

var d = [];
var mCnt = 0;
function plotMemory() {
	$.ajax({
        url: "${appPath}/admin/redis/memory",
        type: "GET",
        success: function (res) {
            if (res.status) {
                var map = res.entry;
                d.push([map.create_time, map.used_memory/1024]);
                
                if(d.length > 7) {
    				d.shift();
    			}
    			$.plot($("#interactive"), [d], {
			      grid  : {
			        borderColor: '#f3f3f3',
			        borderWidth: 1,
			        tickColor  : '#f3f3f3'
			      },
			      series: {
			        shadowSize: 0, // Drawing is faster without shadows
			        color     : '#3c8dbc'
			      },
			      lines : {
			        fill : true, //Converts the line chart to area chart
			        color: '#3c8dbc'
			      },
			      yaxis : {
			        show: true
			      },
			      xaxis : {
			        mode: 'time',             
                    tickSize:[3, 'second'],  
                    timeformat: '%h:%M:%S',
                    timezone: "browser", 
                    show: true
			      }
			    });
			    mCnt++;
			    if (mCnt == 1) {
			    	$('#J_overlay_memory').hide();
			    }
            }
        }
     });
}

var dd = [];
var kCnt = 0;
function plotKeysize() {
	$.ajax({
        url: "${appPath}/admin/redis/keysize",
        type: "GET",
        success: function (res) {
            if (res.status) {
                var map = res.entry;
                dd.push([map.create_time, map.dbSize]);
                
                if(dd.length > 7) {
    				dd.shift();
    			}
    			$.plot($("#keysize"), [dd], {
			      grid  : {
			        borderColor: '#f3f3f3',
			        borderWidth: 1,
			        tickColor  : '#f3f3f3'
			      },
			      series: {
			        shadowSize: 0, // Drawing is faster without shadows
			        color     : '#3c8dbc'
			      },
			      lines : {
			        fill : true, //Converts the line chart to area chart
			        color: '#3c8dbc'
			      },
			      yaxis : {
			        show: true
			      },
			      xaxis : {
			        mode: 'time',             
                    tickSize:[3, 'second'],  
                    timeformat: '%h:%M:%S',
                    timezone: "browser", 
                    show: true
			      }
			    });
			    kCnt++;
			    if (kCnt == 1) {
			    	$('#J_overlay_keysize').hide();
			    }
            }
        }
     });
}


</script>

</body>
</html>
