package com.xiu.jd.schedule.ware;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.jd.open.api.sdk.JdException;
import com.jd.open.api.sdk.domain.ware.Sku;
import com.jd.open.api.sdk.domain.ware.Ware;
import com.jd.open.api.sdk.request.ware.WareGetRequest;
import com.jd.open.api.sdk.request.ware.WareSkuUpdateRequest;
import com.jd.open.api.sdk.request.ware.WareSkusGetRequest;
import com.jd.open.api.sdk.response.ware.WareGetResponse;
import com.jd.open.api.sdk.response.ware.WareSkuUpdateResponse;
import com.jd.open.api.sdk.response.ware.WareSkusGetResponse;
import com.xiu.jd.bean.page.QueryResult;
import com.xiu.jd.bean.ware.JDSku;
import com.xiu.jd.bean.ware.JdSkuIdSyn;
import com.xiu.jd.schedule.BaseSchedule;
import com.xiu.jd.service.ware.JdSkuIdSynService;
import com.xiu.jd.service.ware.JdSkuService;

/**
 * 扫描jd_skuid_syn表
 * 同步京东商品skuid到本地数据jd_product_sku_info中的jdskuid字段
 * @author user
 *
 */
public class JdSkuIdSynSchedule extends BaseSchedule{
	private static final Logger logger = Logger.getLogger(JdSkuIdSynSchedule.class);
   @Resource(name="jdSkuIdSynServiceBean")
   private JdSkuIdSynService jdSkuIdSynServiceBean;
   @Resource(name="jdSkuServiceBean")
    	private JdSkuService<JDSku> jdSkuServiceBean;
	

	public void updateJdSkuIdToProductSku(){
		logger.info("======================>扫描jd_skuid_syn表表开始");
		Map<String, Object> parames=new HashMap<String, Object>();
		//int pageSize=2;
		parames.put("firstNum", 1);
		parames.put("lastNum", pageSize);
		QueryResult<JdSkuIdSyn> queryResule =jdSkuIdSynServiceBean.getPageResule(parames);
		if(queryResule!=null){
			long total=queryResule.getTotalrecord();
			int totalPage=getTotalPage(pageSize,(int)total);
			
			for(int currentPage=0;currentPage<totalPage;currentPage++){
				if(currentPage!=0){
					parames=new HashMap<String, Object>();
					parames.put("firstNum", pageSize*currentPage+1);
					parames.put("lastNum", pageSize*(currentPage+1));
					queryResule =jdSkuIdSynServiceBean.getPageResule(parames);
				}
				if(queryResule!=null){
						List<JdSkuIdSyn> jdSkuIdSyns=queryResule.getResultlist();
						if(jdSkuIdSyns!=null && jdSkuIdSyns.size()>0){
							for(JdSkuIdSyn jdSkuIdSyn:jdSkuIdSyns){
								//System.out.println("京东商品ID="+jdSkuIdSyn.getJdWareId());
								String jdWareId=jdSkuIdSyn.getJdWareId();
								if(jdWareId!=null && !"".equals(jdWareId.trim())){
									logger.info("京东商品ID==>"+jdWareId);
									WareGetRequest  wareGetRequest=new WareGetRequest();
									wareGetRequest.setWareId(jdWareId);
									WareGetResponse response=null;
									try {
										response = client.execute(wareGetRequest);
									} catch (JdException e) {
										logger.error("==调用京东API异常 client3 ==>"+e);
										e.printStackTrace();
									}
									if(response!=null && "0".equals(response.getCode())){
										Ware ware=response.getWare();
										
										if(ware!=null){
											List<Sku> skus=ware.getSkus();
											String wareId=ware.getWareId()+"";
											if(skus!=null && skus.size()>0){
												int size=skus.size();
												int count=0;
												for(Sku s:skus){
													if(s==null){
														continue;
													}
													String skusn=s.getOuterId();
													if(skusn!=null && !"".equals(skusn.trim())){
														String jdSkuId=s.getSkuId()+"";
														  JDSku	jdSku=new JDSku();
														  jdSku.setSkusn(skusn);
														  jdSku.setJdSkuId(jdSkuId);
															int  num=jdSkuServiceBean.update(jdSku);
															logger.info("京东商品ID"+jdWareId+",京东sku="+jdSkuId+",num="+num);
															
															 if(num!=0){
																 count++;
															 }
													}
												}
												 logger.info("京东商品ID==>"+jdWareId+"size ====>"+ size+" ,count ====>"+count);
												//更新的记录数与被更新的数据一致
												if(size==count){
													int recode=jdSkuIdSynServiceBean.deleteById(wareId);
													logger.info(recode==0?"京东商品ID==>:"+wareId+"删除失败":"京东商品ID==>:"+wareId+"删除成功");
													
												}
											}
										}
									}
									
									
								}
							}
							
						}
					}
				
			}
			
		}
		
	}
	/*public void updateJdSkuIdToProductSku(){
		logger.info("======================>扫描jd_skuid_syn表表开始");
		Map<String, Object> parames=new HashMap<String, Object>();
		//int pageSize=2;
		parames.put("firstNum", 1);
		parames.put("lastNum", pageSize);
		QueryResult<JdSkuIdSyn> queryResule =jdSkuIdSynServiceBean.getPageResule(parames);
		if(queryResule!=null){
			long total=queryResule.getTotalrecord();
			int totalPage=getTotalPage(pageSize,(int)total);
			
			for(int currentPage=0;currentPage<totalPage;currentPage++){
				if(currentPage!=0){
					parames=new HashMap<String, Object>();
					parames.put("firstNum", pageSize*currentPage+1);
					parames.put("lastNum", pageSize*(currentPage+1));
					queryResule =jdSkuIdSynServiceBean.getPageResule(parames);
				}
				if(queryResule!=null){
						List<JdSkuIdSyn> jdSkuIdSyns=queryResule.getResultlist();
						if(jdSkuIdSyns!=null && jdSkuIdSyns.size()>0){
							for(JdSkuIdSyn jdSkuIdSyn:jdSkuIdSyns){
								//System.out.println("京东商品ID="+jdSkuIdSyn.getJdWareId());
								String jdWareId=jdSkuIdSyn.getJdWareId();
								if(jdWareId!=null && !"".equals(jdWareId.trim())){
									 WareSkusGetRequest request=new WareSkusGetRequest();
									    WareSkusGetResponse response=null;
							               
							     		request.setWareIds( jdWareId);
							     		request.setFields( "jd_price,sku_id,ware_id,attributes,outer_id,color_value,size_value,stock_num,outer_id" );
										try {
											response = client.execute(request);
										} catch (JdException e) {
											logger.error("==调用京东API异常 client3 ==>"+e);
											e.printStackTrace();
										}
										Map<String,Object> parame=new HashMap<String,Object>();
										parame.put("jdWareId", jdWareId);
										List<JDSku> skuList=null;
										try {
										     logger.info("查询本地sku信息："+jdWareId);
										     skuList = jdSkuServiceBean
												.findSkuBywareId(parame);
										} catch (Exception e) {
										     logger.error("查询本地sku信息错误："+jdWareId+" "+e);
										}
									if(response!=null && "0".equals(response.getCode())){
										
										List<Sku> skus=response.getSkus();
										if(skus!=null && skus.size()>0){
											int size=skus.size();
											int count=0;
											for(Sku s:skus){
												if(s==null){
													continue;
												}
											    WareSkuUpdateRequest skuUpdateRequest=new WareSkuUpdateRequest();
	                                            String outerId=this.getSkuSn(s, skuList);
	                                            logger.info("查询相对应走秀sku： 京东Id"+jdWareId+" 京东sku："+s.getSkuId()+"走秀sku："+outerId);
											    skuUpdateRequest.setSkuId(s.getSkuId().toString() );
											    skuUpdateRequest.setWareId(Long.valueOf(s.getWareId()).toString());
											    skuUpdateRequest.setOuterId(outerId);
											    skuUpdateRequest.setJdPrice( s.getJdPrice() );
											    skuUpdateRequest.setStockNum(Long.valueOf(s.getStockNum()).toString());
									     		try {
									     			logger.info("更新京东sku信息"+jdWareId+" 京东sku："+s.getSkuId()+"走秀sku："+outerId);
									     			WareSkuUpdateResponse updateRes=client.execute(skuUpdateRequest);
	                                                if(updateRes!=null&&updateRes.getCode().equals("0")){
														String skusn=s.getOuterId();
														if(skusn!=null && !"".equals(skusn.trim())){
															String jdSkuId=s.getSkuId()+"";
															  JDSku	jdSku=new JDSku();
															  jdSku.setSkusn(skusn);
															  jdSku.setJdSkuId(jdSkuId);
															  int  num=jdSkuServiceBean.update(jdSku);
															  logger.info("京东商品ID"+jdWareId+",京东sku="+jdSkuId+",num="+num);
															  if(num!=0){
																 count++;
															  }
														}
	                                                }else{
	                                                	logger.info("更新京东sku信息失败"+jdWareId+" 京东sku："+s.getSkuId()+"走秀sku："+outerId+"京东返回值："+updateRes.getCode()+updateRes.getZhDesc());
	                                                }
									     			
									     		} catch (Exception e) {
									     			logger.info("更新京东sku信息失败"+jdWareId+" 京东sku："+s.getSkuId()+"走秀sku："+outerId);
									     			e.printStackTrace();
									     		} 
											}
											 logger.info("京东商品ID==>"+jdWareId+"size ====>"+ size+" ,count ====>"+count);
											//更新的记录数与被更新的数据一致
											if(size==count){
												int recode=jdSkuIdSynServiceBean.deleteById(jdWareId);
												logger.info(recode==0?"京东商品ID==>:"+jdWareId+"删除失败":"京东商品ID==>:"+jdWareId+"删除成功");
												
											}
										}
									}
									
									
									
								}
							}
							
						}
					}
				
			}
			
		}
		
	}
	
	private String getSkuSn(Sku sku,List<JDSku> list){
		String skuId = null;
		try {
		
			if (list == null || list.size() == 0) {
				return null;
			}
			for (JDSku jdSku : list) {
				String jdAttribtue = sku.getAttributes();
				if (jdAttribtue != null
						&& jdAttribtue.equals(jdSku.getAttributes())) {
					skuId = jdSku.getSkusn();
					break;
				}
			}
			logger.error("获取sku成功"+sku.getAttributes()+" "+sku.getWareId()+" "+sku.getSkuId());
			
		} catch (Exception e) {
			logger.error("获取sku失败"+sku.getAttributes()+" "+sku.getWareId()+" "+sku.getSkuId());
		}
		return skuId;
		 
	 }*/
}
