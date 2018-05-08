import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Members {
    //this class contains bibnumber and name associated to bib number
	private Map<Integer, String> bibMap;

	public Members(){
		bibMap = new HashMap<>();
	}

	/**
	 * parse file text and put into hashmap
	 * @param filename
	 */
	public Members(String filename){
		bibMap = new HashMap<>();
		File file = new File(filename);
		try{
			BufferedReader br = new BufferedReader(new FileReader(file));

			String line;
			while((line = br.readLine()) != null){
				String[] parts = line.split(":");
				String name = parts[0];
				String number = parts[1];
				bibMap.put(Integer.parseInt(number), name);
			}
		}catch(IOException exc){
			System.out.println("File not found: " + filename);
		}
	}
	public String getName(int bibNum){//if null then not found
		return bibMap.get(bibNum);
	}

	public void print(){
		System.out.println("Bib Number=name: " + bibMap.toString());
	}
}
