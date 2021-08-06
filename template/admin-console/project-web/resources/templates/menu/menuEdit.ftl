<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>菜单管理</title>
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
        菜单管理
        <small></small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="${appPath}/console/xxx/list"><i class="fa fa-dashboard"></i> Home</a></li>
        <li><a href="#">系统管理</a></li>
        <li class="active">菜单管理</li>
      </ol>
    </section>

    <!-- Main content -->
    <section class="content">
      <div class="row">
        <div class="col-md-3">
	          
	          <div class="box box-primary">
	            <div class="box-header with-border">
	              <h3 class="box-title">菜单树</h3>
	            </div>
	            <!-- /.box-header -->
	            <div class="box-body">
	              <ul id="menuTree" class="ztree"></ul>
	            </div>
	            <!-- /.box-body -->
	          </div>
	          <!-- /.box -->
        </div>
        <!-- /.col -->
        
        <div class="col-md-9">
	          <div class="nav-tabs-custom">
	            <ul class="nav nav-tabs">
	              <li class="active"><a href="#settings" data-toggle="tab" aria-expanded="true">基本信息</a></li>
	            </ul>
	            <div class="tab-content">
	              <div class="tab-pane active" id="settings">
	                <form class="form-horizontal" id="J_formMenu">
	                
	                  <div class="form-group">
	                    <label for="inputParent" class="col-sm-2 control-label">父节点</label>
	                    <div class="col-sm-10">
	                      <input type="text" class="form-control" id="J_form_parentName" name="parentName" value="无" placeholder="" disabled>
	                      <input type="hidden" class="form-control" id="J_form_parentId" name="parentId" value="0">
	                      <input type="hidden" class="form-control" id="J_form_id" name="id" value="">
	                    </div>
	                  </div>
	                  
	                  <div class="form-group">
	                    <label for="inputName" class="col-sm-2 control-label">名称</label>
	                    <div class="col-sm-10">
	                      <input type="text" class="form-control" id="J_form_name" name="name" placeholder="菜单显示名">
	                    </div>
	                  </div>
	                  
	                  <div class="form-group">
	                    <label for="inputType" class="col-sm-2 control-label">类型</label>
	                    <div class="col-sm-10">
	                      <label class="radio-inline">
							<input type="radio" name="type" id="radio_1" value="1"> 菜单目录
						  </label>
						  <label class="radio-inline">
							<input type="radio" name="type" id="radio_2" value="2"> 模块页面
						  </label>
						  <label class="radio-inline">
							<input type="radio" name="type" id="radio_3" value="3"> 功能点
						  </label>
	                    </div>
	                  </div>
	                  
	                  <div class="form-group" id="J_formGroup_uri">
	                    <label for="input_uri" class="col-sm-2 control-label">URI路径</label>
	                    <div class="col-sm-10">
	                      <input type="hidden" class="form-control" id="J_form_uri" name="uri" value="">
	                      <select id="J_pageResource" name="_ATTR_pageResource" class="form-control select2" style="width:100%;">
	                      		<option></option>
			              	<#list pages as item>
                				<option value="${item.id}" data-uri="${item.uri}">${item.name} - ${item.uri}</option>
                			</#list>
	                      </select>
	                    </div>
	                  </div>
	                  
	                  <div class="form-group" id="J_formGroup_res">
	                    <label for="input_uri" class="col-sm-2 control-label">关联资源</label>
	                    <div class="col-sm-10">
	                      <select id="J_bindResources" name="_ATTR_bindResources" class="form-control select2" multiple="multiple" data-placeholder=" 关联绑定资源 " style="width:100%;">
			                	
	                      </select>
	                    </div>
	                  </div>
	                  
	                  <div class="form-group">
	                    <label for="input_iconCss" class="col-sm-2 control-label">图标/样式</label>
	                    <div class="col-sm-10">
	                      <input type="text" class="form-control icp icp-auto" id="J_form_iconCss" name="iconCss" placeholder="icon图标或css样式">
	                    </div>
	                  </div>
	                  
	                  <div class="form-group">
	                    <label for="input_sort" class="col-sm-2 control-label">排序值</label>
	                    <div class="col-sm-10">
	                      <input type="text" class="form-control" id="J_form_sort" name="sort" placeholder="排序值，作用于显示先后顺序" value="0">
	                    </div>
	                  </div>
	                  
	                  <div class="form-group">
	                    <label for="input_remark" class="col-sm-2 control-label">备注说明</label>
	                    <div class="col-sm-10">
	                      <textarea class="form-control" id="J_form_remark" name="remark" placeholder="备注说明"></textarea>
	                    </div>
	                  </div>
	                  
	                  <div class="form-group">
	                    <div class="col-sm-offset-2 col-sm-10">
	                      <button type="button" class="btn btn-danger" onclick="clickSaveBtn();">保存</button>
	                    </div>
	                  </div>
	                </form>
	              </div>
	              <!-- /.tab-pane -->
	            </div>
	            <!-- /.tab-content -->
	          </div>
	          <!-- /.nav-tabs-custom -->
        </div>
	    <!-- /.col -->
	    
      </div>
      <!-- /.row -->
      
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
<script src="${appPath}/components/select2/dist/js/select2.full.min.js"></script>
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

var zTreeObj;
var zNodes = ${menuTreeJson!'[]'};

var setting = {
    view: {
        selectedMulti: false, 
        addHoverDom: addHoverDom,
        removeHoverDom: removeHoverDom
    },
    check: {
        enable: false
    },
    data: {
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "parentId"
        }
    }, 
    edit: {
        enable: true,
        showRemoveBtn: true,
        showRenameBtn: false,
        drag: {
        	isCopy: false,
        	isMove: false
        }
    },
    callback: {
		onClick: zTreeOnClick,
		beforeRemove: zTreeBeforeRemove, 
		onNodeCreated: zTreeOnNodeCreated
	}
};


$(document).ready(function() {
	zTreeObj = $.fn.zTree.init($("#menuTree"), setting, zNodes);
	// zTreeObj.expandAll(true); // 展开所有节点

    var nodes = zTreeObj.getNodes();
    if (nodes.length > 0) {
    	for(var i=0; i<nodes.length; i++) {
          	zTreeObj.expandNode(nodes[i], true, false, false); // 默认展开第一级节点
        }
    }
	
	$('input[type=radio][name=type]').change(function() {
		if(this.value == 1) {
			$('#J_formGroup_uri').hide();
			$('#J_formGroup_res').hide();
		} else if (this.value == 2) {
			$('#J_formGroup_uri').show();
			$('#J_formGroup_res').hide();
		} else if (this.value == 3) {
			$('#J_formGroup_uri').hide();
			$('#J_formGroup_res').show();
		}
	});
	
	initResourcesSearchQuery();
	
	$('#J_pageResource').select2({
	    language: "zh-CN", 
	    placeholder: "请选择对应页面模块"
	});
	
	$('#J_pageResource').on("change", function(e) {
		var uri = $('#J_pageResource option:checked').attr('data-uri');
		$('#J_form_uri').val(uri);
	});
	
});


var newCount = 1;
function addHoverDom(treeId, treeNode) {
	if (treeNode.isNewAdd) { // 新增加节点，尚未持久化的不允许添加子节点
		return;
	}
    var sObj = $("#" + treeNode.tId + "_span");
    if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
    var addStr = "<span class='button add' id='addBtn_" + treeNode.tId + "' title='add' onfocus='this.blur();'></span>";
    sObj.after(addStr);
    var btn = $("#addBtn_"+treeNode.tId);
    if (btn) btn.bind("click", function() {
        var zTree = $.fn.zTree.getZTreeObj("menuTree");
        zTree.addNodes(treeNode, {id:(100 + newCount), pId:treeNode.id, name:"New子节点" + (newCount++), isNewAdd:true});
        return false;
    });
}

function removeHoverDom(treeId, treeNode) {
    $("#addBtn_"+treeNode.tId).unbind().remove();
}

function zTreeOnClick(event, treeId, treeNode) {
	
    $('#J_formMenu input[name=id]').val(treeNode.id);
    $('#J_formMenu input[name=parentId]').val(treeNode.parentId);
    
    var pNode = treeNode.getParentNode();
    if(null != pNode && pNode != 'undefined') {
    	$('#J_formMenu input[name=parentName]').val(pNode.name);
    } else {
    	$('#J_formMenu input[name=parentName]').val('无');
    	$('#J_formMenu input[name=parentId]').val(0);
    }
    
    $('#J_formMenu input[name=name]').val(treeNode.name);
    $('#J_formMenu input[name=sort]').val(treeNode.sort);
    $('#J_formMenu input[name=iconCss]').val(treeNode.iconCss);
    $('#J_formMenu input[name=remark]').val(treeNode.remark);
    
    if(treeNode.isNewAdd) {
    	var pId = treeNode.parentId;
    	$('#J_formMenu input[name=id]').val('');
    	$('#J_formMenu input[name=uri]').val('');
    	$("#J_bindResources").val(['']).trigger('change');
    	$('#radio_1').prop("checked", false);
    	$('#radio_2').prop("checked", false); 
    	$('#radio_3').prop("checked", false);
    	$.ajax({
	        type: "GET",
	        url: "${appPath}/admin/menu/queryMaxSort?parentId=" + pId,
	        dataType: "JSON",
	        success: function(res) {
	        	if (res.status) {
	    			var sort = typeof(res.entry)==='undefined' ? 0 : (res.entry + 1);
	    			$('#J_formMenu input[name=sort]').val(sort);
	        	}
	        }
		});
    } else {
    	$('#radio_' + treeNode.type).prop("checked", true); 
	    if (treeNode.type == 2) {
	    	$('#J_formMenu input[name=uri]').val(treeNode.uri);
	    	if (treeNode.attributesMap && treeNode.attributesMap.pageResource) {
	    		$("#J_pageResource").val(treeNode.attributesMap.pageResource).trigger('change');
	    	} else {
	    		$("#J_pageResource").val('').trigger('change');
	    	}
	    	$("#J_bindResources").val(['']).trigger('change');
	    	$('#J_formGroup_uri').show();
	    	$('#J_formGroup_res').hide();
	    } else if (treeNode.type == 1) {
	    	$('#J_formMenu input[name=uri]').val('');
	    	$("#J_pageResource").val('').trigger('change');
	    	$("#J_bindResources").val(['']).trigger('change');
	    	$('#J_formGroup_uri').hide();
	    	$('#J_formGroup_res').hide();
	    } else if (treeNode.type == 3) {
	    	$('#J_formMenu input[name=uri]').val('');
	    	$("#J_pageResource").val('').trigger('change');
	    	$('#J_formGroup_uri').hide();
	    	$('#J_formGroup_res').show();
	    	
	    	if (treeNode.attributesMap && treeNode.attributesMap.bindResources) {
	    		loadBindingResources(treeNode.attributesMap.bindResources);
	    	} else {
	    		loadBindingResources('');
	    	}
	    }
	    
    }
    
}

function zTreeOnNodeCreated(event, treeId, treeNode) {
	if(!treeNode.isNewAdd) {
		return;
	}
    zTreeObj.selectNode(treeNode);
    zTreeObj.setting.callback.onClick('', 'menuTree', treeNode); // 手动触发onClick事件
}

function zTreeBeforeRemove(treeId, treeNode) {
	var nodes = treeNode.children;
	if (null != nodes && nodes.length > 0) {
		toastr.error("父节点不允许直接删除");
		return false;
	}
	if (treeNode.isNewAdd) {
		return true;
	}
	var id = treeNode.id;
	var flag = false;
	confirm(true, "确定要删除此节点吗?", function() {
		$.ajax({
	        type: "POST",
	        url: "${appPath}/admin/menu/delete?id=" + id,
	        dataType: "JSON",
	        success: function(res) {
	        	if (res.status) {
	    			toastr.success("恭喜，操作已成功！");
	    			window.location.reload();
	        	} else {
	        		toastr.error(res.message);
	        		flag = false;
	        	}
	        }
		});
	});
	
	return flag;
}

function initResourcesSearchQuery() {
    
	$('#J_bindResources').select2({
		language: "zh-CN",
		ajax: {
        	url: "${appPath}/admin/resource/queryList",
        	dataType: 'json',
        	delay: 250,
        	data: function(params) {
            	return {
                	nameLike: params.term, // search term
                	pageNo: (isBlank(params.page) ? 1 : params.page),
                	pageSize: 20
            	};
        	},
        	processResults: function(data, params) {
	            return {
	                results: data.rows
	            };
        	},
        	cache: false
		},
		escapeMarkup: function(markup) {
        	return markup;
    	},
    	minimumInputLength: 1,
    	templateResult: formatTask,
    	templateSelection: formatTaskSelection
	});
	
}

function loadBindingResources(ids) { // [111, 112] 或 单值如："111"
    
    var idArrayStr = '';
	if(Array.isArray(ids) && ids.length > 0) {
		idArrayStr = ids.join(",");
	} else if ( !isBlank(ids) ) {
		idArrayStr = ids;
	}
	if ( isBlank(idArrayStr) ) {
		$("#J_bindResources").val([]).trigger('change');
	} else {
        $.ajax("${appPath}/admin/resource/queryList?idArray=" + idArrayStr, {
            dataType: "json"
        }).done(function (data) { 
        	var dataArray = new Array();
        	for(var i = 0; i < data.rows.length; i++) {
		        dataArray.push({
		        	id: data.rows[i].id,
		        	text: data.rows[i].name
		        });
		    }
		    
		    $('#J_bindResources').select2({
				language: "zh-CN",
				data: dataArray,
				ajax: {
		        	url: "${appPath}/admin/resource/queryList",
		        	dataType: 'json',
		        	delay: 250,
		        	data: function(params) {
		            	return {
		                	nameLike: params.term, // search term
		                	pageNo: (isBlank(params.page) ? 1 : params.page),
		                	pageSize: 20
		            	};
		        	},
		        	processResults: function(data, params) {
			            return {
			                results: data.rows
			            };
		        	},
		        	cache: false
				},
				escapeMarkup: function(markup) {
		        	return markup;
		    	},
		    	minimumInputLength: 1,
		    	templateResult: formatTask,
		    	templateSelection: formatTaskSelection
			});
			
			$("#J_bindResources").val(idArrayStr.split(",")).trigger('change');
        });
    }
	
}

function formatTask(repo) {
	if (repo.loading) { 
	  	return repo.name || repo.text;
	}
	var markup = "<div class='clearfix'>" + (repo.name || repo.text) + "</div>";
	return markup;
}

function formatTaskSelection(repo) {
	return repo.name || repo.text;
}

function clickSaveBtn() {
    
	var name = $('#J_formMenu input[name=name]').val();
	var type = $('#J_formMenu input[name=type]:checked').val();
	
	if ( isBlank(name) ) {
		toastr.error("名称不能为空");
        return;
	}
	if ( isBlank(type) ) {
		toastr.error("用户类型不能为空");
        return;
	}
    
    $.ajax({
        url: "${appPath}/admin/menu/saveRequest",
        data: $("#J_formMenu").serialize(),
        type: "POST",
        success: function (res) {
            if (res.status) {
                toastr.success("恭喜，操作已成功！");
                window.location.reload();
            } else {
                toastr.error(res.message);
            }
        }
    });
}



</script>

</body>
</html>
