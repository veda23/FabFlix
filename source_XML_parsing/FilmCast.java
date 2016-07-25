//package sax;

import java.util.ArrayList;
import java.util.List;

public class FilmCast {

	String title;
	ArrayList<String> cast;
	int id;
	
	public FilmCast(){
		title = null;
		cast=new ArrayList<String>();
	}
	
	public void setTitle(String title){
		
		this.title=title;
	}
	
	public void setId(int id){
		this.id=id;
	}
	
	public void addStar(String star){
		if(star!=null){
			cast.add(star);
		}
	}
	
	public ArrayList<String> getCast(){
		return cast;
	}
	
	public int getId(){
		return id;
	}
	
	public String getTitle(){
		return title;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Movie Details - ");
		sb.append("Title:" + title);
		sb.append(", ");
		sb.append("ID:" + id);
		sb.append(", ");
		sb.append("Cast:" + cast);
		sb.append(".");
		
		return sb.toString();
	}
}
