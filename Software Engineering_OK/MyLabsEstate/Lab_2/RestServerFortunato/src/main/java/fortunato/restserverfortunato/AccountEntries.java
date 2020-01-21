/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fortunato.restserverfortunato;

import java.util.List;
import java.util.Objects;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author biar
 */
@XmlRootElement( name = "AccountEntries" )
public class AccountEntries {
    private List<AccountEntry> entries;

    public AccountEntries() {}
    
    public AccountEntries(List<AccountEntry> accEnt) {
        this.entries = accEnt;
    }

    public List<AccountEntry> getAccEnt() {
        return entries;
    }

    @XmlElement( name = "entries" )
    public void setAccEnt(List<AccountEntry> accEnt) {
        this.entries = accEnt;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.entries);
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
        final AccountEntries other = (AccountEntries) obj;
        if (!Objects.equals(this.entries, other.entries)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AccountEntries{" + "accEnt=" + entries + '}';
    }
}
