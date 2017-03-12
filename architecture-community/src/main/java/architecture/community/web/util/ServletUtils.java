package architecture.community.web.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import architecture.ee.util.StringUtils;

public class ServletUtils {

	/** 디폴트로 웹 페이지 컨텐츠 타입. */
	public static final String DEFAULT_HTML_CONTENT_TYPE = "text/html;charset=UTF-8";
	/**
	 * 한글 처리를 위하여 response의 Content Type 속성을 변경하는 유틸리티. 
	 * 
	 * <ul>
	 * 		<li>Freemarker 사용시 한글 처리를 위한 사용.<li>
	 * </ul>
	 * 
	 * @param contentType 컨텐츠 타입 값
	 * @param response 
	 */
	public static void setContentType(String contentType, HttpServletResponse response) {
		
    	String contentTypeToUse = StringUtils.defaultString(contentType, DEFAULT_HTML_CONTENT_TYPE);
    	response.setContentType(contentTypeToUse);
    }
	
	
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
