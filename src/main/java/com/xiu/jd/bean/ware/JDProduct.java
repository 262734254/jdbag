package com.xiu.jd.bean.ware;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 京东商品实体类
 * @author user
 *
 */
public class JDProduct implements Serializable{

	private static final long serialVersionUID = 5738030793934987419L;
	/**
	 * 走秀商品id
	 */
	private Long xiuItemId;
	/**
	 * 走秀属性值串 格式1:1;2:2;;
	 */
	private String xiuAttrValues;
	/**
	 * 走秀属性值-属性值名称；格式 颜色:银色;尺寸:F;品牌:施华洛世奇;
	 */
	private String xiuAttrValuesZh;
	/**
	 * 走秀品牌code
	 */
	private String xiuBrandCode;
	/**
	 * 京东品牌id
	 */
	private String jdBrandId;
	/**
	 * 京东商品id
	 */
	private String jdWareId;
	/**商品的走秀码***/
	private String xiucode;
	/**京东商品分类ID***/
	private String cid;
	/***商品标题**/
	private String title;
	/**商品的高度**/
	private String high;
	/**商品的宽度**/
	private String wide;
	/**商品的重量***/
	private String weight;
	/***长度**/
	private String lenght;
	//商品属性列表,多组之间用"|"分隔，格式:aid:vid  或 aid:vid|aid1:vid1 或 aid1:vid1(需要从类目服务接口获取) 是必填
	private String attributes;
	/***进货价**/
	private String costprice;
	
	/***市场价**/
	private String marketprice;
	
	/**商品京东价格***/
	private String jdprice;
	/**商品描述***/
	private String notes;
	/***走秀商品主图路径**/
	private String mainimagepath;
	/**总的库存量***/
	private String stocknum;
	
	/***操作者名称或导入着名称**/
	private String operatename;
	
	/**走秀商品所属的品牌编码**/
	private String brandcode;
	/**走秀商品基础分类ID**/
	private String categoryid;
	
	/**导入Excel的批次号**/
	private long num;
	
	/**被锁住的用户名称 **/
	private String lockuser;
	
	/**商品是否已经更新0：未更新，1：已更新**/
	private String status;
	
	private Date createDate=new Date();
	
	private Date lastupdate=new Date();
	
	/**new add start **/
	/**京东商品id***/
	private  String  jd_ware_id;
	
	/**走秀品牌code***/
	private  String  xiu_brand_code;
	
	/***京东品牌id**/
	private  String jd_brand_id;
	
	
	/***走秀属性值-属性值名称；格式 颜色:银色;尺寸:F;品牌:施华洛世奇;**/
	private  String  xiu_attr_values_zh;
	
	
	/**走秀属性值-属性值；格式1:1;2:2;***/
	private  String  xiu_attr_values;
	
	
	/**走秀商品id***/
	private  String  xiu_item_id;
	
	/**京东品牌名**/
	private String jdBrandName;
	
	/**商品品牌对应的aid**/
	private String jdBrandAid;
	
	/**商品品牌对应的vid**/
	private String jdBrandVid;
	
	/***
	 * 0:默认,1:在售,2:下架
	 */
	private Integer onLineStatus;
	/**
	 * 京东店内分类列表
	 */
	private String shopCategory;
	
	/**
	 * 商品供应商编码
	 */
	private String supplierCode;
	
	/**
	 * 商品货号
	 */
	private String itemNum;
	
	/**
	 * 商品广告词
	 */
	private String adContent;
	
	private String globalFlag;
	
	
	
	
	public String getGlobalFlag() {
		return globalFlag;
	}
	public void setGlobalFlag(String globalFlag) {
		this.globalFlag = globalFlag;
	}
	/**new add end **/
	
	// 商品属性列表
	private List<ProductAttributeInfoModel> attributeList;
	
	public String getXiucode() {
		return xiucode;
	}
	public void setXiucode(String xiucode) {
		this.xiucode = xiucode;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getHigh() {
		return high;
	}
	public void setHigh(String high) {
		this.high = high;
	}
	public String getWide() {
		return wide;
	}
	public void setWide(String wide) {
		this.wide = wide;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getLenght() {
		return lenght;
	}
	public void setLenght(String lenght) {
		this.lenght = lenght;
	}
	public String getCostprice() {
		return costprice;
	}
	public void setCostprice(String costprice) {
		this.costprice = costprice;
	}
	public String getMarketprice() {
		return marketprice;
	}
	public void setMarketprice(String marketprice) {
		this.marketprice = marketprice;
	}
	public String getJdprice() {
		return jdprice;
	}
	public void setJdprice(String jdprice) {
		this.jdprice = jdprice;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getMainimagepath() {
		return mainimagepath;
	}
	public void setMainimagepath(String mainimagepath) {
		this.mainimagepath = mainimagepath;
	}
	public String getStocknum() {
		return stocknum;
	}
	public void setStocknum(String stocknum) {
		this.stocknum = stocknum;
	}
	public String getOperatename() {
		return operatename;
	}
	public void setOperatename(String operatename) {
		this.operatename = operatename;
	}
	public long getNum() {
		return num;
	}
	public void setNum(long num) {
		this.num = num;
	}
	public String getBrandcode() {
		return brandcode;
	}
	public void setBrandcode(String brandcode) {
		this.brandcode = brandcode;
	}
	public String getCategoryid() {
		return categoryid;
	}
	public void setCategoryid(String categoryid) {
		this.categoryid = categoryid;
	}
	public String getLockuser() {
		return lockuser;
	}
	public void setLockuser(String lockuser) {
		this.lockuser = lockuser;
	}
	public String getItemNum() {
		return itemNum;
	}
	public void setItemNum(String itemNum) {
		this.itemNum = itemNum;
	}
	public String getAdContent() {
		return adContent;
	}
	public void setAdContent(String adContent) {
		this.adContent = adContent;
	}
	public String getAttributes() {
		return attributes;
	}
	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((xiucode == null) ? 0 : xiucode.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JDProduct other = (JDProduct) obj;
		if (xiucode == null) {
			if (other.xiucode != null)
				return false;
		} else if (!xiucode.equals(other.xiucode))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "JDProduct [xiuItemId=" + xiuItemId + ", xiuAttrValues="
				+ xiuAttrValues + ", xiuAttrValuesZh=" + xiuAttrValuesZh
				+ ", xiuBrandCode=" + xiuBrandCode + ", jdBrandId=" + jdBrandId
				+ ", jdWareId=" + jdWareId + ", xiucode=" + xiucode + ", cid="
				+ cid + ", title=" + title + ", high=" + high + ", wide="
				+ wide + ", weight=" + weight + ", lenght=" + lenght
				+ ", attributes=" + attributes + ", costprice=" + costprice
				+ ", marketprice=" + marketprice + ", jdprice=" + jdprice
				+ ", notes=" + notes + ", mainimagepath=" + mainimagepath
				+ ", stocknum=" + stocknum + ", operatename=" + operatename
				+ ", brandcode=" + brandcode + ", categoryid=" + categoryid
				+ ", num=" + num + ", lockuser=" + lockuser + ", status="
				+ status + ", createDate=" + createDate + ", lastupdate="
				+ lastupdate + ", jd_ware_id=" + jd_ware_id
				+ ", xiu_brand_code=" + xiu_brand_code + ", jd_brand_id="
				+ jd_brand_id + ", xiu_attr_values_zh=" + xiu_attr_values_zh
				+ ", xiu_attr_values=" + xiu_attr_values + ", xiu_item_id="
				+ xiu_item_id + ", jdBrandName=" + jdBrandName
				+ ", jdBrandAid=" + jdBrandAid + ", jdBrandVid=" + jdBrandVid
				+ ", onLineStatus=" + onLineStatus + ", shopCategory="
				+ shopCategory + ", supplierCode=" + supplierCode
				+ ", itemNum=" + itemNum + ", adContent=" + adContent
				+ ", attributeList=" + attributeList + "]";
	}
	public String getJdWareId() {
		return jdWareId;
	}
	public void setJdWareId(String jdWareId) {
		this.jdWareId = jdWareId;
	}
	public String getXiuBrandCode() {
		return xiuBrandCode;
	}
	public void setXiuBrandCode(String xiuBrandCode) {
		this.xiuBrandCode = xiuBrandCode;
	}
	public String getJdBrandId() {
		return jdBrandId;
	}
	public void setJdBrandId(String jdBrandId) {
		this.jdBrandId = jdBrandId;
	}
	public String getXiuAttrValues() {
		return xiuAttrValues;
	}
	public void setXiuAttrValues(String xiuAttrValues) {
		this.xiuAttrValues = xiuAttrValues;
	}
	public String getXiuAttrValuesZh() {
		return xiuAttrValuesZh;
	}
	public void setXiuAttrValuesZh(String xiuAttrValuesZh) {
		this.xiuAttrValuesZh = xiuAttrValuesZh;
	}
	public Long getXiuItemId() {
		return xiuItemId;
	}
	public void setXiuItemId(Long xiuItemId) {
		this.xiuItemId = xiuItemId;
	}
	public String getJd_ware_id() {
		return jd_ware_id;
	}
	public void setJd_ware_id(String jd_ware_id) {
		this.jd_ware_id = jd_ware_id;
	}
	public String getXiu_brand_code() {
		return xiu_brand_code;
	}
	public void setXiu_brand_code(String xiu_brand_code) {
		this.xiu_brand_code = xiu_brand_code;
	}
	public String getJd_brand_id() {
		return jd_brand_id;
	}
	public void setJd_brand_id(String jd_brand_id) {
		this.jd_brand_id = jd_brand_id;
	}
	public String getXiu_attr_values_zh() {
		return xiu_attr_values_zh;
	}
	public void setXiu_attr_values_zh(String xiu_attr_values_zh) {
		this.xiu_attr_values_zh = xiu_attr_values_zh;
	}
	public String getXiu_attr_values() {
		return xiu_attr_values;
	}
	public void setXiu_attr_values(String xiu_attr_values) {
		this.xiu_attr_values = xiu_attr_values;
	}
	public String getXiu_item_id() {
		return xiu_item_id;
	}
	public void setXiu_item_id(String xiu_item_id) {
		this.xiu_item_id = xiu_item_id;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getLastupdate() {
		return lastupdate;
	}
	public void setLastupdate(Date lastupdate) {
		this.lastupdate = lastupdate;
	}
	public List<ProductAttributeInfoModel> getAttributeList() {
		return attributeList;
	}
	public void setAttributeList(List<ProductAttributeInfoModel> attributeList) {
		this.attributeList = attributeList;
	}
	public String getJdBrandName() {
		return jdBrandName;
	}
	public void setJdBrandName(String jdBrandName) {
		this.jdBrandName = jdBrandName;
	}
	public String getJdBrandAid() {
		return jdBrandAid;
	}
	public void setJdBrandAid(String jdBrandAid) {
		this.jdBrandAid = jdBrandAid;
	}
	public String getJdBrandVid() {
		return jdBrandVid;
	}
	public void setJdBrandVid(String jdBrandVid) {
		this.jdBrandVid = jdBrandVid;
	}
	public Integer getOnLineStatus() {
		return onLineStatus;
	}
	public void setOnLineStatus(Integer onLineStatus) {
		this.onLineStatus = onLineStatus;
	}
	public String getShopCategory() {
		return shopCategory;
	}
	public void setShopCategory(String shopCategory) {
		this.shopCategory = shopCategory;
	}
	public String getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	
	
	
	
	
	
	
	

}
