package fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projeto_brillare.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import classes.Produto;

public class FinalizarCompraFragment extends Fragment {

    private ImageButton imgBtnVoltarFC, btnEditEndereco, btnAtualizar;
    private Button btnFinalizar, btnCancelar;
    private TextView txtRua, txtNumero, txtCEP, txtBairro, txtComplemento, txtSubtotal, txtTotal;
    private EditText inputEmail, inputNome;
    private TextInputLayout layoutEmail, layoutNome;
    private String rua, bairro, complemento;
    private int numero, cep;

    private double subtotal, frete;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_finalizar_compra, container, false);
        frete = 15.00;

        Bundle data = getArguments();
        ArrayList<Produto> produtos = (ArrayList<Produto>) data.getSerializable("carrinho");

        txtRua = view.findViewById(R.id.ruaFC);
        txtNumero = view.findViewById(R.id.numeroFC);
        txtCEP = view.findViewById(R.id.cepFC);
        txtBairro = view.findViewById(R.id.bairroFC);
        txtComplemento = view.findViewById(R.id.complementoFC);

        txtSubtotal = view.findViewById(R.id.subtotalFC);
        txtTotal = view.findViewById(R.id.totalFC);

        inputEmail = view.findViewById(R.id.inputEmailFC);
        inputNome = view.findViewById(R.id.inputNomeFC);
        layoutEmail = view.findViewById(R.id.inputLayoutEmail);
        layoutNome = view.findViewById(R.id.inputLayoutNome);

        btnEditEndereco = view.findViewById(R.id.imgBtnEditEndereco);
        btnEditEndereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnderecoFragment enderecoFragment = new EnderecoFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.frameMainActivity, enderecoFragment);
                transaction.commit();
            }
        });

        btnAtualizar = view.findViewById(R.id.imgBtnAtualizar);
        btnAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recuperarEndereco();
                inserirEndereco();
            }
        });

        imgBtnVoltarFC = view.findViewById(R.id.imgBtnVoltarFC);
        imgBtnVoltarFC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                Fragment fragment = fragmentManager.findFragmentById(R.id.frameMainActivity);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.remove(fragment);
                transaction.commit();
            }
        });

        subtotal = subtotalCalculo(produtos);
        txtSubtotal.setText(Double.toString(subtotal));
        txtTotal.setText(Double.toString(subtotal + frete));

        btnFinalizar = view.findViewById(R.id.btnFinalizarFC);
        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FinalizarCompraFragment finalizarCompraFragment = new FinalizarCompraFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                CompraFinalizadaFragment compraFinalizadaFragment = new CompraFinalizadaFragment();
                validacaoPagamento();
                if (confirmarPagamento() == true) {
                    data.putSerializable("carrinho", produtos);
                    finalizarCompraFragment.setArguments(data);
                    transaction.replace(R.id.frameMainActivity, compraFinalizadaFragment);

                    transaction.commit();
                }
            }
        });

        btnCancelar = view.findViewById(R.id.btnCancelarFC);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment homeFragment = new HomeFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameMainActivity, homeFragment);
                transaction.commit();
                Toast.makeText(getContext(), "Compra cancelada!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    public void recuperarEndereco() {
        SharedPreferences preferences = getActivity().getSharedPreferences("com.example.projeto_brillare.ENDERECO_SHARED_KEY", Context.MODE_PRIVATE);
        rua = preferences.getString("rua", "Rua não informada");
        numero = preferences.getInt("numero", 0);
        cep = preferences.getInt("cep", 0);
        bairro = preferences.getString("bairro", "Bairro não informado");
        complemento = preferences.getString("complemento", "Complemento não informado");
    }

    public void inserirEndereco() {
        if (!rua.isEmpty() && numero != 0 && cep != 0) {
            txtRua.setText(rua);
            txtNumero.setText(Integer.toString(numero));
            txtCEP.setText(Integer.toString(cep));
            txtBairro.setText(bairro);
            txtComplemento.setText(complemento);
        }
    }

    private double subtotalCalculo(ArrayList<Produto> produtos) {

        double subtotal = 0.0;

        for (Produto produto : produtos) {
            subtotal = subtotal + produto.getPreco();
        }

        return subtotal;
    }

    private void validacaoPagamento() {
       if (inputNome.getText().toString().isEmpty() || inputEmail.getText().toString().isEmpty()) {
            layoutEmail.setHelperText("Peencha o campo de e-mail!");
            layoutNome.setHelperText("Peencha o campo de nome!");
        }
        if (txtRua.getText().toString().equals("Rua")) {
            Toast.makeText(getContext(), "Insira um endereço ou clique no botão de atualizar.", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean confirmarPagamento() {
        if (!inputNome.getText().toString().isEmpty() && !inputEmail.getText().toString().isEmpty() && !txtRua.getText().toString().equals("Rua")) {
            return true;
        }
        return false;
    }

}