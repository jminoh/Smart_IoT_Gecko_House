package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TemActivity extends AppCompatActivity {
    private Socket socket;
    PrintWriter out;
    BufferedReader in;
    EditText temtext, humtext;
    ImageButton btnsend;
    TextView tvserver;
    String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tem);

        temtext = (EditText) findViewById(R.id.temtext);
        humtext = (EditText) findViewById(R.id.humtext);
        btnsend = (ImageButton) findViewById(R.id.btnsend);
        tvserver = (TextView) findViewById(R.id.tvserver);

        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tem = temtext.getText().toString();
                String hum = humtext.getText().toString();
                if(tem != null && hum !=null) {
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    intent.putExtra("Tem", tem);
                    intent.putExtra("Hum", hum);
                    Toast.makeText(TemActivity.this, "설정 완료", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }else{
                    Toast.makeText(TemActivity.this, "다시 설정해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ImageButton temback = findViewById(R.id.btntemback);
        temback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TemActivity.this, "취소", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}

//192.168.2.211:5000



