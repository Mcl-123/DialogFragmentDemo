package com.saicmotor.maxus.myapplication;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ProgressDialogFragment extends DialogFragment {

    ProgressBar progressBar;

    int percent = 0;

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            percent+=10;
            progressBar.setProgress(percent);
            if (percent <= 100) {
                handler.postDelayed(this, 1000);
                return;
            }

            handler.removeCallbacks(this);
            if (getDialog() != null) {
                getDialog().cancel();
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);

        View view = inflater.inflate(R.layout.progress_dialog, container, false);
        ImageView delete = view.findViewById(R.id.delete);
        TextView cancel = view.findViewById(R.id.cancel);
        progressBar = view.findViewById(R.id.progressBar);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().cancel();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().cancel();
            }
        });
        handler.postDelayed(runnable, 1000);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null )
        {
            //如果宽高都为MATCH_PARENT,内容外的背景色就会失效，所以只设置宽全屏
            int width = ViewGroup.LayoutParams.WRAP_CONTENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);//全屏
//            progress_dialog.getWindow().setGravity(Gravity.BOTTOM);//内容设置在底部
            //内容的背景色.系统的内容宽度是不全屏的，替换为自己的后宽度可以全屏
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }
}
