<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
 
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>List Customers</title>
	
	<!-- reference style sheet -->
	<link type="text/css"
		  rel="stylesheet"
		  href="${pageContext.request.contextPath}/resources/css/style.css" />

</head>
<body>
	<div id="wrapper">
		<div id="header">
			<h2>CRM - Customer Relationship Manager</h2>
		</div>
	</div>
	<div id="container">
		<div id="content">
		
		<!-- put new button: Add Customer -->
		<input type="button" value="Add Customer"
			onclick="window.location.href='showFormForAdd'; return false;"
			class="add-button"
		/>
		
		<form:form action="search" method="GET">
			
			Search customer: <input type="text" name="theSearchName"/>
			<input type="submit" value="Search" class="add-button"/>
		</form:form> 
		
		 <!-- add our html table here -->
		 
		 <table>
		 	<tr>
		 		<th>First Name</th>
		 		<th>Last Name</th>		 
		 		<th>Email</th>
		 		<th>Action</th>
		 	</tr>
		 	<!-- Loop over and print our customers -->
		 	<c:forEach var="tempCustomer" items="${customers}">
		 	
		 		<!-- construct an "update" link with customer id (for each customer)-->	
		 		<c:url var="updateLink" value="/customer/showFormForUpdate">
		 			<c:param name="customerId" value="${tempCustomer.id }"/>
		 		</c:url>
		 		
		 		<!-- construct a "delete" link with customer id (for each customer)-->	
		 		<!-- Embedds customerId in the link param. -->
		 		<c:url var="deleteLink" value="/customer/delete">
		 			<c:param name="customerId" value="${tempCustomer.id }"/>
		 		</c:url>
		 		
		 		<tr>
		 			<td>${tempCustomer.firstName}</td>
		 			<td>${tempCustomer.lastName}</td>
		 			<td>${tempCustomer.email}</td>
		 			<td>
		 				<!-- display update link in table data -->
		 				<a href="${updateLink}">Update</a>
		 				| 
		 				<a href="${deleteLink}"
		 				onclick="if ( !(confirm('Are you sure you want to delete this?'))) return false">Delete</a>
		 			</td>
		 		</tr>
		 		
		 	</c:forEach>
		 </table>
		</div>
	</div>
</body>
</html>