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
import spk_saw.models.Nilai;

import java.sql.*;
import java.util.*;

public class NilaiController {

    public static double getNilai(int siswaId, int kriteriaId) {
        try (Connection conn = KoneksiDB.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT nilai FROM nilai WHERE siswa_id=? AND kriteria_id=?");
            ps.setInt(1, siswaId);
            ps.setInt(2, kriteriaId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("nilai");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public static void insertAtauUpdate(int siswaId, int kriteriaId, double nilai) {
        try (Connection conn = KoneksiDB.getConnection()) {
            PreparedStatement psCek = conn.prepareStatement("SELECT * FROM nilai WHERE siswa_id=? AND kriteria_id=?");
            psCek.setInt(1, siswaId);
            psCek.setInt(2, kriteriaId);
            ResultSet rs = psCek.executeQuery();

            if (rs.next()) {
                PreparedStatement psUpdate = conn.prepareStatement("UPDATE nilai SET nilai=? WHERE siswa_id=? AND kriteria_id=?");
                psUpdate.setDouble(1, nilai);
                psUpdate.setInt(2, siswaId);
                psUpdate.setInt(3, kriteriaId);
                psUpdate.executeUpdate();
            } else {
                PreparedStatement psInsert = conn.prepareStatement("INSERT INTO nilai(siswa_id, kriteria_id, nilai) VALUES (?, ?, ?)");
                psInsert.setInt(1, siswaId);
                psInsert.setInt(2, kriteriaId);
                psInsert.setDouble(3, nilai);
                psInsert.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
