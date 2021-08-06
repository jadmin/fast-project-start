<div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="updateModalLabel">修改用户</h4>
    </div>
    <div class="modal-body">
        <form class="form-horizontal" id="moduleForm" method="post">
			<input type="hidden" id="id" name="id" value="${id!''}">
			<input type="hidden" id="J_dbUserRoles" value="${userRoles!''}">
            <div class="form-group">
                <label for="username" class="col-sm-2 control-label">用户名</label>
                <div class="col-sm-9">
                    <input type="text" class="form-control" name="username" autocomplete="off" placeholder="" value="${entity.username!''}" readonly>
                </div>
            </div>

            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">姓名</label>
                <div class="col-sm-9">
                    <input type="text" class="form-control" name="name" autocomplete="off" placeholder="" value="${entity.name!''}">
                </div>
            </div>

            <div class="form-group">
                <label for="mobile" class="col-sm-2 control-label">手机号</label>
                <div class="col-sm-9">
                    <input type="text" class="form-control" name="mobile" autocomplete="off" placeholder="" value="${entity.mobile!''}">
                </div>
            </div>

            <div class="form-group">
                <label for="email" class="col-sm-2 control-label">邮件地址</label>
                <div class="col-sm-9">
                    <input type="text" class="form-control" name="email" autocomplete="off" placeholder="" value="${entity.email!''}">
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

	var roleIdArr = $('#J_dbUserRoles').val();
	if ( isBlank(roleIdArr) ) {
		$('#J_userRole').select2({
	    	language: "zh-CN",
        	placeholder: "请选择用户关联的角色"
    	});
	} else {
		$.ajax("${appPath}/admin/role/findRoles?roleIds=" + roleIdArr, {
            dataType: "json"
        }).done(function (data) { 
        	var dataArray = new Array();
        	for(var i = 0; i < data.entry.length; i++) {
		        dataArray.push({
		        	id: data.entry[i].id,
		        	text: data.entry[i].roleName
		        });
		    }
		    $('#J_userRole').select2({
				language: "zh-CN",
				placeholder: "请选择用户关联的角色", 
				data: dataArray
			});
			$("#J_userRole").val(roleIdArr.split(",")).trigger('change');
        });
	}
	
	
});

</script>