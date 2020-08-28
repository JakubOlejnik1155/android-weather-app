package com.mm.findweather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    TextView mInfoCity;
    TextView mTemp;
    TextView mDesc;
    TextView mPressure;
    TextView mHumidity;
    TextView mWindSpeed;
    TextView mWindAngle;
    TextView mClouds;

    ImageView mImageWeather;
    EditText mSearchField;
    Button mSearchButton;

    CardView mCardViewWeather;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInfoCity = findViewById(R.id.mainName);
        mTemp= findViewById(R.id.temperature);
        mDesc=findViewById(R.id.description);
        mPressure = findViewById(R.id.pressure);
        mHumidity = findViewById(R.id.humidity);
        mWindSpeed = findViewById(R.id.windSpeed);
        mWindAngle = findViewById(R.id.windAngle);
        mClouds = findViewById(R.id.cloudiness);

        mImageWeather= findViewById(R.id.imageWeather);
        mSearchField= findViewById(R.id.editText);
        mSearchButton= findViewById(R.id.searchButton);

        mCardViewWeather=findViewById(R.id.cardview2);
        mSearchField.setText("");

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!String.valueOf(mSearchField.getText()).equals("")){
                    InputMethodManager inputManager=(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getRootView().getWindowToken(),0);
                    mCardViewWeather.setVisibility(View.VISIBLE);
                    apiKey(String.valueOf(mSearchField.getText()));
                    mSearchField.setText("");
                }else {
                    Toast.makeText(MainActivity.this,"Please choose a City",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void apiKey(final String city) {
        OkHttpClient client;
        client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=e25e4b9a5c85c0542314d87a78d3f7ad&units=metric")
                .get()
                .build();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Response response = client.newCall(request).execute();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {

                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    String responseData= response.body().string();
                    try {
                        JSONObject json= new JSONObject(responseData);
                        JSONArray array=json.getJSONArray("weather");
                        JSONObject object= array.getJSONObject(0);

                        String description=object.getString("description");
                        String icons =object.getString("icon");
                        JSONObject templ= json.getJSONObject("main");
                        Double Temperature= templ.getDouble("temp");
                        Double press = templ.getDouble("pressure");
                        Double hum = templ.getDouble("humidity");
                        JSONObject wind= json.getJSONObject("wind");
                        String windSpeed = wind.getString("speed");
                        String windAngle = wind.getString("deg");
                        JSONObject clouds = json.getJSONObject("clouds");
                        String cloudiness = clouds.getString("all");

                        setText(mInfoCity, city.toUpperCase());
                        String temps=Math.round(Temperature)+"°C";
                        setText(mTemp,temps);
                        setText(mDesc,description);
                        setImage(mImageWeather,icons);
                        setText(mPressure, "pressure: "+ press.toString() + "hPa");
                        setText(mHumidity, "humidity: " + hum + "%");
                        setText(mWindSpeed, "speed: " + windSpeed + "m/s");
                        setText(mWindAngle, "direction: " + windAngle + "°");
                        setText(mClouds, "cloudiness: " + cloudiness + "%");

                        Log.i("INFO", String.valueOf(json));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    private void setText(final TextView text, final String value){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                text.setText(value);
            }
        });
    }
    private void setImage(final ImageView imageView, final String value){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //paste switch
                switch (value){
                    case "01d": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d01d));
                        break;
                    case "01n": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d01d));
                        break;
                    case "02d": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d02d));
                        break;
                    case "02n": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d02d));
                        break;
                    case "03d": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d02d));
                        break;
                    case "03n": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d02d));
                        break;
                    case "04d": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d04d));
                        break;
                    case "04n": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d04d));
                        break;
                    case "09d": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d09d));
                        break;
                    case "09n": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d09d));
                        break;
                    case "10d": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d10d));
                        break;
                    case "10n": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d10d));
                        break;
                    case "11d": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d11d));
                        break;
                    case "11n": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d11d));
                        break;
                    case "13d": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d13d));
                        break;
                    case "13n": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d13d));
                        break;
                    default:
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.load));

                }
            }
        });
    }
}
