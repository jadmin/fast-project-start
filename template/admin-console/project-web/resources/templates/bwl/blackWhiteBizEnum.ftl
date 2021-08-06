<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>名单业务类型</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.7 -->
    <link rel="stylesheet" href="${appPath}/components/bootstrap/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="${appPath}/components/bootstrap-table/bootstrap-table.min.css">
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
                名单代码
                <small></small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="${appPath}/console/bwl/blackWhiteBizEnum"><i class="fa fa-dashboard"></i> Home</a></li>
                <li><a href="#">名单中心</a></li>
                <li class="active">名单类型</li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-xs-12">
                    <div class="box">
                        <div class="box-body content">
                            <form id="listForm" class="form-inline">
                                <div class="form-group">
                                    <label for="name">名单代码</label>
                                    <input type="text" class="form-control" id="bizCode" name="bizCode" placeholder="">
                                </div>
                                <div class="form-group">
                                    <label for="mobile">名单描述</label>
                                    <input type="text" class="form-control" id="bizDesc" name="bizDesc" placeholder="">
                                </div>
                                <button type="button" class="btn btn-default" id="submit">查询</button>
                            </form>

                            <h4></h4>

                            <div id="toolbar">
                                <div class="btn-group">
                                    <#if blackWhiteListAdmin>
                                        <button id="btn_add" type="button" class="btn btn-default" onclick="clickAddBtn();" data-toggle="modal"
                                                data-target="#newModule">
                                            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
                                        </button>
                                    </#if>
                                </div>
                            </div>
                            <table id="table"
                                   data-toggle="table"
                                   data-toolbar="#toolbar"
                                   data-side-pagination="server"
                                   data-pagination="true"
                                   data-url="${appPath}/admin/bwlenum/listData.do"
                                   data-query-params="requestParameter">
                                <thead>
                                <tr>
                                    <th data-field="bizCode" data-formatter="bizCodeFormatter">名单代码</th>
                                    <th data-field="bizDesc">名单描述</th>
                                    <th data-field="creator">创建人</th>
                                    <th data-field="gmtCreate">创建时间</th>
                                 <#if blackWhiteListAdmin>
                                    <th data-field="action" data-formatter="actionFormatter">操作</th>
                                 </#if>
                                </tr>
                                </thead>
                            </table>
                        </div>
                    </div>
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

<!-- Modal start -->
<div class="modal fade" id="newModule" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document" style="width:600px; height:400px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">添加名单类型</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="moduleForm" method="post"
                      action="${appPath}/admin/bwlenum/saveRequest.do">
                    <div class="form-group">
                        <label for="idcard" class="col-sm-2 control-label">名单代码</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" name="bizCode" id="F_bizCode" placeholder="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="idcard" class="col-sm-2 control-label">名单描述</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" name="bizDesc" id="F_bizDesc" placeholder="">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-8 col-sm-4">
                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                            <button type="button" class="btn btn-primary" id="J_save">确定</button>
                        </div>
                    </div>
                </form>
            </div>

        </div>
    </div>
</div>
<!-- Modal end -->

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

<script src="${appPath}/js/common.js"></script>

<#include "/_components.ftl" />

<!-- FastClick -->
<script src="${appPath}/components/fastclick/1.0.6/fastclick.min.js"></script>
<!-- AdminLTE App -->
<script src="${appPath}/js/adminlte.min.js"></script>

<script>

    toastr.options.positionClass = 'toast-top-center';

    $(document).ready(function () {
        $("#submit").click(function () {
            refresh();
        });
    });

    function requestParameter(params) {
        var obj = {};
        obj.pageSize = params.limit;
        obj.pageNo = (params.offset / params.limit) + 1;

        var bizCode = $("input[name='bizCode']").val();
        var bizDesc = $("input[name='bizDesc']").val();

        if (!isEmpty(bizCode)) {
            obj.bizCode = bizCode;
        }
        if (!isEmpty(bizDesc)) {
            obj.bizDesc = bizDesc;
        }

        return obj;
    }

    function refresh() {
        $('#table').bootstrapTable('removeAll');
        $('#table').bootstrapTable('refresh');
    }

    function actionFormatter(value, row, index) {
        return "<a href='javascript:void(0)' onClick=doDel(" + row.id + ",'"  + row.bizCode + "');>删除</a>&nbsp";
    }


    function doDel(id, bizCode) {
        confirm(true, "您确定要删除此名单类型<font color='red'>["+ bizCode +"]</font>吗?", function() {
            var url = '${appPath}/admin/bwlenum/delete';
            $.post(url, { id: id }, function(data) {
                if(data.status) {
                    toastr.success("恭喜，操作成功！");
                    refresh();
                } else {
                    toastr.error(data.message);
                }
            }, "json");
        });
    }

    function bizCodeFormatter(value, row, index) {
        var link = '${appPath}/console/bwl/blackWhiteList?bizCode=' + row.bizCode;
        return "<a href='" + link + "'>" + row.bizCode + "</a>";
    }

    function clickAddBtn() {
        $('#F_bizCode').val("");
        $('#F_bizDesc').val("");
    }

    $(function () {
        $("#J_save").click(function () {
            $.ajax({
                url: "${appPath}/admin/bwlenum/saveRequest.do",
                data: $("#moduleForm").serialize(),
                type: "POST",
                success: function (res) {
                    $("#newModule").modal('hide');
                    if (res.status) {
                        toastr.success("恭喜，操作已成功！");
                        window.location.reload();
                    } else {
                        toastr.error("操作失败：" + res.message);
                    }
                }
            });
        });
    });


</script>

</body>
</html>
