
package com.mycompany.sqlitebaru;

public class History {
    private int id_voucher;
    private int id_user;
    private String title_voucher;
    private String company;
    private String type;
    private String detail_voucher;
    private long valid_date;
    private long expired_date;
    private String description;
    private long use_date;

    // Constructor
    public History(int id_voucher, int id_user, String title_voucher, String company, String type, String detail_voucher, long valid_date, long expired_date, String description, long use_date) {
        this.id_voucher = id_voucher;
        this.id_user = id_user;
        this.title_voucher = title_voucher;
        this.company = company;
        this.type = type;
        this.detail_voucher = detail_voucher;
        this.valid_date = valid_date;
        this.expired_date = expired_date;
        this.description = description;
        this.use_date = use_date;
    }

    // Getter and Setter for id_voucher
    public int getId_voucher() {
        return id_voucher;
    }

    public void setId_voucher(int id_voucher) {
        this.id_voucher = id_voucher;
    }

    // Getter and Setter for id_user
    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    // Getter and Setter for title_voucher
    public String getTitle_voucher() {
        return title_voucher;
    }

    public void setTitle_voucher(String title_voucher) {
        this.title_voucher = title_voucher;
    }

    // Getter and Setter for company
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    // Getter and Setter for type
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    // Getter and Setter for detail_voucher
    public String getDetail_voucher() {
        return detail_voucher;
    }

    public void setDetail_voucher(String detail_voucher) {
        this.detail_voucher = detail_voucher;
    }

    // Getter and Setter for valid_date
    public long getValid_date() {
        return valid_date;
    }

    public void setValid_date(long valid_date) {
        this.valid_date = valid_date;
    }

    // Getter and Setter for expired_date
    public long getexpired_date() {
        return expired_date;
    }

    public void setExpired_date(long expired_date) {
        this.expired_date = expired_date;
    }

    // Getter and Setter for description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Getter and Setter for use_date
    public long getUse_date() {
        return use_date;
    }

    public void setUse_date(long use_date) {
        this.use_date = use_date;
    }

    long getExpired_date() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
