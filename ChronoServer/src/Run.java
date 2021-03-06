import Util.Time;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class Run {
	private String timeStamp;
	private String eventType;
	private ArrayList<Result> results;

	public Run(){
		results = new ArrayList<>();

	}

	public void add(String str){
		Gson gson = new Gson();
		try{
			ArrayList<Result> fromJson = gson.fromJson(str, new TypeToken<Collection<Result>>(){
			}.getType());


		}catch(JsonSyntaxException ex){
			System.out.println("Error in syntax occured.");
		}
	}
	public void printResults(){
		int cnt = 0;
		for(Result res: results){
			++cnt;
			System.out.println(cnt + ": " + res.get_bib() + " " + res.get_time());
		}
	}


	public void clear() {
		results.clear();
	}
	public ArrayList<Result> getResults() {
		//added
		int cnt = 0;
		for (Result res : results) {
			++cnt;
			String time = res.get_time();
			if (time.equals("0")) {
				time = "00:00:00:00";
			} else if (time.equals("-1")) {
				time = "DNF";
			}
		}//added
		return results;
	}
	public String getEventType(){
		return eventType;
	}


}

