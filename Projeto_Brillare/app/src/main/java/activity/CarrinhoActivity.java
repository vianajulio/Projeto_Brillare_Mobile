package activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.projeto_brillare.R;

import java.util.ArrayList;

import classes.Produto;
import classes.RecyclerViewAdapter;
import classes.RecyclerViewInterface;

public class CarrinhoActivity extends AppCompatActivity implements RecyclerViewInterface {

    private RecyclerView recyclerViewCarrinho;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);

        try {

     ArrayList<Produto> produtosCarrinho = (ArrayList<Produto>) getIntent().getSerializableExtra("carrinhoProduto");
            recyclerViewCarrinho = findViewById(R.id.recyclerViewCarrinho);

            RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, produtosCarrinho, this);
            recyclerViewCarrinho.setAdapter(adapter);

            recyclerViewCarrinho.setLayoutManager(new LinearLayoutManager(this));

        } catch (Exception e) {
            Log.d("ERRO", "onCreate: " + e);
        }

        Button btnCarrinhoContinuar = findViewById(R.id.btnCarrinhoContinuar);

        btnCarrinhoContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CarrinhoActivity.this, FormaPagamentoActivity.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public void onItemClick(int position) {
//        Toast.makeText(this, "Produto: " + produtosCarrinho.get(position).getTitulo(), Toast.LENGTH_SHORT).show();
    }
}