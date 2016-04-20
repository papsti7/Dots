package com.sewm.defaultteam.dots;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    void test(View view )
    {
        TextView textView = (TextView)findViewById(R.id.tv_test);
        textView.setText("Hat funktioniert!");

    }
}
