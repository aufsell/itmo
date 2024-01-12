package Servlets;

import Models.Point;
import Models.Points;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("areaCheck")
public class AreaCheckServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws  IOException, ServletException {
        try {
            Point point = buildPoint(req);
            Points points = (Points) req.getSession().getAttribute("points");
            if (points == null) {
                points = new Points();
                req.getSession().setAttribute("points", points);
            }
            points.getPoints().add(point);

            sendResponse(point, req, resp);
        } catch (NumberFormatException e) {
            log(e.toString());
            resp.setStatus(403);
        } catch (IOException | ServletException e) {
            resp.setStatus(500);
        }
    }

    private void sendResponse(Point point, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if("submit".equals(req.getParameter("action"))) {
            log("results");
            RequestDispatcher dispatcher = req.getRequestDispatcher("./results.jsp");
            dispatcher.forward(req, resp);
        }

    }

    protected Point buildPoint(HttpServletRequest req) throws NumberFormatException {
        log("x = " + req.getParameter("x"));
        log("y = " + req.getParameter("y"));
        log("r = " + req.getParameter("r"));
        double x = Double.parseDouble(req.getParameter("x"));
        double y = Double.parseDouble(req.getParameter("y"));
        double r = Double.parseDouble(req.getParameter("r"));
        return new Point(x, y, r);
    }
}