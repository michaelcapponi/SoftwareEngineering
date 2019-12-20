/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.restclient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author biar
 */

@XmlType( propOrder = {"id", "amount", "date", "description"} )
@XmlRootElement( name = "Operation" )
public class Operation {
    private int id;
    private double amount;
    private Date date;
    private String description;

    public Operation() {}

    public Operation(int id, double amount, String date, String description) throws ParseException {
        this.id = id;
        this.amount = amount;
        this.date = new SimpleDateFormat("yyyy-mm-dd").parse(date);
        this.description = description;
    }

    public int getId() {
        return id;
    }

    @XmlElement( name = "id" )
    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    @XmlElement( name = "amount" )
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    @XmlElement( name = "date" )
    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    @XmlElement( name = "description" )
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + this.id;
        hash = 17 * hash + (int) (Double.doubleToLongBits(this.amount) ^ (Double.doubleToLongBits(this.amount) >>> 32));
        hash = 17 * hash + Objects.hashCode(this.date);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Operation other = (Operation) obj;
        if (this.id != other.id) {
            return false;
        }
        if (Double.doubleToLongBits(this.amount) != Double.doubleToLongBits(other.amount)) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Operation{" + "id=" + id + ", amount=" + amount + ", date=" + date + ", description=" + description + '}';
    }
}
