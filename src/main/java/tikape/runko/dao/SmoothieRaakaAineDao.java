
package tikape.runko.dao;
import java.sql.*;
import java.util.*;
import tikape.runko.database.Database;
import tikape.runko.domain.SmoothieRaakaAine;

public class SmoothieRaakaAineDao implements Dao<SmoothieRaakaAine, Integer> {
    
    private final Database database;

    public SmoothieRaakaAineDao(Database database) {
        this.database = database;
    }

    @Override
    public SmoothieRaakaAine findOne(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public SmoothieRaakaAine findOne(Integer raakaAineID, Integer smoothieID) throws SQLException {
        
        try {
            Connection conn = database.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT *FROM SmoothieRaakaAine "
                    + "WHERE raaka_aine_id = ? AND smoothie_id = ?");
            stmt.setInt(1, raakaAineID);
            stmt.setInt(2, smoothieID);
            
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {

                SmoothieRaakaAine sra = new SmoothieRaakaAine(rs.getInt("raaka_aine_id"),
                        rs.getInt("smoothie_id"), rs.getInt("jarjestys"),
                        rs.getString("maara"), rs.getString("ohje"));

                rs.close();
                stmt.close();
                conn.close();

                return sra;
            }
            
        } catch (Exception ex) {
            System.out.println("Error>>" + ex);
        }
        
        return null;
    }

    @Override
    public List<SmoothieRaakaAine> findAll() throws SQLException {
        
        try {
            Connection conn = database.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM SmoothieRaakaAine");
            
            ResultSet rs = stmt.executeQuery();
            List<SmoothieRaakaAine> srat = new ArrayList<>();
            
            while(rs.next()) {
                SmoothieRaakaAine sra = new SmoothieRaakaAine(rs.getInt("raaka_aine_id"),
                        rs.getInt("smoothie_id"), rs.getInt("jarjestys"),
                        rs.getString("maara"), rs.getString("ohje"));
                srat.add(sra);
            }
            
            rs.close();
            stmt.close();
            conn.close();
            
            return srat;
            
        } catch (Exception ex) {
            System.out.println("Error>>" + ex);
        }
        
        return new ArrayList<>();
    }
    
    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public void delete(Integer raakaAineID, Integer smoothieID) throws SQLException {
        
        try {
            Connection conn = database.getConnection();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM SmoothieRaakaAine "
                    + "WHERE raaka_aine_id = ? AND smoothie_id = ?");
            stmt.setInt(1, raakaAineID);
            stmt.setInt(2, smoothieID);
            
            stmt.executeUpdate();
            
            stmt.close();
            conn.close();
            
        } catch (Exception ex) {
            System.out.println("Error>>" + ex);
        }
        
    }
    
    public void deleteWithSmoothieID(Integer smoothieID) throws SQLException {
        
        try {
            Connection conn = database.getConnection();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM SmoothieRaakaAine "
                    + "WHERE smoothie_id = ?");
            stmt.setInt(1, smoothieID);
            
            stmt.executeUpdate();
            
            stmt.close();
            conn.close();
            
        } catch (Exception ex) {
            System.out.println("Error>>" + ex);
        }
        
    }
    
    public void deleteWithRaakaAineID(Integer raakaAineID) throws SQLException {
        
        try {
            Connection conn = database.getConnection();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM SmoothieRaakaAine "
                    + "WHERE raaka_aine_id = ?");
            stmt.setInt(1, raakaAineID);
            
            stmt.executeUpdate();
            
            stmt.close();
            conn.close();
            
        } catch (Exception ex) {
            System.out.println("Error>>" + ex);
        }
        
    }

    @Override
    public void saveOrUpdate(SmoothieRaakaAine object) throws SQLException {
        
        SmoothieRaakaAine sra = findOne(object.getRaakaAineID(), object.getSmoothieID());
        if(sra != null) {
            return;
        }
        
        try {
            Connection conn = database.getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO SmoothieRaakaAine "
                    + "(raaka_aine_id, smoothie_id, jarjestys, maara, ohje) VALUES (?, ?, ?, ?, ?)");
            stmt.setInt(1, object.getRaakaAineID());
            stmt.setInt(2, object.getSmoothieID());
            stmt.setInt(3, object.getJarjestys());
            stmt.setString(4, object.getMaara());
            stmt.setString(5, object.getOhje());

            stmt.executeUpdate();

            stmt.close();
            conn.close();
            
        } catch (Exception ex) {
            System.out.println("Error>>" + ex);
        }
        
    }
    
    public int smoothiesContainingRaakaAine(Integer raakaAineID) throws SQLException {
         
        try {
            Connection conn = database.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(DISTINCT smoothie_id) "
                    + "AS lukumaara FROM SmoothieRaakaAine WHERE raaka_aine_id = ?");
            stmt.setInt(1, raakaAineID);
            
            ResultSet rs = stmt.executeQuery();
            
            rs.next();
            int lkm = rs.getInt("lukumaara");
            
            rs.close();
            stmt.close();
            conn.close();
            
            return lkm;
            
        } catch (Exception ex) {
            System.out.println("Error>>" + ex);
        }
        
        return 0;
    }

    public List<SmoothieRaakaAine> RaakaAineListInOrderForSmoothie(Integer smoothieID) throws SQLException {
        
        try {
            Connection conn = database.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * "
                    + "FROM RaakaAine, SmoothieRaakaAine "
                    + "WHERE id = raaka_aine_id "
                    + "AND smoothie_id = ? ORDER BY jarjestys");
            stmt.setInt(1, smoothieID);
            
            ResultSet rs = stmt.executeQuery();
            List<SmoothieRaakaAine> srat = new ArrayList<>();
            
            while(rs.next()) {
                SmoothieRaakaAine sra = new SmoothieRaakaAine(rs.getInt("raaka_aine_id"),
                        rs.getInt("smoothie_id"), rs.getInt("jarjestys"),
                        rs.getString("maara"), rs.getString("ohje"));
                sra.setNimi(rs.getString("nimi"));
                srat.add(sra);
            }
            
            rs.close();
            stmt.close();
            conn.close();
            
            return srat;
            
        } catch (Exception ex) {
            System.out.println("Error>>" + ex);
        }
        
        return new ArrayList<>();
    }

}
