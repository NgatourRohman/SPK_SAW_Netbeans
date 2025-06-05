/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spk_saw.controllers;

/**
 *
 * @author ngato
 */
import spk_saw.config.KoneksiDB;

import java.sql.*;
import java.util.*;

public class SAWController {

    public static class Hasil {
        public int siswaId;
        public String namaSiswa;
        public double skor;

        public Hasil(int siswaId, String namaSiswa, double skor) {
            this.siswaId = siswaId;
            this.namaSiswa = namaSiswa;
            this.skor = skor;
        }
    }

    public static List<Hasil> hitungSAW() {
        List<Hasil> hasilList = new ArrayList<>();

        try (Connection conn = KoneksiDB.getConnection()) {
            // Ambil siswa
            Map<Integer, String> siswaMap = new HashMap<>();
            Statement stmt = conn.createStatement();
            ResultSet rsSiswa = stmt.executeQuery("SELECT * FROM siswa");
            while (rsSiswa.next()) {
                siswaMap.put(rsSiswa.getInt("id"), rsSiswa.getString("nama"));
            }

            // Ambil kriteria
            List<Integer> kriteriaId = new ArrayList<>();
            List<Double> bobot = new ArrayList<>();
            List<String> tipe = new ArrayList<>();
            ResultSet rsKriteria = stmt.executeQuery("SELECT * FROM kriteria");
            while (rsKriteria.next()) {
                kriteriaId.add(rsKriteria.getInt("id"));
                bobot.add(rsKriteria.getDouble("bobot"));
                tipe.add(rsKriteria.getString("tipe"));
            }

            // Ambil nilai siswa
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
                        nilai.add(0.0);
                    }
                }
                nilaiSiswa.put(idSiswa, nilai);
            }

            // Cari max/min
            List<Double> max = new ArrayList<>(Collections.nCopies(kriteriaId.size(), Double.MIN_VALUE));
            List<Double> min = new ArrayList<>(Collections.nCopies(kriteriaId.size(), Double.MAX_VALUE));

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

            // Tambahkan ke list hasil
            skorTotal.entrySet().stream()
                    .sorted((a, b) -> Double.compare(b.getValue(), a.getValue()))
                    .forEach(e -> hasilList.add(
                            new Hasil(e.getKey(), siswaMap.get(e.getKey()), e.getValue())
                    ));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hasilList;
    }
}
