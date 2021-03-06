package baidumapsdk.demo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class BuildingAdapter extends ArrayAdapter<ImageList> {
    private int resourceId;
    public BuildingAdapter(Context context, int resource, List<ImageList> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        ViewHolder viewHolder;
        View view;
        if(convertView==null){
            viewHolder=new ViewHolder();
            view = LayoutInflater.from(getContext()).inflate(//convertView为空代表布局没有被加载过，即getView方法没有被调用过，需要创建
                    resourceId, null);     // 得到子布局，非固定的，和子布局id有关
            viewHolder.ivImage = (ImageView) view.findViewById(R.id.ivImage);//获取控件,只需要调用一遍，调用过后保存在ViewHolder中
            viewHolder.tvName = (TextView) view.findViewById(R.id.tvName);  //获取控件
            view.setTag(viewHolder);
        }else{
            view=convertView;      //convertView不为空代表布局被加载过，只需要将convertView的值取出即可
            viewHolder=(ViewHolder) view.getTag();
        }
        ImageList imageList = getItem(position);//实例指定位置的水果
        viewHolder.ivImage.setImageResource(imageList.getImageId());//获得指定位置水果的id
        viewHolder.tvName.setText(imageList.getName());    //获得指定位置水果的名字
        return view;
    }
}

class ViewHolder{   //当布局加载过后，保存获取到的控件信息。
    ImageView ivImage;
    TextView tvName;
}

