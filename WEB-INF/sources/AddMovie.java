import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class AddMovie extends HttpServlet
{
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {

        String loginUser = "root";
        String loginPasswd = "root";
        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";

        response.setContentType("text/html");    // Response mime type

        // Output stream to STDOUT
        PrintWriter out = response.getWriter();


        try{
		Class.forName("org.gjt.mm.mysql.Driver");
                Class.forName("com.mysql.jdbc.Driver").newInstance();

                Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
               // Statement stmt = dbcon.createStatement();

		String title = request.getParameter("title");
		String year = request.getParameter("year");
		String director = request.getParameter("director");
		String star = request.getParameter("star");
		String genre = request.getParameter("genre");
		

                out.println("<html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"reset.css\"><link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\"><link href='//fonts.googleapis.com/css?family=Crete+Round' rel='stylesheet' type='text/css'><link href=\"https://fonts.googleapis.com/icon?family=Material+Icons\" rel=\"stylesheet\"><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"><title>FabFlix</title></head><body><header><div class=\"wrapper\"><h1>FabFlix<span class=\"color\">.</span></h1><nav><ul><li><a href=\"dashboard.html\"><i class=\"material-icons\">person</i>Logout</a></li><li><a href=\"dashboardmain.html\"><i class=\"material-icons\">settings</i>Options</a></li></ul></nav></div></header><div id=\"image\"><br><br><br><br><br><h4 align=\"center\">");
	
		if(title=="" || title==null|| year=="" || year==null || director=="" || director==null || star=="" || star==null || genre=="" || genre==null){
			RequestDispatcher rd = request.getRequestDispatcher("movieincorrect.html");
			rd.forward(request,response);
		}
		else{	
			add_movie(title,year,director,star,genre,out);	
			out.println("Record successfully inserted into database");
                        out.println("<br><br><br><br>");
                        out.println("Go to");
                        out.println("<a href=\"dashboardmain.html\" style=\"color:rgba(255,255,255,255)\">Options</a>");
		}
	
		out.println("</h4><script src='//cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script></div></body></html>");
	
	
		dbcon.close();
	}
        catch (SQLException ex) {
                while (ex != null) {
                	out.println ("SQL Exception:  " + ex.getMessage ());
                	ex = ex.getNextException ();
                }  // end while
        }  // end catch SQLException

        catch(java.lang.Exception ex){
                out.println("<HTML>" +
                            "<HEAD><TITLE>" +
                            "MovieDB: Error" +
                            "</TITLE></HEAD>\n<BODY>" +
                            "<P>SQL error in doGet: " +
                            ex.getMessage() + "</P></BODY></HTML>");
                return;
        }
        
    }

    public void add_movie(String title, String year, String director, String star, String genre,PrintWriter out){

        String loginUser = "root";
        String loginPasswd = "root";
        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
	
	try{
		Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
		Statement stmt = dbcon.createStatement();
		CallableStatement cs;
		ResultSet rs;
		
	
		rs = stmt.executeQuery("select name from mysql.proc where name='add_movie'");




	
		if(!rs.next()){
			stmt.execute("CREATE PROCEDURE add_movie (IN t VARCHAR(100), y integer, d VARCHAR(100), s_fn VARCHAR(100),s_ln VARCHAR(100), g VARCHAR(100)) BEGIN INSERT INTO movies (title,year,director) values(t,y,d); INSERT INTO stars_in_movies (star_id,movie_id) values( (SELECT id FROM stars WHERE first_name=s_fn AND last_name=s_ln LIMIT 1), (SELECT id FROM movies WHERE title=t LIMIT 1) ); INSERT INTO genres_in_movies (genre_id,movie_id) values ( (SELECT id FROM genres WHERE name=g LIMIT 1), (SELECT id FROM movies WHERE title=t LIMIT 1) ); END");
		}




		String split[] = star.split(" ");
		String fn = split.length >= 2 ? split[0] : "";
		String ln = split.length >= 2 ? split[1] : split[0];
		

		rs = stmt.executeQuery("select * from stars where first_name='"+fn+"' and last_name='"+ln+"' LIMIT 1");



		
		if(!rs.next()){

			ResultSet rs2 = stmt.executeQuery("select name from mysql.proc where name='insert_star'");



			

			if(!rs2.next())
                                stmt.execute("CREATE PROCEDURE insert_star (IN fn VARCHAR(100), ln VARCHAR(100)) BEGIN INSERT INTO stars (first_name,last_name) VALUES(fn,ln) END");



			 
			cs = dbcon.prepareCall("{CALL insert_star(?,?)}");
			cs.setString("fn",fn);
			cs.setString("ln",ln);

			cs.execute();

        
    
		}

		rs = stmt.executeQuery("select * from genres where name='"+genre+"' limit 1");



		

		if(!rs.next()){
			stmt.executeUpdate("insert into genres (name) values("+genre+")");
		}



		

		rs = stmt.executeQuery("select * from movies where title='"+title+"' and year="+year+" and director='"+director+"'");



		

		if(!rs.next()){
			
			cs = dbcon.prepareCall("{CALL add_movie(?,?,?,?,?,?)}");
			cs.setString("t",title);
			cs.setInt("y",Integer.parseInt(year));
			cs.setString("d",director);
			cs.setString("s_fn",fn);
			cs.setString("s_ln",ln);
			cs.setString("g",genre);
			//out.println(cs.toString());
			cs.execute();
		}



		
		else{

			stmt.executeUpdate("insert into stars_in_movies (star_id,movie_id) values( (select id from stars where first_name='"+fn+"' and last_name='"+ln+"'), (select id from movies where title='"+title+"') )");



		

		stmt.executeUpdate("insert into genres_in_movies (genre_id,movie_id) values( (select id from genres where name='"+genre+"'),(select id from movies where title='"+title+"' limit 1) )");



}

			
		
	}
	catch (SQLException ex) {
                while (ex != null) {
                        System.out.println ("SQL Exception:  " + ex.getMessage ());
                        ex = ex.getNextException ();
                }  // end while
        }  // end catch SQLException

        catch(java.lang.Exception ex){
                System.out.println("<HTML>" +
                            "<HEAD><TITLE>" +
                            "MovieDB: Error" +
                            "</TITLE></HEAD>\n<BODY>" +
                            "<P>SQL error in doGet: " +
                            ex.getMessage() + "</P></BODY></HTML>");
        }
	
	//return count;
    } 
}
