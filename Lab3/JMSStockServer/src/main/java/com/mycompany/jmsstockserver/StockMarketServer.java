/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jmsstockserver;

/**
 *
 * @author biar
 */
public class StockMarketServer {
    public static void main(String[] args) {
        NotificatoreAcquisto n = new NotificatoreAcquisto();
        n.start();
        
        ProduttoreQuotazioni q = new ProduttoreQuotazioni();
        q.start();
    }
}
