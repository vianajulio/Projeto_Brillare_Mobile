package fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.projeto_brillare.R;

public class PerfilFragment extends Fragment {

    private TextView txtNome, txtEmail, txtRua, txtComplemento, txtCEP;
    private Button btnNovoEndereco;
    private ImageButton imgAtualizar;
    private String nome, email, rua, bairro, complemento;
    private int numero, cep;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);


        txtNome = view.findViewById(R.id.txtNome);
        txtEmail = view.findViewById(R.id.txtEmail);

        txtRua = view.findViewById(R.id.txtRuaPerfil);
        txtComplemento = view.findViewById(R.id.txtComplementoPerfil);
        txtCEP = view.findViewById(R.id.txtCEPPerfil);

        recuperarDados();
        if (!nome.isEmpty() || !email.isEmpty()) {
            txtNome.setText(nome);
            txtEmail.setText(email);
        }

        imgAtualizar = view.findViewById(R.id.imgBtnAtualizaEndereco);
        imgAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recuperarEndereco();
                inserirEndereco();
            }
        });

        btnNovoEndereco = view.findViewById(R.id.btnNovoEndereco);
        btnNovoEndereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnderecoFragment enderecoFragment = new EnderecoFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameMainActivity, enderecoFragment);
                transaction.commit();
            }
        });


        return view;
    }

    private void recuperarDados() {
        SharedPreferences preferences = getActivity().getSharedPreferences("com.example.projeto_brillare.DADOS_CLIENTE_SHARED_KEY", Context.MODE_PRIVATE);
        nome = preferences.getString("usuario", "Usuário não cadastrado!");
        email = preferences.getString("email", "E-mail não cadastrado!");
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
            txtRua.setText(rua + ", " + numero + " - " + bairro);
            txtCEP.setText(Integer.toString(cep));
            txtComplemento.setText(complemento);
        }
    }



}