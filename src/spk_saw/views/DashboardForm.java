/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spk_saw.views;

/**
 *
 * @author ngato
 */
import javax.swing.*;

public class DashboardForm extends JFrame {

    private JButton btnSiswa, btnKriteria, btnNilai, btnHasil;

    public DashboardForm() {
        setTitle("Dashboard SPK - SAW");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JLabel title = new JLabel("Sistem Pendukung Keputusan (SAW)");
        title.setBounds(50, 20, 300, 30);
        add(title);

        btnSiswa = new JButton("Kelola Siswa");
        btnSiswa.setBounds(100, 70, 200, 30);
        add(btnSiswa);

        btnKriteria = new JButton("Kelola Kriteria");
        btnKriteria.setBounds(100, 110, 200, 30);
        add(btnKriteria);

        btnNilai = new JButton("Input Nilai");
        btnNilai.setBounds(100, 150, 200, 30);
        add(btnNilai);

        btnHasil = new JButton("Lihat Hasil SAW");
        btnHasil.setBounds(100, 190, 200, 30);
        add(btnHasil);

        // Action
        btnSiswa.addActionListener(e -> new SiswaForm().setVisible(true));
        btnKriteria.addActionListener(e -> new KriteriaForm().setVisible(true));
        btnNilai.addActionListener(e -> new NilaiForm().setVisible(true));
        btnHasil.addActionListener(e -> new HasilSAWForm().setVisible(true));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DashboardForm().setVisible(true));
    }
}
