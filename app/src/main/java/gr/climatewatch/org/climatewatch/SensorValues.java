package gr.climatewatch.org.climatewatch;

/**
 * Created by Dimitris on 23/04/16.
 */
public class SensorValues {
    private String humidity;
    private String lux;
    private String pressure;
    private String latitude;
    private String longitude;
    private String temprature;

    public SensorValues(String humidity, String lux, String pressure, String temprature, String latitude, String longitude) {
        this.humidity = humidity;
        this.lux = lux;
        this.pressure = pressure;
        this.temprature = temprature;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getLux() {
        return lux;
    }

    public void setLux(String lux) {
        this.lux = lux;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getTemprature() {
        return temprature;
    }

    public void setTemprature(String temprature) {
        this.temprature = temprature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SensorValues)) return false;

        SensorValues that = (SensorValues) o;

        if (!humidity.equals(that.humidity)) return false;
        if (!lux.equals(that.lux)) return false;
        if (!pressure.equals(that.pressure)) return false;
        if (!latitude.equals(that.latitude)) return false;
        if (!longitude.equals(that.longitude)) return false;
        return temprature.equals(that.temprature);

    }

    @Override
    public int hashCode() {
        int result = humidity.hashCode();
        result = 31 * result + lux.hashCode();
        result = 31 * result + pressure.hashCode();
        result = 31 * result + latitude.hashCode();
        result = 31 * result + longitude.hashCode();
        result = 31 * result + temprature.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "SensorValues{" +
                "humidity='" + humidity + '\'' +
                ", lux='" + lux + '\'' +
                ", pressure='" + pressure + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", temprature='" + temprature + '\'' +
                '}';
    }
}
