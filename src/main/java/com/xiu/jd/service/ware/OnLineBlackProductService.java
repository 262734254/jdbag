package com.xiu.jd.service.ware;

import java.util.List;
import java.util.Map;

import com.xiu.jd.bean.page.QueryResult;
import com.xiu.jd.bean.ware.OnLineBlackProductBean;
import com.xiu.jd.dao.BaseDao;

/**
 * 上传商品上下架黑名单Excle描述信息业务接口
 * @author root
 *
 */
public interface OnLineBlackProductService extends
		BaseDao<OnLineBlackProductBean> {
	


	/**
	 * 批量保存商品走秀码到上下架黑名单表中(jd_online_black_product);
	 * @param xiuCodes
	 */
	Object insertBlackProductBeans(final List<OnLineBlackProductBean> blackProductBeans);
	
	/**
	 * 存在黑名单表,就是黑名单
	 * 商品是否是上下架黑名单商品
	 * @param xiuCode 走秀码,删除状态
	 * @return true:是黑名单商品(存在),false不是黑名单中的商品(不存在)
	 */
	public boolean isOnLineBlack(String xiuCode);
	
	/**
	 * 判断走秀码在本地是否存在
	 * @param xiuCode
	 * @return
	 */
	public boolean xiuCodeIsExits(String xiuCode);
	

	/**
	 * 更新商品黑名单表信息 jd_online_black_product
	 * @param parames
	 * @return
	 */
	int updateOnLineBlackProductBean(Map<String, Object> parames);

	/**
	 * 后台定时任务分页查询
	 * @param parames
	 * @return
	 */
	QueryResult<OnLineBlackProductBean> getQueryResult(
			Map<String, Object> parames);

	/**
	 * 将所有商品待确认的商品状态变为:已过期
	 * @param confrimparames
	 */
	int updateAllConfrimStatus(Map<String, Object> confrimparames);
	
	
	

}
