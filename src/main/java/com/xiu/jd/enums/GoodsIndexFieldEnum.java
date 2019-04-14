package com.xiu.jd.enums;

public enum GoodsIndexFieldEnum {
	/**商品名称**/
	ITEM_NAME(){
		public  String fieldName(){
			return "itemName";
		}
	}, 
	/**商品图片url***/
	IMG_URL(){
		public  String fieldName(){
			return "imgUrl";
		}
		
	},
	/**走秀码***/
	PART_NUMBER(){
		public  String fieldName(){
			return "partNumber";
		}
		
	},
	// 走秀价(参加活动的)
	PRICE_FINAL(){
		public  String fieldName(){
			return "priceFinal";
		}
		
	}, 
	PRICE_XIU(){
		public  String fieldName(){
			return "priceXiu";
		}
		
	},
	
	/**市场价***/
	PRICE_MKT(){
		public  String fieldName(){
			return "priceMkt";
		}
	}, 
	/**商品ID**/
	ITEM_ID(){
		public  String fieldName(){
			return "itemId";
		}
	},
	
	/**供应商编号 1528(ebay) ferragamo(菲拉格慕) 空为:走秀**/
	PROVIDER_CODE(){
		public  String fieldName(){
			return "providerCode";
		}
	},
		
	/**上下架状态 1:上架，0:下架***/
	STATE_ON_SALE(){
		public  String fieldName(){
			return "stateOnsale";
		}
		
	};

	public abstract String fieldName();

}
