package architecture.community.web.spring.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import architecture.community.web.util.ServletUtils;

@Controller("main-page-controller")
public class MainPageController {

	private static final Logger log = LoggerFactory.getLogger(MainPageController.class);	

	@RequestMapping(value={"/","/index"} , method = { RequestMethod.POST, RequestMethod.GET } )
    public String displayMainPage (HttpServletRequest request, HttpServletResponse response, Model model) {
		ServletUtils.setContentType(null, response);	
		return "index" ;
    }

}
