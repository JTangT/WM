package com.jtangt.wm.wode;

import androidx.appcompat.app.AppCompatActivity;
import com.jtangt.wm.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class wodeshoucang extends Activity {
    ImageView re;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wodeshoucang);
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