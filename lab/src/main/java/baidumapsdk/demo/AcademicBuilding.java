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

public class AcademicBuilding extends Activity {
    private ListView lvBuilding;
    private List<ImageList> libraryList = new ArrayList<>();
    private static final LatLng[] LibraryLoc = {   //图书馆坐标
            new LatLng(24.86627,102.860326),
            new LatLng(24.86627,102.860326),
            new LatLng(24.865692,102.859163),
            new LatLng(24.866795,102.859001),
            new LatLng(24.865295,102.854263),
            new LatLng(24.865774,102.853962),
            new LatLng(24.866168,102.853841),
            new LatLng(24.866836,102.853719),
    };
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.academicbuilding);//设置布局文件
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
                AlertDialog.Builder builder = new AlertDialog.Builder(AcademicBuilding.this);
                builder.setTitle("提示");
                builder.setMessage("确定前往这里吗？\n点击“带我去”进入导航功能");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {//“确定”进入路线规划
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        double wd;
                        double jd;
                        wd = LibraryLoc[index].latitude;
                        jd = LibraryLoc[index].longitude;

                        Intent intent = new Intent(AcademicBuilding.this, RoutePlan.class);
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
                        Toast.makeText(AcademicBuilding.this, "点击了带我去", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AcademicBuilding.this, Navigation.class);
                        startActivity(intent);
                    }
                });
                builder.create().show();
            }
        });
    }

    private void getData(){
        ImageList library1 = new ImageList("汇学1",R.drawable.academicbuilding);
        libraryList.add(library1);
        ImageList library2 = new ImageList("汇学2",R.drawable.academicbuilding);
        libraryList.add(library2);
        ImageList library3 = new ImageList("汇学3",R.drawable.academicbuilding);
        libraryList.add(library3);
        ImageList library4 = new ImageList("汇学4",R.drawable.academicbuilding);
        libraryList.add(library4);
        ImageList library5 = new ImageList("汇文1",R.drawable.academicbuilding);
        libraryList.add(library5);
        ImageList library6 = new ImageList("汇文2",R.drawable.academicbuilding);
        libraryList.add(library6);
        ImageList library7 = new ImageList("汇文3",R.drawable.academicbuilding);
        libraryList.add(library7);
        ImageList library8 = new ImageList("汇文4",R.drawable.academicbuilding);
        libraryList.add(library8);
    }
}
