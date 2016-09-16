package org.example.module1.app;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

public class RemoveFragment extends Fragment {

    private Interface mainActivitiy;

    private Button delete;

    private Spinner hyperlinkSpinner;
    private TextView url;
    private TextView description;
    private TextView category;

    private Hyperlink[] hyperlinks;
    private Hyperlink sel;

    public RemoveFragment() {
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //TODO: stuff
        delete = (Button) getView().findViewById(R.id.delete);
        hyperlinkSpinner = (Spinner) getView().findViewById(R.id.hyperlink);
        url = (TextView) getView().findViewById(R.id.url);
        description = (TextView) getView().findViewById(R.id.description);
        category = (TextView) getView().findViewById(R.id.category);

        hyperlinks = mainActivitiy.onListHyperlinks();

        hyperlinkSpinner.setAdapter(new ArrayAdapter(getActivity(), R.layout.support_simple_spinner_dropdown_item, hyperlinks));
        hyperlinkSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sel = (Hyperlink) hyperlinkSpinner.getSelectedItem();
                url.setText(sel.URL);
                description.setText(sel.Description);
                category.setText(getResources().getStringArray(R.array.cats)[sel.Category]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                sel = null;
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mainActivitiy.onRemoveHyperlink(sel)) {
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
        return inflater.inflate(R.layout.fragment_remove, container, false);
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

        Boolean onRemoveHyperlink(Hyperlink h);
    }

}
