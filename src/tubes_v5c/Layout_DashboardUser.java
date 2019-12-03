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
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import static tubes_v5c.Peminjaman.byDate;

/**
 *
 * @author _MFMq_PC
 */
public class Layout_DashboardUser extends javax.swing.JFrame {
    private Connection conn;
    private String nama;
    private String username;
    private String id;
    
    private DefaultTableModel model = new DefaultTableModel();
    private ArrayList<Peminjaman> listPeminjaman;
    private String querySelect;
    private String statusP = "status='proses'";
    private String statusData = "peminjaman diterima";

    /**
     * Creates new form Layout_DashboardUser
     */
    public Layout_DashboardUser(User user) {
        initComponents();
        this.setLocationRelativeTo(null);        
        Koneksi koneksi = new Koneksi();
        conn = koneksi.bukaKoneksi();
        lbNama.setText(user.getNama());
        nama = user.getNama();
        username = user.getUsername();
        id = user.getId();
        cbStatus.setVisible(false);
        tblListPeminjaman.setModel(model);
        querySelect = "SELECT * FROM data_peminjaman WHERE id_user="+id+" GROUP BY kode_peminjaman;";
        loadKolom();
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
        Collections.sort(listPeminjaman , byDate.reversed());
        for(int i = 0;i<model.getRowCount();i++){
            model.removeRow(i);
        }
        for (Peminjaman pinjam : listPeminjaman){
            if (cbSort.getSelectedItem().equals(pinjam.getJenisPeminjaman())){
                model.addRow(new Object[]{pinjam.getKodePeminjaman(),pinjam.getNamaPeminjam(),pinjam.getNamaOrganisasi(),pinjam.getTanggalPeminjaman(),pinjam.getTanggalPengembalian(),pinjam.getStatus()});
            }
        }
        lblJTOT.setText(model.getRowCount()+"");
    }
    
    private void tampilListPinjam(String statusData){
        model.setRowCount(0);
        Collections.sort(listPeminjaman , byDate.reversed());
        for(int i = 0;i<model.getRowCount();i++){
            model.removeRow(i);
        }
        for (Peminjaman pinjam : listPeminjaman){
            if (cbSort.getSelectedItem().equals(pinjam.getJenisPeminjaman()) && statusData.equals(pinjam.getStatus())){
                model.addRow(new Object[]{pinjam.getKodePeminjaman(),pinjam.getNamaPeminjam(),pinjam.getNamaOrganisasi(),pinjam.getTanggalPeminjaman(),pinjam.getTanggalPengembalian(),pinjam.getStatus()});
            }
        }
         lblJTOT.setText(model.getRowCount()+"");
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

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        btnGanti = new javax.swing.JButton();
        btnPermintaanPeminjaman = new javax.swing.JButton();
        mainPanel = new javax.swing.JPanel();
        panelTambah = new javax.swing.JPanel();
        btnPeminjamanBarang = new javax.swing.JButton();
        btnPeminjamanRuangan = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        panelView = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblListPeminjaman = new javax.swing.JTable();
        tfCariKode = new javax.swing.JTextField();
        btnCariKode = new javax.swing.JButton();
        cbSort = new javax.swing.JComboBox<>();
        lbTampil = new javax.swing.JLabel();
        btnDetail = new javax.swing.JButton();
        cbStatus = new javax.swing.JComboBox<>();
        btnHistoryPeminjaman = new javax.swing.JButton();
        btnHistoryPengembalian = new javax.swing.JButton();
        btnProsesPinjam = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        lblJTOT = new javax.swing.JLabel();
        lbNama = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Dashboard User");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(51, 153, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Menu", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(51, 153, 255));
        jButton1.setText("Tambah Peminjaman");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(255, 255, 255));
        jButton4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton4.setForeground(new java.awt.Color(51, 153, 255));
        jButton4.setText("Keluar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        btnGanti.setBackground(new java.awt.Color(255, 255, 255));
        btnGanti.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnGanti.setForeground(new java.awt.Color(51, 153, 255));
        btnGanti.setText("Ganti Password");
        btnGanti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGantiActionPerformed(evt);
            }
        });

        btnPermintaanPeminjaman.setBackground(new java.awt.Color(255, 255, 255));
        btnPermintaanPeminjaman.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnPermintaanPeminjaman.setForeground(new java.awt.Color(51, 153, 255));
        btnPermintaanPeminjaman.setText("Lihat Riwayat Peminjaman");
        btnPermintaanPeminjaman.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPermintaanPeminjamanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnGanti, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnPermintaanPeminjaman, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnPermintaanPeminjaman, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 345, Short.MAX_VALUE)
                .addComponent(btnGanti, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        mainPanel.setLayout(new java.awt.CardLayout());

        panelTambah.setBackground(new java.awt.Color(255, 255, 255));

        btnPeminjamanBarang.setBackground(new java.awt.Color(51, 153, 255));
        btnPeminjamanBarang.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnPeminjamanBarang.setForeground(new java.awt.Color(255, 255, 255));
        btnPeminjamanBarang.setText("Peminjaman Barang");
        btnPeminjamanBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPeminjamanBarangActionPerformed(evt);
            }
        });

        btnPeminjamanRuangan.setBackground(new java.awt.Color(51, 153, 255));
        btnPeminjamanRuangan.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnPeminjamanRuangan.setForeground(new java.awt.Color(255, 255, 255));
        btnPeminjamanRuangan.setText("Peminjaman Ruangan");
        btnPeminjamanRuangan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPeminjamanRuanganActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 153, 255));
        jLabel1.setText("Pilih Jenis Peminjaman");

        javax.swing.GroupLayout panelTambahLayout = new javax.swing.GroupLayout(panelTambah);
        panelTambah.setLayout(panelTambahLayout);
        panelTambahLayout.setHorizontalGroup(
            panelTambahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTambahLayout.createSequentialGroup()
                .addContainerGap(146, Short.MAX_VALUE)
                .addComponent(btnPeminjamanBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnPeminjamanRuangan, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(134, 134, 134))
            .addGroup(panelTambahLayout.createSequentialGroup()
                .addGap(252, 252, 252)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelTambahLayout.setVerticalGroup(
            panelTambahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTambahLayout.createSequentialGroup()
                .addGap(175, 175, 175)
                .addComponent(jLabel1)
                .addGap(63, 63, 63)
                .addGroup(panelTambahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPeminjamanRuangan, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPeminjamanBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(315, Short.MAX_VALUE))
        );

        mainPanel.add(panelTambah, "card4");

        panelView.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setBorder(null);
        jScrollPane1.setToolTipText("");
        jScrollPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

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
        tblListPeminjaman.setGridColor(new java.awt.Color(51, 153, 255));
        jScrollPane1.setViewportView(tblListPeminjaman);

        btnCariKode.setBackground(new java.awt.Color(51, 153, 255));
        btnCariKode.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnCariKode.setForeground(new java.awt.Color(255, 255, 255));
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

        lbTampil.setText("Tampilkan  :");

        btnDetail.setBackground(new java.awt.Color(51, 153, 255));
        btnDetail.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnDetail.setForeground(new java.awt.Color(255, 255, 255));
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

        btnHistoryPeminjaman.setBackground(new java.awt.Color(255, 255, 255));
        btnHistoryPeminjaman.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnHistoryPeminjaman.setForeground(new java.awt.Color(51, 153, 255));
        btnHistoryPeminjaman.setText("Data Peminjaman");
        btnHistoryPeminjaman.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHistoryPeminjamanActionPerformed(evt);
            }
        });

        btnHistoryPengembalian.setBackground(new java.awt.Color(255, 255, 255));
        btnHistoryPengembalian.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnHistoryPengembalian.setForeground(new java.awt.Color(51, 153, 255));
        btnHistoryPengembalian.setText("Data Pengembalian");
        btnHistoryPengembalian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHistoryPengembalianActionPerformed(evt);
            }
        });

        btnProsesPinjam.setBackground(new java.awt.Color(255, 255, 255));
        btnProsesPinjam.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnProsesPinjam.setForeground(new java.awt.Color(51, 153, 255));
        btnProsesPinjam.setText("Permintaan Peminjaman");
        btnProsesPinjam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProsesPinjamActionPerformed(evt);
            }
        });

        jLabel7.setText("Jumlah Total :");

        javax.swing.GroupLayout panelViewLayout = new javax.swing.GroupLayout(panelView);
        panelView.setLayout(panelViewLayout);
        panelViewLayout.setHorizontalGroup(
            panelViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelViewLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelViewLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(28, 28, 28)
                        .addComponent(lblJTOT, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(260, 260, 260))
                    .addGroup(panelViewLayout.createSequentialGroup()
                        .addGroup(panelViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 678, Short.MAX_VALUE)
                            .addGroup(panelViewLayout.createSequentialGroup()
                                .addComponent(lbTampil)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbSort, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelViewLayout.createSequentialGroup()
                        .addGap(0, 468, Short.MAX_VALUE)
                        .addComponent(tfCariKode, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCariKode)
                        .addGap(18, 18, 18))
                    .addGroup(panelViewLayout.createSequentialGroup()
                        .addComponent(btnProsesPinjam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnHistoryPeminjaman, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnHistoryPengembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        panelViewLayout.setVerticalGroup(
            panelViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelViewLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCariKode)
                    .addComponent(tfCariKode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(panelViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbTampil)
                    .addComponent(cbSort, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHistoryPeminjaman, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHistoryPengembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProsesPinjam, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(lblJTOT, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7)))
                .addGap(56, 56, 56))
        );

        mainPanel.add(panelView, "card3");

        lbNama.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbNama.setForeground(new java.awt.Color(51, 153, 255));
        lbNama.setText("Nama");
        lbNama.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lbNama, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lbNama, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 626, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 651, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPeminjamanBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPeminjamanBarangActionPerformed
        // TODO add your handling code here:
        User user = new User();
        user.setUsername(username);
        user.setId(id);
        user.setNama(nama);
        Layout_PeminjamanBarang lpb = new Layout_PeminjamanBarang(user);
        lpb.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnPeminjamanBarangActionPerformed

    private void btnPeminjamanRuanganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPeminjamanRuanganActionPerformed
        // TODO add your handling code here:
        User user = new User();
        user.setUsername(username);
        user.setId(id);
        user.setNama(nama);
        Layout_PeminjamanRuangan lpr = new Layout_PeminjamanRuangan(user);
        lpr.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnPeminjamanRuanganActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

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

    private void cbSortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSortActionPerformed
        // TODO add your handling code here:
        for (int i = 0;i<model.getRowCount();i++){
            model.removeRow(i);
        }
        tampilList();
    }//GEN-LAST:event_cbSortActionPerformed

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
                
                new Layout_DetailPeminjamanUser(sendData).setVisible(true);
            }
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(this,"Pilih Data Peminjaman untuk Melihat Detail !");
        }

    }//GEN-LAST:event_btnDetailActionPerformed

    private void cbStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbStatusActionPerformed
        // TODO add your handling code here:
        for (int i = 0;i<model.getRowCount();i++){
            model.removeRow(i);
        }
        String cbData = (String) cbStatus.getSelectedItem();

        if (cbData.equals("Diterima")){
            if (statusData.equals("pengembalian diterima")){
                statusData = "pengembalian diterima";
            }
            else {
                statusData = "peminjaman diterima";
            }

        }
        else {
            if (statusData.equals("pengembalian diterima")){
                statusData = "pengembalian diterima";
            }
            else {
                statusData = "peminjaman ditolak";
            }
        }
        tampilListPinjam(statusData);
    }//GEN-LAST:event_cbStatusActionPerformed

    private void btnHistoryPeminjamanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHistoryPeminjamanActionPerformed
        // TODO add your handling code here:
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();
        mainPanel.add(panelView);
        mainPanel.repaint();
        mainPanel.revalidate();
        
        cbStatus.setVisible(true);
        querySelect = "SELECT * FROM data_peminjaman WHERE status IN ('peminjaman ditolak','peminjaman diterima') AND id_user="+id+" GROUP BY kode_peminjaman;";
        loadList(querySelect);
        tampilList();
        statusP = "status IN ('peminjaman ditolak','peminjaman diterima') AND id_user="+id+"";
        statusData="peminjaman diterima";
    }//GEN-LAST:event_btnHistoryPeminjamanActionPerformed

    private void btnPermintaanPeminjamanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPermintaanPeminjamanActionPerformed
        // TODO add your handling code here:
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();
        mainPanel.add(panelView);
        mainPanel.repaint();
        mainPanel.revalidate();
        
        cbStatus.setVisible(false);
        querySelect = "SELECT * FROM data_peminjaman WHERE id_user="+id+" GROUP BY kode_peminjaman;";
        loadList(querySelect);
        tampilList();
        statusP = "status IN ('proses','peminjaman ditolak','peminjaman diterima','pengembalian diterima') AND id_user="+id+"";
        
        
        
    }//GEN-LAST:event_btnPermintaanPeminjamanActionPerformed

    private void btnHistoryPengembalianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHistoryPengembalianActionPerformed
        // TODO add your handling code here:
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();
        mainPanel.add(panelView);
        mainPanel.repaint();
        mainPanel.revalidate();
        
        cbStatus.setVisible(false);
        querySelect = "SELECT * FROM data_peminjaman WHERE status = 'pengembalian diterima' AND id_user="+id+" GROUP BY kode_peminjaman;";
        loadList(querySelect);
        tampilList();
        statusP = "status = 'pengembalian diterima' AND id_user="+id+"";
        statusData="pengembalian diterima";
    }//GEN-LAST:event_btnHistoryPengembalianActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();
        mainPanel.add(panelTambah);
        mainPanel.repaint();
        mainPanel.revalidate();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnGantiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGantiActionPerformed
        // TODO add your handling code here:
        User user = new User();
        user.setUsername(username);
        user.setId(id);
        user.setNama(nama);
        this.dispose();
        new Layout_Ganti(user).setVisible(true);
    }//GEN-LAST:event_btnGantiActionPerformed

    private void btnProsesPinjamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProsesPinjamActionPerformed
        // TODO add your handling code here:
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();
        mainPanel.add(panelView);
        mainPanel.repaint();
        mainPanel.revalidate();
        
        cbStatus.setVisible(false);
        querySelect = "SELECT * FROM data_peminjaman WHERE status='proses' AND id_user="+id+" GROUP BY kode_peminjaman;";
        loadList(querySelect);
        tampilList();
        statusP = "status='proses' AND id_user="+id+"";
    }//GEN-LAST:event_btnProsesPinjamActionPerformed

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
            java.util.logging.Logger.getLogger(Layout_DashboardUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Layout_DashboardUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Layout_DashboardUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Layout_DashboardUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCariKode;
    private javax.swing.JButton btnDetail;
    private javax.swing.JButton btnGanti;
    private javax.swing.JButton btnHistoryPeminjaman;
    private javax.swing.JButton btnHistoryPengembalian;
    private javax.swing.JButton btnPeminjamanBarang;
    private javax.swing.JButton btnPeminjamanRuangan;
    private javax.swing.JButton btnPermintaanPeminjaman;
    private javax.swing.JButton btnProsesPinjam;
    private javax.swing.JComboBox<String> cbSort;
    private javax.swing.JComboBox<String> cbStatus;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbNama;
    private javax.swing.JLabel lbTampil;
    private javax.swing.JLabel lblJTOT;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel panelTambah;
    private javax.swing.JPanel panelView;
    private javax.swing.JTable tblListPeminjaman;
    private javax.swing.JTextField tfCariKode;
    // End of variables declaration//GEN-END:variables
}
