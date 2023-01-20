package activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projeto_brillare.R;
import com.example.projeto_brillare.databinding.ActivityEsqueceuSenhaBinding;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.regex.Pattern;

import classes.Cliente;

public class EsqueceuSenhaActivity extends AppCompatActivity {

    private ActivityEsqueceuSenhaBinding binding;
    private EditText inpuEmail;
    private Button btnEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEsqueceuSenhaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayList<Cliente> clientes = (ArrayList<Cliente>) getIntent().getSerializableExtra("clientes");

        inpuEmail = binding.txtEmailES;
        btnEnviar = binding.btnEnviar;
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Cliente cliente : clientes) {
                    if (cliente.getEmail().equals(inpuEmail.getText().toString())){
                        if (inpuEmail.getText().toString().isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(inpuEmail.getText().toString()).matches()) {
                            Toast.makeText(EsqueceuSenhaActivity.this, "E-mail inválido!", Toast.LENGTH_SHORT).show();
                        } else {
                            inpuEmail.getText().clear();
                            Toast.makeText(EsqueceuSenhaActivity.this, "Solicitação enviada! Aguarde o e-mail para resetar sua senha.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });


    }
}