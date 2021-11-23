package com.example.komik_welly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class UpdateKomikActivity extends AppCompatActivity {

    EditText edt_update_kode, edt_update_judul, edt_update_tipe;
    Button btn_update_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_komik);

        edt_update_kode = findViewById(R.id.edt_update_kode);
        edt_update_judul = findViewById(R.id.edt_update_judul);
        edt_update_tipe = findViewById(R.id.edt_update_tipe);
        btn_update_data = findViewById(R.id.btn_update_data);
        Intent getdata = getIntent();
        Bundle b = getdata.getExtras();
        String update_kodebuku =(String) b.get("update_kodebuku");
        String update_judul =(String) b.get("update_judul");
        String update_tipe =(String) b.get("update_tipe");
        String update_cover =(String) b.get("update_cover");

        edt_update_kode.setText(update_kodebuku);
        edt_update_judul.setText(update_judul);
        edt_update_tipe.setText(update_tipe);

        btn_update_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String get_update_judul = edt_update_judul.getText().toString().trim();
                String get_update_tipe = edt_update_tipe.getText().toString().trim();
                update_komik(update_kodebuku,get_update_judul, get_update_tipe);
            }
        });

    }

    private void update_komik(String update_kodebuku, String get_update_judul,  String get_update_tipe) {
        String URI = "http://192.168.0.101/server-komik/?komik=update";
        RequestQueue queue          = Volley.newRequestQueue(UpdateKomikActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(UpdateKomikActivity.this,response.toString(),Toast.LENGTH_SHORT).show();
                if(response.toString().equals("data_berhasil_diupdate")){
                    Intent i_main = new Intent(UpdateKomikActivity.this, MainActivity.class);
                    startActivity(i_main);
                    finish();
                }else{
                    Toast.makeText(UpdateKomikActivity.this,response.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UpdateKomikActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();

                params.put("kode_buku", update_kodebuku);
                params.put("get_update_judul", get_update_judul);
                params.put("get_update_tipe", get_update_tipe);

                return params;
            }

        };

        queue.add(stringRequest);
    }
}