package com.vk.app_arduino;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.biansemao.widget.ThermometerView;
import com.hadiidbouk.charts.BarData;
import com.hadiidbouk.charts.ChartProgressBar;



import java.util.ArrayList;
import java.util.Random;

public class CurrentTasksAtHome<view> extends Fragment {

    ChartProgressBar mChart;
    ChartProgressBar mChart_two;
    private ThermometerView thermometerTv;

    public static CurrentTasksAtHome newInstance(){
        return new CurrentTasksAtHome();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_tasks_at_home, container, false);

        ArrayList<BarData> dataList = new ArrayList<>();

        BarData data = new BarData("Sep", 3.4f, "3.4€");
        dataList.add(data);

        data = new BarData("Oct", 8.0f, "8.0€");
        dataList.add(data);

        data = new BarData("Nov", 1.8f, "1.8€");
        dataList.add(data);

        data = new BarData("Dec", 7.3f, "7.3€");
        dataList.add(data);

        data = new BarData("Jan", 6.2f, "6.2€");
        dataList.add(data);

        data = new BarData("Feb", 3.3f, "3.3€");
        dataList.add(data);

        mChart = (ChartProgressBar) view.findViewById(R.id.ChartProgressBar);

        mChart.setDataList(dataList);
        mChart.build();
        mChart.setOnBarClickedListener(index -> Toast.makeText(getActivity(), String.valueOf(index), Toast.LENGTH_SHORT).show());
        mChart.disableBar(dataList.size());


        thermometerTv = view.findViewById(R.id.tv_thermometer);

        view.findViewById(R.id.btn_anim).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float value = new Random().nextFloat() * 7 + 35;
                thermometerTv.setValueAndStartAnim(value);
            }
        });
        view.findViewById(R.id.btn_operate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float value = new Random().nextFloat() * 7 + 35;
                thermometerTv.setCurValue(value);
            }
        });

        return view;
    }
}