import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import architecture.ee.jdbc.sqlquery.builder.xml.XmlSqlSetBuilder;
import architecture.ee.jdbc.sqlquery.factory.Configuration;

/**
 *    Copyright 2015-2016 donghyuck
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

public class XmlTest {
	
	@Test
	public void loadXml() throws IOException{
		
		DefaultResourceLoader loader = new DefaultResourceLoader();		
		Resource resource = loader.getResource("common-sqlset.xml");
		InputStream is = resource.getInputStream();
		
		Configuration config = new Configuration();
		XmlSqlSetBuilder builder = new XmlSqlSetBuilder(is, config, resource.getURI().toString(), null);
		builder.parse();
		
		System.out.println( resource.getFile() );
	}

}
