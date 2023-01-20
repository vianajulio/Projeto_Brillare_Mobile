package fragments;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.projeto_brillare.R;

import java.util.ArrayList;

import classes.Produto;


public class FormaDePagamentoFragment extends Fragment {

    private ImageButton imgbBtnVoltar;
    private CardView cardCartao, cardPix;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forma_de_pagamento, container, false);

        Bundle data = getArguments();
        ArrayList<Produto> produtos = (ArrayList<Produto>) data.getSerializable("carrinho");

        cardCartao = view.findViewById(R.id.cardCartao);
        cardCartao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PagamentoCartaoFragment cartaoFragment = new PagamentoCartaoFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                Bundle data = new Bundle();
                data.putSerializable("carrinho", produtos);
                cartaoFragment.setArguments(data);

                transaction.add(R.id.frameMainActivity, cartaoFragment);
                transaction.commit();
            }
        });

        cardPix = view.findViewById(R.id.cardPix);
        cardPix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PagamentoPixFragment pixFragment = new PagamentoPixFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                Bundle data = new Bundle();
                data.putSerializable("carrinho", produtos);
                pixFragment.setArguments(data);

                transaction.add(R.id.frameMainActivity, pixFragment);
                transaction.commit();
            }
        });


        imgbBtnVoltar = view.findViewById(R.id.imgBtnVoltarFP);
        imgbBtnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                Fragment fragment = fragmentManager.findFragmentById(R.id.frameMainActivity);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.remove(fragment);
                transaction.commit();
            }
        });


        return view;
    }
}