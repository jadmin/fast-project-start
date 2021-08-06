<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>用户登录</title>
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
</head>
<body class="hold-transition login-page">
<div class="login-box">
  <div class="login-logo">
    <b>用户登录</b>
  </div>
  <!-- /.login-logo -->
  <div class="login-box-body">
    <p class="login-box-msg" id="J_pompt"></p>

    <form id="loginForm" action="${appPath}/admin/user/login" method="post">
      <div class="form-group has-feedback">
        <input type="text" name="username" class="form-control" placeholder="用户名">
        <span class="glyphicon glyphicon-user form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <input type="password" name="password" class="form-control" placeholder="登录密码">
        <span class="glyphicon glyphicon-lock form-control-feedback"></span>
        <input type="hidden" name="crsfToken" value="${crsfToken}">
        <input type="hidden" name="tokenKey" value="${tokenKey}">
      </div>
      <div class="form-group has-feedback">
	        <div class="checkbox icheck">
	            <label>
	                <input id="ldap" type="checkbox"> LDAP登陆
	            </label>
	            <label>
	                <input id="rmbMe" type="checkbox"> 记住我
	            </label>
	        </div>
      </div>
      <div class="row">
        <div class="col-xs-8">
          <div class="checkbox icheck">
            <label>
                 <a href="${appPath}/console/user/forgetPassword">忘记密码</a>
            </label>
          </div>
        </div>
        <!-- /.col -->
        <div class="col-xs-4">
          <button type="button" id="loginSubmit" class="btn btn-primary btn-block btn-flat">登录</button>
        </div>
        <!-- /.col -->
      </div>
    </form>
    
    <form id="loginConfirmForm" style="display:none;" action="/aibox/admin/user/loginConfirm" method="post">
      
	    <div class="form-group has-feedback">
	      <input type="text" name="code" class="form-control" autocomplete="off" placeholder="动态验证码">
	      <span class="glyphicon glyphicon-lock form-control-feedback"></span>
	      <input type="hidden" name="type" id="J_codeType" value="">
	      <input type="hidden" name="uid" id="J_uid" value="">
	    </div>
	    <div class="row">
	      
	      <!-- /.col -->
	      <div class="col-xs-12">
	        <button type="button" id="loginConfirmSubmit" class="btn btn-primary btn-block btn-flat">登录</button>
	      </div>
	      <!-- /.col -->
	    </div>
	</form>

	<!-- 
    <div class="social-auth-links text-center">
      <p>- OR -</p>
      <a href="#" class="btn btn-block btn-social btn-facebook btn-flat"><i class="fa fa-facebook"></i> Sign in using
        Facebook</a>
      <a href="#" class="btn btn-block btn-social btn-google btn-flat"><i class="fa fa-google-plus"></i> Sign in using
        Google+</a>
    </div>

    <a href="#">I forgot my password</a><br>
    <a href="register.html" class="text-center">Register a new membership</a>
    -->

  </div>
  <!-- /.login-box-body -->
</div>
<!-- /.login-box -->

<!-- Bootstrap 3.3.7 -->
<script src="${appPath}/components/bootstrap/dist/js/bootstrap.min.js"></script>
<!-- iCheck -->
<script src="${appPath}/plugins/iCheck/icheck.min.js"></script>
<script src="${appPath}/js/base64.min.js"></script>
<script src="${appPath}/js/common.js"></script>
<script src="${appPath}/js/ui.js"></script>
<script>

  toastr.options.positionClass = 'toast-top-center';
  
  var isLogined = false;
  $(document).keydown(function(event) {
     if (event.keyCode == 13 && isLogined === false) { // 监听回车键，触发登录操作
         $("#loginSubmit").click();
     }
  });

  $(function () {
  
    $('input').iCheck({
      checkboxClass: 'icheckbox_square-blue',
      radioClass: 'iradio_square-blue',
      increaseArea: '20%' /* optional */
    });
    
    $("#loginSubmit").click(function () {
    	var crsfToken = $("input[name='crsfToken']").val();
    	var tokenKey = $("input[name='tokenKey']").val();
    	var username = $("input[name='username']").val();
    	var rawPwd = $("input[name='password']").val();
    	var password = Base64.encode(rawPwd + tokenKey);
    	
    	var isRememberMe = $('#rmbMe').is(':checked');
        var isLdap = $('#ldap').is(':checked');
        
        if ( isBlank(username) ) {
        	$("#J_pompt").html(codeText("用户名不能为空"));
        	return;
        }
        if ( isBlank(rawPwd) ) {
        	$("#J_pompt").html(codeText("登录密码不能为空"));
        	return;
        }
    	
    	var reqBody = {};
    	reqBody.username = username;
    	reqBody.password = password;
    	reqBody.rememberMe = isRememberMe;
    	if (isLdap === undefined || isLdap === false) {
            //  用户名密码登陆
            reqBody.loginType = 1;
        } else {
            // LDAP登陆
            reqBody.loginType = 2;
        }
        $("#J_pompt").html('<img src="${appPath}/images/loading.gif">');
        $('#loginSubmit').attr("disabled", true);
        $.ajax({
            url: "${appPath}/admin/user/login",
            data: JSON.stringify(reqBody),
            type: "POST",
            headers: {
            	'token': crsfToken,
            	'Content-Type': 'application/json; charset=UTF-8'
            },
            success: function (res) {
            	$('#loginSubmit').removeAttr("disabled");
                if (res.status && null != res.entry && res.entry.loginSucess) {
                	isLogined = true;
                    var openedVerify = res.entry.openSecondVerify;
                    $("#J_pompt").html(""); // 清空之前的提示信息
                    if (openedVerify) {
                    	$("#loginForm").hide();
                    	$("#J_codeType").val(res.entry.secondVerifyType);
                    	$("#J_uid").val(res.entry.userId);
                    	$("#loginConfirmForm").show();
                    } else {
                    	toastr.success("恭喜，登录成功！");
                        var url = '${appPath}/console/index';
                        location.href = url; 
                    }
                } else {
                    $("#J_pompt").html(codeText(res.message));
                    refreshTokenKey();
                }
            }
        });
     });
     
     
     $("#loginConfirmSubmit").click(function () {
    	var uid = $("input[name='uid']").val();
    	var type = $("input[name='type']").val();
    	var code = $("input[name='code']").val();
    	
    	var reqBody = {};
    	reqBody.uid = uid;
    	reqBody.type = type;
    	reqBody.code = code;
    	
        $.ajax({
            url: "${appPath}/admin/user/loginConfirm",
            data: JSON.stringify(reqBody),
            type: "POST",
            headers: {
            	'Content-Type': 'application/json; charset=UTF-8'
            }, 
            success: function (res) {
                if (res.status && null != res.entry && res.entry) {
                	toastr.success("恭喜，登录成功！");
                    var url = '${appPath}/console/index';
                    location.href = url; 
                } else {
                    $("#J_pompt").html(codeText(res.message));
                }
            }
        });
     });
    
  });
  
  function refreshTokenKey() {
  	  $.ajax({
	        type: "GET",
	        url: "${appPath}/admin/user/getTokenKey",
	        data: {},
	        dataType: "JSON",
	        success: function(res) {
	        	if (res.status && !isEmpty(res.entry)) {
	        		$("input[name='tokenKey']").val(res.entry.left);
	        		$("input[name='crsfToken']").val(res.entry.right);
	        	}
	        }
      });
  }
  
</script>

</body>
</html>