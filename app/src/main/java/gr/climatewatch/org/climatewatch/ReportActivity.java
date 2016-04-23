package gr.climatewatch.org.climatewatch;

import android.content.Context;
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

import com.google.gson.Gson;

import java.security.Timestamp;
import java.util.Date;

public class ReportActivity extends AppCompatActivity {

    //Graphics
    private ImageButton posImage;


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
    private Gson gson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
    }

    @Override
    protected void onStart() {
        super.onStart();
        gson = new Gson();
        posImage = (ImageButton) findViewById(R.id.posBtn);
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
                Date date = new Date();
                Timestamp timestamp = new Timestamp(date, null);
                System.out.println("Values from class: " + values.toString());
                String s = gson.toJson(values);
                System.out.println("Values from json: " + s);
            }
        });
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
