<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>角色管理</title>
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
        <small></small>
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
						<div class="form-group">
			                <label for="roleName">角色名称</label>
			                <input type="text" id="J_queryForm_roleName" name="roleName" class="form-control" placeholder="">
			            </div>
						<div class="form-group">
			                <label for="status">状态</label>
			                <select id="J_queryForm_status" name="status" class="form-control input-sm">
			                	<option value="">全部</option>
			                	<option value="0">已创建</option>
			                	<option value="1">正常</option>
			                	<option value="2">已停用</option>
			                </select>
			            </div>
			            <button type="button" class="btn btn-default" id="submit">查询</button>
			            
			        </form>
			        
			        <h4></h4>
					
					<div id="toolbar">
					    <div class="btn-group">
						    <a href="${appPath}/console/role/modal" data-toggle="modal" data-target='#addModal'>
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
			               data-url="${appPath}/admin/role/bootstrapTableList" 
			               data-query-params="requestParameter">
			            <thead>
				            <tr>
				            	<th data-field="id">ID</th>
				            	<th data-field="roleCode">角色代码</th>
				            	<th data-field="roleName"data-formatter="roleNameFormatter">角色名称</th>
				            	<th data-field="status" data-formatter="statusFormatter">状态</th>
				            	<th data-field="gmtCreate">创建时间</th>
				            	<th data-field="gmtModify">变更时间</th>
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
    
	$("#updateModal").on("hidden.bs.modal", function() {  
        $(this).removeData("bs.modal");
        $(this).find(".modal-content").children().remove(); 
	});
	
});

function requestParameter(params) {
	var obj = {};
	obj.pageSize = params.limit;
	obj.pageNo = (params.offset / params.limit) + 1;
	
	var roleName = $('#queryForm input[name=roleName]').val();
	var status = $('#queryForm select[name=status] option:selected').val();
	
	if ( !isBlank(roleName) ) {
		obj.roleNameLike = roleName;
	}
	if ( !isBlank(status) ) {
		obj.status = status;
	}
	return obj;
}

function refresh() {
	$('#table').bootstrapTable('removeAll');
	$('#table').bootstrapTable('refresh');
}

function statusFormatter(value, row, index) {
	var config = {'0': '已创建','1': '正常','2': '已停用'};
	return textByEnum(row.status, config);
}

function roleNameFormatter(value, row, index) {
	var html = '<a href="${appPath}/console/role?id=' + row.id + '">' + row.roleName + '</a>';
	return html
}

function actionFormatter(value, row, index) {
	var html = '';
	var modalUrl = "${appPath}/console/role/modal?id=" + row.id;
	html += '<a data-toggle="modal" data-target="#updateModal" href="' + modalUrl + '">编辑</a>&nbsp;';
	html += '<a href="javascript:void(0)" onClick="deleteBtn(' + row.id + ');">删除</a>&nbsp;';
	return html;
}


function deleteBtn(id) {
    confirm(false, "确定要删除此记录吗?", function() {
	
		$.ajax({
	        type: "POST",
	        url: "${appPath}/admin/role/delete?id=" + id,
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
	var roleName = $('#moduleForm input[name=roleName]').val();
	var roleCode = $('#moduleForm input[name=roleCode]').val();
	var status = $('#moduleForm select[name=status] option:selected').val();
	var description = $('#moduleForm input[name=description]').val();
	
	if ( isBlank(roleName) ) {
		toastr.error("角色名称不能为空");
        return;
	}
	if ( isBlank(roleCode) ) {
		toastr.error("角色代码不能为空");
        return;
	}
	if ( isBlank(status) ) {
		toastr.error("状态不能为空");
        return;
	}
	
	let post = {};
	let role = {};
	if(id > 0) {
		role.id = id;
	}
	role.roleName = roleName;
	role.roleCode = roleCode;
	role.status = status;
	role.description = description;
	
	var menuParams = new Array();
	var nodes = zTreeObj.getCheckedNodes(true);
    for(var i=0;i < nodes.length; i++) {
        var type = nodes[i].type;
        var idArray = new Array();
        if(type == 3) {
        	if(nodes[i].attributesMap && nodes[i].attributesMap.bindResources) {
        		var res = nodes[i].attributesMap.bindResources;
        		if(Array.isArray(res) && res.length > 0) {
					idArray = res;
				} else if ( !isBlank(res) ) {
					idArray.push(res);
				}
        	}
        } else if (type == 2) {
        	if(nodes[i].attributesMap && nodes[i].attributesMap.pageResource) {
        		var res = nodes[i].attributesMap.pageResource;
        		if ( !isBlank(res) ) {
					idArray.push(res);
				}
        	}
        }
        menuParams.push({
	    	menuId: nodes[i].id,
	    	menuType: type,
	    	bindResources: idArray
	    });
    }
	post.role = role;
	post.menuParams = menuParams;
    
    var modalId = (!isBlank(id) && parseInt(id) > 0) ? 'updateModal' : 'addModal';
    $.ajax({
        url: "${appPath}/admin/role/saveRequest",
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
