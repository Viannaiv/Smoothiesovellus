
package tikape.runko.dao;
import java.sql.*;
import java.util.*;
import tikape.runko.database.Database;
import tikape.runko.domain.RaakaAine;


public class RaakaAineDao implements Dao<RaakaAine, Integer> {
    
    private Database database;

    public RaakaAineDao(Database database) {
        this.database = database;
    }

    @Override
    public RaakaAine findOne(Integer key) throws SQLException {
        
        try {
            Connection conn = database.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM RaakaAine "
                    + "WHERE id = ?");
            stmt.setInt(1, key);
            
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()) {
            
                RaakaAine ra = new RaakaAine(rs.getInt("id"), rs.getString("nimi"));

                rs.close();
                stmt.close();
                conn.close();

                return ra;
            }
        } catch (Exception ex) {
            System.out.println("Error>>" + ex);
        }
        
        return null;
    }
    
    public RaakaAine findByName(String nimi) throws SQLException {
        
        try {
            Connection conn = database.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM RaakaAine "
                    + "WHERE nimi = ?");
            stmt.setString(1, nimi);
            
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()) {
                
                RaakaAine ra = new RaakaAine(rs.getInt("id"), rs.getString("nimi"));

                rs.close();
                stmt.close();
                conn.close();

                return ra;
            }
        } catch (Exception ex) {
            System.out.println("Error>>" + ex);  
        }
        
        return null;
    }

    @Override
    public List<RaakaAine> findAll() throws SQLException {
        
        try {
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
            
        } catch (Exception ex) {
            System.out.println("Error>>" + ex);
        }
        
        return new ArrayList<>();
    }
    
    public List<RaakaAine> findAllAlphabetically() throws SQLException {
        
        try {
            Connection conn = database.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM RaakaAine "
                    + "ORDER BY nimi");
            
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
            
        } catch (Exception ex) {
            System.out.println("Error>>" + ex);
        }
        
        return new ArrayList<>();
    }

    @Override
    public void delete(Integer key) throws SQLException {
        
        try {
            Connection conn = database.getConnection();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM RaakaAine "
                    + "WHERE id = ?");
            stmt.setInt(1, key);
            
            stmt.executeUpdate();
            
            stmt.close();
            conn.close();
        } catch (Exception ex) {
            System.out.println("Error>>" + ex);
        }
    }

    @Override
    public void saveOrUpdate(RaakaAine ra) throws SQLException {
        
            RaakaAine raakaA = findByName(ra.getNimi());
            
            if(raakaA != null || ra.getNimi().isEmpty()) {
                return;
            }
            
        try {
            Connection conn = database.getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO RaakaAine "
                    + "(nimi) VALUES (?)");
            stmt.setString(1, ra.getNimi());
            
            stmt.executeUpdate();
            
            stmt.close();
            conn.close();
        } catch (Exception ex) {
            System.out.println("Error>>" + ex);
        }
        
    }
    
}
