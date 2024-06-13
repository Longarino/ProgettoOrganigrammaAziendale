import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public class Menu extends JFrame
{
    private String filePresente="File selezionato.";
    private String fileNonPresente="Crea un nuovo file o seleziona uno già esistente.";
    private JLabel label;


    private String titolo="Menu Organigramma Aziendale";


    public Menu()
    {
        setTitle(titolo);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                if(consensoUscita() )
                {
                    System.exit(0);
                }

            }
        });
        AscoltatoreEventiAzione listener= new AscoltatoreEventiAzione(this);

        JMenuBar menuBar= new JMenuBar();
        this.setJMenuBar(menuBar);

        //comandi su File
        JMenu fileMenu= new JMenu("File");
        menuBar.add(fileMenu);

        JMenuItem nuovo = new JMenuItem("Nuovo");
        nuovo.addActionListener(listener);
        fileMenu.add(nuovo);
        fileMenu.addSeparator();

        JMenuItem apri=new JMenuItem("Apri");
        apri.addActionListener(listener);
        fileMenu.add(apri);

        JMenuItem salva=new JMenuItem("Salva");
        salva.addActionListener(listener);
        fileMenu.add(salva);

        fileMenu.addSeparator();

        JMenuItem esci=new JMenuItem("Esci");
        esci.addActionListener(listener);
        fileMenu.add(esci);
        //comandi su File

        //comandi su Comandi

        JMenu comandiMenu= new JMenu("Comandi");
        menuBar.add(comandiMenu);

        //comandi sulle unita
        JMenu aggiungiNuovaUnita=new JMenu("Aggiungi nuova Unità");
        comandiMenu.add(aggiungiNuovaUnita);

        JMenuItem aggiungiUnitaPadre=new JMenuItem("Unità padre (di tutte)");
        aggiungiUnitaPadre.addActionListener(listener);
        aggiungiNuovaUnita.add(aggiungiUnitaPadre);

        JMenuItem aggiungiSottoUnita=new JMenuItem("Sottounità");
        aggiungiSottoUnita.addActionListener(listener);
        aggiungiNuovaUnita.add(aggiungiSottoUnita);

        JMenuItem modificaNomeUnita=new JMenuItem("Modifica nome unità");
        modificaNomeUnita.addActionListener(listener);
        comandiMenu.add(modificaNomeUnita);

        JMenuItem rimuoviUnita = new JMenuItem("Rimuovi unità");
        rimuoviUnita.addActionListener(listener);
        comandiMenu.add(rimuoviUnita);

        JMenuItem mostraUnita = new JMenuItem("Mostra unità");
        mostraUnita.addActionListener(listener);
        comandiMenu.add(mostraUnita);

        JMenuItem mostraGerarchiaUnita = new JMenuItem("Mostra gerarchia unità");
        mostraGerarchiaUnita.addActionListener(listener);
        comandiMenu.add(mostraGerarchiaUnita);

        JMenuItem rimuoviTutteLeUnita = new JMenuItem("Rimuovi tutte Le unità");
        rimuoviTutteLeUnita.addActionListener(listener);
        comandiMenu.add(rimuoviTutteLeUnita);


        comandiMenu.addSeparator();

        //comandi sui ruoli
        JMenuItem aggiungiNuovoRuolo = new JMenuItem("Aggiungi nuovo ruolo");
        aggiungiNuovoRuolo.addActionListener(listener);
        comandiMenu.add(aggiungiNuovoRuolo);

        JMenuItem rimuoviRuolo = new JMenuItem("Rimuovi ruolo");
        rimuoviRuolo.addActionListener(listener);
        comandiMenu.add(rimuoviRuolo);

        JMenuItem mostraRuolo= new JMenuItem("Mostra ruolo");
        mostraRuolo.addActionListener(listener);
        comandiMenu.add(mostraRuolo);

        JMenuItem modificaNomeRuolo = new JMenuItem("Modifica nome ruolo");
        modificaNomeRuolo.addActionListener(listener);
        comandiMenu.add(modificaNomeRuolo);

        JMenuItem assegnaRuolo = new JMenuItem("Assegna ruolo");
        assegnaRuolo.addActionListener(listener);
        comandiMenu.add(assegnaRuolo);

        JMenuItem deassegnaRuolo = new JMenuItem("Deassegna ruolo");
        deassegnaRuolo.addActionListener(listener);
        comandiMenu.add(deassegnaRuolo);

        JMenuItem mostraRuoliAncoraDisponibili = new JMenuItem("Mostra ruoli ancora disponibili");
        mostraRuoliAncoraDisponibili.addActionListener(listener);
        comandiMenu.add(mostraRuoliAncoraDisponibili);

        JMenuItem mostraTuttiIRuoli= new JMenuItem("Mostra tutti i ruoli");
        mostraTuttiIRuoli.addActionListener(listener);
        comandiMenu.add(mostraTuttiIRuoli);

        JMenuItem resettaAssegnamentoRuoliDipendente=new JMenuItem("Resetta ruoli dipendente");
        resettaAssegnamentoRuoliDipendente.addActionListener(listener);
        comandiMenu.add(resettaAssegnamentoRuoliDipendente);

        JMenuItem resettaAssegnamentoRuoli = new JMenuItem("Resetta assegnamento ruoli");
        resettaAssegnamentoRuoli.addActionListener(listener);
        comandiMenu.add(resettaAssegnamentoRuoli);

        JMenuItem eliminaTuttiIRuoli = new JMenuItem("Elimina tutti i ruoli");
        eliminaTuttiIRuoli.addActionListener(listener);
        comandiMenu.add(eliminaTuttiIRuoli);
        //comandi sui ruoli

        comandiMenu.addSeparator();

        //comandi sui dipendenti
        JMenuItem aggiungiNuovoDipendente = new JMenuItem("Aggiungi nuovo dipendente");
        aggiungiNuovoDipendente.addActionListener(listener);
        comandiMenu.add(aggiungiNuovoDipendente);

        JMenuItem rimuoviDipendente = new JMenuItem("Rimuovi dipendente");
        rimuoviDipendente.addActionListener(listener);
        comandiMenu.add(rimuoviDipendente);

        //comandi sulle modifiche dei dipendenti
        JMenu modificaDipendente = new JMenu("Modifica dipendente");
        comandiMenu.add(modificaDipendente);

        JMenuItem modificaCFdipendente= new JMenuItem("Modifica codice fiscale dipendente");
        modificaCFdipendente.addActionListener(listener);
        modificaDipendente.add(modificaCFdipendente);

        JMenuItem modificaCognomeDipendente= new JMenuItem("Modifica cognome dipendente");
        modificaCognomeDipendente.addActionListener(listener);
        modificaDipendente.add(modificaCognomeDipendente);

        JMenuItem modificaNomeDipendente= new JMenuItem("Modifica nome dipendente");
        modificaNomeDipendente.addActionListener(listener);
        modificaDipendente.add(modificaNomeDipendente);

        JMenuItem modificaSessoDipendente= new JMenuItem("Modifica sesso dipendente");
        modificaSessoDipendente.addActionListener(listener);
        modificaDipendente.add(modificaSessoDipendente);

        JMenuItem modificaDataNascitaDipendente= new JMenuItem("Modifica data nascita dipendente");
        modificaDataNascitaDipendente.addActionListener(listener);
        modificaDipendente.add(modificaDataNascitaDipendente);

        JMenuItem modificaLuogoNascitaDipendente= new JMenuItem("Modifica luogo nascita dipendente");
        modificaLuogoNascitaDipendente.addActionListener(listener);
        modificaDipendente.add(modificaLuogoNascitaDipendente);


        JMenu mostraDipendente = new JMenu("Mostra dipendente");
        comandiMenu.add(mostraDipendente);

        JMenuItem mostraDipendentePerCF= new JMenuItem("Per codice fiscale");
        mostraDipendentePerCF.addActionListener(listener);
        mostraDipendente.add(mostraDipendentePerCF);

        JMenuItem mostraDipendentePerCognome= new JMenuItem("Per cognome");
        mostraDipendentePerCognome.addActionListener(listener);
        mostraDipendente.add(mostraDipendentePerCognome);

        JMenuItem mostraDipendentePerNome= new JMenuItem("Per nome");
        mostraDipendentePerNome.addActionListener(listener);
        mostraDipendente.add(mostraDipendentePerNome);

        JMenuItem mostraDipendentePerSesso= new JMenuItem("Per sesso");
        mostraDipendentePerSesso.addActionListener(listener);
        mostraDipendente.add(mostraDipendentePerSesso);

        JMenuItem mostraDipendentePerDataDiNascita= new JMenuItem("Per data di nascita");
        mostraDipendentePerDataDiNascita.addActionListener(listener);
        mostraDipendente.add(mostraDipendentePerDataDiNascita);

        JMenuItem  mostraDipendentePerLuogoDiNascita=new JMenuItem("Per luogo di nascita");
        mostraDipendentePerLuogoDiNascita.addActionListener(listener);
        mostraDipendente.add(mostraDipendentePerLuogoDiNascita);

        JMenuItem mostraRuoliDipendente=new JMenuItem("Mostra ruoli dipendente");
        mostraRuoliDipendente.addActionListener(listener);
        comandiMenu.add(mostraRuoliDipendente);

        JMenuItem mostraTuttiIDipendenti= new JMenuItem("Mostra tutti i dipendenti");
        mostraTuttiIDipendenti.addActionListener(listener);
        comandiMenu.add(mostraTuttiIDipendenti);


        JMenuItem resettaDipendenti = new JMenuItem( "Elimina tutti i dipendenti");
        resettaDipendenti.addActionListener(listener);
        comandiMenu.add(resettaDipendenti);



        this.label=new JLabel();
        this.label.setText(fileNonPresente);
        this.add(label);
        setLocation(200,200);
        setSize(500,400);
        setVisible(true);
    }
    private boolean consensoUscita() {
        int option = JOptionPane.showConfirmDialog(
                null,
                "Sei sicuro di voler uscire?",
                "Conferma Uscita",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
        return option == JOptionPane.YES_OPTION;
    }

    protected void setLabel()
    {
        this.label.setText(this.filePresente);
        this.label.updateUI();
    }


}
