package activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.example.projeto_brillare.R;

public class FormaPagamentoActivity extends AppCompatActivity {

    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forma_pagamento);





        Button btnPagementoContinuar = findViewById(R.id.btnPagamentoContinuar);

        btnPagementoContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FormaPagamentoActivity.this, ConfirmarPagamentoActivity.class);
                startActivity(intent);

            }
        });

    }
}