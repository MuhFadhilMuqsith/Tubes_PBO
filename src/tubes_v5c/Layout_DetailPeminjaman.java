/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tubes_v5c;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author _MFMq_PC
 */
public class Layout_DetailPeminjaman extends javax.swing.JFrame {
    private DefaultTableModel model = new DefaultTableModel();
    private Connection conn;
    private ArrayList<Peminjaman> listPeminjaman;

    /**
     * Creates new form Layout_DetailPeminjaman
     */
    public Layout_DetailPeminjaman(ArrayList<Peminjaman> sendData) {
        initComponents();
        this.setLocationRelativeTo(null);
        tblListPeminjaman.setModel(model);       
        loadKolom();
        Koneksi koneksi = new Koneksi();
        conn = koneksi.bukaKoneksi();
        
        for (int i=0;i<sendData.size();i++){
            tfKodePeminjaman.setText(sendData.get(i).getKodePeminjaman());
            tfNamaPeminjam.setText(sendData.get(i).getNamaPeminjam());
            tfNamaOrganisasi.setText(sendData.get(i).getNamaOrganisasi());
            tfNamaKegiatan.setText(sendData.get(i).getNamaKegiatan());
            tfTanggalPeminjaman.setText(sendData.get(i).getTanggalPeminjaman());
            tfTanggalPengembalian.setText(sendData.get(i).getTanggalPengembalian());
            taCatatan.setText(sendData.get(i).getCatatan());
            if (sendData.get(i).getStatus().equals("proses")){
                btnTerima.setText("Terima Permintaan");
                btnTolak.setText("Tolak Permintaan");
            }
            else if (sendData.get(i).getStatus().equals("peminjaman diterima")) {
                btnTerima.setText("Terima Pengembalian");
                 btnTolak.setVisible(false);
            }
            else {
                btnTerima.setVisible(false);
                btnTolak.setVisible(false);
            }
        }
        loadDetail(tfKodePeminjaman.getText());
    }

    
    
     private void loadKolom(){
        model.addColumn("Id");
        model.addColumn("Nama");
        model.addColumn("Jumlah Peminjaman");
        model.addColumn("Jumlah Tersedia");
        
    }
     
     private void loadDetail(String getKode){
         if (conn != null){
            String query = "SELECT data_peminjaman.id_objek,objek_pinjam.nama_objek,data_peminjaman.jumlah_barang,objek_pinjam.jumlah_tersedia FROM data_peminjaman INNER JOIN objek_pinjam ON objek_pinjam.id_objek = data_peminjaman.id_objek WHERE data_peminjaman.kode_peminjaman='"+getKode+"';";
            try {
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    int idObjek = rs.getInt("id_objek");
                    int jumlahBarang = rs.getInt("jumlah_barang");
                    String namaObjek = rs.getString("nama_objek");
                    int jumlahTersedia = rs.getInt("jumlah_tersedia");
                    model.addRow(new Object[]{idObjek,namaObjek,jumlahBarang,jumlahTersedia});
                }
                rs.close();
                ps.close();
            }
            catch (SQLException ex){
                Logger.getLogger(Layout_PeminjamanBarang.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
     }
     
     private void updateData (String status,String kodePeminjaman){
        if (conn != null){
            try{
                int hasil = 0;
                int hasilUp = 0;
                String query = "UPDATE data_peminjaman SET status = '"+status+"' , catatan = '"+taCatatan.getText()+"' WHERE kode_peminjaman='"+kodePeminjaman+"'";
                PreparedStatement ps = conn.prepareStatement(query);
                hasil = ps.executeUpdate();
                if (status.equals("peminjaman diterima")){
                    for(int i=0;i<model.getRowCount();i++){
                        String update = "UPDATE objek_pinjam SET jumlah_tersedia = jumlah_tersedia - "+model.getValueAt(i, 2)+" WHERE id_objek = "+model.getValueAt(i, 0)+" ;";
                        PreparedStatement pe = conn.prepareStatement(update);
                        hasilUp = pe.executeUpdate();
                    }
                    if (hasilUp > 0 && hasil > 0){
                           JOptionPane.showMessageDialog(this,"Peminjaman Telah Dikonfirmasi !");
                            this.dispose();
                            User user = new User();
                            new Layout_DashboardAdmin(user).setVisible(true);
                    }
                    else {
                        JOptionPane.showMessageDialog(this,"Gagal !");
                    }                    
                }
                else if (status.equals("pengembalian diterima")){
                    for(int i=0;i<model.getRowCount();i++){
                        String update = "UPDATE objek_pinjam SET jumlah_tersedia = jumlah_tersedia + "+model.getValueAt(i, 2)+" WHERE id_objek = "+model.getValueAt(i, 0)+" ;";
                        PreparedStatement pe = conn.prepareStatement(update);
                        hasilUp = pe.executeUpdate();
                    }
                    if (hasilUp > 0 && hasil > 0){
                           JOptionPane.showMessageDialog(this,"Pengembalian Telah Dikonfirmasi !");
                            this.dispose();
                            User user = new User();
                            new Layout_DashboardAdmin(user).setVisible(true);
                    }
                    else {
                        JOptionPane.showMessageDialog(this,"Gagal !");
                    }
                }
                else {
                    JOptionPane.showMessageDialog(this,"Data Telah DiProses");
                }
                
            }
           catch (SQLException ex){
                Logger.getLogger(Layout_DetailPeminjaman.class.getName()).log(Level.SEVERE, null, ex);
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

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        taCatatan = new javax.swing.JTextArea();
        tfKodePeminjaman = new javax.swing.JLabel();
        tfNamaPeminjam = new javax.swing.JLabel();
        tfNamaOrganisasi = new javax.swing.JLabel();
        tfNamaKegiatan = new javax.swing.JLabel();
        tfTanggalPeminjaman = new javax.swing.JLabel();
        tfTanggalPengembalian = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblListPeminjaman = new javax.swing.JTable();
        btnTolak = new javax.swing.JButton();
        btnTerima = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 255));
        jLabel1.setText("Detail Informasi");

        jPanel1.setBackground(new java.awt.Color(51, 153, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Data Peminjam"));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Nama Peminjam :");

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Kode Peminjaman :");

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Nama Organisasi :");

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Nama Kegiatan :");

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Tanggal Peminjaman :");

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Tanggal Pengembalian :");

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Tambahkan Catatan");

        taCatatan.setColumns(20);
        taCatatan.setRows(5);
        jScrollPane2.setViewportView(taCatatan);

        tfKodePeminjaman.setForeground(new java.awt.Color(255, 255, 255));

        tfNamaPeminjam.setForeground(new java.awt.Color(255, 255, 255));

        tfNamaOrganisasi.setForeground(new java.awt.Color(255, 255, 255));

        tfNamaKegiatan.setForeground(new java.awt.Color(255, 255, 255));

        tfTanggalPeminjaman.setForeground(new java.awt.Color(255, 255, 255));

        tfTanggalPengembalian.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel7)
                            .addGap(18, 18, 18)
                            .addComponent(tfTanggalPengembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel6)
                            .addGap(18, 18, 18)
                            .addComponent(tfTanggalPeminjaman, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addGap(18, 18, 18)
                            .addComponent(tfNamaKegiatan, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addGap(18, 18, 18)
                            .addComponent(tfNamaOrganisasi, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addGap(18, 18, 18)
                            .addComponent(tfNamaPeminjam, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addGap(18, 18, 18)
                            .addComponent(tfKodePeminjaman, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tfKodePeminjaman, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(tfNamaPeminjam, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tfNamaOrganisasi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(tfNamaKegiatan, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(tfTanggalPeminjaman, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel7))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(tfTanggalPengembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(31, 31, 31)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        tblListPeminjaman.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblListPeminjaman);

        btnTolak.setBackground(new java.awt.Color(51, 153, 255));
        btnTolak.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        btnTolak.setForeground(new java.awt.Color(255, 255, 255));
        btnTolak.setText("Tolak Permintaan");
        btnTolak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTolakActionPerformed(evt);
            }
        });

        btnTerima.setBackground(new java.awt.Color(51, 153, 255));
        btnTerima.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        btnTerima.setForeground(new java.awt.Color(255, 255, 255));
        btnTerima.setText("Terima Permintaan");
        btnTerima.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTerimaActionPerformed(evt);
            }
        });

        btnBack.setBackground(new java.awt.Color(51, 153, 255));
        btnBack.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        btnBack.setForeground(new java.awt.Color(255, 255, 255));
        btnBack.setText("Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnTolak)
                        .addGap(21, 21, 21)
                        .addComponent(btnTerima)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnTolak, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnTerima, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnBack, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(24, 24, 24))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTolakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTolakActionPerformed
        // TODO add your handling code here:
        if (!taCatatan.getText().equals("")){
            if (btnTolak.getText().equals("Tolak Permintaan")){
                String status = "peminjaman ditolak";
                String kodePeminjaman = tfKodePeminjaman.getText();
                updateData(status,kodePeminjaman);
            }
            else {
                String status = "pengembalian ditolak";
                String kodePeminjaman = tfKodePeminjaman.getText();
                updateData(status,kodePeminjaman);
            }
        }
        else {
           JOptionPane.showMessageDialog(this,"Tambahkan Catatan untuk Peminjam"); 
        }
        
    }//GEN-LAST:event_btnTolakActionPerformed

    private void btnTerimaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTerimaActionPerformed
        // TODO add your handling code here:
        if (!taCatatan.getText().equals("")){
            if (btnTerima.getText().equals("Terima Permintaan")){
                String status = "peminjaman diterima";
                String kodePeminjaman = tfKodePeminjaman.getText();
                updateData(status,kodePeminjaman); 
            }
            else {
                String status = "pengembalian diterima";
                String kodePeminjaman = tfKodePeminjaman.getText();
                updateData(status,kodePeminjaman); 
            }
            
        }
        else {
           JOptionPane.showMessageDialog(this,"Tambahkan Catatan untuk Peminjam"); 
        }
    }//GEN-LAST:event_btnTerimaActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:
        this.dispose();
        User user = new User();
        new Layout_DashboardAdmin(user).setVisible(true);
        
    }//GEN-LAST:event_btnBackActionPerformed

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
            java.util.logging.Logger.getLogger(Layout_DetailPeminjaman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Layout_DetailPeminjaman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Layout_DetailPeminjaman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Layout_DetailPeminjaman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnTerima;
    private javax.swing.JButton btnTolak;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea taCatatan;
    private javax.swing.JTable tblListPeminjaman;
    private javax.swing.JLabel tfKodePeminjaman;
    private javax.swing.JLabel tfNamaKegiatan;
    private javax.swing.JLabel tfNamaOrganisasi;
    private javax.swing.JLabel tfNamaPeminjam;
    private javax.swing.JLabel tfTanggalPeminjaman;
    private javax.swing.JLabel tfTanggalPengembalian;
    // End of variables declaration//GEN-END:variables
}
