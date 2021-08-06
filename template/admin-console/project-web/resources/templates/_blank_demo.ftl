<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>配置中心</title>
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
  <!-- AdminLTE Skin -->
  <link rel="stylesheet" href="${appPath}/css/skins/${skin}.min.css">
  
  <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->

  <!-- jQuery 3 -->
  <script src="${appPath}/components/jquery/dist/jquery.min.js"></script>

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
        配置管理
        <small></small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="${appPath}/index"><i class="fa fa-dashboard"></i> Home</a></li>
        <li class="active">配置管理</li>
      </ol>
    </section>

    <!-- Main content -->
    <section class="content">
    
    	<div class="row">
	      	<div class="col-xs-12">
	      		<div class="box">
	      			<div class="box-body content">
	        				这里是 XXX 内容
	        		</div>
	        	</div>
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

<#include "/_components.ftl" />

<!-- AdminLTE App -->
<script src="${appPath}/js/adminlte.min.js"></script>
</body>
</html>
