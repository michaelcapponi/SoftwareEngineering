/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fortunato.restclientfortunato;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author biar
 */
@XmlRootElement( name = "BankAccount" )
public class BankAccount {
    private Client holder;
    private List<Operation> ops;
    
    public BankAccount(String name, String surname) {
        this.holder = new Client(name, surname);
        this.ops = new ArrayList<Operation>();
    }

    public BankAccount() {
        this.ops = new ArrayList<Operation>();
    }

    public Client getHolder() {
        return holder;
    }

    @XmlElement(name="Holder")
    public void setHolder(Client holder) {
        this.holder = holder;
    }

    public List<Operation> getOps() {
        return ops;
    }

    @XmlElement( name = "Operations")
    public void setOps(List<Operation> ops) {
        this.ops = ops;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.holder);
        hash = 83 * hash + Objects.hashCode(this.ops);
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
        final BankAccount other = (BankAccount) obj;
        if (!Objects.equals(this.holder, other.holder)) {
            return false;
        }
        if (!Objects.equals(this.ops, other.ops)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BankAccount{" + "holder=" + holder + ", ops=" + ops + '}';
    }

    public boolean addOperation(Operation op) {
        return this.ops.add(op);
    }
}