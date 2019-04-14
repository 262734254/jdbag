package com.xiu.jd.schedule.ware;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.jd.open.api.sdk.JdException;
import com.jd.open.api.sdk.domain.ware.Ware;
import com.jd.open.api.sdk.request.ware.WareGetRequest;
import com.jd.open.api.sdk.request.ware.WareUpdateDelistingRequest;
import com.jd.open.api.sdk.request.ware.WareUpdateListingRequest;
import com.jd.open.api.sdk.response.ware.WareGetResponse;
import com.jd.open.api.sdk.response.ware.WareUpdateDelistingResponse;
import com.jd.open.api.sdk.response.ware.WareUpdateListingResponse;
import com.xiu.jd.bean.page.QueryResult;
import com.xiu.jd.bean.ware.JDProduct;
import com.xiu.jd.bean.ware.JdChangedGoodsOnSale;
import com.xiu.jd.schedule.BaseSchedule;
import com.xiu.jd.service.ware.JDWareService;
import com.xiu.jd.service.ware.JdChangeGoodsOnSaleService;
import com.xiu.jd.service.ware.OnLineBlackProductService;

/***
 * 定时更新京东商品上下架商品
	NEVER_UP:从未上架, 
	NEVER_UP AUDIT_AWAIT

	CUSTORMER_DOWN:自主下架
	
	SYSTEM_DOWN:系统下架
	
	ON_SALE:在售
	
	AUDIT_AWAIT: 待审核
	
	AUDIT_FAIL: 审核不通过
 * 
 * @author liweibiao
 * 
 */
public class UpdateJdProductOnSaleSchedule extends BaseSchedule {

	private static final Logger logger = Logger.getLogger(UpdateJdProductOnSaleSchedule.class);

	
	@Resource(name="jdChangeGoodsOnSaleServiceBean")
	private JdChangeGoodsOnSaleService jdChangeGoodsOnSaleServiceBean;
	@Resource(name="onLineBlackProductServiceBean")
	private OnLineBlackProductService onLineBlackProductServiceBean;


	@Resource(name = "jDWareServiceBean")
	private JDWareService jDWareServiceBean;

	public void updateJdProductOnSale() {

		Map<String, Object> parames = new HashMap<String, Object>();
		parames.put("firstNum", "1");
		int pageSize = 200;
		parames.put("lastNum", pageSize);
		parames.put("updateStatus", "0");
		QueryResult<JdChangedGoodsOnSale> queryResult = jdChangeGoodsOnSaleServiceBean.getPageResule(parames);
		if (queryResult != null) {
			long total = queryResult.getTotalrecord();
			if(total<=0){
				return;
			}
			logger.info("MQ上下架状态总记录数" + total);
			int totalPage = getTotalPage(pageSize, (int) total);
			logger.info("MQ上下架状态总页数" + totalPage);
			for (int currentPage = 1; currentPage <= totalPage; currentPage++) {
				if (currentPage != 1) {
					parames = new HashMap<String, Object>();
					parames.put("firstNum", pageSize * (currentPage - 1) + 1);
					parames.put("lastNum", pageSize * currentPage + 1);
					parames.put("updateStatus", "0");
					queryResult = jdChangeGoodsOnSaleServiceBean.getPageResule(parames);
				}
				logger.info("MQ上下架状态第<---" + currentPage + "--->页");
				// 取得需要同步到京东的商品
				List<JdChangedGoodsOnSale> jdChangedGoodsOnSales = queryResult.getResultlist();
				if (jdChangedGoodsOnSales != null && jdChangedGoodsOnSales.size() > 0) {
					for (JdChangedGoodsOnSale changedGoodsOnSale : jdChangedGoodsOnSales) {
						if(changedGoodsOnSale==null){
							continue;
						}
						//默认是成功的
						changedGoodsOnSale.setUpdateStatus(1);
						Integer onSale=changedGoodsOnSale.getOnSale();
						String xiuCode=changedGoodsOnSale.getXiuCode();
						String jdWareId=changedGoodsOnSale.getJdWareId();
						boolean isBlack =onLineBlackProductServiceBean.isOnLineBlack(xiuCode.trim());
					
						int num=0;
						if(!isBlack)
						{
							logger.info("MQ上下架状态:\n"+changedGoodsOnSale);
						if(jdWareId==null ||jdWareId.isEmpty()){
							//该商品还未推送到京东
							if(xiuCode!=null && !xiuCode.isEmpty()){
								changedGoodsOnSale.setFailDesc("走秀码为:"+xiuCode+",该商品还未推送到京东,也未同步成功");
								if(onSale!=null){
									if(onSale.equals(1)){
										
										//走秀目前为在售状态更新本地数据的销售状态
//										//num=updateNativeProductOnSaleStatus(onSale,xiuCode);
									}
								}
							}
						}else{
							Ware ware=getJdWareById(jdWareId.trim());
							if(ware==null){
								logger.info("MQ上下架状态:,京东商品不存在,京东商品ID为:"+jdWareId);
								changedGoodsOnSale.setUpdateStatus(5);//京东商品不存在
							    jdChangeGoodsOnSaleServiceBean.update(changedGoodsOnSale);
								continue;
							}
							String jdWareStatus=ware.getWareStatus();
							long wareId=ware.getWareId();
							if(onSale!=null ){
								if( onSale.equals(1)){
									//走秀的商品目前处于在售状态
									if(ware!=null){
										//商品已经推送到京东了  ON_SALE AUDIT_AWAIT
										logger.info("MQ上下架状态,京东商品ID:"+ware.getWareId()+",在京东存在,京东商品状态为:"+jdWareStatus);
										if(jdWareStatus!=null ){
											if(!jdWareStatus.startsWith("ON_SALE") && !jdWareStatus.startsWith("NEVER_UP") ){
												//商品在走秀是在售了,而京东非在售
												//更新京东商品状态为在售状态
												
												//将京东商品上架
												updateJdWareOnSale(changedGoodsOnSale, wareId);
												//更新本地数据库商品销售状态
												Integer updateStatus=changedGoodsOnSale.getUpdateStatus();
												if(updateStatus!=null && updateStatus.equals(1)){
													//京东上架成功,更新本地数据库
													num=updateNativeProductOnSaleStatus(onSale,xiuCode);
													logger.info("MQ上下架状态,京东商品ID:"+wareId+",本地数据库商品(上架)影响的记录数"+num+",走秀码"+xiuCode);
												}
											}else{
												changedGoodsOnSale.setUpdateStatus(1);
												logger.info("MQ上下架状态,京东商品ID:"+wareId+",在京东存在,标志成功");
											}
										}
									}
									
								}else{
								//走秀的商品目前处于下架状态
								//把京东的也下架(这里只对在京东销售的商品下架)
								//if("ON_SALE".equalsIgnoreCase(jdWareStatus) ){
								if(jdWareStatus!=null && jdWareStatus.startsWith("ON_SALE")){ //包含上架,与上架审核中
									//将京东商品下架
									updateJdWareOnOffSale(changedGoodsOnSale, ware.getWareId());
									Integer updateStatus=changedGoodsOnSale.getUpdateStatus();
									if(updateStatus!=null && updateStatus.equals(1)){
										//-2下架异常
										//京东下架成功,更新本地数据库
										num=updateNativeProductOnSaleStatus(2,xiuCode);
										logger.info("MQ上下架状态,京东商品ID:"+wareId+",本地数据库商品(下架)影响的记录数"+num+",走秀码"+xiuCode);
									}
								}
							   }
							}
						}
						logger.info("MQ上下架状态,京东商品ID:"+jdWareId+",走秀码"+xiuCode+",走秀目前商品上下架状态:"+ onSale+"  0下架，1上架 ,更新状态:"+changedGoodsOnSale.getUpdateStatus());
						//更新jd_changed_goods_online表状态
						if(num==-1 || num==-2){
							changedGoodsOnSale.setFailDesc("MQ上下架状态,京东商品ID:"+jdWareId+",走秀码"+xiuCode+",更新本地数据库商品销售状态异常,"+num);
						}
						}else{
							//是黑名单中的商品
							changedGoodsOnSale.setFailDesc("是黑名单商品,不需要同步到京东");
							changedGoodsOnSale.setUpdateStatus(4);//黑名单商品
							logger.info("========京东商品ID" + jdWareId +",走秀码:"+xiuCode+",是黑名单中的商品 ");
						}
						jdChangeGoodsOnSaleServiceBean.update(changedGoodsOnSale);
					}
				}
			}
		}
	}

	private int updateNativeProductOnSaleStatus(Integer onSale, String xiuCode) {
		int num = -2;
		try {
			JDProduct jdProduct=new JDProduct();
			jdProduct.setXiucode(xiuCode.trim());
			//商品销售状态：0:未上架,1:在售,2:下架
			jdProduct.setOnLineStatus(onSale);
			//根据商品走秀码更新
			 num=jDWareServiceBean.updateJDProduct(jdProduct);
			logger.info("MQ上下架状态更新本地数据库,走秀码为:"+xiuCode+",影响的记录数为:"+num);
		} catch (Exception e) {
			num=-2;
			e.printStackTrace();
		}
		return num;
		
	}
	
	/**
	 * 根据京东商品ID查询京东上的商品
	 * @param jdWareId 京东商品ID
	 * @return
	 */
    private Ware getJdWareById( String jdWareId){
    	Ware ware=null;
    	WareGetRequest  wareGetRequest=new WareGetRequest();
		wareGetRequest.setWareId(jdWareId);
		wareGetRequest.setFields("ware_id,ware_status");
		WareGetResponse response=null;
		try {
			response = client.execute(wareGetRequest);
		} catch (JdException e) {
			logger.error("MQ上下架状态,根据京东商品ID查询京东上的商品异常"+e);
			e.printStackTrace();
		 }
		if(response!=null){
			ware=response.getWare();
		}
		return  ware;
     }
    
    /**
     * 更新京东商品的状态为在售状态
     */
    private void updateJdWareOnSale(JdChangedGoodsOnSale changedGoodsOnSale,long wareId){
    	//将商品上架
		WareUpdateListingRequest updateListingRequest = new WareUpdateListingRequest();
		updateListingRequest.setWareId(wareId+"");
		WareUpdateListingResponse res = null;
		String code="-1";
		try {
			res = client.execute(updateListingRequest);
			if(res!=null){
				 code=res.getCode();
				if("0".equals(code)){
					//更新京东商品的状态在售状态成功
					changedGoodsOnSale.setUpdateStatus(1);
					return;
				}else if("11100010".equals(code)){   
					//商家类目没有设置扣点,京东错误码为:11100010
	     			changedGoodsOnSale.setUpdateStatus(2);
	     			changedGoodsOnSale.setFailDesc("京东错误描述:"+res.getZhDesc()+",京东错误码为:"+code);
					return;
				}else if("66".equals(code)|| "67".equals(code)){  
					//平台连接后端服务不可用
					changedGoodsOnSale.setUpdateStatus(0);
	     			changedGoodsOnSale.setFailDesc("京东错误描述:"+res.getZhDesc()+",京东错误码为:"+code+"网络问题");
	     			return;
				}
				
				
			}
		} catch (JdException e) {
			logger.error("京东商品ID:"+wareId+"MQ上下架状态更新京东商品的状态为在售异常"+e);
			e.printStackTrace();
		}
		logger.info("京东商品ID:"+wareId+"MQ上下架状态更新京东商品的状态为在售,京东错误码:"+code);
		if(res!=null){
			String desc=res.getZhDesc();
			if(desc.length()<=50){
				changedGoodsOnSale.setFailDesc(desc+",异常,京东错误码为:"+code+",ID "+wareId);
			}else{
				changedGoodsOnSale.setFailDesc(desc.substring(0,50)+",京东错误码为:"+code+",ID "+wareId);
			}
		}else{
			changedGoodsOnSale.setFailDesc("MQ上下架状态更新京东商品的状态为在售失败,未知原因"+",ID"+wareId);
		}
		changedGoodsOnSale.setUpdateStatus(2);
    }
    
    /**
     * 更新京东商品的状态为下架状态
     * @param wareId
     */
    private void  updateJdWareOnOffSale(JdChangedGoodsOnSale changedGoodsOnSale,long wareId){
    	WareUpdateDelistingRequest wareUpdateDelistingRequest = new WareUpdateDelistingRequest();
		wareUpdateDelistingRequest.setWareId(wareId+"");
		WareUpdateDelistingResponse res =null;
		String code="-1";
		try {
			 res = client.execute(wareUpdateDelistingRequest);
			if(res!=null){
				 code=res.getCode();
				if("0".equals(code)){
					//更新京东商品的状态为下架状态
					changedGoodsOnSale.setUpdateStatus(1);
					return;
				}
			}
		} catch (JdException e) {
			logger.error("MQ上下架状态更新京东商品的状态为下架异常"+e);
			e.printStackTrace();
		}
		logger.info("京东商品ID:"+wareId+"MQ上下架状态更新京东商品的状态为下架,京东错误码:"+code);
		if(res!=null){
			String desc=res.getZhDesc();
			if(desc.length()<=80){
				changedGoodsOnSale.setFailDesc(desc+",异常,京东错误码为:"+code);
			}else{
				changedGoodsOnSale.setFailDesc("MQ上下架状态失败,京东错误码为:"+code);
			}
		}else{
			changedGoodsOnSale.setFailDesc("MQ上下架状态更新京东商品的状态为下架失败,未知原因");
		}
		changedGoodsOnSale.setUpdateStatus(2);
    }
}
