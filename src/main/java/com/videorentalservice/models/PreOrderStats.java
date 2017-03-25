package com.videorentalservice.models;

import com.videorentalservice.models.abstracts.AbstractModelClass;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Rave on 25.02.2017.
 */

public class PreOrderStats extends AbstractModelClass {

    private BigDecimal total_days;

    private BigDecimal total_discs;

    private BigDecimal price;

    private String fromDate;

    public BigDecimal getTotal_days() {
        return total_days;
    }

    public void setTotal_days(BigDecimal total_days) {
        this.total_days = total_days;
    }

    public BigDecimal getTotal_discs() {
        return total_discs;
    }

    public void setTotal_discs(BigDecimal total_discs) {
        this.total_discs = total_discs;
    }

    private String toDate;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public PreOrderStats() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date tmp = new Date();
        Date tmp1 = new Date(tmp.getTime() + (1000 * 60 * 60 * 24));
        fromDate = dateFormat.format(tmp);
        toDate = dateFormat.format(tmp1);
        price = new BigDecimal(0);
        total_discs = new BigDecimal(0);
        long diff = tmp1.getTime() - tmp.getTime();
        total_days = new BigDecimal(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
    }

    public Boolean removeDay() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date tmp = null;
        Date before = null;
        try {
            tmp = dateFormat.parse(toDate);
            before = dateFormat.parse(fromDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date newDate = new Date(tmp.getTime() - (1000 * 60 * 60 * 24));

        if (newDate.after(before)) {
            toDate = dateFormat.format(newDate);
            long diff = newDate.getTime() - before.getTime();
            total_days = new BigDecimal(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
            return true;
        } else {
            return false;
        }
    }

    public void addDay() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date tmp = null;
        Date before = null;
        try {
            tmp = dateFormat.parse(toDate);
            before = dateFormat.parse(fromDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date newDate = new Date(tmp.getTime() + (1000 * 60 * 60 * 24));
        toDate = dateFormat.format(newDate);
        long diff = newDate.getTime() - before.getTime();
        total_days = new BigDecimal(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
    }
}
