package objects;

import android.text.format.DateFormat;

public class Record {
    private String name = "";
    private String date = "";
    private double latitude;
    private double longitude;
    private int score = 0;

    public Record() { }

    public Record(String name, double latitude, double longitude, int score) {
        this.name = name;
        this.setDate();
        this.latitude = latitude;
        this.longitude = longitude;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public int getScore() {
        return score;
    }

    private void setDate() {
        this.date = DateFormat.format("dd.MM.yy HH:mm:ss",System.currentTimeMillis()).toString();
    }

    @Override
    public String toString() {
        return "Record{" +
                "name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", location= (" + getLatitude() + ", " + getLongitude() +
                "), score=" + score +
                '}'+'\n';
    }
}
