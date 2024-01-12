<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="Models.Point" %>
<%@ page import="Models.Points" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>LB 2</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
<div class="background">

    <main class="main-res">
        <div class="row">
            <div class="results">
                <H3>Результаты проверки</H3>
                <%
                    Points points = (Points) request.getSession().getAttribute("points");
                    if (points == null) {
                %>
                <span>Нет результатов</span>
                <%} else {%>
                <table>
                    <tr>
                        <th><div>R</div></th>
                        <th><div>X</div></th>
                        <th><div>Y</div></th>
                        <th><div>Status</div></th>
                    </tr>
                    </thead>
                    <tbody id="resultsTable">
                    <%for(Point point : points.getPoints()) {%>
                    <tr>
                        <td><%=point.getR()%></td>
                        <td><%=point.getX()%></td>
                        <td><%=point.getY()%></td>
                        <td><%=point.isInArea()%></td>
                    </tr>
                    <%}%>
                    </tbody>
                    </thead>
                </table>
                <%}%>
                <a href="./">Вернуться к форме</a>
            </div>
        </div>
    </main>
    <script type='text/javascript' src="js/graph.js"></script>
    <script src="js/script.js"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</div>
</body>
</html>