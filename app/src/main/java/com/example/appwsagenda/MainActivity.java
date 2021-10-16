package com.example.appwsagenda;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText txtId, txtFec, txtAsu, txtAct ;
    Button btnAdd, btnCons ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtId = findViewById(R.id.txtId);
        txtFec = findViewById(R.id.txtFec);
        txtAsu = findViewById(R.id.txtAsu);
        txtAct = findViewById(R.id.txtAct);
        btnAdd = findViewById(R.id.btnAdd);
        btnCons = findViewById(R.id.btnCons);

        btnCons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LeerWS();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdicionarWS();
            }
        });
    }

    //Metodos Ws
    private void LeerWS(){
        //Prueba desde JsonPlaceholder
        //String url = "https://jsonplaceholder.typicode.com/posts/1" ;
        String url = "http://192.168.0.8/wsRest/api/Naca/" + txtId.getText().toString() ;

        StringRequest postRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    // prueba a jsonplaceholder
                    /*
                    txtId.setText(jsonObject.getString("id"));
                    txtFec.setText(jsonObject.getString("userId"));
                    txtAsu.setText(jsonObject.getString("title"));
                    txtAct.setText(jsonObject.getString("body"));
                    */

                    txtId.setText(jsonObject.getString("id_agenda"));
                    txtFec.setText(jsonObject.getString("fecha"));
                    txtAsu.setText(jsonObject.getString("asunto"));
                    txtAct.setText(jsonObject.getString("actividad"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        Volley.newRequestQueue(this).add(postRequest);
    }

    private void AdicionarWS(){
        // Prueba a sitio placeholderjson
        //String url = "https://jsonplaceholder.typicode.com/posts" ;

        String url = "http://192.168.0.8/wsRest/api/Naca" ;
        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MainActivity.this, "Resultado " + response, Toast.LENGTH_LONG).show();
                //Devuelvo el id de la actividad
                txtId.setText(response);
                // si el valor devuelto es json
                /*
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    //Devuelvo el id de la actividad
                    //txtId.setText(jsonObject.getString("id_agenda"));
                    //-----------------------------------------------------
                    Toast.makeText(MainActivity.this, "Resultado " + response, Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                */
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("error", error.getMessage());

            }
        })
                // pasar parametros al post
        {
            protected Map<String, String> getParams(){
                Map<String,String> params = new HashMap<>();
                //Parametros a sitio de pruebas placeholderjson
                /*
                params.put("title", txtAsu.getText().toString());
                params.put("body", txtAct.getText().toString());
                params.put("userId", txtId.getText().toString());
                */
                params.put("fecha", txtFec.getText().toString());
                params.put("asunto", txtAsu.getText().toString());
                params.put("actividad", txtAct.getText().toString());
                return params ;
            }
        }
                ;

        Volley.newRequestQueue(this).add(postRequest);
    }

}