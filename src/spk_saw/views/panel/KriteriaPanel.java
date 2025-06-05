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
import spk_saw.models.Kriteria;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class KriteriaPanel extends JPanel {

    private JTextField tfNama, tfBobot;
    private JComboBox<String> cbTipe;
    private JTable table;
    private DefaultTableModel tableModel;

    public KriteriaPanel() {
        setLayout(null);

        JLabel lblNama = new JLabel("Nama Kriteria:");
        lblNama.setBounds(20, 20, 100, 25);
        add(lblNama);

        tfNama = new JTextField();
        tfNama.setBounds(130, 20, 150, 25);
        add(tfNama);

        JLabel lblBobot = new JLabel("Bobot:");
        lblBobot.setBounds(20, 55, 100, 25);
        add(lblBobot);

        tfBobot = new JTextField();
        tfBobot.setBounds(130, 55, 150, 25);
        add(tfBobot);

        JLabel lblTipe = new JLabel("Tipe:");
        lblTipe.setBounds(20, 90, 100, 25);
        add(lblTipe);

        cbTipe = new JComboBox<>(new String[]{"benefit", "cost"});
        cbTipe.setBounds(130, 90, 150, 25);
        add(cbTipe);

        JButton btnTambah = new JButton("Tambah");
        btnTambah.setBounds(300, 20, 100, 25);
        add(btnTambah);

        JButton btnEdit = new JButton("Edit");
        btnEdit.setBounds(410, 20, 100, 25);
        add(btnEdit);

        JButton btnHapus = new JButton("Hapus");
        btnHapus.setBounds(520, 20, 100, 25);
        add(btnHapus);

        tableModel = new DefaultTableModel(new String[]{"ID", "Nama", "Bobot", "Tipe"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 130, 700, 200);
        add(scrollPane);

        btnTambah.addActionListener(e -> tambahData());
        btnEdit.addActionListener(e -> editData());
        btnHapus.addActionListener(e -> hapusData());
        table.getSelectionModel().addListSelectionListener(e -> isiForm());

        loadData();
    }

    private void loadData() {
        tableModel.setRowCount(0);
        List<Kriteria> list = KriteriaController.getAll();
        for (Kriteria k : list) {
            tableModel.addRow(new Object[]{k.getId(), k.getNamaKriteria(), k.getBobot(), k.getTipe()});
        }
    }

    private void tambahData() {
        try {
            String nama = tfNama.getText().trim();
            double bobot = Double.parseDouble(tfBobot.getText().trim());
            String tipe = cbTipe.getSelectedItem().toString();
            KriteriaController.insert(nama, bobot, tipe);
            tfNama.setText(""); tfBobot.setText("");
            loadData();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Bobot harus angka.");
        }
    }

    private void editData() {
        int row = table.getSelectedRow();
        if (row == -1) return;
        int id = (int) tableModel.getValueAt(row, 0);
        String nama = tfNama.getText().trim();
        double bobot = Double.parseDouble(tfBobot.getText().trim());
        String tipe = cbTipe.getSelectedItem().toString();
        KriteriaController.update(id, nama, bobot, tipe);
        loadData();
    }

    private void hapusData() {
        int row = table.getSelectedRow();
        if (row == -1) return;
        int id = (int) tableModel.getValueAt(row, 0);
        KriteriaController.delete(id);
        tfNama.setText(""); tfBobot.setText("");
        loadData();
    }

    private void isiForm() {
        int row = table.getSelectedRow();
        if (row != -1) {
            tfNama.setText(tableModel.getValueAt(row, 1).toString());
            tfBobot.setText(tableModel.getValueAt(row, 2).toString());
            cbTipe.setSelectedItem(tableModel.getValueAt(row, 3));
        }
    }
}
