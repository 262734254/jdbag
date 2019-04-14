// JavaScript Document
/* 参数定义*/
//ID:控件ID编号
//DivName:Div名称
//ClickDivName:鼠标触发控件名称
//AUrl:超链接的Href值
//ATitle:超链接的文本描述
//AValue:超链接的文本值
//Number:需要翻转的标签数值
//StyleName:选中前的样式
//ClickStyleName:选中后的样式
//onmouseover:滑过
//onclick:点击
function ShowSlippage(ID,DivName,ClickName,AUrl,ATitle,AValue,Num,DefaultStyleName,ClickStyleName)
{
	if(Num>0 && Num!="" && Num!=null)
	{
		for(i=1;i<=Num;i++)
		{
			if(i!=ID)
			{
				document.getElementById(DivName+i).style.display ='none';//隐藏不显示的DIv
				document.getElementById(ClickName+i).className=DefaultStyleName;//添加选中前的样式
			}
			else
			{
				document.getElementById(DivName+i).style.display ='';//隐藏不显示的DIv
				document.getElementById(ClickName+i).className=ClickStyleName;//添加选中后的样式
			}
		}
		if(AUrl!="" && AUrl!=null)//判断是否需要对超链接赋值
		{
			document.getElementById('A'+DivName).setAttribute('href',AUrl);//添加链接地址
			if(AValue!="" && AValue!=null)//判断是否需要设置超链接的文本值
			{
				document.getElementById('A'+DivName).innerText = AValue;//设置超链接的文本值
			}
			if(ATitle!="" && ATitle!=null)//判断是否需要设置超链接的标题说明
			{
				document.getElementById('A'+DivName).title=ATitle; //设置超链接的标题说明
			}
		}
	}
}