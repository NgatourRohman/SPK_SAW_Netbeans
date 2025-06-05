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
import spk_saw.models.Kriteria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KriteriaController {

    public static List<Kriteria> getAll() {
        List<Kriteria> list = new ArrayList<>();
        try (Connection conn = KoneksiDB.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM kriteria");
            while (rs.next()) {
                list.add(new Kriteria(
                        rs.getInt("id"),
                        rs.getString("nama_kriteria"),
                        rs.getDouble("bobot"),
                        rs.getString("tipe")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void insert(String nama, double bobot, String tipe) {
        try (Connection conn = KoneksiDB.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO kriteria(nama_kriteria, bobot, tipe) VALUES (?, ?, ?)");
            ps.setString(1, nama);
            ps.setDouble(2, bobot);
            ps.setString(3, tipe);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void update(int id, String nama, double bobot, String tipe) {
        try (Connection conn = KoneksiDB.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("UPDATE kriteria SET nama_kriteria=?, bobot=?, tipe=? WHERE id=?");
            ps.setString(1, nama);
            ps.setDouble(2, bobot);
            ps.setString(3, tipe);
            ps.setInt(4, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void delete(int id) {
        try (Connection conn = KoneksiDB.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM kriteria WHERE id=?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
