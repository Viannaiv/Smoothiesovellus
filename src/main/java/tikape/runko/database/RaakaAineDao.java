/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;
import java.sql.*;
import java.util.*;
import tikape.runko.domain.RaakaAine;

/**
 *
 * @author Vivianna
 */
public class RaakaAineDao implements Dao<RaakaAine, Integer> {
    
    private Database database;

    public RaakaAineDao(Database database) {
        this.database = database;
    }

    @Override
    public RaakaAine findOne(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM RaakaAine "
                + "WHERE id = ?");
        stmt.setInt(1, key);
        
        ResultSet rs = stmt.executeQuery();
        if(!rs.next()) {
            return null;
        }
        
        RaakaAine ra = new RaakaAine(rs.getInt("id"), rs.getString("nimi"));
        
        rs.close();
        stmt.close();
        conn.close();
        
        return ra;
    }
    
    public RaakaAine findOneOnName(String nimi) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM RaakaAine "
                + "WHERE nimi = '?'");
        stmt.setString(1, nimi);
        
        ResultSet rs = stmt.executeQuery();
        if(!rs.next()) {
            return null;
        }
        
        RaakaAine ra = new RaakaAine(rs.getInt("id"), rs.getString("nimi"));
        
        rs.close();
        stmt.close();
        conn.close();
        
        return ra;
    }

    @Override
    public List<RaakaAine> findAll() throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM RaakaAine");
        
        ResultSet rs = stmt.executeQuery();
        List<RaakaAine> raakaaineet = new ArrayList<>();
        
        while(rs.next()) {
            RaakaAine ra = new RaakaAine(rs.getInt("id"), rs.getString("nimi"));
            raakaaineet.add(ra);
        }
        
        rs.close();
        stmt.close();
        conn.close();
        
        return raakaaineet;
    }

    @Override
    public void delete(Integer key) throws SQLException {
//        Toteuta niin, että myös SmoothieRaaka-aine poistuu ehkä vois
//        toteutuu kahella deletellä ja mainissa molemmat.
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM RaakaAine "
                + "WHERE id = ?");
        stmt.setInt(1, key);
        
        stmt.executeUpdate();
        
        stmt.close();
        conn.close();
    }

    @Override
    public void saveOrUpdate(RaakaAine ra) throws SQLException {
        RaakaAine raakaA = findOneOnName(ra.getNimi());
        
        if(raakaA != null) {
            return;
        }
        
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO RaakaAine "
                + "(nimi) VALUES ('" + ra.getNimi()+ "')");
    }
    
}
