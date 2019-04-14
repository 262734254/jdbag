

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xiu.jd.schedule.BaseSchedule;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:config/spring/applicationContext.xml"})
public class BaseTestCase extends BaseSchedule {
	
	protected Log logger = LogFactory.getLog(this.getClass());
	
	@Before
	public void before() {
		logger.debug("-------------------------------" + this.getClass() + "----------------------------------");
	}
	
	@After
	public void after() {
		logger.debug("-------------------------------" + this.getClass() + "----------------------------------");
	}
}
