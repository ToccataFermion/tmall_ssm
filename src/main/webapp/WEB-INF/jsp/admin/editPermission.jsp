<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

</head>
<body>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>
<title>权限编辑</title>


    <div class="workingArea">
        <div class="panel panel-warning addDiv">
            <div class="panel-heading">权限修改</div>
            <div class= "panel-body">

        <form action="admin_updatePermission" method="post">
            权限名称: <input type="text" name="name" value="${permission.name}"> <br>
            权限描述: <input type="text" name="desc_" value="${permission.desc_}"> <br>
            权限对应的url: <input type="text" name="url"  value="${permission.url}"> <br><br>
            <input type="hidden" name="id" value="${permission.id}">
            <button class="btn btn-success" type="submit" >修改</button>
        </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>