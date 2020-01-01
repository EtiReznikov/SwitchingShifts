package backend;

public class Shift {
    /* private data members */
    private String date;
    private String type;

    /*constructors */
    public Shift() {
        this.date = "";
        this.type = "";
    }

    public Shift(Shift s) {
        this.date = s.date;
        this.type = s.type;
    }
    public Shift(String date, String type) {
        this.date = date;
        this.type = type;

    }

    /* getters and setters*/
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRole() {
        return type;
    }

    public void setRole(String type) {
        this.type = type;
    }



}
