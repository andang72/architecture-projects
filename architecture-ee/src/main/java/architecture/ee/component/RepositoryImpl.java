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
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.support.ServletContextResource;

import architecture.ee.service.ConfigRoot;
import architecture.ee.service.Repository;

public class RepositoryImpl implements Repository, ServletContextAware {

	private AtomicBoolean initailized = new AtomicBoolean(false);

	private Logger log = LoggerFactory.getLogger(getClass());

	private Resource rootResource = getRootResource();

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
			Resource child = getRootResource().createRelative("config");
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
			return getRootResource().createRelative(name).getFile();
		} catch (IOException e) {
		}
		return null;
	}

}
