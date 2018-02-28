
package tikape.runko.dao;
import java.sql.*;
import java.util.*;
import tikape.runko.database.Database;
import tikape.runko.domain.Smoothie;


public class SmoothieDao implements Dao<Smoothie, Integer> {
    
    private Database database;

    public SmoothieDao(Database database) {
        this.database = database;
    }

    @Override
    public Smoothie findOne(Integer key) throws SQLException {
        
        try {
            Connection conn = database.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Smoothie "
                    + "WHERE id = ?");
            stmt.setInt(1, key);
            
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()) {
            
                Smoothie s = new Smoothie(rs.getInt("id"), rs.getString("nimi"));

                rs.close();
                stmt.close();
                conn.close();

                return s;
            }
            
        } catch (Exception ex) {
            System.out.println("Error>>" + ex);
        }
        
        return null;
    }
    
    public Smoothie findByName(String nimi) throws SQLException {
        
        try {
            Connection conn = database.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Smoothie "
                    + "WHERE nimi = ?");
            stmt.setString(1, nimi);
            
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
            
                Smoothie s = new Smoothie(rs.getInt("id"), rs.getString("nimi"));

                rs.close();
                stmt.close();
                conn.close();

                return s;
            }
            
        } catch (Exception ex) {
            System.out.println("Error>>" + ex);
        }
        
        return null;
    }

    @Override
    public List<Smoothie> findAll() throws SQLException {
        
        try {
            Connection conn = database.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Smoothie");
            
            ResultSet rs = stmt.executeQuery();
            List<Smoothie> smoothiet = new ArrayList<>();
            while(rs.next()) {
                Smoothie s = new Smoothie(rs.getInt("id"), rs.getString("nimi"));
                smoothiet.add(s);
            }
            
            rs.close();
            stmt.close();
            conn.close();
            
            return smoothiet;
            
        } catch (Exception ex) {
            System.out.println("Error>>" + ex);
        }
        
        return new ArrayList<>();
    }
    
    public List<Smoothie> findAllAlphabetically() throws SQLException {
        try {
            Connection conn = database.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Smoothie "
                    + "ORDER BY nimi");
            
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
            
        } catch (Exception ex) {
            System.out.println("Error>>" + ex);
        }
        
        return new ArrayList<>();
    }

    @Override
    public void delete(Integer key) throws SQLException {
        
        try {
            Connection conn = database.getConnection();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Smoothie "
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
    public void saveOrUpdate(Smoothie smoothie) throws SQLException {
       
            Smoothie s = findByName(smoothie.getNimi());
            
            if(s != null || smoothie.getNimi().isEmpty()) {
                return;
            }
            
        try {
            Connection conn = database.getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Smoothie "
                    + "(nimi) VALUES (?)");
            stmt.setString(1, smoothie.getNimi());
            
            stmt.executeUpdate();
            
            stmt.close();
            conn.close();
            
        } catch (Exception ex) {
            System.out.println("Error>>" + ex);
        }
    }
    
    public int countAllSmoothies() throws SQLException {
        try {
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
        } catch (Exception ex) {
            System.out.println("Error>>" + ex);
        }
        
        return 0;
    }
    
}
