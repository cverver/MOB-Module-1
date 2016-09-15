package org.example.module1.app;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
        db.execSQL("CREATE TABLE IF NOT EXISTS Hyperlinks(ID INTEGER PRIMARY KEY AUTOINCREMENT, URL TEXT, Description TEXT, Category TEXT, Timestamp TEXT)");
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
        db.execSQL("INSERT INTO Hyperlinks(URL,Description,Category,Timestamp) VALUES(?,?,?,?)",new Object[]{h.URL, h.Description, h.Category, h.Timestamp});
        return true;
    }

    public Boolean onModifyHyperlink(Hyperlink h) {
        //TODO
        return true;
    }

    public Boolean onRemoveHyperlink(Hyperlink h) {
        //TODO
        return true;
    }

    public Hyperlink[] onListHyperlinks() {
        //TODO
        return new Hyperlink[]{};
    }

    public void onOpenHyperlink(Hyperlink h) {
        //TODO
    }
}