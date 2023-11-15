package Zrayouil.controle_project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private List<Empolye> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycleview);

        recyclerView = findViewById(R.id.recycleView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Initialize your data list
        items = new ArrayList<>();

        // Initialize your adapter
        adapter = new MyAdapter(items);
        recyclerView.setAdapter(adapter);

        loadData();
    }

    private void loadData() {
        String url = "http://192.168.35.182:8083/api/v1/employe";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String nom = jsonObject.getString("nom");
                        String prenom = jsonObject.getString("prenom");
                        String dateNaissance = jsonObject.getString("dateNaissance");

                        // Get the nested service object
                        JSONObject serviceObject = jsonObject.getJSONObject("service");
                        String serviceName = serviceObject.getString("nom");

                        String image = jsonObject.getString("photo");
                        items.add(new Empolye(nom, prenom, dateNaissance, serviceName, image));
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }

                // Notify the adapter that the data has changed
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error response
            }
        });

        requestQueue.add(jsonArrayRequest);
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        private List<Empolye> items;

        public MyAdapter(List<Empolye> items) {
            this.items = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Empolye item = items.get(position);

            holder.nomTextView.setText(item.getNom());
            holder.prenomTextView.setText(item.getPrenom());
            holder.dateNaissanceTextView.setText((CharSequence) item.getDate());
            holder.serviceTextView.setText((CharSequence) item.getService());

            // Set other TextViews or ImageViews as needed
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView nomTextView;
            public TextView prenomTextView;
            public TextView dateNaissanceTextView;
            public TextView serviceTextView;
            public ImageView imageView;

            public ViewHolder(View view) {
                super(view);
                nomTextView = view.findViewById(R.id.nom);
                prenomTextView = view.findViewById(R.id.prenom);
                dateNaissanceTextView = view.findViewById(R.id.dateDeNaissance);
                serviceTextView = view.findViewById(R.id.service);
                imageView = view.findViewById(R.id.image);
            }
        }
    }
}
