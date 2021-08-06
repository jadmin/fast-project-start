<div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">添加角色</h4>
      </div>
      <div class="modal-body">
        <form class="form-horizontal" id="moduleForm" method="post" action="${appPath}/admin/role/saveRequest">
          <input type="hidden" id="id" name="id" value="">
               <div class="form-group">
            <label for="roleName" class="col-sm-2 control-label">角色名称</label>
            <div class="col-sm-9">
            	<input type="text" class="form-control" id="J_modal_roleName" name="roleName" autocomplete="off" placeholder="">
            </div>
          </div>
          <div class="form-group">
            <label for="roleCode" class="col-sm-2 control-label">角色代码</label>
            <div class="col-sm-9">
            	<input type="text" class="form-control" id="J_modal_roleCode" name="roleCode" autocomplete="off" placeholder="允许内容: 英文字母/数字/下划线">
            </div>
          </div>
          <div class="form-group">
            <label for="status" class="col-sm-2 control-label">状态</label>
            <div class="col-sm-9">
              <select id="J_modal_status" name="status" class="form-control input-sm">
	                <option value="">请选择</option>
	                <option value="0">已创建</option>
	                <option value="1">正常</option>
	                <option value="2">已停用</option>
              </select>
            </div>
          </div>
          <div class="form-group">
            <label for="description" class="col-sm-2 control-label">备注说明</label>
            <div class="col-sm-9">
            	<input type="text" class="form-control" id="J_modal_description" name="description" autocomplete="off" placeholder="">
            </div>
          </div>
          <div class="form-group">
            <label for="auth" class="col-sm-2 control-label">权限配置</label>
            <div class="col-sm-9">
            	<ul id="menuTree" class="ztree"></ul>
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-offset-8 col-sm-4">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="J_save" onclick="clickSaveBtn();">确定</button>
            </div>
          </div>
        </form>
      </div>
      
 <script>
 
var zTreeObj;
var zNodes = ${menuTreeJson!'[]'};

var setting = {
    view: {
        selectedMulti: false
    },
    check: {
        enable: true
    },
    data: {
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "parentId"
        }
    }, 
    edit: {
        enable: false
    }
};
 
 $(document).ready(function() {

	zTreeObj = $.fn.zTree.init($("#menuTree"), setting, zNodes);
    var nodes = zTreeObj.getNodes();
    if (nodes.length > 0) {
    	for(var i=0; i<nodes.length; i++) {
          	zTreeObj.expandNode(nodes[i], true, false, false); // 默认展开第一级节点
        }
    }
	
});

 </script>