
  var  g_googlemap = null;
  var  g_polyline = null;
  var  g_zoom = 2;
 // var  g_zoom = 10;
  var  g_bPlay = false;
  var  g_mapobjs = new Array();
  
  
  //////////////////////////////////////////////////////////
  // 初始化地图
  function initialize() {
	
    g_googlemap = new google.maps.Map(document.getElementById("div_map"),			
			{
				center								: new google.maps.LatLng( 35.0000,101.8 ),				
				mapTypeControl				: true,
				mapTypeControlOptions	: {style: google.maps.MapTypeControlStyle.DEFAULT, position:google.maps.ControlPosition.TOP},
				scaleControl					: true,
				scaleControlOptions	: {style: google.maps.ScaleControlStyle.DEFAULT,   position:google.maps.ControlPosition.BOTTOM},
				navigationControl			: true,
				navigationControlOptions :{style: google.maps.NavigationControlStyle.DEFAULT,   position: google.maps.ControlPosition.LEFT },
				streetViewControl			:true,
				draggableCursor				: 'crosshair',
				mapTypeId							: google.maps.MapTypeId.ROADMAP,
				zoom									: g_zoom
			}
		);
    
    var polyOptions = {      
							  		strokeColor: 'red',      
							  		strokeOpacity: 0.8,							  		      
							  		strokeWeight: 4    }
					  		
  	g_polyline = new google.maps.Polyline(polyOptions);    
  	g_polyline.setMap(g_googlemap);
			  
//		mapDisplayGPSObj("({uiid:'52901880',lng:114.298105,lat:30.624508,speed:0,direction:169,alState:0,name:'JC359',description:'<div align=left><font size=1> <b>JC359</b><br><br>时间: 2012-10-25 00:15:19<br>速度(km/h):0<br>纬度经度 30.624508 114.298105<br>终端序列号:52901880<br>电话号码:13871142384<br>名称:           <br>电话号码:<br>运行行驶时间(分):0<br>地址:中国湖北省武汉市江岸区二七侧路1号251米 </font></div>',showInfo:false})");	  		
  }

	//var  time = setTimeout(test, 2000);
 // function test(){
  	
  //	clearInterval(time);
  	//mapDisplayGPSObj("({uiid:'52901880',lng:114.298105,lat:30.624508,speed:0,direction:169,alState:0,name:'JC359',description:'<div align=left><font size=1> <b>JC359</b><br><br>时间: 2012-10-25 00:15:19<br>速度(km/h):0<br>纬度经度 30.624508 114.298105<br>终端序列号:52901880<br>电话号码:13871142384<br>名称:           <br>电话号码:<br>运行行驶时间(分):0<br>地址:中国湖北省武汉市江岸区二七侧路1号251米 </font></div>',showInfo:false})");	  		
  	//history_play("({uiid:'52901880',lng:114.298105,lat:30.624508,speed:0,direction:169,alState:0,name:'JC359',description:'<div align=left><font size=1> <b>JC359</b><br><br>时间: 2012-10-25 00:15:19<br>速度(km/h):0<br>纬度经度 30.624508 114.298105<br>终端序列号:52901880<br>电话号码:13871142384<br>名称:           <br>电话号码:<br>运行行驶时间(分):0<br>地址:中国湖北省武汉市江岸区二七侧路1号251米 </font></div>',showInfo:false})");
	 // history_play("({uiid:'52901880',lng:114.298105,lat:30.634508,speed:0,direction:169,alState:0,name:'JC359',description:'<div align=left><font size=1> <b>JC359</b><br><br>时间: 2012-10-25 00:15:19<br>速度(km/h):0<br>纬度经度 30.624508 114.298105<br>终端序列号:52901880<br>电话号码:13871142384<br>名称:           <br>电话号码:<br>运行行驶时间(分):0<br>地址:中国湖北省武汉市江岸区二七侧路1号251米 </font></div>',showInfo:false})");
	 // history_play("({uiid:'52901880',lng:114.298105,lat:30.644508,speed:0,direction:169,alState:0,name:'JC359',description:'<div align=left><font size=1> <b>JC359</b><br><br>时间: 2012-10-25 00:15:19<br>速度(km/h):0<br>纬度经度 30.624508 114.298105<br>终端序列号:52901880<br>电话号码:13871142384<br>名称:           <br>电话号码:<br>运行行驶时间(分):0<br>地址:中国湖北省武汉市江岸区二七侧路1号251米 </font></div>',showInfo:false})");
	 // history_play("({uiid:'52901880',lng:114.298105,lat:30.654508,speed:0,direction:169,alState:0,name:'JC359',description:'<div align=left><font size=1> <b>JC359</b><br><br>时间: 2012-10-25 00:15:19<br>速度(km/h):0<br>纬度经度 30.624508 114.298105<br>终端序列号:52901880<br>电话号码:13871142384<br>名称:           <br>电话号码:<br>运行行驶时间(分):0<br>地址:中国湖北省武汉市江岸区二七侧路1号251米 </font></div>',showInfo:false})");
	  //history_play("({uiid:'52901880',lng:114.298105,lat:30.664508,speed:0,direction:169,alState:0,name:'JC359',description:'<div align=left><font size=1> <b>JC359</b><br><br>时间: 2012-10-25 00:15:19<br>速度(km/h):0<br>纬度经度 30.624508 114.298105<br>终端序列号:52901880<br>电话号码:13871142384<br>名称:           <br>电话号码:<br>运行行驶时间(分):0<br>地址:中国湖北省武汉市江岸区二七侧路1号251米 </font></div>',showInfo:false})");
//  }
  ///////////////////////////////////////////////////////////
  // 
  function  MapZoomin(){

		var   nZoom = g_googlemap.getZoom();

		if( nZoom > 1 ){
			nZoom=nZoom-1;
		}
		g_googlemap.setZoom( nZoom);
  }
  //////////////////////////////////////////////////////////
  //
  function SetZoomin( nZoom ){
	  
	  g_googlemap.setZoom( nZoom );
  }
  //////////////////////////////////////////////////////////
  // 
  function  MapZoomOut(){

	  var   nZoom = g_googlemap.getZoom();

	  g_googlemap.setZoom( nZoom+1);
  } 
  //////////////////////////////////////////////////////////
  //
  function centerAt(latitude, longitude){
  	
      myLatlng = new google.maps.LatLng(latitude,longitude);
      g_googlemap.panTo(myLatlng);
  }
  //*********************************************
  //
  function history_play( mapobj ){
  	
  	var path;
  	var obj = getObjectFromStr(mapobj);
  	if( g_bPlay == false ){ 
  			g_bPlay = true;
  			g_polyline.setMap(g_googlemap);
  	}
  	var mylatlng = new google.maps.LatLng(obj.lat,obj.lng);
  	if( IsContainPoint(obj) == false ){
    	g_googlemap.setCenter( mylatlng );
    }
  	path = g_polyline.getPath();
  	path.push(mylatlng);
		obj.color = mapGetColor(obj);	
    addTriAngle(obj);
  }    
	///////////
	///    *
	///  *   *
	//  *  *  *
	///*       * 
  function addTriAngle( obj ){
  	
		var		x = parseFloat(obj.lng);
		var		y = parseFloat(obj.lat);
		var		nLen;		
		var		lat1;
		var		lat2;	
		var 	nSize = 1024;			
		var 	bounds = g_googlemap.getBounds();
			
		lat1 = bounds.getSouthWest().lat();
		lat2 = bounds.getNorthEast().lat();	
		lat2 -= lat1;		
		
		nLen = 80 * lat2/nSize;
		direction = 270-obj.direction;
		var	points = new Array();	
		points.push( new mapPoint( x,y ) );
		points.push( new mapPoint( GetX1Point( x, direction, nLen), GetY1Point( y, direction, nLen) ) );
	  points.push( new mapPoint( GetX2Point( x, direction, nLen), GetY2Point( y, direction, nLen) ) );
		points.push( new mapPoint( GetX3Point( x, direction, nLen), GetY3Point( y, direction, nLen) ) );
		
		obj.points = points;
		addPolygon( obj );
		return true;	
	}
	function mapPoint(lng,lat){
		this.lng = lng;
		this.lat = lat;	
	}
	function addPolygon2( obj ){		
		
		var		self = this;	
		var		latlngs = obj.datastr.split('__');
		var		latlng;
		var		lat1,lat2,lng1,lng2;
		obj.points = new Array();
		
		for( var i = 0; i < latlngs.length; i++ ){		
			latlng = latlngs[i].split(',');		
			obj.points.push( {lng:parseFloat(latlng[0]), lat:parseFloat(latlng[1])} );		
		}
		lat1 = obj.points[0].lat;
		lng1 = obj.points[0].lng;
		
		var		j = Math.round( latlngs.length / 2 );
		
		lat2 = obj.points[j].lat;
		lng2 = obj.points[j].lng;
		
		obj.lat = lat1+(lat2-lat1)/2;
		obj.lng = lng1+(lng2-lng1)/2;	
		return self.addPolygon( obj );		 		
	}	
	
	function addPolygon( obj ){			
		
		var		self = this;
		if( obj.points.length < 3 ){
			return;		
		}	
		if( typeof(obj.color) == "undefined" ){
			obj.color = "#0000FF";			
		}
		if( typeof(obj.lat) == "undefined" ){
			obj.lat = obj.points[0].lat;			
		}
		if( typeof(obj.lng) == "undefined" ){
			obj.lng = obj.points[0].lng;			
		}
		var mapobj= getmapobj( obj.uiid );
		deletemarker( mapobj );		
				
		var			points = new Array();
		for( var i = 0; i < obj.points.length; i++ ){
			points.push( new google.maps.LatLng( obj.points[i].lat, obj.points[i].lng ) );  			
		}	 	
		var polyobj = new google.maps.Polygon({
			paths				:	points, 
			strokeColor	: obj.color, 
			strokeOpacity:0.8,
			fillColor		: obj.color,
			fillOpacity : 0.4 
		});
		polyobj.setMap( g_googlemap );		
		mapobj.obj.push( polyobj );	
				
		var	markobj = new GUMarker( obj );	
		markobj.setMap( g_googlemap );					
		mapobj.obj.push( markobj );
		
		addObject( mapobj );		
		return polyobj;		
	}
	
  function IsContainPoint( obj ){
  	
  	var bounds = g_googlemap.getBounds();
  	return bounds.contains( new google.maps.LatLng(obj.lat,obj.lng) );
  }
  //*********************************************
  //
  function history_stop(){
  		  		
  		var    arraypath = g_polyline.getPath();
  		while(1){
  			  var obj = arraypath.pop();
  				if( obj == undefined ){
  		   		 break;
  	  		}
  		}
  		g_bPlay = false;
  		g_polyline.setMap(null);
  }  
  //*********************************************
  //
  function getObjectFromStr( str ){
	
		if( typeof(str) === 'object' ){
			return str;
		}
		else if(typeof(str) === 'string'){
			var obj = eval('('+str+')');
			return obj;
		}
		else{
			return {};
		}	
	}
  function  mapDisplayGPSObj( name ){
    	
    var  obj = getObjectFromStr(name);    
    var  mapobj =  getmapobj(obj.uiid);    
		deletemarker( mapobj );	
		
		obj.icon = mapGetIconUrl(obj);
		obj.rotate = obj.direction;
		obj.color = mapGetColor(obj);	
		
		var	markobj = new GUMarker( obj );
		markobj.setMap( g_googlemap );
		mapobj.obj[0]=markobj;
		addObject( mapobj );		
		var iw = new google.maps.InfoWindow({content: obj.description, disableAutoPan:true, pixelOffset: new google.maps.Size(0,0)});
	
		google.maps.event.addListener(
				 markobj, 
				"click", 
				function() {
		      iw.open( g_googlemap, markobj);
		    }
    );
  }    
	function mapGetColor( obj ){	
		var		color ="";
		if( obj.alState > 0 ){
			color = "#ff0000";		
		}
		else if( obj.speed > 10 ){
			color = "#0000ff";				
		}
		else{
			color = "#000000";		
		}
		return color;
	}
  //**********************************************
	function mapGetIconUrl(obj){
		
		var text='';
		var textIcon='';
		var icon=getIconByType(0);
		
		if( obj.alState > 0  ){
			icon = icon.alarm;
		}
		else if( obj.speed > 10 ){
			icon = icon.run;
		}
		else{
			icon = icon.stop;
		}
		text += 'image/car2/'+icon;
		return text;
	}
	var	IconInfo =[
			{	id : 0,		mini : '0_mini.gif',	run : '0_run.gif',	stop : '0_stop.gif',	alarm : '0_alarm.gif',	offline : '0_lost.gif'	}
	];
	function	getIconByType( type ){
		if( type >= IconInfo.length ){
			type = IconInfo.length-1;
		}
		return IconInfo[type];	
	}
	function GUMarker(obj) {
	  this.latlng_ = new google.maps.LatLng( obj.lat, obj.lng),
	  this.obj = obj;	
	}
  GUMarker.prototype = new google.maps.OverlayView();
  GUMarker.prototype.draw = function() {
		
		var self = this;		
		 // Check if the div has been created.
		var div = self.div_;
		if (!div) {
		  // Create a overlay text DIV
		  div = self.div_ = document.createElement('DIV');
		  // Create the DIV representing our CustomMarker
		  div.style.border = "none";
		  div.style.position = "absolute";
		  div.style.paddingLeft = "0px";
		  div.style.cursor = 'pointer';      
		  // Then add the overlay to the DOM
		  var panes = this.getPanes();
		  panes.overlayImage.appendChild(div);
		  google.maps.event.addDomListener(div, "click", function(event) {
		    google.maps.event.trigger(self, "click");
		  });
		}
		if( self.obj.rotate == null ){
			self.obj.rotate = 0;
		}
		if( self.obj.icon != null ){
			var text="<span>"+getRotateImgHtml({URL:self.obj.icon, angle:self.obj.rotate})+"</span>";
		}
		else{
			  var text= "";
		}
		text+="<div style='border:1px solid #aaaaaa;background-color:#ffffd7;color:"+self.obj.color+";font:normal 10px verdana;white-space:nowrap;'>"
		+ self.obj.name
		+ "</div>";					
		div.innerHTML=text;
		
		var point = this.getProjection().fromLatLngToDivPixel(self.latlng_);
		if (point) {
		  div.style.left = point.x + 'px';
		  div.style.top = point.y + 'px';
			}
	};
	GUMarker.prototype.remove = function() {
	  // Check if the overlay was on the map and needs to be removed.
	  if (this.div_) {
	    this.div_.parentNode.removeChild(this.div_);
	    this.div_ = null;
	  }
	};
	GUMarker.prototype.getPosition = function() {
	  return this.latlng_;
	};
	
	function getRotateImgHtml(cfg){
	
	
		var config={
			URL		: '',
			angle	: 0,
			round	: false
		};
		config.URL = cfg.URL;
		config.angle = cfg.angle;
		config.round = cfg.round;
	//	Ext.apply(config,cfg);
		var strHtml;
		var angle=config.angle;
		if(!angle||typeof(angle)!='number'){
			angle=0;
		}
		if(angle>=0){
			var rotation=Math.PI*angle/180;
		}else{
			var rotation=Math.PI*(360+angle)/180;
		}
		var costheta=Math.cos(rotation);
		var sintheta=Math.sin(rotation);
		if (document.all){ 
			strHtml='<div>'
			+'<img style="filter:progid:'
			+'DXImageTransform.Microsoft.Matrix('
			+'M11='+costheta+','
			+'M12='+(-sintheta)+','
			+'M21='+sintheta+','
			+'M22='+costheta+','
			+'SizingMethod=\'auto expand\');" '
			+'src="'+config.URL+'" '
			+'/>'
			+'</div>';
		}
		else{
			var id=getRandomID();
			strHtml='<div>'
			+'<img id="'+id+'" src="'+config.URL+'" />'
			+'</div>';
			setTimeout(function(){
				var p=document.getElementById(id);
				if(!p){return;}
				p.angle=angle;
				var canvas=document.createElement('canvas');
				if(!p.oImage){
					canvas.oImage=new Image();
					canvas.oImage.src=p.src;
				}else{
					canvas.oImage=p.oImage;
				}
				canvas.style.width=canvas.width=Math.abs(costheta*canvas.oImage.width)+Math.abs(sintheta*canvas.oImage.height);
				canvas.style.height=canvas.height=Math.abs(costheta*canvas.oImage.height)+Math.abs(sintheta*canvas.oImage.width);
				var context=canvas.getContext('2d');
				context.save();
				if(rotation<=Math.PI/2){
					context.translate(sintheta*canvas.oImage.height,0);
				}
				else if(rotation<=Math.PI){
					context.translate(canvas.width,-costheta*canvas.oImage.height);
				}
				else if(rotation<=1.5*Math.PI){
					context.translate(-costheta*canvas.oImage.width,canvas.height);
				}
				else{
					context.translate(0,-sintheta*canvas.oImage.width);
				}
				context.rotate(rotation);
				context.drawImage(canvas.oImage,0,0,canvas.oImage.width,canvas.oImage.height);
				context.restore();
				canvas.id=p.id;
				canvas.angle=p.angle;
				p.parentNode.replaceChild(canvas,p);
			},50);
		}
		return strHtml;
	}
	getRandomID =(function(){
		var a=0;function b(){return'_ID_MT_'+(a++);}return b;
	})();
	//**********************************************
  function  addObject( mapobj){
  	g_mapobjs.push(mapobj);
  }
	function  newobject( deuid ){	
			
			this.deuid = deuid;
			this.obj = new Array();
			return this;
	}
		
	function  getmapobj( deuid ){
			
			var obj = null;
			
			for( var nCnt = 0; nCnt < g_mapobjs.length; nCnt++ ){
				if( g_mapobjs[nCnt].deuid == deuid){			
					obj=g_mapobjs[nCnt];
					g_mapobjs.splice(nCnt, 1);	
					break;
				}
			}	
			if( obj == null ){			
				obj = new newobject( deuid );
			}		
			return obj;
	}
	function  deletemarker(mapobj){
		
		var			obj;
				
		while( 1 ) {
			obj = mapobj.obj.pop();
			if( obj == undefined ){
				break;	
			}
			else{
				obj.setMap(null );
			}			
		}
	}
  function  mapDeleteMarker( deuid ){
  	
  	deletemarker( getmapobj(deuid) );
  }  
  function  mapZoomAll(){
  	
  	 var myLatlng = new google.maps.LatLng(35.0000,101.8);
  	 g_googlemap.setCenter(myLatlng);
  	 g_googlemap.setZoom(g_zoom);
  }
	function GetIconPath( nDriection, nSpeed, nAlarmState ){
			
			var 	nCnt;	
			var     strResult;
			var		nSpeedTmp;
			var		nAlarmTmp;
			var 	nBmp=1;
				
			nSpeedTmp = Number(nSpeed);
			nAlarmTmp = Number(nAlarmState);	
			nCnt = Number(nDriection);
			if ( (nCnt >= 0) && (nCnt <= 22) ){
				nBmp = 1;
			}
			else if ( (nCnt >= 337) && (nCnt <= 359)){
				nBmp = 1;
			}
			else if ( (nCnt > 22) && (nCnt <= 67)){
				nBmp = 2;
			}
			else if ( (nCnt > 67) && (nCnt <= 112)){
				nBmp = 3;
			}
			else if ( (nCnt > 112) && (nCnt <= 157)){
				nBmp = 4;
			}
			else if ( (nCnt > 157) && (nCnt <= 202)){
				nBmp = 5;
			}
			else if ( (nCnt > 202) && (nCnt <= 247)){
				nBmp = 6;
			}
			else if ( (nCnt > 247) && (nCnt <= 292)){
				nBmp = 7;
			}
			else if ( (nCnt > 292) && (nCnt <= 337)){
				nBmp = 8;
			}	
			if( nAlarmTmp > 0 ){
				strResult = "car/Car101"+nBmp+".gif";  
			}
			else{		
				if( nSpeedTmp > 0 ){
					strResult = "car/Car10"+"2"+nBmp+".gif";  
				}	
				else{
					strResult = "car/Car10"+"3"+nBmp+".gif";  
				}
			}	
			return strResult;
	}
	/////////////////////////////////////////////////////////////////////////
//					b*(x1,y1)		
//  
//   a*(x,y)	c*(x2,y2)
//
//					d*(x3,y3)

// gps 0 --> 270  ===> ang = 270-gps
// gps 90 --> 180  ==> ang = 270-gps 		
// gps 180 --> 90  ==> ang = 270-gps
// gps 270 --> 0  ==> ang = 270-gps
// gps 359 --> -89  ==> ang = 270-gps

//////////////////////////////////////////////////////////////////////////
//X1 = X + Math.cos(30 + Direction) * nLen
function GetX1Point(x, Direction, nLen ){
	
	var  result;	
	
	if ( (Direction + 30) >= 360 ){			
		Direction = (Direction + 30) - 360;
	}
	else{
		Direction += 30;
	}
	result = x + Math.cos(Direction*Math.PI/180)*nLen;
	
	return result;
}
//////////////////////////////////////////////////////////////////////////
//Y1 = Y + Math.sin(30 + sDirection) * nLenght
function GetY1Point(y, Direction, nLen){
	var  result;	
	
	if ( (Direction + 30) >= 360 ){			
		Direction = (Direction + 30) - 360;
	}
	else{
		Direction += 30;
	}
	result = y + Math.sin(Direction*Math.PI/180)*nLen;
	
	return result;
}
//////////////////////////////////////////////////////////////////////////
//X2 = X + ( (nLenght / 2) / Math.cos(60) ) * Math.cos(sDirection)
function GetX2Point(x, Direction, nLen){
	
	var  result = 0.0;
	
	result = x + ( nLen / (2* Math.sin(60 * Math.PI / 180.0)) ) * Math.cos(Direction * Math.PI / 180.0);
	
	return result;
}
//////////////////////////////////////////////////////////////////////////
//y2 = y + ( (nLenght / 2) / Math.cos(60) ) * Math.sin(sDirection)
function GetY2Point(y, Direction, nLen){
	
	var  result = 0.0;	
	result = y + ( nLen / (2 * Math.sin(60 * Math.PI / 180.0)) ) * Math.sin(Direction * Math.PI / 180.0);
	return result;
}
//////////////////////////////////////////////////////////////////////////
//X3 = X + Math.cos(-30 + sDirection) * nLenght
function GetX3Point(x, Direction, nLen ){
	
	var  result;	
	
	if ( (Direction - 30) < 0 ){			
		Direction = 360+ (Direction - 30);
	}
	else{
		Direction -= 30;
	}
	result = x + Math.cos(Direction*Math.PI/180)*nLen;	
	return result;
	
}
//////////////////////////////////////////////////////////////////////////
//y3 = Y + Math.sin(-30 + sDirection) * nLenght
function GetY3Point(y, Direction, nLen ){
	
	var  result;	
	
	if ( (Direction - 30) < 0 ){			
		Direction = 360+ (Direction - 30);
	}
	else{
		Direction -= 30;
	}
	result = y + Math.sin(Direction*Math.PI/180)*nLen;
	
	return result;
}