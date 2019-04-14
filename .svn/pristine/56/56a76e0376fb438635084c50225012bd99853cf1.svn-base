// JavaScript Document
// 加载xml文档




//runxml
window.onload = init;


//xmlDocument
var xmlDocAddress = null;

//province element
var provinceA;
//city element
var cityA;


function init(){
	provinceA = document.getElementById('province');
	cityA = document.getElementById('city');
	ct = document.getElementById('area');
	provinceA.onchange = getCity;
	cityA.onchange = getCounty;
	initProvince();
}





function loadXML(xmlFile){
	var xmlDocAddress;
	if(window.ActiveXObject){//for ie
		xmlDocAddress = new ActiveXObject('Microsoft.XMLDOM');
	}
	else if (document.implementation&&document.implementation.createDocument){//for moz
		xmlDocAddress = document.implementation.createDocument('', '', null);
	}
	else{
		alert('xml read ERROR!');
		return null;
	}
	xmlDocAddress.async = false;
	xmlDocAddress.preserveWhiteSpace = false;
	xmlDocAddress.load(xmlFile);
	return xmlDocAddress;
}



function initProvince(){
	if(xmlDocAddress == null){
		xmlDocAddress = loadXML('template/javascript/addAddress/GBT2260-1999.xml');	
	}
	
	var provinces = xmlDocAddress.getElementsByTagName("province");
	
	//alert(provinces.length);
	
	
	while(provinceA.options.length > 1){
		provinceA.removeChild(provinceA.options.item(1));
	}

	for(var i = 0; i<provinces.length; i++){
		var oOption = document.createElement("option");
        //oOption.innerHTML = provinces[i].getAttribute('name');
		oOption.appendChild(document.createTextNode(provinces[i].getAttribute('name')));
        oOption.value = provinces[i].getAttribute('name');
        provinceA.appendChild(oOption);	
	}
}



function getCity(){

	while(cityA.options.length > 1){
		cityA.removeChild(cityA.options.item(1));	
	}
	
	while(ct.options.length > 1){
		ct.removeChild(ct.options.item(1));	
	}
	
	var name = provinceA.options[provinceA.selectedIndex].value;
	
	//ie work! but moz fail!
	//var pro = xmlDocAddress.selectSingleNode("//province[@name='"+name+"']");//xpath
	
	
	//ugly method but can work!
	var pro = null;
	var provinces = xmlDocAddress.getElementsByTagName("province");
	for(var k = 0; k < provinces.length; k++){
		if(provinces[k].getAttribute('name') == name){
			pro = provinces[k];
			break;
		}
	}
	
	if(pro!=null){
		var citys = pro.getElementsByTagName("city");
		if(citys != null){
			for(var i = 0; i<citys.length; i++){
				var oOption = document.createElement("option");
				//oOption.innerHTML = citys[i].getAttribute('name');
				oOption.appendChild(document.createTextNode(citys[i].getAttribute('name')));
				oOption.value = citys[i].getAttribute('name');
				cityA.appendChild(oOption);	
			}
		}
	}
}


function getCounty(){
	while(ct.options.length > 1){
		ct.removeChild(ct.options.item(1));	
	}
	var name = cityA.options[cityA.selectedIndex].value;
	
	//ie work! but moz fail!
	//var pro = xmlDocAddress.selectSingleNode("//province[@name='"+name+"']");//xpath
	
	
	//ugly method but can work!
	var pro = null;
	var citys = xmlDocAddress.getElementsByTagName("city");
	for(var k = 0; k < citys.length; k++){
		if(citys[k].getAttribute('name') == name){
			pro = citys[k];
			break;
		}
	}
	
	if(pro!=null){
		var countys = pro.getElementsByTagName("county");
		if(countys != null){
			for(var i = 0; i<countys.length; i++){
				var oOption = document.createElement("option");
				//oOption.innerHTML = citys[i].getAttribute('name');
				oOption.appendChild(document.createTextNode(countys[i].getAttribute('name')));
				oOption.value = countys[i].getAttribute('name');
				ct.appendChild(oOption);	
			}
		}
	}
	
}
