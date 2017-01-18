/*
 * Copyright 2016 donghyuck
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package architecture.ee.jdbc.sqlquery.factory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import architecture.ee.jdbc.sqlquery.mapping.MappedStatement;
import architecture.ee.jdbc.sqlquery.parser.XNode;
import architecture.ee.jdbc.sqlquery.type.TypeAliasRegistry;


public class Configuration {

	protected Integer defaultStatementTimeout;

	protected final TypeAliasRegistry typeAliasRegistry = new TypeAliasRegistry();

	protected Properties variables = new Properties();

	protected final Set<String> loadedResources = new HashSet<String>();

	// protected final Map<String, MappedStatement> mappedStatements = new
	// StrictMap<MappedStatement>("Mapped Statements collection");

	protected final BiMap<String, MappedStatement> mappedStatements = HashBiMap.create();

	// protected final BiMap<String, ParameterMap> parameterMaps =
	// HashBiMap.create();

	/** A map holds statement nodes for a namespace. */
	protected final ConcurrentMap<String, List<XNode>> statementNodesToParse = new ConcurrentHashMap<String, List<XNode>>();

	public void addMappedStatement(MappedStatement statement) {
		mappedStatements.put(statement.getId(), statement);
	}

	public void addStatementNodes(String namespace, List<XNode> nodes) {
		statementNodesToParse.put(namespace, nodes);
	}

	public void addLoadedResource(String resource) {
		loadedResources.add(resource);
	}

	public boolean isResourceLoaded(String resource) {
		return loadedResources.contains(resource);
	}

	public void removeLoadedResource(String resource) {
		loadedResources.remove(resource);
	}

	public void setDefaultStatementTimeout(Integer defaultStatementTimeout) {
		this.defaultStatementTimeout = defaultStatementTimeout;
	}

	public Integer getDefaultStatementTimeout() {
		return defaultStatementTimeout;
	}

	/**
	 * Extracts namespace from fully qualified statement id.
	 * 
	 * @param statementId
	 * @return namespace or null when id does not contain period.
	 */
	protected String extractNamespace(String statementId) {
		int lastPeriod = statementId.lastIndexOf('.');
		return lastPeriod > 0 ? statementId.substring(0, lastPeriod) : null;
	}

	public TypeAliasRegistry getTypeAliasRegistry() {
		return typeAliasRegistry;
	}

	protected static class StrictMap<V> extends HashMap<String, V> {

		private static final long serialVersionUID = -4950446264854982944L;
		private final String name;

		public StrictMap(String name, int initialCapacity, float loadFactor) {
			super(initialCapacity, loadFactor);
			this.name = name;
		}

		public StrictMap(String name, int initialCapacity) {
			super(initialCapacity);
			this.name = name;
		}

		public StrictMap(String name) {
			super();
			this.name = name;
		}

		public StrictMap(String name, Map<String, ? extends V> m) {
			super(m);
			this.name = name;
		}

		@SuppressWarnings("unchecked")
		public V put(String key, V value) {
			if (containsKey(key)) {
				throw new IllegalArgumentException(name + " already contains value for " + key);
			}
			if (key.contains(".")) {
				final String shortKey = getShortName(key);
				if (super.get(shortKey) == null) {
					super.put(shortKey, value);
				} else {
					super.put(shortKey, (V) new Ambiguity(shortKey));
				}
			}
			return super.put(key, value);
		}

		public V get(Object key) {
			V value = super.get(key);
			if (value == null) {
				throw new IllegalArgumentException(name + " does not contain value for " + key);
			}
			if (value instanceof Ambiguity) {
				throw new IllegalArgumentException(((Ambiguity) value).getSubject() + " is ambiguous in " + name
						+ " (try using the full name including the namespace, or rename one of the entries)");
			}
			return value;
		}

		private String getShortName(String key) {
			final String[] keyParts = key.split("\\.");
			return keyParts[keyParts.length - 1];
		}

		protected static class Ambiguity {
			final private String subject;

			public Ambiguity(String subject) {
				this.subject = subject;
			}

			public String getSubject() {
				return subject;
			}
		}
	}
}
