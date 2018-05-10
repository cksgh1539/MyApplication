package com.example.b10626.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;

/**
 * Created by hp on 2018-03-30.
 */

public class UserData extends AppCompatActivity {
   AlertDialog.Builder alertDialog;
    ListView listView;
    php task;
    ArrayList<userInfoItem> listItem = new ArrayList<>();
    SwipeRefreshLayout SRlayout;
    LinearLayout sel_layout,DB_layout;
    RelativeLayout graph;
    String ID,PWD;
    Calendar cal =Calendar.getInstance();
    Calendar sel_cal1 = Calendar.getInstance();
    Calendar sel_cal2 = Calendar.getInstance();
    PieChart pieChart;

    int deposit,minus,point; //그래프 사용 변수
    int Item_key=0;
    int sel_key=0;
    int Year,Month,Day ,Num;

    TextView Day1,Day2;
    Button sel_day1,sel_day2,search;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_db);

        Day1 = (TextView)findViewById(R.id.day1);
        Day2 = (TextView)findViewById(R.id.day2);
        sel_day1 = (Button)findViewById(R.id.sel_day1);
        sel_day2 = (Button)findViewById(R.id.sel_day2);
        search = (Button)findViewById(R.id.search);
        listView = (ListView) findViewById(R.id.listView);
        SRlayout = (SwipeRefreshLayout) findViewById(R.id.SRlayout);
        sel_layout = (LinearLayout) findViewById(R.id.sel_layout);
        DB_layout = (LinearLayout)findViewById(R.id.DB_layout);
        graph = (RelativeLayout)findViewById(R.id.graph);
        graph.setVisibility(View.GONE);
        Intent intent = getIntent();
        ID = intent.getStringExtra("ID");
        PWD = intent.getStringExtra("password");

        deposit = intent.getIntExtra("deposit_total",0);
        minus = intent.getIntExtra("minus_total",0);
        point = intent.getIntExtra("point_total",0);

      //  Graph();
        task = new php();

        task.execute("http://113.198.80.147/login_done.php", ID, PWD);

            SRlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    SRlayout.setRefreshing(true);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            cal = Calendar.getInstance();
                            Item_key = 0;
                            listItem.clear();
                            task = new php();
                            task.execute("http://113.198.80.147/login_done.php", ID, PWD);
                            Toast.makeText(getApplicationContext(), "갱신 중입니다!", Toast.LENGTH_SHORT).show();
                            SRlayout.setRefreshing(false);
                        }
                    },1000);

                }
            });


    }

    private class php extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            try {
                String user_id = urls[1];
                String user_password = urls[2];
                URL url = new URL(urls[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("user_name","UTF-8")+"="+URLEncoder.encode(user_id,"UTF-8")+"&"
                        +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(user_password,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
                String result = "";
                String line;

                while((line = bufferedReader.readLine())!=null){
                    result += line;
                }
                bufferedReader.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String str) {

        String date = "";
        String uid,name,ins,money,total;

        Date ChangeTime = cal.getTime();

        try{
            JSONObject root = new JSONObject(String.valueOf(str));
            JSONArray ja = root.getJSONArray("results");

            for(int i=ja.length()-1; i>0; i--) {
                JSONObject jo = ja.getJSONObject(i);
                date = jo.getString("date");
                name = jo.getString("name");
              //  ins = jo.getString("ins");
                money = jo.getString("money");
                total = jo.getString("total");

                SimpleDateFormat transdate = new SimpleDateFormat("yyyy-MM-dd");
                Date abc = transdate.parse(date);

                if (i > ja.length() -6 && Item_key == 0) {
                    listItem.add(new userInfoItem(date, name, money, total));
                }else if (Item_key == 1 || Item_key == 2 || Item_key == 3) {
                    if (ChangeTime.compareTo(abc) < 0)
                        listItem.add(new userInfoItem(date, name, money, total));
                }else if(Item_key == 4){
                    Date Day1 = sel_cal1.getTime();
                    Date Day2 = sel_cal2.getTime();

                    if(Day1.compareTo(abc)<0 && Day2.compareTo(abc)>0)
                        listItem.add(new userInfoItem(date, name, money, total));
                }
            }
        }catch(JSONException e){
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
            infoAdapter adapter = new infoAdapter(listItem,getApplicationContext(),R.layout.infolist);
          listView.setAdapter(adapter);
          adapter.notifyDataSetChanged();
          listView.setDividerHeight(10);
            Toast.makeText(getApplicationContext(), new SimpleDateFormat("yyyy.MM.dd").format(ChangeTime)+"리스트뷰 마지막", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void week(View view){
        sel_layout.setVisibility(View.GONE);
        Item_key = 1;
        cal = Calendar.getInstance();
        cal.add(cal.DATE,-7);
        Date c = cal.getTime();
        Toast.makeText(getApplicationContext(), new SimpleDateFormat("yyyy.MM.dd").format(c)+"1주일", Toast.LENGTH_SHORT).show();
        listItem.clear();
        task = new php();
        task.execute("http://113.198.80.147/login_done.php", ID, PWD);
    }

    public void month(View view){
        sel_layout.setVisibility(View.GONE);
        cal = Calendar.getInstance();
        cal.add(Calendar.MONTH,-1);
        Date a = cal.getTime();
        Toast.makeText(getApplicationContext(), new SimpleDateFormat("yyyy.MM.dd").format(a)+"한 달", Toast.LENGTH_SHORT).show();
        Item_key = 2;
        listItem.clear();
        task = new php();
        task.execute("http://113.198.80.147/login_done.php", ID, PWD);
    }

    public void Thr_month(View view){
        sel_layout.setVisibility(View.GONE);
        cal = Calendar.getInstance();
        cal.add(Calendar.MONTH,-3);
        Date b = cal.getTime();
        Toast.makeText(getApplicationContext(), new SimpleDateFormat("yyyy.MM.dd").format(b)+"세 달", Toast.LENGTH_SHORT).show();
        Item_key = 3;
        listItem.clear();
        task = new php();
        task.execute("http://113.198.80.147/login_done.php", ID, PWD);
    }

    public void sel_term(View view){
        if(sel_key == 0) {
            sel_layout.setVisibility(view.VISIBLE);
            sel_key = 1;
        }else if(sel_key == 1){
            sel_layout.setVisibility(view.GONE);
            sel_key = 0;
        }
    }
    public void term_Alpha(){
        Day1.setAlpha(0.0f);
        Day2.setAlpha(0.0f);
        sel_day1.setAlpha(0.0f);
        sel_day2.setAlpha(0.0f);
        search.setAlpha(0.0f);

    }

    public void search(View view){
        cal = Calendar.getInstance();
        cal.add(Calendar.MONTH,-3);
        Date b = cal.getTime();
        Toast.makeText(getApplicationContext(), new SimpleDateFormat("yyyy.MM.dd").format(b)+"세 달", Toast.LENGTH_SHORT).show();
        Item_key = 4;
        listItem.clear();
        task = new php();
        task.execute("http://113.198.80.147/login_done.php", ID, PWD);
    }
    public void sel_day1(View view){
        showCalender(1);
    }

    public void sel_day2(View view){
        showCalender(2);
    }

    public void showCalender(int i){
        cal = Calendar.getInstance();
        if(i == 1) {
            cal.add(Calendar.MONTH,-6);
            Year = cal.get(Calendar.YEAR);
            Month = cal.get(Calendar.MONTH);
            Day = cal.get(Calendar.DAY_OF_MONTH);
            Num = i;
            new DatePickerDialog(UserData.this, mDateSetListener, Year,

                    Month, Day).show();
         }
        if(i == 2){
            Year = cal.get(Calendar.YEAR);
            Month = cal.get(Calendar.MONTH);
            Day = cal.get(Calendar.DAY_OF_MONTH);
            Num = i;
            new DatePickerDialog(UserData.this, mDateSetListener, Year,

                    Month, Day).show();
          }
        }

    DatePickerDialog.OnDateSetListener mDateSetListener =

            new DatePickerDialog.OnDateSetListener() {

                @Override

                public void onDateSet(DatePicker view, int year, int monthOfYear,

                                      int dayOfMonth) {
                    Year = year;
                    Month = monthOfYear;
                    Day = dayOfMonth;

                 if(Num == 1) {
                       Day1.setText(String.format("%d/%d/%d", Year,
                         Month + 1, Day));
                       sel_cal1.set(Calendar.YEAR,Year);
                     sel_cal1.set(Calendar.MONTH,Month);
                     sel_cal1.set(Calendar.DATE,Day);
                       //sel_cal1 = cal;
                       Date z = sel_cal1.getTime();
                     Toast.makeText(getApplicationContext(), new SimpleDateFormat("yyyy.MM.dd").format(z)+"", Toast.LENGTH_SHORT).show();
                    // Toast.makeText(getApplicationContext(), ""+cal, Toast.LENGTH_SHORT).show();
                     }else if(Num ==2){
                       Day2.setText(String.format("%d/%d/%d", Year,
                        Month + 1, Day));
                     sel_cal2.set(Calendar.YEAR,Year);
                     sel_cal2.set(Calendar.MONTH,Month);
                     sel_cal2.set(Calendar.DATE,Day);
                     Date z = sel_cal2.getTime();
                     Toast.makeText(getApplicationContext(), new SimpleDateFormat("yyyy.MM.dd").format(z)+"", Toast.LENGTH_SHORT).show();
                        }
                     }
                   };

    public void Graph(){
      //  setContentView(R.layout.chart);

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_logout:
                alertDialog = new AlertDialog.Builder(this);
                alertDialog.setMessage("로그아웃을 하시겠습니까??");
                alertDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });
                alertDialog.setNegativeButton("취소",null);
                alertDialog.show();
                //  return true;
                break;
            case R.id.trade_detail :
                graph.setVisibility(View.GONE);
                break;
            case R.id.graph :
                graph.setVisibility(View.VISIBLE);
                Graph();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}


