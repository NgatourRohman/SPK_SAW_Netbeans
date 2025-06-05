/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spk_saw.views;

/**
 *
 * @author ngato
 */
import spk_saw.controllers.SiswaController;
import spk_saw.models.Siswa;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.util.List;

public class SiswaForm extends JFrame {

    private DefaultTableModel tableModel;
    private JTextField tfNama;
    private JTable table;
    private JButton btnTambah, btnHapus, btnEdit, btnBack;

    public SiswaForm() {
        setTitle("Manajemen Data Siswa");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
        scrollPane.setBounds(20, 60, 560, 250);
        add(scrollPane);

        btnBack = new JButton("Kembali");
        btnBack.setBounds(20, 320, 100, 30);
        add(btnBack);

        // EVENT BUTTON
        btnTambah.addActionListener(e -> tambahData());
        btnEdit.addActionListener(e -> editData());
        btnHapus.addActionListener(e -> hapusData());
        btnBack.addActionListener(e -> {
            dispose();
            new DashboardForm().setVisible(true);
        });
        table.getSelectionModel().addListSelectionListener(e -> isiFormDariTable());
    }

    private void loadData() {
        tableModel.setRowCount(0);
        List<Siswa> siswaList = SiswaController.getAll();
        for (Siswa s : siswaList) {
            tableModel.addRow(new Object[]{s.getId(), s.getNama()});
        }
    }

    private void tambahData() {
        String nama = tfNama.getText().trim();
        if (nama.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama tidak boleh kosong!");
            return;
        }
        SiswaController.insert(nama);
        tfNama.setText("");
        loadData();
    }

    private void editData() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        String nama = tfNama.getText().trim();

        SiswaController.update(id, nama);
        loadData();
    }

    private void hapusData() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        SiswaController.delete(id);
        tfNama.setText("");
        loadData();
    }

    private void isiFormDariTable() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            tfNama.setText((String) tableModel.getValueAt(selectedRow, 1));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SiswaForm().setVisible(true));
    }
}
