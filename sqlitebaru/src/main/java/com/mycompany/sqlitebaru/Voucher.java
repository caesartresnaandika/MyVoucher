/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.sqlitebaru;

public class Voucher {
    private int id_user;
    private int id_voucher;
    private String title_voucher;
    private String company;
    private String type;
    private int value;
    private String detail_voucher;
    private long valid_date; // Changed to long
    private long expired_date; // Changed to long
    private String description;
    private String image;

    public Voucher(int id_voucher, String title_voucher, String description1) {
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

    public Voucher(int id_user, int id_voucher, String title_voucher, String company, int value) {
        this.id_user = id_user;
        this.id_voucher = id_voucher;
        this.title_voucher = title_voucher;
        this.company = company;
        this.value = value;
    }

    public Voucher(int id_user, int id_voucher, String title_voucher, String company, int value, String detail_voucher) {
        this.id_user = id_user;
        this.id_voucher = id_voucher;
        this.title_voucher = title_voucher;
        this.company = company;
        this.value = value;
        this.detail_voucher = detail_voucher;
    }

    public Voucher(int id_user, int id_voucher, String title_voucher, String company, int value, String detail_voucher, long valid_date) {
        this.id_user = id_user;
        this.id_voucher = id_voucher;
        this.title_voucher = title_voucher;
        this.company = company;
        this.value = value;
        this.detail_voucher = detail_voucher;
        this.valid_date = valid_date;
    }

    public Voucher(int id_user, int id_voucher, String title_voucher, String company, int value, String detail_voucher, long valid_date, long expired_date) {
        this.id_user = id_user;
        this.id_voucher = id_voucher;
        this.title_voucher = title_voucher;
        this.company = company;
        this.value = value;
        this.detail_voucher = detail_voucher;
        this.valid_date = valid_date;
        this.expired_date = expired_date;
    }

    public Voucher(int id_user, int id_voucher, String title_voucher, String company, int value, String detail_voucher, long valid_date, long expired_date, String description, String image, String type) {
        this.id_user = id_user;
        this.id_voucher = id_voucher;
        this.title_voucher = title_voucher;
        this.company = company;
        this.value = value;
        this.detail_voucher = detail_voucher;
        this.valid_date = valid_date;
        this.expired_date = expired_date;
        this.description = description;
        this.image = image;
        this.type = type;
    }
    
    
    public Voucher(int id_user, int id_voucher, String title_voucher, String company, String type, int value, String detail_voucher, long valid_date, long expired_date, String description, String image) {
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

    public String getTitleVoucher() {
        return title_voucher;
    }

    public void setTitleVoucher(String title_voucher) {
        this.title_voucher = title_voucher;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDetailVoucher() {
        return detail_voucher;
    }

    public void setDetailVoucher(String detail_voucher) {
        this.detail_voucher = detail_voucher;
    }

    public long getValidDate() { // Changed to long
        return valid_date;
    }

    public void setValidDate(long valid_date) { // Changed to long
        this.valid_date = valid_date;
    }

    public long getExpiredDate() { // Changed to long
        return expired_date;
    }

    public void setExpiredDate(long expired_date) { // Changed to long
        this.expired_date = expired_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}