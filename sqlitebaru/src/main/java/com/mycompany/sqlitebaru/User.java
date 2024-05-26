/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sqlitebaru;

/**
 *
 * @author LENOVO_THINKPAD
 */
public class User {
    private int id_user;
    private String nama_depan;
    private String nama_belakang;
    private String email;
    private String password;

    // Constructor tanpa parameter
    public User() {
    }

    // Constructor dengan parameter
    public User(int id_user, String nama_depan, String nama_belakang, String email, String password) {
        this.id_user = id_user;
        this.nama_depan = nama_depan;
        this.nama_belakang = nama_belakang;
        this.email = email;
        this.password = password;
    }

    // Getter dan Setter untuk id_user
    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    // Getter dan Setter untuk nama_depan
    public String getNama_depan() {
        return nama_depan;
    }

    public void setNama_depan(String nama_depan) {
        this.nama_depan = nama_depan;
    }

    // Getter dan Setter untuk nama_belakang
    public String getNama_belakang() {
        return nama_belakang;
    }

    public void setNama_belakang(String nama_belakang) {
        this.nama_belakang = nama_belakang;
    }

    // Getter dan Setter untuk email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter dan Setter untuk password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
