<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*,java.text.*" %>
<%String path = request.getContextPath();
String basePath = request.getScheme() + "://"
+ request.getServerName() + (request.getServerPort()==80?"":":"+request.getServerPort())
+ path + "/";
int wrapThreshold = 100; %>
<c:set var="basePath" value="<%=basePath%>" />
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>登录</title>
        <script src="<%=basePath%>resources/script/jq/jquery.js" type="text/javascript"></script>
        <script src="<%=basePath%>resources/script/jq/jquery.form.js" type="text/javascript"></script>
        <script src="<%=basePath%>resources/script/jq/jquery.json-2.4.js" type="text/javascript"></script>
        <link href="<%=basePath%>resources/style/login.css" type="text/css" rel="stylesheet" />
    </head>
    <body id="cas" style="overflow: hidden; padding-top:100px;" topmargin="0" leftmargin="0">
        <form id="fm1" method="post">
            <table width="100%" height="100%" cellspacing="0" cellpadding="0" border="0">
                <tbody>
                    <tr>
                        <td valign="middle" height="379" align="center">
                            <table class="logo" width="512" cellspacing="0" cellpadding="2" border="0">
                                <tbody>
                                    <tr>
                                        <td class="zi-head" align="center" colspan="4">数据开放平台运营管理系统</td>
                                    </tr>
                                    <tr>
                                        <td class="color" style="padding-left: 60px;"></td>
                                    </tr>
                                    <tr>
                                        <td class="color">
                                            <input id="username" type="text" autocomplete="false" size="25" value="" accesskey="n" tabindex="1" name="username"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="color">
                                            <input id="password" type="password" autocomplete="off" size="25" value="" accesskey="p" tabindex="2" name="password"/>
                                        </td>
                                    </tr>
                                    <tr style="display: none;">
                                        <td class="color">
                                            <div>
                                                <div style="float:left">
                                                    <input id="randCode" type="text" name="randCode" value=""/>
                                                </div>
                                                <div style="float:left">
                                                    <img class="yzm" width="112" height="48" onclick="changeValidateCode(this)" src="rand!loginRand.action"/>
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="color" align="center" colspan="2">
                                            <input id="login_0" type="button" value="登录"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="bottom" align="center" colspan="2"></td>
                                    </tr>
                                </tbody>
                            </table>
                        </td>
                    </tr>
                </tbody>
            </table>
            <script type="text/javascript">
                String.prototype.Trim = function() {
                    return this.replace(/(^\s*)|(\s*$)/g, "");
                };
                
                //响应回车事件    
                document.onkeydown = function(e) {
                    if (e.keyCode == 13) {
                        $("#login_0").trigger("click");
                    }
                }
                
                //点击“登录”按钮   
                $("#login_0").live("click", function() {
                    var username = $("#username").val().Trim();
                    if (username == '' || username == null) {
                        alert("用户名不能为空");
                        return;
                    }
                    
                    var passwd = $("#password").val();
                    if (passwd == '' || passwd == null) {
                        alert("密码不能为空");
                        return;
                    }
                    
                    //登录操作   
                    $("#fm1").ajaxSubmit({
                        type: 'post',
                        url: 'login/check',
                        success: function(data) {
                            location.assign("<%=basePath%>index");
                        },
                        error: function(XmlHttpRequest, textStatus, errorThrown) {
                            alert(XmlHttpRequest.responseText);
                        }
                    });
                });
            </script>
        </form>
    </body>
</html>
