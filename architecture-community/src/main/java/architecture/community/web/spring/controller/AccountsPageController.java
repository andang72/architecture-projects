package architecture.community.web.spring.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import architecture.community.web.util.ServletUtils;

@Controller("accounts-page-controller")
@RequestMapping("/accounts")
public class AccountsPageController {
	
	@RequestMapping(value="/login", method = { RequestMethod.POST, RequestMethod.GET } )
    public String displayLoginPage(@RequestParam(value="url", defaultValue="/", required=false ) String returnUrl,
    		HttpServletRequest request, 
    		HttpServletResponse response, 
    		Model model) {		
		
		ServletUtils.setContentType(null, response);		
		model.addAttribute("returnUrl", returnUrl);		
        return "/accounts/login";        
    }

}
