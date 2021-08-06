<div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="newModalLabel">添加新用户</h4>
      </div>
      <div class="modal-body">
        <form class="form-horizontal" id="moduleForm" method="post" action="${appPath}/admin/usermanage/saveRequest">
          
          <div class="form-group">
            <label for="username" class="col-sm-2 control-label">用户名</label>
            <div class="col-sm-9">
            	<input type="text" class="form-control" id="username" name="username" autocomplete="off" placeholder="">
            </div>
          </div>
          
          <div class="form-group">
            <label for="password" class="col-sm-2 control-label">密码</label>
            <div class="col-sm-9">
            	<input type="password" class="form-control" id="password" name="password" autocomplete="off" placeholder="">
            </div>
          </div>
          
          <div class="form-group">
            <label for="name" class="col-sm-2 control-label">姓名</label>
            <div class="col-sm-9">
            	<input type="text" class="form-control" id="name" name="name" autocomplete="off" placeholder="">
            </div>
          </div>
          
          <div class="form-group">
            <label for="mobile" class="col-sm-2 control-label">手机号</label>
            <div class="col-sm-9">
            	<input type="text" class="form-control" id="mobile" name="mobile" autocomplete="off" placeholder="">
            </div>
          </div>
          
          <div class="form-group">
            <label for="email" class="col-sm-2 control-label">邮件地址</label>
            <div class="col-sm-9">
            	<input type="text" class="form-control" id="email" name="email" autocomplete="off" placeholder="">
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

	$('#J_userRole').select2({
	    language: "zh-CN",
        placeholder: "请选择用户关联的角色"
    });
	
});

</script>