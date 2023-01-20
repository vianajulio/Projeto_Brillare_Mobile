package fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projeto_brillare.R;

import java.util.ArrayList;

import classes.Produto;
import classes.ViewAdapterCarrinho;

public class CarrinhoFragment extends Fragment {

    private RecyclerView recyclerViewCarrinho;

    private TextView txtValorTotal;
    private Button btnContinuar;
    private ImageButton btnLixeira;

    private ArrayList<Produto> produtosCarrinho = new ArrayList<>();

    private double valorFinal = 0.0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carrinho, container, false);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle data = getArguments();
        recyclerViewCarrinho = view.findViewById(R.id.recyclerViewFragment);

        produtosCarrinho = (ArrayList<Produto>) data.getSerializable("carrinho");
        recyclerViewCarrinho.setLayoutManager(new LinearLayoutManager(getContext()));
        ViewAdapterCarrinho adapterCarrinho = new ViewAdapterCarrinho(produtosCarrinho);
        recyclerViewCarrinho.setAdapter(adapterCarrinho);

        txtValorTotal = view.findViewById(R.id.txtValorTotal);

        if (produtosCarrinho.size() != 0) {
            for (int i = 0; i < produtosCarrinho.size(); i++) {
                valorFinal = valorFinal + produtosCarrinho.get(i).getPreco();
            }
            txtValorTotal.setText(Double.toString(valorFinal));
        }


        btnContinuar = view.findViewById(R.id.btnContinuarCarrinho);
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!produtosCarrinho.isEmpty()) {
                    FormaDePagamentoFragment formaDePagamentoFragment = new FormaDePagamentoFragment();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                    Bundle data = new Bundle();
                    data.putSerializable("carrinho", produtosCarrinho);

                    formaDePagamentoFragment.setArguments(data);

                    transaction.add(R.id.frameMainActivity, formaDePagamentoFragment);
                    transaction.commit();
                } else {
                    Toast.makeText(getContext(), "Adicione algum produto no carrinho!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnLixeira = view.findViewById(R.id.imgBtnLixeira);
        btnLixeira.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                produtosCarrinho.clear();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                Fragment fragment = fragmentManager.findFragmentById(R.id.frameMainActivity);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.remove(fragment);
                transaction.commit();
            }
        });

    }

}