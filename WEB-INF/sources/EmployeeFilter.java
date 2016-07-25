import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;



public class EmployeeFilter implements Filter{

	private ServletContext context;

	public void init(FilterConfig fConfig) throws ServletException {
        	this.context = fConfig.getServletContext();
        	this.context.log("AuthenticationFilter initialized");
    	}
     
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
 
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
         
        String uri = req.getRequestURI();
        this.context.log("Requested Resource::"+uri);
         
        HttpSession session = req.getSession(false);
         
        if(session == null || (String)session.getAttribute("validated")!="yes"){
            this.context.log("Unauthorized access request");
            res.sendRedirect("dashboard.html");
        }else{
            // pass the request along the filter chain
            chain.doFilter(request, response);
        }
         
         
    }
 
     
 
    public void destroy() {
        //close any resources here
    }
 
}
