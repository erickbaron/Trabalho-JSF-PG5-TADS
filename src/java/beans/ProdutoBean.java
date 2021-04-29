package beans;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import entities.Categoria;
import entities.Produto;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.omg.CORBA.BAD_PARAM;
import repository.CategoriaDaoRepository;
import repository.ProdutoDaoRepository;
import repository.Config;

/**
 *
 * @author erickbaron
 */

@ManagedBean
@ViewScoped
public class ProdutoBean {
    
    private Integer idProduto;
    private String descricao = "";
    private String observacao;
    private Float preco;
    private Float estoque;
    private Integer idCategoria;
    
    private boolean getIsGetByCategoria;

    public boolean isGetIsGetByCategoria() {
        return getIsGetByCategoria;
    }

    public void setGetIsGetByCategoria(boolean getIsGetByCategoria) {
        this.getIsGetByCategoria = getIsGetByCategoria;
    }
    
    private List<Produto> listaProdutos = new ArrayList<>();
    
    ProdutoDaoRepository produtoRepository = new ProdutoDaoRepository(Config.getConnection());
    CategoriaDaoRepository categoriaRepository = new CategoriaDaoRepository(Config.getConnection());
    
    
    @PostConstruct
    public void init(){
        getByName(descricao);
    }
    
    public void get(){
        if (getIsGetByCategoria && (idCategoria != null && idCategoria != 0)) {
            getByIdCategoria(idCategoria);
        } else {
            getByName(descricao);
        }
    }
    
    public String redirectEditar(Integer idproduto){
        return "cadastros?faces-redirect=true&idProduto=" + idproduto;
    }
    
    public void loadFieldsByIdProduto(Integer idProduto){
        if (isValidInteger(idProduto)){
            getById(idProduto);
        }
    }
    
    public String redirectExclude(Integer idProduto){
        String returnPath = "";
        if (isValidInteger(idProduto)) {
            deleteById(idProduto);
            returnPath = "index?faces-redirect=true";
        }
        return returnPath;
    }
    
    public String salvar(){
        validate();
        String redirectTo = "";

        try {
            if (idProduto == null) {
                Produto prod = new Produto(descricao, observacao, preco, estoque, idCategoria);
            insert(prod);
            }else{
                Produto prod = new Produto(this.idProduto, this.descricao, this.observacao, this.preco, this.estoque, this.idCategoria);
            update(prod);
            }
            redirectTo = "index?faces-redirect=true";
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        
        return redirectTo;
    }
    
    public void insert(Produto produto){
        produtoRepository.insert(produto);
        updatePrivateFields(produto);
    }
    
    public void update(Produto produto){
        produtoRepository.update(produto);
        updatePrivateFields(produto);
    }
    
    public void deleteById(Integer id){
        produtoRepository.delete(id);
        clearPrivateFields();
    }
    
    public Produto getById(Integer id){
        Produto produto = produtoRepository.getById(id);
        updatePrivateFields(produto);
        return produto;
    }

    public List<Produto> getByName(String descricao){
        listaProdutos = produtoRepository.getByName(descricao);
        return listaProdutos;
    }
    
    public List<Produto> getAll(){
        listaProdutos = produtoRepository.getAll();
        return listaProdutos;
    }
    
    public List<Produto> getByIdCategoria(Integer idCategoria){
        listaProdutos = produtoRepository.getByIdCategoria(idCategoria);
        return listaProdutos;
    }
    
    private void updatePrivateFields(Produto obj){
        idProduto = obj.getIdProduto();
        descricao = obj.getDescricao();
        observacao = obj.getObservacao();
        preco = obj.getPreco();
        estoque = obj.getEstoque();
        idCategoria = obj.getIdCategoria();
    }
    
    private void clearPrivateFields(){
        idProduto = null;
        descricao = null;
        observacao = null;
        preco = null;
        estoque = null;
        idCategoria = null;
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

    public List<Produto> getListaProdutos() {
        return listaProdutos;
    }
    
    public String getCategoriaDescricao(Integer idCategoria){
        String descricaoCategoria = "";
        if (idCategoria == null){
            return descricaoCategoria;
        } 
        Categoria cat = categoriaRepository.getById(idCategoria);
        if (cat != null) {
            descricaoCategoria = cat.getDescricao();
        }
        return descricaoCategoria;
    }
    
    private boolean isValidInteger(Integer number){
        return (number instanceof Integer && number >0);
    }

    private String validate(){
        String msg = "";
        
        if (descricao == null || descricao.trim().equals("")) {
            msg = "Informe a Descrição";
            throw new BAD_PARAM(msg);
        }

        if (idCategoria == null || idCategoria == 0) {
            msg = "Selecione a Categoria";
            throw new BAD_PARAM(msg);
        } 

        if (preco == null) {
            msg = "Informe o Preço";
            throw new BAD_PARAM(msg);
        }
        
        if (estoque == null) {
            msg = "Informe a Quantidade em Estoque";
            throw new BAD_PARAM(msg);
        }
        
        if (observacao == null || observacao.trim().equals("")) {
            msg = "Informe a Observação";
            throw new BAD_PARAM(msg);
        }
        return msg;
    }
}
