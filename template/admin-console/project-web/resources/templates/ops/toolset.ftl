<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>工具配置</title>
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
        工具配置
        <small></small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="${appPath}/console/index"><i class="fa fa-dashboard"></i> Home</a></li>
        <li><a href="#">工具中心</a></li>
        <li class="active">工具配置</li>
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
			                <label for="widgetName">组件名称</label>
			                <input type="text" class="form-control" id="widgetName" name="widgetName" placeholder="">
			            </div>
			            <div class="form-group">
			                <label for="widgetType">组件类型</label>
			                <select id="widgetType" name="widgetType" class="form-control input-sm">
			                		<option value="">全部类型</option>
			                		<option value="1">开发工具</option>
			                		<option value="2">业务工具</option>
			                </select>
			            </div>
			            <div class="form-group">
			                <label for="status">状态</label>
			                <select id="status" name="status" class="form-control input-sm">
			                		<option value="">全部类型</option>
			                		<option value="-1">不可用</option>
			                		<option value="0">开发调试中</option>
			                		<option value="1">可用</option>
			                </select>
			            </div>
			            <button type="button" class="btn btn-default" id="submit">查询</button>
			            
			        </form>
			        
			        <h4></h4>
					
					<div id="toolbar">
					    <div class="btn-group">
						    <a href="${appPath}/console/ops/toolset/modal" data-toggle="modal" data-target='#addModal'>
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
			               data-url="${appPath}/admin/ops/tools/listData" 
			               data-query-params="requestParameter">
			            <thead>
				            <tr>
				            	<th data-field="id">ID</th>
				                <th data-field="widgetName">组件名称</th>
				                <th data-field="widgetType" data-formatter="typeFormatter">组件类型</th>
				                <th data-field="status" data-formatter="statusFormatter">状态</th>
				                <th data-field="gmtCreate">创建时间</th>
				                <th data-field="creator">操作人</th>
				                
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
	
	var widgetName = $("#widgetName").val();
	var widgetType = $("#widgetType").val();
	var status = $("#status").val();
	
	if(!isBlank(widgetName)) {
		obj.widgetNameLike = widgetName;
	}
	if(!isBlank(widgetType)) {
		obj.widgetType = widgetType;
	}
	if(!isEmpty(status)) {
		obj.status = status;
	}
	
	return obj;
}

function refresh() {
	$('#table').bootstrapTable('removeAll');
	$('#table').bootstrapTable('refresh');
}

function typeFormatter(value, row, index) {
	return textByEnum(row.widgetType, {'1' : '开发工具', '2' : '业务工具'});
}

function statusFormatter(value, row, index) {
	var config = {
	  '-1' : 'danger:不可用',
	   '0' : 'primary:开发调试中',
	   '1' : 'success:可用'
	};
	return labelByEnum(row.status, config);
}

function actionFormatter(value, row, index) {
	var html = "";
	var modalUrl = "${appPath}/console/ops/toolset/modal?id=" + row.id;
	if (row.status == -1) {
		html += '<a href="javascript:void(0)" onClick="ingBtn(' + row.id + ');">调试</a>&nbsp;';
		html += '<a href="javascript:void(0)" onClick="enableBtn(' + row.id + ');">启用</a>&nbsp;';
	<#if developAdmin?? && developAdmin>
		html += '<a href="javascript:void(0)" onClick="deleteBtn(' + row.id + ');">删除</a>&nbsp;';
	</#if>
	} else if (row.status == 0) {
		html += '<a data-toggle="modal" data-target="#updateModal" href="' + modalUrl + '">编辑</a>&nbsp;';
		html += '<a href="javascript:void(0)" onClick="disableBtn(' + row.id + ');">废弃</a>&nbsp;';
		html += '<a href="javascript:void(0)" onClick="enableBtn(' + row.id + ');">启用</a>&nbsp;';
	} else if (row.status == 1) {
		html += '<a data-toggle="modal" data-target="#updateModal" href="' + modalUrl + '">编辑</a>&nbsp;';
		html += '<a href="javascript:void(0)" onClick="disableBtn(' + row.id + ');">废弃</a>&nbsp;';
		html += '<a href="javascript:void(0)" onClick="ingBtn(' + row.id + ');">调试</a>&nbsp;';
	}
	return html;
}


function disableBtn(id) {
		confirm(false, "确定要废弃此组件吗?", function() {
		
			$.ajax({
		        type: "POST",
		        url: "${appPath}/admin/ops/toolsDisable?id=" + id,
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


function ingBtn(id) {
	    confirm(false, "确定此组件进入维护状态吗?", function() {
		
			$.ajax({
		        type: "POST",
		        url: "${appPath}/admin/ops/toolsIng?id=" + id,
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


function enableBtn(id) {
	    confirm(false, "确定正式生效此组件吗?", function() {
		
			$.ajax({
		        type: "POST",
		        url: "${appPath}/admin/ops/toolsEnable?id=" + id,
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

function deleteBtn(id) {
	    confirm(false, "确定要删除此组件吗?", function() {
		
			$.ajax({
		        type: "POST",
		        url: "${appPath}/admin/ops/toolsDelete?id=" + id,
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
    var widgetName = $('#moduleForm input[name=widgetName]').val();
    var widgetType = $('#moduleForm input[name=widgetType]:checked').val();
      
    var widgetHtml = $('#moduleForm textarea[name=widgetHtml]').val();

    if(isEmpty(widgetName)) {
        toastr.error("组件名称不能为空");
        return;
    }
	if(isEmpty(widgetType)) {
        toastr.error("请选择组件类型");
        return;
    }
	if(isEmpty(widgetHtml)) {
        toastr.error("Html代码不能为空");
        return;
    }
    
    var modalId = (!isBlank(id) && parseInt(id) > 0) ? 'updateModal' : 'addModal';
    $.ajax({
        url: "${appPath}/admin/ops/tools/saveRequest",
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
