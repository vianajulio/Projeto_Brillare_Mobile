package activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.projeto_brillare.R;
import com.example.projeto_brillare.databinding.ActivityCadastroBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import classes.Cliente;
import classes.Produto;

public class CadastroActivity extends AppCompatActivity {

    private ActivityCadastroBinding binding;
    private TextInputEditText inputUsuario, inputEmail, inputSenha, inputSenhaConfimacao;
    private TextInputLayout inputLayoutUsuario, inputLayoutEmail, inputLayoutSenha, inputLayoutConfirmarSenha;
    private Button btnRegistreSe;
    private ImageButton btnVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCadastroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayList<Cliente> clientes = (ArrayList<Cliente>) getIntent().getSerializableExtra("clientes");

        inputUsuario = findViewById(R.id.inputUsuario);
        inputEmail = findViewById(R.id.inputEmail);
        inputSenha = findViewById(R.id.inputSenha);
        inputSenhaConfimacao = findViewById(R.id.inputConfirmarSenha);

        inputLayoutUsuario = findViewById(R.id.textInputLayoutUsuario);
        inputLayoutEmail = findViewById(R.id.textInputLayoutEmail);
        inputLayoutSenha = findViewById(R.id.textInputLayoutSenha);
        inputLayoutConfirmarSenha = findViewById(R.id.textInputLayoutConfirmarSenha);


        btnRegistreSe = findViewById(R.id.btnRegistreSe);
        btnVoltar = findViewById(R.id.imgBtnVoltarLogin);

        btnRegistreSe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean cadastrar = false;
                validacaoCadastro(clientes, cadastrar);
                realizarCadastro(clientes, cadastrar);
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private boolean validacaoCadastro(ArrayList<Cliente> clientes, boolean cadastrar) {

        boolean usuario = false;
        boolean email = false;
        boolean senha = false;


        for (Cliente cliente : clientes) {
            if (inputUsuario.getText().toString().toLowerCase().equals(cliente.getUsuario())) {
                inputLayoutUsuario.setHelperText("Usuário já utilizado!");
            } else if (inputUsuario.getText().toString().isEmpty()) {
                inputLayoutUsuario.setHelperText("Insira seu nome de usuário!");
            } else {
                inputLayoutUsuario.setHelperText("");
                usuario = true;
            }

            if (inputEmail.getText().toString().equals(cliente.getEmail())) {
                inputLayoutEmail.setHelperText("E-mail já utilizado!");

            } else if (!Patterns.EMAIL_ADDRESS.matcher(inputEmail.getText().toString()).matches()) {
                inputLayoutEmail.setHelperText("E-mail inválido!");
            } else {
                inputLayoutEmail.setHelperText("");
                email = true;
            }

        }

        if (!inputSenha.getText().toString().toLowerCase().equals(inputSenhaConfimacao.getText().toString()) || inputSenha.getText().toString().isEmpty() || inputSenhaConfimacao.getText()
                .toString().isEmpty()) {
            inputLayoutSenha.setHelperText("Senhas divergentes! Preencha o campo novamente.");
            inputLayoutConfirmarSenha.setHelperText("Senhas divergentes! Preencha o campo novamente.");
            inputSenha.getText().clear();
            inputSenhaConfimacao.getText().clear();
        } else {
            inputLayoutSenha.setHelperText("");
            inputLayoutConfirmarSenha.setHelperText("");
            senha = true;
        }

        if (usuario && email && senha) {
            cadastrar = true;
        }

        return cadastrar;
    }

    private void realizarCadastro(ArrayList<Cliente> clientes, boolean cadastrar)  {
         cadastrar = validacaoCadastro(clientes, cadastrar);

        if (cadastrar) {
            Cliente cliente = new Cliente(inputUsuario.getText().toString().toLowerCase(), inputEmail.getText().toString(), inputSenha.getText().toString());

            clientes.add(cliente);

            Intent intent = new Intent();
            intent.putExtra("cliente", cliente);
            intent.putExtra("codigo", "1");

            setResult(Activity.RESULT_OK, intent);
            finish();

            inputUsuario.setText("");
            inputSenha.setText("");
        }
    }


}