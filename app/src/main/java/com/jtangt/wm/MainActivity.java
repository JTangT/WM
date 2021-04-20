package com.jtangt.wm;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.jtangt.wm.circleimageview.CircleImageView;
import com.jtangt.wm.fragment.DaiNaFragment;
import com.jtangt.wm.fragment.DingDaiFragment;
import com.jtangt.wm.fragment.WoDeFragment;
import com.jtangt.wm.fragment.ZhuYeFragment;


public class MainActivity extends FragmentActivity {
    private LinearLayout llTabzhuye,llTabdaina,llTabdingdai,llTabwode;
    private ImageView imgZhuye,imgDaina,imgDingdai,imgWode;
    private Fragment tabZhuye,tabDaina,tabDingdai,tabWode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.activity_main);
        initView();
    }
    void initView(){
        llTabzhuye=this.findViewById(R.id.llZhuye);
        llTabdaina=this.findViewById(R.id.llDaina);
        llTabdingdai=this.findViewById(R.id.llDingdai);
        llTabwode=this.findViewById(R.id.llWode);

        llTabzhuye.setOnClickListener(clickListener);
        llTabdaina.setOnClickListener(clickListener);
        llTabdingdai.setOnClickListener(clickListener);
        llTabwode.setOnClickListener(clickListener);

        imgZhuye=this.findViewById(R.id.imgZhuye);
        imgDaina=this.findViewById(R.id.imgDaina);
        imgDingdai=this.findViewById(R.id.imgDingdai);
        imgWode=this.findViewById(R.id.imgWode);


        mr();
    }

    private void mr(){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction=fm.beginTransaction();
        imgZhuye.setImageResource(R.mipmap.zhuye_x);
        tabZhuye = new ZhuYeFragment();
        transaction.replace(R.id.content,tabZhuye);
        transaction.commit();
    }
    View.OnClickListener clickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction transaction=fm.beginTransaction();
            imgZhuye.setImageResource(R.mipmap.zhuye);
            imgDaina.setImageResource(R.mipmap.daina);
            imgDingdai.setImageResource(R.mipmap.dingdai);
            imgWode.setImageResource(R.mipmap.wode);

            switch (v.getId()){
                case R.id.llZhuye:
                    //Toast.makeText(MainActivity.this,"1",Toast.LENGTH_SHORT).show();
                    imgZhuye.setImageResource(R.mipmap.zhuye_x);
                    tabZhuye = new ZhuYeFragment();
                    transaction.replace(R.id.content,tabZhuye);

                    break;
                case R.id.llDaina:
                    //Toast.makeText(MainActivity.this,"2",Toast.LENGTH_SHORT).show();
                    imgDaina.setImageResource(R.mipmap.daina_x);
                    tabDaina = new DaiNaFragment();
                    transaction.replace(R.id.content,tabDaina);

                    break;
                case R.id.llDingdai:
                    //Toast.makeText(MainActivity.this,"3",Toast.LENGTH_SHORT).show();
                    imgDingdai.setImageResource(R.mipmap.dingdai_x);
                    tabDingdai = new DingDaiFragment();
                    transaction.replace(R.id.content,tabDingdai);
                    break;
                case R.id.llWode:
                    //Toast.makeText(MainActivity.this,"4",Toast.LENGTH_SHORT).show();
                    imgWode.setImageResource(R.mipmap.wode_x);
                    tabWode = new WoDeFragment();
                    transaction.replace(R.id.content,tabWode);
                    break;
            }
            transaction.commit();
        }
    };

}