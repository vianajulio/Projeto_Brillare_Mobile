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

import com.example.projeto_brillare.R;

import java.util.ArrayList;

import classes.Produto;

public class PagamentoPixFragment extends Fragment {

    private ImageButton btnVoltar;
    private Button btnContinuar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pagamento_pix, container, false);

        Bundle data = getArguments();
        ArrayList<Produto> produtos = (ArrayList<Produto>) data.getSerializable("carrinho");

        btnContinuar = view.findViewById(R.id.btnFinalPagamentoPix);
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FinalizarCompraFragment finalizarCompraFragment = new FinalizarCompraFragment();

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                Bundle data = new Bundle();
                if (produtos != null) {
                    data.putSerializable("carrinho", produtos);
                    finalizarCompraFragment.setArguments(data);
                }

                transaction.add(R.id.frameMainActivity, finalizarCompraFragment);
                transaction.commit();
            }
        });


        btnVoltar = view.findViewById(R.id.imgBtnVoltarFPPix);
        btnVoltar.setOnClickListener(new View.OnClickListener() {
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