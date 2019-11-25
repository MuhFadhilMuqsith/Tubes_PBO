/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tubes_v4;

import java.security.SecureRandom;

/**
 *
 * @author _MFMq_PC
 */
public class Peminjaman {
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
    
    private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    private static final String NUMBER = "0123456789";

    private static final String DATA_FOR_RANDOM_STRING = CHAR_LOWER + CHAR_UPPER + NUMBER;
    private static SecureRandom random = new SecureRandom();

    public Peminjaman(String kodePeminjaman, String jenisPeminjaman, String namaPeminjam, String namaOrganisasi, String namaKegiatan, int idObjek, int jumlahBarang, String tanggalPeminjaman, String tanggalPengembalian, String status, String catatan, int idUser) {
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

    public Peminjaman() {
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

    
     public static String generateRandomString() {
        if (5 < 1) throw new IllegalArgumentException();

        StringBuilder sb = new StringBuilder(5);
        for (int i = 0; i < 5; i++) {

			// 0-62 (exclusive), random returns 0-61
            int rndCharAt = random.nextInt(DATA_FOR_RANDOM_STRING.length());
            char rndChar = DATA_FOR_RANDOM_STRING.charAt(rndCharAt);

            

            sb.append(rndChar);

        }

        return sb.toString();

    }

}
