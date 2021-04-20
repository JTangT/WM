package com.jtangt.wm.wode;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.jtangt.wm.R;

public class guanyu extends Activity {
    ImageView re;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guanyu);
        init();
    }
    public void init(){
        re=findViewById(R.id.imgre);
        re.setOnClickListener(clickListener);
    }

    View.OnClickListener clickListener =new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.imgre:
                    finish();
                    break;
            }
        }
    };
}