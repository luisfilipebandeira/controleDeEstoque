package com.example.play.autenticacao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.play.R;
import com.example.play.activity.MainActivity;
import com.example.play.helper.FirebaseHelper;

public class LoginActivity extends AppCompatActivity {

    private TextView text_criar_conta;
    private TextView text_recuperar_conta;
    private EditText edit_email;
    private EditText edit_senha;
    private ProgressBar progress_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        iniciaComponentes();

        configCliques();
    }

    public void logar(View view){
        String email = edit_email.getText().toString().trim();
        String senha = edit_senha.getText().toString();

        if (!email.isEmpty()) {
            if(!senha.isEmpty()) {

                progress_bar.setVisibility(View.VISIBLE);

                validaLogin(email, senha);

            } else {
                edit_senha.requestFocus();
                edit_senha.setError("Informe sua senha");
            }
        } else {
            edit_email.requestFocus();
            edit_email.setError("Informe seu email");
        }
    }

    private void validaLogin(String email, String senha) {
        FirebaseHelper.getAuth().signInWithEmailAndPassword(
                email, senha
        ).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                finish();
                startActivity(new Intent(this, MainActivity.class));
            } else {
                progress_bar.setVisibility(View.GONE);
                String error = task.getException().getMessage();
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void configCliques() {
        text_criar_conta.setOnClickListener(view -> startActivity(new Intent(this, CriarContaActivity.class)));
        text_recuperar_conta.setOnClickListener(view -> startActivity(new Intent(this, RecuperarContaActivity.class)));
    }

    private void iniciaComponentes() {
        edit_email = findViewById(R.id.edit_email);
        edit_senha = findViewById(R.id.edit_senha);
        text_criar_conta = findViewById(R.id.text_criar_conta);
        text_recuperar_conta = findViewById(R.id.text_recuperar_conta);
        progress_bar = findViewById(R.id.progressBar);
    }
}