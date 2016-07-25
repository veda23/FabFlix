import javax.servlet.*;
import java.io.*;
import javax.servlet.http.*;
import java.nio.*;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.Files;
import java.util.EnumSet;
//import org.apache.log4j.Logger;

public class LogFilter implements Filter{
	
	private ServletContext context;
	public void init(FilterConfig fconfig){
		this.context=fconfig.getServletContext();
	} 
	public void doFilter(ServletRequest request,ServletResponse response,FilterChain chain) throws IOException,ServletException{
	//HttpServletRequest req= (HttpServletRequest) request;
	//HttpServletResponse res=(HttpServletResponse) response;
	PrintWriter out=response.getWriter();
	
	try{
		long starttime=System.nanoTime();
		//out.println("In start");
		chain.doFilter(request,response);
		long endtime=System.nanoTime();
		//out.println("In end");
		long elapsedtime= endtime-starttime;
		//out.println("Search"+elapsedtime+",");
		System.out.println("ts "+elapsedtime);
		} 
	catch(IOException e){
		e.printStackTrace();
	}
	}
	public void destroy(){
	}
	
}	
