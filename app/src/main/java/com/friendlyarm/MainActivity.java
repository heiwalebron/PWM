package com.friendlyarm;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.friendlyarm.AndroidSDK.HardwareControler;
import com.friendlyarm.Utils.CommonFuncs;

import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int START =1 ;
    HardwareControler hw;
    EditText textEditor;
    Button btnSub;
    Button btnAdd;
    Button btn_start;
    Button btn_stop;

    Boolean mStop=false;
    /*private Handler handler=new Handler(){
      public void handleMessage(Message msg){
          switch (msg.what){
              case START:
                  for (int i=0;i<4;i++){
                      hw.PWMPlay(1000);

                      try {
                          sleep(1000);
                      } catch (InterruptedException e) {
                          e.printStackTrace();
                      }

                      hw.PWMStop();
                      if (mStop) break;
                      try {
                          sleep(1000);
                      } catch (InterruptedException e) {
                          e.printStackTrace();
                      }
                  }
                  break;
          }
      }
    };*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSub = ((Button)findViewById(R.id.btnSub));
        btnAdd = ((Button)findViewById(R.id.btnAdd));
        btn_start=(Button)findViewById(R.id.btn_start);
        btn_stop=(Button)findViewById(R.id.btn_stop);

        btnSub.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btn_start.setOnClickListener(this);
        btn_stop.setOnClickListener(this);

        textEditor = ((EditText)findViewById(R.id.fEditor));
        hw = new HardwareControler();
    }
    private void playPWM()
    {
        int f;
        f = Integer.valueOf(textEditor.getText().toString()).intValue();
        if (hw.PWMPlay(f) != 0) {
            CommonFuncs.showAlertDialog(this,"Fail to play!");
        }

    }

    private void stopPWM()
    {
        if (hw.PWMStop() != 0) {
            CommonFuncs.showAlertDialog(this,"Fail to stop!");
        }
    }

    @Override
    public void onClick(View v) {
        int f;
        switch (v.getId()) {
            case R.id.btnSub:
                f = Integer.valueOf(textEditor.getText().toString()).intValue() - 100;
                if (f < 1) {
                    f = 1;
                }
                textEditor.setText( "" + f );
                playPWM();
                break;
            case R.id.btnAdd:
                f = Integer.valueOf(textEditor.getText().toString()).intValue() + 100;
                if (f < 1) {
                    f = 1;
                }
                textEditor.setText( "" + f );
                playPWM();
                break;
            case R.id.btn_start:
                mStop=false;
                new Thread(new Runnable() {
                @Override
                public void run() {
                   /* Message message=new Message();
                    message.what=START;
                    handler.sendMessage(message);*/
                    for (int i=0;i<4;i++){
                        hw.PWMPlay(1000);

                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        hw.PWMStop();
                        if (mStop) break;
                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    for (int i=0;i<4;i++){
                        hw.PWMPlay(1000);

                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        hw.PWMStop();
                        if (mStop) break;
                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }).start();




                break;
            case R.id.btn_stop:

                mStop=true;
                stopPWM();
                break;
            default:
                break;
        }
    }
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onRestart() {
        super.onRestart();
    }

    /*@Override
    public void onResume() {
        super.onResume();
        playPWM();
    }*/

    @Override
    public void onPause() {
        stopPWM();
        super.onPause();
    }

    @Override
    public void onStop() {
        stopPWM();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        stopPWM();
        super.onDestroy();
    }
}
