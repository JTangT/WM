package com.jtangt.wm.fragment;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.jtangt.wm.R;
import com.jtangt.wm.po.Message_Post;
import com.jtangt.wm.po.ShopBean;
import com.jtangt.wm.utils.HttpUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.os.Handler;

import com.jtangt.wm.utils.base64ToPicture;

public class ZhuYeFragment extends Fragment {
    private Banner banner;
    private List<Integer> image=new ArrayList<>();
    private List<String> title=new ArrayList<>();
    private View view;

    //轮播图
    private void initData() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                String json = null;
//                try {
//                    HttpUtils httpUtils = new HttpUtils();
//                    Message_Post message_post=new Message_Post();
//                    message_post.setType("shop/getAllShop");
//                    message_post.setMessage("{}");
//                    json = httpUtils.getJsonContent(message_post);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                Message msg = handler.obtainMessage();
//                msg.obj = json;
//                msg.what=2;
//                handler.sendMessage(msg);
//            }
//        }).start();

        image.add(R.drawable.rbt1);
        image.add(R.drawable.rbt2);
        title.add("缤纷肯德基");
        title.add("校园团购上线了");

    }
    private void initView() {

        banner.setIndicatorGravity(BannerConfig.CENTER);

        banner.setImageLoader(new MyImageLoader());

        banner.setImages(image);

        banner.setBannerAnimation(Transformer.Default);

        banner.isAutoPlay(true);

        banner.setBannerTitles(title);

        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);//可以根据自己想要的更换stylr

        banner.setDelayTime(3000);

        banner.setOnBannerListener(this::OnBannerClick);
        banner.start();
    }
    public void OnBannerClick(int position) {
        //Toast.makeText(getActivity(), "你点了第" + (position + 1) + "张轮播图", Toast.LENGTH_SHORT).show();
    }
    private class MyImageLoader extends ImageLoader {

        public void displayImage(Context context, Object path, ImageView imageView) {

            Glide.with(context).load(path).into(imageView);

        }
    }



    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    initlist((String)msg.obj);
                    break;
            }

        }
    };

    //店铺
    public void getjson(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String json = null;
                try {
                    HttpUtils httpUtils = new HttpUtils();
                    Message_Post message_post=new Message_Post();
                    message_post.setType("shop/getAllShop");
                    message_post.setMessage("{}");
                    json = httpUtils.getJsonContent(message_post);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Message msg = handler.obtainMessage();
                msg.obj = json;
                msg.what=1;
                handler.sendMessage(msg);
            }
        }).start();
    }

    public void initlist(String json){
        ArrayList arrayList=arrayList=new ArrayList();
        base64ToPicture base64ToPicture=new base64ToPicture();
            //String pic ="1";
        //String json="[{\"num\":1,\"0\":{\"id\":1,\"shopName\":\"啦啦啦\",\"saleNum\":120,\"offerprice\":10,\"startprice\":10,\"welfare1\":\"qwe\",\"welfare2\":\"asd\",\"adNotice\":\"qweasdzxc\",\"time\":2,\"shopIcon\":\""+pic+"\"}]";
        List<ShopBean> shopBeans= JSON.parseArray(json,ShopBean.class);
        for (ShopBean s :shopBeans) {
            HashMap<String,Object> map=new HashMap<>();
            map.put("shop_icon",base64ToPicture.sendImage(s.getShopIconbase64()));
            //map.put("shop_icon",R.drawable.shop1);
            map.put("shop_name",s.getShopName());
            map.put("sale_num",s.getSaleNum());
            map.put("cost",s.getStartprice()+s.getOfferprice());
            map.put("adNotice",s.getAdNotice());
            map.put("welfare1",s.getWelfare1());
            map.put("welfare2",s.getWelfare2());
            map.put("time",s.getTime());


            arrayList.add(map);
        }

        String from[]={"shop_icon","shop_name","sale_num","cost","adNotice","welfare1","welfare2","time"};
        //"tv_shop_name","tv_sale_num","tv_cost","tv_adNotice","tv_welfare1","tv_welfare2","tv_time"
        int to[]={R.id.iv_shop_icon,R.id.tv_shop_name,R.id.tv_sale_num,R.id.tv_cost,R.id.tv_adNotice,R.id.tv_welfare1,R.id.tv_welfare2,R.id.tv_time};

        SimpleAdapter simpleAdapter=new SimpleAdapter(getActivity(),arrayList,R.layout.yihang,from,to);
        simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            public boolean setViewValue(View view, Object data,
                                        String textRepresentation) {
                if (view instanceof ImageView && data instanceof Bitmap) {
                    ImageView image = (ImageView) view;
                    image.setImageBitmap((Bitmap) data);
                    return true;
                }
                return false;
            }
        });
        ListView listView=view.findViewById(R.id.zhuye_list);
        ScrollView sv = (ScrollView) view.findViewById(R.id.zhuye_scroll);
        sv.smoothScrollTo(0, 0);
        listView.setAdapter(simpleAdapter);


    }




    public ZhuYeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =LayoutInflater.from(getActivity()).inflate(R.layout.tab_zhuye,container,false);
        banner = view.findViewById(R.id.banner);
        initData();
        initView();
        getjson();
        return view;
    }


}