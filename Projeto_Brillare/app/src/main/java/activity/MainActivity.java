package activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.projeto_brillare.R;
import com.example.projeto_brillare.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import classes.Produto;
import classes.ViewAdapter;
import classes.RecyclerViewInterface;
import fragments.CarrinhoFragment;
import fragments.ConfimacaoSairFragment;
import fragments.HomeFragment;
import fragments.PerfilFragment;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface {

    private ActivityMainBinding binding;
    private BottomNavigationView navBarMenu;

    private ArrayList<Produto> produtoLista = new ArrayList<>();

    private int[] produtoImagens = {
            R.drawable.bricos_dourados_diamante, R.drawable.brincos_dourados,
            R.drawable.colar_borboleta, R.drawable.colar_laminado, R.drawable.colar_lua,
            R.drawable.pulseira_dourada, R.drawable.pulseira_ice, R.drawable.brincos_zicornia,
            R.drawable.crucifixo, R.drawable.brinco_banho_prata_zirconia, R.drawable.anel_zirconia,
            R.drawable.brinco_colar_petalas, R.drawable.colar_banho_ouro_zirconia, R.drawable.colar_coracao_zirconia_rosa,
            R.drawable.colar_zirconia_azul_rosa, R.drawable.conjunto_colar_verde, R.drawable.conjunto_prata_zirconia_vermelha,
            R.drawable.pulseira_banho_ouro_prata_zirconia};

    protected ArrayList<Produto> produtos = new ArrayList<>();
    ActivityResultLauncher<Intent> resultado = registerForActivityResult(new
                    ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent intent = result.getData();
                        String codigo = intent.getStringExtra("codigo");

                        if (intent != null && codigo.equals("2")) {

                            produtos.add((Produto) intent.getSerializableExtra("carrinho"));
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setUpProdutos();

        navBarMenu = findViewById(R.id.navBarHome);
        navBarMenu.setSelectedItemId(R.id.icon_navBarHome);
        navBarMenu.setOnItemSelectedListener(item -> {
            navegacao(item, produtos);
            return true;
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerViewHome);
        try {
            if (produtoLista != null) {
                ViewAdapter adapter = new ViewAdapter(this, produtoLista, this);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Método para criar os produtos da lista.
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

    //Utilizado para navegar entre as Fragments
    private void navegacao(MenuItem item, ArrayList<Produto> produtos) {
        PerfilFragment perfilFragment = new PerfilFragment();
        HomeFragment mainFragment = new HomeFragment();
        CarrinhoFragment carrinhoFragment = new CarrinhoFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Bundle data = new Bundle();

        if (item.getItemId() == R.id.icon_navBarPerfil) {
            transaction.replace(R.id.frameMainActivity, perfilFragment);
            transaction.commit();
        }
        if (item.getItemId() == R.id.icon_navBarHome) {
            transaction.replace(R.id.frameMainActivity, mainFragment);
            transaction.commit();
        }
        if (item.getItemId() == R.id.icon_navBarCarrinho) {
            abrirFragmentCarrinho(transaction, data);
        }
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(MainActivity.this, ProdutoActivity.class);

        Produto produto = new Produto(produtoLista.get(position).getImage(), produtoLista.get(position).getTitulo(), produtoLista.get(position).getDescricao(), produtoLista.get(position).getPreco());
        intent.putExtra("produto", produto);
        resultado.launch(intent);
    }

    private void abrirFragmentCarrinho(FragmentTransaction transaction, Bundle data) {
        CarrinhoFragment carrinhoFragment = new CarrinhoFragment();

        transaction = getSupportFragmentManager().beginTransaction();

        data.putSerializable("carrinho", produtos);

        carrinhoFragment.setArguments(data);

        transaction.replace(R.id.frameMainActivity, carrinhoFragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        ConfimacaoSairFragment sairFragment = new ConfimacaoSairFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameMainActivity, sairFragment);
        transaction.commit();
    }
}
