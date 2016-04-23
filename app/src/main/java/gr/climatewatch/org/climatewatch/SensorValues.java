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
    private String generatedJson;
    private String timestamp;

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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String toJson() {
        generatedJson = "[" +
                "{" +
                "\"type\":\"Feature\"," +
                "\"temperature\":{" +
                "\"temperature\":\"" + temprature + "\"," +
                "\"timestamp\":\"" + timestamp + "\"" +
                "}," +
                "{" +
                "\"humidity\":{" +
                "\"humidity\":\"" + humidity + "\"," +
                "\"timestamp\":\"" + timestamp + "\"" +
                "}," +
                "{" +
                "\"lux\":{" +
                "\"lux\":\"" + lux + "\"," +
                "\"timestamp\":\"" + timestamp + "\"" +
                "}," +
                "{" +
                "\"pressure\":{" +
                "\"pressure\":\"" + pressure + "\"," +
                "\"timestamp\":\"" + timestamp + "\"" +
                "}," +
                "\"geometry\":{\n" +
                "\"type\":\"Point\",\n" +
                "\"coordinates\":[\n" +
                "" + latitude + ",\n" +
                "" + longitude + "\n" +
                "]\n" +
                "}\n" +
                "}}]";
        return generatedJson;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SensorValues)) return false;

        SensorValues that = (SensorValues) o;

        if (humidity != null ? !humidity.equals(that.humidity) : that.humidity != null)
            return false;
        if (lux != null ? !lux.equals(that.lux) : that.lux != null) return false;
        if (pressure != null ? !pressure.equals(that.pressure) : that.pressure != null)
            return false;
        if (latitude != null ? !latitude.equals(that.latitude) : that.latitude != null)
            return false;
        if (longitude != null ? !longitude.equals(that.longitude) : that.longitude != null)
            return false;
        if (temprature != null ? !temprature.equals(that.temprature) : that.temprature != null)
            return false;
        if (generatedJson != null ? !generatedJson.equals(that.generatedJson) : that.generatedJson != null)
            return false;
        return timestamp != null ? timestamp.equals(that.timestamp) : that.timestamp == null;

    }

    @Override
    public int hashCode() {
        int result = humidity != null ? humidity.hashCode() : 0;
        result = 31 * result + (lux != null ? lux.hashCode() : 0);
        result = 31 * result + (pressure != null ? pressure.hashCode() : 0);
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        result = 31 * result + (temprature != null ? temprature.hashCode() : 0);
        result = 31 * result + (generatedJson != null ? generatedJson.hashCode() : 0);
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
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
                ", generatedJson='" + generatedJson + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
