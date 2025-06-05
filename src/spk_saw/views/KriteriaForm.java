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
import java.awt.event.*;
import java.sql.*;
import spk_saw.controllers.KriteriaController;
import spk_saw.models.Kriteria;

public class KriteriaForm extends JFrame {

    private JTextField tfNama, tfBobot;
    private JComboBox<String> cbTipe;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnTambah, btnEdit, btnHapus;

    public KriteriaForm() {
        setTitle("Manajemen Data Kriteria");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        loadData();
    }

    private void initComponents() {
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

        btnTambah = new JButton("Tambah");
        btnTambah.setBounds(300, 20, 90, 25);
        add(btnTambah);

        btnEdit = new JButton("Edit");
        btnEdit.setBounds(400, 20, 70, 25);
        add(btnEdit);

        btnHapus = new JButton("Hapus");
        btnHapus.setBounds(480, 20, 80, 25);
        add(btnHapus);

        tableModel = new DefaultTableModel(new String[]{"ID", "Nama", "Bobot", "Tipe"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 130, 540, 200);
        add(scrollPane);

        // Event Listener
        btnTambah.addActionListener(e -> tambahData());
        btnEdit.addActionListener(e -> editData());
        btnHapus.addActionListener(e -> hapusData());
        table.getSelectionModel().addListSelectionListener(e -> isiFormDariTable());

        JButton btnBack = new JButton("Kembali");
        btnBack.setBounds(20, 350, 100, 30); // posisi di bawah tabel
        add(btnBack);

        btnBack.addActionListener(e -> {
            dispose();
            new DashboardForm().setVisible(true);
        });
    }

    private void loadData() {
        tableModel.setRowCount(0);
        for (Kriteria k : KriteriaController.getAll()) {
            tableModel.addRow(new Object[]{
                k.getId(), k.getNamaKriteria(), k.getBobot(), k.getTipe()
            });
        }
    }

    private void tambahData() {
        String nama = tfNama.getText().trim();
        String bobotText = tfBobot.getText().trim();
        String tipe = (String) cbTipe.getSelectedItem();

        if (nama.isEmpty() || bobotText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama dan bobot wajib diisi!");
            return;
        }

        try {
            double bobot = Double.parseDouble(bobotText);
            KriteriaController.insert(nama, bobot, tipe);
            tfNama.setText("");
            tfBobot.setText("");
            cbTipe.setSelectedIndex(0);
            loadData();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Bobot harus angka!");
        }
    }

    private void editData() {
        int selected = table.getSelectedRow();
        if (selected == -1) {
            return;
        }

        int id = (int) tableModel.getValueAt(selected, 0);
        String nama = tfNama.getText().trim();
        String bobotText = tfBobot.getText().trim();
        String tipe = (String) cbTipe.getSelectedItem();

        try {
            double bobot = Double.parseDouble(bobotText);
            KriteriaController.update(id, nama, bobot, tipe);
            loadData();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Bobot harus angka!");
        }
    }

    private void hapusData() {
        int selected = table.getSelectedRow();
        if (selected == -1) {
            return;
        }

        int id = (int) tableModel.getValueAt(selected, 0);
        KriteriaController.delete(id);
        tfNama.setText("");
        tfBobot.setText("");
        cbTipe.setSelectedIndex(0);
        loadData();
    }

    private void isiFormDariTable() {
        int selected = table.getSelectedRow();
        if (selected != -1) {
            tfNama.setText((String) tableModel.getValueAt(selected, 1));
            tfBobot.setText(String.valueOf(tableModel.getValueAt(selected, 2)));
            cbTipe.setSelectedItem(tableModel.getValueAt(selected, 3));
        }
    }
}
