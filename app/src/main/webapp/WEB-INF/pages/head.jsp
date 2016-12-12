<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="nav_menu">
    <nav class="" role="navigation">
        <div class="nav toggle">
            <a id="menu_toggle"><i class="fa fa-bars"></i></a>
        </div>

        <ul class="nav navbar-nav navbar-right">
            <li class="">
                <a href="javascript:;" class="user-profile dropdown-toggle" data-toggle="dropdown"
                   aria-expanded="false">
                    <img src="resources/images/img.jpg" alt=""><span class="username">...</span>
                    <span class=" fa fa-angle-down"></span>
                </a>
                <ul class="dropdown-menu dropdown-usermenu pull-right">
                    <%--<li><a href="javascript:;"> 设置信息</a></li>--%>
                    <!--<li>-->
                    <!--<a href="javascript:;">-->
                    <!--<span class="badge bg-red pull-right">50%</span>-->
                    <!--<span>Settings</span>-->
                    <!--</a>-->
                    <!--</li>-->
                    <%--<li><a href="internalstaffupdatepwd.html">修改密码</a></li>--%>
                    <li><a href="javascript:;" class="logout"><i class="fa fa-sign-out pull-right"></i>退出</a></li>
                </ul>
            </li>

            <%--<li role="presentation" class="dropdown">--%>
            <%--<a href="javascript:;" class="dropdown-toggle info-number" data-toggle="dropdown" aria-expanded="false">--%>
            <%--<i class="fa fa-envelope-o"></i>--%>
            <%--<span class="badge bg-green">6</span>--%>
            <%--</a>--%>
            <%--<ul id="menu1" class="dropdown-menu list-unstyled msg_list" role="menu">--%>
            <%--<li>--%>
            <%--<a>--%>
            <%--<span class="image"><img src="resources/images/img.jpg" alt="Profile Image"/></span>--%>
            <%--<span>--%>
            <%--<span>John Smith</span>--%>
            <%--<span class="time">3 mins ago</span>--%>
            <%--</span>--%>
            <%--<span class="message">--%>
            <%--Film festivals used to be do-or-die moments for movie makers. They were where...--%>
            <%--</span>--%>
            <%--</a>--%>
            <%--</li>--%>
            <%--<li>--%>
            <%--<a>--%>
            <%--<span class="image"><img src="resources/images/img.jpg" alt="Profile Image"/></span>--%>
            <%--<span>--%>
            <%--<span>John Smith</span>--%>
            <%--<span class="time">3 mins ago</span>--%>
            <%--</span>--%>
            <%--<span class="message">--%>
            <%--Film festivals used to be do-or-die moments for movie makers. They were where...--%>
            <%--</span>--%>
            <%--</a>--%>
            <%--</li>--%>
            <%--<li>--%>
            <%--<a>--%>
            <%--<span class="image"><img src="resources/images/img.jpg" alt="Profile Image"/></span>--%>
            <%--<span>--%>
            <%--<span>John Smith</span>--%>
            <%--<span class="time">3 mins ago</span>--%>
            <%--</span>--%>
            <%--<span class="message">--%>
            <%--Film festivals used to be do-or-die moments for movie makers. They were where...--%>
            <%--</span>--%>
            <%--</a>--%>
            <%--</li>--%>
            <%--<li>--%>
            <%--<a>--%>
            <%--<span class="image"><img src="resources/images/img.jpg" alt="Profile Image"/></span>--%>
            <%--<span>--%>
            <%--<span>John Smith</span>--%>
            <%--<span class="time">3 mins ago</span>--%>
            <%--</span>--%>
            <%--<span class="message">--%>
            <%--Film festivals used to be do-or-die moments for movie makers. They were where...--%>
            <%--</span>--%>
            <%--</a>--%>
            <%--</li>--%>
            <%--<li>--%>
            <%--<div class="text-center">--%>
            <%--<a>--%>
            <%--<strong>See All Alerts</strong>--%>
            <%--<i class="fa fa-angle-right"></i>--%>
            <%--</a>--%>
            <%--</div>--%>
            <%--</li>--%>
            <%--</ul>--%>
            <%--</li>--%>
        </ul>
    </nav>
    <script>
        $(function () {
            $(".logout").on("click", function () {
                $.ajax({
                    url: 'user/logout',
                    type: 'POST',
                    contentType: 'application/json',
                    success: function (data) {
                        window.location.href = "login";
                    },
                    error: function (e) {
                        window.location.href = "login";
                    },
                    beforeSend: function () {
                        showLoading();
                    },
                    complete: function () {
                        hideLoading();
                    }
                });
            })
            $.ajax({
                url: 'user/userinfo',
                type: 'GET',
                contentType: 'application/json',
                cache: true,
                success: function (data) {
                    if (data.statusCode == 0) {
                        $(".username").text(data.data.personName)
                        $(".rolename").text(data.data.role == 1 ? "采购人员" : data.data.role == 2 ? "销售员" : data.data.role == 0 ? "管理员" : "未知");
                    }
                },
                beforeSend: function () {
                    showLoading();
                },
                complete: function () {
                    hideLoading();
                }
            })
        })
        function showLoading() {
            $("body").append('<div class="loadingbg" id="loadingbg"></div>');
        }
        function hideLoading() {
            $("#loadingbg").remove();
        }
        function smallshowLoading() {
            $(".x_panel").append('<div class="loadingbg" id="smallloadingbg"></div>');
        }
        function hidesmallshowLoading() {
            $("#smallloadingbg").remove();
        }
    </script>
</div>


