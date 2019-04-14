package com.xiu.jd.service.ware.impl;


import org.springframework.stereotype.Service;

import com.xiu.jd.bean.ware.JdChangedGoodsOnSale;
import com.xiu.jd.dao.BaseDaoImpl;
import com.xiu.jd.service.ware.JdChangeGoodsOnSaleService;

@Service("jdChangeGoodsOnSaleServiceBean")
public class JdChangeGoodsOnSaleServiceBean extends BaseDaoImpl<JdChangedGoodsOnSale> implements
		JdChangeGoodsOnSaleService {}
