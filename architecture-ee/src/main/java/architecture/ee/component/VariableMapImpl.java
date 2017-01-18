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

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import architecture.ee.service.VariableMap;

public class VariableMapImpl implements VariableMap {

	private Map<String, String> variables;

	public VariableMapImpl() {
		variables = new HashMap<String, String>();
	}

	public VariableMapImpl(Properties props) {
		variables = new HashMap<String, String>();
		variables.putAll((Map) props);
	}

	public VariableMapImpl(Map<String, String> variables) {
		this.variables = variables;
	}

	public String expand(String expression) {
		return substitute(expression, variables);
	}

	void append(StringBuffer stringbuffer, String s, int i, int j) {
		if (j < 0)
			j = s.length() - i;
		stringbuffer.ensureCapacity(stringbuffer.length() + j);
		j += i;
		for (int k = i; k < j; k++)
			stringbuffer.append(s.charAt(k));

	}

	String expand(String str, int i, int j, Map<String, String> map, Map<String, String> map1) throws IllegalArgumentException {
		StringBuffer stringbuffer = new StringBuffer();
		for (int l = i; l < j; l++) {
			int k = str.indexOf('$', l);
			append(stringbuffer, str, l, k - l);
			if (k == -1)
				break;
			l = k + 1;
			if (l >= j)
				continue;
			switch (str.charAt(l)) {
			case 36: // '$'
				stringbuffer.append('$');
				break;

			case 40: // '('
			case 123: // '{'
				char c = str.charAt(l);
				byte byte0 = ((byte) (c != '(' ? 125 : 41));
				int i1 = l + 1;
				int j1 = str.indexOf(byte0, i1);
				if (j1 == -1)
					throw new IllegalArgumentException("unterminated variable reference");
				l = indexOf(str, '$', i1, j1);
				if (l != -1) {
					i1 = l + 1;
					int k1 = 0;
					for (l = i1; l < str.length(); l++) {
						char c2 = str.charAt(l);
						if (c2 == c) {
							k1++;
							continue;
						}
						if (c2 == byte0 && --k1 < 0)
							break;
					}

					String s1;
					if (k1 < 0)
						s1 = expand(str, i1, l, map, map1);
				} else {
					l = j1;
				}
				referenceVariable(stringbuffer, str, i1, j1 - i1, map, map1);
				break;

			default:
				char c1 = str.charAt(l);
				if (!Character.isWhitespace(c1))
					referenceVariable(stringbuffer, str, l, 1, map, map1);
				break;
			}
		}

		return stringbuffer.toString();
	}

	String expand(String s, Map<String, String> map, Map<String, String> map1) throws IllegalArgumentException {
		return expand(s, 0, s.length(), map, map1);
	}

	void expandIt(String s, Map<String, String> map) {
		try {
			System.out.println(s + " ---> " + substitute(s, map));
		} catch (Throwable throwable) {
			System.out.println(s + " failed");
			throwable.printStackTrace();
		}
	}

	int indexOf(String s, char c, int i, int j) {
		for (int k = i; k < j; k++)
			if (s.charAt(k) == c)
				return k;

		return -1;
	}

	String recursivelyExpand(String s, Map<String, String> map, Map<String, String> map1) throws IllegalArgumentException {
		if (map1.get(s) != null)
			throw new IllegalArgumentException("Recursive variable: " + s);
		map1.put(s, s);
		String s1 = map.get(s);
		if (s1 == null)
			s1 = "";
		String s2 = expand(s1, map, map1);
		map1.remove(s);
		return s2;
	}

	void referenceVariable(StringBuffer stringbuffer, String str, int i, int j, Map<String, String> map, Map<String, String> map1)
			throws IllegalArgumentException {
		String key = str.substring(i, i + j);
		if (!map.containsKey(key)) {
			throw new IllegalArgumentException("undefined variable: " + key);
		} else {
			String s2 = recursivelyExpand(key, map, map1);
			stringbuffer.append(s2);
			return;
		}
	}

	public String substitute(String s, Map<String, String> map) throws IllegalArgumentException {
		if (s == null){
			return null;
		}else{
			return expand(s, 0, s.length(), map, new HashMap<String, String>());
		}
	}
}
