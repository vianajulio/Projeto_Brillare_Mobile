package fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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

public class PagamentoCartaoFragment extends Fragment {

    private TextView numero, nome, mes, ano, cvv;
    private ImageButton imgVoltar;
    private Button btnContinuar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pagamento_cartao, container, false);

        numero = view.findViewById(R.id.textNumeroCartao);
        nome = view.findViewById(R.id.textNomeCartao);
        mes = view.findViewById(R.id.textMesCartao);
        ano = view.findViewById(R.id.textAnoCartao);
        cvv = view.findViewById(R.id.textCVVCartao);

        imgVoltar = view.findViewById(R.id.imgBtnVoltarFPCartao);
        imgVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                Fragment fragment = fragmentManager.findFragmentById(R.id.frameMainActivity);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.remove(fragment);
                transaction.commit();
            }
        });

        Bundle data = getArguments();
        ArrayList<Produto> produtos = (ArrayList<Produto>) data.getSerializable("carrinho");

        btnContinuar = view.findViewById(R.id.btnFinalPagamentoCartao);
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FinalizarCompraFragment finalizarCompraFragment = new FinalizarCompraFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                Bundle data = new Bundle();

                if (validaDados()) {
                    data.putSerializable("carrinho", produtos);
                    finalizarCompraFragment.setArguments(data);
                    transaction.add(R.id.frameMainActivity, finalizarCompraFragment);
                    transaction.commit();
                } else {
                    Toast.makeText(getContext(), "Confira os dados do cartão, e preencha-os corretamente!", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

    private boolean validaDados() {
        int meses = 0;
        int anos = 0;

        if (!mes.getText().toString().isEmpty() || !ano.getText().toString().isEmpty()) {
            meses = Integer.parseInt(mes.getText().toString());
            anos = Integer.parseInt(ano.getText().toString());
        }
        if (numero.getText().toString().isEmpty() || nome.getText().toString().isEmpty()
                || mes.getText().toString().isEmpty() || ano.getText().toString().isEmpty()
                || cvv.getText().toString().isEmpty()) {
            return false;
        } else if (meses > 12 || anos < 21) {
            return false;
        }
        return true;
    }

}