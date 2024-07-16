<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.training.java.springboot.casestudy.bean.Student" %>

<%

%>
<html>
    <head>
        <title>Input Student</title>

    </head>
    <body>
        <form method="POST" action="./add-student">
        <table border="1">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Room</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>auto</td>
                    <td><input type="text" name="firstname"></td>
                    <td><input type="text" name="lastname"></td>
                    <td><input type="text" name="room"></td>
                </tr>
            </tbody>
            <input type="submit" value="Confirm">
        </table>
        </form>
    </body>
</html>