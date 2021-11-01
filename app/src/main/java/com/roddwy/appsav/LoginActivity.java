package com.roddwy.appsav;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.roddwy.appsav.config.Constants;
import com.roddwy.appsav.database.AppDatabase;
import com.roddwy.appsav.views.MainActivity;
import com.roddwy.appsav.views.RegisterUserActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSession;
    TextView txtPassRegisterUser;
    EditText etUserName, etPassword;

    AppDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //getSupportActionBar().hide();

        btnSession = findViewById(R.id.buttonSession);
        btnSession.setOnClickListener(this);

        txtPassRegisterUser = findViewById(R.id.textViewPassRegisterUser);
        txtPassRegisterUser.setOnClickListener(this);

        etUserName = findViewById(R.id.editTextUserLogin);
        etPassword = findViewById(R.id.editTextPasswordLogin);

        db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class, Constants.DB_NAME)
                .allowMainThreadQueries()
                .build();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id){
            case R.id.buttonSession:
                sessionUser();
                break;
            case R.id.textViewPassRegisterUser:
                registerUser();
                break;
        }
    }

    private void sessionUser() {
        String username = etUserName.getText().toString();
        String password = etPassword.getText().toString();

        if(username.isEmpty() ){
            etUserName.setError("El usuario es requerido");
        }else if(password.isEmpty()){
            etPassword.setError("La contraseña es requerida");
        }else{
            int loginUser = db.userDao().getUserLogin(username,password);

            if(loginUser > 0){
                Toast.makeText(LoginActivity.this,"EXELENTE YA SE LOGEO",Toast.LENGTH_SHORT).show();
                Intent main = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(main);
                finish();
            }else{
                Toast.makeText(LoginActivity.this,"OPS NO EXISTE USUARIO, USTED NECESITA REGISTRARSE.",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void registerUser() {
        Intent i = new Intent(LoginActivity.this, RegisterUserActivity.class);
        startActivity(i);
        finish();
    }
}