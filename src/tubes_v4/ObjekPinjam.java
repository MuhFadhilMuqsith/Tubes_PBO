/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tubes_v4;

/**
 *
 * @author _MFMq_PC
 */
public class ObjekPinjam {
    private int id;
    private String namaObjek;
    private int jumlahTersedia;
    private int totalJumlah;


    public ObjekPinjam(int id, String namaObjek, int jumlahTersedia, int totalJumlah) {
        this.id = id;
        this.namaObjek = namaObjek;
        this.jumlahTersedia = jumlahTersedia;
        this.totalJumlah = totalJumlah;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamaObjek() {
        return namaObjek;
    }

    public void setNamaObjek(String namaObjek) {
        this.namaObjek = namaObjek;
    }

    public int getJumlahTersedia() {
        return jumlahTersedia;
    }

    public void setJumlahTersedia(int jumlahTersedia) {
        this.jumlahTersedia = jumlahTersedia;
    }

    public int getTotalJumlah() {
        return totalJumlah;
    }

    public void setTotalJumlah(int totalJumlah) {
        this.totalJumlah = totalJumlah;
    } 
    
}
