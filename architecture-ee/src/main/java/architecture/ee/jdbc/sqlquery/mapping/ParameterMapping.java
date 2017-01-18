/*
 * Copyright 2012 Donghyuck, Son
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
package architecture.ee.jdbc.sqlquery.mapping;

public class ParameterMapping {
	public enum Mode {
		IN, OUT, NONE
	};

	private String cipher;

	private String cipherKey;

	private String cipherKeyAlg;

	private int size;

	private int index;

	private String property;

	private String column;

	private String encoding;

	private JdbcType jdbcType;

	private String jdbcTypeName;

	private String pattern;

	private String digest;

	private boolean primary;

	private Class<?> javaType = Object.class;

	private Mode mode = Mode.IN;

	public String getCipher() {
		return cipher;
	}

	public void setCipher(String cipher) {
		this.cipher = cipher;
	}

	public String getCipherKey() {
		return cipherKey;
	}

	public void setCipherKey(String cipherKey) {
		this.cipherKey = cipherKey;
	}

	public String getCipherKeyAlg() {
		return cipherKeyAlg;
	}

	public void setCipherKeyAlg(String cipherKeyAlg) {
		this.cipherKeyAlg = cipherKeyAlg;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public JdbcType getJdbcType() {
		return jdbcType;
	}

	public void setJdbcType(JdbcType jdbcType) {
		this.jdbcType = jdbcType;
	}

	public String getJdbcTypeName() {
		return jdbcTypeName;
	}

	public void setJdbcTypeName(String jdbcTypeName) {
		this.jdbcTypeName = jdbcTypeName;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	public boolean isPrimary() {
		return primary;
	}

	public void setPrimary(boolean primary) {
		this.primary = primary;
	}

	public Class<?> getJavaType() {
		return javaType;
	}

	public void setJavaType(Class<?> javaType) {
		this.javaType = javaType;
	}

	public Mode getMode() {
		return mode;
	}

	public void setMode(Mode mode) {
		this.mode = mode;
	}

	@Override
	public String toString() {
		return "ParameterMapping [cipher=" + cipher + ", cipherKey=" + cipherKey + ", cipherKeyAlg=" + cipherKeyAlg
				+ ", size=" + size + ", index=" + index + ", property=" + property + ", column=" + column
				+ ", encoding=" + encoding + ", jdbcType=" + jdbcType + ", jdbcTypeName=" + jdbcTypeName + ", pattern="
				+ pattern + ", digest=" + digest + ", primary=" + primary + ", javaType=" + javaType + ", mode=" + mode
				+ "]";
	}

	public static class Builder {

		/**
		 * @uml.property name="parameterMapping"
		 * @uml.associationEnd
		 */
		private ParameterMapping parameterMapping = new ParameterMapping();

		public Builder(String property) {
			parameterMapping.property = property;
		}

		public Builder(String property, Class<?> javaType) {
			parameterMapping.property = property;
			parameterMapping.javaType = javaType;
		}

		public Builder javaType(Class<?> javaType) {
			parameterMapping.javaType = javaType;
			return this;
		}

		public Builder jdbcType(JdbcType jdbcType) {
			parameterMapping.jdbcType = jdbcType;
			return this;
		}

		public Builder digest(String digest) {
			parameterMapping.digest = digest;
			return this;
		}

		public Builder cipher(String cipher) {
			parameterMapping.cipher = cipher;
			return this;
		}

		public Builder cipherKey(String cipherKey) {
			parameterMapping.cipherKey = cipherKey;
			return this;
		}

		public Builder cipherKeyAlg(String cipherKeyAlg) {
			parameterMapping.cipherKeyAlg = cipherKeyAlg;
			return this;
		}

		public Builder encoding(String encoding) {
			parameterMapping.encoding = encoding;
			return this;
		}

		public Builder mode(String mode) {
			parameterMapping.mode = Mode.valueOf(mode);
			return this;
		}

		public Builder size(String sizeString) {
			parameterMapping.size = Integer.parseInt(sizeString);
			return this;
		}

		public Builder primary(boolean primary) {
			parameterMapping.primary = primary;
			return this;
		}

		public Builder jdbcTypeName(String jdbcTypeName) {
			parameterMapping.jdbcTypeName = jdbcTypeName;
			parameterMapping.jdbcType = resolveJdbcType(jdbcTypeName);
			return this;
		}

		public Builder pattern(String pattern) {
			parameterMapping.pattern = pattern;
			return this;
		}

		public Builder index(int index) {
			parameterMapping.index = index;
			return this;
		}

		public Builder column(String column) {
			parameterMapping.column = column;
			return this;
		}

		public ParameterMapping build() {
			return parameterMapping;
		}

		protected JdbcType resolveJdbcType(String alias) {
			if (alias == null)
				return null;
			try {
				return JdbcType.valueOf(alias.toUpperCase());
			} catch (IllegalArgumentException e) {
				return null;
			}
		}
	}

}
