/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aaaws;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.jws.WebService;

/**
 * Implementation of endpoint referred to AAAWS
 */
@WebService(endpointInterface = "aaaws.AAAWS")
public class AAAWSImpl implements AAAWS {
    private Map<Integer, Client> clients = new HashMap<Integer, Client>();

    public AAAWSImpl() {
        clients.put(1, new Client("Massimo", "Mecella"));
        clients.put(2, new Client("Maurizio", "Lenzerini"));
        clients.put(3, new Client("Bruno", "Ciciani"));
        clients.put(4, new Client("Fabrizio", "D'Amore"));
        clients.put(5, new Client("Alessandro", "Pellegrini"));
    }
    
    @Override
    public String[] getClients(){
        int numClients = clients.keySet().size();
        String[] list = new String[numClients];
        Iterator<Integer> itKeyClients = clients.keySet().iterator();
        int key = -1;
        for(int i=0; i < numClients && itKeyClients.hasNext(); i++){
            key = itKeyClients.next();
            Client clt = clients.get(key);
            list[i] = key + "," + clt.getName() + " " + clt.getSurname();
        }
        return list;
    }
}
