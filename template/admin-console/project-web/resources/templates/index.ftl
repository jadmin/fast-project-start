<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>管理后台</title>
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
  <link rel="stylesheet" href="${appPath}/css/toastr.min.css">
  
  <link rel="shortcut icon" href="${appPath}/images/icon-48x48.png">

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
        Dashboard
        <small>Control panel</small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
        <li class="active">Dashboard</li>
      </ol>
    </section>

    <!-- Main content -->
    <section class="content">

      <!-- Small boxes (Stat box) -->
      <div class="row">
        <div class="col-lg-3 col-xs-6">
          <!-- small box -->
          <div class="small-box bg-aqua">
            <div class="inner">
              <h3>150</h3>

              <p>新订单</p>
            </div>
            <div class="icon">
              <i class="ion ion-bag"></i>
            </div>
            <a href="#" class="small-box-footer">更多 <i class="fa fa-arrow-circle-right"></i></a>
          </div>
        </div>
        <!-- ./col -->
        <div class="col-lg-3 col-xs-6">
          <!-- small box -->
          <div class="small-box bg-green">
            <div class="inner">
              <h3>53<sup style="font-size: 20px">%</sup></h3>

              <p>转化率</p>
            </div>
            <div class="icon">
              <i class="ion ion-stats-bars"></i>
            </div>
            <a href="#" class="small-box-footer">更多 <i class="fa fa-arrow-circle-right"></i></a>
          </div>
        </div>
        <!-- ./col -->
        <div class="col-lg-3 col-xs-6">
          <!-- small box -->
          <div class="small-box bg-yellow">
            <div class="inner">
              <h3>44</h3>

              <p>新用户</p>
            </div>
            <div class="icon">
              <i class="ion ion-person-add"></i>
            </div>
            <a href="#" class="small-box-footer">更多 <i class="fa fa-arrow-circle-right"></i></a>
          </div>
        </div>
        <!-- ./col -->
        <div class="col-lg-3 col-xs-6">
          <!-- small box -->
          <div class="small-box bg-red">
            <div class="inner">
              <h3>65</h3>

              <p>访客</p>
            </div>
            <div class="icon">
              <i class="ion ion-pie-graph"></i>
            </div>
            <a href="#" class="small-box-footer">更多 <i class="fa fa-arrow-circle-right"></i></a>
          </div>
        </div>
        <!-- ./col -->
      </div>
      <!-- /.row -->

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
<script src="${appPath}/js/common.js"></script>
<script>
  toastr.options.positionClass = 'toast-top-center';
  
  $(function () {
  
  });

  function getDeviceActivateTotal() {
    $.ajax({
      url: "${appPath}/admin/devices/listData?pageSize=10&pageNo=1",
      type: 'get',
      dataType: "json",
      async: false,
      cache: false,
      contentType: false,
      processData: false,
      success: function (data) {
         $('#deviceActivateTotal').html(data.total);
      },
      error: function (request) {
        alert("网络错误，请重试！");
      },
    });
  }

  function getDeviceActivateStatistic() {
    let startTime = toTimestampWithoutSecondString(new Date());
    let endTime = toTimestampWithoutSecondString(new Date());
    $.ajax({
      url: "${appPath}/admin/reportForm/device?startDate=" + startTime + "&endDate=" + endTime,
      type: 'get',
      dataType: "json",
      async: false,
      cache: false,
      contentType: false,
      processData: false,
      success: function (data) {
        if (data.status) {
          for (let item of data.entry) {
            $('#deviceActivateToday').html(item.firstActivateTotalPerDay);
          }
        } else {
          alert(data.message);
        }
      },
      error: function (request) {
        alert("网络错误，请重试！");
      },
    });
  }

  function getAnchorFocusStatistic() {
    $.ajax({
      url: "${appPath}/admin/reportForm/focus/total",
      type: 'get',
      dataType: "json",
      async: false,
      cache: false,
      contentType: false,
      processData: false,
      success: function (data) {
        if (data.status) {
          $('#anchorFocus').html(data.entry);
        } else {
          alert(data.message);
        }
      },
      error: function (request) {
        alert("网络错误，请重试！");
      },
    });
  }
</script>
</body>
</html>
