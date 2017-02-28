package architecture.community.web.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import architecture.ee.util.StringUtils;

public class ServletUtils {

	
	/**
	 * 이전 요청했던 URL을 알아낸다.
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public static String getReturnUrl(HttpServletRequest request, HttpServletResponse response) {
		RequestCache requestCache = new HttpSessionRequestCache();
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		if (savedRequest == null) {
			return request.getSession().getServletContext().getContextPath();
		}
		return savedRequest.getRedirectUrl();
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isAcceptJson(HttpServletRequest request){
		String accept = request.getHeader("accept");		
		if(StringUtils.countOccurrencesOf(accept, "json") > 0 )
			return true;
		else 
			return false;
	}
}
