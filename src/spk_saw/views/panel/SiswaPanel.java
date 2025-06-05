/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spk_saw.views.panel;

/**
 *
 * @author ngato
 */
import spk_saw.controllers.SiswaController;
import spk_saw.models.Siswa;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class SiswaPanel extends JPanel {

    private DefaultTableModel tableModel;
    private JTextField tfNama;
    private JTable table;

    public SiswaPanel() {
        setLayout(null);

        JLabel lblNama = new JLabel("Nama Siswa:");
        lblNama.setBounds(20, 20, 100, 25);
        add(lblNama);

        tfNama = new JTextField();
        tfNama.setBounds(120, 20, 200, 25);
        add(tfNama);

        JButton btnTambah = new JButton("Tambah");
        JButton btnEdit = new JButton("Edit");
        JButton btnHapus = new JButton("Hapus");

        btnTambah.setBounds(340, 20, 100, 25);
        btnEdit.setBounds(450, 20, 100, 25);
        btnHapus.setBounds(560, 20, 100, 25);

        add(btnTambah);
        add(btnEdit);
        add(btnHapus);

        tableModel = new DefaultTableModel(new String[]{"ID", "Nama"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 60, 700, 250);
        add(scrollPane);

        btnTambah.addActionListener(e -> tambahData());
        btnEdit.addActionListener(e -> editData());
        btnHapus.addActionListener(e -> hapusData());
        table.getSelectionModel().addListSelectionListener(e -> isiForm());

        loadData();
    }

    private void loadData() {
        tableModel.setRowCount(0);
        List<Siswa> list = SiswaController.getAll();
        for (Siswa s : list) {
            tableModel.addRow(new Object[]{s.getId(), s.getNama()});
        }
    }

    private void tambahData() {
        String nama = tfNama.getText().trim();
        if (nama.isEmpty()) return;
        SiswaController.insert(nama);
        tfNama.setText("");
        loadData();
    }

    private void editData() {
        int selected = table.getSelectedRow();
        if (selected == -1) return;
        int id = (int) tableModel.getValueAt(selected, 0);
        String nama = tfNama.getText().trim();
        SiswaController.update(id, nama);
        loadData();
    }

    private void hapusData() {
        int selected = table.getSelectedRow();
        if (selected == -1) return;
        int id = (int) tableModel.getValueAt(selected, 0);
        SiswaController.delete(id);
        tfNama.setText("");
        loadData();
    }

    private void isiForm() {
        int selected = table.getSelectedRow();
        if (selected != -1) {
            tfNama.setText(tableModel.getValueAt(selected, 1).toString());
        }
    }
}
