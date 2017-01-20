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
package architecture.ee.spring.jdbc.sqlquery;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import architecture.ee.jdbc.sqlquery.factory.Configuration;
import architecture.ee.jdbc.sqlquery.factory.SqlQueryFactory;
import architecture.ee.jdbc.sqlquery.factory.impl.SqlQueryFactoryImpl;

public class SqlQueryFactoryBuilderBean implements InitializingBean, DisposableBean, FactoryBean<SqlQueryFactory> {

	public boolean isSingleton() {
		return true;
	}
	
	public SqlQueryFactory build(Configuration config) {
	    return new SqlQueryFactoryImpl(config);
	}

	public SqlQueryFactory getObject() throws Exception {		
		Configuration config = new Configuration();		
		return build(config);
	}

	public Class<?> getObjectType() {
		return SqlQueryFactory.class;
	}

	public void destroy() throws Exception {		
	}

	public void afterPropertiesSet() throws Exception {		
	}

}
