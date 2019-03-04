package yanzhikai.threadsdemo;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * class for Testing Asynchronous Task
 */
public class AsyncTaskActivity extends AppCompatActivity {
    public static final String TAG = "AsyncTaskActivity";
    @BindView(R.id.btn_start)
    Button btnStart;
    @BindView(R.id.tv_head_title)
    TextView tvHeadTitle;
    @BindView(R.id.ll_head_left)
    LinearLayout llHeadLeft;

    private AsyncTask<Integer, Integer, String> task;

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);
        ButterKnife.bind(this);

        tvHeadTitle.setText("AsyncTask");

        task = new AsyncTask<Integer, Integer, String>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Log.d(TAG, "AsyncTask onPreExecute: init!");
                btnStart.setEnabled(false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.d(TAG, "AsyncTask onPostExecute: " + s);
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                Log.d(TAG, "AsyncTask onProgressUpdate: " + values[0]);
                btnStart.setText(String.valueOf(values[0]) + "%");
            }

            @Override
            protected void onCancelled(String s) {
                super.onCancelled(s);
                Log.d(TAG, "AsyncTask onCancelled: " + s);
            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
                Log.d(TAG, "AsyncTask onCancelled: ");
            }

            protected String doInBackground(Integer... integers) {
                if (isCancelled()) {
                    return "Cancel";
                }
                for (int i = 0; i <= 100; i += integers[0]) {
                    if (isCancelled()) {
                        return "Cancel";
                    }
                    try {
                        Thread.sleep(300);
                        publishProgress(i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return "Finish all!";
            }
        };
    }



    @OnClick({R.id.ll_head_left,R.id.btn_start})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_head_left:
                finish();
                break;
            case R.id.btn_start:
                task.execute(4);
                break;
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        task.cancel(true);
    }
}
