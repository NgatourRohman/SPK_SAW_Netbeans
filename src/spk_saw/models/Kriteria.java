/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spk_saw.models;

/**
 *
 * @author ngato
 */
public class Kriteria {

    private int id;
    private String namaKriteria;
    private double bobot;
    private String tipe;

    public Kriteria(int id, String namaKriteria, double bobot, String tipe) {
        this.id = id;
        this.namaKriteria = namaKriteria;
        this.bobot = bobot;
        this.tipe = tipe;
    }

    public int getId() {
        return id;
    }

    public String getNamaKriteria() {
        return namaKriteria;
    }

    public double getBobot() {
        return bobot;
    }

    public String getTipe() {
        return tipe;
    }

    @Override
    public String toString() {
        return namaKriteria + " (" + tipe + ")";
    }
}
