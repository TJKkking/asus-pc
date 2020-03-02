package baidumapsdk.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.OverlayManager;
import com.baidu.mapapi.overlayutil.WalkingRouteOverlay;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteLine;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;

public class RoutePlanTest extends Activity implements OnGetRoutePlanResultListener {
    // 浏览路线节点相关
    RouteLine route = null;
    MassTransitRouteLine massroute = null;
    OverlayManager routeOverlay = null;
    boolean useDefaultIcon = false;
    private TextView popupText = null; // 泡泡view

    MapView mMapView = null;    // 地图View
    BaiduMap mBaidumap = null;
    // 搜索相关
    RoutePlanSearch mSearch = null;

    WalkingRouteResult nowResultwalk = null;
    BikingRouteResult nowResultbike = null;
    TransitRouteResult nowResultransit = null;
    DrivingRouteResult nowResultdrive = null;
    MassTransitRouteResult nowResultmass = null;

    //目的地坐标
//    LatLng stLatlng;
//    LatLng enLatlng;

    int nowSearchType = -1; // 当前进行的检索，供判断浏览节点时结果使用。
    boolean hasShownDialogue = false;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.routrplantest2);
        CharSequence titleLable = "路线规划功能";
        setTitle(titleLable);

        // 初始化地图
        mMapView = (MapView) findViewById(R.id.maptest2);
        mBaidumap = mMapView.getMap();
//        mBtnPre = (Button) findViewById(R.id.pre);
//        mBtnNext = (Button) findViewById(R.id.next);
//        mBtnPre.setVisibility(View.INVISIBLE);
//        mBtnNext.setVisibility(View.INVISIBLE);

        // 初始化搜索模块，注册事件监听
        mSearch = RoutePlanSearch.newInstance();
        mSearch.setOnGetRoutePlanResultListener(this);
    }

    @Override
    protected void onStart(){
        super.onStart();
        route = null;
        mBaidumap.clear();

        LatLng stLatLng = new LatLng(24.868893,102.867252);
        Intent intent =getIntent();
        //24.86827,102.860754
        LatLng enLatLng = new LatLng(intent.getDoubleExtra("wd",24.86827),intent.getDoubleExtra("jd",102.860754));
        PlanNode stNode = PlanNode.withLocation(stLatLng);
        PlanNode enNode = PlanNode.withLocation(enLatLng);

        mSearch.walkingSearch((new WalkingRoutePlanOption())
                .from(stNode).to(enNode));

    }

    public void searchButtontest2(){

    }

    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult result){
        if (null == result) {
            return;
        }

        if (result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(RoutePlanTest.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        } else if (result.getRouteLines().size() >= 1){

            route = result.getRouteLines().get(0);
            WalkingRouteOverlay overlay = new RoutePlanTest.MyWalkingRouteTestOverlay(mBaidumap);
            mBaidumap.setOnMarkerClickListener(overlay);
            routeOverlay = overlay;
            overlay.setData(result.getRouteLines().get(0));
            overlay.addToMap();
            overlay.zoomToSpan();
        }else {
            Log.d("route result", "结果数<0");
        }
    }

    private class MyWalkingRouteTestOverlay extends WalkingRouteOverlay {

        public MyWalkingRouteTestOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
            }
            return null;
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
            }
            return null;
        }
    }

    @Override
    public void onGetTransitRouteResult(TransitRouteResult result){

    }

    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult result){

    }

    @Override
    public void onGetBikingRouteResult(BikingRouteResult result){

    }

    @Override
    public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

    }

    @Override
    public void onGetMassTransitRouteResult(MassTransitRouteResult result){

    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if (mSearch != null) {
            mSearch.destroy();
        }
        mMapView.onDestroy();
        super.onDestroy();
    }

}
