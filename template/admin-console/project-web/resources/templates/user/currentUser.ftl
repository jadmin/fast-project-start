<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>我的详情</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.7 -->
    <link rel="stylesheet" href="${appPath}/components/bootstrap/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="${appPath}/components/bootstrap-table/bootstrap-table.min.css">
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

    <link rel="stylesheet" href="${appPath}/css/jquery.fileupload-ui.css">
    <link rel="stylesheet" href="${appPath}/css/jquery.fileupload.css">

    <link rel="stylesheet" href="${appPath}/css/toastr.min.css">
    
    <link rel="shortcut icon" href="${appPath}/images/icon-48x48.png">

    <#-- 上传 -->
    <link rel="stylesheet" href="https://at.alicdn.com/t/font_1805932_ysrcp4y0uy9.css">
    <link rel="stylesheet" href="${appPath}/css/upload/uploadImg.css">
    <link rel="stylesheet" href="${appPath}/css/custom/custom.css">
    <link rel="stylesheet" href="${appPath}/css/docs/highlight.css">
    <link rel="stylesheet" href="${appPath}/css/docs/main.css">
    <link rel="stylesheet" href="${appPath}/css/bootstrap-switch.css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

    <!-- jQuery 3 -->
    <script src="${appPath}/components/jquery/dist/jquery.min.js"></script>
    <script src="${appPath}/js/toastr.min.js"></script>
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
                我的详情
                <small></small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="${appPath}/console/index"><i class="fa fa-dashboard"></i> Home</a></li>
                <li><a href="#">用户管理</a></li>
                <li class="active">我的详情</li>
            </ol>
            <br/>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-xs-12">
                    <div class="nav-tabs-custom">
                        <ul class="nav nav-tabs">
                            <li class="active"><a href="#personalInformation" data-toggle="tab">个人信息</a></li>
                            <li><a href="#updatePassword" data-toggle="tab">修改密码</a></li>
                        </ul>
                        <div class="col-xs-12 tab-content">
                            <div class="col-xs-6 active tab-pane" id="personalInformation">
                                <form class="form-horizontal" id="currentUserForm" method="post"
                                      action="${appPath}/admin/skills/save">
                                    <div class="form-group">
                                        <label for="name" class="col-sm-2 control-label">用户名</label>
                                        <div class="col-sm-9">
                                            <input type="text" class="form-control" id="username" name="username"
                                                   autocomplete="off"
                                                   placeholder="" readonly>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="name" class="col-sm-2 control-label">姓名</label>
                                        <div class="col-sm-9">
                                            <input type="text" class="form-control" id="name" name="name"
                                                   autocomplete="off"
                                                   placeholder="" readonly>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="name" class="col-sm-2 control-label">邮件</label>
                                        <div class="col-sm-9">
                                            <input type="text" class="form-control" id="email" name="email"
                                                   autocomplete="off"
                                                   placeholder="" readonly>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="name" class="col-sm-2 control-label">手机号</label>
                                        <div class="col-sm-9">
                                            <input type="text" class="form-control" id="mobile" name="mobile"
                                                   autocomplete="off"
                                                   placeholder="" readonly>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="name" class="col-sm-2 control-label">二次身份认证</label>
                                        <div class="col-sm-9">
                                            <input id="switch-state" name="openSecondVerify" type="checkbox" checked
                                                   data-on-text="开启" data-off-text="关闭">
                                        </div>
                                    </div>
                                    <div class="form-group" id="secondVerifyType">
                                        <label for="type" class="col-sm-2 control-label">认证类型</label>
                                        <div class="col-sm-9">
                                            <select class="form-control input-sm" id="J_secondVerifyType" name="secondVerifyType" onchange="doChangeType()">
                                                <option value="2">谷歌身份认证</option>
                                                <option value="1">短信验证码</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group" id="authKey">
                                        <label for="name" class="col-sm-2 control-label">密钥</label>
                                        <div class="col-sm-9">
                                            <input
                                                    name="authKey"
                                                    data-toggle="password"
                                                    class="form-control"
                                                    type="password"
                                                    placeholder="请输入密钥"
                                                    readonly>
                                        </div>
                                    </div>
                                    <div class="form-group" id="qrcodeUrl">
                                        <label for="name" class="col-sm-2 control-label">二维码</label>
                                        <div class="col-sm-9">
                                            <img name="qrcodeUrl" src="" alt="使用Google身份验证器扫码添加">
                                            <br/>
                                            <br/>
                                            <p>
                                                谷歌身份认证则需要安装Google Authenticator：
                                                <a href="javascript:"
                                                   onClick="window.open('https://google-authenticator.en.softonic.com/android/download')">IOS下载入口</a>，
                                                <a href="javascript:"
                                                   onClick="window.open('http://www.downyi.com/downinfo/173568.html')">安卓下载入口</a>
                                            </p>
                                        </div>

                                    </div>
                                    <div class="form-group">
                                        <div class="col-sm-offset-8 col-sm-4">
                                            <button type="button" class="btn btn-primary" style="float: right;"
                                                    id="J_updateModel">更新
                                            </button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <!-- /.tab-pane -->
                            <div class="col-xs-6 tab-pane" id="updatePassword">
                                <form class="col-xs-12" id="updatePasswordForm">
                                    <div class="form-group has-feedback">
                                        <label for="name" class="col-sm-3 control-label">密码</label>
                                        <input
                                                class="form-control"
                                                name="oldPassword"
                                                data-toggle="password"
                                                type="password"
                                                maxlength="20"
                                                placeholder=""
                                                readonly
                                                onfocus="this.removeAttribute('readonly');">
                                    </div>
                                    <div class="form-group has-feedback">
                                        <label for="name" class="col-sm-3 control-label">密码</label>
                                        <input
                                                class="form-control"
                                                name="password"
                                                data-toggle="password"
                                                type="password"
                                                maxlength="20"
                                                placeholder=""
                                                readonly
                                                onfocus="this.removeAttribute('readonly');">
                                    </div>
                                    <div class="form-group has-feedback">
                                        <label for="name" class="col-sm-3 control-label">确认密码</label>
                                        <input
                                                name="confirmPassword"
                                                data-toggle="password"
                                                class="form-control"
                                                type="password"
                                                maxlength="20"
                                                placeholder=""
                                                readonly
                                                onfocus="this.removeAttribute('readonly');">
                                    </div>
                                    <div class="row">
                                        <!-- /.col -->
                                        <div class="col-sm-offset-8 col-sm-4">
                                            <button type="button" class="btn btn-primary" style="float: right;"
                                                    id="J_updatePasswordConfirmModel" onClick="resetPassword();">修改密码
                                            </button>
                                        </div>
                                        <!-- /.col -->
                                    </div>
                                </form>
                            </div>
                            <!-- /.tab-pane -->
                        </div>
                        <!-- /.tab-content -->
                    </div>
                    <!-- /.nav-tabs-custom -->
                </div>
            </div>
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
<script src="${appPath}/components/select2/dist/js/select2.min.js"></script>
<script src="${appPath}/components/select2/dist/js/i18n/zh-CN.js"></script>
<script src="${appPath}/js/jquery.fileupload.js"></script>
<script src="${appPath}/js/common.js"></script>
<script src="${appPath}/js/upload/uploadImg.js"></script>
<script src="${appPath}/js/bootstrap-switch.js"></script>
<script src="${appPath}/js/docs/highlight.js"></script>
<script src="${appPath}/js/docs/main.js"></script>
<script src="https://unpkg.com/bootstrap-show-password@1.2.1/dist/bootstrap-show-password.min.js"></script>
<#include "/_components.ftl" />

<!-- AdminLTE App -->
<script src="${appPath}/js/adminlte.min.js"></script>
<script src="${appPath}/js/base64.min.js"></script>

<script>
    // 定义变量
    toastr.options.positionClass = 'toast-top-center';

    // 定义函数
    $(document).ready(function () {

        $('.player-selector').select2({
            language: "zh-CN",
            placeholder: "请选择业务名单类型"
        });

        $("#submit").click(function () {
            refresh();
        });

        $('#currentUserForm input[id=switch-state]').on('switchChange.bootstrapSwitch', function (event, state) {
            if (state === true) {
                // $("#authenticationConfirmModule").modal('show');
                saveSecondAuth();
            } else {
                document.getElementById("secondVerifyType").style.display = "none";
                document.getElementById("authKey").style.display = "none";
                document.getElementById("qrcodeUrl").style.display = "none";
            }
        })

        refresh();
    });

    function refresh() {
        loadCurrentUserDetailInfo()
    }
    
    function doChangeType() {
       var secondVerifyType = $('#currentUserForm select[name=secondVerifyType]').val();
       if (secondVerifyType == 2) {
       	  document.getElementById("authKey").style.display = "block";
          document.getElementById("qrcodeUrl").style.display = "block";
       } else {
       	  document.getElementById("authKey").style.display = "none";
          document.getElementById("qrcodeUrl").style.display = "none";
       }
    }

    // 后端请求 start
    function saveSecondAuth() {
        let reqParams = {};
        let username = $("#currentUserForm input[name='username']").val();
        reqParams.username = username;
        reqParams.open = false;
        $.ajax({
            url: "${appPath}/admin/usermanage/currentUser/generateUserAuthKey",
            contentType: 'application/json; charset=UTF-8',
            type: "GET",
            data: reqParams,
            success: function (res) {
                if (res.status) {
                    // refresh();
                    document.getElementById("secondVerifyType").style.display = "block";
                    document.getElementById("authKey").style.display = "block";
                    document.getElementById("qrcodeUrl").style.display = "block";
                    $('#currentUserForm select[name=secondVerifyType]').val(2);
                    if (typeof res.entry.googleAuthKey !== 'undefined') {
                        $('#currentUserForm input[name=authKey]').val(res.entry.googleAuthKey);
                    } else {
                        $('#currentUserForm input[name=authKey]').attr(null);
                    }
                    if (typeof res.entry.qrcodeUrl !== 'undefined') {
                        $('#currentUserForm img[name=qrcodeUrl]').attr("src", res.entry.qrcodeUrl);
                    } else {
                        $('#currentUserForm img[name=qrcodeUrl]').attr("src", null);
                    }
                } else {
                    toastr.error("操作失败：" + res.message);
                }
            }
        });
    }


    function loadCurrentUserDetailInfo() {
        $.ajax({
            url: "${appPath}/admin/usermanage/currentUser/detail",
            contentType: 'application/json; charset=UTF-8',
            type: "GET",
            success: function (res) {
                if (res.status) {
                    $('#currentUserForm input[name=username]').val(res.entry.username);
                    $('#currentUserForm input[name=name]').val(res.entry.name);
                    $('#currentUserForm input[name=email]').val(res.entry.email);
                    $('#currentUserForm input[name=mobile]').val(res.entry.mobile);
                    $('#currentUserForm input[id=switch-state]').bootstrapSwitch("state", res.entry.openSecondVerify);
                    
                    document.getElementById("secondVerifyType").style.display = (res.entry.openSecondVerify ? "block" : "none");
                    
                    var googleAuthComp = (res.entry.openSecondVerify && res.entry.secondVerifyType == 2) ? "block" : "none";
                    document.getElementById("authKey").style.display = googleAuthComp;
                    document.getElementById("qrcodeUrl").style.display = googleAuthComp;
                    
                    if (typeof res.entry.secondVerifyType !== 'undefined') {
                        $('#currentUserForm select[name=secondVerifyType]').val(res.entry.secondVerifyType);
                    }
                    if (typeof res.entry.authKey !== 'undefined') {
                        $('#currentUserForm input[name=authKey]').val(res.entry.authKey);
                    } else {
                        $('#currentUserForm input[name=authKey]').val(null);
                    }
                    if (typeof res.entry.qrcodeUrl !== 'undefined') {
                        $('#currentUserForm img[name=qrcodeUrl]').attr("src", res.entry.qrcodeUrl);
                        loadQrcodeImage(res.entry.qrcodeUrl);
                    } else {
                        $('#currentUserForm img[name=qrcodeUrl]').attr("src", null);
                    }
                } else {
                    toastr.error("操作失败：" + res.message);
                }
            }
        });
    }
    
    function loadQrcodeImage(url) {
    	$.ajax({
            url: "${appPath}/admin/usermanage/currentUser/getUserProfileQrcode?qrcodeUrl=" + url,
            contentType: 'application/json; charset=UTF-8',
            type: "GET",
            success: function (res) {
                if (res.status && !isBlank(res.entry)) {
                    $('#currentUserForm img[name=qrcodeUrl]').attr("src", 'data:image/jpeg;base64,' + res.entry);
                }
            }
    	});
    }
    
    function resetPassword() {
    	let rawOldPwd = $("input[name='oldPassword']").val();
        let rawPwd = $("input[name='password']").val();
        let confirmPassword = $("input[name='confirmPassword']").val();
        let oldPassword = Base64.encode(rawOldPwd);
        let password = Base64.encode(rawPwd);
        let entryConfirmPassword = Base64.encode(confirmPassword);
        if (isEmpty(rawOldPwd)) {
            toastr.error("操作失败：旧密码不能为空");
            return;
        }
        if (isEmpty(rawPwd)) {
            toastr.error("操作失败：新密码不能为空");
            return;
        }
        if (isEmpty(confirmPassword)) {
            toastr.error("操作失败：请再次确认新密码");
            return;
        }
        if (rawPwd !== confirmPassword) {
            toastr.error("操作失败：新密码两次输入不一致");
            return;
        }
        let formSerial = {};
        formSerial.oldPassword = oldPassword;
        formSerial.password = password;
        formSerial.confirmPassword = entryConfirmPassword;
        
        confirm(false, "确定要修改密码吗?", function() {
	
			$.ajax({
                url: "${appPath}/admin/usermanage/currentUser/updatePassword",
                data: JSON.stringify(formSerial),
                contentType: 'application/json; charset=UTF-8',
                type: "POST",
                success: function (res) {
                    if (res.status) {
                        toastr.success("密码修改已成功，即将跳转登录！");
                        location.href = '${appPath}/console/login';
                    } else {
                        toastr.error("操作失败：" + res.message);
                    }
                }
            });
	
		});
    }

    $(function () {
        $("#J_updateModel").click(function () {
            
            confirm(false, "确定要更新个人信息吗?", function() {
            
            	let pram = $("#currentUserForm").serializeArray();
	            let formSerial = {};
	            $(pram).each(function () {
	                formSerial[this.name] = this.value;
	            });
	            formSerial["openSecondVerify"] = $('#currentUserForm input[name=openSecondVerify]').bootstrapSwitch("state");
				
				$.ajax({
	                url: "${appPath}/admin/usermanage/currentUser/update",
	                data: JSON.stringify(formSerial),
	                contentType: 'application/json; charset=UTF-8',
	                type: "POST",
	                success: function (res) {
	                    if (res.status) {
	                        toastr.success("恭喜，操作已成功！");
	                        refresh();
	                    } else {
	                        toastr.error("操作失败：" + res.message);
	                    }
	                }
            	});
	
			});
        });


        $("#J_authenticationConfirm").click(function () {
            $("#authenticationConfirmModule").modal('hide');
            document.getElementById("secondVerifyType").style.display = "block";
            document.getElementById("authKey").style.display = "block";
            document.getElementById("qrcodeUrl").style.display = "block";

        });
    });

    // 后端请求 end
</script>

</body>
</html>
