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
package architecture.ee.jdbc.sqlquery.factory.impl;

import java.io.IOException;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.object.SqlQuery;

import architecture.ee.jdbc.sqlquery.builder.BuilderException;
import architecture.ee.jdbc.sqlquery.builder.xml.XmlSqlSetBuilder;
import architecture.ee.jdbc.sqlquery.factory.Configuration;
import architecture.ee.jdbc.sqlquery.factory.SqlQueryFactory;

public class SqlQueryFactoryImpl implements SqlQueryFactory {
	protected Logger log = LoggerFactory.getLogger(getClass());

	private final Configuration configuration;

	private List<String> resourceLocations;
	
	
	public SqlQueryFactoryImpl(Configuration configuration) {
		this.configuration = configuration;
	}

	public void initialize() {
		
		if (resourceLocations!=null && resourceLocations.size() > 0){
			buildFromResourceLocations();
		}
		
	}
	

	public List<String> getResourceLocations() {
		return resourceLocations;
	}

	public void setResourceLocations(List<String> resourceLocations) {
		this.resourceLocations = resourceLocations;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	protected void buildFromResourceLocations() {
		DefaultResourceLoader loader = new DefaultResourceLoader();
		for (String location : resourceLocations) {
			Resource resource = loader.getResource(location);			
			if (resource.exists() && !isResourceLoaded( resource ) ) {
				try {
					log.debug("building {}", resource.getURI().toString() );
					XmlSqlSetBuilder builder = new XmlSqlSetBuilder(resource.getInputStream(), configuration, resource.getURI().toString(), null);
					builder.parse();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					throw new BuilderException("build faild", e);
				}
			}
		}
	}
	
	protected boolean isResourceLoaded( Resource resource ){
		try {
			return configuration.isResourceLoaded( resource.getURI().toString() );
		} catch (IOException e) {
			return false;
		}		
	}

	public SqlQuery createSqlQuery(DataSource dataSource) {

		return null;

	}

}
