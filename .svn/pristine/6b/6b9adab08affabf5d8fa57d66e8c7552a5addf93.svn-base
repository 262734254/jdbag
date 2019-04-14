package com.xiu.jd.schedule.ware;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.xiu.jd.bean.page.QueryResult;
import com.xiu.jd.bean.ware.OnLineBlackProductBean;
import com.xiu.jd.schedule.BaseSchedule;
import com.xiu.jd.service.ware.OnLineBlackProductService;
import com.xiu.jd.utils.DateHelper;

/**
 * 上下架商品黑名单定时任务
 * @author root
 *
 */
public class OnLineProductBlackSchedule extends BaseSchedule{
	private static final Logger LOGGER=Logger.getLogger(OnLineProductBlackSchedule.class);
	 private static Map<Integer, List<String>>  monthAndLastThreeDay;
	 static{
		 
		 /**
		  *    初始化时间
		  *  * 例如:2014年7月份最后三天为29号,30,31号
             * 返回的为<7 List (2014-07-29 00:00:00 , 2014-07-31 23:59:59)>
		  */
		 monthAndLastThreeDay=DateHelper.getCurrentMonthLastThreeDay();
	 }
	   @Resource(name="onLineBlackProductServiceBean")
	    private OnLineBlackProductService onLineBlackProductServiceBean;
	/**
	 * 扫描黑名单商品表
	 */
	public void scanBlackProduct(){
		LOGGER.info("扫描黑名单商品表");
		Integer currentMonth=DateHelper.getCurrentMonth();//当前月份
		try{
		    if(monthAndLastThreeDay!=null){
		    	 Date toDay=DateHelper.getCurrentDate(); //当前系统时间(长格式)
		    	 List<String> values= monthAndLastThreeDay.get(currentMonth);
		    	 if(values!=null && values.size()==2){
		    		  String beforeDateString=values.get(0);
					  String afterDateString=values.get(1);
                       /**
                        * 是当前月份的最后三天了，定时器需要处理啦
                        */
					  if( (toDay.compareTo(DateHelper.stringToDate(beforeDateString)))==1 && (DateHelper.stringToDate(afterDateString).compareTo(toDay)==1)){
						  LOGGER.info("扫描黑名单商品表,可以执行了");
						  Map<String, Object> parames=new HashMap<String, Object>();
						  parames.put("firstNum", "1");
						  int pageSize =500;
						   parames.put("lastNum", pageSize);
						   parames.put("isDelete", 1);//没被删除
						  QueryResult<OnLineBlackProductBean> qr= onLineBlackProductServiceBean.getQueryResult(parames);
						  if(qr!=null){
							  long total=qr.getTotalrecord();
							  int totalPage = getTotalPage(pageSize, (int) total);
							  for (int currentPage=1; currentPage <= totalPage; currentPage++) {
									if (currentPage != 1) {
										parames.put("firstNum", pageSize * (currentPage - 1) + 1); 
										parames.put("lastNum", pageSize * currentPage );
										qr= onLineBlackProductServiceBean.getPageResule(parames);
									}
									if(qr!=null){
										List<OnLineBlackProductBean> productBeans=	qr.getResultlist();
										if(productBeans!=null && productBeans.size()>0){
											for(OnLineBlackProductBean productBean:productBeans ){
												if(productBean!=null){
													//如果商品的确认时间在当前月的最后三天内,状态不变,否则确认状态变化为待确认
													String confirmDate=productBean.getConfirmDate();
													if(confirmDate!=null){
														Date createDate =DateHelper.parseFormatDate(confirmDate);
														if(createDate!=null){
															  //如果商品的确认时间不在当前月的最后三天内
															  if( !((createDate.compareTo(DateHelper.stringToDate(beforeDateString)))==1 && (DateHelper.stringToDate(afterDateString).compareTo(createDate)==1))){
																  productBean.setConfirmStatus(2);//确认状态:1:已确认,2:待确认,3:已过期
																  productBean.setIsButtJoint(1);//上下架对接状态,默认为1,未对接,2:已对接(走秀商品上下架是否与京东对接)
																  onLineBlackProductServiceBean.update(productBean);
															  }
														}
													}
												}
											}
										}
									}
							  }
						  }
						      /**每个自然月最后一天（24：00）超期未确认时,
						                对应商品确认状态变为：已过期，已过期的商品在京东的  上下架状态需要和走秀的上下架状态保持一致；
						       (需要对接上下架到京东)
						     **/
						  if(DateHelper.isCurrentMonthLastDate(toDay, afterDateString)){
							  //是当前月的最后一天
							  Map<String, Object> Confrimparames=new HashMap<String, Object>();
							  Confrimparames.put("isDelete", 1);//没被删除
							  Confrimparames.put("confirmStatus", 3);//已过期
							  Confrimparames.put("isButtJoint", 2);//1,未对接,2:已对接(走秀商品上下架是否与京东对接)
							 int count=  onLineBlackProductServiceBean.updateAllConfrimStatus(Confrimparames);
							  LOGGER.info("是当前月的最后一天,将所有商品待确认的商品状态变为:已过期,影响的记录数 "+count);
						  }
						  
					  }else{
						  LOGGER.info("扫描黑名单商品表,不可以执行,时间未到");
					  }
		    	 }
		    }
		}catch (Exception e) {
			LOGGER.info("扫描黑名单商品表异常"+e);
			e.printStackTrace();
		}finally{
			currentMonth=DateHelper.getCurrentMonth();//当前月份
			if(!monthAndLastThreeDay.containsKey(currentMonth)){
	    		monthAndLastThreeDay.clear();
	    		monthAndLastThreeDay=DateHelper.getCurrentMonthLastThreeDay();
	    	}
		}
	
		
		
	}

}
