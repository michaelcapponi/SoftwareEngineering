package bankaaaws;

import javax.xml.ws.Endpoint;
public class BankAAAWSMain {
    public static void main(String[] args) throws InterruptedException{
        BankAAAWS server= new BankAAAWSImpl();
        String address="http://localhost:8081/BankAAAWS";
        Endpoint.publish(address,server);
        Thread.sleep(60*1000);
        System.exit(0);
    }
}
