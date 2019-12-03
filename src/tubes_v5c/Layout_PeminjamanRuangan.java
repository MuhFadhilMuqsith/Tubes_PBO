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
public class Layout_PeminjamanRuangan extends javax.swing.JFrame {
    
    private DefaultTableModel modelPinjam = new DefaultTableModel();
    private SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
    private Connection conn;
    private ArrayList<ObjekPinjam> listObjek;
    private ArrayList<Peminjaman> listPeminjaman = new ArrayList<>();
     
    private String nama;
    private String username;
    private String id;
    
 

    /**
     * Creates new form Layout_PeminjamanRuangan
     */
    public Layout_PeminjamanRuangan(User user) {
        initComponents();
        this.setLocationRelativeTo(null);
        loadKolomList();
        tblRuanganPinjam.setModel(modelPinjam);
        Koneksi koneksi = new Koneksi();
        conn = koneksi.bukaKoneksi();
        loadObjek();
        tampilRuangan();
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
        modelPinjam.addColumn("Id Ruangan");
        modelPinjam.addColumn("Nama Ruangan");
        modelPinjam.addColumn("Status Ruangan");
    }
    
    private void loadObjek(){
        if (conn != null){
            listObjek = new ArrayList<>();
            String query = "SELECT id_objek,nama_objek,jumlah_tersedia,total_jumlah FROM objek_pinjam WHERE jenis_objek='ruangan';";
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
    private void tampilRuangan(){
        modelPinjam.setRowCount(0);
        String status = "Tersedia";
        for (ObjekPinjam ruangan : listObjek){
            
                    if (ruangan.getJumlahTersedia() <= 0){
                        status = "Sedang Dalam Peminjaman";
                    }
                    else {
                        status = "Tersedia";
                    }
                    
             modelPinjam.addRow(new Object [] {ruangan.getId(),ruangan.getNamaObjek(),status});
        }
            
    }
    
    private void insertPeminjaman(){
        if (conn != null){
            try{
                int hasil = 0;
                    String query = "INSERT INTO data_peminjaman "
                        + "(kode_peminjaman,jenis_peminjaman,nama_peminjam,nama_organisasi,nama_kegiatan,id_objek,jumlah_barang,tanggal_peminjaman,tanggal_pengembalian,status,catatan,id_user) VALUES"
                        + "(?,?,?,?,?,?,?,?,?,?,?,?)";
                    PreparedStatement ps = conn.prepareStatement(query);
                    ps.setString(1,listPeminjaman.get(0).getKodePeminjaman());
                    ps.setString(2,listPeminjaman.get(0).getJenisPeminjaman());
                    ps.setString(3,listPeminjaman.get(0).getNamaPeminjam());
                    ps.setString(4,listPeminjaman.get(0).getNamaOrganisasi());
                    ps.setString(5,listPeminjaman.get(0).getNamaKegiatan());
                    ps.setInt(6,listPeminjaman.get(0).getIdObjek());
                    ps.setInt(7,listPeminjaman.get(0).getJumlahBarang());
                    ps.setString(8,listPeminjaman.get(0).getTanggalPeminjaman());
                    ps.setString(9,listPeminjaman.get(0).getTanggalPengembalian());
                    ps.setString(10,listPeminjaman.get(0).getStatus());
                    ps.setString(11,listPeminjaman.get(0).getCatatan());
                    ps.setInt(12,listPeminjaman.get(0).getIdUser());
                    hasil = ps.executeUpdate();

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
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
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
        tblRuanganPinjam = new javax.swing.JTable();
        btnProses = new javax.swing.JButton();
        dtAwal = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        mainPanel.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 153, 255));
        jLabel1.setText("Form Peminjaman Ruangan");

        jPanel2.setBackground(new java.awt.Color(51, 153, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Data Peminjaman"));

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Nama Peminjam :");

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Nama Organisasi :");

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Tanggal Peminjaman :");

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Nama Kegiatan :");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("-");

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Pilih Ruangan"));

        tblRuanganPinjam.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tblRuanganPinjam);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );

        btnProses.setBackground(new java.awt.Color(255, 255, 255));
        btnProses.setForeground(new java.awt.Color(51, 153, 255));
        btnProses.setText("Proses Peminjaman");
        btnProses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProsesActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setForeground(new java.awt.Color(51, 153, 255));
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
                        .addGap(19, 19, 19)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addGap(55, 55, 55)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnProses)
                    .addComponent(jButton1))
                .addGap(7, 7, 7))
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap(185, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(134, 134, 134))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(75, 75, 75))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnProsesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProsesActionPerformed
        // TODO add your handling code here:
        int barisAktif = tblRuanganPinjam.getSelectedRow();
        if (barisAktif >= 0){
            try {
                String namaPeminjam = tfNamaPeminjam.getText();
                String namaOrganisasi = tfNamaOrganisasi.getText();
                String namaKegiatan = tfNamaKegiatan.getText();
                Date tanggalAwal = dtAwal.getDate();
                Date tanggalAkhir = dtAkhir.getDate();
                String tAwal = dtf.format(tanggalAwal);
                String tAkhir = dtf.format(tanggalAkhir);
                String kodePinjam = new Peminjaman().generateRandomString();
                String status = (String) modelPinjam.getValueAt(barisAktif,2);

                if (tanggalAkhir.compareTo(tanggalAwal) >= 0){
                    if (status.equals("Tersedia")){
                        int idBarang = (Integer) modelPinjam.getValueAt(barisAktif,0);
                        int jumlahBarang = listObjek.get(barisAktif).getJumlahTersedia();
                        int idAkun = Integer.parseInt(id);
                        Peminjaman dpb = new Peminjaman(kodePinjam,"ruangan",namaPeminjam,namaOrganisasi,namaKegiatan,idBarang,jumlahBarang,tAwal,tAkhir,"proses","proses",idAkun);
                        listPeminjaman.add(dpb);
                        insertPeminjaman();
                    }
                    else {
                       JOptionPane.showMessageDialog(this,"Ruangan Sedang Dalam Peminjaman"); 
                    }
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
            java.util.logging.Logger.getLogger(Layout_PeminjamanRuangan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Layout_PeminjamanRuangan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Layout_PeminjamanRuangan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Layout_PeminjamanRuangan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
               
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnProses;
    private com.toedter.calendar.JDateChooser dtAkhir;
    private com.toedter.calendar.JDateChooser dtAwal;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JTable tblRuanganPinjam;
    private javax.swing.JTextField tfNamaKegiatan;
    private javax.swing.JTextField tfNamaOrganisasi;
    private javax.swing.JTextField tfNamaPeminjam;
    // End of variables declaration//GEN-END:variables
}
