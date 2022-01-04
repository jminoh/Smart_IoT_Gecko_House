package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private View drawerView;
    TextView temview, serverview;

    private Socket socket;
    PrintWriter out;
    BufferedReader in;
    String msg;

    String ip = "192.168.2.58";
    int port = 9000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        drawerLayout = (DrawerLayout) findViewById(R.id.constrain);
        drawerView = (View) findViewById(R.id.drawer);

        ImageButton mnuopen = (ImageButton) findViewById(R.id.mnuopen); // 메뉴 오픈 버튼
        ImageButton btntem = (ImageButton) findViewById(R.id.btnTem); // 온도 습도 버튼
        ImageButton eat = findViewById(R.id.btn_eat);



        temview = findViewById(R.id.tvTem); // 온도 습도 확인 뷰
        serverview = findViewById(R.id.tvserver);




        mnuopen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(drawerView);
            }
        });

        Button close = (Button) findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
            }
        });

        ImageButton memo = findViewById(R.id.memo);
        memo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MemomainActivicy.class);
                startActivity(intent);
            }
        });

        btntem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TemActivity.class);
                startActivity(intent);
            }
        });

        eat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,EatActivity.class);
                startActivity(intent);
            }
        });

        ImageButton camera = findViewById(R.id.btn_came);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CCTVActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String tem = bundle.getString("Tem");
        String hum = bundle.getString("Hum");
        String sendmsg = (tem+","+hum);
        String no   = ("");
        if (tem == null && hum == null) {
            temview.setText(" 설정 온도 : " + "\n" + " 설정 습도 : ");
        } else {
            temview.setText(" 설정 온도 :     " + tem + " ºC" + "\n" + " 설정 습도 :     " + hum + " %");
        }

        Thread work = new Thread() {
            public void run() {
                try {
                    Socket socket = new Socket(ip, port);
                    out = new PrintWriter(socket.getOutputStream(), true);                                                                                                                   //전송한다.
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    if (tem != null && hum != null) {
                        out.write(sendmsg);
                        out.flush();
                    } else {
                        out.write(no);
                        out.flush();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                while (true) {
                    try {
                        msg = in.readLine();
                        serverview.post(new Runnable() {
                            @Override
                            public void run() {
                                serverview.setText(msg);
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };work.start();
    }
}
