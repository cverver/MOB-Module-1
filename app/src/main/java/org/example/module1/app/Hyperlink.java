package org.example.module1.app;

import android.text.format.Time;

public class Hyperlink {
    public Integer ID;
    public String URL;
    public String Description;
    public String Category;
    public String Timestamp;

    public Hyperlink() {
        Time time = new Time();
        time.setToNow();
        this.Timestamp = time.format("%d/%m/%Y %T");
    }
}
