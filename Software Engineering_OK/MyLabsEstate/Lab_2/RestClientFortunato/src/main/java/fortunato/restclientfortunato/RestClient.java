/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fortunato.restclientfortunato;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class RestClient {
    public static void main(String[] args) throws Exception {
        // Create objects to start comunication with RESTServer
        Client client = ClientBuilder.newClient();
        String baseUrl = "http://localhost:8080/Bank";
        WebTarget webTarget = client.target(baseUrl);
        Invocation.Builder ib = webTarget.request("text/xml");
        Response res;
        
        // Create BankAccount and send to the server with post method
        // then print response
        BankAccount acc = new BankAccount("Massimo", "Mecella");
        res=ib.post(Entity.entity(acc, MediaType.TEXT_XML));
        System.out.println(res.getStatus() +" - " + res.getStatusInfo().getReasonPhrase());
        
        // Obtain account entries from RESTServer
        List<Integer> list = new ArrayList<Integer>();
        AccountEntries accEnt = ib.accept(MediaType.TEXT_XML).get(AccountEntries.class);
        for(AccountEntry ent : accEnt.getAccEnt()){
            System.out.println(ent.getId());
            list.add(ent.getId());
        }
        
        // Add an operation on the RESTServer
        webTarget= client.target(baseUrl+"/"+list.get(0)+"/operations");
        ib=webTarget.request("text/xml");
        res=ib.post(Entity.entity(new Operation(1,100.0,"2018-03-02","benzina autostrada"),MediaType.TEXT_XML));
        System.out.println(res.getStatus() +" - " + res.getStatusInfo().getReasonPhrase());
        
        // Getting all operations of specific account from RESTServer
        webTarget= client.target(baseUrl+"/"+list.get(0)+"/operations");
        ib=webTarget.request("text/xml");
        Operations ops=ib.accept(MediaType.TEXT_XML).get(Operations.class);
        for(Operation op : ops.getOps()){
            System.out.println(op.toString());
        }
        
        // Updating infos of a specific account on RESTServer
        webTarget = client.target(baseUrl+"/"+list.get(0));
        ib = webTarget.request("text/xml");
        res=ib.put(Entity.entity(new BankAccount("Massimo","Cerruti"), MediaType.TEXT_XML));
        System.out.println(res.getStatus() +" - " + res.getStatusInfo().getReasonPhrase());
        
        // Getting al the accounts and then visualizing the accounts
        BankAccount acc_res;
        for(Integer i : list){
        webTarget= client.target(baseUrl+"/"+i);
        ib=webTarget.request("text/xml");
        acc_res=ib.accept(MediaType.TEXT_XML).get(BankAccount.class);
        System.out.println(((BankAccount)acc_res).toString());
        for(Operation op: acc_res.getOps()){
            System.out.println(op.toString());
            }
        }
        
        // Deleting an account on RESTServer
        webTarget = client.target(baseUrl+"/"+list.get(0));
        ib = webTarget.request("text/xml");
        res=ib.delete();
        System.out.println(res.getStatus() +" - " + res.getStatusInfo().getReasonPhrase());
        
    }
}
