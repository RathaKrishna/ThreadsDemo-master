package yanzhikai.threadsdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HandlerThreadActivity extends AppCompatActivity {
    public static final String TAG = "HandlerThreadActivity";
    @BindView(R.id.btn_start)
    Button btnStart;
    @BindView(R.id.tv_head_title)
    TextView tvHeadTitle;
    @BindView(R.id.ll_head_left)
    LinearLayout llHeadLeft;

    private HandlerThread handlerThread;
    private Handler workThreadHandler;
    private Handler mainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_thread);
        ButterKnife.bind(this);
        tvHeadTitle.setText("HandlerThread");
        initHandlerThread();
//        initThreadWithHandler();
        initMainHandler();
    }

    private void initMainHandler() {
        mainHandler = new Handler(getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                btnStart.setText(msg.arg1 + "%");
            }
        };
    }

    private void initThreadWithHandler() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                Looper.prepare();
                Log.d(TAG, "run: ");
                workThreadHandler = new Handler(Looper.myLooper()) {
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        //这里是子线程，可以做耗时操作
                        if (msg.arg1 < 100) {
                            Log.d(TAG, "HandlerThread handleMessage: " + msg.arg1 + "%");
                            Message message = obtainMessage(0, msg.arg1 + 4, 0);
                            sendMessageDelayed(message, 500);
                            if (mainHandler != null){
                                mainHandler.sendMessage(mainHandler.obtainMessage(0, msg.arg1, 0));
                            }
                        }

                    }
                };
                Looper.loop();
            }
        };
        thread.start();
    }

    private void initHandlerThread() {
        handlerThread = new HandlerThread("workThreadA", Process.THREAD_PRIORITY_BACKGROUND);
        handlerThread.start();

        workThreadHandler = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //这里是子线程，可以做耗时操作
                if (msg.arg1 <= 100) {
                    Log.d(TAG, "HandlerThread handleMessage: " + msg.arg1 + "%");
                    Message message = obtainMessage(0, msg.arg1 + 4, 0);
                    sendMessageDelayed(message, 500);
                    if (mainHandler != null){
                        mainHandler.sendMessage(mainHandler.obtainMessage(0, msg.arg1, 0));
                    }
                }
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handlerThread != null) {
            handlerThread.quit();
        }
        if (mainHandler != null) {
            mainHandler.removeCallbacksAndMessages(null);
        }
    }

    @OnClick({R.id.ll_head_left,R.id.btn_start})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_head_left:
                finish();
                break;
            case R.id.btn_start:
                btnStart.setEnabled(false);
                Message message = workThreadHandler.obtainMessage(0, 0, 0);
                message.sendToTarget();
                break;
        }
    }

}
