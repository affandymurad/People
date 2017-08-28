package com.assignment.people;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.assignment.people.database.PeopleManagement;
import com.assignment.people.model.People;

import java.util.List;

/**
 * Created by affandy on 25/08/2017.
 */

public class ViewAllPeoples extends ListActivity {

    private PeopleManagement peopleOps;
    List<People> peoples;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_people);
        peopleOps = new PeopleManagement(this);
        peopleOps.open();
        peoples = peopleOps.getAllPeoples();
        peopleOps.close();
        ArrayAdapter<People> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, peoples);
        setListAdapter(adapter);
    }

    @Override
    public void onBackPressed()
    {
        finish();
        // code here to show dialog
        super.onBackPressed();  // optional depending on your needs
    }
}