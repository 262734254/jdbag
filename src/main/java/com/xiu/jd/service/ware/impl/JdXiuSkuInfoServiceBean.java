package com.xiu.jd.service.ware.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.jd.open.api.sdk.JdException;
import com.jd.open.api.sdk.request.ware.WareDeleteRequest;
import com.jd.open.api.sdk.request.ware.WareGetRequest;
import com.jd.open.api.sdk.request.ware.WareSkuDeleteRequest;
import com.jd.open.api.sdk.request.ware.WareUpdateDelistingRequest;
import com.jd.open.api.sdk.response.ware.WareDeleteResponse;
import com.jd.open.api.sdk.response.ware.WareGetResponse;
import com.jd.open.api.sdk.response.ware.WareSkuDeleteResponse;
import com.jd.open.api.sdk.response.ware.WareUpdateDelistingResponse;
import com.xiu.jd.bean.page.QueryResult;
import com.xiu.jd.bean.ware.JDSku;
import com.xiu.jd.bean.ware.JDSkuInfo;
import com.xiu.jd.bean.ware.JDWareAndSkuInfo;
import com.xiu.jd.bean.ware.JDXiuColorAndSize;
import com.xiu.jd.dao.ware.impl.JdXiuSkuInfoDaoBean;
import com.xiu.jd.service.ware.JdXiuSkuInfoService;
import com.xiu.jd.utils.BaseUtils;

@Service("jdXiuSkuInfoServiceBean")
public class JdXiuSkuInfoServiceBean  extends BaseUtils  implements JdXiuSkuInfoService{
	private Logger logger = Logger.getLogger(JdXiuSkuInfoServiceBean.class);
	
	@Resource
	private JdXiuSkuInfoDaoBean jdXiuSkuInfoDaoBean;
	@Override
	public boolean isExistJdXiuSku(JDXiuColorAndSize jdc) {
		boolean flag = false;
		if(jdc.getXiuColor()!= null){
			flag = jdXiuSkuInfoDaoBean.isExistSkuColor(jdc);
		}
		if(jdc.getXiuSize() != null){
			flag = jdXiuSkuInfoDaoBean.isExistSkuSize(jdc);
		}
		return flag;
	}
	@Override
	public void insertBatch(List<JDXiuColorAndSize> jdXiuList, int i) {
		if(jdXiuList.get(0).getXiuColor() != null){
			jdXiuSkuInfoDaoBean.insertBatchJdSku(jdXiuList,i,"insertBatchColor");
		}
		if(jdXiuList.get(0).getXiuSize() != null){
			jdXiuSkuInfoDaoBean.insertBatchJdSku(jdXiuList,i,"insertBatchSize");
		}
	}
	@Override
	public QueryResult<JDSkuInfo> querySkuInfoList(Map<String, Object> paramers) {
		QueryResult<JDSkuInfo> qr = jdXiuSkuInfoDaoBean.querySkuInfoList(paramers);
		return qr;
	}
	@Override
	public long getSkuInfoCount(Map<String, Object> parames) {
		return jdXiuSkuInfoDaoBean.getSkuInfoCount(parames);
	}
	@Override
	public String deleteSku(Map<String, List<String>> parames) {
		List<String> skuIds = parames.get("jdSkuIds");
		Map<String,List<String>> maps = new HashMap<String, List<String>>();
		List<String> jdSkuIdList = null;
		for (String jdSkuId : skuIds) {
			String jdWareId = queryWareId(jdSkuId);
			if(jdWareId != null && !"".equals(jdWareId)){
				if(!maps.containsKey(jdWareId)){
					jdSkuIdList = new ArrayList<String>();
					jdSkuIdList.add(jdSkuId);
					maps.put(jdWareId, jdSkuIdList);
				}else{
					jdSkuIdList.add(jdSkuId);
					maps.put(jdWareId, jdSkuIdList);
				}
			}
		}
		handleInfo(maps);
		return null;
	}
	
	private void handleInfo(Map<String,List<String>> maps){
		Iterator it = maps.keySet().iterator(); 
		while(it.hasNext()){
			String jdWareId = (String) it.next();
			int count = getSkuCount(jdWareId);
			if(count != 0){
				deleteSkuById(jdWareId,maps.get(jdWareId),count);
			}
			/*//再次获取某个商品sku数据京东
			int totalCount = getSkuCount(jdWareId);
			//如果某个商品sku数量为0,即商品没有sku,则把商品删除
			if(totalCount==0){
				logger.info("删除京东上的商品,商品ID为:"+jdWareId);
				deleteJdProduct(jdWareId);
			}*/
			
		}
	}
	
	/**
	 * 将不存在sku的京东商品删除掉
	 * @param wareId 京东商品ID
	
	private void deleteJdProduct(String wareId){
		

		if(wareId!=null && !"".equals(wareId.trim())){
			try {
			WareUpdateDelistingRequest  wareUpdateDelistingRequest=new WareUpdateDelistingRequest();

			wareUpdateDelistingRequest.setWareId(wareId);
            //商品下架
			WareUpdateDelistingResponse res = client.execute(wareUpdateDelistingRequest);
			 if("0".equals(res.getCode())){
				logger.info("商品ID为"+wareId+"的商品,下架成功");
				
			 }else{
				 //商品已经是处于下架的状态
				 if("11201012".equals(res.getCode())){
					 logger.info("商品ID为"+wareId+"商品已经是处于下架的状态,不需要再次下架");
				 }
				 //logger.info("商品ID为"+wareId+"的商品");
			 }
			    WareDeleteRequest  wareDeleteRequest= new WareDeleteRequest();
				wareDeleteRequest.setWareId(wareId);
				WareDeleteResponse response = client.execute(wareDeleteRequest);
				if(response!=null && "0".equals(response.getCode())){
					logger.info("删除京东上的商品成功,商品ID为:"+wareId);
				}
			
			}catch(Exception e){
				logger.error("删除京东上的商品异常,商品ID为:"+wareId);
				e.printStackTrace();
				
			}
		}
	
	} */
	
	
	/**
	 * 根据京东商品SKUID删除京东SKU，删除成功后将本地库相应的SKU删除
	 * @param skuId
	 */
	private void deleteSkuById(String jdWareId,List<String> jdSkuIds,int count) {
		if(jdSkuIds.size()==count){
			try {
				StringBuilder jdSkuIdSbs = new StringBuilder();
				for (int i = 0; i < jdSkuIds.size(); i++) {
					if(i==jdSkuIds.size()-1){
						jdSkuIdSbs.append(jdSkuIds.get(i));
					}else{
						jdSkuIdSbs.append(jdSkuIds.get(i)).append(",");
					}
				}
				boolean isDel = delistingWare(jdWareId);
				if(isDel){
					logger.info("商品ID为："+jdWareId+",商品SkuId为："+jdSkuIdSbs+"删除成功");
				}else{
					logger.info("商品ID为："+jdWareId+",商品SkuId为："+jdSkuIdSbs+"删除失败");
				}
			} catch (Exception e) {
				logger.error("商品ID为："+jdWareId+"删除出现异常", e);
			}
		}else{
			for (String jdSkuId : jdSkuIds) {
				WareSkuDeleteRequest  wareSkuDeleteRequest= new WareSkuDeleteRequest();
				wareSkuDeleteRequest.setSkuId(jdSkuId);
				WareSkuDeleteResponse response = null;
				try {
					response = client.execute(wareSkuDeleteRequest);
					if(response != null && "0".equals(response.getCode())){
						logger.info("商品京东skuid为："+jdSkuId+"删除成功");
						JDSku sku = new JDSku();
						sku.setJdSkuId(jdSkuId);
						jdXiuSkuInfoDaoBean.deleteSku(jdSkuId);
					}else{
						logger.info("商品京东skuid为："+jdSkuId+"删除失败,"+response.getCode()+":"+response.getZhDesc());
					}
				} catch (JdException e) {
					logger.error("商品京东skuid为："+jdSkuId+"删除出现异常",e);
				}
			}
		}
	}
	@Override
	public String queryWareId(String jdSkuId) {
		return jdXiuSkuInfoDaoBean.queryWareId(jdSkuId);
	}
	/**查询对应商品ID的skuId数量**/
	private int getSkuCount(String jdWareId){
		int count = 0;
		WareGetRequest  wareGetRequest= new WareGetRequest();
		wareGetRequest.setWareId(jdWareId);
		WareGetResponse response = null;
		try {
			response = client.execute(wareGetRequest);
			if("0".equals(response.getCode())){
				logger.info("获取商品ID为:"+jdWareId+"信息成功");
				count = response.getWare().getSkus().size();
			}else{
				logger.info("获取商品ID为:"+jdWareId+"信息失败");
				count = 0;
			}
		} catch (JdException e) {
			logger.error("获取商品ID为:"+jdWareId+"信息失败",e);
			count = 0;
		}
		return count;
	}
	
	/**
	 * 当要删除的sku个数与商品id下的个数相同时，将该商品下架
	 * @param jdWareId
	 */
	@SuppressWarnings("unused")
	private boolean delistingWare(String jdWareId) throws Exception{
		WareUpdateDelistingRequest  wareUpdateDelistingRequest=new WareUpdateDelistingRequest();
		wareUpdateDelistingRequest.setWareId(jdWareId);
		WareUpdateDelistingResponse	res = client.execute(wareUpdateDelistingRequest);
		if("0".equals(res.getCode()) || "11201012".equals(res.getCode())){
			logger.info("商品id为："+jdWareId+"已经下架成功或该商品处于下架状态");
			boolean isDel = deleteWare(jdWareId);
			if(isDel){//商品下架成功并且删除商品成功
				return true;
			}else{//商品下架成功但删除商品失败
				return false;
			}
		}else{//商品下架失败，无法删除商品
			logger.info("商品id为："+jdWareId+"已经下架成功或该商品处于下架状态");
			return false;
		}
	}
	
	/**
	 * 商品下架成功之后删除该商品
	 */
	private boolean deleteWare(String jdWareId) throws Exception{
		WareDeleteRequest  wareDeleteRequest= new WareDeleteRequest();
		wareDeleteRequest.setWareId(jdWareId);
		WareDeleteResponse res = client.execute(wareDeleteRequest);
		if("0".equals(res.getCode())){
			logger.info("商品ID为："+jdWareId+"删除成功");
			jdXiuSkuInfoDaoBean.deleteSkuByWareId(jdWareId);
			jdXiuSkuInfoDaoBean.deleteWareByWareId(jdWareId);
			return true;//删除成功
		}else{
			logger.info("商品ID为："+jdWareId+"删除失败");
			return false;//删除失败
		}
	}
}
