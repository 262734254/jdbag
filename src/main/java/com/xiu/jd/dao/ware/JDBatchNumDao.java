package com.xiu.jd.dao.ware;

import com.xiu.jd.dao.BaseDao;

public interface JDBatchNumDao<T> extends BaseDao<T> {
    /**
     * 取得当前数据库中的批次号
     * @return
     */
	public long getBatchNum();
}
