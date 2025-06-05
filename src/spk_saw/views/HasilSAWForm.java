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
import spk_saw.controllers.SAWController;

public class HasilSAWForm extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnProses;

    public HasilSAWForm() {
        setTitle("Hasil Perhitungan SAW");
        setSize(700, 450);
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

        JButton btnBack = new JButton("Kembali");
        btnBack.setBounds(20, 350, 100, 30); // posisi di bawah tabel
        add(btnBack);

        btnBack.addActionListener(e -> {
            dispose();
            new DashboardForm().setVisible(true);
        });
    }

    private void prosesSAW() {
        tableModel.setRowCount(0);

        List<SAWController.Hasil> hasilList = SAWController.hitungSAW();
        for (SAWController.Hasil hasil : hasilList) {
            tableModel.addRow(new Object[]{
                hasil.siswaId,
                hasil.namaSiswa,
                String.format("%.4f", hasil.skor)
            });
        }
    }

}
