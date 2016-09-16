package org.example.module1.app;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

public class ModifyFragment extends Fragment {

    private Interface mainActivitiy;

    private Button save;

    private Spinner hyperlinkSpinner;
    private EditText url;
    private EditText description;
    private Spinner category;

    private Hyperlink[] hyperlinks;
    private Hyperlink sel;

    public ModifyFragment() {
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(getString(R.string.modify));
        hyperlinkSpinner = (Spinner) getView().findViewById(R.id.hyperlink);
        url = (EditText) getView().findViewById(R.id.url);
        description = (EditText) getView().findViewById(R.id.description);
        category = (Spinner) getView().findViewById(R.id.category);
        save = (Button) getView().findViewById(R.id.save);
        hyperlinks = mainActivitiy.onListHyperlinks();

        hyperlinkSpinner.setAdapter(new ArrayAdapter(getActivity(), R.layout.support_simple_spinner_dropdown_item, hyperlinks));
        hyperlinkSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sel = (Hyperlink) hyperlinkSpinner.getSelectedItem();
                url.setText(sel.URL);
                description.setText(sel.Description);
                category.setSelection(sel.Category);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                sel = null;
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sel.URL = url.getText().toString();
                sel.Description = description.getText().toString();
                sel.Category = category.getSelectedItemPosition();
                if (mainActivitiy.onModifyHyperlink(sel)) {
                    hyperlinkSpinner.setSelection(0);
                    Toast.makeText(getActivity(), getString(R.string.save_successful), Toast.LENGTH_SHORT).show();
                } else Toast.makeText(getActivity(), getString(R.string.save_unsuccessful), Toast.LENGTH_LONG).show();
            }
        });

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
