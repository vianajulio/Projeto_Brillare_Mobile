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

public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.MyViewHolder>{

    private final RecyclerViewInterface recyclerViewInterface;

    Context context;
    ArrayList<Produto> produtos;

    public ViewAdapter(Context context, ArrayList<Produto> produtos, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.produtos = produtos;
        this.recyclerViewInterface = recyclerViewInterface;
    }


    @NonNull
    @Override
    public ViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.produto_item, parent, false);
        return new ViewAdapter.MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAdapter.MyViewHolder holder, int position) {
        holder.imageView.setImageResource(produtos.get(position).getImage());
        holder.textViewTitulo.setText(produtos.get(position).getTitulo());
        holder.textViewPreco.setText(Double.toString(produtos.get(position).getPreco()));
    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textViewTitulo;
        TextView textViewPreco;

        private MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageViewProdutoCard);
            textViewTitulo = itemView.findViewById(R.id.tituloProduto);
            textViewPreco = itemView.findViewById(R.id.precoProduto);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerViewInterface != null) {
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}
