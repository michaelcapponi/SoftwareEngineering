package bankclient;

import wdslbankaaaws.BankAAAWS;
import wdslbankaaaws.BankAAAWSImplService;
import wdslbankaaaws.Client;
import wdslbankinterface.BankIFace;
import wdslbankinterface.BankImplService;
import wdslbankinterface.Operation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BankClient {
    public static void main(String[] args) throws Exception {
        String Description = "benzina autostrada";
        HashMap<Integer, String> clients_names = new HashMap<Integer, String>();
        List<Client> clients = getClients();
        Iterator<Client> client_it = clients.listIterator();
        while (client_it.hasNext()) {
            Client client = client_it.next();
            List<Integer> ops = getOperationsByClientID(client.getId());
            Iterator<Integer> op_it = ops.iterator();
            while (op_it.hasNext()) {
                int op_id = op_it.next();
                Operation op = getOperationsDetailsById(op_id);
                if (op.getDescription().equals(Description)) {
                    clients_names.put(client.getId(), client.getName() + " " + client.getSurname());
                }
            }
        }
        System.out.println("clients that have an operation called: " + Description + ": ");
        Iterator<Map.Entry<Integer, String>> it = clients_names.entrySet().iterator();
        while (it.hasNext()) {
            System.out.println(it.next().getValue());
        }
    }

    private static List<Client> getClients() {
        BankAAAWSImplService service = new BankAAAWSImplService();
        BankAAAWS port = service.getBankAAAWSImplPort();
        return port.getClients();
    }

    private static Operation getOperationsDetailsById(int OPID) {
        BankImplService service = new BankImplService();
        BankIFace port = service.getBankImplPort();
        return port.getOperationDetailsByID(OPID);
    }

    private static List<Integer> getOperationsByClientID(int ID) {
        BankImplService service = new BankImplService();
        BankIFace port = service.getBankImplPort();
        return port.getOperationsByClientID(ID);
    }
}
