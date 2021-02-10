package com.example.profil.ui.slideshow;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.profil.JSONParser;
import com.example.profil.Profil;
import com.example.profil.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    EditText ps,nom,prenom;
    Button val,init;
    ArrayList<Profil> data =new ArrayList<Profil>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        ps = root.findViewById(R.id.edps);
        nom = root.findViewById(R.id.ednom);
        prenom = root.findViewById(R.id.edprenom);
        val = root.findViewById(R.id.btnval);
        init = root.findViewById(R.id.btnInit);
        init.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  startActivity(new Intent(SlideshowFragment.this, SlideshowFragment.class));
                ps.setText("");
                nom.setText("");
                prenom.setText("");
            }
        });


        val.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String n = nom.getText().toString();
                String p = prenom.getText().toString();
                String pse = ps.getText().toString();
                if ((!pse.isEmpty()) && (!n.isEmpty()) && (!p.isEmpty())) {
                    String ip = "192.168.43.34";
                    RequestQueue ExampleRequestQueue = Volley.newRequestQueue(SlideshowFragment.this.getContext());
                    String url = "http://" + ip + "/dashboard/servicephp/add_user.php?add_user.php?pseudo=" + pse + "nom=" + n + "prenom=" + p;
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(SlideshowFragment.this.getContext(), "", Toast.LENGTH_SHORT).show();
                        }
                    },

                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(SlideshowFragment.this.getContext(), "", Toast.LENGTH_SHORT).show();

                                }
                            });
                    ExampleRequestQueue.add(stringRequest);

                }



            }
        });
        return root;

    }
}
