package commom;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.StringTokenizer;

public class CommonUtils {
	public static String toString(ArrayList<String> list) {
		String result = GeneralConstants.EMPTY_STRING;

		if(list == null || list.size() == 0) 
			return result;
		
		result = list.get(0);
		for(int i = 1; i < list.size(); i++) {
			result += "; " + list.get(i);
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static int compareTokens(String a, String b) {
		ArrayList<String> listA = new ArrayList<String>(Arrays.asList(a.split("\\s+")));
		ArrayList<String> listB = new ArrayList<String>(Arrays.asList(b.split("\\s+")));

		int nonMatchToken = 0;
		for(String tokenA: listA) {
			if(!listB.remove(tokenA)) {
				nonMatchToken--;
			}
		}
		
		if(nonMatchToken < 0) 
			return nonMatchToken;
		else 
			return listB.size();
	}
	
	public static final LinkedHashMap<String, Integer> digitsMap;
	public static final LinkedHashMap<String, Integer> magnitudesMap;
	
	static {
		digitsMap = new LinkedHashMap<String, Integer>();
		digitsMap.put("ZERO", 0);
		digitsMap.put("ONE", 1);
		digitsMap.put("TWO", 2);
		digitsMap.put("THREE", 3);
		digitsMap.put("FOUR", 4);
		digitsMap.put("FIVE", 5);
		digitsMap.put("SIX", 6);
		digitsMap.put("SEVEN", 7);
		digitsMap.put("EIGHT", 8);
		digitsMap.put("NINE", 9);
		digitsMap.put("TEN", 10);
		digitsMap.put("ELEVEN", 11);
		digitsMap.put("TWELVE", 12);
		digitsMap.put("THIRTEEN", 13);
		digitsMap.put("FOURTEEN", 14);
		digitsMap.put("FIFTEEN", 15);
		digitsMap.put("SIXTEEN", 16);
		digitsMap.put("SEVENTEEN", 17);
		digitsMap.put("EIGHTEEN", 18);
		digitsMap.put("NINETEEN", 19);
		digitsMap.put("TWENTY", 20);
		digitsMap.put("THIRTY", 30);
		digitsMap.put("FORTY", 40);
		digitsMap.put("FIFTY", 50);
		digitsMap.put("SIXTY", 60);
		digitsMap.put("SEVENTY", 70);
		digitsMap.put("EIGHTY", 80);
		digitsMap.put("NINETY", 90);
		
		magnitudesMap = new LinkedHashMap<String, Integer>();
		magnitudesMap.put("HUNDRED", 100);
		magnitudesMap.put("THOUSAND", 1000);
		magnitudesMap.put("MILLION", 100000);
	}
	

	public static String replaceNumberWordsToDigits(String input) {
		String result = "";
		String token;
		int digitForm = 0;
		boolean digitIdentified = false;
		
		input = input.replaceAll("\\s+", " ").trim();
		input.toUpperCase();

		StringTokenizer tokens = new StringTokenizer(input, " ");

		//System.out.println("digitsMap - " + digitsMap);
		//System.out.println("magnitudesMap - " + magnitudesMap);
		while(tokens.hasMoreTokens()) {
			token = tokens.nextToken();
			//System.out.println("token - " + token);
			if(digitsMap.containsKey(token)) {
				digitForm += digitsMap.get(token);
				digitIdentified = true;
				//System.out.println("digitForm - 1 - " + digitForm);
			}
			else if(magnitudesMap.containsKey(token)) {
				if(digitForm == 0) {
					digitForm = magnitudesMap.get(token);
				}
				else {
					digitForm *= magnitudesMap.get(token);
				}
				digitIdentified = true;
				//System.out.println("digitForm - 2 - " + digitForm);
			}
			else {
				if(digitIdentified == true) {
					result = result + GeneralConstants.SPACE_STRING + digitForm + GeneralConstants.SPACE_STRING + token;
				}
				else {
					result = result + GeneralConstants.SPACE_STRING + token;
				}
				digitIdentified = false;
				digitForm = 0;
				//System.out.println("result - " + result);
			}
		}
		
		if(digitIdentified == true) {
			result = result + GeneralConstants.SPACE_STRING + digitForm;
		}
		result = result.replaceAll("\\s+", " ").trim();
		
		return result;
	}
	
	//Return HashMap
	public static HashMap<String, String> returnMapValues(String filePath) {
		HashMap<String, String> keyValueHashMap = new HashMap<String, String>();
		String[] keyValue = new String[2];
		try{
		Scanner scanValues = new Scanner(new File(filePath));
		while(scanValues.hasNextLine()) {
	    	 String line = scanValues.nextLine().trim(); 
	    	 keyValue = line.split("=");
	    	 keyValueHashMap.put(keyValue[0], keyValue[1]);
		}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return keyValueHashMap;
	}
	
	//Return Array List
	public static ArrayList returnListValues(String filePath) {
		ArrayList keyArrayList = new ArrayList();
		try{
		Scanner scanValues = new Scanner(new File(filePath));
		while(scanValues.hasNextLine()) {
	    	 String line = scanValues.nextLine().trim(); 
	    	 keyArrayList.add(line);
		}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return keyArrayList;
	}
	
	public static void main(String[] args) {
		System.out.println(replaceNumberWordsToDigits("ONE FORTY FIVE HUNDRED TIMES DAILY"));

		System.out.println(compareTokens("a d", "a b c"));
		System.out.println(compareTokens("a b", "a a b c"));
		System.out.println(compareTokens("c a b", "a b c"));
		System.out.println(compareTokens("c a b d", "a b c"));
	}
}
