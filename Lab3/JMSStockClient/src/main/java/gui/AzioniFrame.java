/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

/**
 *
 * @author biar
 */
public class AzioniFrame extends JFrame implements Observer{
    private QuotazioniTableModel quotazioniTabModel = new QuotazioniTableModel();
    private JTable tabella = new JTable((TableModel) quotazioniTabModel);

    // pulsanti
    private JButton compraBtn = new JButton("Compra");
    private JButton autenticaBtn = new JButton("Autenticati");

    // pannelli interni
    private JScrollPane jSPane = new JScrollPane(tabella);
    private JPanel centroPnl = new JPanel();
    private JPanel sudPnl = new JPanel();

    private AzioniListener ascoltatore = new AzioniListener(this);
    
    public AzioniFrame(){
        super("Sistema azionario");

        /*
         * Per rendere il bottone "Compra" non cliccabile. Lo deve diventare
         * solo in seguito all'attivazione del bottone "Autenticati"
         */
        this.abilitaPulsanteAcquisto(false);

        // Imposta gli ActionListener sui pulsanti
        this.compraBtn.addActionListener((ActionListener) ascoltatore);
        this.autenticaBtn.setActionCommand(AzioniListener.ACCEDI_CMD);
        this.autenticaBtn.addActionListener((ActionListener) ascoltatore);
        this.compraBtn.setActionCommand(AzioniListener.COMPRA_CMD);

        // Dispone i widget all'interno dei pannelli
        this.centroPnl.add(jSPane);
        this.sudPnl.add(compraBtn);
        this.sudPnl.add(autenticaBtn);

        // Dispone i pannelli all'interno del frame
        this.getContentPane().add(centroPnl, BorderLayout.CENTER);
        this.getContentPane().add(sudPnl, BorderLayout.SOUTH);

        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    
    public void abilitaPulsanteAcquisto(boolean abilita) {
        this.compraBtn.setEnabled(abilita);
    }

    public String getNomeAzioneSelezionata() {
        int riga = this.tabella.getSelectedRow();
        String nomeAzioneSelezionata = null;
        if (riga >= 0) {
            nomeAzioneSelezionata =
                (String) this.tabella.getModel().getValueAt(riga, 0);
        }
        return nomeAzioneSelezionata;
    }

    @Override
    public void update(Observable o, Object arg) {
        this.tabella.updateUI();
    }

    public QuotazioniTableModel getQuotazioniTableModel() {
        return this.quotazioniTabModel;
    }
    
}
