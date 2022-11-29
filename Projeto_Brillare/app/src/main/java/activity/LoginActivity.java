package activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projeto_brillare.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import classes.Cliente;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin, btnCadastro;
    private TextView txtViewEsqueceuSenha;
    private TextInputEditText inputEmail, inputSenha;

     List<Cliente> clientes = new ArrayList<>();

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

        inputEmail = findViewById(R.id.inputEmail);
        inputSenha = findViewById(R.id.inputSenha);

//        CRIAR CLASSE ADM, E UTILIZAR GET
        clientes.add(new Cliente("adm","adm@senai.br","adm123"));

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (Cliente cliente : clientes) {
                    if (inputEmail.getText().toString().equals(cliente.getUsuario())) {
                        if (cliente.getSenha().equals(inputSenha.getText().toString())){
                            Intent irMain = new Intent(LoginActivity.this, LoadingScreenActivity.class);
                            irMain.putExtra("cliente", cliente);
                            startActivity(irMain);
                            finish();
                        }
                        Toast.makeText(LoginActivity.this, "USER FOI", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "Senha ou usuário inválidos", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });

        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
                resultado.launch(intent);
            }
        });

        txtViewEsqueceuSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, EsqueceuSenhaActivity.class);
                startActivity(intent);
            }
        });


    }
}