/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aaaws;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.jws.WebService;

@WebService(endpointInterface = "aaaws.AAAWS")
public class AAAWSImplementation implements AAAWS{
    private Map<Integer, Client> clients = new HashMap<Integer, Client>();

    public AAAWSImplementation() {
        clients.put(1, new Client("Massimo", "Mecella"));
        clients.put(2, new Client("Maurizio", "Lenzerini"));
        clients.put(3, new Client("Bruno", "Ciciani"));
        clients.put(4, new Client("Fabrizio", "D'Amore"));
        clients.put(5, new Client("Alessandro", "Pellegrini"));
    }
    
    @Override
    public List<String> getClients() {
        List<String> cls = new LinkedList<String>();
        
        for (Map.Entry<Integer,Client> cl_entry : clients.entrySet()){
            Client cl = cl_entry.getValue();
            int id = cl_entry.getKey();
            String client = id + "," + cl.getName() + " " + cl.getSurname();
            cls.add(client);
        }
        return cls;
    }
}
