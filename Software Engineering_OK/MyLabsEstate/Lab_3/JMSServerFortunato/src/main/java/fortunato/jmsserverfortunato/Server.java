/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fortunato.jmsserverfortunato;

/**
 *
 * @author biar
 */
public class Server {
    public static void main(String[] args){
        OrderProcessor op=new OrderProcessor();
        QuotationPublisher qp=new QuotationPublisher();
        qp.start();
    }
}
