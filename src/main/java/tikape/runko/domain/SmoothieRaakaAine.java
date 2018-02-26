/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;

/**
 *
 * @author Vivianna
 */
public class SmoothieRaakaAine {
    
    private int raakaAineID;
    private int smoothieID;
    private int jarjestys;
    private String maara;
    private String ohje;
//    lisaa kaytossa?

    public SmoothieRaakaAine(int raakaAineID, int smoothieID, int jarjestys, String maara, String ohje) {
        this.raakaAineID = raakaAineID;
        this.smoothieID = smoothieID;
        this.jarjestys = jarjestys;
        this.maara = maara;
        this.ohje = ohje;
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

}
