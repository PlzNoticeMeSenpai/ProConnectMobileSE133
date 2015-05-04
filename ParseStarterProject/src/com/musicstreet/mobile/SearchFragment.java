package com.musicstreet.mobile;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.parse.ParseUser;

import java.util.zip.Inflater;

/**
 * Created by Owner on 5/1/2015.
 */
public class SearchFragment extends Fragment{

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.search, container, false);

        final ParseUser p = ParseUser.getCurrentUser();
        final Button searchButton = (Button) rootView.findViewById(R.id.searchButton);
        final EditText searchTextField = (EditText)rootView.findViewById(R.id.searchTextField);
        final Spinner spinner = (Spinner) rootView.findViewById(R.id.searchSpinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(rootView.getContext(),
                R.array.searchableItems, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        if(!p.getBoolean("premiumSubscription")) {
            spinner.setEnabled(false);
            rootView.findViewById(R.id.premOnlyText).setVisibility(View.VISIBLE);
        }

        searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                QueryFragment q = new QueryFragment("Media", spinner.getSelectedItem().toString(), "user", searchTextField.getText().toString(), new String[0]);
                FragmentTransaction t = getFragmentManager().beginTransaction();
                t.replace(R.id.container, q);
                t.commit();
            }
        });

        return rootView;
    }
}
