<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>业务工具</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.7 -->
    <link rel="stylesheet" href="${appPath}/components/bootstrap/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="${appPath}/components/bootstrap-table/bootstrap-table.min.css" >
    <link rel="stylesheet" href="${appPath}/components/bootstrap-daterangepicker/daterangepicker.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="${appPath}/components/font-awesome/css/font-awesome.min.css">
    <!-- Ionicons -->
    <link rel="stylesheet" href="${appPath}/components/Ionicons/css/ionicons.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="${appPath}/css/AdminLTE.min.css">
    <!-- AdminLTE Skin -->
    <link rel="stylesheet" href="${appPath}/css/skins/${skin}.min.css">

    <link href="${appPath}/css/toastr.min.css" rel="stylesheet">
    <link href="${appPath}/css/jquery.fileupload-ui.css" rel="stylesheet">
	<link href="${appPath}/css/jquery.fileupload.css" rel="stylesheet">
	<link href="${appPath}/css/jquery-ui.min.css" rel="stylesheet">
	
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
                业务工具
                <small></small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="${appPath}/console/index"><i class="fa fa-dashboard"></i> Home</a></li>
                <li><a href="#">工具中心</a></li>
                <li class="active">业务工具</li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content">
        	
            <div class="row">
		        <div class="col-md-12">
		        
				  <div class="box">
				  
				    <div class="box-body">
				      <div class="row">
				      	<div class="col-sm-5">
							<div class="input-group input-group-sm hidden-xs" style="width: 500px;">
					          <input type="text" name="searchWord" value="${queryWord!""}" id="J_searchWord" class="form-control pull-right" placeholder="搜索查询">
					          <div class="input-group-btn">
					            <button type="submit" class="btn btn-default" onclick="searchQ()"><i class="fa fa-search"></i></button>
					          </div>
					        </div>
				      	</div>
				      	
				      	<div class="col-sm-7">
    						<div class="pull-right">
    							<ul class="pagination" style="margin:0 0" id="J_pageBar">
    							<!-- 
    								<li class="page-first disabled"><a href="javascript:void(0)">«</a></li>
    								<li class="page-pre disabled"><a href="javascript:void(0)">‹</a></li>
    								<li class="page-number active"><a href="javascript:void(0)">1</a></li>
    								<li class="page-number"><a href="javascript:void(0)">2</a></li>
    								<li class="page-number"><a href="javascript:void(0)">3</a></li>
    								<li class="page-number"><a href="javascript:void(0)">4</a></li>
    								<li class="page-number"><a href="javascript:void(0)">5</a></li>
    								<li class="page-next"><a href="javascript:void(0)">›</a></li>
    								<li class="page-last"><a href="javascript:void(0)">»</a></li>
    							-->
    							</ul>
    						</div>
				      	</div>
				      	
				      </div>
				    </div>
				    <!-- /.box-body -->
				    
				  </div>
				  <!-- /.box -->
				  
		        </div>
            </div>
        	
        
            <div class="row">
            	<!-- 左边一列 -->
                <div class="col-md-6">
                		
                <#if leftList?? && (leftList?size > 0) >
	                <#list leftList as left>
	                  ${left.widgetHtml!""}
	                </#list>
                </#if>
                        
                </div>
                
                
                <!-- 右边一列 -->
                <div class="col-md-6">

                <#if rightList?? && (rightList?size > 0) >
	                <#list rightList as right>
	                  ${right.widgetHtml!""}
	                </#list>
                </#if>

                </div>
                
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
<script src="${appPath}/components/bootstrap-table/bootstrap-table.min.js"></script>
<script src="${appPath}/components/bootstrap-table/tableExport.min.js"></script>
<script src="${appPath}/components/bootstrap-table/bootstrap-table-export.js"></script>
<script src="${appPath}/components/moment/min/moment-with-locales.min.js"></script>
<script src="${appPath}/components/bootstrap-daterangepicker/daterangepicker.js"></script>
<script src="${appPath}/js/jquery.ui.widget.js"></script>
<script src="${appPath}/js/jquery.iframe-transport.js"></script>
<script src="${appPath}/js/jquery.fileupload.js"></script>

<script src="${appPath}/js/common.js"></script>
<script src="${appPath}/js/ui.js"></script>

<#include "/_components.ftl" />

<!-- FastClick -->
<script src="${appPath}/components/fastclick/1.0.6/fastclick.min.js"></script>
<!-- AdminLTE App -->
<script src="${appPath}/js/adminlte.min.js"></script>

<script>
    toastr.options.positionClass = 'toast-top-center';
    var ctxPath = "${appPath}";
    
    $(document).ready(function() {
    
    	pagebar(${totalPages}, ${currentPage}, 5);

        ${ajax!""}
        
    });
    
    ${functions!""}
    
    function searchQ() {
    	var q = $('#J_searchWord').val();
		var url = '${appPath}/console/ops/biztools';
		if( !isBlank(q) ) {
			url = url + '?q=' + q.trim();
		}
        location.href = url; 
    }
    
    function goPage(pageNo) {
    	var q = $('#J_searchWord').val();
		var url = '${appPath}/console/ops/biztools?p=' + pageNo;
		if ( !isBlank(q) ) {
			url = url + '&q=' + q.trim();
		}
        location.href = url; 
    }

</script>

</body>
</html>
