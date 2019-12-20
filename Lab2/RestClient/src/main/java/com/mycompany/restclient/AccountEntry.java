/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.restclient;

import java.util.Objects;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author biar
 */
//@XmlType( propOrder = "id, holder" )
@XmlRootElement( name = "AccountEntry" )
public class AccountEntry {
    private int id;
    private String holder;

    public AccountEntry() {}
    
    public AccountEntry(int id, String holder) {
        this.id = id;
        this.holder = holder;
    }

    public int getId() {
        return id;
    }

    @XmlElement( name = "id" )
    public void setId(int id) {
        this.id = id;
    }

    public String getHolder() {
        return holder;
    }

    @XmlElement( name = "holder" )
    public void setHolder(String holder) {
        this.holder = holder;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.id;
        hash = 97 * hash + Objects.hashCode(this.holder);
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
        final AccountEntry other = (AccountEntry) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.holder, other.holder)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AccountEntry{" + "id=" + id + ", holder=" + holder + '}';
    }
}
