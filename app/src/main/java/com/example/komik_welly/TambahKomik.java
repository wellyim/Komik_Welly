package com.example.komik_welly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class TambahKomik extends AppCompatActivity {
    EditText tambah_kode, tambah_judul, tambah_tipe;
    Button btn_simpan_data_tambah;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_komik);

        tambah_kode = findViewById(R.id.tambah_kode);
        tambah_judul = findViewById(R.id.tambah_judul);
        tambah_tipe = findViewById(R.id.tambah_tipe);
        btn_simpan_data_tambah = findViewById(R.id.btn_simpan_data_tambah);

        btn_simpan_data_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String get_kode_buku = tambah_kode.getText().toString().trim();
                String get_judul_buku = tambah_judul.getText().toString().trim();
                String get_tipe_buku = tambah_tipe.getText().toString().trim();

                if(get_kode_buku.isEmpty()){
                    tambah_kode.setError("Kode tidak boleh kosong!");
                    tambah_kode.requestFocus();
                }else if(get_judul_buku.isEmpty()){
                    tambah_judul.setError("Judul tidak boleh kosong!");
                    tambah_judul.requestFocus();
                }else if(get_tipe_buku.isEmpty()){
                    tambah_tipe.setError("Tipe tidak boleh kosong!");
                    tambah_tipe.requestFocus();
                }else{
                    insert_data_ke_server(get_kode_buku,get_judul_buku,get_tipe_buku);
                }


            }

            private void insert_data_ke_server(String get_kode_buku, String get_judul_buku, String get_tipe_buku) {
                String URI = "http://192.168.0.101/server-komik/?komik=tambah";
                RequestQueue queue          = Volley.newRequestQueue(TambahKomik.this);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URI, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

//                        Toast.makeText(TambahKomik.this,response.toString(),Toast.LENGTH_SHORT).show();
                        if(response.toString().equals("insert_data_berhasil")){
                            Intent i_main = new Intent(TambahKomik.this, MainActivity.class);
                            startActivity(i_main);
                            finish();
                        }else{
                            Toast.makeText(TambahKomik.this,response.toString(),Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TambahKomik.this,error.toString(),Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        // Posting parameters ke post url
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("kode_buku", get_kode_buku);
                        params.put("judul", get_judul_buku);
                        params.put("tipe", get_tipe_buku);

                        return params;
                    }

                };

                queue.add(stringRequest);
            }
        });


    }
}