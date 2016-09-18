package org.example.module1.app;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.widget.Toast;

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
        if (!Patterns.WEB_URL.matcher(h.URL).matches() || h.Description.length() == 0) return false;
        db = openOrCreateDatabase("Hyperlinks", MODE_PRIVATE, null);
        db.execSQL("INSERT INTO Hyperlinks(URL,Description,Category,Timestamp) VALUES(?,?,?,?)", new Object[]{h.URL.startsWith("http://") || h.URL.startsWith("https://") ? h.URL : "http://" + h.URL, h.Description, h.Category, h.Timestamp});
        db.close();
        return true;
    }

    public Boolean onModifyHyperlink(Hyperlink h) {
        if (!Patterns.WEB_URL.matcher(h.URL).matches() || h.Description.length() == 0) return false;
        db = openOrCreateDatabase("Hyperlinks", MODE_PRIVATE, null);
        Cursor c = db.query("Hyperlinks", new String[]{"ID"}, "ID = ?", new String[]{h.ID.toString()}, "", "", "");
        if (c.getCount() == 1) {
            db.execSQL("UPDATE Hyperlinks SET URL = ?, Description = ?, Category = ?, Timestamp = ? WHERE ID = ?", new Object[]{h.URL.startsWith("http://") || h.URL.startsWith("https://") ? h.URL : "http://" + h.URL, h.Description, h.Category, h.Timestamp, h.ID});
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

    public Hyperlink[] onListHyperlinks() { // Robin
        db = openOrCreateDatabase("Hyperlinks", MODE_PRIVATE, null); // Open de database eerst.
        final Cursor c = db.query("Hyperlinks", new String[]{"ID", "URL", "Description", "Category", "Timestamp"}, "", new String[]{}, "", "", "ID DESC"); //Stuur een query naar de Db
        Hyperlink[] h = new Hyperlink[c.getCount()]; //Haal de aantal hyperlinks op
        for (int i = 0; c.moveToNext(); i++) { //schuif de cursor naar de volgende rij
            h[i] = new Hyperlink() {{
                ID = c.getInt(c.getColumnIndex("ID")); //Het id ophalen
                URL = c.getString(c.getColumnIndex("URL")); // De url ophalen
                Description = c.getString(c.getColumnIndex("Description"));
                Category = c.getInt(c.getColumnIndex("Category"));
                Timestamp = c.getString(c.getColumnIndex("Timestamp")); //time.format("%d/%m/%Y %T")
            }};
        }
        c.close(); //Sluit de database zodat het niet open blijft.
        db.close();
        return h;
    }

    public void onOpenHyperlink(Hyperlink h) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(h.URL));
        startActivity(i);
    }
}