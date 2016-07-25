import java.io.IOException;  
import java.io.PrintWriter;  
  
import javax.servlet.RequestDispatcher;  
import javax.servlet.ServletException;  
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
import javax.servlet.http.HttpSession;    
  
public class Login extends HttpServlet{  
  
    public void doPost(HttpServletRequest request, HttpServletResponse response)    
            throws ServletException, IOException {    
  
        response.setContentType("text/html");    
        PrintWriter out = response.getWriter();    
	String n=request.getParameter("email");
        String p=request.getParameter("password");
	Validate v = new Validate();
		String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
		//System.out.println("gRecaptchaResponse=" + gRecaptchaResponse);
		// Verify CAPTCHA.
		boolean valid = VerifyUtils.verify(gRecaptchaResponse);
	if (!valid) {
	    //errorString = "Captcha invalid!";
		RequestDispatcher RD= request.getRequestDispatcher("incorrectcaptcha.html");
			RD.include(request,response);
			/*out.println("<HTML>" +
				"<HEAD><TITLE>" +
				"MovieDB: Error" +
				"</TITLE></HEAD>\n<BODY>" +
				"<P>Recaptcha WRONG!!!! </P></BODY></HTML>");*/
	    return;
		}
        else{  
        	if(v.validate(n, p)){
	    		HttpSession session = request.getSession();
	    		session.setAttribute(n,p);    
            		session.setAttribute("error","");
	    		session.setAttribute("validated","yes");
            		RequestDispatcher rd = request.getRequestDispatcher("main.html");
	    		rd.forward(request,response);    
        	}    
        	else{    
            		RequestDispatcher rd = request.getRequestDispatcher("incorrect.html");
	    		rd.include(request,response);    
        	}    
 	} 
        out.close();    
    }    
}   
   
