package com.example.consumo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();

        button.setOnClickListener((view) -> {

            //startActivity(new Intent(packageContext: MainActivity.this,MainActivity2.class));
        });
    }

    private void initUI(){button=findViewById(R.id.btnVolley);}
}