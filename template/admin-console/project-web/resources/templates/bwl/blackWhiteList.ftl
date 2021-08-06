<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>名单配置</title>
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
        名单配置
        <small></small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="${appPath}/console/index"><i class="fa fa-dashboard"></i> Home</a></li>
        <li><a href="#">名单中心</a></li>
        <li class="active">名单配置</li>
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
			                <label for="name">名单代码</label>
			                <select id="bizCode" name="bizCode" style="width: 200px" class="bizcode-select form-control input-sm">
								<option value="">全部</option>
			                	<#list groupList as item>
			                		<option value="${item.key}">${item.value}</option>
			                	</#list>	
			                	</select>
			            </div>
			            <div class="form-group">
			                <label for="mobile">名单值</label>
			                <input type="text" class="form-control" id="identity" name="identity" placeholder="">
			            </div>
			            <div class="form-group">
			                <label for="type">名单类型</label>
			                <select id="type" name="type" class="form-control input-sm">
			                		<option value="">全部名单</option>
			                		<option value="1">黑名单</option>
			                		<option value="2">白名单</option>
			                		<option value="3">数据配置</option>
			                		
			                	</select>
			            </div>
			            <div class="form-group">
			                <label for="expireType">有效期</label>
			                <select id="expireType" name="expireType" class="form-control input-sm">
			                		<option value="">全部类型</option>
			                		<option value="6">永久</option>
			                		<option value="1">1天</option>
			                		<option value="2">3天</option>
			                		<option value="3">10天</option>
			                		<option value="4">1个月</option>
			                		<option value="5">3个月</option>
			                		<option value="7">自定义</option>
			                		
			                	</select>
			            </div>
			            <button type="button" class="btn btn-default" id="submit">查询</button>
			            <#if blackWhiteListAdmin>
							<button type="button" class="btn btn-default" id="J_refresh_cache" data-acl-component="true" data-acl-key="f_freshBwlCache">
			                    <span aria-hidden="true"></span>刷新缓存
			                </button>
						</#if>
			        </form>
			        
			        <h4></h4>
					
					<div id="toolbar">
					    <div class="btn-group">
					    		<button id="btn_add" type="button" class="btn btn-default" onclick="clickAddBtn();" data-toggle="modal" data-target="#newModule">
			               		<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
			           		</button>
					    </div>
					</div>
					<table id="table"
			               data-toggle="table"
			               data-toolbar="#toolbar"
			               data-side-pagination="server"
			               data-show-export="true" 
			               data-pagination="true" 
			               data-url="${appPath}/admin/bwl/listData.do" 
			               data-query-params="requestParameter">
			            <thead>
				            <tr>
				            	    <th data-field="id">ID</th>
				                <th data-field="bizName" data-formatter="bizNameFormatter">名单代码</th>
				                <th data-field="identity" data-formatter="identityValueFormatter">名单值</th>
				                <th data-field="type" data-formatter="typeFormatter">名单类型</th>
				                <th data-field="comment">备注说明</th>
				                <th data-field="gmtCreate">创建时间</th>
				                <th data-field="expireType" data-formatter="expireTypeFormatter">有效期</th>
				                <th data-field="expireTime" data-formatter="expireTimeFormatter">过期时间</th>
				                <th data-field="creatorName">操作人</th>
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

<!-- Modal start -->
<div class="modal fade" id="newModule" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document" style="width:600px; height:400px;">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">添加配置名单值</h4>
      </div>
      <div class="modal-body">
        <form class="form-horizontal" id="moduleForm" method="post" action="${appPath}/admin/bwl/saveRequest.do">
          <div class="form-group">
            <label for="code" class="col-sm-2 control-label">名单类型</label>
            <div class="col-sm-9">
              <label class="radio-inline">
				<input type="radio" name="type" id="radio_1" value="1"> 黑名单
			  </label>
			  <label class="radio-inline">
				<input type="radio" name="type" id="radio_2" value="2"> 白名单
			  </label>
			  <label class="radio-inline">
				<input type="radio" name="type" id="radio_3" value="3"> 配置项名单
			  </label>
            </div>
          </div>
          <div class="form-group">
            <label for="idcard" class="col-sm-2 control-label">业务代码</label>
            <div class="col-sm-9">
              <select id="F_bizCode" name="bizCode" style="width:100%" class="box-bizcode-selector form-control input-sm">
              	<option></option>
                	<#list groupList as item>
                		<option value="${item.key}">${item.value}</option>
                	</#list>	
            	  </select>
            </div>
          </div>
          <div class="form-group">
            <label for="code" class="col-sm-2 control-label">有效期</label>
            <div class="col-sm-9">
            	  <label class="radio-inline">
				<input type="radio" name="expireType" id="radio_6" value="6" checked> 永久
			  </label>
              <label class="radio-inline">
				<input type="radio" name="expireType" id="radio_1" value="1"> 1天
			  </label>
			  <label class="radio-inline">
				<input type="radio" name="expireType" id="radio_2" value="2"> 3天
			  </label>
			  <label class="radio-inline">
				<input type="radio" name="expireType" id="radio_3" value="3"> 10天
			  </label>
			  <label class="radio-inline">
				<input type="radio" name="expireType" id="radio_4" value="4"> 1个月
			  </label>
			  <label class="radio-inline">
				<input type="radio" name="expireType" id="radio_5" value="5"> 3个月
			  </label>
			  <label class="radio-inline">
				<input type="radio" name="expireType" id="radio_7" value="7"> 自定义时长 <input type="text" id="expireTime" name="expireTime" placeholder="">
			  </label>
            </div>
          </div>
          <div class="form-group">
            <label for="idcard" class="col-sm-2 control-label">名单值</label>
            <div class="col-sm-9">
            	  <textarea type="text" class="form-control" name="identity" id="F_identity" rows="10" placeholder="多个值，逗号分隔"></textarea>
            </div>
          </div>
          <div class="form-group">
            <label for="comment" class="col-sm-2 control-label">备注说明</label>
            <div class="col-sm-9">
            	  <textarea type="text" class="form-control" name="comment" id="F_comment" rows="3" placeholder=""></textarea>
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-offset-8 col-sm-4">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="J_save">确定</button>
            </div>
          </div>
        </form>
      </div>

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

<#include "/_components.ftl" />

<!-- FastClick -->
<script src="${appPath}/components/fastclick/1.0.6/fastclick.min.js"></script>
<!-- AdminLTE App -->
<script src="${appPath}/js/adminlte.min.js"></script>

<script>

toastr.options.positionClass = 'toast-top-center';
$("#bizCode").val('${bizCode}');

$(document).ready(function() {

	$('.bizcode-select').select2({
	    language: "zh-CN",
        placeholder: "请选择业务名单类型"
	});

	$("#submit").click(function() {
        refresh();
    });
    
    $("#expireTime").datetimepicker({
		language: 'zh-CN',
		format: "yyyy-mm-dd hh:ii:ss",
		todayBtn: true,
		autoclose: true
	});
	
	$('.box-bizcode-selector').select2({
		language: "zh-CN",
        placeholder: "请选择业务名单类型"
	});
});

function requestParameter(params) {
	var obj = {};
	obj.pageSize = params.limit;
	obj.pageNo = (params.offset / params.limit) + 1;
	
	var bizCode = $("#bizCode").val();
	var identity = $("input[name='identity']").val();
	var type = $("#type").val();
	var expireType = $("#expireType").val();
	
	if(!isEmpty(bizCode)) {
		obj.bizCode = bizCode;
	}
	if(!isEmpty(identity)) {
		obj.identity = identity;
	}
	if(!isEmpty(type)) {
		obj.type = type;
	}
	if(!isEmpty(expireType)) {
		obj.expireType = expireType;
	}
	
	return obj;
}

function refresh() {
	$('#table').bootstrapTable('removeAll');
	$('#table').bootstrapTable('refresh');
}

function typeFormatter(value, row, index) {
	var result = "";
	switch(row.type) {
		case 1:
			result = "<span class='label label-danger'>黑名单</span>";
			break;
		case 2:
			result = "<span class='label label-success'>白名单</span>";
			break;
		case 3:
			result = "<span class='label label-info'>配置项</span>";
			break;
	}
	return result;
}

function expireTimeFormatter(value, row, index) {
	var result = "-";
	if(row.expireType == 6 || isEmpty(row.expireTime)) {
		return "-";
	}
	return row.expireTime;
}

function expireTypeFormatter(value, row, index) {
	var result = "";
	switch(row.expireType) {
		case 1:
			result = "1天";
			break;
		case 2:
			result = "3天";
			break;
		case 3:
			result = "10天";
			break;
		case 4:
			result = "1个月";
			break;
		case 5:
			result = "3个月";
			break;
		case 6:
			result = "永久";
			break;
		case 7:
			result = "自定义";
			break;
	}
	return result;
}

function actionFormatter(value, row, index) {
	return '<a href="javascript:void(0)" onClick="doDel('+row.id+');">删除</a>&nbsp;';
}

function bizNameFormatter(value, row, index) {
	var link = '${appPath}/console/bwl/blackWhiteList?bizCode=' + row.bizCode;
	return "<a href='" + link + "'>" + row.bizName + "</a>";
}

function identityValueFormatter(value, row, index) {
	if (row.bizCode == 'forbidDevice') {
		var link = '${appPath}/bw/accountImeiList?imei=' + row.identity;
		return "<a href='" + link + "' target='_blank'>" + row.identity + "</a>";
	} else if (row.bizCode == 'riskUserAccount' || row.bizCode == 'createOrderForbidUser' 
	   || row.bizCode == 'forbiddenGcoin' || row.bizCode == 'forbidGetGcoin' 
	   || row.bizCode == 'freightInsureForbidUser' || row.bizCode == 'forbidGetCoupon' 
	   || row.bizCode == 'freightInsurePassUser' || row.bizCode == 'forbidUseCoupon') {
		var link = '${appPath}/bw/accountImeiList?accountId=' + row.identity;
		return "<a href='" + link + "' target='_blank'>" + row.identity + "</a>";
	} else if (row.bizCode == 'forbiddenIp') {
		var link = '${appPath}/bw/accountImeiList?ip=' + row.identity;
		return "<a href='" + link + "' target='_blank'>" + row.identity + "</a>";
	}
	return row.identity;
}

function clickAddBtn() {
	$('#F_bizCode').val("");
	$('#F_identity').val("");
}

function doDel(id) {
	confirm(true, "您确定要删除该名单项吗?", function() {
		var url = '${appPath}/admin/bwl/delete.do';
    	$.post(url, { id: id }, function(data) {
	        if(data.status) {
	        	toastr.success("恭喜，操作成功！");
	          	refresh();
	       	} else {
	          	toastr.error(data.message);
	       	}
    	}, "json");
	});
}

$(function() {
    $("#J_save").click(function () {
    		
      var nameType = $('#newModule input[name=type]:checked').val();
      var bizCode = $('#newModule select[name=bizCode]').val();
      var namelist = $('#newModule textarea[name=identity]').val();
	  
      var expireType = $('#newModule input[name=expireType]:checked').val();
      var expireTime = $('#newModule input[name=expireTime]').val();

      if(isEmpty(nameType)) {
        toastr.error("操作失败：名单类型不能为空");
        return;
      }
	  if(isEmpty(bizCode)) {
        toastr.error("操作失败：业务代码不能为空");
        return;
      }
	  if(expireType == 7 && isEmpty(expireTime)) {
	  	toastr.error("操作失败：自定义时长设置不能为空");
        return;
	  }
	  
	  if(isEmpty(namelist)) {
        toastr.error("操作失败：名单值不能为空");
        return;
      }
    		
      $.ajax({
            url: "${appPath}/admin/bwl/saveRequest.do",
            data: $("#moduleForm").serialize(),
            type: "POST",
            success: function (res) {
            	$("#newModule").modal('hide');
                if (res.status) {
                    toastr.success("恭喜，操作已成功！");
                    window.location.reload();
                } else {
                    toastr.error("操作失败：" + res.message);
                }
            }
        });
    });
});

// 重新加载用户权限缓存
$(function() {
    $("#J_refresh_cache").click(function () {
    		$("#J_refresh_cache").addClass("disabled");
        	$.ajax({
				url:'${appPath}/admin/bwl/refreshCache.do',
				data:JSON.stringify({}),
				type:"POST",       					//  接口类型
				dataType:'json',      				//  参数类型
				contentType:'application/json',   	//  请求类型
				success:function(data) {
                    if (data.status) {
                        toastr.success("恭喜，缓存已加载！");
                    } else {
                        toastr.error(data.message);
                    }
                    $("#J_refresh_cache").removeClass("disabled");
				}
			});
    });
});

</script>

</body>
</html>
