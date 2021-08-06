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
        用户账号
        <small></small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="${appPath}/console/index"><i class="fa fa-dashboard"></i> Home</a></li>
        <li><a href="#">用户管理</a></li>
        <li class="active">用户账号</li>
      </ol>
    </section>

    <!-- Main content -->
    <section class="content">
    
    	<div class="row">
	      	<div class="col-xs-12">
	      		<div class="box">
	      			<div class="box-body content">
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
	        			<table id="table"
                                   data-toggle="table"
                                   data-toolbar="#toolbar"
                                   data-side-pagination="server"
                                   data-show-export="false"
                                   data-pagination="true"
                                   data-url="${appPath}/admin/usermanage/userList"
                                   data-query-params="requestParemeter">
                                <thead>
                                <tr>
                                    <th data-field="username">用户名</th>
                                    <th data-field="name">姓名</th>
                                    <th data-field="mobile">手机号</th>
                                    <th data-field="email">邮件</th>
                                    <th data-field="status" data-formatter="statusFormatter">状态</th>
                                    <th data-field="gmtCreate">创建时间</th>
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
  
  
<!-- addModal start -->
<div class="modal fade" id="addModal" role="dialog" aria-labelledby="addModalLabel">
  <div class="modal-dialog" role="document" style="width:600px; height:400px;">
    <div class="modal-content">

    </div>
  </div>
</div>
<!-- Modal end -->


<!-- updateModal start -->
<div class="modal fade" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="updateModalLabel">
    <div class="modal-dialog" role="document" style="width:600px;height:400px;">
        <div class="modal-content">

        </div>
    </div>
</div>
<!-- Modal end -->


<div class="modal fade" id="rolesModal" tabindex="-1" role="dialog" aria-labelledby="rolesModalLabel">
    <div class="modal-dialog" role="document" style="width:800px;height:400px;">
        <div class="modal-content">

        </div>
    </div>
</div>
  
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
    	
    	$('#J_userRole').select2({
		    language: "zh-CN",
	        placeholder: "请选择用户关联的角色"
        });
    	
        $("#submit").click(function () {
            refresh();
        });
        
        $("#updateModal").on("hidden.bs.modal", function() {  
            $(this).removeData("bs.modal");
            $(this).find(".modal-content").children().remove(); 
	    });
	    
	    $("#rolesModal").on("hidden.bs.modal", function() {  
            $(this).removeData("bs.modal");
            $(this).find(".modal-content").children().remove(); 
	    });
	    
    });
    
    function requestParemeter(params) {
        var obj = {};
        obj.pageSize = params.limit;
        obj.pageNo = (params.offset / params.limit) + 1;

        var username = $("input[name='username']").val();
        var nameLike = $("input[name='nameLike']").val();
        var mobile = $("input[name='mobile']").val();
        var email = $("input[name='email']").val();
        var userStatus = $("#userStatus").val();

        if (!isEmpty(username)) {
            obj.username = username;
        }
        if (!isEmpty(nameLike)) {
            obj.nameLike = nameLike;
        }
        if (!isEmpty(mobile)) {
            obj.mobile = mobile;
        }
        if (!isEmpty(email)) {
            obj.email = email;
        }
        if (!isEmpty(userStatus)) {
            obj.status = userStatus;
        }

        return obj;
    }

    function refresh() {
        $('#table').bootstrapTable('removeAll');
        $('#table').bootstrapTable('refresh');
    }
    
    function statusFormatter(value, row, index) {
    	if(value == 0) {
    		return "<span class='label label-gray'>未激活</span>";
    	} else if (value == 1) {
    		return "<span class='label label-success'>正常</span>";
    	} else if (value == 2) {
    		return "<span class='label label-danger'>已禁用</span>";
    	}
    	return "";
    }

    function actionFormatter(value, row, index) {
    	var modalUrl = "${appPath}/console/user/modal?id=" + row.id;
    	var html = '<a data-toggle="modal" data-target="#updateModal" href="' + modalUrl + '">编辑</a>&nbsp;';
        if(row.status == 2) {
        	html += "<a href='javascript:void(0)' onClick='enableBtn(" + row.id + ");'>启用</a>&nbsp;";
        } else {
        	html += "<a href='javascript:void(0)' onClick='disableBtn(" + row.id + ");'>禁用</a>&nbsp;";
        }
        var userRolesModalUrl = "${appPath}/console/user/userRoleModal?id=" + row.id;
        html += '<a data-toggle="modal" data-target="#rolesModal" href="' + userRolesModalUrl + '">分配角色</a>&nbsp;';
        
        return html;
    }
    
    
    function enableBtn(id) {
    	$.ajax({
		        type: "POST",
		        url: "${appPath}/admin/usermanage/doEnable?id=" + id,
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
    }
    
    function disableBtn(id) {
	    confirm(false, "确定要禁用此用户吗?", function() {
		
			$.ajax({
		        type: "POST",
		        url: "${appPath}/admin/usermanage/doDisable?id=" + id,
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


function clickSaveBtn() {
	var id = $('#moduleForm input[name=id]').val();
	var persited = !isBlank(id) && parseInt(id) > 0;
	
	var username = $('#moduleForm input[name=username]').val();
	var password = $('#moduleForm input[name=password]').val();
	var name = $('#moduleForm input[name=name]').val();
	var mobile = $('#moduleForm input[name=mobile]').val();
	var email = $('#moduleForm input[name=email]').val();
	
	if(isEmpty(username) && !persited) {
	    toastr.error("用户名不能为空");
	    return;
	}
	if(isEmpty(password) && !persited) {
	    toastr.error("密码不能为空");
	    return;
	}
	if(isEmpty(name)) {
	    toastr.error("姓名不能为空");
	    return;
	}
	if(isEmpty(mobile)) {
	    toastr.error("手机号不能为空");
	    return;
	}
	if(isEmpty(email)) {
	    toastr.error("邮件地址不能为空");
	    return;
	}
    
    var modalId = persited ? 'updateModal' : 'addModal';
    $.ajax({
        url: "${appPath}/admin/usermanage/saveRequest",
        data: $("#moduleForm").serialize(),
        type: "POST",
        success: function (res) {
        	$('#' + modalId).modal('hide');
            if (res.status) {
                toastr.success("恭喜，操作已成功！");
                window.location.reload();
            } else {
                toastr.error("操作失败：" + res.message);
            }
        }
    });
}

</script>

</body>
</html>
