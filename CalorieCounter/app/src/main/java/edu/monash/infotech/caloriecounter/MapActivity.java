package edu.monash.infotech.caloriecounter;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;

import edu.monash.infotech.caloriecounter.Tools.PoiOverlay;


public class MapActivity extends AppCompatActivity {
    protected TextureMapView mMapView;
    protected BaiduMap mBaiduMap;
    protected double latitude = 31.322825;
    protected double longitude = 120.686198;
    protected PoiSearch poiSearch;
    protected LatLng ptCenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_map2);
        //地图初始化
        mMapView = (TextureMapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        //定位到东方之门经纬度
        ptCenter = new LatLng(latitude, longitude);
        mBaiduMap.addOverlay(new MarkerOptions().position(ptCenter)
                .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.icon_gcoding)));
        Toast.makeText(MapActivity.this, "Targeted to your location",
                Toast.LENGTH_LONG).show();
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(ptCenter));


        // 实例化PoiSearch对象
        poiSearch = PoiSearch.newInstance();
        // 设置检索监听器
        poiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                if (poiResult == null || poiResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
                    Toast.makeText(MapActivity.this, "Not find Result",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {
                    mBaiduMap.clear();
                    MyPoiOverlay poiOverlay = new MyPoiOverlay(mBaiduMap);
                    poiOverlay.setData(poiResult);//set POI data
                    mBaiduMap.setOnMarkerClickListener(poiOverlay);
                    //add total overlay to map
                    poiOverlay.addToMap();
                    poiOverlay.zoomToSpan();

                }

            }
            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
                if (poiDetailResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    System.out.println("执行第一步");
                    Toast.makeText(MapActivity.this, "Sorry,not find result",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MapActivity.this, poiDetailResult.getName() + ":" + poiDetailResult.getAddress(), Toast.LENGTH_LONG)
                            .show();
                    System.out.println("执行第二部");
                }
            }
        });
    }

    public void nearBySearch(View v){
        PoiNearbySearchOption nearbySearchOption = new PoiNearbySearchOption();
        nearbySearchOption.location(ptCenter);
        nearbySearchOption.keyword("公园");
        nearbySearchOption.radius(5000);
        nearbySearchOption.pageNum(1);
        poiSearch.searchNearby(nearbySearchOption);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }


    class MyPoiOverlay extends PoiOverlay {
        public MyPoiOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }
        @Override
        public boolean onPoiClick(int index) {
            super.onPoiClick(index);
            PoiInfo poi = getPoiResult().getAllPoi().get(index);
            // if (poi.hasCaterDetails) {
            poiSearch.searchPoiDetail((new PoiDetailSearchOption())
                    .poiUid(poi.uid));
            // }
            return true;
        }
    }

}
