    /*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
     */

    package com.mycompany.sqlitebaru;

    public class Voucher {
        private int id_voucher;
        private int id_user;
        private String title_voucher;
        private String company;
        private String type;
        private String detail_voucher;
        private long valid_date;
        private long expired_date;
        private String description;
        private String nama_depan;

        // Constructor
        public Voucher() {
        }
        
        public Voucher(int id_voucher, int id_user, String title_voucher, String company, String type, 
                   String detail_voucher, long valid_date, long expired_date, String description, String nama_depan) {
        this.id_voucher = id_voucher;
        this.id_user = id_user;
        this.title_voucher = title_voucher;
        this.company = company;
        this.type = type;
        this.detail_voucher = detail_voucher;
        this.valid_date = valid_date;
        this.expired_date = expired_date;
        this.description = description;
        this.nama_depan = nama_depan;
    }

        public Voucher(int id_voucher, int id_user, String title_voucher, String company, String type, String detail_voucher, long valid_date, long expired_date, String description) {
            this.id_voucher = id_voucher;
            this.id_user = id_user;
            this.title_voucher = title_voucher;
            this.company = company;
            this.type = type;
            this.detail_voucher = detail_voucher;
            this.valid_date = valid_date;
            this.expired_date = expired_date;
            this.description = description;
        }

        // Getter and Setter methods
        public int getId_voucher() {
            return id_voucher;
        }

        public void setId_voucher(int id_voucher) {
            this.id_voucher = id_voucher;
        }

        public int getId_user() {
            return id_user;
        }

        public void setId_user(int id_user) {
            this.id_user = id_user;
        }

        public String getTitle_voucher() {
            return title_voucher;
        }

        public void setTitle_voucher(String title_voucher) {
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

        public String getDetail_voucher() {
            return detail_voucher;
        }

        public void setDetail_voucher(String detail_voucher) {
            this.detail_voucher = detail_voucher;
        }

        public long getValid_date() {
            return valid_date;
        }

        public void setValid_date(long valid_date) {
            this.valid_date = valid_date;
        }

        public long getExpired_date() {
            return expired_date;
        }

        public void setExpired_date(long expired_date) {
            this.expired_date = expired_date;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
        
        public String getNama_depan() { return nama_depan; }
        
        public void setgetNama_depan(String nama_depan) {
            this.nama_depan = nama_depan;
        }
        @Override
        public String toString() {
            return "Voucher{" +
                    "id_voucher=" + id_voucher +
                    ", id_user=" + id_user +
                    ", title_voucher='" + title_voucher + '\'' +
                    ", company='" + company + '\'' +
                    ", type='" + type + '\'' +
                    ", detail_voucher='" + detail_voucher + '\'' +
                    ", valid_date=" + valid_date +
                    ", expired_date=" + expired_date +
                    ", description='" + description + '\'' +
                    '}';
        }
    }
