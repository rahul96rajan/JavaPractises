package practisePckg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MinionGameMap {
	HashMap<String, Integer> Stuart = new HashMap<String, Integer>();
	HashMap<String, Integer> Kevin = new HashMap<String, Integer>();
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter the String : ");
		String inputString = in.next().toUpperCase();
		System.out.println(inputString);
		MinionGameMap obj = new MinionGameMap();
		obj.createVowelList(inputString);
		System.out.println(obj.whoseTheWinner());
		in.close();
	}
	
	public ArrayList<String> createList(String str) {
		ArrayList<String> arr = new ArrayList<String>();
		for(int i=0; i <str.length(); i++) {
			for(int j = i+1; j<=str.length();j++) {
				arr.add(str.substring(i, j));
			}
		}
		return arr;
	}
	
	public void createVowelList(String str) {
		ArrayList<String> arr = createList(str);
		for(String val : arr) {
			if(!(val.startsWith("A") || 
					val.startsWith("E") || val.startsWith("I") || 
					val.startsWith("O") || val.startsWith("U"))) {
				if(Stuart.containsKey(val)){
					Stuart.put(val, Stuart.get(val)+1);
				}else {
					Stuart.put(val, 1);
				}
			}else {
				if(Kevin.containsKey(val)) {
					Kevin.put(val, Stuart.get(val)+1);
				}else {
					Kevin.put(val, 1);
				}
			}			
		}
	}
	
	public String whoseTheWinner() {
		String Win = "Draw";
		int stuartScore = 0; int kevinScore = 0;
		for(Map.Entry<String, Integer> entry : Stuart.entrySet()) {
			stuartScore += entry.getValue(); 
		}
		for(Map.Entry<String, Integer> entry : Kevin.entrySet()) {
			kevinScore += entry.getValue(); 
		}
		if(stuartScore>kevinScore) {
			Win = "Stuart " + stuartScore;
		}else if(kevinScore>stuartScore) {
			Win = "Kevin " + kevinScore;
		}
		return Win;
	}
	
}
