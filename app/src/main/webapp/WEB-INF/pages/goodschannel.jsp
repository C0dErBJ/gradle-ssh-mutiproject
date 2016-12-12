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
    <link href="resources/vendors/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="resources/vendors/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <link href="resources/vendors/iCheck/skins/flat/green.css" rel="stylesheet">
    <link href="resources/css/sweetalert.css" rel="stylesheet">
    <script src="resources/js/jquery-2.0.2.min.js"></script>
    <!-- Custom Theme Style -->
    <link href="resources/css/custom.css" rel="stylesheet">
</head>
<body class="nav-md">
<div class="container body">
    <div class="main_container">
        <div class="col-md-3 left_col" id="leftbody"></div>
        <script>
            $.ajaxSetup({
                async: false
            });
        </script>
        <script> $.get("leftbody", function (data) {
            $("#leftbody").html(data);
        });</script>
        <div class="top_nav" id="head"></div>
        <script> $.get("head", function (data) {
            $("#head").html(data);
        });</script>
        <script>
            $.ajaxSetup({
                async: true
            });
        </script>
        <div class="right_col" role="main">
            <!--页面开发位置-->
            <div class="">
                <div class="row">
                    <div class="col-md-12 col-sm-12 col-xs-12">
                        <div class="x_panel ">
                            <div class="x_title">
                                <h2 class="col-lg-3 col-md-6 col-sm-6 col-xs-6" style="float: left">商品对应渠道</h2>
                                <%--<ul class="nav navbar-right panel_toolbox" style="float: right">--%>
                                <%--<li><a class=""><i class="fa fa-trash-o"></i> 删除</a>--%>
                                <%--</li>--%>
                                <%--<li><a href="demo.jsp"><i class="fa fa-plus"></i> 新增</a>--%>
                                <%--</li>--%>
                                <%--</ul>--%>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content">
                                <div class="table-responsive" style=" overflow-y: hidden">
                                    <table class="table table-bordered bulk_action" style="min-width:1000px;">
                                        <thead>
                                        <tr class="headings" id="channelHead">
                                            <th></th>

                                        </tr>
                                        </thead>
                                        <tbody id="productbody">

                                        </tbody>
                                    </table>
                                </div>
                                <div class="text-right">
                                    <div class="row" id="pagination">

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
    z-index: 9999">保存成功!
    </div>

</div>

<script src="resources/js/sweetalert/sweetalert.min.js"></script>
<!-- Bootstrap -->
<script src="resources/vendors/bootstrap/dist/js/bootstrap.min.js"></script>
<!-- iCheck -->
<script src="resources/vendors/iCheck/icheck.min.js"></script>
<!-- PNotify -->
<script src="resources/vendors/pnotify/dist/pnotify.js"></script>
<script src="resources/vendors/pnotify/dist/pnotify.buttons.js"></script>
<script src="resources/vendors/pnotify/dist/pnotify.nonblock.js"></script>
<!-- Custom Theme Scripts -->
<script src="resources/js/custom.js"></script>
<script src="resources/js/page/page.js"></script>

<script>
    function setPC(data) {
        for (var i = 0; i < data.length; i++) {
            $(".channnel" + data[i].channelid + ".product" + data[i].productid).attr("checked", true);
        }
    }
    function setChannel(data) {
        for (var i = 0; i < data.length; i++) {
            $("#channelHead").append("<th class='citem' key='" + data[i].key + "' cid='" + data[i].id + "' value='channel" + data[i].id + "'>" + data[i].channelname + "</th>")
        }
        $(".citem").on("click", function () {
            if ($(this).hasClass("selected")) {
                unSelectAll($(this).attr("value"));
                $(this).removeClass("selected");
            } else {
                selectAll($(this).attr("value"));
                $(this).addClass("selected");
            }
            submitCheck($(".checkItem:checked"))
        })
    }
    function selectAll(clazz) {
        $(".checkItem." + clazz).prop("checked", true)
    }
    function unSelectAll(clazz) {
        $(".checkItem." + clazz).prop("checked", false)
    }
    function setProduct(data) {
        var checkLength = $("#channelHead th").length;
        var tds = "";
        $("#productbody").empty();
        for (var i = 0; i < data.length; i++) {
            for (var j = 1; j < checkLength; j++) {
                tds += '<td class=""><input type="checkbox" cid="' + $("#channelHead th:eq(" + j + ")").attr("cid") + '" pid="' + data[i].id + '" class="checkItem flat channel' + $("#channelHead th:eq(" + j + ")").attr("cid") + " channelk" + $("#channelHead th:eq(" + j + ")").attr("key") + " product" + data[i].id + '" name="table_records"></td>';
            }
            $("#productbody").append(' <tr class="pointer">\
                    <td class="pitem" value="product' + data[i].id + '">' + data[i].productName + '</td>\
                   ' + tds + '\
                    </tr>')
            tds = "";
        }
        $(".pitem").on("click", function () {
            if ($(this).hasClass("selected")) {
                unSelectAll($(this).attr("value"));
                $(this).removeClass("selected");
            } else {
                selectAll($(this).attr("value"));
                $(this).addClass("selected");
            }
            submitCheck($(".checkItem:checked"))
        })
        $(".checkItem").on("click", function () {
            submitCheck($(this));
        })
    }
    function submitCheck(ele) {
        var checkitem = []
        ele.each(function (i, a) {
            var oi = {};
            oi.cid = $(a).attr("cid");
            oi.pid = $(a).attr("pid");
            checkitem.push(oi)
        })
        $.ajax({
            url: 'channel',
            type: 'POST',
            data: JSON.stringify(checkitem),
            contentType: "application/json",
            beforeSend: function () {
                smallshowLoading();
            },
            complete: function () {
                hidesmallshowLoading();
            }
        });
    }
    function initPC(pageIndex, pageSize) {
        $.ajax({
            url: 'channel/product?pageIndex=' + pageIndex + '&pageSize=' + pageSize,
            type: 'GET',
            contentType: "application/json",
            beforeSend: function () {
                smallshowLoading();
            },
            complete: function () {
                hidesmallshowLoading();
            },
            success: function (datas) {
                if (datas.statusCode == 0) {
                    setProduct(datas.data.resultList);
                    var id = [];
                    $("#pagination").pagination("render", datas.data.rowCount)
                    for (var i = 0; i < datas.data.resultList.length; i++) {
                        id.push(datas.data.resultList[i].id);
                    }
                    $.ajax({
                        url: 'channel/relation',
                        type: 'POST',
                        data: {"id": id},
                        beforeSend: function () {
                            smallshowLoading();
                        },
                        complete: function () {
                            hidesmallshowLoading();
                        },
                        success: function (data) {
                            if (data.statusCode == 0) {
                                setPC(data.data);
                            }
                        }
                    });
                }
            }
        });
    }

    function init() {
        $.ajax({
            url: 'channel/detail',
            type: 'GET',
            contentType: "application/json",
            beforeSend: function () {
                smallshowLoading();
            },
            complete: function () {
                hidesmallshowLoading();
            },
            success: function (data) {
                if (data.statusCode == 0) {
                    setChannel(data.data);
                    initPC(1, 15);
                }
            }
        });
    }

    $(function () {
        $("#pagination").pagination({
            pageIndexName: 'pageIndex', // 分页参数名称
            pageSize: 15,
            prevText: '<i class="fa fa-angle-double-left">',
            nextText: '<i class="fa fa-angle-double-right">',
            containerStyle: "margin-top: 0px;float: right;margin-right: 11px",
            onChange: function (pi, ps) {
                initPC(pi, ps);
                console.log(pi + "..." + ps);
            }
        })
        init();

    })
</script>


</body>
</html>