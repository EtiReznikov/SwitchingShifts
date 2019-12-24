package backend;

public class Shift {
    /* private data members */
    private String date;
    private String role;

    /*constructors */
    public Shift() {
        this.date="";
        this.role="";
    }

    public Shift(Shift s) {
        this.date=s.date;
        this.role=s.role;
    }
    public Shift(String date, String role) {
        this.date=date;
        this.role=role;

    }

    /* getters and setters*/
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }



}
