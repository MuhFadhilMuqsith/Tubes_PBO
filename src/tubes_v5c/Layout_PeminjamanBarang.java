/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tubes_v5c;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author _MFMq_PC
 */
public class Layout_PeminjamanBarang extends javax.swing.JFrame {
    private DefaultTableModel modelList = new DefaultTableModel();
    private DefaultTableModel modelPinjam = new DefaultTableModel();
    private SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
    private Connection conn;
    private ArrayList<ObjekPinjam> listObjek;
    private ArrayList<Peminjaman> listPeminjaman = new ArrayList<>();
     
    private String nama;
    private String username;
    private String id;
    private int idBarangpinjam;
    


    /**
     * Creates new form Layout_PeminjamanBarang
     */
    public Layout_PeminjamanBarang(User user) {
        initComponents();
        loadKolomList();
        tblListBarang.setModel(modelList);
        tblBarangPinjam.setModel(modelPinjam);
        Koneksi koneksi = new Koneksi();
        conn = koneksi.bukaKoneksi();
        loadObjek();
        tampilListBarang();
        Date now = Date.from(Instant.now());
        Date minDate = addDays(now);
        dtAwal.setMinSelectableDate(minDate);
        dtAkhir.setMinSelectableDate(minDate);
        nama = user.getNama();
        username = user.getUsername();
        id = user.getId();
        
        
    }
    
    private static Date addDays(Date date) {
                GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.DATE, 3);
		return cal.getTime();
	}
    
    private void loadKolomList(){
        modelList.addColumn("Id Barang");
        modelList.addColumn("Nama Barang");
        modelList.addColumn("Jumlah Tersedia");
        modelPinjam.addColumn("Id Barang");
        modelPinjam.addColumn("Nama Barang");
        modelPinjam.addColumn("Jumlah Pinjam");
    }
    
    private void loadObjek(){
        if (conn != null){
            listObjek = new ArrayList<>();
            String query = "SELECT id_objek,nama_objek,jumlah_tersedia,total_jumlah FROM objek_pinjam WHERE jenis_objek = 'barang';";
            try {
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    int id = rs.getInt("id_objek");
                    String namaBarang = rs.getString("nama_objek");
                    int jumlah_tersedia = rs.getInt("jumlah_tersedia");
                    int total = rs.getInt("total_jumlah");
                    ObjekPinjam objek = new ObjekPinjam(id,namaBarang,jumlah_tersedia,total);
                    listObjek.add(objek);
                }
                rs.close();
                ps.close();
            }
            catch (SQLException ex){
                Logger.getLogger(Layout_PeminjamanBarang.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void tampilListBarang(){
        modelList.setRowCount(0);
        for(ObjekPinjam barang : listObjek){
            modelList.addRow(new Object[]{barang.getId(),barang.getNamaObjek(),barang.getJumlahTersedia()});
        }
    }
    
    private void insertPeminjaman(){
        if (conn != null){
            try{
                int hasil = 0;
                for(int i = 0 ; i < listPeminjaman.size();i++){
                    String query = "INSERT INTO data_peminjaman "
                        + "(kode_peminjaman,jenis_peminjaman,nama_peminjam,nama_organisasi,nama_kegiatan,id_objek,jumlah_barang,tanggal_peminjaman,tanggal_pengembalian,status,catatan,id_user) VALUES"
                        + "(?,?,?,?,?,?,?,?,?,?,?,?)";
                    PreparedStatement ps = conn.prepareStatement(query);
                    ps.setString(1,listPeminjaman.get(i).getKodePeminjaman());
                    ps.setString(2,listPeminjaman.get(i).getJenisPeminjaman());
                    ps.setString(3,listPeminjaman.get(i).getNamaPeminjam());
                    ps.setString(4,listPeminjaman.get(i).getNamaOrganisasi());
                    ps.setString(5,listPeminjaman.get(i).getNamaKegiatan());
                    ps.setInt(6,listPeminjaman.get(i).getIdObjek());
                    ps.setInt(7,listPeminjaman.get(i).getJumlahBarang());
                    ps.setString(8,listPeminjaman.get(i).getTanggalPeminjaman());
                    ps.setString(9,listPeminjaman.get(i).getTanggalPengembalian());
                    ps.setString(10,listPeminjaman.get(i).getStatus());
                    ps.setString(11,listPeminjaman.get(i).getCatatan());
                    ps.setInt(12,listPeminjaman.get(i).getIdUser());
                    hasil = ps.executeUpdate();
                }
                
                if (hasil > 0){
                    User user = new User();
                    user.setUsername(username);
                    user.setId(id);
                    user.setNama(nama);
                    JOptionPane.showMessageDialog(this,"Peminjaman Sedang Diproses !");
                    this.dispose();
                    new Layout_DashboardUser(user).setVisible(true);
                }
            }
            catch(SQLException ex){
                 Logger.getLogger(Layout_PeminjamanBarang.class.getName()).log(Level.SEVERE, null, ex);
                 
                 JOptionPane.showMessageDialog(this,"User Telah Terdaftar");
            }
        }
    }
    
    private void resetBarang(){
        tfNamaBarang.setText("");
        tfJumlahPeminjaman.setText("0");
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tfBatasPengembalian = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblListBarang = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        tfNamaPeminjam = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        tfNamaOrganisasi = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        tfNamaKegiatan = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        dtAkhir = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblBarangPinjam = new javax.swing.JTable();
        btnHapus = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnProses = new javax.swing.JButton();
        dtAwal = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        tfNamaBarang = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        tfJumlahPeminjaman = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        btnTambah = new javax.swing.JButton();

        tfBatasPengembalian.setEditable(false);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Form Peminjaman Barang");
        jLabel1.setToolTipText("");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "List Barang"));

        tblListBarang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblListBarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblListBarangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblListBarang);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Data Peminjaman"));

        jLabel2.setText("Nama Peminjam :");

        jLabel3.setText("Nama Organisasi :");

        jLabel4.setText("Tanggal Peminjaman :");

        jLabel5.setText("Nama Kegiatan :");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("-");

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Barang Yang Dipinjam"));

        tblBarangPinjam.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(tblBarangPinjam);

        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(186, Short.MAX_VALUE)
                .addComponent(btnHapus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHapus)
                    .addComponent(btnEdit)))
        );

        btnProses.setText("Proses Peminjaman");
        btnProses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProsesActionPerformed(evt);
            }
        });

        jButton1.setText("Back");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGap(58, 58, 58)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5))
                                .addGap(42, 42, 42)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(dtAwal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel6)
                                        .addGap(7, 7, 7)
                                        .addComponent(dtAkhir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(tfNamaOrganisasi)
                                    .addComponent(tfNamaPeminjam)
                                    .addComponent(tfNamaKegiatan)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(74, 74, 74))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnProses)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tfNamaPeminjam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(tfNamaOrganisasi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(tfNamaKegiatan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(dtAkhir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(dtAwal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnProses)
                    .addComponent(jButton1))
                .addGap(7, 7, 7))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Input Barang Pinjaman"));

        jLabel7.setText("Nama Barang :");

        tfNamaBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfNamaBarangActionPerformed(evt);
            }
        });

        jLabel8.setText("Jumlah Peminjaman :");

        tfJumlahPeminjaman.setText("0");

        jButton4.setText("Reset");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(tfNamaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(tfJumlahPeminjaman, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(tfNamaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(tfJumlahPeminjaman, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(btnTambah))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(41, 41, 41))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tfNamaBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfNamaBarangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfNamaBarangActionPerformed

    private void tblListBarangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblListBarangMouseClicked
        // TODO add your handling code here:
        int barisAktif = tblListBarang.getSelectedRow();
        idBarangpinjam = (Integer) modelList.getValueAt(barisAktif,0);
        tfNamaBarang.setText(modelList.getValueAt(barisAktif, 1).toString());
        tfJumlahPeminjaman.setText("0");
        
    }//GEN-LAST:event_tblListBarangMouseClicked

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        // TODO add your handling code here:
        boolean cek = false;
              
        if (!(tfNamaBarang.getText().equals("") || tfJumlahPeminjaman.getText().equals("0"))){
            try {
                String namaBarang = tfNamaBarang.getText();
                int jumlahBarang = Integer.parseInt(tfJumlahPeminjaman.getText());
                
                
                        
                for (int i=0;i<listObjek.size();i++){
                    if (namaBarang.equals(listObjek.get(i).getNamaObjek()) && jumlahBarang <= listObjek.get(i).getJumlahTersedia()){
                        cek = true;
                        break;
                    }
                    else {
                        cek = false; 
                    }
                }
                if (cek == true){
                    if(btnTambah.getText().equals("Tambah")){
                    modelPinjam.addRow(new Object[] {idBarangpinjam,namaBarang,jumlahBarang});
                    }
                    else {
                        modelPinjam.setValueAt(jumlahBarang, tblBarangPinjam.getSelectedRow(), 2);
                        btnTambah.setText("Tambah");
                    }
                }
                else {
                    JOptionPane.showMessageDialog(this,"Pastikan Nama Barang dan Jumlah Pinjaman");
                }
                resetBarang();
            }
            catch(NumberFormatException e){
                JOptionPane.showMessageDialog(this,"Pastikan Nama Barang dan Jumlah Pinjaman");
            }
        } 
        else {
             JOptionPane.showMessageDialog(this,"Form Tidak Boleh Kosong !");
        }
    }//GEN-LAST:event_btnTambahActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        resetBarang();        
    }//GEN-LAST:event_jButton4ActionPerformed

    private void btnProsesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProsesActionPerformed
        // TODO add your handling code here:
        if (modelPinjam.getRowCount() > 0){
        try {
            String namaPeminjam = tfNamaPeminjam.getText();
            String namaOrganisasi = tfNamaOrganisasi.getText();
            String namaKegiatan = tfNamaKegiatan.getText();
            Date tanggalAwal = dtAwal.getDate();
            Date tanggalAkhir = dtAkhir.getDate();
            String tAwal = dtf.format(tanggalAwal);
            String tAkhir = dtf.format(tanggalAkhir);
            String kodePinjam = new Peminjaman().generateRandomString();
             
  
                if (tanggalAkhir.compareTo(tanggalAwal) >= 0){
                    for (int i=0;i<modelPinjam.getRowCount();i++){
                    int idBarang = (Integer) modelPinjam.getValueAt(i,0);
                    int jumlahBarang = (Integer) modelPinjam.getValueAt(i,2);
                    int idAkun = Integer.parseInt(id);
                    Peminjaman dpb = new Peminjaman(kodePinjam,"barang",namaPeminjam,namaOrganisasi,namaKegiatan,idBarang,jumlahBarang,tAwal,tAkhir,"proses","proses",idAkun);
                    listPeminjaman.add(dpb);
                    }
            
                    insertPeminjaman();
                }
                else {
                   JOptionPane.showMessageDialog(this,"Pastikan Tanggal Peminjaman Awal dan Akhir"); 
                }
            }
            catch (Exception e){
                JOptionPane.showMessageDialog(this,"Form Tidak Boleh Kosong !");
                 Logger.getLogger(Layout_PeminjamanBarang.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        else {
                 JOptionPane.showMessageDialog(this,"Anda Belum Menambahkan Barang Pinjaman !");
            }   
    }//GEN-LAST:event_btnProsesActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        // TODO add your handling code here:
        if (modelPinjam.getRowCount() > 0){
            try {
                int barisAktif = tblBarangPinjam.getSelectedRow();
                modelPinjam.removeRow(barisAktif);
                
            }
            catch (Exception e){
                JOptionPane.showMessageDialog(this,"Anda Belum Menambahkan Barang Pinjaman !");
            }
        }
        else {
            JOptionPane.showMessageDialog(this,"Anda Belum Menambahkan Barang Pinjaman !"); 
        }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        if (modelPinjam.getRowCount() > 0){
            try {
                int barisAktif = tblBarangPinjam.getSelectedRow();
                tfNamaBarang.setText(modelPinjam.getValueAt(barisAktif,1)+"");
                tfJumlahPeminjaman.setText(modelPinjam.getValueAt(barisAktif,2)+"");
                btnTambah.setText("Edit");
            } 
            catch (Exception e){
                JOptionPane.showMessageDialog(this,"Anda Belum Menambahkan Barang Pinjaman !");
            }
        }
        else {
            JOptionPane.showMessageDialog(this,"Anda Belum Menambahkan Barang Pinjaman !"); 
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        User user = new User();
        user.setUsername(username);
        user.setId(id);
        user.setNama(nama);
        this.dispose();
        new Layout_DashboardUser(user).setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Layout_PeminjamanBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Layout_PeminjamanBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Layout_PeminjamanBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Layout_PeminjamanBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
              
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnProses;
    private javax.swing.JButton btnTambah;
    private com.toedter.calendar.JDateChooser dtAkhir;
    private com.toedter.calendar.JDateChooser dtAwal;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tblBarangPinjam;
    private javax.swing.JTable tblListBarang;
    private javax.swing.JTextField tfBatasPengembalian;
    private javax.swing.JTextField tfJumlahPeminjaman;
    private javax.swing.JTextField tfNamaBarang;
    private javax.swing.JTextField tfNamaKegiatan;
    private javax.swing.JTextField tfNamaOrganisasi;
    private javax.swing.JTextField tfNamaPeminjam;
    // End of variables declaration//GEN-END:variables
}
