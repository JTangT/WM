package com.jtangt.wm.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jtangt.wm.MainActivity;
import com.jtangt.wm.R;
import com.jtangt.wm.po.ShopBean;
import com.jtangt.wm.utils.AnalusisUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ZhuYeFragment extends Fragment {

    private Banner banner;
    private List<Integer> image=new ArrayList<>();
    private List<String> title=new ArrayList<>();

    private SimpleAdapter simpleAdapter;
    private ArrayList arrayList;
    private int imgID[]={R.drawable.shop1,R.drawable.shop1};

    private void initData() {

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

    public void initlist(){
//        String json="{\"num\":2,\"1\":{}}"
//        List<ShopBean> shopBeans= AnalusisUtils(json);
//        arrayList=new ArrayList();
//        for(int i =0;i<imgID.length;i++){
//            HashMap<String,Object> map=new HashMap<>();
//            map.put("shop_icon",imgID[i]);
//
//            arrayList.add(map);
//        }
//        String from[]={""};
//        int to[]={};
//        simpleAdapter=new SimpleAdapter(getActivity(),arrayList,R.layout.yihang,from,to);

    }

    public void OnBannerClick(int position) {
        Toast.makeText(getActivity(), "你点了第" + (position + 1) + "张轮播图", Toast.LENGTH_SHORT).show();
    }


    private class MyImageLoader extends ImageLoader {

        public void displayImage(Context context, Object path, ImageView imageView) {

            Glide.with(context).load(path).into(imageView);

        }
    }


    public ZhuYeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =LayoutInflater.from(getActivity()).inflate(R.layout.tab_zhuye,container,false);
        banner = view.findViewById(R.id.banner);
        initData();
        initView();
        initlist();
        return view;
    }
}