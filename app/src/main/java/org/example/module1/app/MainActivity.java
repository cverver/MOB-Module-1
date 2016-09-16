package org.example.module1.app;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements MainFragment.Interface, AddFragment.Interface, ModifyFragment.Interface, RemoveFragment.Interface, ListFragment.Interface {

    private FragmentManager fm;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fm = getFragmentManager();
        if (savedInstanceState == null) {
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.fl, new MainFragment());
            ft.commit();
        }
        db = openOrCreateDatabase("Hyperlinks", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS Hyperlinks(ID INTEGER PRIMARY KEY AUTOINCREMENT, URL TEXT, Description TEXT, Category INT, Timestamp TEXT)");
        db.close();
    }


    @Override
    public void onBackPressed() {
        if (fm.findFragmentById(R.id.fl).getClass() == MainFragment.class)
            super.onBackPressed();
        else {
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fl, new MainFragment());
            ft.commit();
        }
    }

    public void onAddNavigation() {
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fl, new AddFragment());
        ft.commit();
    }

    public void onModifyNavigation() {
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fl, new ModifyFragment());
        ft.commit();
    }

    public void onRemoveNavigation() {
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fl, new RemoveFragment());
        ft.commit();
    }

    public void onListNavigation() {
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fl, new ListFragment());
        ft.commit();
    }

    public Boolean onAddHyperlink(Hyperlink h) {
        db = openOrCreateDatabase("Hyperlinks", MODE_PRIVATE, null);
        db.execSQL("INSERT INTO Hyperlinks(URL,Description,Category,Timestamp) VALUES(?,?,?,?)", new Object[]{h.URL, h.Description, h.Category, h.Timestamp});
        db.close();
        return true;
    }

    public Boolean onModifyHyperlink(Hyperlink h) {
        db = openOrCreateDatabase("Hyperlinks", MODE_PRIVATE, null);
        Cursor c = db.query("Hyperlinks", new String[]{"ID"}, "ID = ?", new String[]{h.ID.toString()}, "", "", "");
        if (c.getCount() == 1) {
            db.execSQL("UPDATE Hyperlinks SET URL = ?, Description = ?, Category = ? WHERE ID = ?", new Object[]{h.URL, h.Description, h.Category, h.ID});
            c.close();
            db.close();
            return true;
        } else {
            c.close();
            db.close();
            return false;
        }
    }

    public Boolean onRemoveHyperlink(Hyperlink h) {
        db = openOrCreateDatabase("Hyperlinks", MODE_PRIVATE, null);
        Cursor c = db.query("Hyperlinks", new String[]{"ID"}, "ID = ?", new String[]{h.ID.toString()}, "", "", "");
        if (c.getCount() == 1) {
            db.execSQL("DELETE FROM Hyperlinks WHERE ID = ?", new Object[]{h.ID});
            c.close();
            db.close();
            return true;
        } else {
            c.close();
            db.close();
            return false;
        }
    }

    @Override
    public Boolean onREMOVE_ALLHyperlinks() {
        db = openOrCreateDatabase("Hyperlinks", MODE_PRIVATE, null);
        db.execSQL("DELETE FROM Hyperlinks");
        Cursor c = db.query("Hyperlinks", new String[]{"ID"}, "", new String[]{}, "", "", "");
        if (c.getCount() == 0) {
            c.close();
            db.close();
            return true;
        } else {
            c.close();
            db.close();
            return false;
        }
    }

    public Hyperlink[] onListHyperlinks() {
        //TODO MAKE SOFT CODED
        return new Hyperlink[]{new Hyperlink() {{
            ID = 9;
            URL = "http://google.com";
            Description = "Google";
            Category = 4; //Other
        }}, new Hyperlink() {{
            ID = 10;
            URL = "http://plaza2.rocvantwente.nl";
            Description = "Plaza";
            Category = 4; //Other
        }}, new Hyperlink() {{
            ID = 11;
            URL = "http://stackoverflow.com";
            Description = "SO";
            Category = 0; //Text
        }}};
    }

    public void onOpenHyperlink(Hyperlink h) {
        //TODO
    }
}