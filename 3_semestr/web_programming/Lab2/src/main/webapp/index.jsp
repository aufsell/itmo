<%@ page import="Models.Point" %>
<%@ page import="Models.Points" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="css/styles.css">
    <link rel="stylesheet" href="css/normalize.css">
</head>

<body>
<div class="container">
    <div class="row no-gutters header">
        <div class="col-md-auto logo">
            <a href="#" class="logo">
                <img src="img/logotypejpg.jpg" alt="itmolab" class="logo">
            </a>
        </div>

        <div class="col-2">

        </div>

        <div class="col-md-auto item ">
            <img src="Icon/Text Square.png" alt="texticon" class="icon">
            <p class="item__up">
                Name<br> <span class="item__down">LabWork2</span>
            </p>
        </div>
        <div class="col-md-auto item">
            <img src="Icon/Widget 6.png" alt="#" class="icon">
            <p class="item__up">
                Group<br> <span class="item__down">Group P3218</span>
            </p>
        </div>
        <div class="col-md-auto item">
            <img src="Icon/User Circle.png" alt="usericon" class="icon">
            <p class="item__up">
                Student<br> <span class="item__down">Levchenko Y.A.</span>
            </p>
        </div>
        <div class="col-md-auto item ">
            <img src="Icon/File.png" alt="fileicon" class="icon">
            <p class="item__up">
                Isu<br> <span class="item__down">367348</span>
            </p>
        </div>
        <div class="col github">
            <a href="https://github.com/aufsell">
                <img src="Icon/Git.png" alt="">
            </a>

        </div>
    </div>
    <section class="section-1">
        <div class="a">
                <span>
                    <h3>Select coordinates</h3>
                    <p>To find out if you are in the area, <br> select coordinates and press the send button</p>
                </span>
            <!-- Input R-->
            <div class="input-R">
                <div class="input-name">
                    <h1>Input R:</h1>
                </div>
                <input type="checkbox" class="checkbox-input-R">
                <label class="custom-checkbox">
                    <div class="checkbox-text">1</div>
                </label>

                <input type="checkbox" class="checkbox-input-R">
                <label class="custom-checkbox">
                    <div class="checkbox-text">2</div>
                </label>

                <input type="checkbox" class="checkbox-input-R">
                <label class="custom-checkbox">
                    <div class="checkbox-text">3</div>
                </label>

                <input type="checkbox" class="checkbox-input-R">
                <label class="custom-checkbox">
                    <div class="checkbox-text">4</div>
                </label>

                <input type="checkbox" class="checkbox-input-R">
                <label class="custom-checkbox">
                    <div class="checkbox-text">5</div>
                </label>
            </div>

            <!--Input X-->
            <div class="input-X">
                <div class="input-name">
                    <h1>Input X:</h1>
                </div>
                <input type="checkbox" class="checkbox-input-X">
                <label class="custom-checkbox">
                    <div class="checkbox-text">-4</div>
                </label>

                <input type="checkbox" class="checkbox-input-X">
                <label class="custom-checkbox">
                    <div class="checkbox-text">-3</div>
                </label>

                <input type="checkbox" class="checkbox-input-X">
                <label class="custom-checkbox">
                    <div class="checkbox-text">-2</div>
                </label>

                <input type="checkbox" class="checkbox-input-X">
                <label class="custom-checkbox">
                    <div class="checkbox-text">-1</div>
                </label>

                <input type="checkbox" class="checkbox-input-X">
                <label class="custom-checkbox">
                    <div class="checkbox-text">0</div>
                </label>

                <input type="checkbox" class="checkbox-input-X">
                <label class="custom-checkbox">
                    <div class="checkbox-text">1</div>
                </label>

                <input type="checkbox" class="checkbox-input-X">
                <label class="custom-checkbox">
                    <div class="checkbox-text">2</div>
                </label>

                <input type="checkbox" class="checkbox-input-X">
                <label class="custom-checkbox">
                    <div class="checkbox-text">3</div>
                </label>

                <input type="checkbox" class="checkbox-input-X">
                <label class="custom-checkbox">
                    <div class="checkbox-text">4</div>
                </label>
            </div>
        </div>
        <div class="b">
            <canvas id="graph"></canvas>
        </div>
    </section>

    <section class="section-2">
        <!--Input Y-->
        <div class="input-Y">
            <div class="input-name">
                <h1>Input Y:</h1>
            </div>
            <input id="yCoordinate" class="y-value-input" required name="yCoordinate" placeholder="[-3 ... 3]">
            <button id="verify-button" class="verify-button "><span>Verify</span></button>
        </div>


        <table id="resultsTable">
            <%
                Points points = (Points) request.getSession().getAttribute("points");
                if (points == null) {
            %>
            <tr style="padding-bottom: 20px">
                <th><div>R</div></th>
                <th><div>X</div></th>
                <th><div>Y</div></th>
                <th><div>Status</div></th>
            </tr>
            <%} else {%>
            <tr style="padding-bottom: 20px">
                <th><div>R</div></th>
                <th><div>X</div></th>
                <th><div>Y</div></th>
                <th><div>Status</div></th>
            </tr>
            <%for(Point point : points.getPoints()) {%>
            <tr data-x="<%= point.getX() %>" data-y="<%= point.getY() %>">
                <td><%=point.getR()%></td>
                <td><%=point.getX()%></td>
                <td><%=point.getY()%></td>
                <td><%=point.isInArea()%></td>
            </tr>
            <%}%>
            <%}%>
        </table>




    </section>


</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
<script type='text/javascript' src="js/graph.js"></script>
<script src="js/script.js"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</body>

</html>
