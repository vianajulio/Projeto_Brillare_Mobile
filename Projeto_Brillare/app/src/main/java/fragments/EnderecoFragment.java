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
import android.widget.ImageButton;

import com.example.projeto_brillare.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class EnderecoFragment extends Fragment {

    private TextInputEditText inputRua, inputNumero, inputCEP, inputBairro, inputComplemento;
    private Button btnSalvarEndereco;
    private ImageButton imgBtnFechar;
    private String rua, bairro, complemento;
    private int numero, cep;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_endereco, container, false);

        inputRua = view.findViewById(R.id.inputRua);
        inputNumero = view.findViewById(R.id.inputNumero);
        inputCEP = view.findViewById(R.id.inputCEP);
        inputBairro = view.findViewById(R.id.inputBairro);
        inputComplemento = view.findViewById(R.id.inputComplemento);

        imgBtnFechar = view.findViewById(R.id.imgBtnFechar);
        imgBtnFechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retornar();
            }
        });


        btnSalvarEndereco = view.findViewById(R.id.btnAddEnderecoFragment);
        btnSalvarEndereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarEnderecoCache();
                retornar();
            }
        });


        return view;
    }

    public void retornar() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.frameMainActivity);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.remove(fragment);
        transaction.commit();
    }

    //Salva os dados do endereço no cahce do app.
    public void salvarEnderecoCache() {
        rua = inputRua.getText().toString();
        numero = Integer.parseInt(inputNumero.getText().toString());
        cep = Integer.parseInt(inputCEP.getText().toString());
        bairro = inputBairro.getText().toString();
        complemento = inputComplemento.getText().toString();

        SharedPreferences preferences = getActivity().getSharedPreferences("com.example.projeto_brillare.ENDERECO_SHARED_KEY", Context.MODE_PRIVATE);
        if (!rua.isEmpty() || numero != 0 || cep != 0) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("rua", rua);
            editor.putInt("numero", numero);
            editor.putInt("cep", cep);
            editor.putString("bairro", bairro);
            editor.putString("complemento", complemento);
            editor.commit();
        }

    }
}