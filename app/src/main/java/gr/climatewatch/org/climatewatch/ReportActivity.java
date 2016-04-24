package gr.climatewatch.org.climatewatch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReportActivity extends AppCompatActivity {

    //Graphics
    private ImageButton posImage;
    private ImageButton sysImage;

    private String latitude;
    private String longitude;
    private LocationManager locationManager = null;
    private SensorEventListener lightSensorListener;
    private SensorEventListener pressureSensorListener;
    private SensorEventListener humiditySensorListener;
    private SensorEventListener tempratureSensorListener;
    private double humidity;
    private double lux;
    private double pressure;
    private double temprature;
    private SensorManager mSensorManager;
    private Sensor mPressure;
    private Sensor mHumidity;
    private Sensor mLux;
    private Sensor mTemprature;
    private SensorValues values;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
     }

    @Override
    protected void onStart() {
        super.onStart();
        posImage = (ImageButton) findViewById(R.id.posBtn);
        sysImage = (ImageButton) findViewById(R.id.symptBtn);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mPressure = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        mHumidity = mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        mLux = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mTemprature = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        initializeListeners();

    }


    private void initializeListeners() {
        lightSensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                lux = event.values[0];
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        pressureSensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                pressure = event.values[0];
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        humiditySensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                humidity = event.values[0];
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        tempratureSensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                temprature = sensorEvent.values[0];
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };

        posImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Location loc = getLastBestLocation();
                values = new SensorValues(String.valueOf(humidity), String.valueOf(lux), String.valueOf(pressure), String.valueOf(temprature), String.valueOf(loc.getLatitude()), String.valueOf(loc.getLongitude()));
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date();
                String format = dateFormat.format(date);
                String t = format.replace(" ", "T");
                values.setTimestamp(t + "Z");
                System.out.println("Values from class: " + values.toJson());
                Toast.makeText(getApplicationContext(), values.toString() + "\n" + loc.getLatitude() + "," + loc.getLongitude(), Toast.LENGTH_LONG).show();
                HttpClient httpClient = new DefaultHttpClient(); //Use this instead
                try {
                    HttpPost request = new HttpPost("http://83.212.98.110:8000/");
                    StringEntity params = new StringEntity(values.toJson());
                    request.setHeader("Accept", "application/json");
                    request.setHeader("Content-type", "application/json");
                    request.setEntity(params);
                    HttpResponse response = httpClient.execute(request);
                } catch (Exception ex) {
                    System.out.println("Something went horribly wrong " + ex.getMessage());
                }
            }
        });


        sysImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent symptoms = new Intent();
                symptoms.setClass(getApplicationContext(), SymptomsActivity.class);
                startActivityForResult(symptoms, 1);

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (1): {
                if (resultCode == Activity.RESULT_OK) {
                    List<Symptoms> symptomList = new ArrayList<Symptoms>();
                    symptomList= (ArrayList<Symptoms>)data.getSerializableExtra("USSYMPTOMS");
//                    System.out.println("GYRUSAAAAAAAAAAAAA");
                    for (Symptoms s :symptomList)
                    {
  //                      System.out.println(s.getName()+"------ >"+s.getRate());
                    }
                }
                break;
            }
        }
    }


    private void registerSensorListeners() {
        mSensorManager.registerListener(pressureSensorListener, mPressure, SensorManager.SENSOR_DELAY_FASTEST);
        mSensorManager.registerListener(lightSensorListener, mLux, SensorManager.SENSOR_DELAY_FASTEST);
        mSensorManager.registerListener(humiditySensorListener, mHumidity, SensorManager.SENSOR_DELAY_FASTEST);
        mSensorManager.registerListener(tempratureSensorListener, mTemprature, SensorManager.SENSOR_DELAY_FASTEST);
    }

    private void unregisterSensorListeners() {
        mSensorManager.unregisterListener(pressureSensorListener);
        mSensorManager.unregisterListener(lightSensorListener);
        mSensorManager.unregisterListener(humiditySensorListener);
    }


    private String symptomsToJSON(ArrayList<Symptoms> sy) {
        Location loc = getLastBestLocation();
        latitude = String.valueOf(loc.getLatitude());
        longitude = String.valueOf(loc.getLongitude());
        String generatedJson = "";
        String tmp = "";
        for (Symptoms s : sy) {
            tmp += "\"" + s.getName() + "\":{\n" +
                    "\"measurements\":{" +
                    "\"value\":\"" + s.getRate() + "\"\n},";
        }
        if (!sy.isEmpty()) {
            generatedJson = "[\n" +
                    "{\n" +
                    "\"type\":\"Feature\",\n" +
                    "\"properties\":{\n" +
                    tmp +
                    "\"geometry\":{\n" +
                    "\"type\":\"Point\",\n" +
                    "\"coordinates\":[\n" +
                    "" + latitude + ",\n" +
                    "" + longitude + "\n" +
                    "]\n" +

                    "}\n}\n]";
            ;
        }
        return generatedJson;
    }

    protected void onResume() {
        super.onResume();
        registerSensorListeners();
    }

    protected void onPause() {
        super.onPause();
        unregisterSensorListeners();
    }

    private Location getLastBestLocation() {
        Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Location locationNet = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        long gpsLocationTime = 0;
        if (null != locationGPS) {
            gpsLocationTime = locationGPS.getTime();
        }

        long NetLocationTime = 0;
        if (null != locationNet) {
            NetLocationTime = locationNet.getTime();
        }

        if (0 < gpsLocationTime - NetLocationTime) {
            return locationGPS;
        } else {
            return locationNet;
        }
    }


}
