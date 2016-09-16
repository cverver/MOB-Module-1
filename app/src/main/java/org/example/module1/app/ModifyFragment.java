package org.example.module1.app;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class ModifyFragment extends Fragment {

    private Interface mainActivitiy;

    private Button save;

    private Spinner hyperlink;
    private EditText url;
    private EditText description;
    private Spinner category;

    private Hyperlink[] hyperlinks;

    public ModifyFragment() {
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //TODO: stuff
//        hyperlinks=mainActivitiy.onListHyperlinks();
        hyperlinks = new Hyperlink[]{new Hyperlink() {{
            ID = 1;
            URL = "http://plaza2.rocvantwente.nl";
            Description = "School plaza";
            Category = "Other";
        }}, new Hyperlink() {{
            ID = 2;
            URL = "http://google.com";
            Description = "Google";
            Category = "Other";
        }}};
        Integer[] is = new Integer[]{};
        for (Hyperlink h : hyperlinks) {
            is[is.length] = h.ID;
        }
        //TODO fill spinner with Hyperlinks
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_modify, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mainActivitiy = (Interface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainActivitiy = null;
    }


    public interface Interface {
        Hyperlink[] onListHyperlinks();

        Boolean onModifyHyperlink(Hyperlink h);
    }

}
