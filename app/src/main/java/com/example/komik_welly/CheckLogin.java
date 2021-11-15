package com.example.komik_welly;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class CheckLogin {
    Context context;

    public CheckLogin(Context context) {
        this.context = context;
    }

    public void check_login(String get_input_username, String get_input_password) {
        String URI = "http://192.168.91.1/server-komik/?komik=login";
        RequestQueue queue          = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.toString().equals("user_ditemukan")){
                    session_login();
                    Intent i = new Intent(context,MainActivity.class);
                    context.startActivity(i);
                }else if(response.toString().equals("user_tidak_ditemukan")){
                    Toast.makeText(context,response.toString(),Toast.LENGTH_SHORT).show();
                }
                }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();

                params.put("username", get_input_username);
                params.put("password", get_input_password);

                return params;
            }

        };

        queue.add(stringRequest);
    }
    private void session_login() {

        SharedPreferences sharedpreferences;
        String sess_created_login      = "session_created_login";
        String session_create_login    = "create_login";
        sharedpreferences = context.getSharedPreferences(sess_created_login, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(session_create_login, "user_sudah_login");
        editor.clear();
        editor.apply();

    }

}
