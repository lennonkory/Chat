$(document).ready(function() {
	$("#add-room-form").click(function() {
		setTimeout(function() {
			$("#room-name").focus();
		}, 1);

		$("#add-room").toggleClass("hidden")
	});

	$("#add-room-button").click(function() {
		addRoom();
	});
	$("#add-room-button-cancel").click(function() {
		$("#add-room").addClass("hidden")
	});

});

$(document).on("keypress", "#room-name", function(e) {
	if (e.which == 13) {
		addRoom();
	}
});

function addRoom() {

	var name = $('#room-name').val()

	if (name.length < 3) {
		alert("Room name must be at least 3 characters");
		return;
	}
	$("#room-name").val('');
	$("#room-name").attr("placeholder", "Name");
	$("#add-room").toggleClass("hidden")
	
	sendAddRoomMessage(name);

}

function addRoomToLobbyTable(name, creator) {

	$("#lobby-table").find('tbody').append("<tr><td>" + name + "</td>"+
			"<td>" + creator + "</td></tr>"

	);

}