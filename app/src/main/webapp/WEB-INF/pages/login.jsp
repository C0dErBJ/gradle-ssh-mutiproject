<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title></title>

    <!-- Bootstrap -->
    <link href="resources/vendors/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="resources/vendors/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <!-- Animate.css -->
    <link href="https://colorlib.com/polygon/gentelella/css/animate.min.css" rel="stylesheet">
    <!-- Custom Theme Style -->
    <link href="resources/css/custom.css" rel="stylesheet">
    <style>
        .error {
            color: red;
        }
    </style>
</head>

<body class="login">
<div>
    <a class="hiddenanchor" id="signup"></a>
    <a class="hiddenanchor" id="signin"></a>

    <div class="login_wrapper">
        <div class="animate form login_form">
            <div style="text-align: center;margin-top: 20px">
                <img src="resources/images/logo.png" width="100">
            </div>
            <section class="login_content">
                <form id="loginForm" style="margin:0px 10px;margin-top:30px; ">
                    <h1>登录</h1>
                    <div>
                        <input type="text" class="form-control" id="account" placeholder="请输入账户" name="account"
                               value="${account}"/>
                    </div>
                    <div>
                        <input type="password" class="form-control" id="password" placeholder="请输入密码" name="password"
                               value="${password}"/>
                    </div>
                    <div style="width: 100%">
                        <input type="text" class="form-control" style="float: left;width:65%" name="pCode" id="pCode"
                               placeholder="验证码"/>
                        <a href="#" id="cPic" style="float: right;margin: 0;display: inline"><img
                                src="user/captcha"/></a>
                    </div>
                    <div style="overflow: hidden;width: 100%" id="loginArea">
                        <span class="reset_pass" style="float: left;font-size: 14px"><input type="checkbox"
                                                                                            id="rmb" ${rmb?"checked":""}
                                                                                            style=";margin: 3px;float: left;width:15px;height:15px">记住密码</span>
                        <a id="submitButton" class="btn btn-default submit" href="javascript:;"
                           style="float: right;padding: 7px 22px;font-size: 15px;margin: 5px 0px 0px 0px;color: #646464">进入系统</a>

                    </div>

                    <div class="clearfix"></div>
                </form>
            </section>
        </div>
    </div>
</div>
</body>
<script src="resources/js/jquery-2.0.2.min.js"></script>
<script src="resources/js/jquery-validation-1.15.0/dist/jquery.validate.min.js"></script>
<script src="resources/js/jquery-validation-1.15.0/dist/localization/messages_zh.js"></script>
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
    $(function () {
        $("#loginForm").validate({
            errorPlacement: function (error, element) {
                error.appendTo(element.parent());
            }, rules: {
                account: {
                    required: true,
                    maxlength: 50,
                },
                password: {
                    required: true,
                    maxlength: 50,
                    minlength: 6,
                },
                pCode: {
                    required: true,
                    maxlength: 4,
                    minlength: 4,
                }
            },
            messages: {
                account: {
                    required: "请输入用户名",
                },
                password: {
                    required: "请输入密码",
                    minlength: "密码长度不能小于 6 个字母",
                    maxlength: "密码长度不能大于 50 个字母"
                }, pCode: {
                    required: "请输入验证码",
                    maxlength: "验证码为 4 位",
                    minlength: "验证码为 4 位",
                }
            }, submitHandler: function (form) {
                $.ajax({
                    url: 'user/login',
                    type: 'POST',
                    data: {
                        username: $("#account").val(),
                        password: $("#password").val(),
                        captcha: $("#pCode").val(),
                        rmbPwd: $("#rmb").is(":checked")
                    },
                    beforeSend: function () {
                        showLoading();
                    },
                    complete: function () {
                        hideLoading();
                    },
                    success: function (data) {
                        if (data.statusCode != 0) {
                            $("#loginArea").before('<label id="login-error" class="error" for="account">' + data.message + '</label>')
                            $("#cPic").click();
                        } else {
                            window.location.href = "channel";
                        }
                    },
                    error: function (e) {
                        $("#loginArea").before('<label id="login-error" class="error" for="account">发生错误请重试</label>')
                    }
                });

            }
        });
        $("#cPic").on("click", function () {
            $("#cPic img").attr("src", "./user/captcha" + "?t=" + new Date().getMilliseconds());
        })
        $("#submitButton").on("click", function () {
            $("#loginForm").submit();
        });
    })

</script>
</html>