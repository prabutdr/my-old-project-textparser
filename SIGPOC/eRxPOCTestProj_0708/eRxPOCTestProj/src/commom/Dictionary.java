package commom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * To hold entire lookup values (words/sentences) as dictionary.
 * 
 * TODO: Values are hard-coded here currently, later it will be loaded from DB/files. 
 * 
 * @author Cognizant
 *
 */
public class Dictionary {
	public static final String VERB = "VERB";

	public static final String FREQUENCY = "FREQUENCY";

	public static final String TIME_OF_DAY = "TIME_OF_DAY";

	public static final String DURATION = "DURATION";

	public static final String DRUG_STRENGTH = "DRUG_STRENGTH";

	public static final String DOSAGE_FORM = "DOSAGE_FORM";

	public static final String ROUTE_OF_ADMIN = "ROUTE_OF_ADMIN";

	public static final String SITE_OF_ADMIN = "SITE_OF_ADMIN";

	public static final String VEHICLE = "VEHICLE";

	public static final String INDICATION = "INDICATION";

	public static final String DIRECTION = "DIRECTION";

	public static final String INSTRUCTION = "INSTRUCTION";

	/**
	 * This map will hold list of historical tokens map
	 */
	public static Map<String,String> tokenList;
	
	/**
	 * This map will contain Latin abbreviation to English sentence 
	 */
	public static Map<String,String> latinAbbrMap;
	
	/**
	 * This map will contain other abbreviations
	 */
	public static Map<String,String> otherAbbrMap;
	
	/**
	 * This map will contain special chars and equivalent direct replacement 
	 */
	public static Map<String,String> specialCharMap;
	
	/**
	 * This map will contain long SIG to short SIG mappings 
	 */
	public static Map<String,String> shortSigMap;
	
	/**
	 * This map will contain default verb values based on dosage form code
	 */
	public static Map<String,String> defaultVerbForDosageFormMap;
	
	/**
	 * This map will contain short SIG to long SIG mappings 
	 */
	public static Map<String,String> longSigMap;
	
	public static ArrayList<String> ignorableTokens = new ArrayList<String>();
	
	public static ArrayList<String> dosageForm = new ArrayList<String>();
			 
	public static ArrayList<String> dosageMetrics = new ArrayList<String>();
			 
	public static ArrayList<String> durationMetrics = new ArrayList<String>();

	public static ArrayList<String> frequencySuffix = new ArrayList<String>();

	public static ArrayList<String> frequencySuffixWithEvery = new ArrayList<String>();
	
	static {
		// Load history tokens list
		HashMap<String, String> tmpTokenList = new HashMap<String, String>();
		HashMap<String, String> tmpLatinAbbrMap = new HashMap<String, String>();
		HashMap<String, String> tmpOtherAbbrMap = new HashMap<String, String>();
		HashMap<String, String> tmpShortSigMap = new HashMap<String, String>();
		defaultVerbForDosageFormMap = new HashMap<String, String>();
		
		//Load Map
		tmpTokenList = CommonUtils.returnMapValues("C:\\Users\\153093\\Desktop\\Prabu\\RFP\\SIG_POC\\eRxPOCTestProj\\eRxPOCTestProj\\tokenCategory.txt");
		tmpLatinAbbrMap = CommonUtils.returnMapValues("C:\\Users\\153093\\Desktop\\Prabu\\RFP\\SIG_POC\\eRxPOCTestProj\\eRxPOCTestProj\\latinAbbr.txt");
		tmpOtherAbbrMap = CommonUtils.returnMapValues("C:\\Users\\153093\\Desktop\\Prabu\\RFP\\SIG_POC\\eRxPOCTestProj\\eRxPOCTestProj\\otherAbbr.txt");
		tmpShortSigMap = CommonUtils.returnMapValues("C:\\Users\\153093\\Desktop\\Prabu\\RFP\\SIG_POC\\eRxPOCTestProj\\eRxPOCTestProj\\shortSig.txt");
		defaultVerbForDosageFormMap = CommonUtils.returnMapValues("C:\\Users\\153093\\Desktop\\Prabu\\RFP\\SIG_POC\\eRxPOCTestProj\\eRxPOCTestProj\\defaultVerbForDosageFormCd.txt");
		
		//Load Lists
		ignorableTokens = CommonUtils.returnListValues("C:\\Users\\153093\\Desktop\\Prabu\\RFP\\SIG_POC\\eRxPOCTestProj\\eRxPOCTestProj\\ignorableTokens.txt");
		dosageForm = CommonUtils.returnListValues("C:\\Users\\153093\\Desktop\\Prabu\\RFP\\SIG_POC\\eRxPOCTestProj\\eRxPOCTestProj\\dosageForm.txt");
		dosageMetrics = CommonUtils.returnListValues("C:\\Users\\153093\\Desktop\\Prabu\\RFP\\SIG_POC\\eRxPOCTestProj\\eRxPOCTestProj\\dosageMetrics.txt");
		durationMetrics = CommonUtils.returnListValues("C:\\Users\\153093\\Desktop\\Prabu\\RFP\\SIG_POC\\eRxPOCTestProj\\eRxPOCTestProj\\durationMetrics.txt");
		frequencySuffix = CommonUtils.returnListValues("C:\\Users\\153093\\Desktop\\Prabu\\RFP\\SIG_POC\\eRxPOCTestProj\\eRxPOCTestProj\\frequencySuffix.txt");
		frequencySuffixWithEvery = CommonUtils.returnListValues("C:\\Users\\153093\\Desktop\\Prabu\\RFP\\SIG_POC\\eRxPOCTestProj\\eRxPOCTestProj\\frequencySuffixWithEvery.txt");
		
		for(String dosageMetric: dosageMetrics) {
			tmpTokenList.put(dosageMetric, Dictionary.DRUG_STRENGTH);
		}

		for(String dosageFrm: dosageForm) {
			tmpTokenList.put(dosageFrm, Dictionary.DOSAGE_FORM);
		}

		
		// Load matching replacement for special chars
		HashMap<String, String> tmpSpecialCharMap = new HashMap<String, String>();
		tmpSpecialCharMap.put(";", " ; ");
		tmpSpecialCharMap.put("\\(", " ( "); // (
		tmpSpecialCharMap.put("\\)", " ) "); // )
		tmpSpecialCharMap.put("\\^", " ^ ");
		tmpSpecialCharMap.put(":", " : ");
		tmpSpecialCharMap.put("#", " # ");
		tmpSpecialCharMap.put("\"", " \" ");
		tmpSpecialCharMap.put("\"", " = ");
		//tmpSpecialCharMap.put(" - ", "-");
		tmpSpecialCharMap.put("\\(S\\)", ""); // (S)
		
		/*** TODO: look for optimization - order by n-gram ***/
		tokenList = new LinkedHashMap<String, String>();
		ArrayList<String> list = new ArrayList<String>(tmpTokenList.keySet());
		Collections.sort(list, new NGramDescComparator());
		for(String key: list) {
			tokenList.put(key, tmpTokenList.get(key));
		}

		latinAbbrMap = new LinkedHashMap<String, String>();
		list = new ArrayList<String>(tmpLatinAbbrMap.keySet());
		Collections.sort(list, new NGramDescComparator());
		for(String key: list) {
			latinAbbrMap.put(key, tmpLatinAbbrMap.get(key));
		}
		
		otherAbbrMap = new LinkedHashMap<String, String>();
		list = new ArrayList<String>(tmpOtherAbbrMap.keySet());
		Collections.sort(list, new NGramDescComparator());
		for(String key: list) {
			otherAbbrMap.put(key, tmpOtherAbbrMap.get(key));
		}

		specialCharMap = new LinkedHashMap<String, String>();
		list = new ArrayList<String>(tmpSpecialCharMap.keySet());
		Collections.sort(list, new NGramDescComparator());
		for(String key: list) {
			specialCharMap.put(key, tmpSpecialCharMap.get(key));
		}
		
		shortSigMap = new LinkedHashMap<String, String>();
		HashMap<String, String> tmpLongSigMap = new HashMap<String, String>();
		list = new ArrayList<String>(tmpShortSigMap.keySet());
		Collections.sort(list, new NGramDescComparator());
		for(String key: list) {
			shortSigMap.put(key, tmpShortSigMap.get(key));
			tmpLongSigMap.put(tmpShortSigMap.get(key), key);
		}
		

		longSigMap = new LinkedHashMap<String, String>();
		list = new ArrayList<String>(tmpLongSigMap.keySet());
		Collections.sort(list, new NGramDescComparator());
		for(String key: list) {
			longSigMap.put(key, tmpLongSigMap.get(key));
		}
		
		Collections.sort(dosageForm, new NGramDescComparator());
		Collections.sort(dosageMetrics, new NGramDescComparator());
		Collections.sort(durationMetrics, new NGramDescComparator());
		Collections.sort(frequencySuffix, new NGramDescComparator());
		Collections.sort(frequencySuffixWithEvery, new NGramDescComparator());
		Collections.sort(ignorableTokens, new NGramDescComparator());
		
		// Print & verify order
		/*System.out.println("\nLatin to Eng Abbreviations - ");
		for (Map.Entry<String,String> entry : latinAbbrMap.entrySet()) {
			  String key = entry.getKey();
			  String value = entry.getValue();
			  System.out.println(key + " = " + value);
		}
		
		System.out.println("\nToken List - ");
		for (Map.Entry<String,String> entry : tokenList.entrySet()) {
			  String key = entry.getKey();
			  String value = entry.getValue();
			  System.out.println(key + " = " + value);
		}
		
		System.out.println("\nspecialCharMap List - ");
		for (Map.Entry<String,String> entry : specialCharMap.entrySet()) {
			  String key = entry.getKey();
			  String value = entry.getValue();
			  System.out.println(key + " = " + value);
		}
		
		System.out.println("\nshortSigMap List - ");
		for (Map.Entry<String,String> entry : shortSigMap.entrySet()) {
			  String key = entry.getKey();
			  String value = entry.getValue();
			  System.out.println(key + " = " + value);
		}
		
		System.out.println("\nlongSigMap List - ");
		for (Map.Entry<String,String> entry : longSigMap.entrySet()) {
			  String key = entry.getKey();
			  String value = entry.getValue();
			  System.out.println(key + " = " + value);
		}*/
	}	
	
	public static void main(String[] args) {
		//Dictionary dictionary = new Dictionary();
		String str = "saravanansaravanan";
		str = str.replaceAll("ra", "test");
		System.out.println("TESTRP - " + str);
	}
}