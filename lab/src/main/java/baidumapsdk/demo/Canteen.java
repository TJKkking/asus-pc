package baidumapsdk.demo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class Canteen extends Activity {
    private ListView lvBuilding;
    private List<ImageList> libraryList = new ArrayList<>();
    private static final LatLng[] LibraryLoc = {   //图书馆坐标
            new LatLng(24.868446,102.86595),
            new LatLng(24.868209,102.852502),
            new LatLng(24.86852,102.853603),
    };
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.canteen);//设置布局文件
        lvBuilding = (ListView)findViewById(R.id.LibraryListView);
        getData();
        BuildingAdapter libraryAdapter = new BuildingAdapter(this,R.layout.listview_item,libraryList);
        lvBuilding.setAdapter(libraryAdapter);
        //设置点击事件
    }

    @Override
    protected void onStart(){
        super.onStart();
        lvBuilding.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                //弹窗
                final int index = arg2;
                AlertDialog.Builder builder = new AlertDialog.Builder(Canteen.this);
                builder.setTitle("提示");
                builder.setMessage("确定前往这里吗？\n点击“带我去”进入导航功能");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {//“确定”进入路线规划
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        double wd;
                        double jd;
                        wd = LibraryLoc[index].latitude;
                        jd = LibraryLoc[index].longitude;

                        Intent intent = new Intent(Canteen.this, RoutePlan.class);
                        intent.putExtra("from", 0);
                        intent.putExtra("wd",wd);
                        intent.putExtra("jd",jd);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("取消",null);
                builder.setNeutralButton("带我去", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        Toast.makeText(Canteen.this, "点击了带我去", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Canteen.this, Navigation.class);
                        startActivity(intent);
                    }
                });
                builder.create().show();
            }
        });
    }

    private void getData(){
        ImageList library1 = new ImageList("东区食堂",R.drawable.canteen);
        libraryList.add(library1);
        ImageList library2 = new ImageList("西区启园1食堂",R.drawable.canteen);
        libraryList.add(library2);
        ImageList library3 = new ImageList("西区启园2食堂",R.drawable.canteen);
        libraryList.add(library3);
    }
}
