<img width="1431" alt="Screenshot 2023-05-26 at 00 23 52" src="https://github.com/Jordan-McGrath/Bookstr-RESTful/assets/63460713/bde1029b-030e-48ff-b18d-bf02cec680ae">

# Bookstr - A RESTful Application

Bookstr is a full-stack RESTful web application that allows users to view, add, edit, and remove books in a virtual bookstore. The application also utilises the Open Library API to fetch book covers and display them alongside book details.

## Features

	•	Browse a collection of books in the virtual bookstore
	•	View detailed information about each book, including the title, author and description in a pop-out modal
	•	Add new books to the bookstore
	•	Edit existing book information, such as the title, author, and description
	•	Remove books from the bookstore
	•	Using Postman, get full CRUD functionality in JSON, XML and Plain Text data formats 


## Technologies Used

Bookstr is built using the following technologies:

###	Frontend:

	•	HTML5 and CSS3
	•	JavaScript (ES6+)
	•	Axios
	•	Bootstrap 4
 
###	Backend:

	•	Mudfoot (MySQL database)
	•	Apache Tomcat 9

## Installation
To set up the Bookstr application locally, follow these steps:

	•	Download the Bookstr .war file from the GitHub repository.
	•	Navigate in your directory to Cloud-Enterprise-RESTful/build/classes/database/config.properties.
	•	In the config.properties file, you will find login details for a MySQL database. 
	•	Change the details for your login and save;
	  ⁃	username=YOUR_USERNAME
	  ⁃	password=YOUR_PASSWORD
	  ⁃	url=YOUR MySQL DATABASE URL
	•	Run create_books_table.sql scheme attached in your workbench.
	•	Deploy the .war file to your preferred Java Servlet Container (e.g., Apache Tomcat 9).


## License

### MIT License with Additional Restrictions

MIT License

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to run the Software for personal, educational, and non-commercial purposes, subject to the following conditions:

1. The content within the Bookstr application, including book details, descriptions, and images, may not be used, reproduced, or distributed for any purpose without explicit permission from the content owners or rights holders.

2. Users are not allowed to modify or remove the Open Library API integration within the Bookstr application without proper authorization.

3. The Bookstr application and its source code may not be used to create derivative works or similar applications for commercial purposes without written consent from the original author.

4. The Bookstr application and its source code may not be distributed, sublicensed, or sold without prior written permission from the original author.

5. The original author of the Bookstr application and associated contributors shall not be liable for any claims, damages, or other liabilities arising from the use or distribution of the Software.

For any other usage or licensing inquiries, please contact the original author.


## Contact

If you have any questions regarding the licensing terms or need clarification, please feel free to contact the original author:

### Email: info@j-mcgrath.com
