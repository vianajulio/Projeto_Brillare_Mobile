package activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.projeto_brillare.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import classes.Cliente;
import classes.Produto;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin, btnCadastro;
    private TextView txtViewEsqueceuSenha;
    private TextInputEditText inputUsuario, inputSenha;
    private TextInputLayout inputLayoutUser, inputLayoutPass;

    private List<Cliente> clientes = new ArrayList<>();

    ActivityResultLauncher<Intent> resultado = registerForActivityResult(new
                    ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent intent = result.getData();
                        String codigo = intent.getStringExtra("codigo");

                        if (intent != null && codigo.equals("1")) {
                            clientes.add((Cliente) intent.getSerializableExtra("cliente"));
                        }
                    }
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        btnCadastro = findViewById(R.id.btnCadastro);
        txtViewEsqueceuSenha = findViewById(R.id.textViewEsqueceuSenha);

            inputLayoutUser = findViewById(R.id.inputLayoutUser);
            inputLayoutPass = findViewById(R.id.inputLayoutPass);

            inputUsuario = findViewById(R.id.inputUser);
            inputSenha = findViewById(R.id.inputPass);


        clientes.add(new Cliente("adm", "adm@senai.br", "adm123"));

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String usuario = inputUsuario.getText().toString().toLowerCase();
                String senha = inputSenha.getText().toString();

                for (Cliente cliente : clientes) {
                    if (usuario.equals(cliente.getUsuario()) && senha.equals(cliente.getSenha())) {
                        Intent intent = new Intent(LoginActivity.this, LoadingScreenActivity.class);
                        intent.putExtra("cliente", cliente);
                        startActivity(intent);

                        String email = cliente.getEmail();
                        salvarDados(usuario, email, senha);
                        finish();
                    } else {
                        inputUsuario.getText().clear();
                        inputSenha.getText().clear();
                        inputLayoutUser.setHelperText("Usuário inválido!");
                        inputLayoutPass.setHelperText("Senha inválida!");
                    }
                }
            }
        });

        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
                intent.putExtra("clientes", (Serializable) clientes);
                resultado.launch(intent);
            }
        });

        txtViewEsqueceuSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, EsqueceuSenhaActivity.class);
                intent.putExtra("clientes", (Serializable) clientes);
                startActivity(intent);
            }
        });
    }

    private void salvarDados(String usuario, String email, String senha) {
        SharedPreferences sharedPreferences = getSharedPreferences(
                getString(R.string.dados_cliente_shared), Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("usuario", usuario);
        editor.putString("email", email);
        editor.putString("senha", senha);
        editor.commit();
    }

}