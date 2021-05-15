package com.jtangt.wm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.jtangt.wm.adapter.LeftAdapter;
import com.jtangt.wm.adapter.RightAdapter;
import com.jtangt.wm.po.LeftBean;
import com.jtangt.wm.po.RightBean;

import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class Shop_detail extends AppCompatActivity {
    private ListView lv_left;
    private StickyListHeadersListView lv_right;
    private int currentLeftItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.shop_detail);
        Intent intent =getIntent();
        String msg=intent.getStringExtra("shop_id");
        initView();
        initData();


    }

    //初始化控件
    private void initView() {
        //初始化控件
        lv_left = (ListView) findViewById(R.id.lv_left);
        lv_right = (StickyListHeadersListView) findViewById(R.id.lv_right);
    }

    //设置适配器
    private void initData() {
        //创建适配器
        final LeftAdapter leftAdapter = new LeftAdapter(Data.getLeftData());
        //获取左侧数据
        final List<LeftBean> leftData = Data.getLeftData();
        //获取右侧数据
        final List<RightBean> rightData = Data.getRightData(leftData);
        RightAdapter rightAdapter = new RightAdapter(leftData, rightData, this);

        //为左侧布局设置适配器
        lv_left.setAdapter(leftAdapter);
        lv_right.setAdapter(rightAdapter);


        //为左侧条目设置点击事件
        lv_left.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //当左侧条目被点击时,记录下被点击条目的type
                int type = leftData.get(position).type;
                //遍历右侧条目,并获取右侧条目的typeId,与刚刚获取的type对比,是否一致
                for (int i = 0; i < rightData.size(); i++) {
                    if (type == rightData.get(i).typeId) {
                        //如果找到对应的条目,那就将右侧条目滚动至对应条目,并跳出循环
                        lv_right.smoothScrollToPosition(i);
                        currentLeftItem = i;
                        //设置当前被选中的左侧条目
                        leftAdapter.setCurrentSelect(currentLeftItem);
                        //刷新数据适配器
                        leftAdapter.notifyDataSetChanged();
                        break;
                    }
                }

//                Toast.makeText(MainActivity.this, "您选中了"+leftData.get(position).title, Toast.LENGTH_SHORT).show();
            }
        });

        //为右侧条目设置点击事件
        lv_right.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(MainActivity.this, "右侧条目被点击了"+position, Toast.LENGTH_SHORT).show();

                //当右侧条目被点击时,获取被点击条目的typeId
                int typeId = rightData.get(position).typeId;
                //遍历左侧条目
                for (int i = 0; i < leftData.size(); i++) {
                    //获取左侧条目的type,与右侧条目的typeId对比是否一致
                    if (typeId == leftData.get(i).type) {
                        //说明找到了对应条目,跳出循环,设置当前被选中的条目
                        currentLeftItem = i;
                        //设置当前被选中的左侧条目
                        leftAdapter.setCurrentSelect(currentLeftItem);
                        //刷新数据适配器
                        leftAdapter.notifyDataSetChanged();
                        break;
                    }

                }
            }
        });
    }
}
