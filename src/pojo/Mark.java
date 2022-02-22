package pojo;

import java.util.Date;

public class Mark {
    private int value;
    private Date date;
    private String notice;

    public Mark() {

    }

    public Mark(int value, Date date, String notice) {
        this.value = value;
        this.date = date;
        this.notice = notice;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}
