package architecture.community.web.spring.freemarker;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModelException;

public class CommunityFreeMarkerView extends FreeMarkerView {
	
	
	protected void exposeHelpers(Map<String, Object> model, HttpServletRequest request) throws Exception {
		BeansWrapper wrapper = (BeansWrapper)getObjectWrapper();
		populateStatics(wrapper, model);
	}
	

	/**
	 * 모든 freemarker 템플릿에서 사용하게될 유틸리티들을 정의한다.
	 * 
	 * @param wrapper
	 * @param model
	 */
	public void populateStatics(BeansWrapper wrapper, Map<String, Object> model){
		/**
		try {
			TemplateHashModel enumModels = wrapper.getEnumModels();
			try {

			} catch (Exception e) {
				
			}
			model.put("enums", wrapper.getEnumModels());
		} catch (UnsupportedOperationException e) {
		}
		**/
		
		//model.put("page", new Page());
		
		/**
		 * 정적 Util / Helper 클래스들을 추가하여 ftl 파일에서 쉽게 필요한 유틸리티들을 사용할 수 있도록 한다. 
		 */
		TemplateHashModel staticModels = wrapper.getStaticModels();
		try {	
			//model.put("SecurityHelper",	staticModels.get("neuro.honeycomb.util.SecurityHelper"));
			//model.put("ApplicationContextHelper",	staticModels.get("neuro.honeycomb.util.ApplicationContextHelper"));
			model.put("WebApplicationContextUtils",	staticModels.get("org.springframework.web.context.support.WebApplicationContextUtils"));			
		} catch (TemplateModelException e) {
			
		}	
		
		model.put("statics", BeansWrapper.getDefaultInstance().getStaticModels());		
		
	}
}
