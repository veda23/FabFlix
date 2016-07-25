import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.*;
import javax.naming.*;

public class executeSQL
{
	public static ResultSet execute(PreparedStatement ps){
		String loginUser = "root";
		String loginPasswd = "root";
		String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
		ResultSet rs;// = new ResultSet();
		try
		{
			//non connection pooling
			//Class.forName("com.mysql.jdbc.Driver").newInstance();
			//Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
			//

			//connection pooling
			Context envCtxt = (Context) new InitialContext().lookup("java:comp/env");
			DataSource ds = (DataSource) envCtxt.lookup("jdbc/moviedb");
			Connection dbcon = ds.getConnection();
			//

			
			Statement statement = dbcon.createStatement();

			rs = ps.executeQuery();
			//ps.close();
			//statement.close();
			//dbcon.close();
			return rs;
		//ps.close();
                       //statement.close();
                       //dbcon.close();
		
}
		catch (SQLException ex) {
			while (ex != null) {
				System.out.println ("SQL Exception:  " + ex.getMessage ());
				ex = ex.getNextException ();
			}  // end while
		}  // end catch SQLException
		catch(java.lang.Exception ex)
		{
			System.out.println("<HTML>" +
				"<HEAD><TITLE>" +
				"MovieDB: Error" +
				"</TITLE></HEAD>\n<BODY>" +
				"<P>SQL error in doGet: " +
				ex.getMessage() + "</P></BODY></HTML>");
		}
		/*finally
		{
			ps.close();
			statement.close();
			dbcon.close();
		}*/
		return null;
	}
	public static ResultSet execute(String query){
		String loginUser = "root";
		String loginPasswd = "root";
		String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
		ResultSet rs;// = new ResultSet();
		//Statement statement = dbcon.createStatement();
		try
		{

			Context envCtxt = (Context) new InitialContext().lookup("java:comp/env");
			DataSource ds = (DataSource) envCtxt.lookup("jdbc/moviedb");
			Connection dbcon = ds.getConnection();

			Statement statement = dbcon.createStatement();
			rs = statement.executeQuery(query);
			
			return rs;
			 //statement.close();
                        //dbcon.close();

		}

		catch (SQLException ex) {
			while (ex != null) {
				System.out.println ("SQL Exception:  " + ex.getMessage ());
				ex = ex.getNextException ();
			}  // end while
		}  // end catch SQLException
		catch(java.lang.Exception ex)
		{
			System.out.println("<HTML>" +
				"<HEAD><TITLE>" +
				"MovieDB: Error" +
				"</TITLE></HEAD>\n<BODY>" +
				"<P>SQL error in doGet: " +
				ex.getMessage() + "</P></BODY></HTML>");
		}
		  /*finally
                {
                        //ps.close();
                        statement.close();
                        dbcon.close();
                }*/

		return null;
	}



	public static void update(String query){
		String loginUser = "root";
		String loginPasswd = "root";
		String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
		ResultSet rs;// = new ResultSet();
		//Statement statement = dbcon.createStatement();
		try
		{
			
			Context envCtxt = (Context) new InitialContext().lookup("java:comp/env");
			DataSource ds = (DataSource) envCtxt.lookup("jdbc/moviedb");
			Connection dbcon = ds.getConnection();

			Statement statement = dbcon.createStatement();
			statement.executeUpdate(query);
			statement.close();
			dbcon.close();
		}
		catch (SQLException ex) {
			while (ex != null) {
				System.out.println ("SQL Exception:  " + ex.getMessage ());
				ex = ex.getNextException ();
			}  // end while
		}  // end catch SQLException
		catch(java.lang.Exception ex)
		{
			System.out.println("<HTML>" +
				"<HEAD><TITLE>" +
				"MovieDB: Error" +
				"</TITLE></HEAD>\n<BODY>" +
				"<P>SQL error in doGet: " +
				ex.getMessage() + "</P></BODY></HTML>");
		}
	}
}
