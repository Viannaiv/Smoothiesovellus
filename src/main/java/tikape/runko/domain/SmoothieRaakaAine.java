
package tikape.runko.domain;


public class SmoothieRaakaAine {
    
    private int raakaAineID;
    private int smoothieID;
    private int jarjestys;
    private String maara;
    private String ohje;
    private String nimi;

    public SmoothieRaakaAine(int raakaAineID, int smoothieID, int jarjestys, String maara, String ohje) {
        this.raakaAineID = raakaAineID;
        this.smoothieID = smoothieID;
        this.jarjestys = jarjestys;
        this.maara = maara;
        this.ohje = ohje;
        
        if(this.ohje.isEmpty()){
            this.ohje = "-";
        }
        
        if(this.maara.isEmpty()) {
            this.maara = "omavalintainen";
        }
    }

    public int getRaakaAineID() {
        return raakaAineID;
    }

    public void setRaakaAineID(int raakaAineID) {
        this.raakaAineID = raakaAineID;
    }

    public int getSmoothieID() {
        return smoothieID;
    }

    public void setSmoothieID(int smoothieID) {
        this.smoothieID = smoothieID;
    }

    public int getJarjestys() {
        return jarjestys;
    }

    public void setJarjestys(int jarjestys) {
        this.jarjestys = jarjestys;
    }

    public String getMaara() {
        return maara;
    }

    public void setMaara(String maara) {
        this.maara = maara;
    }

    public String getOhje() {
        return ohje;
    }

    public void setOhje(String ohje) {
        this.ohje = ohje;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }
    
}
