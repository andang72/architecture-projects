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
package architecture.ee.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.eventbus.EventBus;

import architecture.ee.service.ApplicationProperties;
import architecture.ee.service.ConfigService;
import architecture.ee.service.Repository;
import architecture.ee.util.StringUtils;

public class ConfigServiceImpl implements ConfigService {
 
	@Autowired( required = true)
	private Repository repository;
	
	@Autowired( required = true )
	private EventBus eventBus;
	
	private State state = State.NONE;
	
	private ApplicationProperties setupProperties = null;
	
    private ApplicationProperties properties = null;
	 
	public void initialize() {			
		state = State.INITIALIZING;
		
		state = State.INITIALIZED;
	}
	
	private ApplicationProperties getApplicationProperties() {
		if (properties == null) {
		    getSetupProperties();
		}
		return properties == null ? LocalApplicationProperties.EMPTY_APPLICATION_PROPERTIES : properties;
	}
	
	private ApplicationProperties getSetupProperties() {
		if (setupProperties == null)
		    this.setupProperties = repository.getSetupApplicationProperties();
		return setupProperties;
	}	
	
	public String getLocalProperty(String name) {
		return (String) getSetupProperties().get(name);
	}
 
	public int getLocalProperty(String name, int defaultValue) {		
		return getSetupProperties().getIntProperty(name, defaultValue);
	}
 
	public List<String> getLocalProperties(String parent) {
		List<String> values = new ArrayList<String>();
		Collection<String> propNames = getSetupProperties().getChildrenNames(parent);
		for (String propName : propNames) {
		    String value = getLocalProperty((new StringBuilder()).append(parent).append(".").append(propName).toString());
		    if (StringUtils.isNullOrEmpty(value))
		    	values.add(value);
		}
		return values;
	}
 
	public void setLocalProperty(String name, String value) {
		getSetupProperties().put(name, value);
	}
 
	public void setLocalProperties(Map<String, String> propertyMap) {
		getSetupProperties().putAll(propertyMap);
	}
 
	public void deleteLocalProperty(String name) {
		getSetupProperties().remove(name);
	}

}