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
import java.util.*;

public class HasilSAWForm extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnProses;

    public HasilSAWForm() {
        setTitle("Hasil Perhitungan SAW");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        setLayout(null);

        btnProses = new JButton("Proses SAW");
        btnProses.setBounds(20, 20, 150, 30);
        add(btnProses);

        tableModel = new DefaultTableModel(new String[]{"ID", "Nama Siswa", "Skor Total"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 70, 640, 250);
        add(scrollPane);

        btnProses.addActionListener(e -> prosesSAW());
    }

    private void prosesSAW() {
        tableModel.setRowCount(0);

        try (Connection conn = KoneksiDB.getConnection()) {
            // Ambil semua siswa
            Map<Integer, String> siswaMap = new HashMap<>();
            Statement stmt = conn.createStatement();
            ResultSet rsSiswa = stmt.executeQuery("SELECT * FROM siswa");
            while (rsSiswa.next()) {
                siswaMap.put(rsSiswa.getInt("id"), rsSiswa.getString("nama"));
            }

            // Ambil kriteria dan bobot
            List<Integer> kriteriaId = new ArrayList<>();
            List<Double> bobot = new ArrayList<>();
            List<String> tipe = new ArrayList<>();
            ResultSet rsKriteria = stmt.executeQuery("SELECT * FROM kriteria");
            while (rsKriteria.next()) {
                kriteriaId.add(rsKriteria.getInt("id"));
                bobot.add(rsKriteria.getDouble("bobot"));
                tipe.add(rsKriteria.getString("tipe"));
            }

            // Ambil nilai siswa per kriteria
            Map<Integer, List<Double>> nilaiSiswa = new HashMap<>();
            for (Integer idSiswa : siswaMap.keySet()) {
                List<Double> nilai = new ArrayList<>();
                for (Integer idKriteria : kriteriaId) {
                    PreparedStatement ps = conn.prepareStatement(
                        "SELECT nilai FROM nilai WHERE siswa_id=? AND kriteria_id=?"
                    );
                    ps.setInt(1, idSiswa);
                    ps.setInt(2, idKriteria);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        nilai.add(rs.getDouble("nilai"));
                    } else {
                        nilai.add(0.0); // default jika tidak ada
                    }
                }
                nilaiSiswa.put(idSiswa, nilai);
            }

            // Normalisasi
            List<Double> max = new ArrayList<>(Collections.nCopies(kriteriaId.size(), Double.MIN_VALUE));
            List<Double> min = new ArrayList<>(Collections.nCopies(kriteriaId.size(), Double.MAX_VALUE));

            // cari max dan min per kriteria
            for (List<Double> nilai : nilaiSiswa.values()) {
                for (int i = 0; i < nilai.size(); i++) {
                    max.set(i, Math.max(max.get(i), nilai.get(i)));
                    min.set(i, Math.min(min.get(i), nilai.get(i)));
                }
            }

            // Hitung skor total
            Map<Integer, Double> skorTotal = new HashMap<>();
            for (Map.Entry<Integer, List<Double>> entry : nilaiSiswa.entrySet()) {
                int siswaId = entry.getKey();
                List<Double> nilai = entry.getValue();
                double total = 0.0;
                for (int i = 0; i < nilai.size(); i++) {
                    double norm = tipe.get(i).equals("benefit")
                        ? nilai.get(i) / max.get(i)
                        : min.get(i) / nilai.get(i);
                    total += norm * bobot.get(i);
                }
                skorTotal.put(siswaId, total);
            }

            // Urutkan dan tampilkan
            skorTotal.entrySet().stream()
                .sorted((a, b) -> Double.compare(b.getValue(), a.getValue())) // desc
                .forEach(e -> tableModel.addRow(new Object[]{
                    e.getKey(),
                    siswaMap.get(e.getKey()),
                    String.format("%.4f", e.getValue())
                }));

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error proses SAW: " + e.getMessage());
        }
    }
}
