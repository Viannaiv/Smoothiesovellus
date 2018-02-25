/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;
import java.sql.*;
import java.util.*;
import tikape.runko.domain.SmoothieRaakaAine;

/**
 *
 * @author Vivianna
 */
public class SmoothieRaakaAineDao implements Dao<SmoothieRaakaAine, Integer> {
    
    private Database database;

    public SmoothieRaakaAineDao(Database database) {
        this.database = database;
    }

    @Override
    public SmoothieRaakaAine findOne(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public SmoothieRaakaAine findOne(Integer raakaAineID, Integer smoothieID) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT *FROM SmoothieRaakaAine "
                + "WHERE raaka_aine_id = ? AND smoothie_id = ?");
        stmt.setInt(1, raakaAineID);
        stmt.setInt(2, smoothieID);
        
        ResultSet rs = stmt.executeQuery();
        if(!rs.next()) {
            return null;
        }
        
        SmoothieRaakaAine sra = new SmoothieRaakaAine(rs.getInt("raaka_aine_id"),
                rs.getInt("smoothie_id"), rs.getInt("jarjestys"), 
                rs.getString("maara"), rs.getString("ohje"));
        
        rs.close();
        stmt.close();
        conn.close();
        
        return sra;
    }

    @Override
    public List<SmoothieRaakaAine> findAll() throws SQLException {
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
        
    }
    
    public int smoothiesContainingRaakaAine(Integer raakaAineID) throws SQLException {
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
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void delete(Integer raakaAineID, Integer smoothieID) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM SmoothieRaakaAine "
                + "WHERE raaka_aine_id = ? AND smoothie_id = ?");
        stmt.setInt(1, raakaAineID);
        stmt.setInt(2, smoothieID);
        
        stmt.executeUpdate();
        
        stmt.close();
        conn.close();
        
    }
    
    public void deleteWithSmoothieID(Integer smoothieID) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE * FROM SmoothieRaakaAine "
                + "WHERE smoothie_id = ?");
        stmt.setInt(1, smoothieID);
        
        stmt.executeUpdate();
        
        stmt.close();
        conn.close();
        
    }
    
    public void deleteWithRaakaAineID(Integer raakaAineID) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE * FROM SmoothieRaakaAine "
                + "WHERE raaka_aine_id = ?");
        stmt.setInt(1, raakaAineID);
        
        stmt.executeUpdate();
        
        stmt.close();
        conn.close();
        
    }

    @Override
    public void saveOrUpdate(SmoothieRaakaAine object) throws SQLException {
        SmoothieRaakaAine sra = findOne(object.getRaakaAineID(), object.getSmoothieID());
        
        if(sra != null) {/* mahdollisesti muuta niin, että voidaan updatetata tietoja
            kuten jarjestys ja ohje ja maara*/
            return;
        }
        
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO SmoothieRaakaAine "
                + "(raaka_aine_id, smoothie_id, jarjestys, maara, ohje) VALUES (?, ?, ?, ?, ?)");
//        Olettaa nyt että on jo valmiiksi käsitelty null arvot? missa ne pitaisi muokata?
        stmt.setInt(1, object.getRaakaAineID());
        stmt.setInt(2, object.getSmoothieID());
        stmt.setInt(3, object.getJarjestys());
        stmt.setString(4, object.getMaara());
        stmt.setString(5, object.getOhje());
        
        stmt.executeUpdate();
        
        stmt.close();
        conn.close();
        
    }
    //muuta nimi
    public List<SmoothieRaakaAine> smoothieKohtainenListaJarjestyksessa(Integer smoothieID) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM SmoothieRaakaAine "
                + "WHERE smoothie_id = ? ORDER BY jarjestys");
        stmt.setInt(1, smoothieID);
        
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
        
    }
//    Tarvitaanko tämämetodi ja voisiko jarjestaa vielä raaka_aineidn 
//    perusteella vai tuleeko oikea jarjestys
    //huom! jarjestys haku ratkaistaan order by tupla ehdolla joss aensin 
//    jarjestys numero sitten raaka-aine id.
//    täytyy voida päivittää?  ei saa olla duplikaatteja, 
//    taytyy olla olemassa smoothie ja raakaaine joihin viittaa
    /*
     lista.add("CREATE TABLE SmoothieRaakaAine (raaka_aine_id integer, smoothie_id integer,"
                + " jarjestys integer, maara varchar(100), ohje varchar(200), "
                + "FOREIGN KEY (raaka_aine_id) REFERENCES RaakaAine(id), "
                + "FOREIGN KEY (smoothie_id) REFERENCES Smoothie(id));");
    */
}
