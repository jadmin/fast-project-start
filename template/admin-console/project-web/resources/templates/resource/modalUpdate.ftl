<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
    <h4 class="modal-title" id="myModalLabel">编辑资源</h4>
  </div>
  <div class="modal-body">
    <form class="form-horizontal" id="moduleForm" method="post" action="${appPath}/admin/resource/saveRequest">
  <input type="hidden" id="id" name="id" value="${id!''}">
  <div class="form-group">
    <label for="name" class="col-sm-2 control-label">资源名称</label>
    <div class="col-sm-9">
    	<input type="text" class="form-control" id="J_modal_name" name="name" autocomplete="off" value="${entity.name!''}">
    </div>
  </div>
  <div class="form-group">
    <label for="code" class="col-sm-2 control-label">资源代码</label>
    <div class="col-sm-9">
    	<input type="text" class="form-control" id="J_modal_code" name="code" autocomplete="off" value="${entity.code!''}">
    </div>
  </div>
  <div class="form-group">
    <label for="type" class="col-sm-2 control-label">资源类型</label>
    <div class="col-sm-9">
      <label class="radio-inline">
		<input type="radio" name="type" id="radio_1" value="1" <#if "1"=="${entity.type}">checked</#if>> 页面
	  </label>
	  <label class="radio-inline">
		<input type="radio" name="type" id="radio_2" value="2" <#if "2"=="${entity.type}">checked</#if>> ACTION
	  </label>
	  <label class="radio-inline">
		<input type="radio" name="type" id="radio_3" value="3" <#if "3"=="${entity.type}">checked</#if>> 其他
	  </label>
    </div>
  </div>
  <div class="form-group">
    <label for="uri" class="col-sm-2 control-label">访问路径</label>
    <div class="col-sm-9">
    	<input type="text" class="form-control" id="J_modal_uri" name="uri" autocomplete="off" value="${entity.uri!''}">
    </div>
  </div>
  <div class="form-group">
    <label for="description" class="col-sm-2 control-label">备注说明</label>
    <div class="col-sm-9">
    	<textarea type="text" class="form-control" id="J_modal_description" name="description" rows="6">${Utils.escapeHTML(entity.description)}</textarea>
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
