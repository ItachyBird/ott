<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.Connection, java.sql.PreparedStatement, java.sql.ResultSet, java.sql.DriverManager"%>
<% String ids = request.getParameter("id"); %>
<jsp:include page="index_header.jsp" />
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="style.css">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@100;300;400;500;700;900&family=Sen:wght@400;700;800&display=swap" rel="stylesheet">
    <!-- Your other CSS files -->
    <link rel="stylesheet" href="/css/indexstyle.css">
    <title>Movie Design</title>
 
        <script>
        document.addEventListener("DOMContentLoaded", function() {
            const favoriteButtons = document.querySelectorAll('.favorite-button');

            favoriteButtons.forEach(function(button) {
                button.addEventListener('click', function() {
                    if (button.classList.contains('clicked')) {
                        button.classList.remove('clicked');
                    } else {
                        button.classList.add('clicked');
                    }
                });
            });
        });
    </script>
</head>
<body>

<div style="margin-top: 100px;"> <!-- Moves the element 100 pixels below -->
    <!-- Your HTML content here -->
</div>
<%
String Name = null;
String Image = null;
String Link = null;
String Des = null;
String id = null;

Connection conn = null;
PreparedStatement pstm = null;
ResultSet rs = null;

try {
    String url = "jdbc:mysql://localhost:3306/ott";
    String uid = "root";
    String upass = "piyali";
    conn = DriverManager.getConnection(url, uid, upass);
    String query = "SELECT * FROM favourites WHERE MATCH ( email) AGAINST (?)";
    System.out.println("SQL Query: " + query);
    System.out.println("? " + ids);
    pstm = conn.prepareStatement(query);
    pstm.setString(1, ids); // Assuming you want to search for "horror" genre
    rs = pstm.executeQuery();
    
    

    int count = 0; // Counter for posters per row
    while (rs.next()) {
        if (count % 4 == 0) { // Start a new row for every third poster
        	out.println("<div class='movie-list-wrapper'>");
            out.println("<div class='movie-list'>>");
        }

        Name = rs.getString("movie_name");
        System.out.println("Name " + Name);
        Image = rs.getString("poster"); // Assuming the poster column contains image URLs
        //Link = rs.getString("link");
        Des = rs.getString("movie_description");
        id = rs.getString("movie_id");
        
%>
 
<form method="post" action="http://localhost:8024/favorites/add?userEmail=

&movieId=<%= id %>" class="movie-list-item">
    <img class="movie-list-item-img" src="<%= Image %>" alt="">
    <span class="movie-list-item-title"style="color:white"><%= Name %></span>
    <p class="movie-list-item-desc"style="color:white"><%= Des %></p>
    <a href="watch?movie=<%= Link %>&title=<%= Name%>">
        <button class="movie-list-item-button">
            <i class="fas fa-play"></i>
        </button>
    </a>
                            <div class="favorite-button-container">
                            <button type="submit" class="favorite-button">&#10084;</button>
                        </div>
</form>

<%
        count++;
        if (count % 4 == 0) { // Close the row after displaying three posters
            out.println("</div>");
            out.println("</div>");
        }
    }
} catch (Exception e) {
    e.printStackTrace();
} finally {
    // Close connections, statements, and result sets here
    if (rs != null) {
        try {
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    if (pstm != null) {
        try {
            pstm.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    if (conn != null) {
        try {
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
%>
</div>
</div>
<%@ include file="index_footer.jsp" %>
</body>