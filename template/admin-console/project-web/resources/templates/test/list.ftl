<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>测试Demo页</title>
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
        测试Demo页
        <small></small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="${appPath}/console/xxx/list"><i class="fa fa-dashboard"></i> Home</a></li>
        <li><a href="#">XX中心</a></li>
        <li class="active">测试Demo页</li>
      </ol>
    </section>

    <!-- Main content -->
    <section class="content">
      <div class="row">
      	<div class="col-xs-12">
      		<div class="box box-success">
      			<div class="box-body content">
        			<form id="queryForm" class="form-inline">
						<div class="form-group">
			                <label for="username">用户名</label>
			                <input type="text" id="J_queryForm_username" name="username" class="form-control" placeholder="">
			            </div>
						<div class="form-group">
			                <label for="userType">用户类型</label>
			                <select id="J_queryForm_userType" name="userType" class="form-control input-sm">
			                	<option value="">全部</option>
			                	<option value="1">普通</option>
			                	<option value="2">高级</option>
			                	<option value="3">VIP</option>
			                	<option value="9">超管</option>
			                </select>
			            </div>
						<div class="form-group">
			                <label for="sex">性别</label>
			                <select id="J_queryForm_sex" name="sex" class="form-control input-sm">
			                	<option value="">全部</option>
			                	<option value="0">未知</option>
			                	<option value="1">男</option>
			                	<option value="2">女</option>
			                </select>
			            </div>
			            <button type="button" class="btn btn-default" id="submit">查询</button>
			            
			        </form>
			        
			        <h4></h4>
					
					<div id="toolbar">
					    <div class="btn-group">
						    <a href="${appPath}/console/test/modal" data-toggle="modal" data-target='#addModal'>
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
			               data-pagination="true" 
			               data-url="${appPath}/admin/test/bootstrapTableList" 
			               data-query-params="requestParameter">
			            <thead>
				            <tr>
				            	<th data-field="id">ID</th>
				            	<th data-field="username">用户名</th>
				            	<th data-field="sex" data-formatter="sexFormatter">性别</th>
				            	<th data-field="userType" data-formatter="userTypeFormatter">用户类型</th>
				            	<th data-field="email">电子邮件</th>
				            	<th data-field="gmtCreate">创建时间</th>
				            	<th data-field="gmtModify">修改时间</th>
				            	<th data-field="creator">创建人</th>
				            	<th data-field="modifier">修改人</th>
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
<div class="modal fade" id="addModal" role="dialog" aria-labelledby="addModalLabel">
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
    
	$("#updateModal").on("hidden.bs.modal", function() {  
        $(this).removeData("bs.modal");
        $(this).find(".modal-content").children().remove(); 
	});
	
});

function requestParameter(params) {
	var obj = {};
	obj.pageSize = params.limit;
	obj.pageNo = (params.offset / params.limit) + 1;
	
	var username = $('#queryForm input[name=username]').val();
	var userType = $('#queryForm select[name=userType] option:selected').val();
	var sex = $('#queryForm select[name=sex] option:selected').val();
	
	if ( !isBlank(username) ) {
		obj.usernameLike = username;
	}
	if ( !isBlank(userType) ) {
		obj.userType = userType;
	}
	if ( !isBlank(sex) ) {
		obj.sex = sex;
	}
	return obj;
}

function refresh() {
	$('#table').bootstrapTable('removeAll');
	$('#table').bootstrapTable('refresh');
}

function sexFormatter(value, row, index) {
	var config = {'0': '未知','1': '男','2': '女'};
	return textByEnum(row.sex, config);
}

function userTypeFormatter(value, row, index) {
	var config = {'1': '普通','2': '高级','3': 'VIP','9': '超管'};
	return textByEnum(row.userType, config);
}


function actionFormatter(value, row, index) {
	var html = "";
	var modalUrl = "${appPath}/console/test/modal?id=" + row.id;
	html += '<a data-toggle="modal" data-target="#updateModal" href="' + modalUrl + '">编辑</a>&nbsp;';
	html += '<a href="javascript:void(0)" onClick="deleteBtn(' + row.id + ');">删除</a>&nbsp;';
	return html;
}

function deleteBtn(id) {
    confirm(false, "确定要删除此记录吗?", function() {
	
		$.ajax({
	        type: "POST",
	        url: "${appPath}/admin/test/delete?id=" + id,
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
    
	var username = $('#moduleForm input[name=username]').val();
	var userType = $('#moduleForm select[name=userType] option:selected').val();
	var sex = $('#moduleForm select[name=sex] option:selected').val();
	var email = $('#moduleForm input[name=email]').val();
	var attributes = $('#moduleForm textarea[name=attributes]').val();
	
	if ( isBlank(username) ) {
		toastr.error("用户名不能为空");
        return;
	}
	if ( isBlank(userType) ) {
		toastr.error("用户类型不能为空");
        return;
	}
	if ( isBlank(sex) ) {
		toastr.error("性别不能为空");
        return;
	}
	if ( isBlank(email) ) {
		toastr.error("电子邮件不能为空");
        return;
	}
	if ( isBlank(attributes) ) {
		toastr.error("attributes不能为空");
        return;
	}
    
    var modalId = (!isBlank(id) && parseInt(id) > 0) ? 'updateModal' : 'addModal';
    $.ajax({
        url: "${appPath}/admin/test/saveRequest",
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
