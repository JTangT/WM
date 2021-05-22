package com.jtangt.wm.mine;

import android.app.Activity;
import android.os.Bundle;

import com.jtangt.wm.R;
import com.jtangt.wm.bean.User;
import com.jtangt.wm.utils.DBDefine;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class My_infoActivity extends Activity {
    ImageView re;
    User user;
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
        user=getuser();

        TextView id_geren=findViewById(R.id.id_geren);
        id_geren.setText(user.getId()+"");


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

    private User getuser(){
        User user;
        DBDefine dbDefine=new DBDefine(this);
        dbDefine.open();
        User[] users=dbDefine.queryAllData();
        dbDefine.close();
        if(users!=null){
            user=users[0];
            return user;
        }
        return null;


    }
}