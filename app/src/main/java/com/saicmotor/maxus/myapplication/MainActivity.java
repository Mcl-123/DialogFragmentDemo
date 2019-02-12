package com.saicmotor.maxus.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.contrarywind.view.WheelView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PickerDialogFragment.MyDialogFragmentListener {

    Button progressDialog;
    Button pickerDialog;
    WheelView wheelView;

    List<String> options1Items;
    List<List<Integer>> options2Items;
    List<List<List<Integer>>> options3Items;

    String options1ItemsJson;
    String options2ItemsJson;
    String options3ItemsJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = findViewById(R.id.progress_dialog);
        pickerDialog = findViewById(R.id.picker_dialog);
        wheelView = findViewById(R.id.wheelview);

        initData();
        initEvent();

    }

    @Override
    public void getData(int optionIndex1, int optionIndex2, int optionIndex3) {
        Toast.makeText(this,
                options1Items.get(optionIndex1) + " " + options2Items.get(optionIndex1).get(optionIndex2) +
                        "." + options3Items.get(optionIndex1).get(optionIndex2).get(optionIndex3),
                Toast.LENGTH_SHORT).show();
    }

    private void initEvent() {
        progressDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ProgressDialogFragment().show(getSupportFragmentManager(), "progress");
            }
        });
        pickerDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickerDialogFragment pickerDialogFragment = new PickerDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putString("options1ItemsJson", options1ItemsJson);
                bundle.putString("options2ItemsJson", options2ItemsJson);
                bundle.putString("options3ItemsJson", options3ItemsJson);
                pickerDialogFragment.setArguments(bundle);
                pickerDialogFragment.show(getSupportFragmentManager(), "picker");
            }
        });
    }

    private void initData() {
        options1Items = new ArrayList<>();
        options1Items.add("FM");
        options1Items.add("AM");

        options2Items = new ArrayList<>();
        ArrayList<Integer> options2Items_01 = new ArrayList<>();
        for (int i = 80; i < 121; i++) {
            options2Items_01.add(i);
        }
        ArrayList<Integer> options2Items_02 = new ArrayList<>();
        for (int i = 10; i < 51; i+=5) {
            options2Items_02.add(i);
        }
        options2Items.add(options2Items_01);
        options2Items.add(options2Items_02);

        options3Items = new ArrayList<>();
        List<List<Integer>> options3Items_01 = new ArrayList<>();
        List<List<Integer>> options3Items_02 = new ArrayList<>();

        for (int i = 80; i < 121; i++) {
            ArrayList<Integer> options3Items_01_item = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                options3Items_01_item.add(j);
            }
            options3Items_01.add(options3Items_01_item);
        }

        for (int i = 10; i < 51; i+=5) {
            ArrayList<Integer> options3Items_02_item = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                options3Items_02_item.add(j);
            }
            options3Items_02.add(options3Items_02_item);
        }

        options3Items.add(options3Items_01);
        options3Items.add(options3Items_02);

        Gson gson = new Gson();
        options1ItemsJson = gson.toJson(options1Items);
        options2ItemsJson = gson.toJson(options2Items);
        options3ItemsJson = gson.toJson(options3Items);
    }
}
