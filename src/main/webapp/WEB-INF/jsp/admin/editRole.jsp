<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

</head>
<body>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>

<title>角色编辑</title>



<div class="workingArea">
    <div class="panel panel-warning addDiv">
     <div class="panel-heading">角色修改</div>
       <div class= "panel-body">
         <form action="admin_updateRole" method="post">
            <table class="addTable">
                <tr>
                    <td >
            角色名字: <input type="text" name="name" value="${role.name}"> <br>
                    </td>
                </tr>
                <tr>
                    <td>
                        角色描述:<input type="text" name="desc_" value="${role.desc_}" > <br><br>
                    </td>
                </tr>
                <tr>
                    <td>
            配置权限:<br>
                    </td>
                </tr>
                <tr>
                    <td>
            <div style="text-align:left;width:300px;margin:0px auto;padding-left:50px">
                <c:forEach items="${ps}" var="p">
                    <c:set var="hasPermission" value="false" />
                    <c:forEach items="${currentPermissions}" var="currentPermission">
                        <c:if test="${p.id==currentPermission.id}">
                            <c:set var="hasPermission" value="true" />
                        </c:if>
                    </c:forEach>
                    <input type="checkbox"  ${hasPermission?"checked='checked'":"" } name="permissionIds" value="${p.id}"> ${p.name}<br>
                </c:forEach>
            </div>
                    </td>
                </tr>
            <tr >
                <td align="center">
             <input type="hidden" name="id" value="${role.id}">
                <button type="submit" class="btn btn-success">修改</button>
                </td>
            </tr>
            </table>
        </form>  
    </div>
    </div>
</div>
</body>
</html>