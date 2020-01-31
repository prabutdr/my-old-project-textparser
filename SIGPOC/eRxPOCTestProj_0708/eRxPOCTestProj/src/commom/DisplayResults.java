package commom;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DisplayResults
 */
public class DisplayResults extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    SIGConvertor sigConvertor =  new SIGConvertor();

	EntityParser dict = new EntityParser();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<SIGToken> resultList = new ArrayList<SIGToken>();
		List<String> inputShortSigList = new ArrayList<String>();
		List<Long> elapsedTimeList = new ArrayList<Long>();
		BufferedReader reader;
		PrintWriter out;
		//PrintWriter failOut, partialOut, completeOut;
		long startTime, endTime;
		long startTimeTotal = 0, endTimeTotal = 0;
		
		try {
		    //reader = new BufferedReader(new InputStreamReader(DisplayResults.class.getResourceAsStream("..\\IO\\Input_3.txt")));
			reader = new BufferedReader(new FileReader("C:\\Users\\153093\\Desktop\\Prabu\\RFP\\SIG_POC\\eRxPOCTestProj\\eRxPOCTestProj\\IO\\Input_2000.txt"));
		    out = new PrintWriter(new File("C:\\Users\\153093\\Desktop\\Prabu\\RFP\\SIG_POC\\eRxPOCTestProj\\eRxPOCTestProj\\IO\\Output.txt"));
		    //failOut = new PrintWriter(new File("C:\\Users\\153093\\Desktop\\Prabu\\RFP\\SIG_POC\\eRxPOCTestProj\\eRxPOCTestProj\\IO\\Input_Fail.txt"));
		    //partialOut = new PrintWriter(new File("C:\\Users\\153093\\Desktop\\Prabu\\RFP\\SIG_POC\\eRxPOCTestProj\\eRxPOCTestProj\\IO\\Input_Partial.txt"));
		    //completeOut = new PrintWriter(new File("C:\\Users\\153093\\Desktop\\Prabu\\RFP\\SIG_POC\\eRxPOCTestProj\\eRxPOCTestProj\\IO\\Input_Complete.txt"));
		    
		    int count = 0;
		    String line, inputShortSig;
		    String[] inputs;
		    startTimeTotal = new Date().getTime();
		    while((line = reader.readLine()) != null) {
		    	//line = scanSIG.nextLine();
		    	inputs = line.split("\\|");
		    	
		    	SIGToken tokens = new SIGToken();
		    	tokens.setInputSIG(inputs[0].trim());
		    	
		    	if(inputs.length > 1) {
		    		inputShortSig = inputs[1].trim();
		    	}
		    	else {
		    		inputShortSig = GeneralConstants.EMPTY_STRING;
		    	}
		    	
		    	if(inputs.length > 2) {
		    		tokens.setInDrugDosageFormCd(inputs[2].trim());
		    	}
		    	else {
		    		tokens.setInDrugDosageFormCd(GeneralConstants.EMPTY_STRING);
		    	}
		    	
		    	if(inputs.length > 3) {
		    		tokens.setInDrugStrength(inputs[3].trim());
		    	}
		    	else {
		    		tokens.setInDrugStrength(GeneralConstants.EMPTY_STRING);
		    	}
		    	
		    	/*System.out.println("Line - " + line);
		    	System.out.println(Arrays.toString(inputs));
		    	System.out.println("tokens.getInputSIG() - " + tokens.getInputSIG());
		    	System.out.println("inputShortSig - " + inputShortSig);
		    	System.out.println("tokens.getInDrugDosageFormCd() - " + tokens.getInDrugDosageFormCd());
		    	System.out.println("tokens.getInDrugStrength() - " + tokens.getInDrugStrength());*/
		    	
		    	startTime = new Date().getTime();
		    	tokens = dict.parse(tokens);
		    	sigConvertor.generateShortSIG(tokens);
		    	/*String shortSig = tokens.getShortSig();
		    	String longSig = tokens.getLongSig();
		    	String longSigFromShort = sigConvertor.toLongSIG(shortSig);
		    	if(!longSigFromShort.equals(longSig)) {
		    		System.out.println("No match - ");
		    		System.out.println("shortSig - " + shortSig);
		    		System.out.println("longSig - " + longSig);
		    		System.out.println("longSigFromShort - " + longSigFromShort);
		    	}*/
		    	endTime = new Date().getTime();
		    	 
		    	resultList.add(tokens);
		    	inputShortSigList.add(inputShortSig);
		    	elapsedTimeList.add(endTime - startTime);
		        	
		    	out.println("Record - " + ++count);
		    	out.println("Input SIG: " + tokens.getInputSIG());
		    	out.println("Recommended SIG: " + tokens.getStandardInputSIG());
		    	out.println("Non Parsed Tokens: " + tokens.getNonParsedTokens());
		    	out.println("Parse score: " + String.format("%.2f", tokens.getParseScore()));
		    	out.println("Verb: " + CommonUtils.toString(tokens.getVerb()));
		    	out.println("Frequency: " + CommonUtils.toString(tokens.getFrequency()));
		    	out.println("Frequency Range: " + CommonUtils.toString(tokens.getFrequencyRange()));
		    	out.println("TimeOfDay: " + CommonUtils.toString(tokens.getTimeOfDay()));
		    	out.println("Dosage: " + CommonUtils.toString(tokens.getDosage()));
		    	out.println("DosageForm: " + CommonUtils.toString(tokens.getDosageForm()));
		    	out.println("Dosage Range: " + CommonUtils.toString(tokens.getDosageRange()));
		    	out.println("Duration: " + CommonUtils.toString(tokens.getDuration()));
		    	out.println("Duration Range: " + CommonUtils.toString(tokens.getDurationRange()));
		    	out.println("DrugStrength: " + CommonUtils.toString(tokens.getDrugStrength()));
		    	out.println("RouteOfAdmin: " + CommonUtils.toString(tokens.getRouteOfAdmin()));
		    	out.println("SiteOfAdmin: " + CommonUtils.toString(tokens.getSiteOfAdmin()));
		    	out.println("Vehicle: " + CommonUtils.toString(tokens.getVehicle()));
		    	out.println("Indication: " + CommonUtils.toString(tokens.getIndication()));
		    	out.println("Direction: " + CommonUtils.toString(tokens.getDirection()));
		    	out.println("Instruction: " + CommonUtils.toString(tokens.getInstruction()));
		    	out.println("Short SIG: " + tokens.getShortSig());
		    	out.println();
		    	
		    	
		    	/*if(GeneralConstants.PARSE_STATUS_FAIL.equals(tokens.getParseStatus())) {
		    		failOut.println(line);
		    	}
		    	else if(GeneralConstants.PARSE_STATUS_PARTIAL.equals(tokens.getParseStatus())) {
		    		partialOut.println(line);
		    	}
		    	else if(GeneralConstants.PARSE_STATUS_COMPLETE.equals(tokens.getParseStatus())) {
		    		completeOut.println(line);
		    	}*/
		    }
		    endTimeTotal = new Date().getTime();
		    //scanSIG.close();
		    out.close();
		    reader.close();
		    /*failOut.close();
		    partialOut.close();
		    completeOut.close();*/
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		request.setAttribute("resultListBean", resultList);
		request.setAttribute("inputShortSigList", inputShortSigList);
		request.setAttribute("elapsedTimeList", elapsedTimeList);
		request.setAttribute("elapsedTimeTotal", (endTimeTotal - startTimeTotal));
		RequestDispatcher rd = request.getRequestDispatcher("ResultPage.jsp");
		rd.forward(request, response);
	}
	
	public static void main(String[] args) {
		String input = "TAKE 1 TAB PO QDAY FOR 10 DAYS BIN: 610020, GROUP 99992177, MEMBER 37780084306";
		String input1 = "6130474656005";
	}
}
	
