<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" import="java.util.*"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>


<script>
</script>

<title>用户管理</title>


<div class="workingArea">
	<h1 class="label label-info" >用户管理</h1>

	<br>
	<br>

	<div class="listDataTableDiv">
		<table class="table table-striped table-bordered table-hover  table-condensed">
			<thead>
			<tr class="success">
				<th>ID</th>
				<th>用户名称</th>
				<th>用户密码</th>
				<th>加密盐</th>
				<th>角色</th>
				<th>编辑</th>
				<th>删除</th>
			</tr>
			</thead>
			<tbody>
			<c:forEach items="${us}" var="u">
				<tr>
					<td>${u.id}</td>
					<td>${u.name}</td>
					<td>${fn:substring(u.password, 0, 5)}...</td>
					<td>${fn:substring(u.salt, 0, 5)}...</td>
					<td>
						<c:forEach items="${user_roles[u]}" var="r">
							${r.name} <br>
						</c:forEach>
					</td>
					<td><a href="admin_editUser?id=${u.id}"><span class="glyphicon glyphicon-edit"></span></a></td>
					<td><a href="admin_deleteUser?id=${u.id}"><span class="glyphicon glyphicon-trash"></span></a></td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</div>

	<div class="pageDiv">
		<%@include file="../include/admin/adminPage.jsp" %>
	</div>
	<div class="workingArea">
		<div class="workingArea">
			<div class="panel panel-warning addDiv">
				<div class="panel-heading">新增用户</div>
				<div class= "panel-body">
					<form action="admin_addUser" method="post">
						<table class="addTable">
							<tr>
								用户名: <input type="text" name="name" > <br><br>
							</tr>
							<tr>
								密码: <input type="password" name="password"  placeholder="输入密码"> <br><br>
							</tr>
							<br>
							<tr><td align="center">
								<button  class="btn btn-success" type="submit" >注册</button>
							</td>
							</tr>
						</table>
					</form>

					</table>
				</div>
			</div>
		</div>
	</div>


<%@include file="../include/admin/adminFooter.jsp"%>
