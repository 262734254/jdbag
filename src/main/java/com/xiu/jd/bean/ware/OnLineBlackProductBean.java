package com.xiu.jd.bean.ware;

import java.io.Serializable;

/**
 * 上下架黑名单商品
 * 该商品不会对接走秀的上下架状态到京东,
 * 价格和库存还是会 对接
 * @author liweibiao
 *
 */
public class OnLineBlackProductBean implements Serializable {
	private static final long serialVersionUID = -3947285658625205935L;
	
	private long id;//主键,自增长
	
    private String xiuCode; //	走秀码
    
    private Integer isButtJoint=1; //上下架对接状态,默认为1,未对接,2:已对接(走秀商品上下架是否与京东对接)
    
    private Integer  confirmStatus=1; //确认状态:1:已确认,2:待确认,3:已过期(第一次导入确认状态为:1已确认是对未对接的确认)
    
    private String  confirmDate; //确认时间
    
    private String  createDate; //商品创建时间
    
    private String  importUserName; //导入的用户名称
    
    private Integer   isDelete=1; //默认是1,没被删除,0:被删除了
    
    private String  userName; //记录被删除的用户名
    
    private String confirmUserName; //最后被确认的用户名
    
    private String title;//商品标题
    
    private Integer onLineStatus ;//商品销售状态：0:未上架,1:在售,2:下架
    
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getXiuCode() {
		return xiuCode;
	}
	public void setXiuCode(String xiuCode) {
		this.xiuCode = xiuCode;
	}
	
	public Integer getIsButtJoint() {
		return isButtJoint;
	}
	public void setIsButtJoint(Integer isButtJoint) {
		this.isButtJoint = isButtJoint;
	}
	public Integer getConfirmStatus() {
		return confirmStatus;
	}
	public void setConfirmStatus(Integer confirmStatus) {
		this.confirmStatus = confirmStatus;
	}
	
	public String getConfirmDate() {
		return confirmDate;
	}
	public void setConfirmDate(String confirmDate) {
		this.confirmDate = confirmDate;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getImportUserName() {
		return importUserName;
	}
	public void setImportUserName(String importUserName) {
		this.importUserName = importUserName;
	}
	
	public Integer getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getOnLineStatus() {
		return onLineStatus;
	}
	public void setOnLineStatus(Integer onLineStatus) {
		this.onLineStatus = onLineStatus;
	}
	public String getConfirmUserName() {
		return confirmUserName;
	}
	public void setConfirmUserName(String confirmUserName) {
		this.confirmUserName = confirmUserName;
	}
	
    
    
    
}
