package activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.PointerIcon;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projeto_brillare.R;
import com.example.projeto_brillare.databinding.ActivityProdutoBinding;

import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;

import classes.Cliente;
import classes.Produto;

public class ProdutoActivity extends AppCompatActivity {

    private ActivityProdutoBinding binding;
    private ImageButton btnImgVoltarHome;
    private ImageView imageViewProduto;
    private TextView textViewTitulo, textViewDescricao, textViewPreco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProdutoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Produto produto = (Produto) getIntent().getSerializableExtra("produto");

        int imagem = produto.getImage();
        String titulo = produto.getTitulo();
        String descricao = produto.getDescricao();
        double preco = produto.getPreco();

        imageViewProduto = findViewById(R.id.imageViewProduto);
        textViewTitulo = findViewById(R.id.textViewTitulo);
        textViewDescricao = findViewById(R.id.textViewDescricao);
        textViewPreco = findViewById(R.id.textViewPreco);

        imageViewProduto.setImageResource(imagem);
        textViewTitulo.setText(titulo);
        textViewDescricao.setText(descricao);
        textViewPreco.setText(Double.toString(preco));

        btnImgVoltarHome = findViewById(R.id.imageButtonVoltarHome);
        btnImgVoltarHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProdutoActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        Button addProduto = findViewById(R.id.btnAddCarrinho);
        addProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Produto produto = new Produto(imagem, titulo, descricao, preco);

                Intent intent = new Intent();
                intent.putExtra("carrinho", produto);
                intent.putExtra("codigo", "2");

                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }


}