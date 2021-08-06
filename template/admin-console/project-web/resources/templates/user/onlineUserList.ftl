<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>后台用户</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <!-- Bootstrap 3.3.7 -->
  <link rel="stylesheet" href="${appPath}/components/bootstrap/dist/css/bootstrap.min.css">
  <link rel="stylesheet" href="${appPath}/components/bootstrap-table/bootstrap-table.min.css" >
  <link rel="stylesheet" href="${appPath}/components/bootstrap-daterangepicker/daterangepicker.css">
  <link rel="stylesheet" href="${appPath}/components/bootstrap-datetimepicker/bootstrap-datetimepicker.css">
  <link rel="stylesheet" href="${appPath}/components/select2/dist/css/select2.min.css">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="${appPath}/components/font-awesome/css/font-awesome.min.css">
  <!-- Ionicons -->
  <link rel="stylesheet" href="${appPath}/components/Ionicons/css/ionicons.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="${appPath}/css/AdminLTE.min.css">
  <!-- AdminLTE Skin -->
  <link rel="stylesheet" href="${appPath}/css/skins/${skin}.min.css">
  
  <link href="${appPath}/css/toastr.min.css" rel="stylesheet">
  
  <link rel="shortcut icon" href="${appPath}/images/icon-48x48.png">
  
  <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->

  <!-- jQuery 3 -->
  <script src="${appPath}/components/jquery/dist/jquery.min.js"></script>
  <script src="${appPath}/js/toastr.min.js"></script>

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
        在线用户
        <small></small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="${appPath}/console/index"><i class="fa fa-dashboard"></i> Home</a></li>
        <li><a href="#">用户管理</a></li>
        <li class="active">在线用户</li>
      </ol>
    </section>

    <!-- Main content -->
    <section class="content">
    
    	<div class="row">
	      	<div class="col-xs-12">
	      		<div class="box">
	      			<div class="box-body content">
	      			<!-- 
	        			<form id="listForm" class="form-inline">
                            <div class="form-group">
                                <label for="username">用户名</label>
                                <input type="text" class="form-control" id="username" name="username" placeholder="">
                            </div>
                            <div class="form-group">
                                <label for="nameLike">姓名</label>
                                <input type="text" class="form-control" id="nameLike" name="nameLike" placeholder="">
                            </div>
                            <div class="form-group">
                                <label for="mobile">手机号</label>
                                <input type="text" class="form-control" id="mobile" name="mobile" placeholder="">
                            </div>
                            <div class="form-group">
                                <label for="email">邮件地址</label>
                                <input type="text" class="form-control" id="email" name="email" placeholder="">
                            </div>
                            <div class="form-group">
                                <label for="userStatus">用户状态</label>
                                <select id="userStatus" name="userStatus" class="form-control input-sm">
                                    <option value="">全部</option>
                                    <option value="0">待激活</option>
                                    <option value="1" selected>正常</option>
                                    <option value="2">已禁用</option>
                                </select>
                            </div>
                            <button type="button" class="btn btn-default" id="submit">查询</button>
	        			</form>
	        			
	        			<h4></h4>
	        			
	        			<div id="toolbar">
						    <div class="btn-group">
				           		<a href="${appPath}/console/user/modal" data-toggle="modal" data-target='#addModal'>
							    	<button id="btn_add" type="button" class="btn btn-default">
					               		<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
					           		</button>
				           		</a>
						    </div>
						</div>
						
						-->
						
	        			<table id="table"
                                   data-toggle="table"
                                   data-toolbar="#toolbar"
                                   data-side-pagination="server"
                                   data-show-export="false"
                                   data-pagination="true"
                                   data-url="${appPath}/admin/usermanage/onlineUserList"
                                   data-query-params="requestParemeter">
                                <thead>
                                <tr>
                                    <th data-field="userId">用户ID</th>
                                    <th data-field="username">用户名</th>
                                    <th data-field="name">姓名</th>
                                    <th data-field="mobile">手机号</th>
                                    <th data-field="email">邮件</th>
                                    <th data-field="loginIp">登录IP</th>
                                    <th data-field="browserInfo">浏览器</th>
                                    <th data-field="osInfo">操作系统</th>
                                    <th data-field="action" data-formatter="actionFormatter">操作</th>
                                </tr>
                                </thead>
                        </table>
	        			
	        			
	        		</div>
	        	</div>
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
<script src="${appPath}/components/bootstrap-table/bootstrap-table.min.js"></script>
<script src="${appPath}/components/bootstrap-table/tableExport.min.js"></script>
<script src="${appPath}/components/bootstrap-table/bootstrap-table-export.js"></script>
<script src="${appPath}/components/moment/min/moment-with-locales.min.js"></script>
<script src="${appPath}/components/bootstrap-daterangepicker/daterangepicker.js"></script>
<script src="${appPath}/components/bootstrap-datetimepicker/bootstrap-datetimepicker.js"></script>
<script src="${appPath}/components/bootstrap-datetimepicker/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="${appPath}/components/select2/dist/js/select2.min.js"></script>
<script src="${appPath}/components/select2/dist/js/i18n/zh-CN.js"></script>
<script src="${appPath}/js/common.js"></script>

<#include "/_components.ftl" />

<!-- AdminLTE App -->
<script src="${appPath}/js/adminlte.min.js"></script>

<script>

    toastr.options.positionClass = 'toast-top-center';

    $(document).ready(function () {
    	
        $("#submit").click(function () {
            refresh();
        });
	    
    });
    
    function requestParemeter(params) {
        var obj = {};
        obj.pageSize = params.limit;
        obj.pageNo = (params.offset / params.limit) + 1;

        return obj;
    }

    function refresh() {
        $('#table').bootstrapTable('removeAll');
        $('#table').bootstrapTable('refresh');
    }
    
    function actionFormatter(value, row, index) {
        return "<a href='javascript:void(0)' onClick='offlineBtn(" + row.userId + ");'>下线</a>";
    }
    
    function offlineBtn(id) {
    	confirm(false, "确定要下线此用户吗?", function() {
	    	$.ajax({
		        type: "POST",
		        url: "${appPath}/admin/usermanage/doOffline?id=" + id,
		        data: {},
		        dataType: "JSON",
		        success: function(res) {
		        	if (res.status) {
		        		window.location.reload();
		        	} else {
		        		toastr.error(res.message);
		        	}
		        }
	      	});
      	});
    }

</script>

</body>
</html>
