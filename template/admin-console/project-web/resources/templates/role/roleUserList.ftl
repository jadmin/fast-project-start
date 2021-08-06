<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>角色关联的用户</title>
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
  <link href="${appPath}/css/calendar.css" rel="stylesheet">
  <link href="${appPath}/css/bootstrap-ztree.css" rel="stylesheet">
  
  <link rel="shortcut icon" href="${appPath}/images/icon-48x48.png">
  
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
        角色管理
        <small><span class="label label-danger">${entity.roleName!''}</span></small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="${appPath}/console/xxx/list"><i class="fa fa-dashboard"></i> Home</a></li>
        <li><a href="#">系统管理</a></li>
        <li class="active">角色管理</li>
      </ol>
    </section>

    <!-- Main content -->
    <section class="content">
      <div class="row">
      	<div class="col-xs-12">
      		<div class="box">
      			<div class="box-body content">
        			<form id="queryForm" class="form-inline">
        				<input type="hidden" id="J_queryForm_roleId" name="roleId" class="form-control" value="${entity.id}">
						<div class="form-group">
			                <label for="userid">用户ID</label>
			                <input type="text" id="J_queryForm_userId" name="userId" class="form-control" placeholder="">
			            </div>
			            <div class="form-group">
			                <label for="username">用户账号</label>
			                <input type="text" id="J_queryForm_username" name="username" class="form-control" placeholder="">
			            </div>
			            <div class="form-group">
			                <label for="name">用户姓名</label>
			                <input type="text" id="J_queryForm_name" name="name" class="form-control" placeholder="">
			            </div>
			            <div class="form-group">
			                <label for="mobile">手机号</label>
			                <input type="text" id="J_queryForm_mobile" name="mobile" class="form-control" placeholder="">
			            </div>
			            <button type="button" class="btn btn-default" id="submit">查询</button>
			            
			        </form>
			        
			        <h4></h4>
					
					<div id="toolbar">
					    <div class="btn-group">
						    <a href="${appPath}/console/role">
						    	<button id="btn_back" type="button" class="btn btn-default">
				               		<span class="fa fa-arrow-left" aria-hidden="true"></span>返回
				           		</button>
				           	</a>
				           	<a href="${appPath}/console/role/roleUserModal?roleId=${entity.id}" data-toggle="modal" data-target='#addModal'>
						    	<button id="btn_add" type="button" class="btn btn-default">
				               		<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>关联用户
				           		</button>
				           	</a>
					    </div>
					</div>
					<table id="table"
			               data-toggle="table"
			               data-toolbar="#toolbar"
			               data-side-pagination="server"
			               data-pagination="true" 
			               data-url="${appPath}/admin/role/roleUserTableList" 
			               data-query-params="requestParameter">
			            <thead>
				            <tr>
				            	<th data-field="userId">用户ID</th>
				            	<th data-field="username">账号</th>
				            	<th data-field="name">姓名</th>
				            	<th data-field="mobile">手机号</th>
				            	<th data-field="expiredTime">到期时间</th>
				            	<th data-field="gmtCreate">创建时间</th>
				            	<th data-field="creator">创建人</th>
				                <th data-field="action" data-formatter="actionFormatter">操作</th>
				            </tr>
			            </thead>
			        </table>
			        
        			</div>
        		</div>
        	</div>
      </div>
      <!-- /.row -->
    </section>
    <!-- /.content -->    
    
  </div>
  <!-- /.content-wrapper -->


	<#include "/_footer.ftl" />

</div>
<!-- ./wrapper -->

<!-- addModal start -->
<div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="addModalLabel">
  <div class="modal-dialog" role="document" style="width:900px; height:400px;">
    <div class="modal-content">

    </div>
  </div>
</div>
<!-- Modal end -->


<!-- updateModal start -->
<div class="modal fade" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="updateModalLabel">
    <div class="modal-dialog" role="document" style="width:900px;height:400px;">
        <div class="modal-content">

        </div>
    </div>
</div>
<!-- Modal end -->

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
<script src="${appPath}/js/ztree/jquery.ztree.core.js"></script>
<script src="${appPath}/js/ztree/jquery.ztree.excheck.js"></script>
<script src="${appPath}/js/ztree/jquery.ztree.exedit.js"></script>

<script src="${appPath}/js/common.js"></script>
<script src="${appPath}/js/ui.js"></script>

<#include "/_components.ftl" />

<!-- FastClick -->
<script src="${appPath}/components/fastclick/1.0.6/fastclick.min.js"></script>
<!-- AdminLTE App -->
<script src="${appPath}/js/adminlte.min.js"></script>

<script>

toastr.options.positionClass = 'toast-top-center';

$(document).ready(function() {

	$("#submit").click(function() {
        refresh();
    });
    
	$("#addModal").on("hidden.bs.modal", function() {  
        $(this).removeData("bs.modal");
        $(this).find(".modal-content").children().remove(); 
	});
	
	$("#updateModal").on("hidden.bs.modal", function() {  
        $(this).removeData("bs.modal");
        $(this).find(".modal-content").children().remove(); 
	});
	
});

function requestParameter(params) {
	var obj = {};
	obj.pageSize = params.limit;
	obj.pageNo = (params.offset / params.limit) + 1;
	
	var userId = $('#queryForm input[name=userId]').val();
	var username = $('#queryForm input[name=username]').val();
	var mobile = $('#queryForm input[name=mobile]').val();
	var name = $('#queryForm input[name=name]').val();
	
	obj.roleId = $('#queryForm input[name=roleId]').val();
	if ( !isBlank(userId) ) {
		obj.userId = userId;
	}
	if ( !isBlank(username) ) {
		obj.username = username;
	}
	if ( !isBlank(mobile) ) {
		obj.mobile = mobile;
	}
	if ( !isBlank(name) ) {
		obj.nameLike = name;
	}
	return obj;
}

function refresh() {
	$('#table').bootstrapTable('removeAll');
	$('#table').bootstrapTable('refresh');
}


function actionFormatter(value, row, index) {
	var html = "";
	var modalUrl = "${appPath}/console/role/roleUserModal?id=" + row.id;
	html += '<a data-toggle="modal" data-target="#updateModal" href="' + modalUrl + '">延长有效期</a>&nbsp;';
	html += '<a href="javascript:void(0)" onClick="deleteBtn(' + row.id + ');">移除</a>&nbsp;';
	return html;
}

function deleteBtn(id) {
    confirm(false, "确定要移除此用户吗?", function() {
	
		$.ajax({
	        type: "POST",
	        url: "${appPath}/admin/role/delRoleUser?id=" + id,
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


function clickQueryUser() {

	var value = $('#J_modal_userinfo').val();
	if ( isBlank(value) ) {
		toastr.error("请输入要关联的用户信息");
		return;
	}
	$.ajax({
        type: "GET",
        url: "${appPath}/admin/usermanage/queryUser?q=" + value,
        dataType: "JSON",
        success: function(res) {
        	if (res.status) {
        		var user = res.entry;
        		$('#J_form_userId').val(user.id);
        		$('#J_modal_name').val(user.name);
        		$('#J_modal_username').val(user.username);
        		$('#J_modal_mobile').val(user.mobile);
        		$('#J_modal_email').val(user.email);
        		toastr.success("用户信息已加载");
        	} else {
        		$('#J_form_userId').val('');
        		$('#J_modal_name').val('');
        		$('#J_modal_username').val('');
        		$('#J_modal_mobile').val('');
        		$('#J_modal_email').val('');
        		toastr.error(res.message);
        	}
        }
	});

}

function clickSaveBtn() {
	var id = $('#moduleForm input[name=id]').val();
	var roleId = $('#moduleForm input[name=roleId]').val();
	var userId = $('#moduleForm input[name=userId]').val();
	var expireType = $('#moduleForm input[name=expireType]:checked').val();
	
	var expiredTime = $('#moduleForm input[name=expiredTime]').val();
	
	if ( isBlank(roleId) || roleId == '0' ) {
		toastr.error("选择的角色不能为空");
        return;
	}
	if ( isBlank(userId) || userId == '0' ) {
		toastr.error("选择的用户不能为空");
        return;
	}
	if ( isBlank(expireType) ) {
		toastr.error("赋予用户角色的有效期不能为空");
        return;
	}
	
	if( expireType == '8' && isBlank(expiredTime) ) {
		toastr.error("请填写自定义有效期时间");
        return;
	}
	
	let post = {};
	post.id = id;
	post.roleId = roleId;
	post.userId = userId;
	post.expireType = expireType;
	if (expireType == '8') {
		post.expiredTime = expiredTime;
	}
    
    var modalId = (!isBlank(id) && parseInt(id) > 0) ? 'updateModal' : 'addModal';
    $.ajax({
        url: "${appPath}/admin/role/saveRoleUserRequest",
        data: JSON.stringify(post),
        type: "POST",
        contentType: 'application/json; charset=UTF-8',
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
