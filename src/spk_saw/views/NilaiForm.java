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

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Vector;

public class NilaiForm extends JFrame {
    private JComboBox<String> cbSiswa;
    private JTable tableNilai;
    private DefaultTableModel tableModel;
    private JButton btnSimpan;

    public NilaiForm() {
        setTitle("Input Nilai Siswa");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
        loadSiswa();
    }

    private void initComponents() {
        setLayout(null);

        JLabel lblSiswa = new JLabel("Pilih Siswa:");
        lblSiswa.setBounds(20, 20, 100, 25);
        add(lblSiswa);

        cbSiswa = new JComboBox<>();
        cbSiswa.setBounds(120, 20, 250, 25);
        add(cbSiswa);

        cbSiswa.addActionListener(e -> loadNilai());

        tableModel = new DefaultTableModel(new String[]{"ID Kriteria", "Nama Kriteria", "Nilai"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return column == 2; // hanya kolom nilai yang bisa diubah
            }
        };

        tableNilai = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableNilai);
        scrollPane.setBounds(20, 60, 640, 240);
        add(scrollPane);

        btnSimpan = new JButton("Simpan Nilai");
        btnSimpan.setBounds(20, 310, 150, 30);
        add(btnSimpan);

        btnSimpan.addActionListener(e -> simpanNilai());
        
        JButton btnBack = new JButton("Kembali");
        btnBack.setBounds(20, 350, 100, 30); // posisi di bawah tabel
        add(btnBack);

        btnBack.addActionListener(e -> {
            dispose();
            new DashboardForm().setVisible(true);
        });
    }

    private void loadSiswa() {
        cbSiswa.removeAllItems();
        try (Connection conn = KoneksiDB.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, nama FROM siswa");
            while (rs.next()) {
                cbSiswa.addItem(rs.getInt("id") + " - " + rs.getString("nama"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal load siswa: " + e.getMessage());
        }
    }

    private void loadNilai() {
        if (cbSiswa.getItemCount() == 0) return;

        int siswaId = Integer.parseInt(cbSiswa.getSelectedItem().toString().split(" - ")[0]);
        tableModel.setRowCount(0);

        try (Connection conn = KoneksiDB.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM kriteria");

            while (rs.next()) {
                int idKriteria = rs.getInt("id");
                String nama = rs.getString("nama_kriteria");

                // cek apakah sudah ada nilai
                PreparedStatement ps = conn.prepareStatement("SELECT nilai FROM nilai WHERE siswa_id=? AND kriteria_id=?");
                ps.setInt(1, siswaId);
                ps.setInt(2, idKriteria);
                ResultSet rsNilai = ps.executeQuery();

                double nilai = 0.0;
                if (rsNilai.next()) {
                    nilai = rsNilai.getDouble("nilai");
                }

                tableModel.addRow(new Object[]{idKriteria, nama, nilai});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal load nilai: " + e.getMessage());
        }
    }

    private void simpanNilai() {
        if (cbSiswa.getItemCount() == 0) return;

        int siswaId = Integer.parseInt(cbSiswa.getSelectedItem().toString().split(" - ")[0]);

        try (Connection conn = KoneksiDB.getConnection()) {
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                int idKriteria = (int) tableModel.getValueAt(i, 0);
                double nilai = Double.parseDouble(tableModel.getValueAt(i, 2).toString());

                // cek apakah data sudah ada
                PreparedStatement psCek = conn.prepareStatement("SELECT * FROM nilai WHERE siswa_id=? AND kriteria_id=?");
                psCek.setInt(1, siswaId);
                psCek.setInt(2, idKriteria);
                ResultSet rsCek = psCek.executeQuery();

                if (rsCek.next()) {
                    // update
                    PreparedStatement psUpdate = conn.prepareStatement("UPDATE nilai SET nilai=? WHERE siswa_id=? AND kriteria_id=?");
                    psUpdate.setDouble(1, nilai);
                    psUpdate.setInt(2, siswaId);
                    psUpdate.setInt(3, idKriteria);
                    psUpdate.executeUpdate();
                } else {
                    // insert
                    PreparedStatement psInsert = conn.prepareStatement("INSERT INTO nilai(siswa_id, kriteria_id, nilai) VALUES (?, ?, ?)");
                    psInsert.setInt(1, siswaId);
                    psInsert.setInt(2, idKriteria);
                    psInsert.setDouble(3, nilai);
                    psInsert.executeUpdate();
                }
            }

            JOptionPane.showMessageDialog(this, "Nilai berhasil disimpan!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal simpan nilai: " + e.getMessage());
        }
    }
}
