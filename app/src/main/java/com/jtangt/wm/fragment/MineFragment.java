package com.jtangt.wm.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.jtangt.wm.R;
import com.jtangt.wm.loginandregiser.Login;
import com.jtangt.wm.bean.User;
import com.jtangt.wm.ui.CircleImageView;
import com.jtangt.wm.utils.DBDefine;
import com.jtangt.wm.utils.Base64ToPicture;
import com.jtangt.wm.mine.My_infoActivity;
import com.jtangt.wm.mine.AboutActivity;
import com.jtangt.wm.mine.My_addressActivity;
import com.jtangt.wm.mine.My_commentActivity;
import com.jtangt.wm.mine.My_collectionActivity;

public class MineFragment extends Fragment {
    private User user;
    private View vi;
    public MineFragment() {
        // Required empty public constructor
    }
    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState){
        CircleImageView icon;
        icon= (CircleImageView) view.findViewById(R.id.circleImageView);
        icon.setImageResource(R.mipmap.touxiang);

    }

    private User getuser_id(){
        User user;
        DBDefine dbDefine=new DBDefine(getActivity());
        dbDefine.open();
        User[] users=dbDefine.queryAllData();
        dbDefine.close();
        if(users!=null){
            user=users[0];
            return user;
        }
        return null;
    }

    public void LoginCheck(View v){

        TextView teusername=v.findViewById(R.id.teuser_wode);
        TextView teid=v.findViewById(R.id.teid_wode);
        CircleImageView circleImageView = v.findViewById(R.id.circleImageView);

        if(user!=null){
            Base64ToPicture base64ToPicture = new Base64ToPicture();
            teusername.setText(user.getUsername());
            teid.setText("ID: "+user.getId());
            circleImageView.setImageBitmap(base64ToPicture.sendImage(user.getPicturebase64()));
            return;
        }
        circleImageView.setImageResource(R.mipmap.touxiang);
        teusername.setText("请先登录");
        teid.setText("");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.tab_wode,container,false);
        vi=view;
        ImageView imggrxx=view.findViewById(R.id.imggrxx);
        ImageView imgwddz=view.findViewById(R.id.wddz);
        ImageView imgwdsc=view.findViewById(R.id.wdsc);
        ImageView imgwdpj=view.findViewById(R.id.wdpj);
        ImageView imggy=view.findViewById(R.id.gy);

        imggrxx.setOnClickListener(clickListener);
        imgwddz.setOnClickListener(clickListener);
        imgwdsc.setOnClickListener(clickListener);
        imgwdpj.setOnClickListener(clickListener);
        imggy.setOnClickListener(clickListener);


        user=getuser_id();

        LoginCheck(view);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        user=getuser_id();
        LoginCheck(vi);

    }

    View.OnClickListener clickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(user==null){
                startActivity(new Intent(getActivity(), Login.class));
                return;
            }

            switch (v.getId()){
                case R.id.imggrxx:
                    startActivity(new Intent(getActivity(), My_infoActivity.class));
                    break;
                case R.id.wddz:
                    startActivity(new Intent(getActivity(), My_addressActivity.class));
                    break;
                case R.id.wdsc:
                    startActivity(new Intent(getActivity(), My_collectionActivity.class));
                    break;
                case R.id.wdpj:
                    startActivity(new Intent(getActivity(), My_commentActivity.class));
                    break;
                case R.id.gy:
                    startActivity(new Intent(getActivity(), AboutActivity.class));
                    break;
            }
        }
    };

}