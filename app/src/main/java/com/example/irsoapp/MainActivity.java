package com.example.irsoapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //defining view objects
    private EditText TextEmail;
    private EditText TextPassword;
    private Button BtnRegistrar;
    private Button BtnLogin;

    private ProgressDialog progressDialog;


    //Declaramos un objeto firebaseAuth
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //inicializamos el objeto firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();

        //Referenciamos los views
        TextEmail = findViewById(R.id.txtEmail);

        TextPassword = findViewById(R.id.txtPassword);

        BtnRegistrar = (Button) findViewById(R.id.btnRegistrar);

        BtnLogin = (Button) findViewById(R.id.btnLogin);

        progressDialog = new ProgressDialog(this);

        //attaching listener to button
        BtnRegistrar.setOnClickListener(this);
        BtnLogin.setOnClickListener(this);
    }

    private void registrarUsuario(){

        //Obtenemos el email y la contraseña desde las cajas de texto
        String email = TextEmail.getText().toString().trim();
        String password  = TextPassword.getText().toString().trim();

        //Verificamos que las cajas de texto no esten vacías
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Se debe ingresar un email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Falta ingresar la contraseña",Toast.LENGTH_LONG).show();
            return;
        }


        progressDialog.setMessage("Realizando registro en linea...");
        progressDialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){

                            Toast.makeText(MainActivity.this,"Se ha registrado el usuario con el email: "+ TextEmail.getText(),Toast.LENGTH_LONG).show();
                        }else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {//si se presenta una colision
                                Toast.makeText(MainActivity.this, "El usuario ya existe ", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, "No se pudo registrar el usuario ", Toast.LENGTH_LONG).show();

                            }
                        }
                    }
                });

    }


    private void loguearUsuario (){
        //Obtenemos el email y la contraseña desde las cajas de texto
        final String email = TextEmail.getText().toString().trim();
        String password  = TextPassword.getText().toString().trim();

        //Verificamos que las cajas de texto no esten vacías
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Se debe ingresar un email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Falta ingresar la contraseña",Toast.LENGTH_LONG).show();
            return;
        }


        progressDialog.setMessage("Realizando consulta en linea...");
        progressDialog.show();

        //Consultar si el usuario existe
        Task<AuthResult> no_se_pudo_loguear_ = firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if (task.isSuccessful()) {

                            int pos = email.indexOf("@"); //obtiene el usuario del mail desde el principio hasta la posicion del arroba
                            String user = email.substring(0, pos);

                            Toast.makeText(MainActivity.this, "Bienvenido: " + TextEmail.getText(), Toast.LENGTH_LONG).show();

                            // evaluar si el usuario es admin o user

                            Intent intencion = new Intent(getApplication(), UserActivity.class);
                            intencion.putExtra(UserActivity.user, user); //sirve para enviar datos a otra activity
                            startActivity(intencion);

                        } else
                            Toast.makeText(MainActivity.this, "No se pudo loguear ", Toast.LENGTH_LONG).show();
                    }


                });

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btnRegistrar:
                registrarUsuario();
                break;
            case R.id.btnLogin:
                loguearUsuario();
                break;
        }

        //Invocamos al método:

    }
}
