
/* A servlet to display the contents of the MySQL movieDB database */

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.simple.JSONObject;

public class AndroidSearch extends HttpServlet
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
	int count=0;

        // Output stream to STDOUT
        PrintWriter out = response.getWriter();
	JSONObject obj = new JSONObject();
	ArrayList movies = new ArrayList();
        try
           {
              Class.forName("org.gjt.mm.mysql.Driver");
              Class.forName("com.mysql.jdbc.Driver").newInstance();

              Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
              Statement statement = dbcon.createStatement();

		String query = request.getParameter("query");
		
		String sql = "select * from movies where (title LIKE ? OR 1=?)";
		PreparedStatement ps = dbcon.prepareStatement(sql);		

		ps.setString(1,"");
		ps.setInt(2,1);

		if(query.length()>0){
			ps.setString(1,"%"+query+"%");
			ps.setInt(2,0);
		}
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()){
/*			obj.put("Title",rs.getString("title"));
			 StringWriter jsonout = new StringWriter();
                        obj.writeJSONString(jsonout);

                        String jsonText = jsonout.toString();
                        out.println(jsonText);
*/			
			String movie = rs.getString("title");
			movies.add(movie);
			count=1;
		}

		if(count==0)
			out.println("No movies found");
		else{
			for(int i=0; i<movies.size(); i++){
				out.println("Title: " + movies.get(i) + "\n");
			//	out.println("");
			}
		}
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
