/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fortunato.restserverfortunato;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 *
 * @author biar
 */

@Path("Bank")
@Produces("text/xml")
public class Bank {
    private Map<Integer, BankAccount> accounts;

    public Bank() {
        this.accounts = new HashMap<Integer, BankAccount>();
    }
    
    private BankAccount findById(int id){
        return accounts.get(id);
    }
    
    @GET
    @Path("")
    public AccountEntries listAccounts(){
        Iterator<Entry<Integer, BankAccount>> itAcc = accounts.entrySet().iterator();
        List<AccountEntry> res = new ArrayList<AccountEntry>();
        Entry<Integer, BankAccount> ae;
        while(itAcc.hasNext()){
            ae = itAcc.next();
            res.add(new AccountEntry(ae.getKey(), ae.getValue().toString()));
        }
        return new AccountEntries(res);
    }
    
    @GET
    @Path("{id}")
    public BankAccount getAccount(@PathParam("id")int id){
        BankAccount ba = findById(id);
        System.out.println(ba.toString());
        return ba;
    }
    
    @POST
    @Path("")
    public Response newAccount(BankAccount acc){
        Iterator<BankAccount> itAcc = accounts.values().iterator();
        while(itAcc.hasNext()){
            if(acc.equals(itAcc.next())){
                return Response.status(Response.Status.CONFLICT).build();
            }
        }
        accounts.put(accounts.size()+1, acc);
        return Response.ok(acc).build();
    }
    
    @DELETE
    @Path("{id}")
    public Response deleteAccount(@PathParam("id")int id){
        if(accounts.get(id) == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        
        return Response.ok().build();
    }
    
    @PUT
    @Path("{id}")
    public Response updateAccount(@PathParam("id")int id, BankAccount ba){
        BankAccount ourBa = accounts.get(id);
        if(ourBa == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        if(ourBa.equals(ba)){
            return Response.notModified().build();
        }
        List<Operation> ops = ourBa.getOps();
        ba.setOps(ops);
        accounts.put(id, ba);
        return Response.ok().build();
    }
    
    @GET
    @Path("{id}/operations")
    public Operations pathToOperations(@PathParam("id")int id){
        return new Operations(findById(id).getOps());
    }
    
    @POST
    @Path("{id}/operations")
    public Response addOperation(@PathParam("id")int id, Operation op){
        BankAccount ba = findById(id);
        if(ba == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        
        System.out.println("operation to add:"+op.toString()+"found account:"+ba.toString());
        if(ba.addOperation(op) == true){
            System.out.println("added");
            return Response.ok().build();
        }
        return Response.notModified().build();

    }
}