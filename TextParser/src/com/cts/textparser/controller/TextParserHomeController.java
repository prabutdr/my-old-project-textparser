/**
 * 
 */
package com.cts.textparser.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cts.textparser.constant.GeneralConstants;
import com.cts.textparser.service.TextParserService;
import com.cts.textparser.to.TextParserTO;
import com.cts.textparser.util.DictionaryFactory;
import com.cts.textparser.util.TextParserException;

/**
 * @author 153093
 *
 */
@Controller
@RequestMapping("/home")
public class TextParserHomeController {

	/**
	 * Parser service
	 */
	@Autowired(required = true)
	@Qualifier("textParserService")
	private TextParserService textParserService;
	
	/**
	 * Parser service
	 */
	@Autowired(required = true)
	@Qualifier("dictionaryFactory")
	private DictionaryFactory dictionaryFactory;
	
	@RequestMapping(method = RequestMethod.GET)
	public String showHome(ModelMap model) {
		model.addAttribute("message", "Hello Spring MVC Framework!");

		return GeneralConstants.VIEW_HOME;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String getParseResult(ModelMap model, HttpServletRequest request) {
		String inputText = request.getParameter(GeneralConstants.PARAM_INPUT_TEXT);
		
		try {
			TextParserTO textParserTO = textParserService.parseText(inputText);
			model.put(GeneralConstants.MA_TEXT_PARSER_RESULT, textParserTO);
			model.put("dictionaryFactory", dictionaryFactory);  // TODO: for testing, to be removed
		} catch (TextParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return GeneralConstants.VIEW_SHOW_RESULT;
	}
}
