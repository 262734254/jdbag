package com.xiu.jd.service.ware.impl;

import org.springframework.stereotype.Service;

import com.xiu.jd.bean.ware.JdChangedGoodsPrice;
import com.xiu.jd.dao.BaseDaoImpl;
import com.xiu.jd.service.ware.JdChangeGoodsPriceService;

@Service(value="jdChangeGoodsPriceService")
public class JdChangeGoodsPriceServiceBean extends BaseDaoImpl<JdChangedGoodsPrice> implements
		JdChangeGoodsPriceService {
	
}
