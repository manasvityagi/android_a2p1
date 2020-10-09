package com.manas.a2_p1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    Button getWeatherBtn;
    TextView resultWeatherText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Lat and Longitude of Auckland
        final String url = "https://api.openweathermap.org/data/2.5/onecall?lat=-36.8483&lon=174.7625&appid=ab57f83f7f35c8c4355d9fb49b2d0f96";
        resultWeatherText = findViewById(R.id.weather_result);
        getWeatherBtn = findViewById(R.id.get_weather_data_btn);

        getWeatherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

                JsonObjectRequest jsonObjectRequest = new
                        JsonObjectRequest(Request.Method.GET, url,
                        new JSONObject(),
                        new com.android.volley.Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    String server_data = response.toString();
                                    JSONObject json_outer_obj = new JSONObject(server_data);
//                                    JSONObject businessObject = offerObject.getJSONObject("business");
//                                    String nameValue = businessObject.getString("name");
//                                    System.out.println(nameValue);
                                    JSONObject currentValue = json_outer_obj.getJSONObject("current");
                                    JSONObject json_inner_current_obj = new JSONObject(currentValue.toString());
                                    String currentTemp = json_inner_current_obj.getString("temp");
                                    String currentPressure = json_inner_current_obj.getString("pressure");
                                    String currentWindspeed = json_inner_current_obj.getString("wind_speed");
                                    String humidity = json_inner_current_obj.getString("humidity");
                                    Double tempInCelcius = Double.valueOf(currentTemp) -273.15;
                                    //String currentTempValue = currentValue.getString("currentTempValue");
                                    String result = "Temprature: "+ tempInCelcius + "\n"+
                                     "Pressure: "+ currentPressure + "\n"+
                                     "Humidity: "+ humidity + "\n"+
                                     "Windspeed: "+ currentWindspeed ;


                                    resultWeatherText.setText(result);
                                    Log.e("data", currentTemp);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error8", error.toString());
                    }
                });
                queue.add(jsonObjectRequest);

            }
        });

    }
}