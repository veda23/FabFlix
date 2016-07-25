//package sax;

import java.io.IOException;
import java.net.*;
import java.text.*;
import java.sql.*;
import java.util.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.lang.Object;

public class SAXParserCast extends DefaultHandler {
	String currentmovie;
	ArrayList<FilmCast> FilmList;
	ArrayList<String> castList;
	ArrayList<String> addCast;
	String temp;
	FilmCast filmCast;
	Connection dbcon = null;
	Statement statement;	
	public SAXParserCast(){
		FilmList=new ArrayList<FilmCast>();
		addCast = new ArrayList<String>();
		String loginUser = "classta";
		String loginPasswd = "classta";
		String loginUrl = "jdbc:mysql://localhost:3306/moviedb_project3_grading";
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			this.dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
			
			this.dbcon.setAutoCommit(false);
			statement = dbcon.createStatement();
		}catch (Exception ex) {
			System.out.println("Cannot connect to the database.");
			ex.printStackTrace();
			System.exit(-1);
		}
		
	}

	public void runExample() {
		parseDocument();
		//SaveToDb();
	}
	
	private void insertCast() throws SQLException{
		System.out.println("Inserting stars");
		System.out.println("Remove dupluactes");
		//remove duplicates	
		Set<String> tempSet = new HashSet<>();
		tempSet.addAll(addCast);
		addCast.clear();
		addCast.addAll(tempSet);
		
		System.out.println("Prepare query");
		//prepare query
		StringBuilder query = new StringBuilder();
		query.append("SELECT a.first_name, a.last_name FROM stars a INNER JOIN stars b on a.id=b.id AND a.first_name IN(");
		int l = addCast.size();
		for(int i = 0; i < l; i++){
			query.append("?");
			if(i + 1 < l)
				query.append(",");
		}
		query.append(") AND b.last_name IN(");
		for(int i = 0; i < l; i++){
			query.append("?");
			if(i + 1 < l)
				query.append(",");
		}
		query.append(")");

		System.out.println("Create ps");
		//create preparedstatement
		PreparedStatement ps = dbcon.prepareStatement(query.toString());
		int i = 1;
		for(String s : addCast){
			String[]split=s.split(" ");
			String fn = split[0]!=null ? split[0] : "";
			String ln = split.length >= 2 ? split[1] : "";
			ps.setString(i++, fn);
			ps.setString(i++, ln);
		}

		System.out.println("execute");
		//get results
		ResultSet rs = ps.executeQuery();
		
		System.out.println("Remove stars already in db");
		//remove stars already in database
		while(rs.next()){
			String name = rs.getString("first_name");
			name = !rs.getString("last_name").equals("") ? name + rs.getString("last_name") : name;
			addCast.remove(name);
		}

		System.out.println("Insert int db");
		//insert new cast
		String batchInsertQuery;
		for(String s:addCast){
			String[]split=s.split(" ");
			String fn = split[0]!=null ? split[0] : "";
			String ln = split.length >= 2 ? split[1] : "";
			batchInsertQuery = "INSERT INTO stars (first_name,last_name) VALUES('"+fn+"','"+ln+"')";
			statement.addBatch(batchInsertQuery);
		//	System.out.println(batchInsertQuery);
		}
		statement.executeBatch();
		System.out.println("Insert new stars done");
	}

	private void SaveToDb() throws SQLException {
		
		insertCast();	
		String batchInsertQuery;
	System.out.println("Insert stars_in_movies\nBatch setup");
		//insert to stars_in_movies
		int i = 0;
		int t = 0;
		for(FilmCast f:FilmList){
			//System.out.println(f.toString());
			ArrayList<String> temp = f.getCast();
			for(String s:temp){
				batchInsertQuery = "INSERT IGNORE INTO stars_in_movies (star_id,movie_id) VALUES(";
				String[] split = s.split(" ");
				String fn = split[0]!=null ? split[0] : "";
				String ln = split.length >= 2 ? split[1] : "";
				batchInsertQuery += "(SELECT id FROM stars WHERE first_name='"+fn+"' AND last_name='"+ln+"' LIMIT 1),";
				batchInsertQuery += "(SELECT id FROM movies WHERE title='"+f.getTitle()+"' LIMIT 1))";
				//System.out.println(batchInsertQuery);
				statement.addBatch(batchInsertQuery);
				i++;t++;
			}
	//		System.out.println(f.getTitle());
			if(i>1000){
				System.out.println("Execute batch of 1000");
				statement.executeBatch();
				i = 0;
			}
		}
		statement.executeBatch();
System.out.println("Done");
                statement.close();
                
		this.dbcon.commit();

		System.out.println("No of stars in movies '" + t + "'.");
		//print();

	}

	private void parseDocument() {

		//get a factory
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {

			//get a new instance of parser
			SAXParser sp = spf.newSAXParser();

			//parse the file and also register this class for call backs
			sp.parse("casts124.xml", this);			
			
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch (IOException ie) {
			ie.printStackTrace();
		}
	}

	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		//reset
		//System.out.println("Inside start");
		//System.out.println("qName: "+qName);

		}


	public void characters(char[] ch, int start, int length) throws SAXException {

		//System.out.println("Inside characters");

		temp = new String(ch,start,length);
	}

	public void endElement(String uri, String localName, String qName) throws SAXException {

		if(qName.equalsIgnoreCase("filmc")) {
			//add it to the list
			filmCast=new FilmCast();
			if(filmCast!=null)
				FilmList.add(filmCast);

		}
		else if (qName.equalsIgnoreCase("t")) {
			if(temp!=null && filmCast!=null){
				String newtemp=temp.replace("'", "");
				if(filmCast.getTitle() == null || !filmCast.getTitle().equals(newtemp)){
		//System.out.println("Temp value for title:"+newtemp);
					//int id = getMovieId(newtemp);
		//System.out.println("ID: "+id);
		//			castList = getCast(newtemp);	
					//filmCast.setId(id);
					filmCast.setTitle(newtemp);
				}
			}
		}
		else if(qName.equalsIgnoreCase("a"))
			if(temp!=null && filmCast!=null){
			//	temp = temp.replace("/","");
				temp = temp.replace("'","");
				temp = temp.replace("\"","");
				temp = temp.replace("\\","");
				//if(!castList.contains(temp))
					filmCast.addStar(temp);
				//if(needToAdd(temp))
					addCast.add(temp);					
			}
		
		
	}
	public ArrayList<String> getCast(String s){
		try{
		String id = "SELECT id FROM movies WHERE title='"+s+"' LIMIT 1";
		String subquery = "SELECT star_id FROM stars_in_movies WHERE movie_id=("+id+")";
		String query = "SELECT * FROM stars WHERE id IN ("+subquery+")";
		ResultSet rs = statement.executeQuery(query);
		ArrayList<String> cast = new ArrayList<String>();
		while(rs.next()){
			String name = rs.getString("first_name")+" "+rs.getString("last_name");
			cast.add(name);
		}
		return cast;
		}catch(SQLException e){}
		return null;
	}
	public void print(){

		
		Iterator it = FilmList.iterator();
		while(it.hasNext()) 
		{
			System.out.println(it.next().toString());
		}
	}
	



	public static void main(String[] args){
		long startTime = System.nanoTime();
		SAXParserCast spe = new SAXParserCast();
		spe.runExample();
		try{
		spe.SaveToDb();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		long end = System.nanoTime();
		System.out.println((end-startTime)/1000000000 + "sec");
	}
	
}
