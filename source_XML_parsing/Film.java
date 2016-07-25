//package sax;

import java.util.ArrayList;
import java.util.List;

public class Film {

	String title;
	List<String> directorlist;
	int year;
	String genre = "N/A";
	
	public Film(){
		directorlist=new ArrayList<String>();
	}
	
	public void setTitle(String title){
		
		this.title=title;
	}
	
	public void setYear(int year){
		this.year=year;
	}
	
	public void setDirector(String director){
		if(director!=null){
			directorlist.add(director);
		}
	}
	
	public void setGenre(String genre){
		if(genre != null)
			this.genre=genre;
	}
	
	public String getDirectorlist(){
		if(directorlist.size()>0)
			return directorlist.get(0);
		else
			return "null";
	}
	
	public String getGenre(){
		return genre;
	}
	
	public String getTitle(){
		return title;
	}
	
	public int getYear(){
		return year;
	}
	
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Movie Details - ");
		sb.append("Title:" + title);
		sb.append(", ");
		sb.append("Genre:" + genre);
		sb.append(", ");
		sb.append("Year:" + year);
		sb.append(", ");
		sb.append("Director:" + directorlist);
		sb.append(".");
		
		return sb.toString();
	}
}
