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

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import architecture.ee.service.ApplicationProperties;

public class EmptyApplicationProperties implements ApplicationProperties {

	private static class InstanceHolder {

		private static final EmptyApplicationProperties instance = new EmptyApplicationProperties();

		private InstanceHolder() {
		}
	}

	public static EmptyApplicationProperties getInstance() {
		return InstanceHolder.instance;
	}

	private EmptyApplicationProperties() {
		
	}

	public void clear() {
	}

	public boolean containsKey(Object key) {
		return false;
	}

	public boolean containsValue(Object value) {
		return false;
	}

	public Set<java.util.Map.Entry<String, String>> entrySet() {
		return null;
	}

	public String get(Object key) {
		return null;
	}

	public boolean getBooleanProperty(String name) {
		return false;
	}

	public boolean getBooleanProperty(String name, boolean defaultValue) {
		return false;
	}

	public Collection<String> getChildrenNames(String name) {
		return Collections.emptyList();
	}

	public int getIntProperty(String name, int defaultValue) {
		return 0;
	}

	public long getLongProperty(String name, long defaultValue) {
		return 0;
	}

	public Collection<String> getPropertyNames() {
		return Collections.emptyList();
	}

	public String getStringProperty(String name, String defaultValue) {
		return defaultValue;
	}

	public boolean isEmpty() {
		return true;
	}

	public Set<String> keySet() {
		return Collections.emptySet();
	}

	public String put(String key, String value) {
		return null;
	}

	public void putAll(Map<? extends String, ? extends String> m) {
	}

	public String remove(Object key) {
		return null;
	}

	public int size() {
		return 0;
	}

	public Collection<String> values() {
		return Collections.emptyList();
	}

}
