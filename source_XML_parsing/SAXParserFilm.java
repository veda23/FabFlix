//package sax;

import java.io.IOException;
import java.sql.*;
import java.util.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;



public class SAXParserFilm extends DefaultHandler {
	String currentmovie;
	List<Film> FilmList;
	String temp;
	Film film = new Film();
	Connection dbcon = null;
	Statement statement;
	ArrayList<String> existMovie;
	ArrayList<String> existGenre;
	ArrayList<String> addGenre;
	
	public SAXParserFilm(){
		FilmList=new ArrayList<Film>();
		String loginUser = "classta";
		String loginPasswd = "classta";
		String loginUrl = "jdbc:mysql:///moviedb_project3_grading";
		try{
			Class.forName("org.gjt.mm.mysql.Driver");
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

	public void runExample() throws SQLException {
		existGenre = new ArrayList<String>();
		existMovie = new ArrayList<String>();
		addGenre = new ArrayList<String>();
		ResultSet rs = statement.executeQuery("SELECT title FROM movies");
		while(rs.next()){
			existMovie.add(rs.getString("title"));
		}

		rs = statement.executeQuery("SELECT name FROM genres");
		while(rs.next()){
			existGenre.add(rs.getString("name"));
		}		

		parseDocument();
		
		//SaveToDb();
	}

	private void SaveToDb() throws SQLException {

		String query;
	System.out.println("Insert genres");	
		for(String s : addGenre){
			query  = "INSERT INTO genres (name) VALUES";
			query += "('"+s+"')";
			//System.out.println(query);
			statement.addBatch(query);
		}
		statement.executeBatch();		
	
		/*
		String batchInsertQuery = "INSERT INTO movies (TITLE,year,DIRECTOR) VALUES";
		for(Film f:FilmList){
			batchInsertQuery += "('"+ f.getTitle() + "','" + f.getYear()+ "','"+ f.getDirectorlist()+ "'),";
		}
		batchInsertQuery = batchInsertQuery.substring(0, batchInsertQuery.length() - 1) + ";";

				System.out.println(batchInsertQuery);
                //statement.executeUpdate(batchInsertQuery);
		*/
	System.out.println("Insert movies");	
		for(Film f : FilmList){
			query = "INSERT INTO movies (title,year,director) VALUES";
			query += "('" + f.getTitle() + "','"+f.getYear()+"','"+f.getDirectorlist()+"')";
	//		System.out.println(query);
			statement.addBatch(query);
		}	
		statement.executeBatch();	
	System.out.println("Insert genres_in_movies");
		for(Film f : FilmList){
			query = "INSERT IGNORE INTO genres_in_movies (movie_id,genre_id) VALUES";
			query += "( (SELECT id FROM movies WHERE title='"+f.getTitle()+"'),(SELECT id FROM genres WHERE name='"+f.getGenre()+"') )";
			//System.out.println(query);
			statement.addBatch(query);
		}
		statement.executeBatch();
                statement.close();
                
		this.dbcon.commit();
	
		System.out.println("No of Movies '" + FilmList.size() + "'.");
//		print();

	}

	private void parseDocument() {

		//get a factory
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {

			//get a new instance of parser
			SAXParser sp = spf.newSAXParser();

			//parse the file and also register this class for call backs
			sp.parse("mains243.xml", this);			
			
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
		temp = temp.trim();
		temp = temp.replace("\\","");
		temp = temp.replace("\"","");
		temp = temp.replace("'","");
	}

	public void endElement(String uri, String localName, String qName) throws SAXException {

		if(qName.equalsIgnoreCase("film")) {
			//add it to the list
			if(film!=null && !existMovie.contains(film.getTitle()) ){
				FilmList.add(film);
			}
			film=new Film();

		}
		else if (qName.equalsIgnoreCase("t")) {
			if(temp!=null && film!=null){
				String newtemp=temp.replace("'", "");
		//		System.out.println("Temp value for title:"+newtemp);
				film.setTitle(newtemp);
			}
		}
		else if (qName.equalsIgnoreCase("year")) {
			//check for valid integer
			if(temp!=null && film!=null){
				if(temp.matches("[0-9]+")){
					film.setYear(Integer.parseInt(temp));
				}
				else{
					film.setYear(0000);
				}
			}	
		}
		else if (qName.equalsIgnoreCase("dirn")) {
			if(temp!=null && film!=null){
				String newtemp=temp.replace("'", "");
				film.setDirector(newtemp);
			}
		}
		else if(qName.equalsIgnoreCase("cat")){
			if(temp!=null && film!=null){
				film.setGenre(temp);
			}
			if(temp!=null && !existGenre.contains(temp) && !addGenre.contains(temp))
				addGenre.add(temp);
		}
		
	}
		//System.out.println("Inside end");
		//System.out.println("qName"+qName);
		//System.out.println(film);
		
	public void print(){

		
		Iterator it = FilmList.iterator();
		while(it.hasNext()) 
		{
			System.out.println(it.next().toString());
		}
	}
	



	public static void main(String[] args){
		SAXParserFilm spe = new SAXParserFilm();
		try{
		spe.runExample();
		spe.SaveToDb();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
