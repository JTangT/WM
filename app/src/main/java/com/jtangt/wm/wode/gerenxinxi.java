package com.jtangt.wm.wode;

import android.app.Activity;
import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.jtangt.wm.R;
import com.jtangt.wm.po.User;
import com.jtangt.wm.utils.DBDefine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class gerenxinxi extends Activity {
    ImageView re;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerenxinxi);

        init();
    }
    private void init(){
        re=findViewById(R.id.imgre);
        re.setOnClickListener(clickListener);
        Button logout=findViewById(R.id.logout);
        logout.setOnClickListener(clickListener);
        //finish();

    }
    View.OnClickListener clickListener =new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.imgre:
                    finish();
                    break;
                case R.id.logout:
                    setuser_out();
                    finish();
                    break;
            }
        }
    };

//TODO
    private void setuser_out(){

        DBDefine dbDefine=new DBDefine(this);
        dbDefine.open();
        User[] users=dbDefine.queryAllData();
        dbDefine.rmData(users[0].getId());


        //System.out.println("ads"+re);
        //TODO
        dbDefine.close();
    }
}