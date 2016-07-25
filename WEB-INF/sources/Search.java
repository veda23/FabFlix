
/* A servlet to display the contents of the MySQL movieDB database */

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.*;
import javax.naming.*;

public class Search extends HttpServlet
{
    public String getServletInfo()
    {
       return "Servlet connects to MySQL database and displays result of a SELECT";
    }
	public boolean isInteger(String string) {
		try {
			Integer.valueOf(string);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
    // Use http GET
	
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {

        String loginUser = "root";
        String loginPasswd = "root";
        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";

        response.setContentType("text/html");    // Response mime type

        // Output stream to STDOUT
        PrintWriter out = response.getWriter();

//        out.println("<HTML><HEAD><TITLE>MovieDB</TITLE></HEAD>");
//        out.println("<BODY><H1>MovieDB</H1>");

        try
           {
//              Class.forName("org.gjt.mm.mysql.Driver");
 //             Class.forName("com.mysql.jdbc.Driver").newInstance();
//            Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
		 long starttime=System.nanoTime();
		 Context envCtxt = (Context) new InitialContext().lookup("java:comp/env");
                 DataSource ds = (DataSource) envCtxt.lookup("jdbc/moviedb");
                 Connection dbcon = ds.getConnection();


              Statement statement = dbcon.createStatement();
		
		
		String title = request.getParameter("title");
		String year = request.getParameter("year");
		String director = request.getParameter("director");
		//String star = request.getParameter("star");
		String star = "";
		String first = request.getParameter("first");
		String last = request.getParameter("last");
		String order = request.getParameter("order");
		int ipp = Integer.parseInt(request.getParameter("ipp"));
		int page = Integer.parseInt(request.getParameter("page"));
		

		String query = "SELECT * FROM movies WHERE (title LIKE ? OR 1=?) AND (year = ? or 1=?) AND (director LIKE ? OR 1=?) AND id IN (SELECT movie_id FROM stars_in_movies WHERE star_id = ? OR 1=? OR star_id IN ((SELECT id FROM stars WHERE first_name=?)) OR star_id IN ((SELECT id FROM stars WHERE last_name=?)))";
		query=MovieDisplay.sortOnly(query,out,request.getServletPath()+"?"+request.getQueryString());
		//String query = "SELECT * FROM movies WHERE (title LIKE ? OR 1=?) AND (year = ? or 1=?) AND (director LIKE ? OR 1=?) AND id IN (SELECT movie_id FROM stars_in_movies WHERE star_id = ? OR 1=? OR star_id IN (SELECT movie_id FROM stars_in_movies WHERE star_id IN (SELECT id FROM stars WHERE first_name=?)) OR star_id IN (SELECT movie_id FROM stars_in_movies WHERE star_id IN (SELECT id FROM stars WHERE last_name=?)))";
		//ResultSet rs;
		PreparedStatement ps = dbcon.prepareStatement(query);		
		long endtime=System.nanoTime();
		long elapsedtime=endtime-starttime;
		System.out.println("tj "+elapsedtime);
		//title
		ps.setString(1,"");
		ps.setInt(2,1);
		//year
		ps.setInt(3,-1);
		ps.setInt(4,1);
		//director
		ps.setString(5,"");
		ps.setInt(6,1); 
		//star id num
		ps.setInt(7,-1);
		ps.setInt(8,1);
		//star first name
		ps.setString(9,"");
		//star last name
		ps.setString(10,"");
		if(title.length()>0){
			ps.setString(1,"%"+title+"%");
			ps.setInt(2,0);
		}
		if(year.length()>0){
			ps.setInt(3,Integer.parseInt(year));
			ps.setInt(4,0);
		}
		if(director.length()>0){
			ps.setString(5,director);
			ps.setInt(6,0);
		}
		if(star.length()>0 || first.length()>0 || last.length()>0){
			ps.setInt(8,0);
			if(star.length()>0){
				if(isInteger(star))
					ps.setInt(7,Integer.parseInt(star));
			}
			if(first.length()>0){
				ps.setString(9,first);
			}
			if(last.length()>0){
				ps.setString(10,last);
			}
		}

		MovieDisplay.toPage(ps,out,request.getContextPath() + request.getServletPath()+"?"+request.getQueryString(),ipp,page, request.getContextPath());
		

		ps.close();
		statement.close();
		dbcon.close();
	}
        catch (SQLException ex) {
              while (ex != null) {
                    out.println ("SQL Exception:  " + ex.getMessage ());
                    ex = ex.getNextException ();
                }  // end while
            }  // end catch SQLException
	catch(NamingException ne){
 //                       while(ne!=null){
                        out.println("Naming Exception: "+ne.getMessage());
   //                     ne=ne.getNextException();
     //                   }
        }

        catch(java.lang.Exception ex)
            {
                out.println("<HTML>" +
                            "<HEAD><TITLE>" +
                            "MovieDB: Error" +
                            "</TITLE></HEAD>\n<BODY>" +
                            "<P>SQL error in doGet: " +
                            ex.getMessage() + "</P></BODY></HTML>");
                return;
            }
        // out.close();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
	doGet(request, response);
    }
}
