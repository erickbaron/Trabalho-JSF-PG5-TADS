package entities;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author erickbaron
 */
public class Produto implements Serializable {
    
    private Integer idProduto;
    private String descricao;
    private String observacao;
    private Float preco;
    private Float estoque;
    private Integer idCategoria;

    public Produto() {
    }

    public Produto(Integer idProduto, String descricao, String observacao, Float preco, Float estoque, Integer idCategoria) {
        this.idProduto = idProduto;
        this.descricao = descricao;
        this.observacao = observacao;
        this.preco = preco;
        this.estoque = estoque;
        this.idCategoria = idCategoria;
    }
    
    public Produto(String descricao, String observacao, Float preco, Float estoque, Integer idCategoria) {
        this.descricao = descricao;
        this.observacao = observacao;
        this.preco = preco;
        this.estoque = estoque;
        this.idCategoria = idCategoria;
    }

    public Integer getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Float getPreco() {
        return preco;
    }

    public void setPreco(Float preco) {
        this.preco = preco;
    }

    public Float getEstoque() {
        return estoque;
    }

    public void setEstoque(Float estoque) {
        this.estoque = estoque;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.idProduto);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Produto produto = (Produto) obj;
        if (!Objects.equals(this.idProduto, produto.idProduto)) {
            return false;
        }
        return true;
    }
    
}
