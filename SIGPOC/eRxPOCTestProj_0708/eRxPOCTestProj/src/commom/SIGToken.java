package commom;

import java.util.ArrayList;

public class SIGToken {
	//inputs
	private String inputSIG ="";
	private String inDrugDosageFormCd ="";
	private String inDrugStrength ="";

	private String standardInputSIG = "";
	private String nonParsedTokens = "";
	private String shortSig = "";
	private String longSig = "";
	
	private double parseScore;
	private String parseStatus;
	private String reasonForFail;
	
	private ArrayList<String> verb = new ArrayList<String>();
	private ArrayList<String> frequency = new ArrayList<String>();
	private ArrayList<String> frequencyRange = new ArrayList<String>();
	private ArrayList<String> timeOfDay = new ArrayList<String>();
	private ArrayList<String> dosage = new ArrayList<String>();
	private ArrayList<String> dosageRange = new ArrayList<String>();
	private ArrayList<String> dosageForm = new ArrayList<String>();
	private ArrayList<String> duration = new ArrayList<String>();
	private ArrayList<String> durationRange = new ArrayList<String>();
	private ArrayList<String> drugStrength = new ArrayList<String>();
	private ArrayList<String> routeOfAdmin = new ArrayList<String>();
	private ArrayList<String> siteOfAdmin = new ArrayList<String>();
	private ArrayList<String> vehicle = new ArrayList<String>();
	private ArrayList<String> indication = new ArrayList<String>();
	private ArrayList<String> direction = new ArrayList<String>();
	private ArrayList<String> instruction = new ArrayList<String>();
	
	/**
	 * @return the verb
	 */
	public ArrayList<String> getVerb() {
		return verb;
	}
	/**
	 * @param verb the verb to set
	 */
	public void setVerb(ArrayList<String> verb) {
		this.verb = verb;
	}
	/**
	 * @return the frequency
	 */
	public ArrayList<String> getFrequency() {
		return frequency;
	}
	/**
	 * @param frequency the frequency to set
	 */
	public void setFrequency(ArrayList<String> frequency) {
		this.frequency = frequency;
	}
	/**
	 * @return the timeOfDay
	 */
	public ArrayList<String> getTimeOfDay() {
		return timeOfDay;
	}
	/**
	 * @param timeOfDay the timeOfDay to set
	 */
	public void setTimeOfDay(ArrayList<String> timeOfDay) {
		this.timeOfDay = timeOfDay;
	}
	/**
	 * @return the dosage
	 */
	public ArrayList<String> getDosage() {
		return dosage;
	}
	/**
	 * @param dosage the dosage to set
	 */
	public void setDosage(ArrayList<String> dosage) {
		this.dosage = dosage;
	}
	/**
	 * @return the dosageForm
	 */
	public ArrayList<String> getDosageForm() {
		return dosageForm;
	}
	/**
	 * @param dosageForm the dosageForm to set
	 */
	public void setDosageForm(ArrayList<String> dosageForm) {
		this.dosageForm = dosageForm;
	}
	/**
	 * @return the duration
	 */
	public ArrayList<String> getDuration() {
		return duration;
	}
	/**
	 * @param duration the duration to set
	 */
	public void setDuration(ArrayList<String> duration) {
		this.duration = duration;
	}
	/**
	 * @return the drugStrength
	 */
	public ArrayList<String> getDrugStrength() {
		return drugStrength;
	}
	/**
	 * @param drugStrength the drugStrength to set
	 */
	public void setDrugStrength(ArrayList<String> drugStrength) {
		this.drugStrength = drugStrength;
	}
	/**
	 * @return the routeOfAdmin
	 */
	public ArrayList<String> getRouteOfAdmin() {
		return routeOfAdmin;
	}
	/**
	 * @param routeOfAdmin the routeOfAdmin to set
	 */
	public void setRouteOfAdmin(ArrayList<String> routeOfAdmin) {
		this.routeOfAdmin = routeOfAdmin;
	}
	/**
	 * @return the siteOfAdmin
	 */
	public ArrayList<String> getSiteOfAdmin() {
		return siteOfAdmin;
	}
	/**
	 * @param siteOfAdmin the siteOfAdmin to set
	 */
	public void setSiteOfAdmin(ArrayList<String> siteOfAdmin) {
		this.siteOfAdmin = siteOfAdmin;
	}
	/**
	 * @return the vehicle
	 */
	public ArrayList<String> getVehicle() {
		return vehicle;
	}
	/**
	 * @param vehicle the vehicle to set
	 */
	public void setVehicle(ArrayList<String> vehicle) {
		this.vehicle = vehicle;
	}
	/**
	 * @return the nonParsedTokens
	 */
	public String getNonParsedTokens() {
		return nonParsedTokens;
	}
	/**
	 * @param nonParsedTokens the nonParsedTokens to set
	 */
	public void setNonParsedTokens(String nonParsedTokens) {
		this.nonParsedTokens = nonParsedTokens;
	}
	/**
	 * @return the indication
	 */
	public ArrayList<String> getIndication() {
		return indication;
	}
	/**
	 * @param indication the indication to set
	 */
	public void setIndication(ArrayList<String> indication) {
		this.indication = indication;
	}
	/**
	 * @return the direction
	 */
	public ArrayList<String> getDirection() {
		return direction;
	}
	/**
	 * @param direction the direction to set
	 */
	public void setDirection(ArrayList<String> direction) {
		this.direction = direction;
	}
	/**
	 * @return the instruction
	 */
	public ArrayList<String> getInstruction() {
		return instruction;
	}
	/**
	 * @param instruction the instruction to set
	 */
	public void setInstruction(ArrayList<String> instruction) {
		this.instruction = instruction;
	}
	/**
	 * @return the shortSig
	 */
	public String getShortSig() {
		return shortSig;
	}
	/**
	 * @param shortSig the shortSig to set
	 */
	public void setShortSig(String shortSig) {
		this.shortSig = shortSig;
	}
	
	/**
	 * @return the frequencyRange
	 */
	public ArrayList<String> getFrequencyRange() {
		return frequencyRange;
	}
	/**
	 * @param frequencyRange the frequencyRange to set
	 */
	public void setFrequencyRange(ArrayList<String> frequencyRange) {
		this.frequencyRange = frequencyRange;
	}
	/**
	 * @return the dosageRange
	 */
	public ArrayList<String> getDosageRange() {
		return dosageRange;
	}
	/**
	 * @param dosageRange the dosageRange to set
	 */
	public void setDosageRange(ArrayList<String> dosageRange) {
		this.dosageRange = dosageRange;
	}
	/**
	 * @return the durationRange
	 */
	public ArrayList<String> getDurationRange() {
		return durationRange;
	}
	/**
	 * @param durationRange the durationRange to set
	 */
	public void setDurationRange(ArrayList<String> durationRange) {
		this.durationRange = durationRange;
	}
	/**
	 * @return the parseScore
	 */
	public double getParseScore() {
		return parseScore;
	}
	/**
	 * @param parseScore the parseScore to set
	 */
	public void setParseScore(double parseScore) {
		this.parseScore = parseScore;
	}
	/**
	 * @return the parseStatus
	 */
	public String getParseStatus() {
		return parseStatus;
	}
	/**
	 * @param parseStatus the parseStatus to set
	 */
	public void setParseStatus(String parseStatus) {
		this.parseStatus = parseStatus;
	}
	/**
	 * @return the reasonForFail
	 */
	public String getReasonForFail() {
		return reasonForFail;
	}
	/**
	 * @param reasonForFail the reasonForFail to set
	 */
	public void setReasonForFail(String reasonForFail) {
		this.reasonForFail = reasonForFail;
	}
	/**
	 * @return the inputSIG
	 */
	public String getInputSIG() {
		return inputSIG;
	}
	/**
	 * @param inputSIG the inputSIG to set
	 */
	public void setInputSIG(String inputSIG) {
		this.inputSIG = inputSIG;
	}
	/**
	 * @return the standardInputSIG
	 */
	public String getStandardInputSIG() {
		return standardInputSIG;
	}
	/**
	 * @param standardInputSIG the standardInputSIG to set
	 */
	public void setStandardInputSIG(String standardInputSIG) {
		this.standardInputSIG = standardInputSIG;
	}
	/**
	 * @return the longSig
	 */
	public String getLongSig() {
		return longSig;
	}
	/**
	 * @param longSig the longSig to set
	 */
	public void setLongSig(String longSig) {
		this.longSig = longSig;
	}
	/**
	 * @return the inDrugDosageFormCd
	 */
	public String getInDrugDosageFormCd() {
		return inDrugDosageFormCd;
	}
	/**
	 * @param inDrugDosageFormCd the inDrugDosageFormCd to set
	 */
	public void setInDrugDosageFormCd(String inDrugDosageFormCd) {
		this.inDrugDosageFormCd = inDrugDosageFormCd;
	}
	/**
	 * @return the inDrugStrength
	 */
	public String getInDrugStrength() {
		return inDrugStrength;
	}
	/**
	 * @param inDrugStrength the inDrugStrength to set
	 */
	public void setInDrugStrength(String inDrugStrength) {
		this.inDrugStrength = inDrugStrength;
	}
}
