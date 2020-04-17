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

    EditText mvTitle;
    EditText mvLink;
    EditText mvDescription;
    Button addBtn;

    DatabaseReference dbMovies;

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
//        query = findViewById(R.id.item_edit_text);
//        searchBtn = findViewById(R.id.search_btn);
//        result = findViewById(R.id.display_result);

        mvTitle = findViewById(R.id.mv_title);
        mvLink = findViewById(R.id.mv_link);
        mvDescription = findViewById(R.id.description);
        addBtn = findViewById(R.id.add_btn);

        dbMovies = FirebaseDatabase.getInstance().getReference("movies");

    }

    /**
     * Displays the data corresponding to the student name entered.
     * @param view
     */
    public void findMovie(View view) {

        // take studentId to query database
        String titleToQuery = query.getText().toString().trim();

        Query query = dbMovies.orderByChild("movieTitle").equalTo(titleToQuery);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //String data = dataSnapshot.getValue().toString();
                //System.out.println(data);
                //result.setText(data);
                result.setText("");

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Movie movie = snapshot.getValue(Movie.class);
                    if (movie != null) {
                        result.append("Title: " + movie.getMovieTitle() + "\n");
                        result.append("Link: " + movie.getMovieLink() + "\n");
                        result.append("Description: " + movie.getMovieDescription() + "\n");
                    } else {
                        result.append("Movie Not Found.");
                    }
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    /**
     * Adds a movie to the database
     * @param view
     */
    public void addMovieItem(View view) {

        // input
        String title = mvTitle.getText().toString().trim();
        String link = mvLink.getText().toString().trim();
        String description = mvDescription.getText().toString().trim();

        // write to firebase if name not empty
        if (!TextUtils.isEmpty(title)) {

            String id = dbMovies.push().getKey();
            Movie movie = new Movie(title, link, description);
            dbMovies.child(id).setValue(movie);

            Toast.makeText(this, "Movie Added!", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this, "Please Enter a title", Toast.LENGTH_LONG).show();
        }


    }

}
