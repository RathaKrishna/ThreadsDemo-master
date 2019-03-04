package yanzhikai.threadsdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ThreadPoolActivity extends AppCompatActivity {

    @BindView(R.id.tv_head_title)
    TextView tvHeadTitle;
    @BindView(R.id.ll_head_left)
    LinearLayout llHeadLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_pool);
        ButterKnife.bind(this);
        tvHeadTitle.setText("ThreadPool");

    }

    @OnClick({R.id.ll_head_left})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_head_left:
                finish();
                break;
        }
    }
}
