/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fortunato.restclientfortunato;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author biar
 */
@XmlRootElement( name = "Operations" )
public class Operations {
    private List<Operation> ops;

    public Operations() {}
    
    public Operations(List<Operation> ops) {
        this.ops = ops;
    }

    public List<Operation> getOps() {
        return ops;
    }

    @XmlElement ( name = "ops" )
    public void setOps(List<Operation> ops) {
        this.ops = ops;
    }
}

