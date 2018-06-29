package com.example.innova.saborapp;

import android.app.usage.NetworkStatsManager;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.innova.saborapp.models.Receta;
import com.example.innova.saborapp.models.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    private FirebaseAuth mAuth;
   // private FirebaseAuth.AuthStateListener m;


    TextView txtRegistrate;
    EditText edtUsuario;
    EditText edtContrasena;
    Button btnIniciar;
    DatabaseReference mDatabase;
    DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        txtRegistrate = (TextView)findViewById(R.id.txtRegistrate);
        edtUsuario = (EditText)findViewById(R.id.edtUsuario) ;
        edtContrasena = (EditText)findViewById(R.id.edtContrasena);
        btnIniciar = (Button)findViewById(R.id.btnIniciar);


        txtRegistrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Registro.class);
                startActivity(intent);
            }
        });

           btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              LogearUsuario();
            }
        });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            //Guardar el user en variable Global
            String uid = user.getUid();
            mDatabase = FirebaseDatabase.getInstance().getReference();
            mRef = mDatabase.child("usuario");
            mRef.orderByChild("uid").equalTo(uid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.getChildrenCount() > 0) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Usuario oUsuario = snapshot.getValue(Usuario.class);
                            ((Global) getParent().getApplication()).setUsuario(oUsuario);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

//            ((Global) this.getApplication()).setUsuario("foo");
            Intent intent = new Intent(this, InicioActivity.class);
            startActivity(intent);
        } else {

        }
    }

    private void LogearUsuario(){
        String usuario = edtUsuario.getText().toString();
        String contrasena = edtContrasena.getText().toString();

        mAuth.signInWithEmailAndPassword(usuario, contrasena)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(Login.this, task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }
}
