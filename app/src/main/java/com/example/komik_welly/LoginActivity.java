package com.example.komik_welly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText input_username, input_password;
    Button btn_login;

    SharedPreferences sharedpreferenlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        input_password = findViewById(R.id.input_password);
        input_username = findViewById(R.id.input_username);
        btn_login = findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String get_input_username = input_username.getText().toString().trim();
                String get_input_password = input_password.getText().toString().trim();
                if(get_input_username.isEmpty()){
                    input_username.setError("Harus Diisi Dengan Benar!");
                    input_username.requestFocus();
                } else if(get_input_password.isEmpty()){
                    input_password.setError("Harus Diisi Dengan Benar!");
                    input_password.requestFocus();
                }else{
                    CheckLogin la = new CheckLogin(LoginActivity.this);
                    la.check_login(get_input_username, get_input_password);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        String sess_created_login= "session_created_login";
        String session_create_login= "create_login";
        sharedpreferenlogin= getSharedPreferences(sess_created_login, Context.MODE_PRIVATE);
        String share_session_create_login= sharedpreferenlogin.getString(session_create_login,  null);

        if(share_session_create_login!= null){
          Intent intent_main = new Intent(LoginActivity.this, MainActivity.class);
          startActivity(intent_main);
        }else {
            Toast.makeText(LoginActivity.this, "Silahkan Login Untuk Mengakses Halaman ini!", Toast.LENGTH_SHORT).show();
        }

        super.onStart();
    }
}