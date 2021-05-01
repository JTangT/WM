package com.jtangt.wm.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.jtangt.wm.R;
import com.jtangt.wm.ui.CircleImageView;
import com.jtangt.wm.wode.gerenxinxi;
import com.jtangt.wm.wode.guanyu;
import com.jtangt.wm.wode.wodedizi;
import com.jtangt.wm.wode.wodepinjia;
import com.jtangt.wm.wode.wodeshoucang;

public class WoDeFragment extends Fragment {
    public WoDeFragment() {
        // Required empty public constructor
    }
    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState){
        CircleImageView icon;
        icon= (CircleImageView) view.findViewById(R.id.circleImageView);
        icon.setImageResource(R.mipmap.touxiang);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.tab_wode,container,false);
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

        return view;
    }

    View.OnClickListener clickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.imggrxx:
                    startActivity(new Intent(getActivity(), gerenxinxi.class));
                    break;
                case R.id.wddz:
                    startActivity(new Intent(getActivity(), wodedizi.class));
                    break;
                case R.id.wdsc:
                    startActivity(new Intent(getActivity(), wodeshoucang.class));
                    break;
                case R.id.wdpj:
                    startActivity(new Intent(getActivity(), wodepinjia.class));
                    break;
                case R.id.gy:
                    startActivity(new Intent(getActivity(), guanyu.class));
                    break;
            }
        }
    };

}