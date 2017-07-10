package com.example.cier.counter;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button start,stop;
    private TextView showTime;
    private EditText input;
    private int countTime=0;
    private Timer timer;
    private TimerTask timerTask;
    private boolean isRunning=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView(){
        start= (Button) findViewById(R.id.start);
        stop= (Button) findViewById(R.id.stop);
        showTime= (TextView) findViewById(R.id.showTime);
        input= (EditText) findViewById(R.id.input);
        start.setOnClickListener(this);
        stop.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start:
                start();
                break;
            case R.id.stop:
                stop();
        }

    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            showTime.setText(msg.arg1+"");
        }
    };
    private void start(){
        countTime=Integer.parseInt(input.getText().toString());
        if(countTime==0){
           return;
        }
        isRunning=true;
        timer=new Timer();
        timerTask=new TimerTask() {
            @Override
            public void run() {
                while(isRunning) {
                    countTime--;
                    Message msg = handler.obtainMessage();
                    msg.arg1 = countTime;
                    handler.sendMessage(msg);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        timer.schedule(timerTask,1000);
    }
    private void stop(){
        timer.cancel();
        isRunning=false;
    }
}
