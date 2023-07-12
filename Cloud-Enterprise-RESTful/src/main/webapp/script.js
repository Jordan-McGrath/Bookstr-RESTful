$$(document).ready(function() {
	$("#searchButton").click(function() {
		const searchInput = $("#searchInput").val();
		let apiUrl = "/books"; // default API URL to retrieve all books
		if (searchInput) {
			apiUrl += "?search=" + searchInput; // update API URL to include search input as a query parameter
		}
		$.ajax({
			type: "GET",
			url: apiUrl,
			success: function(books) {
				$("#bookTable tbody").empty();
				$.each(books, function(index, book) {
					$("#bookTable tbody").append("<tr>" +
						"<td>" + book.title + "</td>" +
						"<td>" + book.author + "</td>" +
						"<td>" + book.genres + "</td>" +
						"<td>" + book.characters + "</td>" +
						"<td>" + book.synopsis + "</td>" +
						"<td>" +
						"<button class='update' data-id='" + book.id + "'>Update</button>" +
						"<button class='delete' data-id='" + book.id + "'>Delete</button>" +
						"</td>" +
						"</tr>");
				});
			}
		});
	});
});

function getBookData(books) {
	for (let i = 0; i < books.length; i++) {
		const bookTitle = books[i].title;
		const apiUrl = `https://openlibrary.org/api/books?bibkeys=title:${encodeURIComponent(bookTitle)}&format=json&jscmd=data`;

		fetch(apiUrl)
			.then(response => response.json())
			.then(data => {
				const mediumImageUrl = data[`title:${bookTitle}`].cover.medium;
				document.getElementById("book-cover").src = mediumImageUrl;
			})
			.catch(error => console.error(error));
	}
}



$(document).ready(function() {
	$('input[type="checkbox"]').change(function() {
		var selectedFormats = $('input[type="checkbox"][name="format"]:checked').map(function() {
			return $(this).val();
		}).get();
		$('#selectedFormat').val(selectedFormats.join(','));
	});
});