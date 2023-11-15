package Zrayouil.controle_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    Button button;
    Button buttonAffich;
    String insertUrl = "http://192.168.35.182:8083/api/v1/employe";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.bnAdd);
        buttonAffich = findViewById(R.id.bnEdit);

        TextView nom = findViewById(R.id.nom);
        TextView prenom = findViewById(R.id.prenom);
        Spinner service = findViewById(R.id.service);
        TextView date1 = findViewById(R.id.Naissance);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Gestion des Employées");
        toolbar.setTitleTextColor(getResources().getColor(R.color.black));
        setSupportActionBar(toolbar);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get data from input fields
                String employeeNom = nom.getText().toString();
                String employeePrenom = prenom.getText().toString();
                String employeeDateNaissance = date1.getText().toString();
                String selectedService = service.getSelectedItem().toString();

                // Convert date string to Date object
                Date dateOfBirth = null;
                try {
                    dateOfBirth = new SimpleDateFormat("yyyy-MM-dd").parse(employeeDateNaissance);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                // Create Empolye object
                Empolye employee = new Empolye();
                employee.setNom(employeeNom);
                employee.setPrenom(employeePrenom);
                employee.setDate(dateOfBirth);
                // Set other properties of the employee object

                // Send POST request to add employee
                addEmployee(employee);
            }
        });

        buttonAffich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });
    }

    private void addEmployee(Empolye employee) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("nom", employee.getNom());
            requestBody.put("prenom", employee.getPrenom());
            requestBody.put("dateNaissance", new SimpleDateFormat("yyyy-MM-dd").format(employee.getDate()));
            // Add other properties to the request body

            // If service is an object, you might need to include its id instead of the name
            // requestBody.put("service", employee.getService().getId());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, insertUrl, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle successful response
                        Toast.makeText(MainActivity.this, "Employee added successfully", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error response
                        Toast.makeText(MainActivity.this, "Error adding employee", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(request);
    }
}
