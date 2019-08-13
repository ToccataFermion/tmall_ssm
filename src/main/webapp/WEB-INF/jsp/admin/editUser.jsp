<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>


<title>用户编辑</title>
<div class="workingroom">
    <h1 class="label label-info" >用户编辑</h1>

    <br>
    <br>
    <div class="workingArea">
        <div class="panel panel-warning addDiv">
            <div class="panel-heading">新增分类</div>
            <div class= "panel-body">
        <form action="admin_updateUser" method="post">
            <table class="addTable">
            <tr>
            用户名: <input type="text" name="name" value="${user.name}"> <br><br>
            </tr>
            <tr>
            密码: <input type="password" name="password" value="" placeholder="留空就表示不修改密码"> <br><br>
            </tr>
            <tr>
            配置角色:<br>
            </tr>
            <tr>
            <div style="text-align:left;width:300px;margin:0px auto;padding-left:50px">
                <c:forEach items="${rs}" var="r">
                    <c:set var="hasRole" value="false" />
                    <c:forEach items="${currentRoles}" var="currentRole">
                        <c:if test="${r.id==currentRole.id}">
                            <c:set var="hasRole" value="true" />
                        </c:if>
                    </c:forEach>
                    <input type="checkbox"  ${hasRole?"checked='checked'":"" } name="roleIds" value="${r.id}"> ${r.name}<br>
                </c:forEach>
            </div>
            </tr>
            <br>
            <tr><td align="center">
            <input type="hidden" name="id" value="${user.id}">
                <button  class="btn btn-success" type="submit" >修改</button>
            </td>
            </tr>
            </table>
        </form>

        </table>
    </div>
        </div>
    </div>
</div>
<script>
</script>
