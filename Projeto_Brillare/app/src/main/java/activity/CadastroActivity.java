package activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.projeto_brillare.R;
import com.google.android.material.textfield.TextInputEditText;

import classes.Cliente;

public class CadastroActivity extends AppCompatActivity {

    private TextInputEditText inputUsuario, inputEmail, inputSenha, inputSenhaConfimacao;
    private Button btnRegistreSe;
    private ImageButton btnVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        inputUsuario = findViewById(R.id.inputUsuario);
        inputEmail = findViewById(R.id.inputEmail);
        inputSenha = findViewById(R.id.inputSenha);
        inputSenhaConfimacao = findViewById(R.id.inputConfirmarSenha);

        btnRegistreSe = findViewById(R.id.btnRegistreSe);
        btnVoltar = findViewById(R.id.imgBtnVoltarLogin);
        
        btnRegistreSe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cliente cliente = new Cliente(inputUsuario.getText().toString(),inputEmail.getText().toString(),inputSenha.getText().toString());

                Intent intent = new Intent();
                intent.putExtra("cliente", cliente);
                intent.putExtra("codigo", "1");

                setResult(Activity.RESULT_OK, intent);

                CadastroActivity.super.onBackPressed();
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
}