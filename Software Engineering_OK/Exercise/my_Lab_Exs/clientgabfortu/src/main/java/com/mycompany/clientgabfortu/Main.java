/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clientgabfortu;

import javax.ws.rs.core.Response;
import org.apache.cxf.jaxrs.client.WebClient;

/**
 *
 * @author biar
 */
public class Main {

    public static void main(String[] args) {

        WebClient client = WebClient.create("http://localhost:8080/");
        client.path("myResources/courses/1");
        client.type("text/xml").accept("text/xml");
        Response r = client.get();
        Corso c = r.readEntity(Corso.class);
        System.out.println(c.toString());

        Studente stud = new Studente();
        stud.setId(3);
        stud.setName("Adolfo");
        client.replacePath("1/students");
        Response r1 = client.post(stud);
        System.out.println(r1.getStatus());
    }
}
