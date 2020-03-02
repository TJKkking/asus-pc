package baidumapsdk.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Building extends Activity {
    private ListView FunctionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.building);
        FunctionList = (ListView) findViewById(R.id.bulidingList);
        FunctionList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, getData()));
        // after andrioid m,must request Permiision on runtime
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        FunctionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                Class<?> TargetClass = null;
                switch (arg2) {
                    case 0:
                        TargetClass = AcademicBuilding.class;
                        break;
                    case 1:
                        TargetClass = Library.class;
                        break;
                    case 2:
                        TargetClass = Canteen.class;
                        break;
                    case 3:
                        TargetClass = Dormitory.class;
                        break;
                    case 4:
                        TargetClass = Schoolgate.class;
                        break;
                    default:
                        break;
                }
                if (TargetClass != null) {
                    Intent intent = new Intent(Building.this, TargetClass);
                    intent.putExtra("from", 0);
                    startActivity(intent);
                }
            }
        });
    }

    private List<String> getData() {
        List<String> data = new ArrayList<String>();
        data.add("教学楼");
        data.add("图书馆");
        data.add("食堂");
        data.add("学生公寓");
        data.add("校门");
        return data;
    }
}

