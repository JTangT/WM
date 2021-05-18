package com.jtangt.wm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.jtangt.wm.adapter.LeftAdapter;
import com.jtangt.wm.adapter.RightAdapter;
import com.jtangt.wm.po.Gwc;
import com.jtangt.wm.po.LeftBean;
import com.jtangt.wm.po.Message_Post;
import com.jtangt.wm.po.RightBean;
import com.jtangt.wm.po.ShopBean;
import com.jtangt.wm.po.Shop_detail_type;
import com.jtangt.wm.utils.HttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.jtangt.wm.utils.base64ToPicture;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class Shop_detail extends AppCompatActivity {
    private ListView lv_left;
    private String id;
    private StickyListHeadersListView lv_right;
    //private int currentLeftItem;
    private List<LeftBean> leftData;
    private List<RightBean> rightData;
    private ShopBean shopBean;
    RightAdapter rightAdapter;

    List<com.jtangt.wm.po.Shop_detail> shop_details;
    private List<Gwc> gwcs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.shop_detail);
        Intent intent =getIntent();
        id=intent.getStringExtra("shop_id");
        initView();
        getshopData();
        getData();

        gwcs=new ArrayList<>();

    }

    //初始化控件
    private void initView() {
        //初始化控件
        lv_left = (ListView) findViewById(R.id.lv_left);
        lv_right = (StickyListHeadersListView) findViewById(R.id.lv_right);
        TextView tv_go_to_pay=(TextView)findViewById(R.id.tv_go_to_pay);
        tv_go_to_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Shop_detail.this, dingdan_quereng.class);
                intent.putExtra("shop_id", id);
                intent.putExtra("gwcs",JSON.toJSONString(gwcs));
                startActivity(intent);
            }
        });
    }

    public void postData(Message_Post message_post,int what){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String json = null;
                try {
                    HttpUtils httpUtils = new HttpUtils();
                    json = httpUtils.getJsonContent(message_post);
                    //System.out.println(json);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Message msg = handler.obtainMessage();
                msg.obj = json;
                msg.what=what;
                handler.sendMessage(msg);
            }
        }).start();
    }
    Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    //System.out.println((String)msg.obj);
                    Map<String,Object> map=JSON.parseObject((String)msg.obj);
                    List<Shop_detail_type>  shop_detail_types=JSON.parseArray(map.get("left").toString(),Shop_detail_type.class);
                    shop_details=JSON.parseArray(map.get("right").toString(),com.jtangt.wm.po.Shop_detail.class);
                    leftData=new ArrayList<>();
                    rightData=new ArrayList<>();
                    for(Shop_detail_type s:shop_detail_types){
                        LeftBean leftBean =new LeftBean();
                        leftBean.title=s.getName();
                        leftBean.type=s.getType();

                        leftData.add(leftBean);
                    }
                    for (com.jtangt.wm.po.Shop_detail s:shop_details){
                        RightBean rightBean=new RightBean();
                        rightBean.num=s.getNum();
                        rightBean.biaoti=s.getName();
                        rightBean.picture=s.getPicturebase64();
                        rightBean.type=s.getType_name();
                        rightBean.typeId=s.getType();
                        rightBean.detail=s.getDetail();

                        rightData.add(rightBean);
                    }
                    initData();
                    break;
                case 2:
                    shopBean=JSON.parseObject((String) msg.obj,ShopBean.class);
                    //System.out.println(shopBean.getId());
                    set_shop();
                    break;

            }

        }
    };

    private void getData(){
        Message_Post message_post=new Message_Post();
        message_post.setType("goods_detail");
        message_post.setMessage("{\"shop_id\":"+id+"}");
        postData(message_post,1);
    }
    private void getshopData(){
        Message_Post message_post=new Message_Post();
        message_post.setType("Shop_getbyid");
        message_post.setMessage("{\"shop_id\":"+id+"}");
        postData(message_post,2);
    }


    private void set_shop(){
        base64ToPicture base64ToPicture =new base64ToPicture();
        ((ImageView)findViewById(R.id.iv_shop_icon)).setImageBitmap(base64ToPicture.sendImage(shopBean.getShopIconbase64()));
        ((TextView)findViewById(R.id.ll_shop_id)).setText(shopBean.getId()+"");
        ((TextView)findViewById(R.id.tv_shop_name)).setText(shopBean.getShopName());
        ((TextView)findViewById(R.id.tv_sale_num)).setText("月销："+shopBean.getSaleNum());
        ((TextView)findViewById(R.id.tv_cost)).setText("起送从¥"+shopBean.getStartprice()+" 配送¥"+shopBean.getOfferprice());
        ((TextView)findViewById(R.id.tv_adNotice)).setText(shopBean.getAdNotice());

        HashMap<String,String> w1 = JSON.parseObject(shopBean.getWelfare1(), HashMap.class);
        String we1="";
        for(Map.Entry<String, String> entry : w1.entrySet()){
            String mapKey = entry.getKey();
            String mapValue = entry.getValue();
            if(!we1.equals(""))
                we1+="，";
            we1+="满"+mapKey+"减"+mapValue;

        }
        ((TextView)findViewById(R.id.tv_welfare1)).setText(we1);
        if(shopBean.getWelfare2().equals("0"))
            ((TextView)findViewById(R.id.tv_welfare2)).setText("");
        else
            ((TextView)findViewById(R.id.tv_welfare2)).setText("新用户减"+shopBean.getWelfare2()+"元");
        ((TextView)findViewById(R.id.tv_time)).setText("预计送达时间："+shopBean.getTime()+"分钟");
    }





    //设置适配器
    private void initData() {
        View.OnClickListener clickListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num=0;
                int i=shop_details.get((Integer)v.getTag()).getId();
                int allnum=0;
                double sum=0;
                switch (v.getId()){
                    case R.id.dec_detail:
                        for(Gwc g:gwcs){
                            if(g.id==i){
                                gwcs.remove(g);
                                g.num--;
                                if(g.num>0)
                                    gwcs.add(g);
                                break;
                            }
                        }
                        break;
                    case R.id.add_detail:
                        double price=shop_details.get((Integer)v.getTag()).getPrice();
                        boolean f=true;
                        for(Gwc g:gwcs){
                            if(g.id==i){
                                gwcs.remove(g);
                                g.num++;
                                gwcs.add(g);
                                f=false;
                                break;
                            }
                        }
                        if(f){
                            Gwc gwc=new Gwc();
                            gwc.id=i;
                            gwc.num=1;
                            gwc.price=price;
                            gwc.name=shop_details.get((Integer)v.getTag()).getName();
                            gwcs.add(gwc);
                        }




                        break;
                }
                for(Gwc gwc:gwcs){
                    if(i==gwc.id){
                        num=gwc.num;
                    }
                    sum+=gwc.num*gwc.price;
                    allnum+=gwc.num;
                }

                ((TextView)findViewById(R.id.tv_total_price)).setText("￥"+String.format("%.2f", sum));
                ((TextView)findViewById(R.id.tv_go_to_pay)).setText("付款("+allnum+")");
                rightAdapter.addgwc((Integer)v.getTag(),num);
                rightAdapter.notifyDataSetChanged();
                //System.out.println((Integer)v.getTag()+" "+num);
            }
        };
        //创建适配器
        LeftAdapter leftAdapter = new LeftAdapter(leftData);
        rightAdapter = new RightAdapter(leftData, rightData, clickListener,this);

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
                        //currentLeftItem = i;
                        //设置当前被选中的左侧条目
                        leftAdapter.setCurrentSelect(position);
                        //刷新数据适配器
                        leftAdapter.notifyDataSetChanged();
                        break;
                    }
                }
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
                        //currentLeftItem = i;
                        //设置当前被选中的左侧条目
                        leftAdapter.setCurrentSelect(i);
                        //刷新数据适配器
                        leftAdapter.notifyDataSetChanged();
                        break;
                    }

                }
            }
        });
        //为右侧条目设置滚动事件
        lv_right.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                int now=lv_right.getFirstVisiblePosition();
                if(now<shop_details.size())now++;
                for (int i = 0; i < leftData.size(); i++) {
                    if (shop_details.get(now).getType() == leftData.get(i).type) {
                        //说明找到了对应条目,跳出循环,设置当前被选中的条目
                        //currentLeftItem = i;
                        //设置当前被选中的左侧条目
                        leftAdapter.setCurrentSelect(i);
                        //刷新数据适配器
                        leftAdapter.notifyDataSetChanged();
                        break;
                    }
                }

                //System.out.println(shop_details.get(now).getType_name());
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

    }
}
