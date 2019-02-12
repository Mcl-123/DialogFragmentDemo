package com.saicmotor.maxus.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class PickerDialogFragment extends DialogFragment {

    List<String> options1Items;
    List<List<String>> options2Items;
    List<List<List<String>>> options3Items;

    private MyDialogFragmentListener myDialogFragmentListener;

    int optionIndex1 = 0;
    int optionIndex2 = 0;
    int optionIndex3 = 0;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            myDialogFragmentListener = (MyDialogFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implementon MyDialogFragmentListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String options1ItemsJson = bundle.getString("options1ItemsJson");
            String options2ItemsJson = bundle.getString("options2ItemsJson");
            String options3ItemsJson = bundle.getString("options3ItemsJson");

            Gson gson = new Gson();
            Type type1 = new TypeToken<List<String>>(){}.getType();
            options1Items = gson.fromJson(options1ItemsJson, type1);

            Type type2 = new TypeToken<List<List<String>>>(){}.getType();
            options2Items = gson.fromJson(options2ItemsJson, type2);

            Type type3 = new TypeToken<List<List<List<String>>>>(){}.getType();
            options3Items = gson.fromJson(options3ItemsJson, type3);
        }

        View view = inflater.inflate(R.layout.picker_dialog, container, false);
        ImageView delete = view.findViewById(R.id.delete);
        TextView sure = view.findViewById(R.id.sure);
        TextView cancel = view.findViewById(R.id.cancel);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 通过接口回传数据给activity
                if (myDialogFragmentListener != null) {
                    myDialogFragmentListener.getData(optionIndex1, optionIndex2, optionIndex3);
                }
                getDialog().cancel();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().cancel();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().cancel();
            }
        });
        initWheelView(view);
        return view;
    }

    private void initWheelView(View view) {
        WheelView options1 = view.findViewById(R.id.options1);
        final WheelView options2 = view.findViewById(R.id.options2);
        final WheelView options3 = view.findViewById(R.id.options3);

        options1.setCyclic(false);
        options1.setTextColorCenter(Color.WHITE);
        options1.setTextSize(34);
        options1.setAdapter(new ArrayWheelAdapter(options1Items));
        options1.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                optionIndex1 = index;
                optionIndex2 = 0;
                optionIndex3 = 0;
                options2.setAdapter(new ArrayWheelAdapter(options2Items.get(optionIndex1)));
                options3.setAdapter(new ArrayWheelAdapter((options3Items.get(optionIndex1)).get(optionIndex2)));
                options2.setCurrentItem(0);
                options3.setCurrentItem(0);
            }
        });

        options2.setCyclic(false);
        options2.setTextColorCenter(Color.WHITE);
        options2.setTextSize(34);
        options2.setAdapter(new ArrayWheelAdapter(options2Items.get(optionIndex1)));
        options2.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                optionIndex2 = index;
                optionIndex3 = 0;
                options3.setAdapter(new ArrayWheelAdapter((options3Items.get(optionIndex1)).get(optionIndex2)));
                options3.setCurrentItem(0);
            }
        });

        options3.setCyclic(false);
        options3.setTextColorCenter(Color.WHITE);
        options3.setTextSize(34);
        options3.setAdapter(new ArrayWheelAdapter((options3Items.get(optionIndex1)).get(optionIndex2)));
        options3.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                optionIndex3 = index;
            }
        });
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

    public interface MyDialogFragmentListener {
        void getData(int optionIndex1, int optionIndex2, int optionIndex3);
    }
}
