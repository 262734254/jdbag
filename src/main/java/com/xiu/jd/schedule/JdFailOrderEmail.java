package com.xiu.jd.schedule;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.xiu.email.bean.EmailBean;
import com.xiu.email.hessian.IEmailHessianService;
import com.xiu.email.hessian.SendingResult;
import com.xiu.jd.bean.email.JdEmail;
import com.xiu.jd.bean.order.JDOrderItemInfo;
import com.xiu.jd.service.order.JDOrderItemInfoService;
import com.xiu.jd.service.user.JDConsigneenInfoService;
import com.xiu.jd.utils.ParseProperties;

public class JdFailOrderEmail {
	private static final Logger logger = Logger.getLogger(JdFailOrderEmail.class);
	@Resource(name="jDOrderItemInfoServiceBean")
	private JDOrderItemInfoService jDOrderItemInfoServiceBean;
	
	@Resource(name="jDConsigneenInfoServiceBean")
	private JDConsigneenInfoService jDConsigneenInfoServiceBean;
	
	@Resource(name = "emailSendService")
	private IEmailHessianService emailSendService;
	public void sendFailOderEmail(){
		try {
			List<JDOrderItemInfo> list=null;
			try {
			    list=jDOrderItemInfoServiceBean.getFailOrderItem();
			    if(list!=null&&list.size()>0){
			    	Template template = Velocity.getTemplate("fail_order_mail.html");
					VelocityContext context = new VelocityContext();
					context.put("jdOrderItemInfoList", list);
					StringWriter writer = new StringWriter();
					template.merge(context, writer);
					String content = writer.toString();
					JdEmail jdEmail=null;
					String address=null;
					try {
						jdEmail= jDConsigneenInfoServiceBean.getEmail(1);
						address=jdEmail.getEmailAddress();
					} catch (Exception e) {
						logger.error("获取邮箱地址失败");
						e.printStackTrace();
					}
					if(address==null||"".equals(address)){
						logger.info("获取邮箱地址失败");
						return;
					}	
					String buyerName = ParseProperties.getPropertiesValue("remote.url.osc.buyerName");
					EmailBean emailBean=new EmailBean();
					emailBean.setSubject("京东店铺jd_bag订单对接异常");
					emailBean.setReceiverMail(jdEmail.getEmailAddress());
					emailBean.setCreator("走秀网");
					emailBean.setBody("<h3>以下是京东推送失败的订单，请注意查收：</h3>"+content);
					SendingResult result=emailSendService.sendEmail(emailBean);
					if(result!=null&&result.isSuccess()){
						for (JDOrderItemInfo jdOrderItemInfo : list) {
							Map map = new HashMap();
							map.put("jdOrderId", jdOrderItemInfo.getJdOrderId());
							try {
								jDOrderItemInfoServiceBean.updateEmailStatus(map);
								logger.info("jdOrderId:"+jdOrderItemInfo.getJdOrderId()+"更新成功");
							} catch (Exception e) {
								logger.error("jdOrderId:"+jdOrderItemInfo.getJdOrderId()+"更新状态失败");
								e.printStackTrace();
							}
							

						}
					
					}
			    }
			    
			} catch (Exception e) {
				logger.error("获取失败订单失败");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
	}
}
