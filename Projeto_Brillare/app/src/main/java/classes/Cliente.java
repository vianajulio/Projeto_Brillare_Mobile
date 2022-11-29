package classes;

import java.io.Serializable;

public class Cliente implements Serializable {

    private String usuario;
    private String email;
    private String senha;
    private String endereco;

    public Cliente(String usuario, String email, String senha) {
        this.usuario = usuario;
        this.email = email;
        this.senha = senha;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public String getEndereco() {
        return endereco;
    }
}
