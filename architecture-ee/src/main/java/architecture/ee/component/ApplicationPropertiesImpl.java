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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import architecture.ee.service.ApplicationProperties;
import architecture.ee.util.NumberUtils;
import architecture.ee.util.StringUtils;
import architecture.ee.util.xml.XmlProperties;
/**
 * 
 * @author donghyuck
 *
 */
public class ApplicationPropertiesImpl implements ApplicationProperties {
	
    private static final String ENCRYPTED_PROPERTY_NAME_PREFIX = "encrypt.";
    
    private static final String ENCRYPTED_PROPERTY_NAMES = ENCRYPTED_PROPERTY_NAME_PREFIX + "property.name";
    
    private static final String ENCRYPTION_ALGORITHM = ENCRYPTED_PROPERTY_NAME_PREFIX + "algorithm";
    
    private static final String ENCRYPTION_KEY_CURRENT = ENCRYPTED_PROPERTY_NAME_PREFIX + "key.current";
    
    private static final String ENCRYPTION_KEY_NEW = ENCRYPTED_PROPERTY_NAME_PREFIX + "key.new";
    
    private static final String ENCRYPTION_KEY_OLD = ENCRYPTED_PROPERTY_NAME_PREFIX + "key.old";
    
    private static final String ENCRYPTION_ALGORITHM_AES = "AES";
    
    private static final String ENCRYPTION_ALGORITHM_BLOWFISH = "Blowfish";
    
	private XmlProperties properties;

	public ApplicationPropertiesImpl(File fileToUse) throws IOException {
		properties = new XmlProperties(fileToUse);
	}

	public ApplicationPropertiesImpl(InputStream in) throws Exception {
		properties = new XmlProperties(in);
	}

	public ApplicationPropertiesImpl(String fileName) throws IOException {
		properties = new XmlProperties(fileName);
	}

	public Collection<String> getChildrenNames(String name) {
		return properties.getChildrenNames(name);
	}

	public Collection<String> getPropertyNames() {
		return properties.getPropertyNames();
	}

	public int size() {
		throw new UnsupportedOperationException();
	}

	public boolean isEmpty() {
		return false;
	}

	public boolean containsKey(Object key) {
		return get(key) != null;
	}

	public boolean containsValue(Object value) {
		throw new UnsupportedOperationException();
	}

	public String get(Object key) {
		return properties.getProperty((String) key);
	}

	public String put(String key, String value) {
		properties.setProperty(key, value);
		return "";
	}

	public String remove(Object key) {
		properties.deleteProperty((String) key);
		return "";
	}

	public void putAll(Map<? extends String, ? extends String> propertyMap) {
		properties.setProperties((Map<String, String>) propertyMap);
	}

	public void clear() {
		throw new UnsupportedOperationException();
	}

	public Set<String> keySet() {
		throw new UnsupportedOperationException();
	}

	public Collection<String> values() {
		throw new UnsupportedOperationException();
	}

	public Set<java.util.Map.Entry<String, String>> entrySet() {
		throw new UnsupportedOperationException();
	}

	public boolean getBooleanProperty(String name) {
		return Boolean.valueOf(get(name)).booleanValue();
	}

	public boolean getBooleanProperty(String name, boolean defaultValue) {
		String value = get(name);
		if (value != null)
			return Boolean.valueOf(value).booleanValue();
		else
			return defaultValue;
	}

	public int getIntProperty(String name, int defaultValue) {
		return NumberUtils.toInt(get(name), defaultValue);
	}

	public long getLongProperty(String name, long defaultValue) {
		return NumberUtils.toLong(get(name), defaultValue);
	}

	public String getStringProperty(String name, String defaultValue) {
		return StringUtils.defaultString(get(name), defaultValue);
	}

	@Override
	public String toString() {
		return "ApplicationPropertiesImpl [properties=" + properties + "]";
	}

}