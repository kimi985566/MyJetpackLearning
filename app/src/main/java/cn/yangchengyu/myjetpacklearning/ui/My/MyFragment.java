package cn.yangchengyu.myjetpacklearning.ui.My;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import cn.yangchengyu.libnavannotation.FragmentDestination;
import cn.yangchengyu.myjetpacklearning.R;

@FragmentDestination(pageUrl = "main/tabs/my", asStarter = false)
public class MyFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        textView.setText("this is MyFragment");
        return root;
    }
}
