/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spk_saw.views.panel;

/**
 *
 * @author ngato
 */
import spk_saw.controllers.KriteriaController;
import spk_saw.controllers.NilaiController;
import spk_saw.controllers.SiswaController;
import spk_saw.models.Kriteria;
import spk_saw.models.Siswa;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class NilaiPanel extends JPanel {

    private JComboBox<String> cbSiswa;
    private JTable table;
    private DefaultTableModel tableModel;

    public NilaiPanel() {
        setLayout(null);

        JLabel lblSiswa = new JLabel("Pilih Siswa:");
        lblSiswa.setBounds(20, 20, 100, 25);
        add(lblSiswa);

        cbSiswa = new JComboBox<>();
        cbSiswa.setBounds(120, 20, 250, 25);
        add(cbSiswa);

        tableModel = new DefaultTableModel(new String[]{"ID Kriteria", "Nama Kriteria", "Nilai"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return column == 2;
            }
        };

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 60, 700, 220);
        add(scrollPane);

        JButton btnSimpan = new JButton("Simpan Nilai");
        btnSimpan.setBounds(20, 300, 150, 30);
        add(btnSimpan);

        cbSiswa.addActionListener(e -> loadNilai());
        btnSimpan.addActionListener(e -> simpanNilai());

        loadSiswa();
    }

    private void loadSiswa() {
        cbSiswa.removeAllItems();
        for (Siswa s : SiswaController.getAll()) {
            cbSiswa.addItem(s.getId() + " - " + s.getNama());
        }
    }

    private void loadNilai() {
        if (cbSiswa.getItemCount() == 0) return;
        int siswaId = Integer.parseInt(cbSiswa.getSelectedItem().toString().split(" - ")[0]);
        tableModel.setRowCount(0);
        for (Kriteria k : KriteriaController.getAll()) {
            double nilai = NilaiController.getNilai(siswaId, k.getId());
            tableModel.addRow(new Object[]{k.getId(), k.getNamaKriteria(), nilai});
        }
    }

    private void simpanNilai() {
        int siswaId = Integer.parseInt(cbSiswa.getSelectedItem().toString().split(" - ")[0]);
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            int kriteriaId = (int) tableModel.getValueAt(i, 0);
            double nilai = Double.parseDouble(tableModel.getValueAt(i, 2).toString());
            NilaiController.insertAtauUpdate(siswaId, kriteriaId, nilai);
        }
        JOptionPane.showMessageDialog(this, "Nilai berhasil disimpan!");
    }
}
