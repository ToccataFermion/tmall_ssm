<%--
  Created by IntelliJ IDEA.
  User: 54189
  Date: 2019/8/5
  Time: 16:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.*"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>


<script>
</script>

<title>权限管理</title>


<div class="workingArea">
    <h1 class="label label-info" >权限管理</h1>

    <br>
    <br>

    <div class="listDataTableDiv">
        <table class="table table-striped table-bordered table-hover  table-condensed">
            <thead>
            <tr class="success">
                <th>id</th>
                <th>权限名称</th>
                <th>权限描述</th>
                <th>权限对应的路径</th>
                <th>编辑</th>
                <th>删除</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${ps}" var="p">
                <tr>
                    <td>${p.id}</td>
                    <td>${p.name}</td>
                    <td>${p.desc_}</td>
                    <td>${p.url}</td>
                    <td><a href="admin_editPermission?id=${p.id}"><span class="glyphicon glyphicon-edit"></span></a></td>
                    <td><a href="admin_deletePermission?id=${p.id}"><span class="glyphicon glyphicon-trash"></span></a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="pageDiv">
        <%@include file="../include/admin/adminPage.jsp" %>
    </div>
    <div class="panel panel-warning addDiv" >
        <div class="panel-heading">新增权限</div>>
            <div class="panel-body">
        <table class="addTable">
           <form action="admin_addPermission" method="post">
               <table class="addTable">
                   <tr>
                       <td>
            权限名称: <input type="text" name="name"> <br>
                       </td>
                   </tr>
                   <tr>
                       <td>
            权限描述: <input type="text" name="desc_"> <br>
                       </td>
                   </tr>
                   <tr>
                       <td>
                       对应的url: <input type="text" name="url"> <br><br>
                       </td>
                   </tr>
                   <tr class="submitTR">
                       <td  align="center">
                       <button type="submit" class="btn btn-success">提 交</button>
                      </td>
                   </tr>
               </table>
           </form>
        </table>
            </div>
    </div>


    <%@include file="../include/admin/adminFooter.jsp"%>
