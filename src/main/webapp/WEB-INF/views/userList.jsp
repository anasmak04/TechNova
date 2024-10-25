<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>User List</title>
</head>
<body>
<h1>User List</h1>

<table border="1">
    <tr>
        <th>ID</th>
        <th>Last Name</th>
        <th>First Name</th>
        <th>Identification Document</th>
        <th>Nationality</th>
        <th>Registration Date</th>
        <th>Expiration Date</th>
        <th>Actions</th>
    </tr>
    <c:forEach var="user" items="${users}">
        <tr>
            <td>${user.id}</td>
            <td>${user.lastName}</td>
            <td>${user.firstName}</td>
            <td>${user.identificationDocument}</td>
            <td>${user.nationality}</td>
            <td>${user.registrationDate}</td>
            <td>${user.expirationDate}</td>
            <td>
                <a href="${pageContext.request.contextPath}/users/${user.id}/edit">Edit</a>
                <a href="${pageContext.request.contextPath}/users/${user.id}/delete" onclick="return confirm('Are you sure?')">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>

<a href="${pageContext.request.contextPath}/users/new">Add New User</a>
</body>
</html>
