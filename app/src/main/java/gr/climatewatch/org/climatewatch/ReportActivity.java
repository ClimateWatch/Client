package gr.climatewatch.org.climatewatch;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

public class ReportActivity extends AppCompatActivity implements  SensorEventListener{


    LocationManager locationManager = null;
    private SensorManager mSensorManager;
    private Sensor mPressure;
    private Sensor mHumidity;
    private Sensor mLux;

    SensorEventListener lightSensorEventListener;
    SensorEventListener pressureSensorEventListener;
    SensorEventListener humidityEventListener;
    double humidity;
    double lux;
    double pressure;


            ProgressBar lightMeter;
    String metrisi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

    }

    @Override
    protected void onStart(){
        super.onStart();
        ImageButton posImage = (ImageButton) findViewById(R.id.posBtn);

        locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        Location currentBestLocation = null;

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mPressure = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        mHumidity = mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        mLux = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        lightSensorEventListener =new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                lux = event.values[0];
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        pressureSensorEventListener  = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                pressure = event.values[0];
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        humidityEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                humidity = event.values[0];
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };


        View.OnClickListener btnListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSensorManager.registerListener(pressureSensorEventListener, mPressure, SensorManager.SENSOR_DELAY_NORMAL);
                mSensorManager.registerListener(lightSensorEventListener, mLux, SensorManager.SENSOR_DELAY_NORMAL);
                mSensorManager.registerListener(humidityEventListener, mHumidity, SensorManager.SENSOR_DELAY_NORMAL);

                Location loc = getLastBestLocation();
                Toast.makeText(ReportActivity.this, "Hello Sofia\n"+loc.getAltitude()+"\n"+loc.getLatitude()+","+loc.getLongitude()+"\n"+humidity+"\n"+pressure+"\n"+lux, Toast.LENGTH_SHORT).show();

                mSensorManager.unregisterListener(pressureSensorEventListener);
                mSensorManager.unregisterListener(lightSensorEventListener);
                mSensorManager.unregisterListener(humidityEventListener);

            }
        };

        posImage.setOnClickListener(btnListener);

    }


    protected void onResume() {

        super.onResume();

        mSensorManager.registerListener(pressureSensorEventListener, mPressure, SensorManager.SENSOR_DELAY_FASTEST);
        mSensorManager.registerListener(lightSensorEventListener, mLux, SensorManager.SENSOR_DELAY_FASTEST);
        mSensorManager.registerListener(humidityEventListener, mHumidity, SensorManager.SENSOR_DELAY_FASTEST);

    }

    protected void onPause() {

        super.onPause();

        mSensorManager.unregisterListener(pressureSensorEventListener);
        mSensorManager.unregisterListener(lightSensorEventListener);
        mSensorManager.unregisterListener(humidityEventListener);

    }

    private Location getLastBestLocation() {
        Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Location locationNet = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        long GPSLocationTime = 0;
        if (null != locationGPS) { GPSLocationTime = locationGPS.getTime(); }

        long NetLocationTime = 0;

        if (null != locationNet) {
            NetLocationTime = locationNet.getTime();
        }

        if ( 0 < GPSLocationTime - NetLocationTime ) {
            return locationGPS;
        }
        else {
            return locationNet;
        }
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
