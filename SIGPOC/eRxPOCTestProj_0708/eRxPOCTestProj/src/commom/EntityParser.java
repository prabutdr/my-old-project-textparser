package commom;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EntityParser {
	/**
	 * 
	 * @param input
	 * @return
	 */
	public SIGToken parse(SIGToken tokens) {
		
		try {
			// Convert entire SIG to upper case
			String input = tokens.getInputSIG().toUpperCase();
			tokens.setInputSIG(input);
			
			// Replace special chars
			input = replaceSpecialChars(input);
			input = input.replaceAll("\\s+", " ").trim();
			
			input = parseSpecialFormats(input);
			input = input.replaceAll("\\s+", " ").trim();
			
			input = replaceLatinAbbr(input);
			input = input.replaceAll("\\s+", " ").trim();
	
			input = replaceOtherAbbr(input);
			input = input.replaceAll("\\s+", " ").trim();
	
			input = CommonUtils.replaceNumberWordsToDigits(input);
			input = input.replaceAll("\\s+", " ").trim();
	
			tokens.setStandardInputSIG(input);
	
			input = normalizeSpecialTokens(input);

			input = parseFrequency(input, tokens);
			input = input.replaceAll("\\s+", " ").trim();
			
			input = parseDrugStrength(input, tokens);
			input = input.replaceAll("\\s+", " ").trim();
	
			input = parseDosageInfo(input, tokens);
			input = input.replaceAll("\\s+", " ").trim();
			
			input = parseDuration(input, tokens);
			input = input.replaceAll("\\s+", " ").trim();
			
			input = matchDictionary(input, tokens);
			input = input.replaceAll("\\s+", " ").trim();
			
			input = removeIgnorableTokens(input);
			
			tokens.setNonParsedTokens(input);
	
			doPostOperation(tokens);
		}
		catch(Exception e) {
			e.printStackTrace();
			tokens.setParseStatus(GeneralConstants.PARSE_STATUS_FAIL);
			tokens.setReasonForFail(GeneralConstants.FR_EXCEPTION);
		}
		
		return tokens;
	}

	/**
	 * 
	 * @param tokens
	 */
	private void doPostOperation(SIGToken tokens) {
		// Calculate default values
		String dosageForm = CommonUtils.toString(tokens.getDosageForm());
		if(tokens.getVerb().size() == 0) {
			String defaultVerb = Dictionary.defaultVerbForDosageFormMap.get(tokens.getInDrugDosageFormCd());
			if(defaultVerb != null && !defaultVerb.trim().equals(GeneralConstants.EMPTY_STRING)) {
				tokens.getVerb().add(defaultVerb);
			}
		}

		// Calculate parse score
		int orginalTokenCount = tokens.getStandardInputSIG().trim().split("\\s+").length; // [ ;:\\.\\,\\)\\(]
		int nonParsedTokenCount = 0;
		if(!GeneralConstants.EMPTY_STRING.equals(tokens.getNonParsedTokens())) {
			nonParsedTokenCount = tokens.getNonParsedTokens().trim().split("\\s+").length;
		}
		double parsePercentage = (((double)orginalTokenCount - nonParsedTokenCount)/orginalTokenCount) * 100;
		tokens.setParseScore(parsePercentage);
		
		// Calculate parse status
		if(tokens.getVerb().size() == 0) {
			tokens.setParseStatus(GeneralConstants.PARSE_STATUS_FAIL);
			tokens.setReasonForFail(GeneralConstants.FR_VERB_MISSING);
		}
		else if(tokens.getDosage().size() == 0 && tokens.getDrugStrength().size() == 0) {
			tokens.setParseStatus(GeneralConstants.PARSE_STATUS_FAIL);
			tokens.setReasonForFail(GeneralConstants.FR_DOSAGE_MISSING);
		}
		else if(tokens.getRouteOfAdmin().size() == 0 && tokens.getSiteOfAdmin().size() == 0) {
			tokens.setParseStatus(GeneralConstants.PARSE_STATUS_FAIL);
			tokens.setReasonForFail(GeneralConstants.FR_DOSAGE_MISSING);
		}
		else if(tokens.getFrequency().size() == 0 && tokens.getTimeOfDay().size() == 0) {
			tokens.setParseStatus(GeneralConstants.PARSE_STATUS_FAIL);
			tokens.setReasonForFail(GeneralConstants.FR_FREQUENCY_MISSING);
		}
		else if(tokens.getParseScore() == 100) {
			tokens.setParseStatus(GeneralConstants.PARSE_STATUS_COMPLETE);
			tokens.setReasonForFail(GeneralConstants.EMPTY_STRING);
		}
		else if(tokens.getParseScore() == 0) {
			tokens.setParseStatus(GeneralConstants.PARSE_STATUS_FAIL);
			tokens.setReasonForFail(GeneralConstants.FR_NO_MATCH);
		}
		else {
			tokens.setParseStatus(GeneralConstants.PARSE_STATUS_PARTIAL);
			tokens.setReasonForFail(GeneralConstants.EMPTY_STRING);
		}
	}

	/**
	 * 
	 * @param input
	 * @return
	 */
	private static String replaceLatinAbbr(String input) {
		StringBuffer originalSIG = new StringBuffer(input);

		// Replace latin words with English 
		for (Map.Entry<String,String> entry : Dictionary.latinAbbrMap.entrySet()) {
			  String key = entry.getKey();
			  
			  int startIdx, endIdx;
			  endIdx = 0;
			  
			  while((startIdx = originalSIG.indexOf(key, endIdx)) != -1) {
				  endIdx = startIdx + key.length();
				  if(((startIdx <= 0) || (!Character.isLetter(originalSIG.charAt(startIdx - 1)) && !Character.isDigit(originalSIG.charAt(startIdx - 1))))
						  && ((endIdx >= originalSIG.length()) || (!Character.isLetter(originalSIG.charAt(endIdx)) && !Character.isDigit(originalSIG.charAt(endIdx))))) {
					  originalSIG.replace(startIdx, endIdx, entry.getValue());
					  endIdx = startIdx + 1;
				  }
			  }
		}
		
		return originalSIG.toString();
	}
	
	/**
	 * 
	 * @param input
	 * @return
	 */
	private static String normalizeSpecialTokens(String input) {
		StringBuffer buffer = new StringBuffer(input);
		String wordInBracket, wordTillBracket;
		String wordsWithBracket = "";
		Pattern pattern = Pattern.compile("[(].*?[)]");

		while(true) {
		    Matcher matcher = pattern.matcher(buffer);
		    if (matcher.find()) {
		    	wordInBracket = buffer.substring(matcher.start()+1, matcher.end()-1).trim();
		    	wordTillBracket = buffer.substring(0, matcher.start()).trim();

		    	if(wordTillBracket.endsWith(wordInBracket)) {
			    	buffer.replace(matcher.start(), matcher.end(), GeneralConstants.EMPTY_STRING);
		    	}
		    	else {
		    		wordsWithBracket += (GeneralConstants.SPACE_STRING + buffer.substring(matcher.start(), matcher.end()));
			    	buffer.replace(matcher.start(), matcher.end(), GeneralConstants.EMPTY_STRING);
		    	}
		    }
		    else {
		    	break; // stop if there is no match
		    }
	    }
	    input = buffer.toString() + wordsWithBracket;
		input = input.replaceAll("\\s+", " ").trim();

		return input;
	}

	/**
	 * 
	 * @param input
	 * @return
	 */
	private static String replaceOtherAbbr(String input) {
		StringBuffer originalSIG = new StringBuffer(input);

		// Replace other abbreviations 
		for (Map.Entry<String,String> entry : Dictionary.otherAbbrMap.entrySet()) {
			  String key = entry.getKey();
			  
			  int startIdx, endIdx;
			  endIdx = 0;
			  
			  while((startIdx = originalSIG.indexOf(key, endIdx)) != -1) {
				  endIdx = startIdx + key.length();
				  if(((startIdx <= 0) || (!Character.isLetter(originalSIG.charAt(startIdx - 1)) && !Character.isDigit(originalSIG.charAt(startIdx - 1))))
						  && ((endIdx >= originalSIG.length()) || (!Character.isLetter(originalSIG.charAt(endIdx)) && !Character.isDigit(originalSIG.charAt(endIdx))))) {
					  originalSIG.replace(startIdx, endIdx, entry.getValue());
					  endIdx = startIdx;
				  }
			  }
		}
		
		return originalSIG.toString();
	}
	
	/**
	 * 
	 * @param input
	 * @return
	 */
	private static String replaceSpecialChars(String input) {
		// Replace special chars per mapping
		for (Map.Entry<String,String> entry : Dictionary.specialCharMap.entrySet()) {
			input = input.replaceAll(entry.getKey(), entry.getValue());
		}
		
		StringBuffer originalSIG = new StringBuffer(input);
		Pattern pattern = Pattern.compile("[0-9][\\,][0-9]");
	    while(true) {
		    Matcher matcher = pattern.matcher(originalSIG);
		    if (matcher.find()) {
		    	originalSIG.replace(matcher.start() + 1, matcher.start() + 2, GeneralConstants.EMPTY_STRING);
		    }
		    else {
		    	break; // stop if there is no match
		    }
		}
	    pattern = Pattern.compile("[0-9]\\s+[-]\\s+[0-9]");
	    while(true) {
		    Matcher matcher = pattern.matcher(originalSIG);
		    if (matcher.find()) {
		    	originalSIG.replace(matcher.start() + 1, matcher.end() - 1, "-");
		    }
		    else {
		    	break; // stop if there is no match
		    }
		}
		/*pattern = Pattern.compile("[^0-9 ][\\.][^0-9 ]");
	    while(true) {
		    Matcher matcher = pattern.matcher(originalSIG);
		    if (matcher.find()) {
		    	originalSIG.replace(matcher.start() + 1, matcher.start() + 2, " . ");
		    }
		    else {
		    	break; // stop if there is no match
		    }
		    System.out.println(matcher);
		}*/
	    input = originalSIG.toString();

		// Replace comma (,) & dot (.) characters appropriately
		/*String token;
		StringTokenizer st = new StringTokenizer(input," ");
		input = "";
		String regexNumbersWithDot = "[0-9]*[\\.]([0-9]{1,3}[a-zA-Z]*)";
		//String regexNumbersWithComma = "[0-9]*[\\,]([0-9]{1,3}[a-zA-Z]*)";
		while(st.hasMoreTokens()) {
			token = st.nextToken(); 
			if(!(token.matches(regexNumbersWithDot))){
				token = token.replaceAll("[.]", " . ");
			}
			else {
				token = token.replaceAll("[,]", " , "); 
			}
			
			input = input + " " + token;
		}*/
		
		return input;
	}
	
	/**
	 * 
	 * @param input
	 * @return
	 */
	private static String removeIgnorableTokens(String input) {
		StringBuffer originalSIG = new StringBuffer(input);

		for(String ignorableToken: Dictionary.ignorableTokens) {
			  int startIdx, endIdx;
			  endIdx = 0;
			  
			  while((startIdx = originalSIG.indexOf(ignorableToken, endIdx)) != -1) {
				  endIdx = startIdx + ignorableToken.length();
				  if(((startIdx <= 0) || (!Character.isLetter(originalSIG.charAt(startIdx - 1)) && !Character.isDigit(originalSIG.charAt(startIdx - 1))))
						  && ((endIdx >= originalSIG.length()) || (!Character.isLetter(originalSIG.charAt(endIdx)) && !Character.isDigit(originalSIG.charAt(endIdx))))) {
					  originalSIG.replace(startIdx, endIdx, GeneralConstants.EMPTY_STRING);
					  endIdx = startIdx;
				  }
			  }
		}
		
		return originalSIG.toString().trim();
	}

	/**
	 * 
	 * @param input
	 * @return
	 */
	private static String parseSpecialFormats(String input) {
		StringBuffer originalSIG = new StringBuffer(input);

		// Parse format like Q24 
	    Pattern pattern = Pattern.compile("[a-zA-Z][0-9]");
	    while(true) {
		    Matcher matcher = pattern.matcher(originalSIG);
		    if (matcher.find()) {
		    	originalSIG.replace(matcher.start() + 1, matcher.start() + 1, " ");
		    }
		    else {
		    	break; // stop if there is no match
		    }
	    }

	    pattern = Pattern.compile("[0-9][a-zA-Z]");
	    while(true) {
		    Matcher matcher = pattern.matcher(originalSIG);
		    if (matcher.find()) {
		    	originalSIG.replace(matcher.start() + 1, matcher.start() + 1, " ");
		    }
		    else {
		    	break; // stop if there is no match
		    }
	    }

	    // Parse format like Q24H, Q2-6H..etc 
	    /*pattern = Pattern.compile("[Qq][0-9-]+[Hh]");
	    while(true) {
		    Matcher matcher = pattern.matcher(originalSIG);
		    if (matcher.find()) {
		    	originalSIG.replace(matcher.start(), matcher.start() + 1, " Q ");
		    	originalSIG.replace(matcher.end() + 1, matcher.end() + 2, " H ");
		    }
		    else {
		    	break; // stop if there is no match
		    }
	    }*/

	    // Parse format like "x30 days" 
	    pattern = Pattern.compile("[X][\\s]*[0-9]+");
	    while(true) {
		    Matcher matcher = pattern.matcher(originalSIG);
		    if (matcher.find()) {
		    	originalSIG.replace(matcher.start(), matcher.start() + 1, " FOR ");
		    }
		    else {
		    	break; // stop if there is no match
		    }
	    }
		
	    // Parse format like "2x a day" 
	    pattern = Pattern.compile("[0-9]+[\\s]*[X]");
	    while(true) {
		    Matcher matcher = pattern.matcher(originalSIG);
		    if (matcher.find()) {
		    	//System.out.println("end - " +matcher.end());
		    	originalSIG.replace(matcher.end() - 1, matcher.end(), " TIMES ");
		    }
		    else {
		    	break; // stop if there is no match
		    }
	    }
		
		return originalSIG.toString();
	}
	
	/**
	 * 
	 * @param input
	 * @param tokens
	 * @return
	 */
	private static String parseDrugStrength(String input, SIGToken tokens) {
		StringBuffer originalSIG = new StringBuffer(input);

		for(String dosageMetrics: Dictionary.dosageMetrics) {
			Pattern pattern = Pattern.compile("(((\\d+[./-])?(\\d+ TO )?\\d+)+[\\s]*)+" + dosageMetrics);
		    while(true) {
			    Matcher matcher = pattern.matcher(originalSIG);
			    if (matcher.find()) {
			    	tokens.getDrugStrength().add(originalSIG.substring(matcher.start(), matcher.end()));
			    	
			    	originalSIG.replace(matcher.start(), matcher.end(), GeneralConstants.EMPTY_STRING);
			    }
			    else {
			    	break; // stop if there is no match
			    }
		    }
		}
		
		return originalSIG.toString();
	}
	
	/**
	 * 
	 * @param input
	 * @param tokens
	 * @return
	 */
	private static String parseDuration(String input, SIGToken tokens) {
		StringBuffer originalSIG = new StringBuffer(input);

		for(String durationMetric: Dictionary.durationMetrics) {
			Pattern pattern = Pattern.compile("(((\\d+[./-])?(\\d+ TO )?\\d+)+[\\s]*)+" + durationMetric);
			Pattern rangePattern = Pattern.compile("((\\d+-)|(\\d+ TO ))+\\d+");
		    while(true) {
			    Matcher matcher = pattern.matcher(originalSIG);
			    if (matcher.find()) {
			    	String duration = originalSIG.substring(matcher.start(), matcher.end());
			    	tokens.getDuration().add(duration);

			    	if(rangePattern.matcher(duration).find()) {
		    			tokens.getDurationRange().add(duration);
		    		}
			    	
			    	originalSIG.replace(matcher.start(), matcher.end(), GeneralConstants.EMPTY_STRING);
			    }
			    else {
			    	break; // stop if there is no match
			    }
		    }
		}
		
		return originalSIG.toString();
	}
	
	/**
	 * 
	 * @param input
	 * @param tokens
	 * @return
	 */
	private static String parseDosageInfo(String input, SIGToken tokens) {
		StringBuffer originalSIG = new StringBuffer(input);

		for(String dosageForm: Dictionary.dosageForm) {
			Pattern pattern = Pattern.compile("(((\\d+[./-])?(\\d+ TO )?\\d+)+[\\s]*)+" + dosageForm);
			Pattern rangePattern = Pattern.compile("((\\d+-)|(\\d+ TO ))+\\d+");
		    while(true) {
			    Matcher matcher = pattern.matcher(originalSIG);
			    if (matcher.find()) {
			    	String dosage = originalSIG.substring(matcher.start(), matcher.end()).replace(dosageForm, "").trim();
			    	if(!GeneralConstants.EMPTY_STRING.equals(dosage)) {
			    		tokens.getDosage().add(dosage);
			    		if(rangePattern.matcher(dosage).find()) {
			    			tokens.getDosageRange().add(dosage);
			    		}
			    	}
			    	tokens.getDosageForm().add(dosageForm);
			    	
			    	originalSIG.replace(matcher.start(), matcher.end(), GeneralConstants.EMPTY_STRING);
			    }
			    else {
			    	break; // stop if there is no match
			    }
		    }
		}
		
		return originalSIG.toString();
	}

	/**
	 * 
	 * @param input
	 * @param tokens
	 * @return
	 */
	private static String parseFrequency(String input, SIGToken tokens) {
		StringBuffer originalSIG = new StringBuffer(input);

		Pattern rangePattern = Pattern.compile("((\\d+-)|(\\d+ TO ))+\\d+");
		for(String frequencySuffix: Dictionary.frequencySuffix) {
			Pattern pattern = Pattern.compile("((\\d+[./-])?(\\d+ TO )?\\d+)+[\\s]*" + frequencySuffix);
		    while(true) {
			    Matcher matcher = pattern.matcher(originalSIG);
			    if (matcher.find()) {
			    	String frequency = originalSIG.substring(matcher.start(), matcher.end());
			    	tokens.getFrequency().add(originalSIG.substring(matcher.start(), matcher.end()));
		    		if(rangePattern.matcher(frequency).find()) {
		    			tokens.getFrequencyRange().add(frequency);
		    		}
			    	
			    	originalSIG.replace(matcher.start(), matcher.end(), GeneralConstants.EMPTY_STRING);
			    }
			    else {
			    	break; // stop if there is no match
			    }
		    }
		}
		
		for(String frequencySuffixWithEvery: Dictionary.frequencySuffixWithEvery) {
			Pattern pattern = Pattern.compile("EVERY[\\s]*((\\d+[./-])?(\\d+ TO )?\\d+)?[\\s]*" + frequencySuffixWithEvery);
		    while(true) {
			    Matcher matcher = pattern.matcher(originalSIG);
			    if (matcher.find()) {
			    	String frequency = originalSIG.substring(matcher.start(), matcher.end());
			    	tokens.getFrequency().add(originalSIG.substring(matcher.start(), matcher.end()));
		    		if(rangePattern.matcher(frequency).find()) {
		    			tokens.getFrequencyRange().add(frequency);
		    		}
			    	
			    	originalSIG.replace(matcher.start(), matcher.end(), GeneralConstants.EMPTY_STRING);
			    }
			    else {
			    	break; // stop if there is no match
			    }
		    }
		}

		return originalSIG.toString();
	}
	
	/**
	 * 
	 * @param input
	 * @param tokens
	 * @return
	 */
	private static String matchDictionary(String input, SIGToken tokens) {
		StringBuffer originalSIG = new StringBuffer(input);
		String tokenType;

		for (Map.Entry<String,String> entry : Dictionary.tokenList.entrySet()) {
			String key = entry.getKey();
			 
			int startIdx, endIdx;
			endIdx = 0;
			  
			while((startIdx = originalSIG.indexOf(key, endIdx)) != -1) {
				endIdx = startIdx + key.length();
				if(((startIdx <= 0) || (!Character.isLetter(originalSIG.charAt(startIdx - 1)) && !Character.isDigit(originalSIG.charAt(startIdx - 1))))
						&& ((endIdx >= originalSIG.length()) || (!Character.isLetter(originalSIG.charAt(endIdx)) && !Character.isDigit(originalSIG.charAt(endIdx))))) {
					tokenType = entry.getValue();
					 
					if(Dictionary.VERB.equals(tokenType)) {
						tokens.getVerb().add(key);
					}
					else if(Dictionary.FREQUENCY.equals(tokenType)) {
						tokens.getFrequency().add(key);
					}
					else if(Dictionary.TIME_OF_DAY.equals(tokenType)) {
						tokens.getTimeOfDay().add(key);
					}
					else if(Dictionary.DURATION.equals(tokenType)) {
						tokens.getDuration().add(key);
					}
					else if(Dictionary.DOSAGE_FORM.equals(tokenType)) {
						tokens.getDosageForm().add(key);
					}
					else if(Dictionary.DRUG_STRENGTH.equals(tokenType)) {
						tokens.getDrugStrength().add(key);
					}
					else if(Dictionary.ROUTE_OF_ADMIN.equals(tokenType)) {
						tokens.getRouteOfAdmin().add(key);
					}
					else if(Dictionary.SITE_OF_ADMIN.equals(tokenType)) {
						tokens.getSiteOfAdmin().add(key);
					}
					else if(Dictionary.VEHICLE.equals(tokenType)) {
						tokens.getVehicle().add(key);
					}
					else if(Dictionary.INDICATION.equals(tokenType)) {
						tokens.getIndication().add(key);
					}
					else if(Dictionary.DIRECTION.equals(tokenType)) {
						tokens.getDirection().add(key);
					}
					else if(Dictionary.INSTRUCTION.equals(tokenType)) {
						tokens.getInstruction().add(key);
					}
					
					originalSIG.replace(startIdx, endIdx, GeneralConstants.EMPTY_STRING);
					endIdx = startIdx;
				}
			}
		}
		
		return originalSIG.toString();
	}
	
	/**
	 * Main method for testing
	 * @param args
	 */
	public static void main(String[] args) { 
		String input = "inhale 1 puff by inhalation route every 12 hours";
		/*input = input.toUpperCase();
		input = parseSpecialFormats(input);
		System.out.println("Input String after replacing latin abbr: " + input);
		
		SIGToken tokens = new SIGToken(); 
		input = parseDrugStrength(input, tokens);
		input = input.replaceAll("\\s+", " ").trim();

		input = parseDosageInfo(input, tokens);
		
		System.out.println("Output - " + tokens.getDrugStrength());
		System.out.println("Output - " + tokens.getDosage());*/
		
		input = "TAKE 1 CAPSULE ( 1 CAPSULE ) BY MOUTH EVERY 6 ( 6 ) HOURS ( HOUR ) AS NEEDED.";
		StringBuffer buffer = new StringBuffer(input);
		String wordInBracket, wordTillBracket;
		String wordsWithBracket = "";
		Pattern pattern = Pattern.compile("[(].*?[)]");
	    while(true) {
		    Matcher matcher = pattern.matcher(buffer);
		    if (matcher.find()) {
		    	wordInBracket = buffer.substring(matcher.start()+1, matcher.end()-1);
		    	wordTillBracket = buffer.substring(0, matcher.start());
		    	System.out.println("wordInBracket - " + wordInBracket);
		    	System.out.println("wordTillBracket - " + wordTillBracket);

		    	if(wordTillBracket.endsWith(wordInBracket)) {//wordTillBracket.matches(wordInBracket + "\\s+$")) {
			    	buffer.replace(matcher.start(), matcher.end(), GeneralConstants.EMPTY_STRING);
			    	System.out.println("found");
		    	}
		    	else {
		    		wordsWithBracket += (GeneralConstants.SPACE_STRING + buffer.substring(matcher.start(), matcher.end()));
			    	buffer.replace(matcher.start(), matcher.end(), GeneralConstants.EMPTY_STRING);
		    		System.out.println("not found");
		    	}
		    }
		    else {
		    	break; // stop if there is no match
		    }
	    }
	    input = buffer.toString() + wordsWithBracket;
	    System.out.println("Normalized - " + input);
	}
	
}