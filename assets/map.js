
  var  g_googlemap = null;
  //////////////////////////////////////////////////////////
  // 初始化地图
  function initialize() {
	  
    var latitude = 0;
    var longitude = 0;
    if (window.android){
      latitude = window.android.getLatitude();
      longitude = window.android.getLongitude();
    }    
    var myLatlng = new google.maps.LatLng(latitude,longitude);
    var myOptions = {
      //zoom: 3,
      zoom: 10,		
      center: myLatlng,
      mapTypeId: google.maps.MapTypeId.ROADMAP
    }
    g_googlemap = new google.maps.Map(document.getElementById("div_map"), myOptions);

  }
  ///////////////////////////////////////////////////////////
  // 地图缩小
  function  MapZoomin(){

		var   nZoom = g_googlemap.getZoom();

		if( nZoom > 1 ){
			nZoom=nZoom-1;
		}
		g_googlemap.setZoom( nZoom);
  }
  //////////////////////////////////////////////////////////
  // 地图放大
  function  MapZoomOut(){

	  var   nZoom = g_googlemap.getZoom();

	  g_googlemap.setZoom( nZoom+1);
  } 
  //////////////////////////////////////////////////////////
  //设置中心点
  function centerAt(latitude, longitude){
	  
      myLatlng = new google.maps.LatLng(latitude,longitude);
      g_googlemap.panTo(myLatlng);
  }
  ///////////////////////////////////////////////////////////
  //
  function addMarker( strDEUID, strLicense, lat, lng ){

	  var oLatlng =  new google.maps.LatLng(lat,lng);
	  g_googlemap.panTo(oLatlng);
	  
	  var marker = new google.maps.Marker({
		           map: g_googlemap,
		           position: oLatlng,
		           icon:"car.png",
		           title:strLicense
		           });
	  marker.setMap(g_googlemap);
      var infowindow = new google.maps.InfoWindow();
      infowindow.setContent('<b>test</b>');
      google.maps.event.addListener(marker, 'click', function() {            
            infowindow.open(g_googlemap, marker);
      });
  }
  
  