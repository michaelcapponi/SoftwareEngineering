/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.util.Date;

/**
 *
 * @author biar
 */
public class Quotazione {
    public String nome;
    public float valore;
    public Date data = new Date();

    public Quotazione() {}

    public Quotazione(String nome, float quotazione) {
        this.nome = nome;
        this.valore = quotazione;
    }

    public int compareTo(Quotazione o) {
        return (((Quotazione) o).nome.compareTo(nome));
    }

    public boolean equals(Object o) {
        if (!(o instanceof Quotazione))
            return (false);
        else
            return (((Quotazione) o).nome.equals(nome));
    }
}
