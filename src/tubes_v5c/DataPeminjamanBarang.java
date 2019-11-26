/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tubes_v5c;

import java.util.Date;


/**
 *
 * @author _MFMq_PC
 */
public class DataPeminjamanBarang {
    private String kodePeminjaman;
    private String jenisPeminjaman;
    private String namaPeminjam;
    private String namaOrganisasi;
    private String namaKegiatan;
    private int idObjek;
    private int jumlahBarang;
    private String tanggalPeminjaman;
    private String tanggalPengembalian;
    private String status;
    private String catatan;
    private int idUser;

    public DataPeminjamanBarang(String kodePeminjaman, String jenisPeminjaman, String namaPeminjam, String namaOrganisasi, String namaKegiatan, int idObjek, int jumlahBarang, String tanggalPeminjaman, String tanggalPengembalian, String status, String catatan, int idUser) {
        this.kodePeminjaman = kodePeminjaman;
        this.jenisPeminjaman = jenisPeminjaman;
        this.namaPeminjam = namaPeminjam;
        this.namaOrganisasi = namaOrganisasi;
        this.namaKegiatan = namaKegiatan;
        this.idObjek = idObjek;
        this.jumlahBarang = jumlahBarang;
        this.tanggalPeminjaman = tanggalPeminjaman;
        this.tanggalPengembalian = tanggalPengembalian;
        this.status = status;
        this.catatan = catatan;
        this.idUser = idUser;
    }

    public String getKodePeminjaman() {
        return kodePeminjaman;
    }

    public void setKodePeminjaman(String kodePeminjaman) {
        this.kodePeminjaman = kodePeminjaman;
    }

    public String getJenisPeminjaman() {
        return jenisPeminjaman;
    }

    public void setJenisPeminjaman(String jenisPeminjaman) {
        this.jenisPeminjaman = jenisPeminjaman;
    }

    public String getNamaPeminjam() {
        return namaPeminjam;
    }

    public void setNamaPeminjam(String namaPeminjam) {
        this.namaPeminjam = namaPeminjam;
    }

    public String getNamaOrganisasi() {
        return namaOrganisasi;
    }

    public void setNamaOrganisasi(String namaOrganisasi) {
        this.namaOrganisasi = namaOrganisasi;
    }

    public String getNamaKegiatan() {
        return namaKegiatan;
    }

    public void setNamaKegiatan(String namaKegiatan) {
        this.namaKegiatan = namaKegiatan;
    }

    public int getIdObjek() {
        return idObjek;
    }

    public void setIdObjek(int idObjek) {
        this.idObjek = idObjek;
    }

    public int getJumlahBarang() {
        return jumlahBarang;
    }

    public void setJumlahBarang(int jumlahBarang) {
        this.jumlahBarang = jumlahBarang;
    }

    public String getTanggalPeminjaman() {
        return tanggalPeminjaman;
    }

    public void setTanggalPeminjaman(String tanggalPeminjaman) {
        this.tanggalPeminjaman = tanggalPeminjaman;
    }

    public String getTanggalPengembalian() {
        return tanggalPengembalian;
    }

    public void setTanggalPengembalian(String tanggalPengembalian) {
        this.tanggalPengembalian = tanggalPengembalian;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    

    
    
    
    
}