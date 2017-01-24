import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import architecture.ee.jdbc.sqlquery.factory.SqlQueryFactory;
import architecture.ee.jdbc.sqlquery.mapping.MappedStatement;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:application-context.xml"})
public class SqlQueryTest {

	private static Logger log = LoggerFactory.getLogger(SqlQueryTest.class);
	
	@Autowired
    private SqlQueryFactory sqlQueryFactory;
	
    private static ClassPathXmlApplicationContext context = null;
    
	@BeforeClass 
	public static void setUpBeforeClass() throws Exception {
		context = new ClassPathXmlApplicationContext("application-context.xml");
	} 
	
	@Test
	public void createSqlQueryFactory(){		
		for(MappedStatement ms : sqlQueryFactory.getConfiguration().getMappedStatements())
			System.out.println(ms.getId() + ":" + ms.getResource());
	}

	
	@AfterClass 
	public static void tearDownAfterClass() throws Exception {		
		
	} 

}
