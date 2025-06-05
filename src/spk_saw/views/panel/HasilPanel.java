/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spk_saw.views.panel;

/**
 *
 * @author ngato
 */
import spk_saw.controllers.SAWController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class HasilPanel extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;

    public HasilPanel() {
        setLayout(null);

        JButton btnProses = new JButton("Proses SAW");
        btnProses.setBounds(20, 20, 150, 30);
        add(btnProses);

        tableModel = new DefaultTableModel(new String[]{"ID", "Nama Siswa", "Skor Total"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 70, 700, 250);
        add(scrollPane);

        btnProses.addActionListener(e -> prosesSAW());
    }

    private void prosesSAW() {
        tableModel.setRowCount(0);
        for (SAWController.Hasil h : SAWController.hitungSAW()) {
            tableModel.addRow(new Object[]{h.siswaId, h.namaSiswa, String.format("%.4f", h.skor)});
        }
    }
}
