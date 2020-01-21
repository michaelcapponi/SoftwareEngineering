/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.lab_ex2;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author biar
 */

// Questa annotazione consente al compilatore di capire che questa è la root
// class per gli oggetti di tipo Student per i documenti XML che andremo
// a generare
@XmlRootElement(name = "Student")

public class Student {
    // Attributi della classe
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
