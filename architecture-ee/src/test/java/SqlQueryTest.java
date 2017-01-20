import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import architecture.ee.jdbc.sqlquery.factory.SqlQueryFactory;

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

public class SqlQueryTest {

	private static Logger log = LoggerFactory.getLogger(SqlQueryTest.class);
	
    private static ClassPathXmlApplicationContext context = null;
    
	@BeforeClass 
	public static void setUpBeforeClass() throws Exception {
		context = new ClassPathXmlApplicationContext("application-context.xml");
	} 
	
	@Test
	public void createSqlQueryFactory(){
		
		System.out.println(context.getBean(SqlQueryFactory.class, "sqlQueryFactory").hashCode() );
		System.out.println(context.getBean(SqlQueryFactory.class, "sqlQueryFactory").hashCode() );
		System.out.println(context.getBean(SqlQueryFactory.class, "sqlQueryFactory").hashCode() );
		System.out.println(context.getBean(SqlQueryFactory.class, "sqlQueryFactory").hashCode() );
	}

	
	@AfterClass 
	public static void tearDownAfterClass() throws Exception {		
		
	} 

}