/**
 * 
 */
package com.cts.textparser.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.cts.textparser.constant.ActiveInd;
import com.cts.textparser.constant.ErrorConstants;
import com.cts.textparser.constant.SortInd;
import com.cts.textparser.to.AttributeTO;
import com.cts.textparser.to.InsertInPatternItem;
import com.cts.textparser.to.ListTO;
import com.cts.textparser.to.MapTO;
import com.cts.textparser.to.OperationTO;
import com.cts.textparser.to.ReplaceInPatternItem;
import com.cts.textparser.to.ParseActivityItem;

/**
 * Utility class which will provide utility methods to read/write data 
 * from configured workbook  
 * 
 * @author 153093
 *
 */
@Component("workbookUtil")
public class WorkbookUtil {

	private static final Logger LOGGER = Logger.getLogger(WorkbookUtil.class); 

	/**
	 * Resource to hold file reference of dictionary file
	 */
	@Value("${textparser.dictionary.file}")
	private Resource workbookFile;
	
	@Autowired(required = true)
	@Qualifier("applicationUtil")
	private ApplicationUtil applicationUtil;
	
	/**
	 * Cell data formatter to parse cell values from work book
	 */
	private static final DataFormatter DATAFORMATTER = new DataFormatter();
	
	/**
	 * Method to get configured dictionary lists from given sheet name
	 * 
	 * @param 	sheetName
	 * 			Sheet name in the work book			
	 * 
	 * @return	List of ListTO objects which represent the complete lists.
	 * 
	 * @throws 	TextParserException 
	 */
	public List<ListTO<String>> getListsFromSheet(String sheetName) throws TextParserException {
		List<ListTO<String>> listTOs = new ArrayList<>();

		try {
			HSSFSheet sheet = this.getSheet(sheetName);
			
			if(sheet == null) {
				LOGGER.debug("getListsFromSheet - Sheet name not found in the workbook - " + sheetName);
				throw applicationUtil.buildTextParserException(ErrorConstants.UNABLE_TO_READ_FROM_WORKBOOK);
			}
			
			// Iterate through sheet and get ListTO items if any
			HSSFRow row = sheet.getRow(0);
			if(row != null) {
				int columnIdx = 0;
				String cellValue = DATAFORMATTER.formatCellValue(row.getCell(columnIdx));
				ListTO<String> listTO;
				while(StringUtils.isNotBlank(cellValue)) {
					listTO = getListTOFromColumn(sheet, columnIdx);
					listTOs.add(listTO);
					
					columnIdx += 2;
					cellValue = DATAFORMATTER.formatCellValue(row.getCell(columnIdx));
				}
			}
			LOGGER.debug("getListsFromSheet - Completed - List Count - " + listTOs.size());
		} catch (Exception e) {
			LOGGER.debug("getListsFromSheet - Exception occurred");
			throw applicationUtil.buildTextParserException(ErrorConstants.UNABLE_TO_READ_FROM_WORKBOOK, null, e);
		}
		
		return listTOs;
	}		

	/**
	 * Method to get configured dictionary maps from given sheet name
	 * 
	 * @param 	sheetName
	 * 			Sheet name in the work book			
	 * 
	 * @return	List of MapTO objects which represent the complete maps.
	 * 
	 * @throws 	TextParserException 
	 * 
	 */
	public List<MapTO> getMapsFromSheet(String sheetName) throws TextParserException {
		List<MapTO> mapTOs = new ArrayList<>();

		try {
			HSSFSheet sheet = this.getSheet(sheetName);
			
			if(sheet == null) {
				LOGGER.debug("getMapsFromSheet - Sheet name not found in the workbook - " + sheetName);
				throw applicationUtil.buildTextParserException(ErrorConstants.UNABLE_TO_READ_FROM_WORKBOOK);
			}
			
			// Iterate through sheet and get MapTO items if any
			HSSFRow row = sheet.getRow(0);
			if(row != null) {
				int columnIdx = 0;
				String cellValue = DATAFORMATTER.formatCellValue(row.getCell(columnIdx));
				MapTO mapTO;
				while(StringUtils.isNotBlank(cellValue)) {
					mapTO = getMapTOFromColumn(sheet, columnIdx);
					mapTOs.add(mapTO);
					
					columnIdx += 2;
					cellValue = DATAFORMATTER.formatCellValue(row.getCell(columnIdx));
				}
			}
			LOGGER.debug("getMapsFromSheet - Completed - Map Count - " + mapTOs.size());
		} catch (Exception e) {
			LOGGER.debug("getMapsFromSheet - Exception occurred");
			throw applicationUtil.buildTextParserException(ErrorConstants.UNABLE_TO_READ_FROM_WORKBOOK, null, e);
		}
		
		return mapTOs;
	}		

	/**
	 * Method to get configured dictionary replaceInPattern lists from given sheet name
	 * 
	 * @param 	sheetName
	 * 			Sheet name in the work book			
	 * 
	 * @return	List of ListTO replaceInPattern which represent the complete lists.
	 * 
	 * @throws 	TextParserException 
	 */
	public List<ListTO<ReplaceInPatternItem>> getReplaceInPatternsFromSheet(String sheetName) throws TextParserException {
		List<ListTO<ReplaceInPatternItem>> listTOs = new ArrayList<>();

		try {
			HSSFSheet sheet = this.getSheet(sheetName);
			
			if(sheet == null) {
				LOGGER.debug("getReplaceInPatternsFromSheet - Sheet name not found in the workbook - " + sheetName);
				throw applicationUtil.buildTextParserException(ErrorConstants.UNABLE_TO_READ_FROM_WORKBOOK);
			}
			
			// Iterate through sheet and get ListTO items if any
			HSSFRow row = sheet.getRow(0);
			if(row != null) {
				int columnIdx = 0;
				String cellValue = DATAFORMATTER.formatCellValue(row.getCell(columnIdx));
				ListTO<ReplaceInPatternItem> listTO;
				while(StringUtils.isNotBlank(cellValue)) {
					listTO = getReplaceInPatternFromColumn(sheet, columnIdx);
					listTOs.add(listTO);
					
					columnIdx += 3;
					cellValue = DATAFORMATTER.formatCellValue(row.getCell(columnIdx));
				}
			}
			LOGGER.debug("getReplaceInPatternsFromSheet - Completed - List Count - " + listTOs.size());
		} catch (Exception e) {
			LOGGER.debug("getReplaceInPatternsFromSheet - Exception occurred");
			throw applicationUtil.buildTextParserException(ErrorConstants.UNABLE_TO_READ_FROM_WORKBOOK, null, e);
		}
		
		return listTOs;
	}		

	/**
	 * Method to get configured dictionary insertInPattern lists from given sheet name
	 * 
	 * @param 	sheetName
	 * 			Sheet name in the work book			
	 * 
	 * @return	List of ListTO insertInPattern which represent the complete lists.
	 * 
	 * @throws 	TextParserException 
	 */
	public List<ListTO<InsertInPatternItem>> getInsertInPatternsFromSheet(String sheetName) throws TextParserException {
		List<ListTO<InsertInPatternItem>> listTOs = new ArrayList<>();

		try {
			HSSFSheet sheet = this.getSheet(sheetName);
			
			if(sheet == null) {
				LOGGER.debug("getInsertInPatternsFromSheet - Sheet name not found in the workbook - " + sheetName);
				throw applicationUtil.buildTextParserException(ErrorConstants.UNABLE_TO_READ_FROM_WORKBOOK);
			}
			
			// Iterate through sheet and get ListTO items if any
			HSSFRow row = sheet.getRow(0);
			if(row != null) {
				int columnIdx = 0;
				String cellValue = DATAFORMATTER.formatCellValue(row.getCell(columnIdx));
				ListTO<InsertInPatternItem> listTO;
				while(StringUtils.isNotBlank(cellValue)) {
					listTO = getInsertInPatternFromColumn(sheet, columnIdx);
					listTOs.add(listTO);
					
					columnIdx += 3;
					cellValue = DATAFORMATTER.formatCellValue(row.getCell(columnIdx));
				}
			}
			LOGGER.debug("getInsertInPatternsFromSheet - Completed - List Count - " + listTOs.size());
		} catch (Exception e) {
			LOGGER.debug("getInsertInPatternsFromSheet - Exception occurred");
			throw applicationUtil.buildTextParserException(ErrorConstants.UNABLE_TO_READ_FROM_WORKBOOK, null, e);
		}
		
		return listTOs;
	}		

	/**
	 * Method to get configured dictionary attributes from given sheet name
	 * 
	 * @param 	sheetName
	 * 			Sheet name in the work book			
	 * 
	 * @return	List of attributes
	 * 
	 * @throws 	TextParserException 
	 */
	public List<AttributeTO> getAttributesFromSheet(String sheetName) throws TextParserException {
		List<AttributeTO> attributes = new ArrayList<>();

		try {
			HSSFSheet sheet = this.getSheet(sheetName);
			
			if(sheet == null) {
				LOGGER.debug("getAttributesFromSheet - Sheet name not found in the workbook - " + sheetName);
				throw applicationUtil.buildTextParserException(ErrorConstants.UNABLE_TO_READ_FROM_WORKBOOK);
			}
			
			// Iterate through sheet and get attributes if any
			int rowIdx = 1;
			String cellValue;
			HSSFRow row = sheet.getRow(rowIdx);
			while (row != null) {
				cellValue = DATAFORMATTER.formatCellValue(row.getCell(0));
				if (StringUtils.isBlank(cellValue)) {
					break;
				}
				
				attributes.add(getAttributeFromRow(sheet, rowIdx));
					
				rowIdx++;
				row = sheet.getRow(rowIdx);
			}
			LOGGER.debug("getAttributesFromSheet - Completed - Attribute Count - " + attributes.size());
		} catch (Exception e) {
			LOGGER.debug("getAttributesFromSheet - Exception occurred");
			throw applicationUtil.buildTextParserException(ErrorConstants.UNABLE_TO_READ_FROM_WORKBOOK, null, e);
		}
		
		return attributes;
	}		

	/**
	 * Method to get configured dictionary operations from given sheet name
	 * 
	 * @param 	sheetName
	 * 			Sheet name in the work book			
	 * 
	 * @return	List of operations
	 * 
	 * @throws 	TextParserException 
	 */
	public List<OperationTO> getOperationsFromSheet(String sheetName) throws TextParserException {
		List<OperationTO> operations = new ArrayList<>();

		try {
			HSSFSheet sheet = this.getSheet(sheetName);
			
			if(sheet == null) {
				LOGGER.debug("getOperationsFromSheet - Sheet name not found in the workbook - " + sheetName);
				throw applicationUtil.buildTextParserException(ErrorConstants.UNABLE_TO_READ_FROM_WORKBOOK);
			}
			
			// Iterate through sheet and get operations if any
			int rowIdx = 1;
			String cellValue;
			HSSFRow row = sheet.getRow(rowIdx);
			while (row != null) {
				cellValue = DATAFORMATTER.formatCellValue(row.getCell(0));
				if (StringUtils.isBlank(cellValue)) {
					break;
				}
				
				operations.add(getOperationFromRow(sheet, rowIdx));
				rowIdx++;
				row = sheet.getRow(rowIdx);
			}
			LOGGER.debug("getOperationsFromSheet - Completed - Operation Count - " + operations.size());
		} catch (Exception e) {
			LOGGER.debug("getOperationsFromSheet - Exception occurred");
			throw applicationUtil.buildTextParserException(ErrorConstants.UNABLE_TO_READ_FROM_WORKBOOK, null, e);
		}
		
		return operations;
	}		

	/**
	 * Method to get configured dictionary parse activities from given sheet name
	 * 
	 * @param 	sheetName
	 * 			Sheet name in the work book			
	 * 
	 * @return	List of parse activities
	 * 
	 * @throws 	TextParserException 
	 */
	public List<ParseActivityItem> getParseActivitiesFromSheet(String sheetName) throws TextParserException {
		List<ParseActivityItem> parseActivities = new ArrayList<>();

		try {
			HSSFSheet sheet = this.getSheet(sheetName);
			
			if(sheet == null) {
				LOGGER.debug("getParseActivitiesFromSheet - Sheet name not found in the workbook - " + sheetName);
				throw applicationUtil.buildTextParserException(ErrorConstants.UNABLE_TO_READ_FROM_WORKBOOK);
			}
			
			// Iterate through sheet and get operations if any
			int rowIdx = 1;
			String cellValue;
			HSSFRow row = sheet.getRow(rowIdx);
			while (row != null) {
				cellValue = DATAFORMATTER.formatCellValue(row.getCell(0));
				if (StringUtils.isBlank(cellValue)) {
					break;
				}
				
				parseActivities.add(getParseActivityFromRow(sheet, rowIdx));
				rowIdx++;
				row = sheet.getRow(rowIdx);
			}
			LOGGER.debug("getParseActivitiesFromSheet - Completed - Parse activity Count - " + parseActivities.size());
		} catch (Exception e) {
			LOGGER.debug("getParseActivitiesFromSheet - Exception occurred");
			throw applicationUtil.buildTextParserException(ErrorConstants.UNABLE_TO_READ_FROM_WORKBOOK, null, e);
		}
		
		return parseActivities;
	}		

	/**
	 * Helper method to read a list from given sheet & column index
	 * 
	 * @param 	sheet
	 * 			Sheet name in the work book
	 * 
	 * @param 	columnIdx
	 * 			Column index where list starts 
	 * 
	 * @return	ListTO representation of the list
	 * 
	 */
	private ListTO<String> getListTOFromColumn(HSSFSheet sheet, int columnIdx) {
		ListTO<String> listTO = new ListTO<String>();
		int rowIdx = 0;
		
		// To retrieve list properties
		listTO.setName(DATAFORMATTER.formatCellValue(sheet.getRow(rowIdx++).getCell(columnIdx + 1)));
		listTO.setDescription(DATAFORMATTER.formatCellValue(sheet.getRow(rowIdx++).getCell(columnIdx + 1)));
		String sortInd = DATAFORMATTER.formatCellValue(sheet.getRow(rowIdx++).getCell(columnIdx + 1));
		if(StringUtils.isBlank(sortInd)) {
			listTO.setSortInd(SortInd.NO_SORT);
		}
		else {
			listTO.setSortInd(SortInd.valueOf(sortInd));
		}
		
		// Retrieve list values
		List<String> list = new ArrayList<>();
		String cellValue;
		rowIdx++; // Skip a row
		HSSFRow row = sheet.getRow(rowIdx++);
		while(row != null) {
			cellValue = DATAFORMATTER.formatCellValue(row.getCell(columnIdx));
			if(StringUtils.isNotBlank(cellValue)) {
				list.add(cellValue);
			}
			else {
				break;
			}
			row = sheet.getRow(rowIdx++);
		}
		listTO.setValue(list);
		
		return listTO;
	}

	/**
	 * Helper method to read a map from given sheet & column index
	 * 
	 * @param 	sheet
	 * 			Sheet name in the work book
	 * 
	 * @param 	columnIdx
	 * 			Column index where map starts 
	 * 
	 * @return	MapTO representation of the list
	 * 
	 */
	private MapTO getMapTOFromColumn(HSSFSheet sheet, int columnIdx) {
		MapTO mapTO = new MapTO();
		int rowIdx = 0;
		int valueColumnIdx = columnIdx + 1;
		
		// To retrieve list properties
		mapTO.setName(DATAFORMATTER.formatCellValue(sheet.getRow(rowIdx++).getCell(valueColumnIdx)));
		mapTO.setDescription(DATAFORMATTER.formatCellValue(sheet.getRow(rowIdx++).getCell(valueColumnIdx)));
		String sortInd = DATAFORMATTER.formatCellValue(sheet.getRow(rowIdx++).getCell(valueColumnIdx));
		if(StringUtils.isBlank(sortInd)) {
			mapTO.setSortInd(SortInd.NO_SORT);
		}
		else {
			mapTO.setSortInd(SortInd.valueOf(sortInd));
		}
		
		// Retrieve list values
		Map<String, String> map = new HashMap<String, String>();
		String key;
		rowIdx++; // Skip a row
		HSSFRow row = sheet.getRow(rowIdx++);
		while(row != null) {
			key = DATAFORMATTER.formatCellValue(row.getCell(columnIdx));
			if(StringUtils.isNotBlank(key)) {
				map.put(key, DATAFORMATTER.formatCellValue(row.getCell(valueColumnIdx)));
			}
			else {
				break;
			}
			row = sheet.getRow(rowIdx++);
		}
		mapTO.setValue(map);
		
		return mapTO;
	}

	/**
	 * Helper method to read a replaceInPattern from given sheet & column index
	 * 
	 * @param 	sheet
	 * 			Sheet name in the work book
	 * 
	 * @param 	columnIdx
	 * 			Column index where replaceInPattern starts 
	 * 
	 * @return	ListTO representation of the replaceInPatterns
	 * 
	 */
	private ListTO<ReplaceInPatternItem> getReplaceInPatternFromColumn(HSSFSheet sheet, int columnIdx) {
		ListTO<ReplaceInPatternItem> listTO = new ListTO<ReplaceInPatternItem>();
		int rowIdx, lookupValueIdx, targetValueIdx, replacementIdx;
		
		// To retrieve list properties
		rowIdx = 0;
		lookupValueIdx = columnIdx;
		targetValueIdx = lookupValueIdx + 1;
		replacementIdx = targetValueIdx + 1;
		listTO.setName(DATAFORMATTER.formatCellValue(sheet.getRow(rowIdx++).getCell(columnIdx + 1)));
		listTO.setDescription(DATAFORMATTER.formatCellValue(sheet.getRow(rowIdx++).getCell(columnIdx + 1)));
		String sortInd = DATAFORMATTER.formatCellValue(sheet.getRow(rowIdx++).getCell(columnIdx + 1));
		if(StringUtils.isBlank(sortInd)) {
			listTO.setSortInd(SortInd.NO_SORT);
		}
		else {
			listTO.setSortInd(SortInd.valueOf(sortInd));
		}
		
		// Retrieve list values
		List<ReplaceInPatternItem> list = new ArrayList<>();
		ReplaceInPatternItem replaceInPatternItem;
		String lookupValue;
		rowIdx++; // Skip a row
		HSSFRow row = sheet.getRow(rowIdx++);
		while(row != null) {
			lookupValue = DATAFORMATTER.formatCellValue(row.getCell(lookupValueIdx));
			if(StringUtils.isNotBlank(lookupValue)) {
				replaceInPatternItem = new ReplaceInPatternItem();
				replaceInPatternItem.setLookupValue(lookupValue);
				replaceInPatternItem.setTargetValue(DATAFORMATTER.formatCellValue(row.getCell(targetValueIdx)));
				replaceInPatternItem.setReplacement(DATAFORMATTER.formatCellValue(row.getCell(replacementIdx)));
				
				list.add(replaceInPatternItem);
			}
			else {
				break;
			}
			row = sheet.getRow(rowIdx++);
		}
		listTO.setValue(list);
		
		return listTO;
	}

	/**
	 * Helper method to read a insertInPattern from given sheet & column index
	 * 
	 * @param 	sheet
	 * 			Sheet name in the work book
	 * 
	 * @param 	columnIdx
	 * 			Column index where insertInPattern starts 
	 * 
	 * @return	ListTO representation of the insertInPatterns
	 * 
	 */
	private ListTO<InsertInPatternItem> getInsertInPatternFromColumn(HSSFSheet sheet, int columnIdx) {
		ListTO<InsertInPatternItem> listTO = new ListTO<InsertInPatternItem>();
		int rowIdx, lookupValueIdx, targetPositionIdx, insertValueIdx;
		
		// To retrieve list properties
		rowIdx = 0;
		lookupValueIdx = columnIdx;
		targetPositionIdx = lookupValueIdx + 1;
		insertValueIdx = targetPositionIdx + 1;
		listTO.setName(DATAFORMATTER.formatCellValue(sheet.getRow(rowIdx++).getCell(columnIdx + 1)));
		listTO.setDescription(DATAFORMATTER.formatCellValue(sheet.getRow(rowIdx++).getCell(columnIdx + 1)));
		String sortInd = DATAFORMATTER.formatCellValue(sheet.getRow(rowIdx++).getCell(columnIdx + 1));
		if(StringUtils.isBlank(sortInd)) {
			listTO.setSortInd(SortInd.NO_SORT);
		}
		else {
			listTO.setSortInd(SortInd.valueOf(sortInd));
		}
		
		// Retrieve list values
		List<InsertInPatternItem> list = new ArrayList<>();
		InsertInPatternItem insertInPatternItem;
		String value;
		rowIdx++; // Skip a row
		HSSFRow row = sheet.getRow(rowIdx++);
		while(row != null) {
			value = DATAFORMATTER.formatCellValue(row.getCell(lookupValueIdx));
			if(StringUtils.isNotBlank(value)) {
				insertInPatternItem = new InsertInPatternItem();
				insertInPatternItem.setLookupValue(value);
				value = DATAFORMATTER.formatCellValue(row.getCell(targetPositionIdx));
				if(NumberUtils.isNumber(value)) {
					insertInPatternItem.setTargetPosition(Integer.valueOf(value));
				}
				else {
					insertInPatternItem.setTargetPosition(0); // default target position
				}
				insertInPatternItem.setInsertValue(DATAFORMATTER.formatCellValue(row.getCell(insertValueIdx)));
				
				list.add(insertInPatternItem);
			}
			else {
				break;
			}
			row = sheet.getRow(rowIdx++);
		}
		listTO.setValue(list);
		
		return listTO;
	}

	/**
	 * Helper method to read a attribute detail from given sheet & row index
	 * 
	 * @param 	sheet
	 * 			Sheet name in the work book
	 * 
	 * @param 	rowIdx
	 * 			Row index where attribute detail starts 
	 * 
	 * @return	AttributeTO representation of the attribute
	 * 
	 */
	private AttributeTO getAttributeFromRow(HSSFSheet sheet, int rowIdx) {
		AttributeTO attributeTO = new AttributeTO();
		int columnIdx;
		
		// To retrieve attribute properties
		columnIdx = 0;
		HSSFRow row = sheet.getRow(rowIdx);
		attributeTO.setName(DATAFORMATTER.formatCellValue(row.getCell(columnIdx++)));
		attributeTO.setDescription(DATAFORMATTER.formatCellValue(row.getCell(columnIdx++)));
		
		return attributeTO;
	}

	/**
	 * Helper method to read a operation detail from given sheet & row index
	 * 
	 * @param 	sheet
	 * 			Sheet name in the work book
	 * 
	 * @param 	rowIdx
	 * 			Row index where operation detail starts 
	 * 
	 * @return	OperationTO representation of the operation
	 * 
	 */
	private OperationTO getOperationFromRow(HSSFSheet sheet, int rowIdx) {
		OperationTO operationTO = new OperationTO();
		int columnIdx;
		
		// To retrieve attribute properties
		columnIdx = 0;
		HSSFRow row = sheet.getRow(rowIdx);
		operationTO.setName(DATAFORMATTER.formatCellValue(row.getCell(columnIdx++)));
		operationTO.setDescription(DATAFORMATTER.formatCellValue(row.getCell(columnIdx++)));
		operationTO.setClassName(DATAFORMATTER.formatCellValue(row.getCell(columnIdx++)));
		operationTO.setMethodName(DATAFORMATTER.formatCellValue(row.getCell(columnIdx++)));
		
		// Retrieve parameter list values
		List<String> parameters = new ArrayList<>();
		String paramValue;
		paramValue = DATAFORMATTER.formatCellValue(row.getCell(columnIdx++));
		while(StringUtils.isNotBlank(paramValue)) {
			parameters.add(paramValue);

			paramValue = DATAFORMATTER.formatCellValue(row.getCell(columnIdx++));
		}
		operationTO.setParameters(parameters);
		
		return operationTO;
	}

	/**
	 * Helper method to read a parseActivity detail from given sheet & row index
	 * 
	 * @param 	sheet
	 * 			Sheet name in the work book
	 * 
	 * @param 	rowIdx
	 * 			Row index where parseActivity detail starts 
	 * 
	 * @return	{@link ParseActivityItem} representation of the parseActivity
	 * 
	 */
	private ParseActivityItem getParseActivityFromRow(HSSFSheet sheet, int rowIdx) {
		ParseActivityItem parseActivityItem = new ParseActivityItem();
		int columnIdx;
		
		// To retrieve attribute properties
		columnIdx = 0;
		HSSFRow row = sheet.getRow(rowIdx);
		parseActivityItem.setName(DATAFORMATTER.formatCellValue(row.getCell(columnIdx++)));
		parseActivityItem.setDescription(DATAFORMATTER.formatCellValue(row.getCell(columnIdx++)));
		String activeInd = DATAFORMATTER.formatCellValue(row.getCell(columnIdx++));
		if(StringUtils.isBlank(activeInd)) {
			parseActivityItem.setActiveInd(ActiveInd.YES);
		}
		else {
			parseActivityItem.setActiveInd(ActiveInd.fromString(activeInd));
		}
		parseActivityItem.setOperation(DATAFORMATTER.formatCellValue(row.getCell(columnIdx++)));
		
		// Retrieve parameter list values
		List<String> parameters = new ArrayList<>();
		String paramValue;
		paramValue = DATAFORMATTER.formatCellValue(row.getCell(columnIdx++));
		while(StringUtils.isNotBlank(paramValue)) {
			parameters.add(paramValue);

			paramValue = DATAFORMATTER.formatCellValue(row.getCell(columnIdx++));
		}
		parseActivityItem.setParameters(parameters);
		
		return parseActivityItem;
	}

	/**
	 * Helper method to open work book and retrieve sheet reference
	 * 
	 * @param 	sheetName
	 * 			Sheet name in the work book			
	 * 
	 * @return	Sheet reference
	 * 
	 */
	private HSSFSheet getSheet(String sheetName) {
		HSSFSheet sheet = null;
		if (StringUtils.isBlank(sheetName)) {
			LOGGER.debug("Get sheet skipped as sheet name is blank");
			return sheet;
		}
		
		try {
			// Open workbook & sheet
			HSSFWorkbook workbook = new HSSFWorkbook(workbookFile.getInputStream());
			sheet = workbook.getSheet(sheetName);
		} catch (IOException e) {
			LOGGER.error("IO Exception occurred while retrieving sheet", e);
		}
		
		return sheet;
	}		


}
