/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;
import java.sql.*;
import java.util.*;
import tikape.runko.domain.Smoothie;

/**
 *
 * @author Vivianna
 */
public class SmoothieDao implements Dao<Smoothie, Integer> {
    
    private Database database;

    public SmoothieDao(Database database) {
        this.database = database;
    }

    @Override
    public Smoothie findOne(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Smoothie "
                + "WHERE id = ?");
        stmt.setInt(1, key);
        
        ResultSet rs = stmt.executeQuery();
        
        if(!rs.next()) {
            return null;
        }
        
        Smoothie s = new Smoothie(rs.getInt("id"), rs.getString("nimi"));
        
        rs.close();
        stmt.close();
        conn.close();
        
        return s;
    }
    
    public Smoothie findByName(String nimi) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Smoothie "
                + "WHERE nimi = '?'");
        stmt.setString(1, nimi);
        
        ResultSet rs = stmt.executeQuery();
        if(!rs.next()){
            return null;
        }
        
        Smoothie s = new Smoothie(rs.getInt("id"), rs.getString("nimi"));
        
        rs.close();
        stmt.close();
        conn.close();
        
        return s;
    }

    @Override
    public List<Smoothie> findAll() throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Smoothie");
        
        ResultSet rs = stmt.executeQuery();
        List<Smoothie> smoothiet = new ArrayList<>();
        if(rs.next()) {
            Smoothie s = new Smoothie(rs.getInt("id"), rs.getString("nimi"));
            smoothiet.add(s);
        }
        
        rs.close();
        stmt.close();
        conn.close();
        
        return smoothiet;
    }

    @Override
    public void delete(Integer key) throws SQLException {
//        HUom tulee poistua myös Smoothieraakaaineesta mainissa todnäk
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Smoothie "
                + "WHERE id = ?");
        stmt.setInt(1, key);
        
        stmt.executeUpdate();
        
        stmt.close();
        conn.close();
        
    }

    @Override
    public void saveOrUpdate(Smoothie smoothie) throws SQLException {
        Smoothie s = findByName(smoothie.getNimi());
        
        if(s != null) {
            return;
        }
        
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Smoothie "
                + "(nimi) VALUES ('?')");
        stmt.setString(1, smoothie.getNimi());
        
        stmt.executeUpdate();
        
        stmt.close();
        conn.close();
    }
    
    public int countAllSmoothies() throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) AS smoothieita "
                + "FROM Smoothie");
        ResultSet rs = stmt.executeQuery();
        
        rs.next();
        int lkm = rs.getInt("smoothieita");
        
        rs.close();
        stmt.close();
        conn.close();
        
        return lkm;
    }
    
}
