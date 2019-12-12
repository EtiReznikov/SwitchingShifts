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


        /* default constructor */
    public Worker(){}

    public Worker(String first_name, String last_name, String role, String email){
            this.first_name = first_name;
            this.last_name = last_name;
            this.role = role;
            this.email = email;
            this.worker_number = id_counter++;
            this.birthday = "";
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
}
