package com.xjx.renderscriptstudy;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.xjx.renderscriptstudy.tasks.BasicTask;
import com.xjx.renderscriptstudy.tasks.MappingTask;
import com.xjx.renderscriptstudy.tasks.ReduceStructTask;
import com.xjx.renderscriptstudy.tasks.SingleSourceTask;

public class MainActivity extends AppCompatActivity {

    private AsyncTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getLifecycle().addObserver(new LifeComponent());
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, NextActivity.class));
            }
        });
        final EditText editText = findViewById(R.id.editText);
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("basic".equalsIgnoreCase(editText.getText().toString())) {
                    new BasicTask(getApplicationContext()).execute();
                } else if ("mapping".equalsIgnoreCase(editText.getText().toString())) {
                    new MappingTask(getApplicationContext()).execute();
                } else if ("single".equalsIgnoreCase(editText.getText().toString())) {
                    new SingleSourceTask(getApplicationContext()).execute();
                } else if ("struct".equalsIgnoreCase(editText.getText().toString())) {
                    new ReduceStructTask(getApplicationContext()).execute();
                }


            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (task != null) {
            task.cancel(true);
            task = null;
        }
    }
}
