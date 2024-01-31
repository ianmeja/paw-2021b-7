package ar.edu.itba.paw.webapp.form;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

public class BookingForm {

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern="yyyy-MM-dd")
    private Date date;

    private String time;

    private String diners;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDiners() {
        return diners;
    }

    public void setDiners(String diners) {
        this.diners = diners;
    }
}
