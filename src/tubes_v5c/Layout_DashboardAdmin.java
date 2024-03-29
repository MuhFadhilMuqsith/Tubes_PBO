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
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import org.codehaus.groovy.control.messages.Message;
import static tubes_v5c.Peminjaman.byDate;

/**
 *
 * @author _MFMq_PC
 */
public class Layout_DashboardAdmin extends javax.swing.JFrame {
    private DefaultTableModel model = new DefaultTableModel(){
      public boolean isCellEditable(int rowIndex, int mColIndex) {
        return false;
      }
    };
    private DefaultTableModel modelObjek = new DefaultTableModel(){
      public boolean isCellEditable(int rowIndex, int mColIndex) {
        return false;
      }
    };
    private Connection conn;
    private ArrayList<Peminjaman> listPeminjaman;
    private ArrayList<ObjekPinjam> listObjek;
    private String querySelect;
    private String statusP = "status='proses'";
    private String statusData = "peminjaman diterima";
    
     private String namaAkun;
    private String usernameAkun;
    private String idAkun;

    /**
     * Creates new form Layout_DashboardAdmin
     */
    public Layout_DashboardAdmin(User user) {
        initComponents();
        namaAkun = user.getNama();
        usernameAkun = user.getUsername();
        idAkun = user.getId();
        this.setLocationRelativeTo(null);
        cbStatus.setVisible(false);
        tblListPeminjaman.setModel(model);
        tblObjekPinjaman.setModel(modelObjek);
        loadKolom();
        loadKolomObjek();
        Koneksi koneksi = new Koneksi();
        conn = koneksi.bukaKoneksi();
        querySelect = "SELECT * FROM data_peminjaman GROUP BY kode_peminjaman;";
        loadList(querySelect);
        loadObjek();
        tampilObjek();
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
    
    private void loadKolomObjek(){
        modelObjek.addColumn("Id");
        modelObjek.addColumn("Nama");
        modelObjek.addColumn("Jumlah Tersedia");
        modelObjek.addColumn("Total Jumlah");
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
        Collections.sort(listPeminjaman , byDate.reversed());
        model.setRowCount(0);
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
    
    private void tampilListByBulan (int idBulan){
        Collections.sort(listPeminjaman , byDate.reversed());
        idBulan = idBulan + 1;
        model.setRowCount(0);
        for(int i = 0;i<model.getRowCount();i++){
            model.removeRow(i);
        }
        int bulan = 0;
        for (Peminjaman pinjam : listPeminjaman){
            bulan = Integer.parseInt(pinjam.getTanggalPeminjaman().substring(5, 7));
            if (bulan == idBulan ){
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
    
    private void loadObjek(){
        if (conn != null){
            listObjek = new ArrayList<>();
            String query = "SELECT id_objek,nama_objek,jumlah_tersedia,total_jumlah,jenis_objek FROM objek_pinjam ;";
            try {
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    int id = rs.getInt("id_objek");
                    String namaBarang = rs.getString("nama_objek");
                    int jumlah_tersedia = rs.getInt("jumlah_tersedia");
                    int total = rs.getInt("total_jumlah");
                    String jenisObjek = rs.getString("jenis_objek");
                    ObjekPinjam objek = new ObjekPinjam(id,namaBarang,jumlah_tersedia,total,jenisObjek);
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
    
    private void tampilObjek(){
        modelObjek.setRowCount(0);
        for(int i = 0;i<modelObjek.getRowCount();i++){
            modelObjek.removeRow(i);
        }
        for (ObjekPinjam objekPinjam : listObjek){
            if (cbJenisObjekPinjam.getSelectedItem().equals(objekPinjam.getJenisObjek())){
                modelObjek.addRow(new Object[]{objekPinjam.getId(),objekPinjam.getNamaObjek(),objekPinjam.getJumlahTersedia(),objekPinjam.getTotalJumlah()});           
            }
        }
    }
    
    private void resetObjek(){
        lblID.setText("");
        tfEJumlahTersedia.setText("");
        tfENamaBarang.setText("");
        tfETotalJumlah.setText("");
    }
    
    private void updateData(){
        if (lblID.getText().equals("")){
            try{
                if (!(tfENamaBarang.getText().equals("") || tfETotalJumlah.getText().equals("") || tfEJumlahTersedia.getText().equals("") )){
                    String query = "INSERT INTO objek_pinjam (nama_objek,jenis_objek,jumlah_tersedia,total_jumlah) VALUES (?,?,?,?)";
                    PreparedStatement ps = conn.prepareStatement(query);
                    ps.setString(1,tfENamaBarang.getText());
                    ps.setString(2,cbObjek.getSelectedItem().toString());
                    ps.setString(3,tfEJumlahTersedia.getText());
                    ps.setString(4,tfETotalJumlah.getText());
                    int hasil = ps.executeUpdate();
                    if (hasil > 0){
                        JOptionPane.showMessageDialog(this,"Data Berhasil Ditambahkan !");

                    }
                }
                else {
                    JOptionPane.showMessageDialog(this,"Form Tidak Boleh Kosong");
                }
            } 
            catch (Exception e) {
               JOptionPane.showMessageDialog(this,"Form Tidak Boleh Kosong !");         
            }
        }
        else {
            try{
                if (!(tfENamaBarang.getText().equals("") || tfETotalJumlah.getText().equals("") || tfEJumlahTersedia.getText().equals("") )){
                    int id = Integer.parseInt(lblID.getText());
                    String namaBarang = tfENamaBarang.getText();
                    int jumlah_tersedia = Integer.parseInt(tfEJumlahTersedia.getText());
                    int total = Integer.parseInt(tfETotalJumlah.getText());
                    String jenisObjek = cbObjek.getSelectedItem().toString();
                    String query = "UPDATE objek_pinjam SET nama_objek = '"+namaBarang+"' , jenis_objek = '"+jenisObjek+"' , jumlah_tersedia = "+jumlah_tersedia+" , total_jumlah = "+total+" WHERE id_objek = "+id+" ;";
                   
                    PreparedStatement ps = conn.prepareStatement(query);
                    
                    int hasil = ps.executeUpdate();
                    if (hasil > 0){
                        JOptionPane.showMessageDialog(this,"Data Berhasil DiUpdate !");
                        
                    }
                }
                else {
                    JOptionPane.showMessageDialog(this,"Form Tidak Boleh Kosong");
                }
            }
            catch (Exception ex) {
               JOptionPane.showMessageDialog(this,"Form Tidak Boleh Kosong !");         
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
        jPanel1 = new javax.swing.JPanel();
        btnPermohonan = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        btnGanti = new javax.swing.JButton();
        btnEditData = new javax.swing.JButton();
        btnCetak = new javax.swing.JButton();
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
        btnHistoryPengembalian = new javax.swing.JButton();
        btnHistoryPeminjaman = new javax.swing.JButton();
        btnProsesPeminjam = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        lblJTOT = new javax.swing.JLabel();
        mcBulan = new com.toedter.calendar.JMonthChooser();
        jButton1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblObjekPinjaman = new javax.swing.JTable();
        cbJenisObjekPinjam = new javax.swing.JComboBox<>();
        btnETambah = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblID = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        tfENamaBarang = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        tfETotalJumlah = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        tfEJumlahTersedia = new javax.swing.JTextField();
        btnEReset = new javax.swing.JButton();
        btnESubmint = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        cbObjek = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Admin Page");

        mainPanel.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(51, 153, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Menu"));

        btnPermohonan.setBackground(new java.awt.Color(255, 255, 255));
        btnPermohonan.setForeground(new java.awt.Color(51, 153, 255));
        btnPermohonan.setText("Lihat Data Peminjaman");
        btnPermohonan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPermohonanActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(255, 255, 255));
        jButton4.setForeground(new java.awt.Color(51, 153, 255));
        jButton4.setText("Keluar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        btnGanti.setBackground(new java.awt.Color(255, 255, 255));
        btnGanti.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        btnGanti.setForeground(new java.awt.Color(51, 153, 255));
        btnGanti.setText("Ganti Password");
        btnGanti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGantiActionPerformed(evt);
            }
        });

        btnEditData.setBackground(new java.awt.Color(255, 255, 255));
        btnEditData.setForeground(new java.awt.Color(51, 153, 255));
        btnEditData.setText("Edit Data Barang");
        btnEditData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditDataActionPerformed(evt);
            }
        });

        btnCetak.setBackground(new java.awt.Color(255, 255, 255));
        btnCetak.setForeground(new java.awt.Color(51, 153, 255));
        btnCetak.setText("Cetak Data Peminjaman");
        btnCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnPermohonan, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGanti, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEditData, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                    .addComponent(btnCetak, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(btnPermohonan, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnEditData, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCetak, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 163, Short.MAX_VALUE)
                .addComponent(btnGanti, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.CardLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

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

        btnCariKode.setBackground(new java.awt.Color(51, 153, 255));
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

        lbTampil.setForeground(new java.awt.Color(51, 153, 255));
        lbTampil.setText("Tampilkan Permintaan :");

        btnDetail.setBackground(new java.awt.Color(51, 153, 255));
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

        btnHistoryPengembalian.setBackground(new java.awt.Color(255, 255, 255));
        btnHistoryPengembalian.setForeground(new java.awt.Color(51, 153, 255));
        btnHistoryPengembalian.setText("Data Pengembalian");
        btnHistoryPengembalian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHistoryPengembalianActionPerformed(evt);
            }
        });

        btnHistoryPeminjaman.setBackground(new java.awt.Color(255, 255, 255));
        btnHistoryPeminjaman.setForeground(new java.awt.Color(51, 153, 255));
        btnHistoryPeminjaman.setText("Data Peminjaman");
        btnHistoryPeminjaman.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHistoryPeminjamanActionPerformed(evt);
            }
        });

        btnProsesPeminjam.setBackground(new java.awt.Color(255, 255, 255));
        btnProsesPeminjam.setForeground(new java.awt.Color(51, 153, 255));
        btnProsesPeminjam.setText("Permintaan Peminjaman");
        btnProsesPeminjam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProsesPeminjamActionPerformed(evt);
            }
        });

        jLabel7.setText("Jumlah Total :");

        mcBulan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mcBulanMouseClicked(evt);
            }
        });
        mcBulan.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                mcBulanInputMethodTextChanged(evt);
            }
        });
        mcBulan.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                mcBulanPropertyChange(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(51, 153, 255));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Tampilkan Data Bulanan");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 673, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(tfCariKode, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnCariKode))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(btnProsesPeminjam, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnHistoryPeminjaman, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnHistoryPengembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblJTOT, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lbTampil)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbSort, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(cbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(mcBulan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(263, 263, 263))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCariKode)
                    .addComponent(tfCariKode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbStatus, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cbSort, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbTampil, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(mcBulan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHistoryPengembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHistoryPeminjaman, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProsesPeminjam, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblJTOT, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jButton1))
                .addGap(8, 8, 8)
                .addComponent(btnDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        jPanel2.add(jPanel3, "card2");

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        tblObjekPinjaman.setModel(new javax.swing.table.DefaultTableModel(
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
        tblObjekPinjaman.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblObjekPinjamanMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblObjekPinjaman);

        cbJenisObjekPinjam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "barang", "ruangan" }));
        cbJenisObjekPinjam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbJenisObjekPinjamActionPerformed(evt);
            }
        });

        btnETambah.setText("Tambah");
        btnETambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnETambahActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Edit Data");

        jLabel2.setText("ID :");

        jLabel3.setText("Nama");

        jLabel4.setText("Total Jumlah");

        tfETotalJumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfETotalJumlahActionPerformed(evt);
            }
        });
        tfETotalJumlah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfETotalJumlahKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfETotalJumlahKeyTyped(evt);
            }
        });

        jLabel5.setText("Jumlah Tersedia");

        btnEReset.setText("Reset");
        btnEReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEResetActionPerformed(evt);
            }
        });

        btnESubmint.setText("Edit");
        btnESubmint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnESubmintActionPerformed(evt);
            }
        });

        jLabel6.setText("Jenis Objek");

        cbObjek.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "barang", "ruangan" }));
        cbObjek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbObjekActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 449, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblID, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel3)
                            .addComponent(tfENamaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(tfETotalJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel1)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(btnEReset)
                                .addGap(18, 18, 18)
                                .addComponent(btnESubmint, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel6)
                            .addComponent(cbObjek, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tfEJumlahTersedia)))
                    .addComponent(btnETambah, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbJenisObjekPinjam, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(45, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addComponent(cbJenisObjekPinjam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(lblID, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3)
                        .addGap(3, 3, 3)
                        .addComponent(tfENamaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4)
                        .addGap(3, 3, 3)
                        .addComponent(tfETotalJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5)
                        .addGap(3, 3, 3)
                        .addComponent(tfEJumlahTersedia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbObjek, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnEReset)
                            .addComponent(btnESubmint))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnETambah, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(80, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel4, "card3");

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 709, Short.MAX_VALUE))
            .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(mainPanelLayout.createSequentialGroup()
                    .addGap(246, 246, 246)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(mainPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
        jPanel2.removeAll();
        jPanel2.repaint();
        jPanel2.revalidate();
        jPanel2.add(jPanel3);
        jPanel2.repaint();
        jPanel2.revalidate();
        cbStatus.setVisible(false);
        querySelect = "SELECT * FROM data_peminjaman GROUP BY kode_peminjaman;";
        loadList(querySelect);
        tampilList();
        statusP = "status IN ('proses','peminjaman ditolak','peminjaman diterima','pengembalian diterima')";
    }//GEN-LAST:event_btnPermohonanActionPerformed

    private void btnHistoryPeminjamanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHistoryPeminjamanActionPerformed
        // TODO add your handling code here:
        cbStatus.setVisible(true);
        querySelect = "SELECT * FROM data_peminjaman WHERE status='peminjaman ditolak' OR status = 'peminjaman diterima' GROUP BY kode_peminjaman;";
        loadList(querySelect);
        tampilList();
        statusP = "status IN ('proses','peminjaman ditolak','peminjaman diterima','pengembalian diterima')";
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
        cbStatus.setVisible(false);
        querySelect = "SELECT * FROM data_peminjaman WHERE status='pengembalian ditolak' OR status = 'pengembalian diterima' GROUP BY kode_peminjaman;";
        loadList(querySelect);
        tampilList();
        statusP = "status IN ('proses','peminjaman ditolak','peminjaman diterima','pengembalian diterima')";
        statusData="pengembalian diterima";
    }//GEN-LAST:event_btnHistoryPengembalianActionPerformed

    private void btnGantiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGantiActionPerformed
        // TODO add your handling code here:
        User user = new User();
        user.setId(idAkun);
        user.setNama(namaAkun);
        user.setUsername(usernameAkun);
        this.dispose();
        new Layout_Ganti(user).setVisible(true);
    }//GEN-LAST:event_btnGantiActionPerformed

    private void btnProsesPeminjamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProsesPeminjamActionPerformed
        // TODO add your handling code here:
        cbStatus.setVisible(false);
        querySelect = "SELECT * FROM data_peminjaman WHERE status='proses' GROUP BY kode_peminjaman;";
        loadList(querySelect);
        tampilList();
        statusP = "status IN ('proses','peminjaman ditolak','peminjaman diterima','pengembalian diterima')";
    }//GEN-LAST:event_btnProsesPeminjamActionPerformed

    private void btnEditDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditDataActionPerformed
        // TODO add your handling code here:
        jPanel2.removeAll();
        jPanel2.repaint();
        jPanel2.revalidate();
        jPanel2.add(jPanel4);
        jPanel2.repaint();
        jPanel2.revalidate();
    }//GEN-LAST:event_btnEditDataActionPerformed

    private void tfETotalJumlahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfETotalJumlahKeyPressed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_tfETotalJumlahKeyPressed

    private void tfETotalJumlahKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfETotalJumlahKeyTyped
        // TODO add your handling code here:
        
    }//GEN-LAST:event_tfETotalJumlahKeyTyped

    private void cbJenisObjekPinjamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbJenisObjekPinjamActionPerformed
        // TODO add your handling code here:
        if(cbJenisObjekPinjam.getSelectedItem().equals("barang")){
            cbObjek.setSelectedIndex(0);
        }
        else {
            cbObjek.setSelectedIndex(1);
        }
        loadObjek();
        tampilObjek();
    }//GEN-LAST:event_cbJenisObjekPinjamActionPerformed

    private void tblObjekPinjamanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblObjekPinjamanMouseClicked
        // TODO add your handling code here:
        jLabel1.setText("Edit Data");
        tfENamaBarang.requestFocus();
        btnESubmint.setText("Edit");
        
        int barisAktif = tblObjekPinjaman.getSelectedRow();
        String id = (String) modelObjek.getValueAt(barisAktif, 0).toString();
        String nama = (String) modelObjek.getValueAt(barisAktif, 1).toString();
        String jumlahTersedia = (String) modelObjek.getValueAt(barisAktif, 2).toString();
        String total = (String) modelObjek.getValueAt(barisAktif, 3).toString();
        lblID.setVisible(true);
       lblID.setText(id);
        tfENamaBarang.setText(nama);
       tfETotalJumlah.setText(total);
       tfEJumlahTersedia.setText(jumlahTersedia);
    }//GEN-LAST:event_tblObjekPinjamanMouseClicked

    private void btnEResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEResetActionPerformed
        // TODO add your handling code here:
        resetObjek();
    }//GEN-LAST:event_btnEResetActionPerformed

    private void btnETambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnETambahActionPerformed
        // TODO add your handling code here:
        resetObjek();
        lblID.setVisible(false);
        jLabel1.setText("Tambah Data");
        tfENamaBarang.requestFocus();
        btnESubmint.setText("Tambah");
    }//GEN-LAST:event_btnETambahActionPerformed

    private void cbObjekActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbObjekActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbObjekActionPerformed

    private void tfETotalJumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfETotalJumlahActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfETotalJumlahActionPerformed

    private void btnESubmintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnESubmintActionPerformed
        // TODO add your handling code here:   
        if (!lblID.getText().equals("")){
         try {
            int total = Integer.parseInt(tfETotalJumlah.getText());
            int tersedia = Integer.parseInt(tfEJumlahTersedia.getText());
            
             for (int i = 0; i < listObjek.size(); i++) {
                 int pinjam = listObjek.get(i).getTotalJumlah() - listObjek.get(i).getJumlahTersedia();
                 if (lblID.getText().equals(listObjek.get(i).getId()+"")){
                     if (total >= pinjam && tersedia <= total && (total-tersedia) >= pinjam){              
                        updateData();
                        loadObjek();
                        tampilObjek();
                        resetObjek();
                        break;
                }
                else {
                    JOptionPane.showMessageDialog(this,"Pastikan Jumlah Tersedia Lebih Kecil Dari Total \n Atau Sebaliknya!");
                }
                 }
                 
             }
        
               }
             catch (Exception ex) {
               JOptionPane.showMessageDialog(this,"Form Tidak Boleh Kosong !");         
            }   
          
        }
        else {
             try {
            int total = Integer.parseInt(tfETotalJumlah.getText());
            int tersedia = Integer.parseInt(tfEJumlahTersedia.getText());
        if (total >= tersedia && tersedia <= total){
            
                
                    updateData();
                    loadObjek();
                    tampilObjek();
                    resetObjek();
               
                }
                else {
                    JOptionPane.showMessageDialog(this,"Pastikan Jumlah Tersedia Lebih Kecil Dari Total \n Atau Sebaliknya!");
                }
               }
             catch (Exception ex) {
               JOptionPane.showMessageDialog(this,"Form Tidak Boleh Kosong !");         
            }    
        }
    }//GEN-LAST:event_btnESubmintActionPerformed

    private void mcBulanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mcBulanMouseClicked
        // TODO add your handling code here:
        //lblJTOT.setText(mcBulan.getMonth()+"");
    }//GEN-LAST:event_mcBulanMouseClicked

    private void mcBulanPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_mcBulanPropertyChange
        // TODO add your handling code here:
         
    }//GEN-LAST:event_mcBulanPropertyChange

    private void mcBulanInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_mcBulanInputMethodTextChanged
        // TODO add your handling code here:
       // int idBulan = mcBulan.getMonth();
         //tampilListByBulan(idBulan);
    }//GEN-LAST:event_mcBulanInputMethodTextChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int idBulan = mcBulan.getMonth();
        querySelect = "SELECT * FROM data_peminjaman GROUP BY kode_peminjaman;";
        loadList(querySelect);       
        tampilListByBulan(idBulan);
        statusP = "status IN ('proses','peminjaman ditolak','peminjaman diterima','pengembalian diterima')";
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakActionPerformed
        // TODO add your handling code here:
        //String reportSource = null;
        //String reportDest = null;
        
        try{
            tblListPeminjaman.print(JTable.PrintMode.FIT_WIDTH, new MessageFormat("Laporan Peminjaman"), null);
//            com.mysql.jdbc.Connection c = (com.mysql.jdbc.Connection) conn;
//            reportSource = System.getProperty("user.dir") + "/laporan/Laporan Data Peminjaman.jrxml";
//            reportDest = System.getProperty("user.dir") + "/laporan/Laporan Data Peminjaman.jasper";
//            
//            JasperReport jasperReport = JasperCompileManager.compileReport(reportSource);
//            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,null,c);
//            JasperExportManager.exportReportToHtmlFile(jasperPrint, reportDest);
//            JasperViewer.viewReport(jasperPrint,false);
            
        }catch(Exception e){
            System.out.println(e);
        }
    }//GEN-LAST:event_btnCetakActionPerformed

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

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCariKode;
    private javax.swing.JButton btnCetak;
    private javax.swing.JButton btnDetail;
    private javax.swing.JButton btnEReset;
    private javax.swing.JButton btnESubmint;
    private javax.swing.JButton btnETambah;
    private javax.swing.JButton btnEditData;
    private javax.swing.JButton btnGanti;
    private javax.swing.JButton btnHistoryPeminjaman;
    private javax.swing.JButton btnHistoryPengembalian;
    private javax.swing.JButton btnPermohonan;
    private javax.swing.JButton btnProsesPeminjam;
    private javax.swing.JComboBox<String> cbJenisObjekPinjam;
    private javax.swing.JComboBox<String> cbObjek;
    private javax.swing.JComboBox<String> cbSort;
    private javax.swing.JComboBox<String> cbStatus;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbTampil;
    private javax.swing.JLabel lblID;
    private javax.swing.JLabel lblJTOT;
    private javax.swing.JPanel mainPanel;
    private com.toedter.calendar.JMonthChooser mcBulan;
    private javax.swing.JTable tblListPeminjaman;
    private javax.swing.JTable tblObjekPinjaman;
    private javax.swing.JTextField tfCariKode;
    private javax.swing.JTextField tfEJumlahTersedia;
    private javax.swing.JTextField tfENamaBarang;
    private javax.swing.JTextField tfETotalJumlah;
    // End of variables declaration//GEN-END:variables
}
