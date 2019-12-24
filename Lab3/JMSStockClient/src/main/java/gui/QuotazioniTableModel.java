/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dto.Quotazione;
import java.text.DateFormat;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author biar
 */
public class QuotazioniTableModel extends AbstractTableModel implements Observer{
    private Vector<Quotazione> lista = new Vector<Quotazione>();
    private String[] colonne = { "Nome Azione", "Quotazione", "Data aggiornamento" };

    // I quattro metodi che seguono non devono essere utilizzati dallo studente
    public synchronized int getRowCount() {
        return (lista.size());
    }

    public synchronized int getColumnCount() {
        return (3);
    }

    public synchronized Object getValueAt(int rowIndex, int columnIndex) {
        Quotazione elem = (Quotazione) lista.get(rowIndex);
        switch (columnIndex) {
        case 0:
            return (elem.nome);
        case 1:
            return (new Float(elem.valore));
        case 2:
            DateFormat df = DateFormat.getTimeInstance();
            return (df.format(elem.data));
        }
        return ("");
    }

    public synchronized String getColumnName(int n) {
        return (colonne[n]);
    }

    public synchronized void aggiornaQuotazione(String nome, float quotazione) {
        Quotazione nv = new Quotazione();
        nv.nome = nome;
        int i = lista.indexOf(nv);
        if (i < 0) {
            nv.valore = quotazione;
            lista.add(nv);
        } else {
            nv = (Quotazione) lista.get(i);
            nv.valore = quotazione;
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        Quotazione quotazione = (Quotazione) arg;
        this.aggiornaQuotazione(quotazione.nome, quotazione.valore);
    }
}
