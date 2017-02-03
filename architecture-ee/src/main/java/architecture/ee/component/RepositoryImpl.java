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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.servlet.ServletContext;

import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.support.ServletContextResource;

import architecture.ee.service.ApplicationProperties;
import architecture.ee.service.ConfigRoot;
import architecture.ee.service.Repository;
import architecture.ee.util.ApplicationConstants;

/**
 * 
 * @author donghyuck
 *
 */
public class RepositoryImpl implements Repository, ServletContextAware {

	private AtomicBoolean initailized = new AtomicBoolean(false);

	private Logger log = LoggerFactory.getLogger(getClass());

	private Resource rootResource = getRootResource();

	private ApplicationProperties setupProperties = null;
	
	
	public void initialize(){
		if(initailized.get()) {			
			log.debug("initialized");
		}
	}
	
	public void setServletContext(ServletContext servletContext) {
		if (!initailized.get()) {
			ServletContextResource resource = new ServletContextResource(servletContext, "/WEB-INF");
			try {
				File file = resource.getFile();
				log.debug("repository exists : " + file.exists());
				if( !file.exists() )		
				{
					
				}
				rootResource = new FileSystemResource(file);
				log.debug("repository initialize with : " + rootResource.toString());
				initailized.set(true);
			} catch (IOException e) {
				log.error("faile to .. ", e);
			}
			
		}
	}

	public ConfigRoot getConfigRoot() {
		try {
			File file = new File( getRootResource().getFile() , "config" );
			Resource child = new FileSystemResource(file);
			
			log.debug("config exists : " + child.exists());
			
			
			
			return new ConfigRootImpl(child);
		} catch (Exception e) {
			return null;
		}
	}

	private Resource getRootResource() {
		if (initailized.get())
			return rootResource;
		return null;
	}

	public File getFile(String name) {
		try {			
			File file = new File( getRootResource().getFile() , name );
			return file;
		} catch (IOException e) {
		}
		return null;
	}
	
	public ApplicationProperties getSetupApplicationProperties() {
		if (setupProperties == null) {
			if(initailized.get()){
				File file = getFile(ApplicationConstants.DEFAULT_STARTUP_FILENAME);
				if (!file.exists()){
					
					boolean error = false;
				    // create default file...
				    log.debug("No startup file now create !!! {}", file.getAbsolutePath());
				    Writer writer = null;
				    try {
				    	writer = new OutputStreamWriter(new FileOutputStream(file),	StandardCharsets.UTF_8);
				    	XMLWriter xmlWriter = new XMLWriter(writer, OutputFormat.createPrettyPrint());
				    	StringBuilder sb = new StringBuilder();
						org.dom4j.Document document = org.dom4j.DocumentHelper.createDocument();
						org.dom4j.Element root = document.addElement("startup-config");
						// setup start
						// ------------------------------------------------------------
						org.dom4j.Element setupNode = root.addElement("setup");
						setupNode.addElement("complete").setText("false");
						// setup end
						
						// license start
						org.dom4j.Element licenseNode = root.addElement("license");
						// license end
						
						// view start
						/*
						org.dom4j.Element viewNode = root.addElement("view");
						org.dom4j.Element renderNode = viewNode.addElement("render");
						org.dom4j.Element freemarkerNode = renderNode.addElement("freemarker");
						freemarkerNode.addElement("enabled").setText("true");
						freemarkerNode.addElement("source").addElement("location");
						org.dom4j.Element velocityNode = renderNode.addElement("velocity");
						velocityNode.addElement("enabled").setText("false");
						*/
						// view end
						
						// security start
						
						org.dom4j.Element securityNode = root.addElement("security");
						org.dom4j.Element encrpptNode = securityNode.addElement("encrypt");
						encrpptNode.addElement("algorithm").setText("Blowfish");;
						encrpptNode.addElement("key").addElement("current");
						org.dom4j.Element encrpptPropertyNode = encrpptNode.addElement("property");
						encrpptPropertyNode.addElement("name").setText("username");
						encrpptPropertyNode.addElement("name").setText("password");						
						securityNode.addElement("authentication").addElement("encoding").addElement("algorithm").setText("SHA-256");

						// security end
						// scripting start
	
						/**
						org.dom4j.Element scriptingNode = root.addElement("scripting");
						org.dom4j.Element groovyNode = scriptingNode.addElement("groovy");
						groovyNode.addElement("debug").setText("false");
						org.dom4j.Element sourceGroovyNode = groovyNode.addElement("source");
						sourceGroovyNode.addElement("location");
						sourceGroovyNode.addElement("encoding").setText(ApplicationConstants.DEFAULT_CHAR_ENCODING);
						sourceGroovyNode.addElement("recompile").setText("true");
						*/
						// scripting end
						// database start
						
						org.dom4j.Element databaseNode = root.addElement("database").addElement("default");
						
						// database end
						xmlWriter.write(document);
					    } catch (Exception e) {
					    	log.error("fail to making {} - {}", file.getName(), e.getMessage());				    	
					    	error = true;
					    } finally {
						try {
						    writer.flush();
						    writer.close();
						} catch (Exception e) {
						    log.error("error" , e);
						    error = true;
						}
				    }	
				}else{
					try {
						log.debug("load from {}", file.getAbsolutePath() );
						this.setupProperties = new ApplicationPropertiesImpl(file);
					} catch (IOException e) {
						 log.error("error" , e);
					}
				}
			}else{
				return EmptyApplicationProperties.getInstance();
			}
		}
		return setupProperties ;
	}

}
