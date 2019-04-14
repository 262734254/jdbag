import org.junit.Test;

import com.jd.open.api.sdk.JdException;
import com.jd.open.api.sdk.request.ware.WareUpdateDelistingRequest;
import com.jd.open.api.sdk.response.ware.WareUpdateDelistingResponse;
import com.xiu.jd.utils.BaseUtils;


public class UpdateDelistingTest extends BaseUtils {

	@Test
	public void delisting()
	{
		String wareId = "1292739673";
		WareUpdateDelistingRequest  wareUpdateDelistingRequest=new WareUpdateDelistingRequest();

		wareUpdateDelistingRequest.setWareId(wareId);
        //商品下架
		try {
			WareUpdateDelistingResponse res = client.execute(wareUpdateDelistingRequest);
			 if("0".equals(res.getCode())){
					System.out.println("商品ID为"+wareId+"的商品,下架成功");
					
				 }else{
					 //商品已经是处于下架的状态
					 if("11201012".equals(res.getCode())){
						 System.out.println("商品ID为"+wareId+"商品已经是处于下架的状态,不需要再次下架");
					 }
					
				 }
		} catch (JdException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
