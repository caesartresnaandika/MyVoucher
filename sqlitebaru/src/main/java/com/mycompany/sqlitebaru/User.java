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

 

    public User(String nama_depan, String nama_belakang) {
        this.nama_depan = nama_depan;
        this.nama_belakang = nama_belakang;
    }

    public User(int id_user, String nama_depan, String nama_belakang) {
        this.id_user = id_user;
        this.nama_depan = nama_depan;
        this.nama_belakang = nama_belakang;
    }

    public User(int id_user, String nama_depan, String nama_belakang, String email) {
        this.id_user = id_user;
        this.nama_depan = nama_depan;
        this.nama_belakang = nama_belakang;
        this.email = email;
    }

    public User(String nama_depan, String nama_belakang, String kategori) {
        this.nama_depan = nama_depan;
        this.nama_belakang = nama_belakang;
        this.email = email;
    }

    public int getId() {
        return id_user;
    }

    public void setId(int id_user) {
        this.id_user = id_user;
    }

    public String getnama_depan() {
        return nama_depan;
    }

    public void setnama_depan(String nama_depan) {
        this.nama_depan = nama_depan;
    }

    public String getnama_belakang() {
        return nama_belakang;
    }

    public void setnama_belakang(String nama_belakang) {
        this.nama_belakang = nama_belakang;
    }

    public String getemail() {
        return email;
    }

    public void setemail(String email) {
        this.email = email;
    }
//        public static void main(String[] args) {
//            System.out.println("");();
//    }

}
