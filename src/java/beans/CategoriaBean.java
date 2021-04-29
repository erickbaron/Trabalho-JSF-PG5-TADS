package beans;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import entities.Categoria;
import repository.CategoriaDaoRepository;
import repository.Config;

/**
 *
 * @author erickbaron
 */

@ManagedBean
public class CategoriaBean {
    
    private Integer idCategoria;
    private String descricao;
    private List<Categoria> listaCategorias = new ArrayList<>();
    
    CategoriaDaoRepository categoriaRepository = new CategoriaDaoRepository(Config.getConnection());
    
    @PostConstruct
    public void getInitial(){
        listaCategorias = this.getAll();
    }
    
    public void insert(Categoria categoria){
        categoriaRepository.insert(categoria);
        updatePrivateFields(categoria);
    }
    
    public void update(Categoria categoria){
        categoriaRepository.update(categoria);
        updatePrivateFields(categoria);
    }
    
    public void delete(Integer id){
        categoriaRepository.deleteById(id);
        clearPrivateFields();
    }
    
    public Categoria getById(Integer id){
        Categoria categoria = categoriaRepository.getById(id);
        updatePrivateFields(categoria);
        return categoria;
    }

    public List<Categoria> getByName(String descricao){
        listaCategorias = categoriaRepository.getByName(descricao);
        this.setNullOptionCategoria();
        return listaCategorias;
    }
    
    public List<Categoria> getAll(){
        listaCategorias = categoriaRepository.getAll();
        this.setNullOptionCategoria();
        return listaCategorias;
    }
    
    private void updatePrivateFields(Categoria obj){
        idCategoria = obj.getIdCategoria();
        descricao = obj.getDescricao();
    }

    private void clearPrivateFields(){
        idCategoria = null;
        descricao = null;
    }

    public Integer getIdcategoria() {
        return idCategoria;
    }

    public void setIdcategoria(Integer idcategoria) {
        this.idCategoria = idcategoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Categoria> getListaCategorias() {
        return listaCategorias;
    }
    
    private void setNullOptionCategoria() {
        if (listaCategorias != null && listaCategorias.get(0) != null
                && listaCategorias.get(0).getIdCategoria() != 0){
            listaCategorias.add(0, new Categoria(0,"---"));
        }
    }

}
