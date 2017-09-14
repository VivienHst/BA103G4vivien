<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.prod.model.*"%>
<%
	ProdService prodSvc = new ProdService();
	List<ProdVO> list = prodSvc.getAll();
	pageContext.setAttribute("list", list);
%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<title>商品資料</title>
</head>
<body>
	<table border="1" >
		<tr>
			<th>商品編號</th>
			<th>店家編號</th>
			<th>商品名稱</th>
			<th>豆種</th>
			<th>生豆等級</th>
			<th>修改</th>
			<th>刪除</th>
		</tr>
<%-- 		<%@ include file="page1.file" %>  --%>


<%-- 		<c:forEach var="prodVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>"> --%>
		<c:forEach var="prodVO" items="${list}">	
			<tr align='center' valign='middle'>
				<td>${prodVO.prod_no}</td>
				<td>${prodVO.store_no}</td>
				<td>${prodVO.prod_name}</td>
				<td>${prodVO.bean_type}</td>
				<td>${prodVO.bean_grade}</td>
				<td>
				  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/prod/prod.do">
				     <input type="submit" value="修改">
				     <input type="hidden" name="prod_no" value="${prodVO.prod_no}">
				     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
				</td>
				<td>
				  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/prod/prod.do">
				    <input type="submit" value="刪除">
				    <input type="hidden" name="prod_no" value="${prodVO.prod_no}">
				    <input type="hidden" name="action"value="delete"></FORM>
				</td>
			</tr>
		</c:forEach>
	</table>

</body>
</html>