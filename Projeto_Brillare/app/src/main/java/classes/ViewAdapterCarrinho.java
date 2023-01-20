package classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projeto_brillare.R;

import java.util.ArrayList;

public class ViewAdapterCarrinho extends RecyclerView.Adapter<ViewAdapterCarrinho.ViewHolder> {

    ArrayList<Produto> produtos;

    public ViewAdapterCarrinho(ArrayList<Produto> produtosCarrinho) {

        this.produtos = produtosCarrinho;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.produtos_carrinho, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.prodCarrinhoImagem.setImageResource(produtos.get(position).getImage());
        holder.prodCarrinhoTitulo.setText(produtos.get(position).getTitulo());
        holder.prodCarrinhoDescricao.setText(produtos.get(position).getDescricao());
        holder.prodCarrinhoPreco.setText(Double.toString(produtos.get(position).getPreco()));
    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView prodCarrinhoImagem;
        TextView prodCarrinhoTitulo;
        TextView prodCarrinhoDescricao;
        TextView prodCarrinhoPreco;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            prodCarrinhoImagem = itemView.findViewById(R.id.prodCarrinhoImagem);
            prodCarrinhoTitulo = itemView.findViewById(R.id.prodCarrinhoTitulo);
            prodCarrinhoDescricao = itemView.findViewById(R.id.prodCarrinhoDescricao);
            prodCarrinhoPreco = itemView.findViewById(R.id.prodCarrinhoPreco);

        }
    }

}
