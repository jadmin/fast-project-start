<script>

$('.m_logout').click(function() {  
	confirm(false, "您确定要退出系统吗?", function() {
	
		$.ajax({
            url: "${appPath}/admin/user/logout",
            type: "GET",
            success: function (res) {
                if (res.status) {
                    toastr.success("登出成功！");
                    var url = '${appPath}/console/login';
                    location.href = url;  
                } else {
                    toastr.error(res.message);
                }
            }
        });
	
	});
});

var times = 0;
setInterval(function() { 
	$.ajax({
        url: "${appPath}/admin/user/pingStatus",
        data: {},
        type: "GET",
        success: function (res) {
            if (res.status) {
            	if(null != res.entry && false === res.entry.key) {
            		myAlert(res.entry.value, function() {
            			logout(); 
            		});
            		times++;
            		if(times > 10) {
                    	logout();
            		}
            	}
            } else {
            	myAlert(res.message, function() {
            		logout(); 
            	});
        		times++;
        		if(times > 10) {
                	logout();
        		}
            }
        }
	});
}, 6000);


$('.sidebar-menu li:not(.treeview) > a').on('click', function(){
    var $parent = $(this).parent().addClass('active');
    $parent.siblings('.treeview.active').find('> a').trigger('click');
    $parent.siblings().removeClass('active').find('li').removeClass('active');
});
 
$(window).on('load', function(){
    $('.sidebar-menu a').each(function(){
        var cur = window.location.href;
        var url = this.href;
        if(cur.match(url)){
            $(this).parent().addClass('active')
                    .closest('.treeview-menu').addClass('.menu-open')
                    .closest('.treeview').addClass('active');
        }
    });
});

// 菜单搜索
$(function () {
    $('#sidebar-form').on('submit', function (e) {
        e.preventDefault();
    });

    $('.sidebar-menu li.active').data('lte.pushmenu.active', true);

    $('#search-input').on('keyup', function () {
        var term = $('#search-input').val().trim();

        if (term.length === 0) {
            $('.sidebar-menu li').each(function () {
                $(this).show(0);
                $(this).removeClass('active');
                if ($(this).data('lte.pushmenu.active')) {
                    $(this).addClass('active');
                }
            });
            return;
        }

        $('.sidebar-menu li').each(function () {
            if ($(this).text().toLowerCase().indexOf(term.toLowerCase()) === -1) {
                $(this).hide(0);
                $(this).removeClass('pushmenu-search-found', false);

                if ($(this).is('.treeview')) {
                    $(this).removeClass('active');
                }
            } else {
                $(this).show(0);
                $(this).addClass('pushmenu-search-found');

                if ($(this).is('.treeview')) {
                    $(this).addClass('active');
                }

                var parent = $(this).parents('li').first();
                if (parent.is('.treeview')) {
                    parent.show(0);
                }
            }

            if ($(this).is('.header')) {
                $(this).show();
            }
        });

        $('.sidebar-menu li.pushmenu-search-found.treeview').each(function () {
            $(this).find('.pushmenu-search-found').show(0);
        });
    });
            
});


function logout() {

	$.ajax({
        url: "${appPath}/admin/user/clean",
        type: "GET",
        success: function (res) {
        	var url = '${appPath}/console/login';
            if (res.status) {
                location.href = url;  
            }
            location.href = url; 
        }
     });

}


/**
 * 重写确认框 fun:函数对象
 */
function confirm(isDelete, msg, funOk, funNo) {
    if ($("#myConfirm").length > 0) {
        $("#myConfirm").remove();
    }
    msg = msg || '您确定要执行此操作吗？';
    var icon = isDelete ? "glyphicon-trash" : "glyphicon-exclamation-sign";
    var html = "<div class='modal fade' id='myConfirm'>"
            + "<div class='modal-backdrop in' style='opacity:0; '></div>"
            + "<div class='modal-dialog' style='z-index:2901; margin-top:60px; width:400px; '>"
            + "<div class='modal-content'>"
            + "<div class='modal-header' style='font-size:16px;'>"
            + "<span class='glyphicon " + icon + "'>&nbsp;</span>系统提示<button type='button' class='close' data-dismiss='modal'>"
            + "<span style='font-size:20px; ' class='glyphicon glyphicon-remove' id='J_crossBtn'></span></button></div>"
            + "<div class='modal-body text-center' id='myConfirmContent' style='font-size:18px; '>"
            + msg
            + "</div>"
            + "<div class='modal-footer'>"
            + "<button class='btn btn-info' data-dismiss='modal' id='confirmCancel'>取消</button>"
            + "<button class='btn btn-danger' id='confirmOk'>确定</button>"
            + "</div>" + "</div></div></div>";
    $("body").append(html);

    $("#myConfirm").modal("show");

	var doConfirmOk = false;
	$("#confirmOk").on("click", function() { // 点击了确定按钮
    	$("#myConfirm").modal("hide");
    	if(null != funOk && "" != funOk && "undefined" != typeof(funOk)) {
    		funOk(); // 执行"确定"事件函数
    		doConfirmOk = true;
    	}
	});
    	
	$("#confirmCancel").on("click", function() { // 点击了取消按钮
    	$("#myConfirm").modal("hide");
    	if(null != funNo && "" != funNo && "undefined" != typeof(funNo)) {
    		funNo(); // 执行"确定"事件函数
    		doConfirmOk = false;
    	}
	});
    	
	$("#J_crossBtn").on("click", function() { // 点击了X按钮
    	$("#myConfirm").modal("hide");
    	if(null != funNo && "" != funNo && "undefined" != typeof(funNo)) {
    		funNo(); // 执行"确定"事件函数
    		doConfirmOk = false;
    	}
	});
    	
	
	return doConfirmOk;
}

/**
 * 仅有确认动作的Alert弹窗
 */
function myAlert(msg, funOk) {
    if ($("#myAlert").length > 0) {
        $("#myAlert").remove();
    }
    msg = msg || '系统提示消息';
    var icon = "glyphicon-exclamation-sign";
    var html = "<div class='modal fade' id='myAlert'>"
            + "<div class='modal-backdrop in' style='opacity:0; '></div>"
            + "<div class='modal-dialog' style='z-index:2901; margin-top:60px; width:500px;'>"
            + "<div class='modal-content'>"
            + "<div class='modal-header' style='font-size:16px;'>"
            + "  <span class='glyphicon " + icon + "'>&nbsp;</span>系统提示"
            + "</div>"
            + "<div class='modal-body text-center' id='myConfirmContent' style='font-size:18px;'>"
            + msg
            + "</div>"
            + "<div class='modal-footer'>"
            + "<button class='btn btn-danger' id='confirmOk'>确定</button>"
            + "</div>" + "</div></div></div>";
    $("body").append(html);

    $("#myAlert").modal("show");

	var doConfirmOk = false;
	$("#confirmOk").on("click", function() { // 点击了确定按钮
    	$("#myAlert").modal("hide");
    	if(null != funOk && "" != funOk && "undefined" != typeof(funOk)) {
    		funOk(); // 执行"确定"事件函数
    		doConfirmOk = true;
    	}
	});
	
	return doConfirmOk;
}

</script>