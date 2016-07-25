import java.io.IOException;  
import java.io.PrintWriter;  
  
import javax.servlet.RequestDispatcher;  
import javax.servlet.ServletException;  
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
import javax.servlet.http.HttpSession;    
  
public class EmployeeLogin extends HttpServlet{  
  
    public void doPost(HttpServletRequest request, HttpServletResponse response)    
            throws ServletException, IOException {    
  
        response.setContentType("text/html");    
        PrintWriter out = response.getWriter();    
          
        String n=request.getParameter("email");    
        String p=request.getParameter("password");   
          
        ValidateEmployee v = new ValidateEmployee();

        if(v.validate(n, p)){
	    HttpSession session = request.getSession(true);
	    session.setAttribute("validated","yes");
	    RequestDispatcher rd = request.getRequestDispatcher("dashboardmain.html");
	    rd.forward(request,response);    
        }    
        else{    
            RequestDispatcher rd = request.getRequestDispatcher("wrong.html");
	    rd.forward(request,response);    
        }    
  
        out.close();    
    }    
}   
