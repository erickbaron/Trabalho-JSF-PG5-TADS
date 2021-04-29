package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import entities.Produto;
import java.util.logging.Logger;

/**
 *
 * @author erickbaron
 */
public class ProdutoDaoRepository {
    
    private Connection connection;
    
    public ProdutoDaoRepository(Connection conn) {
        this.connection = conn;
    }

    public void insert(Produto obj) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            String sql = "INSERT INTO produto(descricao, observacao, preco, estoque, idcategoria) " +
                         "VALUES(?, ?, ?, ?, ?)";
            st = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, obj.getDescricao());
            st.setString(2, obj.getObservacao());
            st.setFloat(3, obj.getPreco());
            st.setFloat(4, obj.getEstoque());
            st.setInt(5, obj.getIdCategoria());
            
            st.executeUpdate();
            
            rs = st.getGeneratedKeys();
            if (rs.next()){
                int id = rs.getInt(1);
                obj.setIdProduto(id);
            }
        } catch (SQLException e){
            Logger.getLogger(e.getMessage());
        } finally {
            Config.closeResultSet(rs);
            Config.closeStatement(st);
        } 
    }

    public void update(Produto obj) {
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement("UPDATE produto SET "
                                            + "descricao = ?, "
                                            + "observacao = ?, "
                                            + "preco = ?, "
                                            + "estoque = ?, "
                                            + "idcategoria = ? "
                                            + "WHERE idproduto = ?");
            st.setString(1, obj.getDescricao());
            st.setString(2, obj.getObservacao());
            st.setFloat(3, obj.getPreco());
            st.setFloat(4, obj.getEstoque());
            st.setInt(5, obj.getIdCategoria());
            st.setInt(6, obj.getIdProduto());

            st.executeUpdate();

        } catch (SQLException e){
            Logger.getLogger(e.getMessage());
        } finally {
            Config.closeStatement(st);
        }
    }

    public void delete(Integer id) {
        PreparedStatement st = null;
        try{
            st = connection.prepareStatement("DELETE FROM produto "
                                            +"WHERE idproduto = ?");
            st.setInt(1, id);
            st.executeUpdate();

        } catch(SQLException e){
            Logger.getLogger(e.getMessage());
        } finally {
            Config.closeStatement(st);
        }
    }

    public Produto getById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = connection.prepareStatement("SELECT p.* FROM produto p "
                                           + "WHERE p.idproduto = ?");
            st.setInt(1, id);
            rs = st.executeQuery();
            
            if(rs.next()){
                Produto obj = getProduto(rs);
                return obj;
            }
            return null;
            
        } catch(SQLException e){
            Logger.getLogger(e.getMessage());
        } finally {
            Config.closeResultSet(rs);
            Config.closeStatement(st);
        }
        return null;
    }

    public List<Produto> getByName(String descricao) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            String sql = "SELECT p.* FROM produto p "
                       + "WHERE upper(p.descricao) LIKE UPPER('%" + descricao + "%') "
                       + "ORDER BY p.idproduto";
            st = connection.prepareStatement(sql);
            rs = st.executeQuery();
            
            List<Produto> produtos = new ArrayList<>();
            while (rs.next()){
                Produto produto = getProduto(rs);
                produtos.add(produto);
            }
            return produtos;
            
        } catch(SQLException e){
            Logger.getLogger(e.getMessage());
        } finally {
            Config.closeResultSet(rs);
            Config.closeStatement(st);
        }
        return null;
    }

    public List<Produto> getAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            String sql = "SELECT p.* FROM produto p "
                       + "ORDER BY p.idproduto";
            st = connection.prepareStatement(sql);
            rs = st.executeQuery();
            
            List<Produto> produtos = new ArrayList<>();
            while (rs.next()){
                Produto produto = getProduto(rs);
                produtos.add(produto);
            }
            return produtos;
            
        } catch(SQLException e){
            Logger.getLogger(e.getMessage());
        } finally {
            Config.closeResultSet(rs);
            Config.closeStatement(st);
        }
        return null;
    }
    
    public List<Produto> getByIdCategoria(Integer idCategoria) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            String sql = "SELECT * FROM produto p "
                       + "WHERE p.idcategoria = " + idCategoria + " "
                       + "ORDER BY p.idproduto";
            st = connection.prepareStatement(sql);
            rs = st.executeQuery();
            
            List<Produto> produtos = new ArrayList<>();
            while (rs.next()){
                Produto produto = getProduto(rs);
                produtos.add(produto);
            }
            return produtos;
            
        } catch(SQLException e){
            Logger.getLogger(e.getMessage());
        } finally {
            Config.closeResultSet(rs);
            Config.closeStatement(st);
        }
        return null;
    }
    
    private Produto getProduto(ResultSet rs) throws SQLException {
        Produto obj = new Produto();
        obj.setIdProduto(rs.getInt("idproduto"));
        obj.setDescricao(rs.getString("descricao"));
        obj.setObservacao(rs.getString("observacao"));
        obj.setPreco(rs.getFloat("preco"));
        obj.setEstoque(rs.getFloat("estoque"));
        obj.setIdCategoria(rs.getInt("idcategoria"));
        return obj;
    }
}
