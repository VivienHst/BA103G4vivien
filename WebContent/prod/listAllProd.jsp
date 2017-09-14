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
<title>�ӫ~���</title>
</head>
<body>
	<table border="1" >
		<tr>
			<th>�ӫ~�s��</th>
			<th>���a�s��</th>
			<th>�ӫ~�W��</th>
			<th>����</th>
			<th>�ͨ�����</th>
			<th>�ק�</th>
			<th>�R��</th>
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
				     <input type="submit" value="�ק�">
				     <input type="hidden" name="prod_no" value="${prodVO.prod_no}">
				     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
				</td>
				<td>
				  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/prod/prod.do">
				    <input type="submit" value="�R��">
				    <input type="hidden" name="prod_no" value="${prodVO.prod_no}">
				    <input type="hidden" name="action"value="delete"></FORM>
				</td>
			</tr>
		</c:forEach>
	</table>

</body>
</html>