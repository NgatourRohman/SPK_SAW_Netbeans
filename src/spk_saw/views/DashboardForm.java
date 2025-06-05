/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spk_saw.views;

/**
 *
 * @author ngato
 */
import spk_saw.views.panel.SiswaPanel;
import spk_saw.views.panel.KriteriaPanel;
import spk_saw.views.panel.NilaiPanel;
import spk_saw.views.panel.HasilPanel;

import javax.swing.*;
import java.awt.*;

public class DashboardForm extends JFrame {

    private JPanel mainPanel;

    public DashboardForm() {
        setTitle("Sistem Pendukung Keputusan - SAW");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        // Header atau Judul
        JLabel lblTitle = new JLabel("Dashboard SPK SAW", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setBounds(0, 10, 800, 30);
        add(lblTitle);

        // Tombol Navigasi
        JButton btnSiswa = new JButton("Siswa");
        JButton btnKriteria = new JButton("Kriteria");
        JButton btnNilai = new JButton("Nilai");
        JButton btnHasil = new JButton("Hasil SAW");

        btnSiswa.setBounds(50, 50, 150, 30);
        btnKriteria.setBounds(220, 50, 150, 30);
        btnNilai.setBounds(390, 50, 150, 30);
        btnHasil.setBounds(560, 50, 150, 30);

        add(btnSiswa);
        add(btnKriteria);
        add(btnNilai);
        add(btnHasil);

        // Panel Utama
        mainPanel = new JPanel();
        mainPanel.setBounds(30, 100, 720, 330);
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel);

        // Event untuk tombol
        btnSiswa.addActionListener(e -> showPanel(new SiswaPanel()));
        btnKriteria.addActionListener(e -> showPanel(new KriteriaPanel()));
        btnNilai.addActionListener(e -> showPanel(new NilaiPanel()));
        btnHasil.addActionListener(e -> showPanel(new HasilPanel()));

        // Tampilkan panel awal
        showPanel(new SiswaPanel());
    }

    private void showPanel(JPanel panel) {
        mainPanel.removeAll();
        mainPanel.add(panel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DashboardForm().setVisible(true);
        });
    }
}
