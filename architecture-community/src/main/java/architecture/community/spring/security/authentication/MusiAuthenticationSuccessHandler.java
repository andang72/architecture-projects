package architecture.community.spring.security.authentication;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import architecture.community.web.model.json.Result;
import architecture.community.web.util.ServletUtils;
import architecture.ee.util.StringUtils;

public class MusiAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {


	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

		if (ServletUtils.isAcceptJson(request)) {
			Result result = Result.newResult();
			result.getData().put("success", true);
			result.getData().put("returnUrl", ServletUtils.getReturnUrl(request, response));
			String referer = request.getHeader("Referer");
			if (StringUtils.isNullOrEmpty(referer))
				result.getData().put("referer", referer);
			Map<String, Object> model = new ModelMap();
			model.put("item", result);
			
			//MappingJackson2JsonView view = new MappingJackson2JsonView();
			//view.setExtractValueFromSingleKeyModel(true);
			//view.setModelKey("item");
			try {
				createJsonView().render(model, request, response);
			} catch (Exception e) {

			}
			//userService.loginEvent(request, SecurityHelper.getUser());			
			return;
		}

		super.onAuthenticationSuccess(request, response, authentication);

	}

    protected View createJsonView(){
    	MappingJackson2JsonView view = new MappingJackson2JsonView();
    	view.setExtractValueFromSingleKeyModel(true);
    	view.setModelKey("item");
    	return view;
    }

}
