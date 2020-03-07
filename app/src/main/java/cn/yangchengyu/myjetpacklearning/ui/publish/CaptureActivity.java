package cn.yangchengyu.myjetpacklearning.ui.publish;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import cn.yangchengyu.libnavannotation.ActivityDestination;
import cn.yangchengyu.myjetpacklearning.R;


@ActivityDestination(pageUrl = "main/tabs/capture")
public class CaptureActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);
        TextView captureView = findViewById(R.id.text_capture);

        captureView.setText("this is CaptureActivity");
    }
}