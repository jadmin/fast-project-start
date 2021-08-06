<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>忘记密码</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.7 -->
    <link rel="stylesheet" href="${appPath}/components/bootstrap/dist/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="${appPath}/components/font-awesome/css/font-awesome.min.css">
    <!-- Ionicons -->
    <link rel="stylesheet" href="${appPath}/components/Ionicons/css/ionicons.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="${appPath}/css/AdminLTE.min.css">
    <!-- iCheck -->
    <link rel="stylesheet" href="${appPath}/plugins/iCheck/square/blue.css">

    <link rel="stylesheet" href="${appPath}/css/toastr.min.css">
    
    <link rel="shortcut icon" href="${appPath}/images/icon-48x48.png">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

    <script src="${appPath}/components/jquery/dist/jquery.min.js"></script>
    <script src="${appPath}/js/toastr.min.js"></script>
    <script src="${appPath}/js/common.js"></script>
</head>
<body class="hold-transition login-page">
<div class="login-box">
    <div class="login-logo">
        <b>忘记密码</b>
    </div>
    <!-- /.login-logo -->
    <div class="login-box-body">
        <p class="login-box-msg"><code id="J_pompt"></code></p>
        <form id="resetPasswordForm">
            <div class="form-group has-feedback">
                <input type="text" name="username" class="form-control" placeholder="用户名" autocomplete="off">
            </div>
            <div class="form-group has-feedback">
                <input
                        name="password"
                        data-toggle="password"
                        class="form-control"
                        type="password"
                        maxlength="10"
                        placeholder="新密码"
                        readonly
                        onfocus="this.removeAttribute('readonly');">
            </div>
            <div class="form-group has-feedback">
                <input
                        name="confirmPassword"
                        data-toggle="password"
                        class="form-control"
                        type="password"
                        maxlength="10"
                        placeholder="确认密码"
                        readonly
                        onfocus="this.removeAttribute('readonly');">
            </div>
            <div class="form-group has-feedback">
                <input type="text" name="randVerificationCode" placeholder="验证码" autocomplete="off">
                <input id="btnSendCode" type="button" value="获取验证码" onClick="sendMessage()" />
            </div>
            <div class="row">
                <!-- /.col -->
                <div class="col-xs-4">
                    <button type="button" id="returnSubmit" class="btn btn-primary btn-block btn-flat">返回
                    </button>
                </div>
                <div class="col-xs-4" style="float: right">
                    <button type="button" id="resetPasswordSubmit" class="btn btn-primary btn-block btn-flat"
                            disabled="true">确认
                    </button>
                </div>
                <!-- /.col -->
            </div>
        </form>
    </div>
    <!-- /.login-box-body -->
</div>
<!-- /.login-box -->

<!-- Bootstrap 3.3.7 -->
<script src="${appPath}/components/bootstrap/dist/js/bootstrap.min.js"></script>
<!-- iCheck -->
<script src="${appPath}/plugins/iCheck/icheck.min.js"></script>
<script src="${appPath}/js/base64.min.js"></script>
<script src="https://unpkg.com/bootstrap-show-password@1.2.1/dist/bootstrap-show-password.min.js"></script>
<script>

    toastr.options.positionClass = 'toast-top-center';

    $(function () {
        $('input').iCheck({
            checkboxClass: 'icheckbox_square-blue',
            radioClass: 'iradio_square-blue',
            increaseArea: '20%' /* optional */
        });

        $("#resetPasswordSubmit").click(function () {
            var pass = doCommonCheck();
            if(!pass) {
            	return;
            }
            let username = $("input[name='username']").val();
            let rawPwd = $("input[name='password']").val();
            let confirmPassword = $("input[name='confirmPassword']").val();
            let randVerificationCode = $("input[name='randVerificationCode']").val();
            let password = Base64.encode(rawPwd);
            let entryConfirmPassword = Base64.encode(confirmPassword);
            
            if (isEmpty(randVerificationCode)) {
                $("#J_pompt").html("验证码不能为空");
                return;
            }

            let reqBody = {};
            reqBody.password = password;
            reqBody.confirmPassword = entryConfirmPassword
            reqBody.username = username
            reqBody.randVerificationCode = randVerificationCode
            $.ajax({
                url: "${appPath}/admin/user/password/forget",
                data: JSON.stringify(reqBody),
                type: "POST",
                headers: {
                    'Content-Type': 'application/json; charset=UTF-8'
                },
                success: function (res) {
                    if (res.status && null != res.entry && res.entry) {
                    	$("#J_pompt").html("");
                        toastr.success("恭喜，密码重置成功！");
                        setTimeout(jumpLogin, 2000);
                    } else {
                        $("#J_pompt").html(res.message);
                    }
                }
            });
        });

        $("#returnSubmit").click(function () {
            location.href = '${appPath}/console/login';
        });
    });
    
    function doCommonCheck() {
    	let username = $("input[name='username']").val();
        let rawPwd = $("input[name='password']").val();
        let confirmPassword = $("input[name='confirmPassword']").val();
        let password = Base64.encode(rawPwd);
        let entryConfirmPassword = Base64.encode(confirmPassword);
        if (isEmpty(username)) {
            $("#J_pompt").html("用户名不能为空");
            return false;
        }
        if (isEmpty(rawPwd)) {
        	$("#J_pompt").html("新密码不能为空");
        	return false;
        }
        if (isEmpty(confirmPassword)) {
            $("#J_pompt").html("确认密码不能为空");
            return false;
        }
        if (confirmPassword !== rawPwd) {
            $("#J_pompt").html("两次密码不一致，请检查");
            return false;
        }
    	
    	return true; 
    }
    
    function jumpLogin() {
    	location.href = '${appPath}/console/login';
    }
</script>
<script type="text/javascript">

    let InterValObj; //timer变量，控制时间
    let count = 60; //间隔函数，1秒执行
    let curCount;//当前剩余秒数
    let code = ""; //验证码
    let codeLength = 6;//验证码长度
    
    function sendMessage() {
    	var pass = doCommonCheck();
        if(!pass) {
        	return;
        }
        curCount = count;
        let dealType; //验证方式
        let uid = $("#uid").val();//用户uid
        if ($("#phone").attr("checked") === true) {
            dealType = "phone";
        } else {
            dealType = "email";
        }
        //产生验证码
        for (let i = 0; i < codeLength; i++) {
            code += parseInt(Math.random() * 9).toString();
        }
        //设置button效果，开始计时
        $("#btnSendCode").attr("disabled", "true");
        $("#btnSendCode").val(+curCount + "秒后获取");
        InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
        //向后台发送处理数据
        let username = $("input[name='username']").val();
        if (isEmpty(username)) {
            toastr.error("操作失败：用户名不能为空");
        }
        let reqBody = {};
        reqBody.username = username
        $.ajax({
            url: "${appPath}/admin/user/password/forget/sms",
            data: JSON.stringify(reqBody),
            type: "POST",
            headers: {
                'Content-Type': 'application/json; charset=UTF-8'
            },
            success: function (res) {
                if (res.status && null != res.entry && res.entry) {
                	$("#J_pompt").html("");
                    toastr.success("验证码已发送至该账户下绑定邮箱！");
                    $("#resetPasswordSubmit").prop('disabled', false);
                } else {
                    $("#J_pompt").html(res.message);
                }
            }
        });
    }

    //timer处理函数
    function SetRemainTime() {
        if (curCount === 0) {
            window.clearInterval(InterValObj);//停止计时器
            $("#btnSendCode").removeAttr("disabled");//启用按钮
            $("#btnSendCode").val("重新发送验证码");
            code = ""; //清除验证码。如果不清除，过时间后，输入收到的验证码依然有效
        } else {
            curCount--;
            $("#btnSendCode").val(+curCount + "秒后获取");
        }
    }
</script>

</body>
</html>