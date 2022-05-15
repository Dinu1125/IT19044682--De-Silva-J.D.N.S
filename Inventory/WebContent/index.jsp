<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="com.Item"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.4.1.min.js"></script>
<script src="Components/main.js"></script>
</head>
<body>
<body>
	<div class="container">
		<div class="card">
			<div class="card-header bg-info text-light d-flex align-items-center">
				<h1>Item Details</h1>
			</div>
			<div class="card-body">
				<form id="formItem" name="formItem" method="post" action="index.jsp">
				
					  Item Name: <input id="itemName" name="itemName" type="text" class="form-control form-control-sm"><br>
					  Item Qty: <input id="itemQty" name="itemQty" type="text" class="form-control form-control-sm"><br>
					  Item Code: <input id="itemCode" name="itemCode" type="text" class="form-control form-control-sm"><br> 
					  Description: <input id="descripation" name="descripation" type="text" class="form-control form-control-sm"><br>
					 <div class="text-right">
						<input id="btnSave" name="btnSave" type="button" value="SAVE"
							class="btn btn-primary"> <input type="hidden"
							id="hiditemIDSave" name="hiditemIDSave" value="">
					</div>
				</form>
				<div id="alertSuccess" class="alert alert-success" style="margin-top: 15px"></div>
				<div id="alertError" class="alert alert-danger" style="margin-top: 15px"></div>
				<div id="divItemsGrid" class="table-responsive">
					<%
					Item Item1 = new Item();
					out.print(Item1.viewItems());
					%>
				</div>
			</div>

		</div>
	</div>
</body>
</body>
</html>