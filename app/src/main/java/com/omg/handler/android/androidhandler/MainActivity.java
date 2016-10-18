package com.omg.handler.android.androidhandler;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static Handler mHandler;
    private TextView mTextView;
    private Thread t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.text);
        // create a handler to update the UI
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                mTextView.setText("" + msg.what);
            }
        };
    }

    @Override
    public void onResume(){
        super.onResume();
        t = new TestThread();
        t.start();
    }

    @Override
    public void onPause(){
        super.onPause();
        t.interrupt();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    static public class TestThread extends Thread {
        @Override
        public void run() {
            // Simulate a slow network
            try {
                for(int i=0; i < 10; i++) {
                    new Thread().sleep(1000);
                    mHandler.sendEmptyMessage(i);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
