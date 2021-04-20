package com.jtangt.wm.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.jtangt.wm.R;
import com.jtangt.wm.circleimageview.CircleImageView;
import com.jtangt.wm.gerenxinxi;

public class WoDeFragment extends Fragment {
    public WoDeFragment() {
        // Required empty public constructor
    }
    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState){
        CircleImageView icon;
        icon= (CircleImageView) view.findViewById(R.id.circleImageView);
        icon.setImageResource(R.mipmap.dingdai_x);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.tab_wode,container,false);
        ImageView imggrxx=view.findViewById(R.id.imggrxx);
        imggrxx.setOnClickListener(clickListener);

        return view;
    }

    View.OnClickListener clickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.imggrxx:
                    startActivity(new Intent(getActivity(), gerenxinxi.class));
                    break;
            }
        }
    };

}