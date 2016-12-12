<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title></title>
    <link href="../vendors/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="../vendors/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <link href="../vendors/iCheck/skins/flat/green.css" rel="stylesheet">
    <link href="css/sweetalert.css" rel="stylesheet">
    <script src="js/jquery-2.0.2.min.js"></script>
    <!-- PNotify -->
    <link href="../vendors/pnotify/dist/pnotify.css" rel="stylesheet">
    <link href="../vendors/pnotify/dist/pnotify.buttons.css" rel="stylesheet">
    <link href="../vendors/pnotify/dist/pnotify.nonblock.css" rel="stylesheet">
    <!-- Custom Theme Style -->
    <link href="css/custom.css" rel="stylesheet">
</head>
<body class="nav-md">
<div class="container body">
    <div class="main_container">
        <div class="col-md-3 left_col" id="leftbody"></div>
        <script>
            $.ajaxSetup({
                async : false
            });
        </script>
        <script> $.get("../leftbody", function (data) {
            $("#leftbody").html(data);
        });</script>
        <div class="top_nav" id="head"></div>
        <script> $.get("../head", function (data) {
            $("#head").html(data);
        });</script>
        <script>
            $.ajaxSetup({
                async : true
            });
        </script>
        <div  class="right_col" role="main" >
            <!--页面开发位置-->
            <div class="">
                <div class="row">
                    <div class="col-md-12 col-sm-12 col-xs-12">
                        <div class="x_panel ">
                            <div class="x_title">
                                <h2 class="col-lg-3 col-md-6 col-sm-6 col-xs-6" style="float: left">取报告</h2>
                                <ul class="nav navbar-right panel_toolbox" style="float: right">
                                    <li><a class=""><i class="fa fa-trash-o"></i> 删除</a>
                                    </li>
                                    <li><a href="demo.jsp"><i class="fa fa-plus"></i> 新增</a>
                                    </li>
                                </ul>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content">
                                <form class="form-horizontal form-label-left" style="padding: 0px 10px;overflow-y:hidden;overflow-x:auto;margin-bottom: 5px ">
                                    <div class="row" >
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-xs-12">
                                            <label class="" style="line-height: 35px; float: left;">角色：</label>
                                            <div class="col-md-9 col-xs-9 form-group" >
                                                <select class="form-control " required>
                                                    <option value="">Choose..</option>
                                                    <option value="press">Press</option>
                                                    <option value="net">Internet</option>
                                                    <option value="mouth">Word of mouth</option>
                                                </select>
                                            </div>
                                        </div>

                                        <div class="col-lg-4 col-md-4 col-sm-4 col-xs-12">
                                            <label  class="" style="line-height: 35px; float: left;">特征：</label>
                                            <div class="col-md-9 col-xs-9 form-group">
                                                <input type="text" placeholder=".col-md-3" class="form-control">
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <button class="btn btn-primary">搜索</button>
                                        </div>

                                        <!--<span class="tishired">未找到相关数据</span>-->
                                    </div>
                                </form>
                                <div class="table-responsive ">
                                    <table class="table table-bordered bulk_action">
                                        <thead>
                                        <tr class="headings">
                                            <th>
                                                <input type="checkbox" id="check-all" class="flat">
                                            </th>
                                            <th>委托单编号</th>
                                            <th>样品名称</th>
                                            <th>已完成阶段</th>
                                            <th>申请日期</th>
                                            <th>业务分类</th>
                                            <th>操作</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr class="pointer">
                                            <td class="a-center ">
                                                <input type="checkbox" class="flat" name="table_records">
                                            </td>
                                            <td>121000037</td>
                                            <td>May 24, 2014 10:52:44 PM</td>
                                            <td>121000204</td>
                                            <td>Mike Smith</td>
                                            <td>Paid</td>
                                            <td><a href="#">查看</a> | <a href="javascript:;" class="delete">删除</a></td>
                                        </tr>
                                        <tr class="pointer">
                                            <td class="a-center ">
                                                <input type="checkbox" class="flat" name="table_records">
                                            </td>
                                            <td>121000040</td>
                                            <td>May 24, 2014 11:47:56 PM </td>
                                            <td>121000210</td>
                                            <td>John Blank L</td>
                                            <td>Paid</td>
                                            <td><a href="#">查看</a> | <a href="javascript:;" class="reminder">提示框</a></td>
                                        </tr>
                                        <tr class="pointer">
                                            <td class="a-center ">
                                                <input type="checkbox" class="flat" name="table_records">
                                            </td>
                                            <td>121000039</td>
                                            <td>May 26, 2014 11:30:12 PM</td>
                                            <td>121000208</i>
                                            </td>
                                            <td>John Blank L</td>
                                            <td>Paid</td>
                                            <td><a href="#">查看</a> | <a href="javascript:;" class="demo1">淡出</a></td>
                                        </tr>
                                        <tr class="pointer">
                                            <td class="a-center ">
                                                <input type="checkbox" class="flat" name="table_records">
                                            </td>
                                            <td>121000038</td>
                                            <td>May 26, 2014 10:55:33 PM</td>
                                            <td>121000203</td>
                                            <td>Mike Smith</td>
                                            <td>Paid</td>
                                            <td><a href="#">查看</a> | <a href="javascript:;" onclick="showLoading()">加载中</a></td>
                                        </tr>
                                        <tr class="pointer">
                                            <td class="a-center ">
                                                <input type="checkbox" class="flat" name="table_records">
                                            </td>
                                            <td>121000037</td>
                                            <td>May 26, 2014 10:52:44 PM</td>
                                            <td>121000204</td>
                                            <td>Mike Smith</td>
                                            <td>Paid</td>
                                            <td><a href="#">查看</a> | <a href="javascript:;" onclick="smallshowLoading()">小的加载</a></td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                                <div class="text-right">
                                    <div class="row">
                                        <div class="col-lg-3 col-md-6 col-sm-6 col-xs-12" style="font-size: 15px;float: left;line-height: 32px">
                                            共<span>11</span>页<span>53</span>条记录
                                        </div>
                                        <ul class="pagination" style="margin-top: 0px;float: right;margin-right: 11px">
                                        <li><a href="#">首页</a></li>
                                        <li><a href="#"><i class="fa fa-angle-double-left"></i></a></li>
                                        <!--active是高亮的样式，如需要在li中添加-->
                                        <li class=""><a href="#">1</a></li>
                                        <li><a href="#"><i class="fa fa-angle-double-right"></i></a></li>
                                        <li><a href="#">尾页</a></li>
                                        <!--<li style="overflow: hidden;display: inline">-->
                                            <!--<div style="display: inline;float: right;width: 124px;margin-left: 15px">-->
                                                 <!--<input type="text" class="form-control" style="float: left;width:70px;height:32px;">-->
                                                 <!--<span class="input-group-btn" style="width: 54px">-->
                                                 <!--<button type="button" class="btn btn-primary" style="float: left;height: 32px;margin: 0px;">跳转</button>-->
                                                 <!--</span>-->
                                            <!--</div>-->
                                        <!--</li>-->
                                    </ul>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="out" style="display: none; z-index: 9999">
    <!--<div style="  width: 100%; height:100%; position: absolute; top: 0px; left: 0px;z-index: 5000;background-color: rgb(45, 45, 45);  opacity: 0.2;">-->
    <!--</div>-->
    <div class="danchu" style="
    color: #000;
    font-size: 18px;
    text-align: center;
    font-weight: 500;
    line-height:100px;
    margin: auto;
    height:100px;
    background: #ffffff;
    opacity: 0.6;
    border: 2px solid #73879C;
    box-shadow: #73879C 0 0 10px;
    z-index: 9999">保存成功!</div>

</div>

<script src="js/sweetalert/sweetalert.min.js"></script>
<!-- Bootstrap -->
<script src="../vendors/bootstrap/dist/js/bootstrap.min.js"></script>
<!-- iCheck -->
<script src="../vendors/iCheck/icheck.min.js"></script>
<!-- PNotify -->
<script src="../vendors/pnotify/dist/pnotify.js"></script>
<script src="../vendors/pnotify/dist/pnotify.buttons.js"></script>
<script src="../vendors/pnotify/dist/pnotify.nonblock.js"></script>
<!-- Custom Theme Scripts -->
<script src="js/custom.js"></script>
<script>
    $('.delete').click(function () {
        swal({
            title: "您确定要删除这条信息吗",
            text: "删除后将无法恢复，请谨慎操作！",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "删除",
            closeOnConfirm: false
        }, function () {
            swal("删除成功！", "您已经永久删除了这条信息。", "success");
        });
    });
</script>
<script>
    $(document).ready(function(){
        $(".demo1").click(function(){
            $ ("#out").show ().delay (1000).fadeOut ();
        });
        $(".reminder").click(function(){
            swal({
                title:"太帅了",
                text:"小手一抖就打开了一个框",
                type:"success"})
        });

    });
</script>
<script>
    function showLoading() {
        $("body").append('<div class="loadingbg" id="loadingbg"></div>');
    }
    function hideLoading() {
        $("#loadingbg").remove();
    }
    function smallshowLoading() {
        $(".x_panel").append('<div class="loadingbg" id="loadingbg"></div>');
    }
//    $(document).ready(function(){
//        $(".jiazai").on("click",function(){
//            showLoading();
//            hideLoading();
//        }) ;
//    })

</script>
<!-- PNotify -->
<!--<script>-->
    <!--$(document).ready(function() {-->
        <!--new PNotify({-->
            <!--title: "PNotify",-->
            <!--type: "info",-->
            <!--text: "Welcome. Try hovering over me. You can click things behind me, because I'm non-blocking.",-->
            <!--nonblock: {-->
                <!--nonblock: true-->
            <!--},-->
            <!--addclass: 'dark',-->
            <!--styling: 'bootstrap3',-->
            <!--hide: false,-->
            <!--before_close: function(PNotify) {-->
                <!--PNotify.update({-->
                    <!--title: PNotify.options.title + " - Enjoy your Stay",-->
                    <!--before_close: null-->
                <!--});-->

                <!--PNotify.queueRemove();-->

                <!--return false;-->
            <!--}-->
        <!--});-->

    <!--});-->
<!--</script>-->
<!-- /PNotify -->
</body>
</html>