package Servlets;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet("/controller")
public class ControllerServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            if(validate(req.getParameter("x"),
                    req.getParameter("y"),
                    req.getParameter("r"))){
                    resp.setStatus(HttpServletResponse.SC_TEMPORARY_REDIRECT);
                    resp.setHeader("Location", "./areaCheck");
            }
            else{
                sendError(resp, "Go back to the main page and do not enter incorrect data =)");
            }
        } catch (Exception e) {
            sendError(resp, e.toString());
        }
    }

    private void sendError(HttpServletResponse response, String message) {
        try {
            response.setStatus(422);
            response.getWriter().write(message);
        } catch (IOException e) {
            response.setStatus(500);
        }
    }

    protected boolean validate(String x, String y, String r)  {

        return (validateX(x) && validateY(y) && validateR(r));
    }

    protected boolean validateX(String x){
        if (x == null || x.isEmpty()){
            return false;
        }
        try {
            double xValue = Double.parseDouble(x);
            if((xValue >= -4) && ( xValue <= 4)){
                return true;
            }
        }catch (NumberFormatException e){

        }
        return false;
    }

    protected boolean validateY(String y){
        if (y == null || y.isEmpty()){
            return false;
        }
        try {
            double xValue = Double.parseDouble(y);
            if((xValue >= -4) && ( xValue <= 4)){
                return true;
            }
        }catch (NumberFormatException e){

        }
        return false;
    }

    protected boolean validateR(String r){
        if (r == null || r.isEmpty()){
            return false;
        }
        try {
            int rValue = Integer.parseInt(r);
            List<Integer> values = Arrays.asList(1,2,3,4,5);
            if (values.contains(rValue)) {
                return true;
            }
        }catch (NumberFormatException e){

        }
        return false;
    }

}
