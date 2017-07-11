package com.app.android.sketchproject;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.app.android.sketchproject.UI.MenuActivity;

/**
 * Created by jieun on 2017-05-06.
 */

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, MenuActivity.class);
        this.startActivity(intent);
        finish();
    }

}
