package com.xiu.jd.service;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;



import com.xiu.jd.bean.page.QueryResult;
import com.xiu.jd.bean.ware.JDAttribute;
import com.xiu.jd.bean.ware.JDAttributeValue;
import com.xiu.jd.bean.ware.JDCategory;
import com.xiu.jd.bean.ware.XiuCategory;
import com.xiu.jd.bean.ware.XiuJdAttValue;
import com.xiu.jd.bean.ware.XiuJdBrand;
import com.xiu.jd.bean.ware.XiuJdCategory;
import com.xiu.jd.vo.DictVo;

public interface CategoryService {
	
	/**
	 * 查询走秀属性值对应中的类目
	 * @return
	 * @throws Exception
	 */
	public List<DictVo> queryXiuCategoryForValueRef()throws Exception;
	
	/**
	 * 查询走秀属性值对应中的属性
	 * @param cid
	 * @return
	 * @throws Exception
	 */
	public List<DictVo> queryXiuAttrForValueRef(String cid)throws Exception;
	
	/**
	 * 查询走秀属性值
	 * @param aid
	 * @return
	 * @throws Exception
	 */
	public List<DictVo> queryXiuAttrValueForValueRef(String aid)throws Exception;
	
	/**
	 * 查询京东-走秀类目映射
	 * @param xiuJdCategory
	 * @return
	 * @throws Exception
	 */
	public List<XiuJdCategory> queryXiuJdCategory(XiuJdCategory xiuJdCategory)throws Exception;
	
	/**
	 * 删除京东-走秀类目映射
	 * @param id
	 * @throws Exception
	 */
	public void deleteXiuJdCategory(Integer id)throws Exception;
	
	/**
	 * 更新京东-走秀类目映射
	 * @param xiuJdCategory
	 * @throws Exception
	 */
	public void updateXiuJdCategory(XiuJdCategory xiuJdCategory)throws Exception;
	
	/**
	 * 根据id获取京东-走秀类目映射
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public XiuJdCategory getXiuJdCategory(Integer id)throws Exception;
	
	/**
	 * 分页查询京东-走秀类目映射
	 * @param xiuJdCategory
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public QueryResult<XiuJdCategory> getXiuJdCategoryPageResule(XiuJdCategory xiuJdCategory,int pageNum,int pageSize)throws Exception;
	
	/**
	 * 添加京东-走秀类目映射
	 * @param list
	 * @throws Exception
	 */
	public void insertXiuJdCategory(List<XiuJdCategory> list)throws Exception;
	
	public void deleteAll()throws Exception;
	
	/**
	 * 查询走秀类目
	 * @param xiuCategory
	 * @return
	 * @throws Exception
	 */
	public List<XiuCategory> queryXiuCategory(XiuCategory xiuCategory)throws Exception;
	
	/**
	 * 获取京东类目层次最大深度
	 * @return
	 * @throws Exception
	 */
	public Integer queryJDCategoryMaxLev() throws Exception;
	
	/**
	 * 查询子类目
	 * @return
	 * @throws Exception
	 */
	public List<JDCategory> queryJDCategoryByFid(Integer fid)throws Exception;
	
	/**
	 * 查询所有类目
	 * @return
	 * @throws Exception
	 */
	public List<JDCategory> queryJDCategory()throws Exception;
	
	/**
	 * 根据类目ID查询属性信息
	 * @param cid
	 * @return
	 * @throws Exception
	 */
	public List<JDAttribute> queryJDAttributeByCid(String cid)throws Exception;
	
	/**
	 * 根据属性ID查询属性值信息
	 * @param aid
	 * @return
	 * @throws Exception
	 */
	public List<JDAttributeValue> queryJDAttrValueByAid(Long aid)throws Exception;
	
	/**
	 * 删除走秀-京东属性值映射
	 * @param id
	 * @throws Exception
	 */
	public void deleteXiuJdAttValue(Integer id)throws Exception;
	
	/**
	 * 更新走秀-京东属性值映射
	 * @param xiuJdAttValue
	 * @throws Exception
	 */
	public void updateXiuJdAttValue(XiuJdAttValue xiuJdAttValue)throws Exception;
	
	/**
	 * 根据id获取走秀-京东属性值映射
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public XiuJdAttValue getXiuJdAttValue(Integer id)throws Exception;
	
	public JDCategory getJdCategoryById(int categoryId)throws Exception;
	
	/**
	 * 分布查询走秀-京东属性值映射
	 * @param xiuJdAttValue
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public QueryResult<XiuJdAttValue> getXiuJdAttValuePageResule(
			XiuJdAttValue xiuJdAttValue, int pageNum, int pageSize)
			throws Exception;
	
	/**
	 * 添加走秀-京东属性值映射
	 * @param list
	 * @throws Exception
	 */
	public void insertXiuJdAttValue(List<XiuJdAttValue> list)throws Exception;
	
	public QueryResult<XiuJdBrand> getXiuJdBrandPageResule(
			XiuJdBrand xiuJdBrand, int pageNum, int pageSize)
			throws Exception;
	

	
	public void insertXiuJdBrand(List<XiuJdBrand> list)throws Exception;
	public void insertEbayJdBrand(List<XiuJdBrand> list)throws Exception;
	public void insertJdBrandXiu(List<XiuJdBrand> list)throws Exception;
	
	public void deleteXiuJdBrand(Integer id)throws Exception;
	
	public QueryResult<XiuJdBrand> getXiuBrandPageResule(
			XiuJdBrand xiuJdBrand, int pageNum, int pageSize)
			throws Exception;
	
	public String queryXiuBrand2(List<XiuJdBrand> list)throws Exception;
	
	/**
	 * 批量删除映射的属性
	 * @param parames
	 * @return
	 */
	public int batchDeleteAttri(Map<String, Object> parames);
	
	/**
	 * 更新jd_xiu_brand表通过京东第三级类目ID
	 * @param brand
	 * @return
	 */
	public int updateBrandByJdCid(XiuJdBrand brand);
	
	/**
	 * 批量删除映射的品牌
	 * @param parames
	 * @return
	 */
	public int batchDeleteBrand(Map<String, Object> parames);
	
	/**
	 * 导出excel模板
	 * @param wb
	 * @param map
	 */
	public void createWb(HSSFWorkbook wb,Map<String,List<JDAttributeValue>> map,String jdCid);
	
	public List<XiuJdAttValue> queryXiuJdAttValue(Map<String,Object> maps);
	/**
	 * 本地是否存在该走秀类目的映射
	 * @return
	 */
	public boolean isExistXiuJdCategory(XiuJdCategory xiuJdCategory);
	/**
	 * 本地是否存在该走秀类目映射的属性值
	 */
	public boolean isExistXiuJdValue(XiuJdAttValue xiuJdAttValue);
	
	
	public XiuJdBrand getXiuBrand(XiuJdBrand xiuJdBrand);
	

	
	
	
	

	

	

	

	
}
