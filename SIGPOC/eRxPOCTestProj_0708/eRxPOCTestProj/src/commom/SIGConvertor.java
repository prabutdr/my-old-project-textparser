package commom;

import java.util.Map;

public class SIGConvertor {
	/**
	 * 
	 * @param bean
	 * @return
	 */
	public String generateShortSIG(SIGToken sigToken) {
		formulateLongSIG(sigToken);

		String shortSIG = toShortSIG(sigToken.getLongSig());
		
		sigToken.setShortSig(shortSIG);
		
		return shortSIG;
	}

	/**
	 * 
	 * @param sigToken
	 * @return
	 */
	public String formulateLongSIG(SIGToken sigToken) {
		String verb = CommonUtils.toString(sigToken.getVerb());
		String dosage = CommonUtils.toString(sigToken.getDosage());
		String frequency = CommonUtils.toString(sigToken.getFrequency());
		String timeOfDay = CommonUtils.toString(sigToken.getTimeOfDay());
		String dosageForm = CommonUtils.toString(sigToken.getDosageForm());
		String drugStrength = CommonUtils.toString(sigToken.getDrugStrength());
		String vehicle = CommonUtils.toString(sigToken.getVehicle());
		String duration = CommonUtils.toString(sigToken.getDuration());
		String routeOfAdmin = CommonUtils.toString(sigToken.getRouteOfAdmin());
		String siteOfAdmin = CommonUtils.toString(sigToken.getSiteOfAdmin());
		String direction = CommonUtils.toString(sigToken.getDirection());
		String indication = CommonUtils.toString(sigToken.getIndication());

		String originalSIG = verb 
				+ " " + dosage 
				+ " " + dosageForm 
				+ " " + drugStrength 
				+ " " + (routeOfAdmin.equals("") ? routeOfAdmin : "BY " + routeOfAdmin) 
				+ " " + siteOfAdmin 
				+ " " + frequency
				+ " " + timeOfDay 
				+ " " + vehicle 
				+ " " + (duration.equalsIgnoreCase("") ? duration : "X " + duration)
				+ " " + (indication.equals("") ? indication : "FOR " + indication)
				+ " " + direction;
		originalSIG = originalSIG.replaceAll(";", " ");
		originalSIG = originalSIG.replaceAll("\\s+", " ").trim();

		sigToken.setLongSig(originalSIG);
		
		return originalSIG;
	}
	
	/**
	 * 
	 * @param input
	 * @return
	 */
	public static String toShortSIG(String input) {
		StringBuffer originalSIG = new StringBuffer(input);

		int startIdx, endIdx;
		for (Map.Entry<String, String> entry : Dictionary.shortSigMap.entrySet()) {
			String key = entry.getKey();
			endIdx = 0;

			while ((startIdx = originalSIG.indexOf(key, endIdx)) != -1) {
				endIdx = startIdx + key.length();
				if (((startIdx <= 0) || (!Character.isLetter(originalSIG.charAt(startIdx - 1)) && !Character.isDigit(originalSIG.charAt(startIdx - 1))))
						&& ((endIdx >= originalSIG.length()) || (!Character.isLetter(originalSIG.charAt(endIdx)) && !Character.isDigit(originalSIG.charAt(endIdx))))) {
					originalSIG.replace(startIdx, endIdx, entry.getValue());
					endIdx = startIdx + 1;
				}
			}
		}

		input = originalSIG.toString();
		input = input.replaceAll("\\s+", " ").trim();
		
		return input;
	}

	/**
	 * 
	 * @param input
	 * @return
	 */
	public static String toLongSIG(String input) {
		StringBuffer originalSIG = new StringBuffer(input);

		int startIdx, endIdx;
		for (Map.Entry<String, String> entry : Dictionary.longSigMap.entrySet()) {
			String key = entry.getKey();
			endIdx = 0;

			while ((startIdx = originalSIG.indexOf(key, endIdx)) != -1) {
				endIdx = startIdx + key.length();
				if (((startIdx <= 0) || (!Character.isLetter(originalSIG.charAt(startIdx - 1)) && !Character.isDigit(originalSIG.charAt(startIdx - 1))))
						&& ((endIdx >= originalSIG.length()) || (!Character.isLetter(originalSIG.charAt(endIdx)) && !Character.isDigit(originalSIG.charAt(endIdx))))) {
					originalSIG.replace(startIdx, endIdx, entry.getValue());
					endIdx = startIdx + 1;
				}
			}
		}

		input = originalSIG.toString();
		input = input.replaceAll("\\s+", " ").trim();
		
		return input;
	}
}
