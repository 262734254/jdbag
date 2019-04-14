package com.xiu.jd.schedule.ware;

public enum HandleMsg {
	SYN_STOCK("stock","走秀商品sku库存同步到京东成功","走秀商品sku库存同步到京东失败","走秀渠道中心库存为:###,京东库存为:***,将京东的库存更新为:###"),
	SYN_PRICE("price","走秀商品价格同步到京东成功","走秀商品价格同步到京东失败","走秀渠道中心价格为:###, 京东价格为:***,将京东的价格更新为:###");
	
	
	
	private String name;
	private String syn_success;
	private String syn_fail;
	private String desc;
	
	private HandleMsg(String name, String syn_success, String syn_fail,
			String desc) {
		this.name = name;
		this.syn_success = syn_success;
		this.syn_fail = syn_fail;
		this.desc = desc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSyn_success() {
		return syn_success;
	}

	public void setSyn_success(String syn_success) {
		this.syn_success = syn_success;
	}

	public String getSyn_fail() {
		return syn_fail;
	}

	public void setSyn_fail(String syn_fail) {
		this.syn_fail = syn_fail;
	}
	
	public String getDesc(String zouxiu,String jd){
		String descxin = desc.replace("###", zouxiu);
		return descxin.replace("***", jd);
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}
