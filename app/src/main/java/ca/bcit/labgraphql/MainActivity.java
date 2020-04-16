package ca.bcit.labgraphql;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText query;
    Button searchBtn;
    TextView result;

    EditText stName;
    EditText stNum;
    Spinner spinnerSet;
    Button addBtn;

    DatabaseReference dbStudents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        // create reference locally
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(null, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(null, "Failed to read value.", error.toException());
            }
        });

        // create handles on elements
        query = findViewById(R.id.item_edit_text);
        searchBtn = findViewById(R.id.search_btn);
        result = findViewById(R.id.display_result);

        stName = findViewById(R.id.st_name);
        stNum = findViewById(R.id.st_num);
        spinnerSet = findViewById(R.id.set_spinner);
        addBtn = findViewById(R.id.add_btn);


        dbStudents = FirebaseDatabase.getInstance().getReference("students");

    }

    /**
     * Displays the data corresponding to the student name entered.
     * @param view
     */
    public void onClickDisplay(View view) {

        // take studentId to query database
        String nameToQuery = query.getText().toString().trim();

        Query query = dbStudents.orderByChild("studentName").equalTo(nameToQuery);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //String data = dataSnapshot.getValue().toString();
                //System.out.println(data);
                //result.setText(data);
                result.setText("");

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Student student = snapshot.getValue(Student.class);
                    if (student != null) {
                        result.append("Name: " + student.getStudentName() + "\n");
                        result.append("Serial: " + student.getStudentId() + "\n");
                        result.append("Set: " + student.getStudentSet());
                    } else {
                        result.setText("Replicant Not Found.");
                    }
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * Adds a student to the database.
     * @param view
     */
    public void onClickAdd(View view) {

        // input
        String name = stName.getText().toString().trim();
        String num = stNum.getText().toString().trim();
        String set = spinnerSet.getSelectedItem().toString();

        // write to firebase if name not empty
        if (!TextUtils.isEmpty(name)) {

            String id = dbStudents.push().getKey();
            Student student = new Student(id, name, num, set);
            dbStudents.child(id).setValue(student);

            Toast.makeText(this, "Student Added", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this, "Please Enter a name", Toast.LENGTH_LONG).show();
        }


    }

}
