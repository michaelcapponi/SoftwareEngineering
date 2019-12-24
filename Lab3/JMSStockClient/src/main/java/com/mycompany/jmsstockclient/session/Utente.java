/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jmsstockclient.session;

/**
 *
 * @author biar
 */
public class Utente {
    private static Utente istanza = new Utente();
    private String utente = null;

    private Utente() {}

    public static final Utente getInstance() {
        return istanza;
    }

    public String getUtente() {
        return (utente);
    }

    public void setUtente(String utente) {
        this.utente = utente;
    }
}
