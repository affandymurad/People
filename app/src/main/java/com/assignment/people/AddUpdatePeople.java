package com.assignment.people;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.assignment.people.database.PeopleManagement;
import com.assignment.people.model.People;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by affandy on 25/08/2017.
 */

public class AddUpdatePeople extends AppCompatActivity implements DatePickerFragment.DateDialogListener {
    private static final String DIALOG_DATE = "DialogDate";

    private ImageView calendarCreatedImage;
    private ImageView calendarUpdatedImage;
    private EditText nameEditText;
    private EditText descriptionEditText;
    private EditText createdEditText;
    private EditText updatedEditText;
    private Button addUpdateButton;
    private People newPeople;
    private People oldPeople;
    private String mode;
    private long Id;
    private PeopleManagement peopleData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update_people);
        newPeople = new People();
        oldPeople = new People();
        nameEditText = (EditText)findViewById(R.id.et_name);
        descriptionEditText = (EditText)findViewById(R.id.et_description);
        createdEditText = (EditText) findViewById(R.id.et_created_date);
        updatedEditText = (EditText) findViewById(R.id.et_updated_date);
        calendarCreatedImage = (ImageView)findViewById(R.id.image_view_created_date);
        calendarUpdatedImage = (ImageView)findViewById(R.id.image_view_updated_date);
        addUpdateButton = (Button)findViewById(R.id.button_add_update_people);
        peopleData = new PeopleManagement(this);
        peopleData.open();


        mode = getIntent().getStringExtra(MainActivity.EXTRA_ADD_UPDATE);
        if(mode.equals("Update")){
            addUpdateButton.setText("Update People");
            Id = getIntent().getLongExtra(MainActivity.EXTRA_ID,0);
            initializeEmployee(Id);
            createdEditText.setKeyListener(null);
            calendarCreatedImage.setVisibility(View.GONE);
        }
        else
        {
            updatedEditText.setKeyListener(null);
            calendarUpdatedImage.setVisibility(View.GONE);
        }

        calendarCreatedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager manager = getSupportFragmentManager();
                DatePickerFragment dialog = new DatePickerFragment();
                dialog.show(manager, DIALOG_DATE);
            }
        });

        calendarUpdatedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager manager = getSupportFragmentManager();
                DatePickerFragment dialog = new DatePickerFragment();
                dialog.show(manager, DIALOG_DATE);
            }
        });


        addUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mode.equals("Add")) {
                    newPeople.setName(nameEditText.getText().toString());
                    newPeople.setDescription(descriptionEditText.getText().toString());
                    newPeople.setDateCreated(createdEditText.getText().toString());
                    newPeople.setDateUpdated(createdEditText.getText().toString());
                    peopleData.addPeople(newPeople);
                    Toast t = Toast.makeText(AddUpdatePeople.this, "People "+ newPeople.getName() + "has been added successfully !", Toast.LENGTH_SHORT);
                    t.show();
                    finish();
                }else {
                    oldPeople.setName(nameEditText.getText().toString());
                    oldPeople.setDescription(descriptionEditText.getText().toString());
                    oldPeople.setDateCreated(createdEditText.getText().toString());
                    oldPeople.setDateUpdated(updatedEditText.getText().toString());
                    peopleData.updatePeople(oldPeople);
                    Toast t = Toast.makeText(AddUpdatePeople.this, "People "+ oldPeople.getName() + " has been updated successfully !", Toast.LENGTH_SHORT);
                    t.show();
                    finish();

                }


            }
        });


    }

    private void initializeEmployee(long Id) {
        oldPeople = peopleData.getPeople(Id);
        nameEditText.setText(oldPeople.getName());
        descriptionEditText.setText(oldPeople.getDescription());
        createdEditText.setText(oldPeople.getDateCreated());
        updatedEditText.setText(oldPeople.getDateUpdated());
    }


    @Override
    public void onFinishDialog(Date date) {
        if(mode.equals("Add")) {
            createdEditText.setText(formatDate(date));
            updatedEditText.setText(formatDate(date));
        }
        else
        {
            updatedEditText.setText(formatDate(date));
        }

    }

    public String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String theDate = sdf.format(date);
        return theDate;
    }
}
