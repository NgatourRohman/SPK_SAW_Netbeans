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
import spk_saw.models.Siswa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SiswaController {

    public static List<Siswa> getAll() {
        List<Siswa> list = new ArrayList<>();
        try (Connection conn = KoneksiDB.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM siswa");
            while (rs.next()) {
                list.add(new Siswa(rs.getInt("id"), rs.getString("nama")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void insert(String nama) {
        try (Connection conn = KoneksiDB.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO siswa(nama) VALUES (?)");
            ps.setString(1, nama);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void update(int id, String nama) {
        try (Connection conn = KoneksiDB.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("UPDATE siswa SET nama=? WHERE id=?");
            ps.setString(1, nama);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void delete(int id) {
        try (Connection conn = KoneksiDB.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM siswa WHERE id=?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

