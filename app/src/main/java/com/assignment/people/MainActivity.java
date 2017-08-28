package com.assignment.people;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.assignment.people.database.PeopleManagement;
import com.assignment.people.model.People;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button addPeopleButton;
    private Button editPeopleButton;
    private Button deletePeopleButton;
    private Button viewAllPeopleButton;
    private PeopleManagement peopleOps;
    List<People> peoples;
    public static final String EXTRA_ID = MainActivity.class.getSimpleName()+ ".id";
    public static final String EXTRA_ADD_UPDATE = MainActivity.class.getSimpleName()+".add_update";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addPeopleButton = (Button) findViewById(R.id.btn_add_people);
        editPeopleButton = (Button) findViewById(R.id.btn_edit_people);
        deletePeopleButton = (Button) findViewById(R.id.btn_remove_people);
        viewAllPeopleButton = (Button)findViewById(R.id.btn_view_peoples);

        addPeopleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,AddUpdatePeople.class);
                i.putExtra(EXTRA_ADD_UPDATE, "Add");
                startActivity(i);
            }
        });

        editPeopleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                peopleOps = new PeopleManagement(MainActivity.this);
                peopleOps.open();
                peoples = peopleOps.getAllPeoples();
                peopleOps.close();

                if (peoples.isEmpty())
                {
                    Toast t = Toast.makeText(MainActivity.this, "Your current database is empty!", Toast.LENGTH_SHORT);
                    t.show();
                }
                else
                {
                    getIdAndUpdatePpl();
                }
            }
        });

        deletePeopleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                peopleOps = new PeopleManagement(MainActivity.this);
                peopleOps.open();
                peoples = peopleOps.getAllPeoples();
                peopleOps.close();

                if (peoples.isEmpty())
                {
                    Toast t = Toast.makeText(MainActivity.this, "Your current database is empty!", Toast.LENGTH_SHORT);
                    t.show();
                }
                else
                {
                    getIdAndRemovePpl();
                }
            }
        });
        viewAllPeopleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ViewAllPeoples.class);
                startActivity(i);
            }
        });
    }

    public void getIdAndUpdatePpl(){
        LayoutInflater li = LayoutInflater.from(this);
        View getEmpIdView = li.inflate(R.layout.dialog_get_ppl_id, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        // set dialog_get_emp_id.xml to alertdialog builder
        alertDialogBuilder.setView(getEmpIdView);

        final EditText userInput = (EditText) getEmpIdView.findViewById(R.id.editTextDialogUserInput);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // get user input and set it to result
                        // edit text
                        if (userInput.getText().toString() != null && userInput.getText().toString().trim().length() == 0)
                        {
                            Toast t = Toast.makeText(MainActivity.this, "Please enter input properly!", Toast.LENGTH_SHORT);
                            t.show();
                        }else
                        {
                            Intent i = new Intent(MainActivity.this, AddUpdatePeople.class);
                            i.putExtra(EXTRA_ADD_UPDATE, "Update");
                            i.putExtra(EXTRA_ID, Long.parseLong(userInput.getText().toString()));
                            startActivity(i);
                        }

                    }
                }).create()
                .show();

    }


    public void getIdAndRemovePpl(){
        LayoutInflater li = LayoutInflater.from(this);
        View getEmpIdView = li.inflate(R.layout.dialog_get_ppl_id, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        // set dialog_get_people_id.xml to alertdialog builder
        alertDialogBuilder.setView(getEmpIdView);
        final EditText userInput = (EditText) getEmpIdView.findViewById(R.id.editTextDialogUserInput);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // get user input and set it to result
                        // edit text
                        if (userInput.getText().toString() != null && userInput.getText().toString().trim().length() == 0) {
                            Toast t = Toast.makeText(MainActivity.this, "Please enter input properly!", Toast.LENGTH_SHORT);
                            t.show();
                        }else
                        {
                            peopleOps = new PeopleManagement(MainActivity.this);
                            peopleOps.removePeople(Integer.parseInt(userInput.getText().toString()));
                            Toast t = Toast.makeText(MainActivity.this, "People removed successfully!", Toast.LENGTH_SHORT);
                            t.show();
                        }
                    }
                }).create()
                .show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        peopleOps = new PeopleManagement(MainActivity.this);
        peopleOps.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        peopleOps.close();

    }

}
