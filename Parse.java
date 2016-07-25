//package trial;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Parse {
	//Parse Ts and Tj values to find average 
	public Parse() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("catalina1.1.out"));

		// read the first line from the text file
		String fileRead=null;
		long tj=0;
		long ts=0;
		int tj_count=0,ts_count=0;
		// loop until all lines are read
		while ((fileRead =br.readLine())!= null)
		{
			
			String[] tokenize = fileRead.split(" ");
			
			if(tokenize[0].equals("tj")){
			
				tj=tj+Long.parseLong(tokenize[1].trim());
				tj_count++;
				//System.out.println("tj count:"+tj_count);
			}
			else if(tokenize[0].equals("ts")){
				ts=ts+Long.parseLong(tokenize[1].trim());
				ts_count++;
			}
			System.out.println(fileRead);
		
		}
		if(ts_count!=0 && tj_count!=0 ){
			long tj_avg=tj/tj_count;
			long ts_avg=ts/ts_count;
			System.out.println("TS:"+ts_avg+"ns");
			System.out.println("TJ:"+tj_avg+"ns");
		}
	}

}


