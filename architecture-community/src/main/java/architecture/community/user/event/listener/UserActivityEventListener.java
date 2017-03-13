package architecture.community.user.event.listener;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Qualifier;

import com.google.common.eventbus.Subscribe;

import architecture.community.user.event.UserActivityEvent;
import architecture.ee.service.ConfigService;

public class UserActivityEventListener implements InitializingBean{

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	@Qualifier("configService")
	private ConfigService configService;

	@Override
	public void afterPropertiesSet() throws Exception {
		if( configService != null)
		{
			configService.registerEventListener(this);
		}
	}
	
	
	@Subscribe 
	public void handelUserActivityEvent(UserActivityEvent e) {
		logger.debug("USER : {}, ACTIVITY:{}" , e.getUser().getUsername(), e.getActivity().name() );
	}
	
}
