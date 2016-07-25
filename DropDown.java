import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.Statement;

import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.ResultSetMetaData;


public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
    public Search() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String loginUser = "root";
        String loginPasswd = "root";
        String loginUrl = "jdbc:mysql://localhost/moviedb";

        response.setContentType("text/html");    // Response mime type

        // Output stream to STDOUT
        PrintWriter out = response.getWriter();
        String title=request.getParameter("input");
        
        //String title="Shrek";


        try
           {
              Class.forName("org.gjt.mm.mysql.Driver");
              Class.forName("com.mysql.jdbc.Driver");
              
              Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
              //Statement statement = dbcon.createStatement();
              String text="%"+title+"%";
              String query="SELECT * FROM movies WHERE title LIKE ? LIMIT 5";
              PreparedStatement ps=dbcon.prepareStatement(query);
              ps.setString(1, text);
              ResultSet rs=ps.executeQuery();
            
              
              while(rs.next()){
            	              		  
            		  out.println(rs.getString("title"));
            		  out.println(", ");
            	  }           	  
          
           }
        catch (SQLException ex) {
            while (ex != null) {
                  out.println ("SQL Exception:  " + ex.getMessage ());
                  ex = ex.getNextException ();
              }  // end while
          }  // end catch SQLException

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
        

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

