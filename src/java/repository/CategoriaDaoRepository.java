package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import entities.Categoria;
import java.util.logging.Logger;

/**
 *
 * @author erickbaron
 */
public class CategoriaDaoRepository {
    
    private Connection connection;
    
    public CategoriaDaoRepository(Connection conn) {
        this.connection = conn;
    }

    public void insert(Categoria obj) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            String sql = "INSERT INTO categoria(descricao) " +
                         "VALUES(?)";
            st = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, obj.getDescricao());
            
            int rowsAffected = st.executeUpdate();
            
            if (rowsAffected > 0) {
                rs = st.getGeneratedKeys();
                if (rs.next()){
                    int id = rs.getInt(1);
                    obj.setIdCategoria(id);
                }
            } else {
                Logger.getLogger("Error! No rows were affected!");  
            }
            
        } catch (SQLException e){
            Logger.getLogger(e.getMessage());
        } finally {
            Config.closeResultSet(rs);
            Config.closeStatement(st);
        } 
    }

    public void update(Categoria obj) {
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement("UPDATE categoria SET "
                                           + "descricao = ? "
                                           + "WHERE idcategoria = ?");
            st.setString(1, obj.getDescricao());
            st.setInt(2, obj.getIdCategoria());

            st.executeUpdate();

        } catch (SQLException e){
            Logger.getLogger(e.getMessage());
        } finally {
            Config.closeStatement(st);
        }
    }

    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try{
            st = connection.prepareStatement("DELETE FROM categoria" 
                                           + "WHERE idcategoria = ?");
            st.setInt(1, id);
            st.executeUpdate();

        } catch(SQLException e){
            Logger.getLogger(e.getMessage());
        } finally {
            Config.closeStatement(st);
        }
    }

    public Categoria getById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = connection.prepareStatement("SELECT c.* FROM categoria c "
                                           + "WHERE c.idcategoria = ?");
            st.setInt(1, id);
            rs = st.executeQuery();
            
            if(rs.next()){
                Categoria obj = instantiateCategoria(rs);
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

    public List<Categoria> getByName(String descricao) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            String sql = "SELECT c.* FROM categoria c "
                       + "WHERE UPPER(c.descricao) LIKE UPPER('%" + descricao + "%')";
            st = connection.prepareStatement(sql);
            rs = st.executeQuery();
            
            List<Categoria> categorias = new ArrayList<>();
            while (rs.next()){
                Categoria categoria = instantiateCategoria(rs);
                categorias.add(categoria);
            }
            return categorias;
            
        } catch(SQLException e){
            Logger.getLogger(e.getMessage());
        } finally {
            Config.closeResultSet(rs);
            Config.closeStatement(st);
        }
        return null;
    }

    public List<Categoria> getAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            String sql = "SELECT c.* FROM categoria c "
                       + "ORDER BY c.idcategoria";
            st = connection.prepareStatement(sql);
            rs = st.executeQuery();
            
            List<Categoria> categorias = new ArrayList<>();
            while (rs.next()){
                Categoria categoria = instantiateCategoria(rs);
                categorias.add(categoria);
            }
            return categorias;
            
        } catch(SQLException e){
            Logger.getLogger(e.getMessage());
        } finally {
            Config.closeResultSet(rs);
            Config.closeStatement(st);
        }
        return null;
    }
    
    private Categoria instantiateCategoria(ResultSet rs) throws SQLException {
        Categoria obj = new Categoria();
        obj.setIdCategoria(rs.getInt("idcategoria"));
        obj.setDescricao(rs.getString("descricao"));
        return obj;
    }
    
}
