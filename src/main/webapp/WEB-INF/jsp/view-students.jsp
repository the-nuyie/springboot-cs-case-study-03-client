<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.training.java.springboot.casestudy.bean.Student" %>

<%

%>
<html>
    <head>
        <title>View Students</title>

    </head>
    <body>
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
                <%
                List<Student> list = (List<Student>)request.getAttribute("students");
                if(list != null){
                    for(int i=0; i<list.size(); i++){
                        Student student = (Student)list.get(i);
                %>

                        <tr>
                            <td><%=student.getId()%></td>
                            <td><%=student.getFirstname()%></td>
                            <td><%=student.getLastname()%></td>
                            <td><%=student.getRoom()%></td>
                        </tr>

                <%  } // End For
                }   // End if
                %>
            </tbody>
        </table>
    </body>
</html>