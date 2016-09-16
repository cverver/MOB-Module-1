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
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fl, new MainFragment());
        ft.commit();
        setTitle(getString(R.string.main));
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
            setTitle(getString(R.string.main));
        }
    }

    public void onAddNavigation() {
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fl, new AddFragment());
        ft.commit();
        setTitle(getString(R.string.add));
    }

    public void onModifyNavigation() {
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fl, new ModifyFragment());
        ft.commit();
        setTitle(getString(R.string.modify));
    }

    public void onRemoveNavigation() {
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fl, new RemoveFragment());
        ft.commit();
        setTitle(getString(R.string.remove));
    }

    public void onListNavigation() {
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fl, new ListFragment());
        ft.commit();
        setTitle(getString(R.string.list));
    }

    public Boolean onAddHyperlink(Hyperlink h) {
        db = SQLiteDatabase.openDatabase("Hyperlinks", null, MODE_PRIVATE);
        db.execSQL("INSERT INTO Hyperlinks(URL,Description,Category,Timestamp) VALUES(?,?,?,?)", new Object[]{h.URL, h.Description, h.Category, h.Timestamp});
        db.close();
        return true;
    }

    public Boolean onModifyHyperlink(Hyperlink h) {
        //TODO
        db = SQLiteDatabase.openDatabase("Hyperlinks", null, MODE_PRIVATE);
        Cursor c = db.query("Hyperlinks", new String[]{"ID"}, "ID = ?", new String[]{h.ID.toString()}, "", "", "");
        if (c.getCount() == 1) {
            db.execSQL("UPDATE Hyperlinks SET URL = ?, Description = ?, Category = ?, Timestamp = ? WHERE ID = ?", new Object[]{h.URL, h.Description, h.Category, h.Timestamp, h.ID});
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
        //TODO
        db = SQLiteDatabase.openDatabase("Hyperlinks", null, MODE_PRIVATE);
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

    public Hyperlink[] onListHyperlinks() {
        //TODO MAKE SOFT CODED
        return new Hyperlink[]{new Hyperlink() {{
            ID = 1;
            URL = "http://google.com";
            Description = "Google";
            Category = "Other";
        }}, new Hyperlink() {{
            ID = 2;
            URL = "http://plaza2.rocvantwente.nl";
            Description = "Plaza";
            Category = "Other";
        }}, new Hyperlink() {{
            ID = 3;
            URL = "http://stackoverflow.com";
            Description = "SO";
            Category = "Text";
        }}};
    }

    public void onOpenHyperlink(Hyperlink h) {
        //TODO
    }
}