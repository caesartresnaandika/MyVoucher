/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sqlitebaru;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *
 * @author nicho
 */
public class SessionManager implements Serializable{
    private static final long serialVersionUID = 1L;
    private static final String SESSION_FILE = "session.ser";

    private static volatile SessionManager instance;
    private boolean isLoggedIn;

    // Private constructor to prevent instantiation from outside
    private SessionManager() {
        isLoggedIn = false;
    }

    // Static method to get the singleton instance
    public static SessionManager getInstance() {
        if (instance == null) {
            synchronized (SessionManager.class) {
                if (instance == null) {
                    instance = new SessionManager();
                    instance.createSessionFile();
                }
            }
        }
        return instance;
    }

    // Method to check if the session file doesn't exist
    public void createSessionFile() {
        File file = new File(SESSION_FILE);
        if (!file.exists()) {
            saveSession();
        } else {
            loadSession();
        }
    }

    private void loadSession() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SESSION_FILE))) {
            SessionManager sessionManager = (SessionManager) ois.readObject();
            this.isLoggedIn = sessionManager.isLoggedIn;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading session: " + e.getMessage());
        }
    }

    private void saveSession() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SESSION_FILE))) {
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to check if user is logged in
    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    // Method to simulate login
    public void login() {
        isLoggedIn = true;
        saveSession();
    }

    // Method to simulate logout
    public void logout() {
        isLoggedIn = false;
        saveSession();
    }
    
}
