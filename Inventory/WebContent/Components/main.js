$(document).ready(function() {

	$("#alertSuccess").hide();
	$("#alertError").hide();

});

function validateItemForm() {
	

	// NAME-------------------------------
	if ($("#itemName").val().trim() == "") {
		return "Insert Item Name";
	}
	
	let itemQty = $("#itemQty").val().trim();
	if (!$.isNumeric(itemQty)) {
		return "Insert Item Qty.";
	}
	if ($("#itemCode").val().trim() == "") {
		return "Insert Item Code.";
	}
	
	if ($("#descripation").val().trim() == "") {
		return "Insert Item Descrption.";
	}

	return true;

}

//Save Func
function onInventorySaveComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divItemsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}
	$("#hiditemIDSave").val("");
	$("#formItem")[0].reset();
}


// Save Btn
$(document).on("click", "#btnSave", function(event) {
	// Clear alerts---------------------  
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();

	// Form validation-------------------  
	var status = validateItemForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}

	// If valid------------------------  
	var type = ($("#hiditemIDSave").val() == "") ? "POST" : "PUT";

	$.ajax(
		{
			url: "Item",
			type: type,
			data: $("#formItem").serialize(),
			dataType: "text",
			complete: function(response, status) {
				onInventorySaveComplete(response.responseText, status);
			}
		});
});


// UPDATE CLICK
$(document).on("click", ".btnUpdate", function(event) {
	$("#hiditemIDSave").val($(this).closest("tr").find('#hiditemIDUpdate').val());
	$("#itemName").val($(this).closest("tr").find('td:eq(0)').text());
	$("#itemQty").val($(this).closest("tr").find('td:eq(1)').text());
	$("#itemCode").val($(this).closest("tr").find('td:eq(2)').text());
	$("#descripation").val($(this).closest("tr").find('td:eq(3)').text());
	
});


//Delete Func
function onItemDeleteComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divItemsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}


// DELETE Click
$(document).on("click", ".btnRemove", function(event) {
	$.ajax(
		{
			url: "Item",
			type: "DELETE",
			data: "itemID=" + $(this).data("itemID"),
			dataType: "text",
			complete: function(response, status) {
				onItemDeleteComplete(response.responseText, status);
			}
		});
});
