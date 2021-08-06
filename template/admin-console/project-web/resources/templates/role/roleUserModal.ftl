<div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">关联角色用户</h4>
      </div>
      <div class="modal-body">
        <form class="form-horizontal" id="moduleForm" method="post" action="${appPath}/admin/role/saveRoleUserRequest">
          <input type="hidden" id="id" name="id" value="">
          <input type="hidden" id="roleId" name="roleId" value="${role.id}">
          <input type="hidden" id="J_form_userId" name="userId" value="">
          <div class="form-group modal-prompt hidden">
            <label for="modal-prompt-placeholder" class="col-sm-1 control-label"></label>
			<code id="J_modalPromptMsg"></code>
          </div>
          <div class="form-group">
            <label for="roleName" class="col-sm-2 control-label">关联角色</label>
            <div class="col-sm-9">
            	<input type="text" class="form-control" id="J_modal_roleName" value="${role.roleName}" readonly>
            </div>
          </div>
          <div class="form-group">
            <label for="roleCode" class="col-sm-2 control-label">用户信息</label>
            <div class="col-sm-6">
            	<input type="text" class="form-control" id="J_modal_userinfo" autocomplete="off" placeholder="用户ID/用户名/手机号/Email">
            </div>
            <div class="col-sm-3">
            	<button type="button" class="btn btn-primary" id="J_queryUser" onclick="clickQueryUser();">查询</button>
            </div>
          </div>
          <div class="form-group">
            <label for="code" class="col-sm-2 control-label">有效期</label>
            <div class="col-sm-9">
              <label class="radio-inline">
				<input type="radio" name="expireType" id="radio_9" value="9" checked> 永久
			  </label>
              <label class="radio-inline">
				<input type="radio" name="expireType" id="radio_1" value="1"> 1天
			  </label>
			  <label class="radio-inline">
				<input type="radio" name="expireType" id="radio_2" value="2"> 3天
			  </label>
			  <label class="radio-inline">
				<input type="radio" name="expireType" id="radio_3" value="3"> 15天
			  </label>
			  <label class="radio-inline">
				<input type="radio" name="expireType" id="radio_4" value="4"> 3个月
			  </label>
			  <label class="radio-inline">
				<input type="radio" name="expireType" id="radio_5" value="5"> 半年
			  </label>
			  <label class="radio-inline">
				<input type="radio" name="expireType" id="radio_6" value="6"> 1年
			  </label>
			  <label class="radio-inline">
				<input type="radio" name="expireType" id="radio_7" value="7"> 3年
			  </label>
			  <label class="radio-inline">
				<input type="radio" name="expireType" id="radio_8" value="8"> 自定义时长 <input type="text" id="expiredTime" name="expiredTime" placeholder="">
			  </label>
            </div>
          </div>
          <div class="form-group">
            <label for="name" class="col-sm-2 control-label">用户姓名</label>
            <div class="col-sm-9">
            	<input type="text" class="form-control" id="J_modal_name" value="" readonly>
            </div>
          </div>
          <div class="form-group">
            <label for="username" class="col-sm-2 control-label">用户名</label>
            <div class="col-sm-9">
            	<input type="text" class="form-control" id="J_modal_username" value="" readonly>
            </div>
          </div>
          <div class="form-group">
            <label for="mobile" class="col-sm-2 control-label">手机号</label>
            <div class="col-sm-9">
            	<input type="text" class="form-control" id="J_modal_mobile" value="" readonly>
            </div>
          </div>
          <div class="form-group">
            <label for="email" class="col-sm-2 control-label">Email</label>
            <div class="col-sm-9">
            	<input type="text" class="form-control" id="J_modal_email" value="" readonly>
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

$(document).ready(function() {

	$("#expiredTime").datetimepicker({
		language: 'zh-CN',
		format: "yyyy-mm-dd hh:ii:ss",
		todayBtn: true,
		autoclose: true
	});
	
});
	
</script>  
