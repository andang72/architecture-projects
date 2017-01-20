import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.FalseFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.junit.Test;
import org.springframework.core.io.DefaultResourceLoader;

import architecture.ee.jdbc.sqlquery.factory.Configuration;
import architecture.ee.jdbc.sqlquery.factory.impl.SqlQueryFactoryImpl;
import architecture.ee.jdbc.sqlquery.mapping.MappedStatement;

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
		Configuration config = new Configuration();
		SqlQueryFactoryImpl impl = new SqlQueryFactoryImpl(config);
		impl.setResourceLocations(new ArrayList<String>());
		impl.getResourceLocations().add("common-sqlset.xml");
		impl.getResourceLocations().add("common-sqlset.xml");
		impl.initialize();
				
		//XmlSqlSetBuilder builder = new XmlSqlSetBuilder(is, config, resource.getURI().toString(), null);
		//builder.parse();
		
		for( MappedStatement ms : config.getMappedStatements()  )
		{
			System.out.println( ms.getId() + " : " + ms.getResource());
		}
		
		
	}
	
	
	public void fileMonitorTest() throws Exception{
		
		Configuration config = new Configuration();
		SqlQueryFactoryImpl impl = new SqlQueryFactoryImpl(config);
		File directory = new File("src/test/resources");
		
		for( File f : FileUtils.listFiles(directory, FileFilterUtils.suffixFileFilter(config.getSuffix()), FileFilterUtils.trueFileFilter())){
			
			System.out.println( f.toURI().toString());			
		}
		
		impl.setResourceLocations(new ArrayList<String>());
		impl.getResourceLocations().add("common-sqlset.xml");
		
		
		FileAlterationMonitor monitor = new FileAlterationMonitor(1000L);		
		FileAlterationObserver observer = new FileAlterationObserver(directory, FileFilterUtils.suffixFileFilter(config.getSuffix()));
		observer.addListener(new FileAlterationListener(){
			public void onStart(FileAlterationObserver observer) {
				
			}
			public void onDirectoryCreate(File directory) {
				System.out.println("onDirectoryCreate:");
			}
			public void onDirectoryChange(File directory) {
				System.out.println("onDirectoryChange:");
			}
			public void onDirectoryDelete(File directory) {
				System.out.println("onDirectoryDelete:");
			}
			public void onFileCreate(File file) {
				System.out.println("onFileCreate:");
			}
			public void onFileChange(File file) {
				System.out.println("onFileChange:");
			}
			public void onFileDelete(File file) {
				System.out.println("onFileDelete:");
			}
			public void onStop(FileAlterationObserver observer) {
								
			}});
		monitor.addObserver(observer);
		monitor.start();		
		System.out.println("Current working directory : " + directory.getAbsolutePath());
	} 
	
	public static final void main(String args[]) throws Exception{
		
		XmlTest t = new XmlTest();
		t.fileMonitorTest();		
	}

}
