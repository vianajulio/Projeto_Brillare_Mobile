package activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projeto_brillare.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import classes.Cliente;
import classes.Produto;
import classes.RecyclerViewAdapter;
import classes.RecyclerViewInterface;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface {

    private BottomNavigationView navBarMenu;
    private ImageButton btnImgCarrinho;

    private ArrayList<Produto> produtoLista = new ArrayList<>();

    int[] produtoImagens = {R.drawable.gato, R.drawable.tenis, R.drawable.maromba, R.drawable.maromba, R.drawable.tenis, R.drawable.tenis};

    private ArrayList<Produto> produtosCarrinho = new ArrayList<>();
    ActivityResultLauncher<Intent> resultado = registerForActivityResult(new
                    ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent intent = result.getData();
                        String codigo = intent.getStringExtra("codigo");

                        if (intent != null && codigo.equals("2")) {
                            produtosCarrinho.add((Produto) intent.getSerializableExtra("carrinho"));
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navBarMenu = findViewById(R.id.navBarHome);
        navBarMenu.setSelectedItemId(R.id.icon_navBarHome);

        setUpProdutos();

        RecyclerView recyclerView = findViewById(R.id.recyclerViewHome);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, produtoLista, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        btnImgCarrinho = findViewById(R.id.btnImgCarrinho);
        btnImgCarrinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CarrinhoActivity.class);
                intent.putExtra("carrinhoProduto", produtosCarrinho);
                startActivity(intent);
            }
        });

    }

    private void setUpProdutos() {
        String[] produtosNomes = getResources().getStringArray(R.array.nomeProdutos);
        String[] produtosDescricao = getResources().getStringArray(R.array.descProdutos);
        int[] produtosPreco = getResources().getIntArray(R.array.precoProduto);

        for (int i = 0; i < produtosNomes.length; i++) {
            try {
                produtoLista.add(new Produto(produtoImagens[i], produtosNomes[i], produtosDescricao[i], produtosPreco[i]));
            } catch (Exception e) {
                Log.d("Erro", "setUpProdutos: " + e);
            }

        }
    }


    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(MainActivity.this, ProdutoActivity.class);

        intent.putExtra("IMAGEM", produtoLista.get(position).getImage());
        intent.putExtra("TITULO", produtoLista.get(position).getTitulo());
        intent.putExtra("DESCRICAO", produtoLista.get(position).getDescricao());
        intent.putExtra("PRECO", produtoLista.get(position).getPreco());

        resultado.launch(intent);
    }
}
