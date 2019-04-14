package com.xiu.jd.schedule.ware;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.jd.open.api.sdk.FileItem;
import com.jd.open.api.sdk.JdException;
import com.jd.open.api.sdk.domain.ware.WarePropimg;
import com.jd.open.api.sdk.domain.website.ware.WareImg;
import com.jd.open.api.sdk.request.ware.WarePropimgAddRequest;
import com.jd.open.api.sdk.request.ware.WarePropimgDeleteRequest;
import com.jd.open.api.sdk.request.ware.WarePropimgsGetRequest;
import com.jd.open.api.sdk.response.ware.WarePropimgAddResponse;
import com.jd.open.api.sdk.response.ware.WarePropimgDeleteResponse;
import com.jd.open.api.sdk.response.ware.WarePropimgsGetResponse;
import com.xiu.image.biz.dto.GoodsInfoDTO;
import com.xiu.image.biz.hessian.interfaces.OriImageCheckHessianService;
import com.xiu.image.biz.hessian.interfaces.SkuImagesPair;
import com.xiu.jd.bean.ware.JDSku;
import com.xiu.jd.schedule.BaseSchedule;
import com.xiu.jd.service.ware.JdSkuService;
import com.xiu.jd.utils.HttUtils;
import com.xiu.jd.utils.ParseProperties;
/**
 * 定时推送商品sku图片
 * @author Administrator
 *
 */
public class PushWareSkuPicSchedule extends BaseSchedule  {
	private static final Logger logger = Logger.getLogger(PushWareSkuPicSchedule.class);
	
	
	@Resource(name="jdSkuServiceBean")
	private JdSkuService<JDSku> jdSkuServiceBean;
	
	/**图片获取接口**/
	@Resource(name="imageHessianService")
	private OriImageCheckHessianService imageHessianService ;
	
	
	@SuppressWarnings("null")
	public void pushWareSkuPic(){
		List<JDSku> jdSkus = jdSkuServiceBean.isUpLoadToJd();
		if(jdSkus!=null){
			List<String> colorList = new ArrayList<String>();
			for (JDSku jdSku : jdSkus) {
				WarePropimgAddRequest picRequest = new WarePropimgAddRequest(); 
				picRequest.setWareId(jdSku.getWareId());
				String[] attrId = null;
				String[] valueID = null;
				String jdColorAttr = "";
				if(jdSku.getAttributes() != null && !"".equals(jdSku.getAttributes())){
					attrId = jdSku.getAttributes().split("\\^");
					valueID = attrId[0].split(":");
					String jdAttrName = jdSkuServiceBean.findSaleAttrName(valueID[0]);
					if(attrId.length==2){
						if(jdAttrName.trim().equals("颜色") || jdAttrName.trim().equals("颜 色")){
							jdColorAttr = valueID[1];
						}else{
							jdColorAttr = attrId[1].split(":")[1];
						}
					}else{
						if(jdAttrName.trim().equals("颜色") || jdAttrName.trim().equals("颜 色")){
							jdColorAttr = valueID[1];
						}else{
							jdColorAttr = "0000000000";
						}
					}
					int size = getWareImge(jdSku.getWareId(),jdColorAttr);
					if(size != 0 && size ==5){
						jdSku.setStatus("3");
						jdSkuServiceBean.updateStatus(jdSku);
						logger.info("该颜色的sku图片已经上传");
						continue;
					}
				}else{
					jdColorAttr = "0000000000";
					logger.info("商品编码为："+jdSku.getWareId()+",商品SKU为:"+jdSku.getSkusn()+"属性为空,属性值ID用0000000000代替");
					int size = getWareImge(jdSku.getWareId(),jdColorAttr);
					if(size == 0 || size == 1){
						jdSku.setStatus("3");
						jdSkuServiceBean.updateStatus(jdSku);
						logger.info("该颜色的sku图片已经上传");
						continue;
					}
				}
				
				if(colorList.size()==0){
					colorList.add(jdSku.getWareId()+"_"+jdColorAttr);
				}else{
					if(colorList.contains(jdSku.getWareId()+"_"+jdColorAttr)){
						jdSku.setStatus("3");
						jdSkuServiceBean.updateStatus(jdSku);
						logger.info("该颜色的sku图片已经上传");
						continue;
					}else{
						colorList.add(jdSku.getWareId()+"_"+jdColorAttr);
					}
				}
				picRequest.setAttributeValueId(jdColorAttr);
				picRequest.setMainPic(true);
				FileItem fileItem = null;
				//String picDate = "20131029";				
				String picDate = jdSkuServiceBean.findPicDate(jdSku);
				if(jdSku.getSkuImagePath() !=null && !"".equals(jdSku.getSkuImagePath())){
				fileItem = new FileItem(jdSku.getSkusn()+".jpg",HttUtils.getResponseData(jdSku.getSkuImagePath()+"/g1_800_800.jpg"));
 			    }else{
				String image = ParseProperties.getPropertiesValue("IMAGE_PREFIX")+ "/upload/goods" +picDate+"/"+jdSku.getXiucode()+"/"+jdSku.getSkusn()+"/g1_800_800.jpg";
				fileItem = new FileItem(jdSku.getSkusn()+".jpg",HttUtils.getResponseData(image));
   			    }
				picRequest.setImage(fileItem);
				WarePropimgAddResponse warePropimgUploadResponse = null;
				try {
					
					WarePropimgsGetRequest getImageRequest=new WarePropimgsGetRequest();
					getImageRequest.setWareId(jdSku.getWareId());
					getImageRequest.setAttributeValueId(jdColorAttr);
					WarePropimgsGetResponse getImageResponse;					
					getImageResponse = client.execute(getImageRequest);
					WarePropimgDeleteRequest delImageRequest=new WarePropimgDeleteRequest();
			        List<WarePropimg> l=getImageResponse.getWarePropimg();
			        if (l!=null) {
						for (WarePropimg warePropimg : l) {
							if (warePropimg.getMain().equals("是")) {
								delImageRequest.setWareId(Long
										.toString(warePropimg.getWareId()));
								delImageRequest
										.setAttributeValueId(jdColorAttr);
								delImageRequest.setImageId(Long
										.toString(warePropimg.getImgId()));
								logger.info("商品编码为：" + jdSku.getWareId()
										+ ",skusn码为:" + jdSku.getSkusn()
										+ "颜色属性：" + jdColorAttr
										+ " sku主图上传后该图片会被删除");
								break;
							}
						}
					}
					warePropimgUploadResponse = client.execute(picRequest);	
			        WarePropimgDeleteResponse delImageResponse=client.execute(delImageRequest);	//上传主图会将原来主图覆盖，会出现主图重复或者第二张图不显示情况，所以上传主图后将原来主图删除					
					if(delImageResponse!=null&&delImageResponse.getCode().equals("0")){
						logger.info("商品编码为："+jdSku.getWareId()+",skusn码为:"+jdSku.getSkusn()+"sku主图删除成功");
					}	
					else{
						logger.info("商品编码为："+jdSku.getWareId()+",skusn码为:"+jdSku.getSkusn()+"sku主图删除失败");
					}
					
					if(warePropimgUploadResponse != null && warePropimgUploadResponse.getCode().equals("0")){
						logger.info("商品编码为："+jdSku.getWareId()+",skusn码为:"+jdSku.getSkusn()+"sku主图片上传成功");
						int imageCount=this.getSkuImageCount(jdSku, picDate);
						for(int i = 2;i<=5;i++){							
							int index=0;
							if (i<=imageCount){
								index=i;
							}else{
								index=1;
							}
							WarePropimgAddRequest picRequestpic = new WarePropimgAddRequest();
							picRequestpic.setWareId(jdSku.getWareId());
							picRequestpic.setMainPic(false);
							picRequestpic.setAttributeValueId(jdColorAttr);
							String img = ParseProperties.getPropertiesValue("IMAGE_PREFIX")+ "/upload/goods" +picDate+"/"+jdSku.getXiucode()+"/"+jdSku.getSkusn()+"/g"+index+"_800_800.jpg";
							fileItem = new FileItem(jdSku.getSkusn()+"_"+index+".jpg",HttUtils.getResponseData(img));
							picRequestpic.setImage(fileItem);
							warePropimgUploadResponse = client.execute(picRequestpic);
							if(warePropimgUploadResponse != null && warePropimgUploadResponse.getCode().equals("0")){
								logger.info("商品编码为："+jdSku.getWareId()+",skusn码为:"+jdSku.getSkusn()+"_"+i+"_sku附图片上传成功");
							}else{
								logger.info("商品编码为："+jdSku.getWareId()+",skusn码为:"+jdSku.getSkusn()+"_"+i+"_sku附图片上传失败");
							}
						}
						jdSku.setStatus("1");
						jdSkuServiceBean.updateStatus(jdSku);
					}else{
						jdSku.setStatus("2");
						jdSkuServiceBean.updateStatus(jdSku);
						logger.info(warePropimgUploadResponse.getZhDesc()+warePropimgUploadResponse.getMsg());
						logger.info("商品编码为："+jdSku.getWareId()+",skusn码为:"+jdSku.getSkusn()+"主图片上传失败");
					}
				} catch (JdException e) {
					jdSku.setStatus("4");
					jdSkuServiceBean.updateStatus(jdSku);
					logger.error("商品编码为："+jdSku.getWareId()+",skusn码为:"+jdSku.getSkusn()+"主图片上传异常",e);
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 根据商品ID和颜色ID查询图片
	 * @param wareId 商品ID
	 * @param colorId颜色ID
	 * @return
	 */
	public int getWareImge(String wareId,String colorId){
		int picSize = 0;
		WarePropimgsGetRequest warePropimgGetRequest = new WarePropimgsGetRequest();
		warePropimgGetRequest.setWareId(wareId);
		warePropimgGetRequest.setAttributeValueId(colorId);
		try {
			WarePropimgsGetResponse response = client.execute(warePropimgGetRequest);
			if(response != null && "0".equals(response.getCode())){
				picSize = response.getWarePropimg().size();
			}
		} catch (JdException e) {
			logger.error("查询商品"+wareId+"的图片出现异常", e);
			e.printStackTrace();
		}
		return picSize;
	}
	
	private int getSkuImageCount(JDSku jdSku,String picStr){
		int count=0;
		GoodsInfoDTO DTO = new GoodsInfoDTO();
		String[] skuCode = new String[1];
		skuCode[0] = jdSku.getSkusn();
		DTO.setSkuCodes(skuCode);
		DTO.setXiuCode(jdSku.getXiucode());
		DTO.setLength(5);
		DTO.setCreateTimeStr(picStr);
		List<SkuImagesPair> list =null;
		try {
			logger.info("调用图片hessian" + jdSku.getSkusn());
			list = imageHessianService.checkImageExists(DTO);
			logger.info("调用图片hessian结束" + jdSku.getSkusn());
			if (list != null && list.size() > 0) {
				int[] images = list.get(0).getImages();
				for (int i : images) {
					if (i == 1) {
						count++;
					}
				}
			}
		} catch (Exception e) {
			logger.error("使用图片hessian失败"+"sku"+jdSku.getSkusn());
		}
		return count;
		
	}
}
