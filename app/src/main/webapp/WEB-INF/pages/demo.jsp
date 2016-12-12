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
    <!-- jQuery -->
    <script src="js/jquery-2.0.2.min.js"></script>
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
        <div  class="right_col" style="">
            <div class="x_panel">
                <div class="x_title">
                    <h2>demo</h2>
                    <ul class="nav navbar-right panel_toolbox">
                        <li><a><i class="fa fa-arrow-left"></i> 返回</a>
                        </li>
                    </ul>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <form class="form-horizontal form-label-left">
                        <div class="form-group">
                            <label class="control-label col-md-2">姓名：</label>
                            <div class="col-md-6  col-sm-12 col-xs-12">
                                <input type="text" class="form-control" placeholder="姓名">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-md-2">不编辑：</label>
                            <div class="col-md-6  col-sm-12 col-xs-12">
                                <input type="text" class="form-control" readonly="readonly" placeholder="不可编辑框">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-md-2">备注：<span style="color: red">*</span>
                            </label>
                            <div class="col-md-6 col-sm-12 col-xs-12">
                                <textarea class="form-control" rows="3" placeholder='rows="3"'></textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-md-2">密码框</label>
                            <div class="col-md-6 col-sm-12 col-xs-12">
                                <input type="password" class="form-control tishiborder" value="passwordonetwo">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-md-2">下拉选择</label>
                            <div class="col-md-6 col-sm-12 col-xs-12">
                                <select class="form-control">
                                    <option>Choose option</option>
                                    <option>Option one</option>
                                    <option>Option two</option>
                                    <option>Option three</option>
                                    <option>Option four</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-2 control-label">爱好
                            </label>
                            <div class="col-md-6 col-sm-12 col-xs-12">
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" value=""> 打球
                                    </label>
                                    <label>
                                        <input type="checkbox" value=""> 游泳
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-2 control-label">性别
                            </label>
                            <div class="col-md-6 col-sm-12 col-xs-12">
                                <div class="radio">
                                    <label>
                                        <input type="radio" checked name="iCheck"> 男
                                    </label>
                                    <label>
                                        <input type="radio"  name="iCheck"> 女
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-2 control-label">上传文件
                            </label>
                            <div class="col-md-6 col-sm-12 col-xs-12">
                                <input id="input-1" type="file" class="">
                            </div>
                        </div>
                        <div class="ln_solid"></div>
                        <div class="form-group">
                            <div class="col-md-9 col-sm-9 col-xs-12 col-md-offset-3">
                                <button type="submit" class="btn btn-primary">Cancel</button>
                                <button type="submit" class="btn btn-success">Submit</button>
                                <span class="tishired">信息未填写完整</span>
                            </div>
                        </div>

                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 日期 -->
<script src="../../common/laydate/laydate.js"></script>

<!-- Bootstrap -->
<script src="../vendors/bootstrap/dist/js/bootstrap.min.js"></script>

<!-- iCheck -->
<script src="../vendors/iCheck/icheck.min.js"></script>
<!-- Custom Theme Scripts -->

<script src="js/custom.js"></script>

</body>
</html>