
/* A servlet to display the contents of the MySQL movieDB database */
import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class MovieDisplay
{
	private static int sortType;
	public static int countRS(ResultSet rs) throws Exception
	{
		int count = -1;
		if(rs!= null){
			rs.last();
			count = rs.getRow();
			rs.beforeFirst();
		}
		return count;
	}
	public static void printHeader(PrintWriter out)
	{
                out.println("<head>");
		//styling imports
		out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"reset.css\">"+
			"<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">"+
			"<link href=\"//fonts.googleapis.com/css?family=Crete+Round\" rel=\"stylesheet\" type=\"text/css\">"+
			"<link href=\"https://fonts.googleapis.com/icon?family=Material+Icons\" rel=\"stylesheet\">"+
			"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">");
			
		//popup css formatting
		out.println("<style type='text/css'>div.pop-up {  display:none;  text-align: left;  position: absolute;background: #eeeeee;  color: #000000;  border: 1px solid #1a1a1a;}</style>\n");
		//get jquery
		out.println("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.8.0/jquery.min.js\" type=\"text/javascript\"></script>\n");

		//popup javascript
		out.println("<script type='text/javascript' src='popup.js'></script>");
		out.println("<title>FabFlix</title></head>\n");
		
		//set headers
		out.println("<body><header><div class=\"wrapper\"><h1>FabFlix<span class=\"color\">.</span></h1> <nav><ul><li><a href=\"login.html\"><i class=\"material-icons\">person</i>Login</a></li><li><a href=\"Cart\"><i class=\"material-icons\">shopping_cart</i>Cart</a></li><li><a href=\"Search.jsp\"><i class=\"material-icons\">search</i>Search</a></li><li><a href=\"BrowseMain\"><i class=\"material-icons\">folder</i>Browse</a></li></ul></nav></div>"+
			"</header>\n");

		out.println("<div id='image'>");
	}
	public static void toPage(PreparedStatement ps, PrintWriter out, String query, int ipp, int page, String context) throws Exception
	{
		printHeader(out);
		String q = ps.toString();
//                out.println("<div id=\"image\"><table style=\"width=65%\">");
		ResultSet rs = executeSQL.execute(ps);
		if(countRS(rs)>0){
			sortPrint(out,query,page);
			printStuff(rs,out,query,ipp,page,context);
//			out.println("</table></center>");
			pagination(rs,out,query,ipp,page);
		}
		else
			out.println("<label style=\"font-size:18px; font-family:Crete Round; align:center; color:#444\">No matches found!</label>");
	}
	public static void toPage(String sqlQuery, PrintWriter out, String query, int ipp, int page, String context) throws Exception
	{
		ResultSet rs;// = new ResultSet();
		printHeader(out);
		//out.println("<center><table style=\"width:65%\">");
		String q = sortOnly(sqlQuery,out,query);
		rs = executeSQL.execute(q);
		if(countRS(rs)>0){
			sortPrint(out,query,page);
			printStuff(rs,out,query,ipp,page,context);
			
			pagination(rs,out,query,ipp,page);
		}
		else
			out.println("<label style=\"font-size:18px; font-family:Crete Round; align:center; color:#444\">No matches found!</label>");
	}
	public static String sortOnly(String sqlQuery, PrintWriter out, String query) throws Exception{
		String q = sqlQuery + " ORDER BY ";
		sortType = -1;
		sortType = query.indexOf("t_asc") != -1 ? 1 : sortType;
		sortType = query.indexOf("t_dsc") != -1 ? 2 : sortType;
		sortType = query.indexOf("y_asc") != -1 ? 3 : sortType;
		sortType = query.indexOf("y_dsc") != -1 ? 4 : sortType;
		String t = "Title";
		String y = "Year";
		switch(sortType){
			case (1):
				q += "title";
				break;
			case (2):
				q += "title desc";
				break;
			case (3):
				q += "year";
				break;
			case (4):
				q += "year desc";
				break;
		}
		return q;
	}
	public static void sortPrint(PrintWriter out, String query, int page) throws Exception
	{
		//String q = sqlQuery + " ORDER BY ";
		sortType = -1;
		sortType = query.indexOf("t_asc") != -1 ? 1 : sortType;
		sortType = query.indexOf("t_dsc") != -1 ? 2 : sortType;
		sortType = query.indexOf("y_asc") != -1 ? 3 : sortType;
		sortType = query.indexOf("y_dsc") != -1 ? 4 : sortType;
		String t = "Title";
		String y = "Year";
		switch(sortType){
			case (1):
				t = "<a href=\""+query.replaceAll("t_asc","t_dsc")+"\">"+t+"</a>";
				y = "<a href=\""+query.replaceAll("t_asc","y_asc")+"\">"+y+"</a>";
				t += " &#8593";
				y += " &#8597";
				break;
			case (2):
				t = "<a href=\""+query.replaceAll("t_dsc","t_asc")+"\">"+t+"</a>";
				y = "<a href=\""+query.replaceAll("t_dsc","y_asc")+"\">"+y+"</a>";
				t += " &#8595";
				y += " &#8597";
				break;
			case (3):
				t = "<a href=\""+query.replaceAll("y_asc","t_asc")+"\">"+t+"</a>";
				y = "<a href=\""+query.replaceAll("y_asc","y_dsc")+"\">"+y+"</a>";
				y += " &#8593";
				t += " &#8597";
				break;
			case (4):
				t = "<a href=\""+query.replaceAll("y_dsc","t_asc")+"\">"+t+"</a>";
				y = "<a href=\""+query.replaceAll("y_dsc","y_asc")+"\">"+y+"</a>";
				y += " &#8595";
				t += " &#8597";
				break;
		}
		t = t.replaceAll("page="+Integer.toString(page),"page=1");
		y = y.replaceAll("page="+Integer.toString(page),"page=1");
		out.println("<br><br><br>");
		out.println("<table class=\"center\"><td><tr><th></th><th align=\"left\">ID</th><th align=\"left\">"+ t + "</th><th align=\"left\">"+y+"</th><th align=\"left\">Director</th><th align=\"left\">Genre</th><th align=\"left\">Actors</th></tr></td>");
	}
	public static void pagination(ResultSet rs,PrintWriter out, String query, int ipp, int page) throws Exception{
		out.print("<br><center><table style=\"width:65%\">");
		out.println("<td>");
			
		if(page > 1)
			out.println("<a href =\""+query.replaceAll("&page="+Integer.toString(page),"")+"&page="+Integer.toString(page-1)+"\">Previous</a>");
		if(ipp*(page) < countRS(rs))
			out.println("<a href =\""+query.replaceAll("&page="+Integer.toString(page),"")+"&page="+Integer.toString(page+1)+"\">Next</a>");
		//out.println("<br><br><br><br>");
		out.println("<br>");
		out.println("<br>");
		out.println("<td align=\"right\">Results Per Page: ");
		for(int i = 1; i <= 5; i++){
			if(i * 5 != ipp){
				String link = ("<a href =\""+query.replaceAll("&ipp="+Integer.toString(ipp),"")+"&ipp="+Integer.toString(i * 5)+"\">"+Integer.toString(i*5)+"</a> ");
				//if(page * i * 5 > countRS(rs)){
					int temp = 1;
					//int count = countRS(rs);
					while(((temp-1) * i * 5 + 1) < ((page-1) * ipp + 1)){temp++;}
					link = link.replaceAll("&page="+Integer.toString(page),"&page="+Integer.toString(temp));
				//}
				out.println(link);
			}
			else
				out.println(Integer.toString(i * 5) + " ");
		}
		out.println("</td>");
		out.println("</table></center>");
	}
	public static void printStuff(ResultSet rs, PrintWriter out, String query, int ipp, int page, String context) throws Exception{
		int i = ipp;
		if(ipp*(page-1)==0)
			rs.beforeFirst();
		else
			rs.absolute(ipp*(page-1));
		out.println("\n");
		while(rs.next() && i-->0){
			String ID = rs.getString("ID");
			String TT = rs.getString("title");
			String href = context+"/getMovie?id="+ID;
			TT = "<a href=\""+href+"\" class=\"trigger\" sytle=\"font-size:14px\">"+TT+"</a>";
			String YR = rs.getString("year");
			String DR = rs.getString("director");
			String BU = rs.getString("banner_url");

			String div = "<div><div class=\"pop-up\" style=\"font-size:14px\">"+
				"<img src='"+BU+"width='100'heigh'140'><br>"+
				"<strong>Title:</strong>"+rs.getString("title")+"<br>"+
				"<strong>Year:</strong>"+YR+"<br>"+
				"<strong>Director:</strong>"+DR+"<br>";
			div += "<strong>Stars:</strong>"+printStars(ID,out,context);	
			div += "</div>" + TT + "</div>";
				

			
			out.println(//"<center><table style=\"width:75%\">" + 
					"<tr>\n" +
					"<td style=\"font-size:14px\">" + "<a href=\""+context+"/Cart?id=" + ID + "\"><img src=\"Cart-Icon.png\" height=\"20\" width=\"20\"></a>" + "</td>\n" +
					//"<td><img src=\"" + BU + "\"></td>" +
					"<td style=\"font-size:14px\">" + ID + "</td>\n" +
					"<td>" + div + "</td>\n" +
					"<td style=\"min-width:60px; font-size:14px\">" + YR + "</td>\n" +
					"<td style=\"font-size:14px\">" + DR + "</td>\n" +
					"<td>\n");
			getGenresStars(ID ,out, query, context);
			out.println("</td></tr>");//</table></center>\n");
		}
		out.println("</table></center>\n");
//		pagination(rs,out,query,ipp,page);
	}
	public static void printGenres(String movie_id, PrintWriter out, String order,String context) throws Exception
	{
		//int orderIndex = q.indexOf("order=");
		String subquery = "SELECT genre_id FROM genres_in_movies WHERE movie_id="+movie_id;
		String query = "SELECT * FROM genres WHERE id IN("+subquery+") ORDER BY name";
		ResultSet rs = executeSQL.execute(query);
		String name;

		if(rs.next()){
			name = rs.getString("name");
			out.print("<a href=\""+context+"/Browse?by=genre&query="+name+"&order="+order+"\" style=\"font-size:14px\">"+name+"</a>");
		}
		while(rs.next()){
			out.print(", ");
			name = rs.getString("name");
			out.print("<a href=\""+context+"/Browse?by=genre&query="+name+"&order="+order+"\" style=\"font-size:14px\">"+name+"</a>");
		}

		rs.close();
	}
	public static String printStars(String movie_id,PrintWriter out,String context) throws Exception
	{
		String subquery = "select star_id from stars_in_movies where movie_id="+movie_id;
		String query = "select * from stars where id in (" + subquery + ")";
		String output=new String();
		ResultSet rs = executeSQL.execute(query);
		String name;
		if(rs.next()){
			name = rs.getString("first_name")+ " " + rs.getString("last_name");
			output=("<a href=\""+context+ "/getStar?star="+rs.getInt("id")+"\" style=\"font-size:14px\">"+name+"</a>");
		}
		while(rs.next()){
			output+=(", ");
			name = rs.getString("first_name")+ " " + rs.getString("last_name");
			output+=("<a href=\""+context+"/getStar?star="+rs.getInt("id")+"\" style=\"font-size:14px\">"+name+"</a>");
		}
		return output;
	}
	public static void getGenresStars(String movie_id, PrintWriter out, String q, String context) throws Exception
	{
		int orderIndex = q.indexOf("order=");
		String order = q.substring(orderIndex+6,orderIndex+11);
		printGenres(movie_id,out,order,context);
		out.println("</td><td>");
		out.println(printStars(movie_id,out,context));
	}
}
