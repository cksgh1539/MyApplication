package com.example.b10626.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by hp on 2018-04-12.
 */

public class user_chart extends AppCompatActivity {

    PieChart pieChart;
    int deposit,minus,point;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart);

        Intent intent = getIntent();

        deposit = intent.getIntExtra("deposit_total",0);
        minus = intent.getIntExtra("minus_total",0);
        point = intent.getIntExtra("point_total",0);

        pieChart = (PieChart) findViewById(R.id.pieChart);

     //   pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 10);

        pieChart.setDragDecelerationFrictionCoef(0.85f); // 부드럽게 돌아가는거?

        pieChart.setEntryLabelTextSize(0f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setHoleRadius(40);
       // pieChart.setTransparentCircleRadius(1f);

        ArrayList<PieEntry> yValues =  new ArrayList<>();
        ArrayList<String> xValues =  new ArrayList<>();
if(deposit > 0)
        yValues.add(new PieEntry(deposit, "입금"));
if(minus > 0 )
        yValues.add(new PieEntry(minus, "컨텐츠 사용"));
if(point > 0)
         yValues.add(new PieEntry(point, "포인트 환원"));

       // yValues.add(new PieEntry(3000, "포인트 환원"));
       // yValues.add(new PieEntry(34f, "C"));
      //  yValues.add(new PieEntry(34f, "D"));
      //  yValues.add(new PieEntry(34f, "E"));

        Description description =  new Description();
        description.setText("hello, chanho"); // 그래프 밑에 설명
        description.setTextSize(12);
        pieChart.setDescription(description);

        pieChart.animateY(1500, Easing.EasingOption.EaseInOutCubic);

        PieDataSet dataSet = new PieDataSet(yValues, "         금액이 0원인 경우 표시되지 않습니다");

        dataSet.setSliceSpace(3f); // 틈 간격
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);


        PieData data = new PieData(dataSet);
        data.setValueTextSize(15f);
        data.setValueTextColor(Color.BLUE);

        pieChart.setData(data);
    }
}