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
public class Layout_DashboardAdmin extends javax.swing.JFrame {
    private DefaultTableModel model = new DefaultTableModel();
    private Connection conn;
    private ArrayList<Peminjaman> listPeminjaman;
    private String querySelect;
    private String statusP = "status='proses'";
    private String statusData = "peminjaman diterima";

    /**
     * Creates new form Layout_DashboardAdmin
     */
    public Layout_DashboardAdmin() {
        initComponents();
        cbStatus.setVisible(false);
        tblListPeminjaman.setModel(model);
        loadKolom();
        Koneksi koneksi = new Koneksi();
        conn = koneksi.bukaKoneksi();
        querySelect = "SELECT * FROM data_peminjaman WHERE status='proses' GROUP BY kode_peminjaman;";
        loadList(querySelect);
        tampilList();
        
    }
    
    private void loadKolom(){
        model.addColumn("Kode Peminjaman");
        model.addColumn("Nama Peminjam");
        model.addColumn("Nama Organisasi");
        model.addColumn("Tanggal Peminjaman");
        model.addColumn("Tanggal Pengembalian");
        model.addColumn("Status");
    }
    
    private void loadList(String querySelect){
        if (conn != null){
            listPeminjaman = new ArrayList<>();
            try {
                PreparedStatement ps = conn.prepareStatement(querySelect);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    String kodePeminjaman = rs.getString("kode_peminjaman");
                    String jenisPeminjaman = rs.getString("jenis_peminjaman");
                    String namaPeminjam = rs.getString("nama_peminjam");
                    String namaOrganisasi = rs.getString("nama_organisasi");
                    String namaKegiatan = rs.getString("nama_kegiatan");
                    int idObjek = rs.getInt("id_objek");
                    int jumlahBarang = rs.getInt("jumlah_barang");
                    String tanggalPeminjaman = rs.getString("tanggal_peminjaman");
                    String tanggalPengembalian = rs.getString("tanggal_pengembalian");
                    String status = rs.getString("status");
                    String catatan = rs.getString("catatan");
                    int idUser = rs.getInt("id_user");
                    Peminjaman objek = new Peminjaman(kodePeminjaman,jenisPeminjaman,namaPeminjam,namaOrganisasi,namaKegiatan,idObjek,jumlahBarang,tanggalPeminjaman,tanggalPengembalian,status,catatan,idUser);
                    listPeminjaman.add(objek);
                }
                rs.close();
                ps.close();
            }
            catch (SQLException ex){
                Logger.getLogger(Layout_PeminjamanBarang.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void tampilList(){
        model.setRowCount(0);
        for(int i = 0;i<model.getRowCount();i++){
            model.removeRow(i);
        }
        for (Peminjaman pinjam : listPeminjaman){
            if (cbSort.getSelectedItem().equals(pinjam.getJenisPeminjaman())){
                model.addRow(new Object[]{pinjam.getKodePeminjaman(),pinjam.getNamaPeminjam(),pinjam.getNamaOrganisasi(),pinjam.getTanggalPeminjaman(),pinjam.getTanggalPengembalian(),pinjam.getStatus()});
            }
        }
    }
    
    private void tampilListPinjam(String statusData){
        model.setRowCount(0);
        for(int i = 0;i<model.getRowCount();i++){
            model.removeRow(i);
        }
        for (Peminjaman pinjam : listPeminjaman){
            if (cbSort.getSelectedItem().equals(pinjam.getJenisPeminjaman()) && statusData.equals(pinjam.getStatus())){
                model.addRow(new Object[]{pinjam.getKodePeminjaman(),pinjam.getNamaPeminjam(),pinjam.getNamaOrganisasi(),pinjam.getTanggalPeminjaman(),pinjam.getTanggalPengembalian(),pinjam.getStatus()});
            }
        }
    }
    
    
    private void cariData(String statusPinjam,String keyword){
        if (conn != null){
            listPeminjaman = new ArrayList<>();
            String query = "SELECT * FROM data_peminjaman WHERE "+statusPinjam+" AND kode_peminjaman LIKE '%"+keyword+"%' GROUP BY kode_peminjaman;";
            try {
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    String kodePeminjaman = rs.getString("kode_peminjaman");
                    String jenisPeminjaman = rs.getString("jenis_peminjaman");
                    String namaPeminjam = rs.getString("nama_peminjam");
                    String namaOrganisasi = rs.getString("nama_organisasi");
                    String namaKegiatan = rs.getString("nama_kegiatan");
                    int idObjek = rs.getInt("id_objek");
                    int jumlahBarang = rs.getInt("jumlah_barang");
                    String tanggalPeminjaman = rs.getString("tanggal_peminjaman");
                    String tanggalPengembalian = rs.getString("tanggal_pengembalian");
                    String status = rs.getString("status");
                    String catatan = rs.getString("catatan");
                    int idUser = rs.getInt("id_user");
                    Peminjaman objek = new Peminjaman(kodePeminjaman,jenisPeminjaman,namaPeminjam,namaOrganisasi,namaKegiatan,idObjek,jumlahBarang,tanggalPeminjaman,tanggalPengembalian,status,catatan,idUser);
                    listPeminjaman.add(objek);
                }
                rs.close();
                ps.close();
            }
            catch (SQLException ex){
                Logger.getLogger(Layout_PeminjamanBarang.class.getName()).log(Level.SEVERE, null, ex);
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

        jPanel1 = new javax.swing.JPanel();
        btnPermohonan = new javax.swing.JButton();
        btnHistoryPeminjaman = new javax.swing.JButton();
        btnHistoryPengembalian = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblListPeminjaman = new javax.swing.JTable();
        tfCariKode = new javax.swing.JTextField();
        btnCariKode = new javax.swing.JButton();
        cbSort = new javax.swing.JComboBox<>();
        lbTampil = new javax.swing.JLabel();
        btnDetail = new javax.swing.JButton();
        cbStatus = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Menu"));

        btnPermohonan.setText("Permintaan Peminjaman");
        btnPermohonan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPermohonanActionPerformed(evt);
            }
        });

        btnHistoryPeminjaman.setText("History Peminjaman");
        btnHistoryPeminjaman.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHistoryPeminjamanActionPerformed(evt);
            }
        });

        btnHistoryPengembalian.setText("History Pengembalian");
        btnHistoryPengembalian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHistoryPengembalianActionPerformed(evt);
            }
        });

        jButton4.setText("Keluar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnPermohonan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnHistoryPeminjaman, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
                    .addComponent(btnHistoryPengembalian, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(btnPermohonan, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnHistoryPeminjaman, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnHistoryPengembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 292, Short.MAX_VALUE)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

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

        btnCariKode.setText("Cari Kode");
        btnCariKode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariKodeActionPerformed(evt);
            }
        });

        cbSort.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "barang", "ruangan" }));
        cbSort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSortActionPerformed(evt);
            }
        });

        lbTampil.setText("Tampilkan Permintaan :");

        btnDetail.setText("Lihat Detail");
        btnDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetailActionPerformed(evt);
            }
        });

        cbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ditolak", "Diterima" }));
        cbStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbStatusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(237, 237, 237))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lbTampil)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbSort, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 669, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addContainerGap(471, Short.MAX_VALUE)
                    .addComponent(tfCariKode, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(btnCariKode)
                    .addContainerGap()))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbSort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbTampil))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(cbStatus))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 376, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnDetail)
                .addGap(81, 81, 81))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tfCariKode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCariKode))
                    .addContainerGap(533, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void cbSortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSortActionPerformed
        // TODO add your handling code here:
        for (int i = 0;i<model.getRowCount();i++){
            model.removeRow(i);
        }
        tampilList();
    }//GEN-LAST:event_cbSortActionPerformed

    private void btnCariKodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariKodeActionPerformed
        // TODO add your handling code here:
        String keyword = tfCariKode.getText().trim();
        if (keyword.length() == 0){
            loadList(querySelect);
            tampilList();
        }
        else {
            cariData(statusP,keyword);
            tampilList();
        }
        
    }//GEN-LAST:event_btnCariKodeActionPerformed

    private void btnDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetailActionPerformed
        // TODO add your handling code here:
        ArrayList<Peminjaman> sendData = new ArrayList<>();
        int cek  = 0;
        int barisAktif = tblListPeminjaman.getSelectedRow();
        try{
        for(int i = 0;i < listPeminjaman.size();i++){
            if(listPeminjaman.get(i).getKodePeminjaman().equals(model.getValueAt(barisAktif, 0))){
                Peminjaman dataPinjam = new Peminjaman(
                     listPeminjaman.get(i).getKodePeminjaman(),
                        listPeminjaman.get(i).getJenisPeminjaman(),
                        listPeminjaman.get(i).getNamaPeminjam(),
                        listPeminjaman.get(i).getNamaOrganisasi(),
                        listPeminjaman.get(i).getNamaKegiatan(),
                        listPeminjaman.get(i).getIdObjek(),
                        listPeminjaman.get(i).getJumlahBarang(),
                        listPeminjaman.get(i).getTanggalPeminjaman(),
                        listPeminjaman.get(i).getTanggalPengembalian(),
                        listPeminjaman.get(i).getStatus(),
                        listPeminjaman.get(i).getCatatan(),
                        listPeminjaman.get(i).getIdUser()
                );
                sendData.add(dataPinjam);
                cek = 1;
                break;
            }            
        }
        if (cek == 0){
             JOptionPane.showMessageDialog(this,"Pilih Data Peminjaman untuk Melihat Detail !");                  
        }
        else {
            this.dispose();
            new Layout_DetailPeminjaman(sendData).setVisible(true);  
        }
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(this,"Pilih Data Peminjaman untuk Melihat Detail !"); 
        }
        
    }//GEN-LAST:event_btnDetailActionPerformed

    private void btnPermohonanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPermohonanActionPerformed
        // TODO add your handling code here:
        cbStatus.setVisible(false);
        querySelect = "SELECT * FROM data_peminjaman WHERE status='proses' GROUP BY kode_peminjaman;";
        loadList(querySelect);
        tampilList();
        statusP = "status='proses'";
    }//GEN-LAST:event_btnPermohonanActionPerformed

    private void btnHistoryPeminjamanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHistoryPeminjamanActionPerformed
        // TODO add your handling code here:
        cbStatus.setVisible(true);
        querySelect = "SELECT * FROM data_peminjaman WHERE status='peminjaman ditolak' OR status = 'peminjaman diterima' GROUP BY kode_peminjaman;";
        loadList(querySelect);
        tampilList();
        statusP = "status IN ('peminjaman ditolak','peminjaman diterima')";
        statusData="peminjaman diterima";
    }//GEN-LAST:event_btnHistoryPeminjamanActionPerformed

    private void cbStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbStatusActionPerformed
        // TODO add your handling code here:
        for (int i = 0;i<model.getRowCount();i++){
            model.removeRow(i);
        }
        String cbData = (String) cbStatus.getSelectedItem();
        
        if (cbData.equals("Diterima")){
            if (statusData.equals("pengembalian diterima")||statusData.equals("pengembalian ditolak")){
              statusData = "pengembalian diterima";  
            }
            else {
                statusData = "peminjaman diterima";
            }
            
        }
        else {
           if (statusData.equals("pengembalian diterima")||statusData.equals("pengembalian ditolak")){
              statusData = "pengembalian ditolak";  
            }
            else {
                statusData = "peminjaman ditolak";
            }
        }
        tampilListPinjam(statusData);
    }//GEN-LAST:event_cbStatusActionPerformed

    private void btnHistoryPengembalianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHistoryPengembalianActionPerformed
        // TODO add your handling code here:
        cbStatus.setVisible(true);
        querySelect = "SELECT * FROM data_peminjaman WHERE status='pengembalian ditolak' OR status = 'pengembalian diterima' GROUP BY kode_peminjaman;";
        loadList(querySelect);
        tampilList();
        statusP = "status IN ('pengembalian ditolak','pengembalian diterima')";
        statusData="pengembalian diterima";
    }//GEN-LAST:event_btnHistoryPengembalianActionPerformed

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
            java.util.logging.Logger.getLogger(Layout_DashboardAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Layout_DashboardAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Layout_DashboardAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Layout_DashboardAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Layout_DashboardAdmin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCariKode;
    private javax.swing.JButton btnDetail;
    private javax.swing.JButton btnHistoryPeminjaman;
    private javax.swing.JButton btnHistoryPengembalian;
    private javax.swing.JButton btnPermohonan;
    private javax.swing.JComboBox<String> cbSort;
    private javax.swing.JComboBox<String> cbStatus;
    private javax.swing.JButton jButton4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbTampil;
    private javax.swing.JTable tblListPeminjaman;
    private javax.swing.JTextField tfCariKode;
    // End of variables declaration//GEN-END:variables
}
