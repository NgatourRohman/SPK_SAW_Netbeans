/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spk_saw.views;

/**
 *
 * @author ngato
 */
import spk_saw.config.KoneksiDB;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class SiswaForm extends JFrame {

    private DefaultTableModel tableModel;
    private JTextField tfNama;
    private JTable table;
    private JButton btnTambah, btnHapus, btnEdit;

    public SiswaForm() {
        setTitle("Manajemen Data Siswa");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
        loadData();
    }

    private void initComponents() {
        setLayout(null);

        JLabel lblNama = new JLabel("Nama Siswa:");
        lblNama.setBounds(20, 20, 100, 25);
        add(lblNama);

        tfNama = new JTextField();
        tfNama.setBounds(120, 20, 200, 25);
        add(tfNama);

        btnTambah = new JButton("Tambah");
        btnTambah.setBounds(330, 20, 90, 25);
        add(btnTambah);

        btnEdit = new JButton("Edit");
        btnEdit.setBounds(430, 20, 70, 25);
        add(btnEdit);

        btnHapus = new JButton("Hapus");
        btnHapus.setBounds(510, 20, 70, 25);
        add(btnHapus);

        tableModel = new DefaultTableModel(new String[]{"ID", "Nama"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 60, 560, 280);
        add(scrollPane);

        // EVENT BUTTON
        btnTambah.addActionListener(e -> tambahData());
        btnEdit.addActionListener(e -> editData());
        btnHapus.addActionListener(e -> hapusData());
        table.getSelectionModel().addListSelectionListener(e -> isiFormDariTable());
    }

    private void loadData() {
        try (Connection conn = KoneksiDB.getConnection()) {
            tableModel.setRowCount(0); // Bersihkan tabel
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM siswa");

            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("nama")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error load data: " + e.getMessage());
        }
    }

    private void tambahData() {
        String nama = tfNama.getText().trim();
        if (nama.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama tidak boleh kosong!");
            return;
        }

        try (Connection conn = KoneksiDB.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO siswa(nama) VALUES (?)");
            ps.setString(1, nama);
            ps.executeUpdate();
            tfNama.setText("");
            loadData();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error tambah: " + e.getMessage());
        }
    }

    private void editData() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) return;

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        String nama = tfNama.getText().trim();

        try (Connection conn = KoneksiDB.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("UPDATE siswa SET nama=? WHERE id=?");
            ps.setString(1, nama);
            ps.setInt(2, id);
            ps.executeUpdate();
            loadData();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error edit: " + e.getMessage());
        }
    }

    private void hapusData() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) return;

        int id = (int) tableModel.getValueAt(selectedRow, 0);

        try (Connection conn = KoneksiDB.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM siswa WHERE id=?");
            ps.setInt(1, id);
            ps.executeUpdate();
            tfNama.setText("");
            loadData();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error hapus: " + e.getMessage());
        }
    }

    private void isiFormDariTable() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String nama = (String) tableModel.getValueAt(selectedRow, 1);
            tfNama.setText(nama);
        }
    }
}
