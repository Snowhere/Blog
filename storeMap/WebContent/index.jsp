<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Echarts</title>
    <style type="text/css">
   
    </style>
</head>

<body>
    <!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
    <div style="width:100%;min-width:1200px;">
        <div id="main" style="float:left;width: 33%;height:300px;"></div>
        <div id="second" style="float:left;width: 33%;height:300px;"></div>
        <div id="third" style="float:left;width: 33%;height:300px;"></div>
    </div>
    <div style="width:1200px;">
    	<!-- 为 地图准备一个具备大小（宽高）的 DOM -->
    	<div id="container" style="float:left;width: 700px;height: 400px;border:1px solid blue"></div>
    	<!-- 文字信息 -->
    	<div style="float:left;width:400px;margin-left:50px">
    		<!-- 热点地区 -->
    		<h3 id="hotTitle"></h3><div id="hotMessage"></div>
    		<!-- poi详情 -->
    		<!-- <h3>POI详情</h3><div id="poiList"></div> -->
    	</div>
    </div>
    <!-- 代码逻辑 -->
    <script src="jquery-1.11.3.min.js"></script>
    <script src="echarts.min.js"></script>
    <script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&key=c136e1f285496b912093e5c5d1933055"></script>
    <script type="text/javascript">
    /**
     *  地图部分
     */
    //缓存标记列表
    var markers = [];
    var infowindow;
    var map = new AMap.Map('container', {
        zoom: 10,
        center: [116.39, 39.9]
    });
    //自定义点
    var icon = new AMap.Icon({
        image: 'http://vdata.amap.com/icons/b18/1/2.png', //24px*24px
        //icon可缺省，缺省时为默认的蓝色水滴图标，
        size: new AMap.Size(24, 24)
    });
    //标记点和移除
    var position = new AMap.Marker({
        icon: icon,
        position: [116.480983, 39.989628],
        map: map
    });
    //marker.setMap();

    function createMarker(lng, lat, content) {
        var marker = new AMap.Marker({
            position: [lng, lat],
            map: map
        });
        var mouseover = AMap.event.addListener(marker, 'mouseover', function() {
            //信息窗体
            infowindow = new AMap.InfoWindow({
                content: content,
                offset: new AMap.Pixel(0, -30),
                size: new AMap.Size(230, 0)
            })
            infowindow.open(map, marker.getPosition())
        });
        var mouseout = AMap.event.addListener(marker, 'mouseout', function() {
            //信息窗体
        	infowindow.close();
        });
        return marker;
    }



    //原点绑定点击
    var _onClick = function(e) {
    	//清除原有点
        if (infowindow) {
            infowindow.close();
        }
        for (var i = 0; i < markers.length; i++) {
            markers[i].setMap();
        }
        markers = [];
        //清除饼状图
        myChart.clear();
        second.clear();
        third.clear();
        //
        position.setPosition(e.lnglat);
        map.setCenter(e.lnglat);
        map.setZoom(14);
        //载入数据
        getAround(e.lnglat.lng, e.lnglat.lat, 1000);
    }
    var clickListener = AMap.event.addListener(map, "click", _onClick); //绑定事件，返回监听对象
    //AMap.event.removeListener(clickListener); //移除事件，以绑定时返回的对象作为参数

    /**
     *  Echart部分
     */
    //基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));
    var second = echarts.init(document.getElementById('second'));
    var third = echarts.init(document.getElementById('third'));

    function getAround(lng, lat, radius) {
        myChart.showLoading();
        $.getJSON('${pageContext.request.contextPath}/getAround?lng=' + lng + '&lat=' + lat + '&radius=' + radius, function(data) {
            myChart.hideLoading();
            myChart.setOption({
                tooltip: {},
                title: {
                    text: '周边(' + data.value + ')'
                },
                series: [{
                    type: 'pie',
                    radius: '55%',
                    data: data.list
                }]
            });
        });
        $.getJSON('${pageContext.request.contextPath}/getHotPlace?lng=' + lng + '&lat=' + lat + '&radius=' + radius, function(data) {
            var text='';
            $('#hotTitle').html('有'+data.length+'几个热点地区');
            for (var i = 0;i<data.length; i++) {
                text+='<a target="_blank" href="http://i.amap.com/detail/'+data[i].poiid+'">'+data[i].name+'</a><br>';
            }
            $('#hotMessage').html(text);
        });
    }

    myChart.on('click', function(params) {
        //console.log(params.data);
        second.setOption({
            tooltip: {},
            title: {
                text: params.data.name + '(' + params.data.value + ')'
            },
            series: [{
                type: 'pie',
                radius: '55%',
                data: params.data.list
            }]
        })
    });
    second.on('click', function(params) {

        //console.log(params.data);
        third.setOption({
            tooltip: {},
            title: {
                text: params.data.name + '(' + params.data.value + ')'
            },
            series: [{
                type: 'pie',
                radius: '55%',
                data: params.data.list
            }]
        })
    });
    third.on('click', function(params) {
        //console.log(params.data);
       
       
       
        for (var j = 0; j < params.data.list.length; j++) {
            markers.push(createMarker(params.data.list[j].location.split(',')[0], params.data.list[j].location.split(',')[1], params.data.list[j].name));
        }
    });
    </script>
</body>

</html>