

import java.util.List;

import net.sf.json.JSONObject;

import org.junit.Test;




import com.jd.open.api.sdk.JdException;
import com.jd.open.api.sdk.domain.ware.Ware;
import com.jd.open.api.sdk.request.ware.WareDelistingGetRequest;
import com.jd.open.api.sdk.request.ware.WareUpdateRequest;
import com.jd.open.api.sdk.response.ware.WareDelistingGetResponse;
import com.xiu.jd.utils.BaseUtils;

public class MyRunnable  extends Thread  {
	public static Object o = new Object();
	public static Integer i = 1;
	public int times = 0;
	@Override
	public void run() {
		while(true){
			synchronized (o) {
				if(i <= 300) {
					WareDelistingGetRequest request=new WareDelistingGetRequest();			

					try {
						
							request.setPage( Integer.toString(i) );
							request.setPageSize( "20" );	
							request.setFields("ware_id");
							WareDelistingGetResponse response = BaseUtils.getJdClient().execute(request);
							
							List<Ware> list = response.getWareInfos();
							for (Ware ware : list) {
								WareUpdateRequest request1 = new WareUpdateRequest();

								request1.setAdContent("海外大牌精选每日上新 品质保证 无忧售后");
								request1.setWareId(Long.toString(ware
										.getWareId()));
								BaseUtils.getJdClient().execute(request1);

								//System.out.println(JSONObject.fromObject(BaseUtils.getJdClient().execute(request1)).toString());

							}
							
						
					} catch (JdException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					
				} else {
					break;
				}
			
		   }
			
			System.out.println(Thread.currentThread().getName() + "包店 "+i);
			i++;
		   
    }

	
	/*synchronized (this) {
		WareDelistingGetRequest request=new WareDelistingGetRequest();			

		try {
			
				request.setPage( Integer.toString(i) );
				request.setPageSize( "50" );	
				WareDelistingGetResponse response = BaseUtils.getJdClient().execute(request);
				List<Ware> list = response.getWareInfos();
				for (Ware ware : list) {
					WareUpdateRequest request1 = new WareUpdateRequest();

					request1.setAdContent("海外大牌精选每日上新 品质保证 无忧售后");
					request1.setWareId(Long.toString(ware
							.getWareId()));
					BaseUtils.getJdClient().execute(request1);

					//System.out.println(JSONObject.fromObject(BaseUtils.getJdClient().execute(request1)).toString());

				}
				
			
		} catch (JdException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} */

	  }
		public static void main(String[] args) {
			MyRunnable test1 = new MyRunnable();
			MyRunnable test2 = new MyRunnable();
			MyRunnable test3 = new MyRunnable();
			MyRunnable test4 = new MyRunnable();
			MyRunnable test5 = new MyRunnable();
			MyRunnable test6 = new MyRunnable();
			MyRunnable test7 = new MyRunnable();
			MyRunnable test8 = new MyRunnable();
			MyRunnable test9 = new MyRunnable();
			MyRunnable test10 = new MyRunnable();
			
			test1.start();
			test2.start();
			test3.start();
			test4.start();
			test5.start();
			test6.start();
			test7.start();
			test8.start();
			test9.start();
			test10.start();	
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(i);
		}

	 
	

}
