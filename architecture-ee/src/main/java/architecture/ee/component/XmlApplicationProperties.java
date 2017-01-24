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

import architecture.ee.util.xml.XmlProperties;

public class XmlApplicationProperties extends AbstractApplicationProperties {


	private XmlProperties properties;

	public XmlApplicationProperties(File fileToUse) throws IOException {
		properties = new XmlProperties(fileToUse);
	}

	public XmlApplicationProperties(InputStream in) throws Exception {
		properties = new XmlProperties(in);
	}

	public XmlApplicationProperties(String fileName) throws IOException {
		properties = new XmlProperties(fileName);
	}

	public Collection<String> getChildrenNames(String name) {
		return properties.getChildrenNames(name);
	}

	public Collection<String> getPropertyNames() {
		return properties.getPropertyNames();
	}

	public int size() {
		throw new UnsupportedOperationException();//L10NUtils.format("002051"));
	}

	public boolean isEmpty() {
		return false;
	}

	public boolean containsKey(Object key) {
		return get(key) != null;
	}

	public boolean containsValue(Object value) {
		throw new UnsupportedOperationException();//L10NUtils.format("002051"));
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
		throw new UnsupportedOperationException(); //L10NUtils.format("002051"));
	}

	public Set<String> keySet() {
		throw new UnsupportedOperationException(); //L10NUtils.format("002051"));
	}

	public Collection<String> values() {
		throw new UnsupportedOperationException(); //L10NUtils.format("002051"));
	}

	public Set<java.util.Map.Entry<String, String>> entrySet() {
		throw new UnsupportedOperationException(); //L10NUtils.format("002051"));
	}

}