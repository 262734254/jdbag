import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import net.sf.json.JSONObject;

import com.jd.open.api.sdk.JdException;
import com.jd.open.api.sdk.request.ware.WarePropimgsSearchRequest;
import com.jd.open.api.sdk.response.ware.WarePropimgsSearchResponse;
import com.xiu.jd.schedule.ware.GetJdProductSchedule;
import com.xiu.jd.schedule.ware.PushWareSkuPicSchedule;
import com.xiu.jd.utils.BaseUtils;


public class ImageTest extends BaseUtils {
	@Test
	public void testPushPic(){
	    ApplicationContext act = new ClassPathXmlApplicationContext("applicationContext.xml");  
		 PushWareSkuPicSchedule ps = (PushWareSkuPicSchedule) act.getBean("pushWareSkuPic");  
         ps.pushWareSkuPic();
        /* PushWareSkuPicSchedule gjps =(PushWareSkuPicSchedule)WebApplicationContextUtils.getWebApplicationContext(this.request.getSession().getServletContext())
 				.getBean("getJdProductSchedule");*/
         /*
         GetJdProductSchedule gjps =(GetJdProductSchedule)WebApplicationContextUtils.getWebApplicationContext(this.request.getSession().getServletContext())
 				.getBean("getJdProductSchedule");*/
	}

}
