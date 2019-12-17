package com.example.switchingshifts;

public class Worker {
    /* Private data members */
    private String first_name;
    private String last_name;
    private String role;
    private String email;
    private long worker_number;
    private String birthday;
    private static long id_counter = 1;
    private String id;
    private boolean first_login;


    /* default constructor */
    public Worker(){
        this.first_name = "";
        this.last_name = "";
        this.role = "";
        this.email = "";
        this.worker_number = id_counter++;
        this.birthday = "";
        this.first_login = true;
    }

    public Worker(String first_name, String last_name, String role, String email){
        this.first_name = first_name;
        this.last_name = last_name;
        this.role = role;
        this.email = email;
        this.worker_number = id_counter++;
        this.birthday = "";
        this.first_login = true;
    }

    /* Getters & Setters */
    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public long getWorker_number() {
        return worker_number;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getId() { return id; }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setId(String id) { this.id = id; }

    public boolean isFirst_login() { return first_login; }

    public void setFirst_login(boolean first_login) { this.first_login = first_login; }
}
