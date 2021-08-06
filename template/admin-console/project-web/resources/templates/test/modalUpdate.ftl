<div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">编辑XXX</h4>
      </div>
      <div class="modal-body">
        <form class="form-horizontal" id="moduleForm" method="post" action="${appPath}/admin/test/saveRequest">
          <input type="hidden" id="id" name="id" value="${id!''}">
               <div class="form-group">
            <label for="username" class="col-sm-2 control-label">用户名</label>
            <div class="col-sm-9">
            	<input type="text" class="form-control" id="J_modal_username" name="username" autocomplete="off" value="${entity.username!''}">
            </div>
          </div>
          <div class="form-group">
            <label for="userType" class="col-sm-2 control-label">用户类型</label>
            <div class="col-sm-9">
              <select id="J_modal_userType" name="userType" class="form-control input-sm">
	                <option value="1" <#if "1"=="${entity.userType}">selected</#if>>普通</option>
	                <option value="2" <#if "2"=="${entity.userType}">selected</#if>>高级</option>
	                <option value="3" <#if "3"=="${entity.userType}">selected</#if>>VIP</option>
	                <option value="9" <#if "9"=="${entity.userType}">selected</#if>>超管</option>
              </select>
            </div>
          </div>
          <div class="form-group">
            <label for="sex" class="col-sm-2 control-label">性别</label>
            <div class="col-sm-9">
              <select id="J_modal_sex" name="sex" class="form-control input-sm">
	                <option value="0" <#if "0"=="${entity.sex}">selected</#if>>未知</option>
	                <option value="1" <#if "1"=="${entity.sex}">selected</#if>>男</option>
	                <option value="2" <#if "2"=="${entity.sex}">selected</#if>>女</option>
              </select>
            </div>
          </div>
          <div class="form-group">
            <label for="email" class="col-sm-2 control-label">电子邮件</label>
            <div class="col-sm-9">
            	<input type="text" class="form-control" id="J_modal_email" name="email" autocomplete="off" value="${entity.email!''}">
            </div>
          </div>
          <div class="form-group">
            <label for="attributes" class="col-sm-2 control-label">attributes</label>
            <div class="col-sm-9">
            	<textarea type="text" class="form-control" id="J_modal_attributes" name="attributes" rows="6">${Utils.escapeHTML(entity.attributes)}</textarea>
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
