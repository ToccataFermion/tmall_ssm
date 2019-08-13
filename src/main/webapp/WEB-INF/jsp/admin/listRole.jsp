<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.*"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>


<script>
</script>

<title>角色管理</title>


<div class="workingArea">
    <h1 class="label label-info" >角色管理</h1>

    <br>
    <br>

    <div class="listDataTableDiv">
        <table class="table table-striped table-bordered table-hover  table-condensed">
            <thead>
                <tr  class="success" >
                    <th>id</th>
                    <th>角色名称</th>
                    <th>角色描述</th>
                    <th>权限</th>
                    <th>编辑</th>
                    <th>删除</th>
                </tr>
                <c:forEach items="${rs}" var="r">
                    <tr>
                        <td>${r.id}</td>
                        <td>${r.name}</td>
                        <td>${r.desc_}</td>
                        <td>
                            <c:forEach items="${role_permissions[r]}" var="p">
                                ${p.name} <br>
                            </c:forEach>
                        </td>

                        <td><a href="admin_editRole?id=${r.id}"><span class="glyphicon glyphicon-edit"></span></a></td>
                        <td><a href="admin_deleteRole?id=${r.id}"><span class="glyphicon glyphicon-trash"></span></a></td>
                    </tr>
                </c:forEach>
            </table>

        <div class="pageDiv">
            <%@include file="../include/admin/adminPage.jsp" %>
        </div>
            <div class="panel panel-warning addDiv" >
                <div class="panel-heading">新增角色</div>
                <div class="panel-body">
                <form action="admin_addRole" method="post">
                    <table class="addTable">
                       <tr>
                    角色名称: <input type="text" name="name"> <br>
                       </tr>
                        <tr>
                    角色描述: <input type="text" name="desc_"> <br><br>
                        </tr>
                        <tr class="submitTR">
                        <td colspan="2" align="center">
                            <button type="submit" class="btn btn-success">提 交</button>
                        </td>
                       </tr>
                    </table>
                </form>
                    </div>
            </div>
    </div>


    <%@include file="../include/admin/adminFooter.jsp"%>
