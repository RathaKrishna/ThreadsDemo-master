package yanzhikai.threadsdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_interrupt)
    Button btnInterrupt;
    @BindView(R.id.btn_async)
    Button btnAsync;
    @BindView(R.id.btn_handle_thread)
    Button btnHandleThread;

    @BindView(R.id.btn_thread_pool)
    Button btnThreadPool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }


    @OnClick({R.id.btn_interrupt,R.id.btn_async,R.id.btn_handle_thread, R.id.btn_thread_pool})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_interrupt:
                startActivity(new Intent(this, InterruptActivity.class));
                break;
            case R.id.btn_async:
                startActivity(new Intent(this, AsyncTaskActivity.class));
                break;
            case R.id.btn_handle_thread:
                startActivity(new Intent(this, HandlerThreadActivity.class));
                break;
            case R.id.btn_thread_pool:
                startActivity(new Intent(this, ThreadPoolActivity.class));
                break;
        }
    }
}
