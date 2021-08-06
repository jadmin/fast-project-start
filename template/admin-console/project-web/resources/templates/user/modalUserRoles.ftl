<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
    <h4 class="modal-title" id="newModalLabel">给用户分配角色</h4>
</div>
<div class="modal-body">
    <form id="J_role_modalForm" class="form-inline">
    	<input type="hidden" class="form-control" id="J_modal_userId" name="userId" value="${entity.id}">
	    <div class="form-group">
	        <label for="username">用户名</label>
	        <input type="text" class="form-control" id="J_modal_username" name="username" value="${entity.username}" readonly>
	    </div>
	    <div class="form-group">
	        <label for="role">角色</label>
	        <select id="J_userRole" name="roleId" class="form-control input-sm">
	        	<option value="">请选择要关联的角色</option>
	        <#list roleList as item>
	            <option value="${item.id}">${item.roleName}</option>
	        </#list>
            </select>
	    </div>
	    <div class="form-group">
	        <label for="expiredTime">有效期</label>
	        <input type="text" class="form-control" id="expiredTime" name="expiredTime" placeholder="拥有角色失效时间">
	    </div>
	    <button type="button" class="btn btn-primary" id="J_addRole">确定</button>
	</form>
	
	<h4></h4>
	
	<table id="J_table_userRoles"></table>
</div>
      
<script>
 
$(document).ready(function() {

	var oTableInit = new TableInit();
    oTableInit.Init();

	$("#expiredTime").datetimepicker({
		language: 'zh-CN',
		format: "yyyy-mm-dd hh:ii:ss",
		todayBtn: true,
		autoclose: true
	});
	
	$("#J_addRole").click(function () {
	
		var userId = $('#J_role_modalForm input[name=userId]').val();
		var roleId = $('#J_role_modalForm select[name=roleId] option:selected').val();
		var expiredTime = $('#J_role_modalForm input[name=expiredTime]').val();
		
		if ( isBlank(userId) || userId == '0' ) {
			toastr.error("选择的用户ID数据异常");
	        return;
		}
		if ( isBlank(roleId) || roleId == '0' ) {
			toastr.error("选择要关联的角色");
	        return;
		}
		
		if( isBlank(expiredTime) ) {
			toastr.error("请填写角色绑定有效期时间");
	        return;
		}
        
        let post = {};
        post.userId = userId;
		post.roleId = roleId;
		post.expiredTime = expiredTime;
		
	    $.ajax({
	        url: "${appPath}/admin/role/saveRoleUserRequest",
	        data: JSON.stringify(post),
	        type: "POST",
	        contentType: 'application/json; charset=UTF-8',
	        success: function (res) {
	            if (res.status) {
	                toastr.success("恭喜，操作已成功！");
	                refreshRoles();
	            } else {
	                toastr.error("操作失败：" + res.message);
	            }
	        }
	    });
        
    });
	
});


var TableInit = function () {
    var oTableInit = new Object();
    // 初始化Table
    oTableInit.Init = function () {
        $('#J_table_userRoles').bootstrapTable({
            url: '${appPath}/admin/role/userRoleTableList',         //请求后台的URL（*）
            method: 'GET',                      //请求方式（*）
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            sortable: false,                     //是否启用排序
            queryParams: oTableInit.queryParams, //传递参数（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber: 1,                       //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            pageList: [10, 25, 50],             //可供选择的每页的行数（*）
            contentType: "application/x-www-form-urlencoded",
            clickToSelect: true,                //是否启用点击选中行
            uniqueId: "no",                     //每一行的唯一标识，一般为主键列
            showToggle: false,                    //是否显示详细视图和列表视图的切换按钮
            cardView: false,                    //是否显示详细视图
            detailView: false,                   //是否显示父子表
            showColumns: false,                  //工具栏是否显示所有的列
            columns: [
            	{
		            field: 'id',
		            title: 'ID'
		        }, {
		            field: 'roleCode',
		            title: '角色代码'
		        }, {
		            field: 'roleName',
		            title: '角色名称'
		        }, {
		        	field: 'expiredTime',
		            title: '失效时间'
		        },
	            {
	                field: 'operate',
	                title: '操作',
	                formatter: operateFormatter //自定义方法，添加操作按钮
	            },
            ],
        });

    };

    oTableInit.queryParams = function (params) {
        var temp = { 
            pageSize: params.limit,
            pageNo: (params.offset / params.limit) + 1, 
            userId: $('#J_role_modalForm input[name=userId]').val()
        };
        return temp;
    };
    return oTableInit;
};


function operateFormatter(value, row, index) {
	return '<a href="javascript:void(0)" onClick="deleteBtn(' + row.id + ');">删除</a>';
}

function refreshRoles() {
    $('#J_table_userRoles').bootstrapTable('removeAll');
    $('#J_table_userRoles').bootstrapTable('refresh');
}

function deleteBtn(id) {
    confirm(false, "确定要删除用户对应此角色绑定关系吗?", function() {
	
		$.ajax({
	        type: "POST",
	        url: "${appPath}/admin/role/delRoleUser?id=" + id,
	        dataType: "JSON",
	        success: function(res) {
	        	if (res.status) {
	        		refreshRoles();
	        	} else {
	        		toastr.error(res.message);
	        	}
	        }
  		});
	
	});
}

</script>