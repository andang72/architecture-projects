/**
 *    Copyright 2015-2017 donghyuck
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package architecture.ee.jdbc.sqlquery.builder.xml.dynamic;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateException;
import freemarker.template.TemplateHashModel;

public class DynamicSqlNode implements SqlNode {

	protected Logger log = LoggerFactory.getLogger(getClass());
	private static BeansWrapper wrapper = new BeansWrapper();
	private String text;
	private Language language;

	public DynamicSqlNode(String text) {
		this.text = text;
		this.language = Language.FREEMARKER;
	}

	/**
	 * 다이나믹 구현은 Freemarker 을 사용하여 처리한다. 따라서 나이나믹 처리를 위해서는 반듯이 freemarker 의 규칙을
	 * 사용하여야 한다.
	 */
	public boolean apply(DynamicContext context) {

		Map<String, Object> map = new HashMap<String, Object>();

		Object parameterObject = context.getBindings().get(DynamicContext.PARAMETER_OBJECT_KEY);
		Object additionalParameterObject = context.getBindings().get(DynamicContext.ADDITIONAL_PARAMETER_OBJECT_KEY);

		if (additionalParameterObject != null) {
			if (additionalParameterObject instanceof Map)
				map.putAll((Map) additionalParameterObject);
			else
				map.put("additional_parameter", additionalParameterObject);
		}

		/**
		 * 파라메터 객체가 널이 아니면 ..
		 */
		if (parameterObject != null) {
			if (parameterObject instanceof Map) {
				map.put("parameters", parameterObject);
			} else if (parameterObject instanceof MapSqlParameterSource) {
				map.put("parameters", ((MapSqlParameterSource) parameterObject).getValues());
			} else if (parameterObject instanceof Object[]) {
				map.put("parameters", parameterObject);
			}
		}

		context.appendSql(processTemplate(map));
		return true;
	}

	@Override
	public String toString() {

		return "dynamic[" + text + "]";
	}

	public enum Language {
		VELOCITY, FREEMARKER
	}

	protected String processTemplate(Map<String, Object> map) {
		StringReader reader = new StringReader(text);
		StringWriter writer = new StringWriter();
		try {
			populateStatics(map);
			freemarker.template.SimpleHash root = new freemarker.template.SimpleHash();
			root.putAll(map);
			freemarker.template.Template template = new freemarker.template.Template("dynamic", reader, null);
			template.process(root, writer);
		} catch (IOException e) {
			log.error("", e);
		} catch (TemplateException e) {
			log.error("", e);
		}
		return writer.toString();
	}

	protected static void populateStatics(Map<String, Object> model) {
		try {
			TemplateHashModel enumModels = wrapper.getEnumModels();
			model.put("enums", enumModels);
		} catch (UnsupportedOperationException e) {
		}
		TemplateHashModel staticModels = wrapper.getStaticModels();
		model.put("statics", BeansWrapper.getDefaultInstance().getStaticModels());
	}
}
