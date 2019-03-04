package yanzhikai.threadsdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * class for Testing Thread Interruption
 */
public class InterruptActivity extends AppCompatActivity {
    public static final String TAG = "InterruptActivity";

    private Thread threadA;

    @BindView(R.id.btn_interrupt)
    Button btnInterrupt;
    @BindView(R.id.tv_head_title)
    TextView tvHeadTitle;
    @BindView(R.id.ll_head_left)
    LinearLayout llHeadLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interrupt);
        ButterKnife.bind(this);
        tvHeadTitle.setText("Interrupt");
        initThread();

    }

    private void initThread(){
        threadA = new Thread(){
            @Override
            public void run() {
                super.run();
                while (!isInterrupted()){
                    try {
                        Log.d(TAG, "Interrupt Run : ------");
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Log.d(TAG, "InterruptedException");
                        break;
                    }
                }
            }
        };
        threadA.start();
    }

    @OnClick({R.id.ll_head_left,R.id.btn_interrupt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_head_left:
                finish();
                break;
            case R.id.btn_interrupt:
                threadA.interrupt();
                break;
        }
    }


}
