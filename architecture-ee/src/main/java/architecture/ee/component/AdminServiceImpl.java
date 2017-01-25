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

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.eventbus.EventBus;

import architecture.ee.component.event.StateChangeEvent;
import architecture.ee.service.AdminService;
import architecture.ee.service.Repository;

public class AdminServiceImpl implements AdminService {

	@Autowired( required = true)
	private Repository repository;
	
	@Autowired( required = true )
	private EventBus eventBus;

	public Repository getRepository() {
		return repository;
	}

	public void publishEvent(Object event) {
		eventBus.post(event);
	}

	public void fireStateChangeEvent(Object soruce, State oldState, State newState) {
		eventBus.post(new StateChangeEvent( soruce, oldState, newState));
	}

	public void registerEventListener(Object listener) {
		eventBus.register(listener);
	}

	public void unregisterEventListener(Object listener) {
		eventBus.unregister(listener);
	}
	
}
