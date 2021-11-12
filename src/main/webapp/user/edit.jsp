<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 11/11/2021
  Time: 2:38 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit/Update User Info</title>
</head>
<body>
    <center>
        <h1>User Management</h1>
        <h2><a href="/user?action=users">Back to list</a></h2>
    </center>
    <div align="center">
        <form method="post">
            <table border="1" cellpadding="5">
                <caption>
                    Edit User
                </caption>
                <c:if test="${user!=null}">
                    <input type="hidden" name="id" id="<c:out value="${user.id}"/>"/>
                </c:if>
                <tr>
                    <th>User Name</th>
                    <td><input type="text" name="name" id="name" size="45" value="${user.name}"></td>
                </tr>
                <tr>
                    <th>User Email</th>
                    <td><input type="text" name="email" id="email" size="45" value="${user.email}"></td>
                </tr>
                <tr>
                    <th>User country</th>
                    <td><input type="text" name="country" id="country" size="45" value="${user.country}"></td>
                </tr>
            </table>
            <input type="submit" name="sub" id="sub" value="chap nhan">
        </form>
    </div>
</body>
</html>
