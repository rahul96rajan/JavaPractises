package practisePckg;

import java.util.Scanner;

public class MinionGameList {
	int stuartScore = 0;
	int kevinScore = 0;
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter the String : ");
		String inputString = in.next().toUpperCase();
		inputString = "";
		MinionGameList obj =new MinionGameList();
		obj.createVowelList(inputString);
		System.out.println(obj.whoseTheWinner());
//		System.out.println(obj.stuart);
//		System.out.println(obj.kevin);
		in.close();
	}
	
//	public ArrayList<String> createList(String str) {
//		ArrayList<String> arr = new ArrayList<String>();
//		for(int i=0; i <str.length(); i++) {
//			for(int j = i+1; j<=str.length();j++) {
//				arr.add(str.substring(i, j));
//			}
//		}
//		return arr;
//	}
	
	public void createVowelList(String str) {
		char val;
		for(int i=0; i<str.length(); i++) {
			val = str.charAt(i);
			if((val=='A'||val=='E'||val=='I'||val=='O'||val=='U')) {
				kevinScore += (str.length()-i);
			}else {
				stuartScore += (str.length()-i);
			}			
		}
	}
	
	public String whoseTheWinner() {
		String Win = "Draw";
		if(stuartScore>kevinScore) {
			Win = "Stuart " + stuartScore;
		}else if(kevinScore>stuartScore) {
			Win = "Kevin " + kevinScore;
		}
		System.out.println(" k = " + kevinScore);
		return Win;
	}
	
}
