/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tabel;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import java.sql.ResultSetMetaData;

/**
 *
 * @author DIMZY
 */
public class konsultasi {
    private Connection Koneksidb;
    private String username="root";
    private String password="";
    private String dbname="pbo_2310010293";
    private String urlKoneksi="jdbc:mysql://localhost/"+dbname;
    public String CEK_TGL, CEK_TANYA=null;
    public boolean duplikasi=false;
    
    public konsultasi(){
        try {
            Driver dbdriver = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(dbdriver);
            Koneksidb=DriverManager.getConnection(urlKoneksi,username,password);
            System.out.print("Database Terkoneksi");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e.toString());
        }
    }
    
    public void simpankonsultasi01(String id, String tgl, String tanya){
        try {
            String sqlsimpan="insert into konsultasi (id_kons, tanggal, pertanyaan) VALUES"
                    + " ('"+id+"', '"+tgl+"', '"+tanya+"')";
            String sqlcari="select*from konsultasi where id_kons='"+id+"'";
            Statement cari=Koneksidb.createStatement();
            ResultSet data=cari.executeQuery(sqlcari);
            if (data.next()){
                JOptionPane.showMessageDialog(null, "ID Konsultasi sudah terdaftar ");
            } else {
                Statement perintah=Koneksidb.createStatement();
                perintah.executeUpdate(sqlsimpan);
                JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
     public void simpankonsultasi02(String id, String tgl, String tanya){
        try {
            String sqlsimpan="insert into konsultasi(id_kons, tanggal, pertanyaan)"
                    + " value (?, ?, ?)";
            String sqlcari= "select*from konsultasi where id_kons=?";
            PreparedStatement cari=Koneksidb.prepareStatement(sqlcari);
            cari.setString(1, id);
            ResultSet data = cari.executeQuery();
            if (data.next()){
                JOptionPane.showMessageDialog(null, "ID sudah terdaftar");
                this.duplikasi=true;
                this.CEK_TGL=data.getString("tanggal");           
                this.CEK_TANYA=data.getString("pertanyaan");                      
            } else {
                this.duplikasi=false;
                this.CEK_TGL=null;
                this.CEK_TANYA=null;
                PreparedStatement perintah=Koneksidb.prepareStatement(sqlsimpan);
                perintah.setString(1, id );
                perintah.setString(2, tgl);
                perintah.setString(3, tanya);
                perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
     }
     
     public void ubahKonsultasi(String id, String tgl, String tanya){
        try {
            String sqlubah="UPDATE konsultasi SET tanggal=?, pertanyaan=? WHERE id_kons=?";        
                PreparedStatement perintah=Koneksidb.prepareStatement(sqlubah);
                perintah.setString(1, tgl);
                perintah.setString(2, tanya);
                perintah.setString(3, id);
                perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil diubah");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        }
     
      public void hapusKonsultasi(String id){
        try {
            String sqlhapus="delete from konsultasi where id_kons=?";        
                PreparedStatement perintah=Koneksidb.prepareStatement(sqlhapus);
                perintah.setString(1, id );
                perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    public void tampilDataKonsultasi(JTable komponentabel, String SQL){
        try {
            PreparedStatement perintah = Koneksidb.prepareStatement(SQL);
            ResultSet data = perintah.executeQuery();
            ResultSetMetaData meta = data.getMetaData();
            int jumlahkolom = meta.getColumnCount();
            DefaultTableModel modeltabel = new DefaultTableModel();
            modeltabel.addColumn("ID Konsultasi");
            modeltabel.addColumn("Tanggal Advokat");
            modeltabel.addColumn("Pertanyaan");
            while(data.next()){
                Object[] row = new Object[jumlahkolom];
                for(int i=1; i<=jumlahkolom; i++){
                    row[i-1]=data.getObject(i);
                }
                modeltabel.addRow(row);
            }
            komponentabel.setModel(modeltabel);
        } catch (Exception e) {
        }
    }    
}



