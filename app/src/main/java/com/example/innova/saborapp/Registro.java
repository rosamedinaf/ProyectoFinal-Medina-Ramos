package com.example.innova.saborapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.innova.saborapp.models.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registro extends AppCompatActivity {

    EditText edtNombre, edtApellidos, edtemail, edtContrasena;
    Button btnGuardar;
    private FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        edtNombre = (EditText) findViewById(R.id.edtNombre);
        edtApellidos = (EditText) findViewById(R.id.edtApellido);
        edtemail = (EditText) findViewById(R.id.edtEmail);
        edtContrasena = (EditText) findViewById(R.id.edtContrasenaR);
        btnGuardar = (Button) findViewById(R.id.btnGuardar);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* String nombre = edtNombre.getText().toString();
                String apellido = edtApellidos.getText().toString();
                String email = edtemail.getText().toString();
                uid= mDatabase.push().getKey();
                Usuario user = new Usuario(nombre, apellido, email,uid);
                mDatabase.child("usuario").child(uid).setValue(user);
*/
                CrearUsuario();
            }
        });

    }
        private void updateUI(FirebaseUser user){
            if (user != null) {

                //Grabar en BD el resto de data
                String nombre = edtNombre.getText().toString();
                String apellido = edtApellidos.getText().toString();
                String email = edtemail.getText().toString();
                String uid = user.getUid();
                String id= mDatabase.push().getKey();

                Usuario oUsuario = new Usuario(nombre, apellido, email,uid);
                mDatabase.child("usuario").child(id).setValue(oUsuario);
                ((Global) this.getApplication()).setUsuario(oUsuario);
                Intent intent = new Intent(this, InicioActivity.class);
                startActivity(intent);

            } else {

            }
        }

        private void CrearUsuario(){
            String email = edtemail.getText().toString();
            String contrasena = edtContrasena.getText().toString();

            mAuth.createUserWithEmailAndPassword(email, contrasena)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {

                                // Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(Registro.this, task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        }
                    });
        }
    }
