package classes;

import java.io.Serializable;

public class Produto implements Serializable {

    int image;
    String titulo;
    String descricao;
    int preco;

    public Produto(int image, String titulo, String descricao, int preco) {
        this.image = image;
        this.titulo = titulo;
        this.descricao = descricao;
        this.preco = preco;
    }

    public int getImage() {
        return image;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getPreco() {
        return preco;
    }
}
