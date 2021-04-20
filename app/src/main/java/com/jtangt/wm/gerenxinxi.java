package com.jtangt.wm;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
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
        //finish();

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