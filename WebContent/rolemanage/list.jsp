<%@page pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="../favicon.ico">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<title>NEUSOFT | OA</title>
<%
pageContext.setAttribute("url",request.getRequestURI());
%>
<!-- 
<base href="${pageContext.request.requestURI}">
 -->
<base href="${url}">

<link rel="stylesheet" href="../common.css">
<link rel="stylesheet"
	href="../thirdparty-lib/fontawesome/font-awesome.min.css">
<script src="../thirdparty-lib/jquery/jquery.min.js"></script>
<script src="../common.js"></script>
<link rel="stylesheet" href="loading.css" />
</head>
<body class="layout-vbox">
	<nav>
		<ol class="breadcrumb">
			<li><a href="javascript:window.top.dashboard()"
				class="fa fa-dashboard">首页 </a></li>
			<li>系统管理</li>
			<li><b>系统角色管理</b></li>
		</ol>
	</nav>
	<menu>
		<a href="add.html" class="btn">新增</a>
		<span class="grow"></span>
		<span class="input-group">
			<form id="findForm">
				<input id="findKeyInput" name="key" type="text" maxlength="12"
					placeholder="Search">
				<button id="findBtn">Go</button>
			</form>
		</span>
	</menu>
	<section class="layout-table">
		<header>
			<table class="data">
				<thead>
					<tr>
						<th>#</th>
						<th>操作</th>
						<th>代号</th>
						<th>名称</th>
						<th>创建时间</th>
						<th>备注</th>
					</tr>
				<colgroup>
					<col width="50" align="right">
					<col width="100">
					<col width="200">
					<col width="150">
					<col width="150">
					<col>
				</colgroup>
				</thead>
			</table>
		</header>
		<main>
		<table class="data">
			
			<tbody>
			<c:if var="found" test="${not empty requestScope.rows}" scope="page">
				<c:forEach var="r" items="${requestScope.rows}" varStatus="vs">
					<tr>
						<td>${vs.count}</td>
						<td>
							<a href="check.html#${r.id}">查看</a>
						</td>
						<td>
							${r.code}
						</td>
						<td>${r.name}</td>
						<td>
						<fmt:formatDate value="${r.createTime}" pattern="yyyy年MM月dd日 HH:mm:ss"/>
						</td>
						<td>${r.remark}</td>
					</tr>	
				</c:forEach>
				
				</c:if>
				<c:if test="${!found}">
					<tr>
						<td colspan="9999">抱歉，未找到相关数据
						${message}
						</td>
					</tr>
				</c:if>
			</tbody>
		</table>
		</main>
		<footer>
			共 <b>
			${fn:length(requestScope.rows)}
			</b> 条数据
		</footer>
	</section>

	<script type="text/javascript" src="list.js"></script>
</body>
</html>