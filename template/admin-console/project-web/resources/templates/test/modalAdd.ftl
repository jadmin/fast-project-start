<div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">添加XXX</h4>
      </div>
      <div class="modal-body">
        <form class="form-horizontal" id="moduleForm" method="post" action="${appPath}/admin/test/saveRequest">
          <input type="hidden" id="id" name="id" value="">
               <div class="form-group">
            <label for="username" class="col-sm-2 control-label">用户名</label>
            <div class="col-sm-9">
            	<input type="text" class="form-control" id="J_modal_username" name="username" autocomplete="off" placeholder="">
            </div>
          </div>
          <div class="form-group">
            <label for="userType" class="col-sm-2 control-label">用户类型</label>
            <div class="col-sm-9">
              <select id="J_modal_userType" name="userType" class="form-control input-sm">
	                <option value="">请选择</option>
	                <option value="1">普通</option>
	                <option value="2">高级</option>
	                <option value="3">VIP</option>
	                <option value="9">超管</option>
              </select>
            </div>
          </div>
          <div class="form-group">
            <label for="sex" class="col-sm-2 control-label">性别</label>
            <div class="col-sm-9">
              <select id="J_modal_sex" name="sex" class="form-control input-sm">
	                <option value="">请选择</option>
	                <option value="0">未知</option>
	                <option value="1">男</option>
	                <option value="2">女</option>
              </select>
            </div>
          </div>
          <div class="form-group">
            <label for="email" class="col-sm-2 control-label">电子邮件</label>
            <div class="col-sm-9">
            	<input type="text" class="form-control" id="J_modal_email" name="email" autocomplete="off" placeholder="">
            </div>
          </div>
          <div class="form-group">
            <label for="attributes" class="col-sm-2 control-label">attributes</label>
            <div class="col-sm-9">
            	<textarea type="text" class="form-control" id="J_modal_attributes" name="attributes" rows="6" placeholder=""></textarea>
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