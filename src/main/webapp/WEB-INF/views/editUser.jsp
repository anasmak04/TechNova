<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit User</title>
    <script>
        function validateForm() {
            const expirationDate = new Date(document.getElementById('expirationDate').value);
            const registrationDate = new Date('${user.registrationDate}');

            if (expirationDate < registrationDate) {
                alert('Expiration date cannot be before registration date');
                return false;
            }
            return true;
        }

        window.onload = function() {
            const expirationDateInput = document.getElementById('expirationDate');
            expirationDateInput.min = '${user.registrationDate}';
        }
    </script>
</head>
<body>
<h1>Edit User</h1>
<c:if test="${not empty error}">
    <div style="color: red; margin-bottom: 15px;">
            ${error}
    </div>
</c:if>
<form action="${pageContext.request.contextPath}/users/save" method="post" onsubmit="return validateForm()">
    <input type="hidden" name="id" value="${user.id}" />

    <div>
        <label for="lastName">Last Name:</label>
        <input type="text" id="lastName" name="lastName" value="${user.lastName}" required>
    </div>
    <div>
        <label for="firstName">First Name:</label>
        <input type="text" id="firstName" name="firstName" value="${user.firstName}" required>
    </div>
    <div>
        <label for="identificationDocument">Identification Document:</label>
        <input type="text" id="identificationDocument" name="identificationDocument" value="${user.identificationDocument}" required>
    </div>
    <div>
        <label for="nationality">Nationality:</label>
        <input type="text" id="nationality" name="nationality" value="${user.nationality}" required>
    </div>
    <div>
        <label for="expirationDate">Expiration Date:</label>
        <input type="date" id="expirationDate" name="expirationDate" value="${user.expirationDate}" required>
    </div>
    <div>
        <label>Registration Date:</label>
        <span>${user.registrationDate}</span>
    </div>

    <button type="submit">Save Changes</button>
</form>

<a href="${pageContext.request.contextPath}/users">Back to User List</a>
</body>
</html>
