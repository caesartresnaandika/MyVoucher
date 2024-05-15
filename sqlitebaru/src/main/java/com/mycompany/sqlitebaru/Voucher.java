/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sqlitebaru;

/**
 *
 * @author LENOVO_THINKPAD
 */
public class Voucher {
    private int id_user;
    private int id_voucher;
    private String title_voucher;
    private String company;
    private String type;
    private int value;
    private String detail_voucher;
    private int valid_date;
    private int expired_date;
    private String description;
    private String image;
    
    public Voucher(int id_voucher, String title_voucher) {
        this.id_voucher = id_voucher;
        this.title_voucher = title_voucher;
    }

    public Voucher(int id_user, int id_voucher, String title_voucher) {
        this.id_user = id_user;
        this.id_voucher = id_voucher;
        this.title_voucher = title_voucher;
    }

    public Voucher(int id_user, int id_voucher, String title_voucher, String company) {
        this.id_user = id_user;
        this.id_voucher = id_voucher;
        this.title_voucher = title_voucher;
        this.company = company;
    }
    
    public Voucher(int id_user, int id_voucher, String title_voucher, String company, String type) {
        this.id_user = id_user;
        this.id_voucher = id_voucher;
        this.title_voucher = title_voucher;
        this.company = company;
        this.type = type;
    }
    
    public Voucher(int id_user, int id_voucher, String title_voucher, String company, String type, int value) {
        this.id_user = id_user;
        this.id_voucher = id_voucher;
        this.title_voucher = title_voucher;
        this.company = company;
        this.type = type;
        this.value = value;
    }
    
    public Voucher(int id_user, int id_voucher, String title_voucher, String company, String type, int value, String detail_voucher) {
        this.id_user = id_user;
        this.id_voucher = id_voucher;
        this.title_voucher = title_voucher;
        this.company = company;
        this.type = type;
        this.value = value;
        this.detail_voucher = detail_voucher;
    }
    
    public Voucher(int id_user, int id_voucher, String title_voucher, String company, String type, int value, String detail_voucher, int valid_date) {
        this.id_user = id_user;
        this.id_voucher = id_voucher;
        this.title_voucher = title_voucher;
        this.company = company;
        this.type = type;
        this.value = value;
        this.detail_voucher = detail_voucher;
        this.valid_date = valid_date;
    }
    
    public Voucher(int id_user, int id_voucher, String title_voucher, String company, String type, int value, String detail_voucher, int valid_date, int expired_date) {
        this.id_user = id_user;
        this.id_voucher = id_voucher;
        this.title_voucher = title_voucher;
        this.company = company;
        this.type = type;
        this.value = value;
        this.detail_voucher = detail_voucher;
        this.valid_date = valid_date;
        this.expired_date = expired_date;
    }
    
    public Voucher(int id_user, int id_voucher, String title_voucher, String company, String type, int value, String detail_voucher, int valid_date, int expired_date, String description, String image) {
        this.id_user = id_user;
        this.id_voucher = id_voucher;
        this.title_voucher = title_voucher;
        this.company = company;
        this.type = type;
        this.value = value;
        this.detail_voucher = detail_voucher;
        this.valid_date = valid_date;
        this.expired_date = expired_date;
        this.description = description;
        this.image = image;
    }
    

    public int getId() {
        return id_user;
    }

    public void setId(int id_user) {
        this.id_user = id_user;
    }
    
    public int getIdVoucher() {
        return id_voucher;
    }

    public void setIdVoucher(int id_voucher) {
        this.id_voucher = id_voucher;
    }

    public String gettitle_voucher() {
        return title_voucher;
    }

    public void settitle_voucher(String title_voucher) {
        this.title_voucher = title_voucher;
    }
    
    public String getcompany() {
        return company;
    }

    public void setcompany(String company) {
        this.company = company;
    }
//    public String gettype {
//        return type;
//    }

    public void settype(String type) {
        this.type = type;
    }
    
    public int getvalue() {
        return value;
    }

    public void setvalue(int value) {
        this.value = value;
    }
    
    public String getdetail_voucher() {
        return detail_voucher;
    }

    public void setdetail_voucher(String detail_voucher) {
        this.detail_voucher = detail_voucher;
    }
    
    public int getvalid_date() {
        return valid_date;
    }

    public void setvaild_date(int valid_date) {
        this.valid_date = valid_date;
    }
    
    public int getexpired_date() {
        return expired_date;
    }

    public void setexpired_date(int expired_date) {
        this.expired_date = expired_date;
    }
    
    public String getdescription() {
        return description;
    }

    public void setdescription(String description) {
        this.description = description;
    }
    
    public String getimage() {
        return image;
    }

    public void setimage(String image) {
        this.image = image;
    }
    
}

