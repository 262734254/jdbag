package com.xiu.jd.service.ware.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xiu.jd.bean.page.QueryResult;
import com.xiu.jd.bean.ware.JdOrderMode;
import com.xiu.jd.bean.ware.JdPromo;
import com.xiu.jd.bean.ware.JdPromoSku;
import com.xiu.jd.bean.ware.JdPromoSkuRec;
import com.xiu.jd.dao.ware.JdPromoDao;
import com.xiu.jd.service.ware.JdPromoService;

@Service("jdPromoServiceBean")
public class JdPromoServiceBean implements JdPromoService {
	private Logger log = LoggerFactory.getLogger("JdPromoServiceBean");

	@Resource(name="JdPromoDaoBean")
	private JdPromoDao jdPromoDaoBean;

	@Override
	public boolean createPromo(JdPromo jdPromo,JdPromoSku jdPromoSku) {
		// TODO Auto-generated method stub
		Boolean bool=true;
		try {
			jdPromoDaoBean.insertPromo(jdPromo);
		} catch (Exception e) {
			log.error("新增单品促销信息失败"+jdPromo);
			bool=false;
			e.printStackTrace();
		}
		try {
			jdPromoDaoBean.insertPromoSku(jdPromoSku);
		} catch (Exception e) {
			log.error("新增单品促销sku失败"+jdPromoSku);
			bool=false;
			e.printStackTrace();
		}
		return bool;
	}

	@Override
	public QueryResult<JdPromo> getPageResule(Map<String, Object> parames) {
		// TODO Auto-generated method stub
		return jdPromoDaoBean.getPageResule(parames);
	}

	@Override
	public boolean addCentsOffPromo(JdPromo jdPromo) {
		Boolean bool=true;
		try {
			jdPromoDaoBean.insertPromo(jdPromo);
		} catch (Exception e) {
			log.error("新增单品促销信息失败 promoId:"+jdPromo.getPromoId());
			bool=false;
			e.printStackTrace();
		}
		return bool;
	}

	@Override
	public boolean addOrderMode(JdOrderMode jdOrderMode) {
		Boolean bool=true;
		try {
			jdPromoDaoBean.insertOrderMode(jdOrderMode);
		} catch (Exception e) {
			log.error("新增订单业务规则 promoId:"+jdOrderMode.getPromoId());
			bool=false;
			e.printStackTrace();
		}
		return bool;
	}

	@Override
	public boolean addPromoSku(JdPromoSku jdPromoSku) {
		Boolean bool=true;
		try {
			jdPromoDaoBean.insertPromoSku(jdPromoSku);
		} catch (Exception e) {
			log.error("新增单品促销sku失败"+jdPromoSku.getJdSkuId());
			bool=false;
			e.printStackTrace();
		}
		return bool;
	}

	@Override
	public JdOrderMode getJdOrderMode(Map<String, Object> parames) {
		// TODO Auto-generated method stub
		JdOrderMode jdOrderMode = new JdOrderMode();
		
		try {
			jdOrderMode = jdPromoDaoBean.getJdOrderMode(parames);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jdOrderMode;
	}

	@Override
	public int getJdPromoSkuRecCount(Map<String, Object> parames) {
		// TODO Auto-generated method stub
		int count=0;
		try {
			count = (int) jdPromoDaoBean.getCount(parames);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public List<JdPromoSkuRec> getJdPromoSkuRecList(Map<String, Object> parames) {
		 List<JdPromoSkuRec> jdPromoSkuRecList = new ArrayList<JdPromoSkuRec>();
		 try {
			 jdPromoSkuRecList=jdPromoDaoBean.getJdPromoSkuRecList(parames);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jdPromoSkuRecList;
	}

	@Override
	public void insertJdPromoSkuRec(JdPromoSkuRec jdPromoSkuRec) {
		try {
			jdPromoDaoBean.insertJdPromoSkuRec(jdPromoSkuRec);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("商品促销入库失败");
		}
		
	}

	@Override
	public QueryResult<JdPromoSkuRec> getPageJdSkuRecResule(
			Map<String, Object> parames) {
		 QueryResult<JdPromoSkuRec> qr=new QueryResult<JdPromoSkuRec>();
		 List<JdPromoSkuRec> lists=null;
		try {
			lists = jdPromoDaoBean.getJdPromoSkuRecList(parames);
		} catch (Exception e) {
			e.printStackTrace();
		}
		 qr.setResultlist(lists);
		 qr.setTotalrecord(jdPromoDaoBean.getJdPromoSkuRecCount(parames));
		 return qr;
		
	}

}
