<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>User Form</title>
</head>
<body>
<h1>User Form</h1>

<c:if test="${not empty error}">
    <div style="color: red; margin-bottom: 15px;">
            ${error}
    </div>
</c:if>
<form action="${pageContext.request.contextPath}/users/save" method="post">
    <div>
        <label for="lastName">Last Name:</label>
        <input type="text" id="lastName" name="lastName" required>
    </div>
    <div>
        <label for="firstName">First Name:</label>
        <input type="text" id="firstName" name="firstName" required>
    </div>
    <div>
        <label for="identificationDocument">Identification Document:</label>
        <input type="text" id="identificationDocument" name="identificationDocument" required>
    </div>
    <div>
        <label for="nationality">Nationality:</label>
        <input type="text" id="nationality" name="nationality" required>
    </div>
    <button type="submit">Submit</button>
</form>

<a href="${pageContext.request.contextPath}/users">Cancel</a>
</body>
</html>
