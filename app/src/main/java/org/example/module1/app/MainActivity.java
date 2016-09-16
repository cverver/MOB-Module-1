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
        if (onListHyperlinks().length == 0) {
            Toast.makeText(this, getString(R.string.no_hyperlinks), Toast.LENGTH_LONG).show();
            return;
        }
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fl, new ModifyFragment());
        ft.commit();
    }

    public void onRemoveNavigation() {
        if (onListHyperlinks().length == 0) {
            Toast.makeText(this, getString(R.string.no_hyperlinks), Toast.LENGTH_LONG).show();
            return;
        }
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fl, new RemoveFragment());
        ft.commit();
    }

    public void onListNavigation() {
        if (onListHyperlinks().length == 0) {
            Toast.makeText(this, getString(R.string.no_hyperlinks), Toast.LENGTH_LONG).show();
            return;
        }
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
        db = openOrCreateDatabase("Hyperlinks", MODE_PRIVATE, null);
        final Cursor c = db.query("Hyperlinks", new String[]{"ID", "URL", "Description", "Category", "Timestamp"}, "", new String[]{}, "", "", "ID DESC");
        Hyperlink[] h = new Hyperlink[c.getCount()];
        for (int i = 0; c.moveToNext(); i++) {
            h[i] = new Hyperlink() {{
                ID = c.getInt(c.getColumnIndex("ID"));
                URL = c.getString(c.getColumnIndex("URL"));
                Description = c.getString(c.getColumnIndex("Description"));
                Category = c.getInt(c.getColumnIndex("Category"));
                Timestamp = c.getString(c.getColumnIndex("Timestamp"));
            }};
        }
        db.close();
        return h;
    }

    public void onOpenHyperlink(Hyperlink h) {
        //TODO
    }
}