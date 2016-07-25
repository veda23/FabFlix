import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.text.*;
import java.net.*;

public class header implements Filter
{
	public void init(FilterConfig config){}
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws java.io.IOException, ServletException
	{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HttpServletRequest r = (HttpServletRequest)request;
		
		HttpSession session = r.getSession(false);

		String context = r.getContextPath();
		out.println(// "<body background=\"back.jpg\">"+
				"<header>"+
				"<center>"+
				"<table style=\"width:65%\">"+
				"<td><h1>FabFlix</h1></td>"+
				"<td align=\"right\">"+
				"<a href=\""+context+"/BrowseMain\">Browse</a> "+
				"<a href=\""+context+"/Search.jsp\">Search</a> "+
				"<a href=\""+context+"/Cart\">Cart</a>"+
				"</div></td>"+
				"</table>"+
				"</center>"+
				"</header>") ;
//out.println("<br/>start non filter<br/>");

		if(session!=null){
			String s = (String)session.getAttribute("error");
			if(s!=null&&s.equals("cc_error")){
				out.println("<center><font color=\"red\">Error, invalid payment information.<br>Please try again.</font><br></center>");
				session.setAttribute("error","");
			}
		}
		chain.doFilter(request,response);
//out.println("<br/>end non filter<br/>");
	}
	public void destroy(){}
}
