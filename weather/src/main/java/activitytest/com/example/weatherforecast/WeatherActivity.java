package activitytest.com.example.weatherforecast;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import activitytest.com.example.weatherforecast.bean.Future;
import activitytest.com.example.weatherforecast.bean.weatherBean;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class WeatherActivity extends AppCompatActivity {
    //声明控件
    private ScrollView weatherLayout;
    private TextView titleCity;
    private TextView titleUpdateTime;
    private TextView degreeText;
    private TextView weatherInfoText;
    private LinearLayout forecastLayout;
    private TextView aqiText;
    private TextView pm25Text;
    private TextView comfortText;
    private TextView carWashText;
    private TextView sportText;
    private ImageView bingimg;
    private final String TAG = "调试";
    weatherBean weatherbean;
    int CODE_FOR_WRITE_PERMISSION=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21){
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(getResources().getColor(R.color.qinglv));
        }
        setContentView(R.layout.activity_weather);

        //初始化控件
        weatherLayout = (ScrollView) findViewById(R.id.weather_layout);
        titleCity = (TextView) findViewById(R.id.title_city);
        titleUpdateTime = (TextView) findViewById(R.id.title_updata_time);
        degreeText = (TextView) findViewById(R.id.degree_text);
        weatherInfoText = (TextView) findViewById(R.id.weather_info_text);
        forecastLayout = (LinearLayout) findViewById(R.id.forecast_layout);
        comfortText = (TextView) findViewById(R.id.comfort_text);
        carWashText = (TextView) findViewById(R.id.car_wash_text);
        sportText = (TextView) findViewById(R.id.sport_text);
        bingimg = (ImageView)findViewById(R.id.bing_img);

        //定位
        initPermission();
        GPSisopen();
        Location location = Location_Based_Services.getNetWorkLocation(this);
        String weatherURL = "http://v.juhe.cn/weather/index?format=2&cityname=双流&key=5de82eb7320772ad9b6eb86f8a24f47c";


        //使用经纬度确定天气信息
        if(location!=null)
        {
            String lon = String.valueOf(location.getLongitude());
            String lat  =String.valueOf(location.getLatitude());
            weatherURL = "http://v.juhe.cn/weather/geo?format=2&key=5de82eb7320772ad9b6eb86f8a24f47c&" +
                    "lon="+lon+"&lat="+lat;

        }

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().
                url(weatherURL)
                .method("GET", null).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {


                //解析gson对象

                try {
                    String responseData = response.body().string();
                    Log.d(TAG, "onResponse: "+responseData);
                    Gson gson = new Gson();
                    weatherbean = gson.fromJson(responseData, weatherBean.class);



                } catch (JsonSyntaxException e) {

                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //初始化界面
                        String cityName = weatherbean.getResult().getToday().getCity();
                        String updateTime = weatherbean.getResult().getSk().getTime();
                        String degree = weatherbean.getResult().getSk().getTemp()+"℃";
                        String weatherInfo = weatherbean.getResult().getToday().getWeather();
                        titleCity.setText(cityName);
                        titleUpdateTime.setText(updateTime);
                        degreeText.setText(degree);
                        weatherInfoText.setText(weatherInfo);
                        forecastLayout.removeAllViews();
                        for(Future forecast : weatherbean.getResult().getFuture()){
                            View view = LayoutInflater.from(WeatherActivity.this).inflate(R.layout.forecast_item,
                                    forecastLayout,false);
                            TextView datatext = (TextView)view.findViewById(R.id.data_text);
                            TextView infotext = (TextView)view.findViewById(R.id.info_text);
                            TextView maxtext = (TextView)view.findViewById(R.id.max_text);
                            datatext.setText(forecast.getWeek());
                            infotext.setText(forecast.getWeather());
                            maxtext.setText(forecast.getTemperature());
                            forecastLayout.addView(view);
                        }

                        String comfort = "穿衣建议 "+weatherbean.getResult().getToday().getDressing_advice();
                        String carWash = "洗车指数 "+weatherbean.getResult().getToday().getWash_index();
                        String sport = "运动指数 "+weatherbean.getResult().getToday().getExercise_index();
                        comfortText.setText(comfort);
                        carWashText.setText(carWash);
                        sportText.setText(sport);

                    }
                });

            }
        });
        //获取背景图片
        String  requestBingPic = "http://guolin.tech/api/bing_pic";
        Request requestBing = new Request.Builder().
                url(requestBingPic)
                .method("GET", null).build();
        Call callBing = okHttpClient.newCall(requestBing);
        callBing.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPic =response.body().string();
                Log.d(TAG,bingPic);

                //主线程加载图片
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(WeatherActivity.this).load(bingPic).into(bingimg);
                    }
                });

            }
        });


    }




    private void initPermission() {

        if (ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "initPermission: 没有获得允许");
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

        }
    }

    private void GPSisopen() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "请打开GPS", Toast.LENGTH_SHORT);
            final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("请打开GPS连接");
            dialog.setMessage("为了获取定位服务，请先打开GPS");
            dialog.setPositiveButton("设置", new android.content.DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(intent, CODE_FOR_WRITE_PERMISSION);
                }
            });
            dialog.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            dialog.show();
        }
    }

}

