import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class AscoltatoreEventiAzione implements ActionListener {

    private Menu menu;
    private MyOrganigramma organigramma=null;
    public AscoltatoreEventiAzione(Menu menu)
    {
        this.menu=menu;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command)
        {
            case "Nuovo":
               nuovo();
               this.menu.setLabel();
               break;

            case "Apri":
                try
                {
                    apri();
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
                if(organigramma!=null)
                {
                    this.menu.setLabel();
                }
                break;

            case "Salva":
                try
                {
                    salva();
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }

                break;

            case "Esci":
                esci();
                break;

            //aggiungi unità
            case "Unità padre (di tutte)":
                unitaPadre();
                break;

            case "Sottounità":
               sottounita();
                break;

            case "Modifica nome unità":
                modificaNomeUnita();
                break;

            case "Rimuovi unità":
                rimuoviUnita();
                break;

            case "Mostra unità":
               mostraUnita();
                break;

            case "Mostra gerarchia unità":
                mostraGerarchiaUnita();
                break;

            case "Rimuovi tutte Le unità":
                rimuoviTutteLeUnita();
                break;

            case "Aggiungi nuovo ruolo":
                aggiungiRuolo();
                break;

            case "Rimuovi ruolo":
                rimuoviRuolo();
                break;

            case "Mostra ruolo":
                mostraRuolo();
                break;

            case "Modifica nome ruolo":
                modificaNomeRuolo();
                break;

            case "Assegna ruolo":
                assegnaRuolo();
                break;

            case "Deassegna ruolo":
                deassegnaRuolo();
                break;

            case "Mostra ruoli ancora disponibili":
                mostraRuoliAncoraDisponibili();
                break;

            case "Mostra tutti i ruoli":
                mostraTuttiIRuoli();
                break;

            case "Resetta ruoli dipendente":
                resettaRuoliDipendente();
                break;

            case "Resetta assegnamento ruoli":
                resettaAssegnamentoRuoli();

                break;
            case "Elimina tutti i ruoli":
                eliminaTuttiIRuoli();
                break;
            case "Aggiungi nuovo dipendente":
                aggiungiDipendente();
                break;

            case "Rimuovi dipendente":
                rimuoviDipendente();
                break;

            case "Modifica codice fiscale dipendente":
                modificaCFDipendente();
                break;

            case "Modifica cognome dipendente":
               modificaCognomeDipendente();
                break;

            case "Modifica nome dipendente":
                modificaNomeDipendente();
                break;

            case "Modifica sesso dipendente":
                modificaSessoDipendente();
                break;

            case "Modifica data nascita dipendente":
               modificaDataNascitaDipendente();
                break;

            case "Modifica luogo nascita dipendente":
               modificaLuogoNascitaDipendente();
                break;

            case "Per codice fiscale":
                mostraDipendentePerCF();
                break;

            case "Per cognome":
                mostraDipendentePerCognome();
                break;

            case "Per nome":
                mostraDipendentePerNome();
                break;

            case "Per sesso":
                mostraDipendentePerSesso();
                break;

            case "Per data di nascita":
                mostraDipendentePerDataNascita();
                break;

            case "Per luogo di nascita":
                mostraDipendentePerLuogoDiNascita();
                break;

            case "Mostra ruoli dipendente":
                mostraRuoliDipendente();
                break;

            case "Mostra tutti i dipendenti":
                mostraTuttiIDipendenti();
                break;


            case "Elimina tutti i dipendenti":
                eliminaTuttiIDipendenti();
                break;

            default:
                break;
        }
    }


    private void nuovo()
    {
        organigramma=new MyOrganigramma();
        JOptionPane.showMessageDialog(menu, "Nuovo documento creato con successo!", "Nuovo documento creato", JOptionPane.INFORMATION_MESSAGE);

    }


    private void apri() throws IOException {
        JFileChooser fileChooserApri = new JFileChooser();
        int resultApri = fileChooserApri.showOpenDialog(menu);

        if (resultApri == JFileChooser.APPROVE_OPTION) {
            File selectedFileApri = fileChooserApri.getSelectedFile();

            if (selectedFileApri == null || !selectedFileApri.exists() || !selectedFileApri.isFile()) {
                JOptionPane.showMessageDialog(menu, "Il file selezionato non è valido.", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(selectedFileApri));
            try
            {

                this.organigramma = (MyOrganigramma) ois.readObject();
                JOptionPane.showMessageDialog(menu, "File aperto con successo!", "File aperto", JOptionPane.INFORMATION_MESSAGE);

            } catch (IOException | ClassNotFoundException ex)
            {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(menu, "Errore nell'aprire il file: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            }
            ois.close();

        }
    }

    private void salva() throws IOException
    {
        if (organigramma != null) {
            JFileChooser fileChooserSalva = new JFileChooser();

            // Imposta il nome del file predefinito con estensione
            fileChooserSalva.setSelectedFile(new File("Nuovo_organigramma.ser"));
            int resultSalva = fileChooserSalva.showSaveDialog(menu);
            if (resultSalva == JFileChooser.APPROVE_OPTION) {
                File selectedFileSalva = fileChooserSalva.getSelectedFile();

                // Aggiungi estensione se manca
                if (!selectedFileSalva.getName().endsWith(".ser")) {
                    selectedFileSalva = new File(selectedFileSalva.getAbsolutePath() + ".ser");
                }

                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(selectedFileSalva));
                try
                {
                    oos.writeObject(this.organigramma);
                    JOptionPane.showMessageDialog(menu, "File salvato con successo!", "Salvataggio Completato", JOptionPane.INFORMATION_MESSAGE);
                }
                catch (IOException ex)
                {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(menu, "Errore durante il salvataggio del file: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
                oos.close();
            }
        }
        else
        {

            JOptionPane.showMessageDialog(menu, "Non hai creato/selezionato nessun file", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void esci()
    {
        if (consensoUscita())
        {
            System.exit(0);
        }
    }


    private void unitaPadre()
    {


        String nomeUnitaPadre = JOptionPane.showInputDialog(menu, "Inserisci il nome dell'unità padre (ricorda che così facendo eliminerai le sottounità presenti):", "Unità Padre", JOptionPane.PLAIN_MESSAGE);


        if (stringaVuota(nomeUnitaPadre))
        {
            JOptionPane.showMessageDialog(menu, "Il nome dell'unità padre non può essere vuoto.", "Errore",JOptionPane.ERROR_MESSAGE);
            return;
        }

        Set<String> elencoUnita=organigramma.getElencoUnita().keySet();


        if(elencoUnita.contains(nomeUnitaPadre))
        {
            JOptionPane.showMessageDialog(menu, "Il nome dell'unità padre è già esistente", "Errore", JOptionPane.ERROR_MESSAGE);
            return;

        }

        if(!stringaVuota(nomeUnitaPadre)&&!organigramma.getElencoUnita().containsKey(nomeUnitaPadre))
        {
            MyUnita padre=organigramma.creaUnita(nomeUnitaPadre);
            organigramma.setUnitaPadre(padre);
            JOptionPane.showMessageDialog(menu, "Nuova unita padre aggiunta con successo!", "Nuova unita aggiunta", JOptionPane.INFORMATION_MESSAGE);
        }
    }


    private void sottounita()
    {
        String nomePadreSottoUnita= JOptionPane.showInputDialog(menu, "Inserisci il nome dell'unità padre della nuova unita:", "Nuova sottounità", JOptionPane.PLAIN_MESSAGE);
        if(stringaVuota(nomePadreSottoUnita))
        {
            JOptionPane.showMessageDialog(menu, "Il nome dell'unità padre non può essere vuoto.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Set<String> nomiUnitaPresenti=organigramma.getElencoUnita().keySet();
        if(!nomiUnitaPresenti.contains(nomePadreSottoUnita))
        {
            JOptionPane.showMessageDialog(menu, "Il nome dell'unità padre non è presente nell'elenco.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nomeNuovaSottoUnita= JOptionPane.showInputDialog(menu, "Inserisci il nome della nuova sottounità:", "Nuova sottounità", JOptionPane.PLAIN_MESSAGE);
        if(stringaVuota(nomePadreSottoUnita))
        {
            JOptionPane.showMessageDialog(menu, "Il nome della nuova unità non può essere vuoto.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;

        }

        if(nomePadreSottoUnita.equals(nomeNuovaSottoUnita)||nomiUnitaPresenti.contains(nomeNuovaSottoUnita))
        {
            JOptionPane.showMessageDialog(menu, "Esiste già un unità con questo nome", "Errore",  JOptionPane.ERROR_MESSAGE);
            return;
        }

        MyUnita nuovaSottoUnita=organigramma.creaUnita(nomeNuovaSottoUnita);
        MyUnita unitaPadre= organigramma.getUnita(nomePadreSottoUnita);
        organigramma.aggiungiSottoUnita(unitaPadre, nuovaSottoUnita);
        JOptionPane.showMessageDialog(menu, "Nuova sottounita aggiunta con successo!", "Nuova unita aggiunta", JOptionPane.INFORMATION_MESSAGE);


    }


    private void modificaNomeUnita()
    {

        String nomeVecchio=JOptionPane.showInputDialog(menu, "Inserire il nome dell'unità a cui modificare il nome:", "Modifica nome unità", JOptionPane.PLAIN_MESSAGE);
        if(stringaVuota(nomeVecchio))
        {
            JOptionPane.showMessageDialog(menu, "Non puoi inseirire un nome vuoto.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        HashMap<String,MyUnita> elencoUnita=organigramma.getElencoUnita();
        if(!elencoUnita.containsKey(nomeVecchio))
        {
           JOptionPane.showMessageDialog(menu, "Non esiste unità con questo nome, vuoi riprovare?", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nomeNuovo=JOptionPane.showInputDialog(menu, "Inserisci il nuovo nome da dare:", "Modifica nome unità", JOptionPane.PLAIN_MESSAGE);
        if(stringaVuota(nomeNuovo))
        {
           JOptionPane.showMessageDialog(menu, "Non puoi inseirire un nome vuoto.", "Errore", JOptionPane.ERROR_MESSAGE);
           return;
        }
        if(organigramma.getElencoUnita().containsKey(nomeNuovo))
        {
            JOptionPane.showMessageDialog(menu, "Esiste già un'unità con questo nome.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        organigramma.modificaNomeUnita(nomeVecchio, nomeNuovo);
        JOptionPane.showMessageDialog(menu, "Nome unità modificato con successo", "Errore", JOptionPane.INFORMATION_MESSAGE);
    }


    private void rimuoviUnita()
    {

        String nomeUnitaDaRimuovere=JOptionPane.showInputDialog(menu, "Inserisci il nome dell'unità che vuoi rimuovere (ricorda che così facendo eliminerai anche tutte le sotto unità): ", "Elimina unita", JOptionPane.PLAIN_MESSAGE);
        if(stringaVuota(nomeUnitaDaRimuovere))
        {
            JOptionPane.showConfirmDialog(menu, "Hai inserito un nome vuoto.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;

        }
        Set<String> elencoUnita=organigramma.getElencoUnita().keySet();
        if(!elencoUnita.contains(nomeUnitaDaRimuovere))
        {
            JOptionPane.showMessageDialog(menu, "L'unità che hai menzionato non è presente nell'elenco.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }


        MyUnita unitaDaRimuovere= organigramma.getUnita(nomeUnitaDaRimuovere);
        organigramma.eliminaUnita(unitaDaRimuovere);
        JOptionPane.showMessageDialog(menu, "Unita rimossa con successo!", "Unita rimossa", JOptionPane.INFORMATION_MESSAGE);
    }



    private void mostraUnita()
    {
        String nomeUnitaDaMostrare=JOptionPane.showInputDialog(menu, "Inserisci il nome dell'unità:", "Mostra unità", JOptionPane.PLAIN_MESSAGE);
        if(stringaVuota(nomeUnitaDaMostrare))
        {
            JOptionPane.showMessageDialog(menu, "Non esistono unità senza nome.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(!organigramma.getElencoUnita().containsKey(nomeUnitaDaMostrare))
        {
            JOptionPane.showMessageDialog(menu, "L'unità che hai menzionato non è presente.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        MyUnita unita= organigramma.getElencoUnita().get(nomeUnitaDaMostrare);
        JFrame frame = new JFrame("Dettagli Unità");



        JPanel panel = new JPanel(new BorderLayout());
        JTextArea textArea = new JTextArea(unita.toString());
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);


        frame.add(panel);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void mostraGerarchiaUnita()
    {
        GraficoAlbero grafico=new GraficoAlbero(organigramma.getElencoUnita(), organigramma.getUnitaPadre());
        JFrame frame= new JFrame("Gerarchia Unità Organizzative");
        frame.add(grafico);
        frame.setSize(800,600);
        frame.setVisible(true);
    }

    private void rimuoviTutteLeUnita()
    {
        int resultRimuoviTutteLeUnita= JOptionPane.NO_OPTION;
        resultRimuoviTutteLeUnita=JOptionPane.showConfirmDialog(menu, "Sicuro di voler eliminare tutte le unità?", "Rimuovi tutte le unità", JOptionPane.YES_NO_OPTION);
        if(resultRimuoviTutteLeUnita==JOptionPane.YES_OPTION)
        {
            organigramma.eliminaTutteLeUnita();
            JOptionPane.showMessageDialog(menu, "Unita rimosse con successo!", "Unita rimosse", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void aggiungiRuolo()
    {
        String nomeUnitaDelRuolo=JOptionPane.showInputDialog(menu, "Inserire il nome dell'unità in cui sarà inserito il nuovo ruolo: " ,"Aggiungi ruolo", JOptionPane.PLAIN_MESSAGE);
        if(stringaVuota(nomeUnitaDelRuolo))
        {
            JOptionPane.showMessageDialog(menu, "Non esistono unità senzà nome.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Set<String> elencoUnita=organigramma.getElencoUnita().keySet();

        if(!elencoUnita.contains(nomeUnitaDelRuolo))
        {
            JOptionPane.showMessageDialog(menu,"L'unità menzionata non è presente tra quelle nell'organigramma.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }


        String nomeRuolo=JOptionPane.showInputDialog(menu, "Inserire il nome del ruolo da inserire:", "Aggiungi ruolo", JOptionPane.PLAIN_MESSAGE);
        if(stringaVuota(nomeUnitaDelRuolo))
        {
            JOptionPane.showMessageDialog(menu, "Non puoi creare un ruolo senza nome.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }
        HashMap<MyRuolo,Boolean> elencoRuoli= organigramma.getElencoRuoli();
        if(!elencoRuoli.isEmpty())
        {
            for(MyRuolo ruoloX: elencoRuoli.keySet())
            {
                if(ruoloX.getNome().equals(nomeRuolo)&& !ruoloX.getNomeUnita().equals(nomeUnitaDelRuolo))
                {
                    JOptionPane.showMessageDialog(menu, "Il ruolo con questo nome è già presente in un'altra unità.", "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        }
        MyUnita unita=organigramma.getUnita(nomeUnitaDelRuolo);
        organigramma.creaRuolo(unita, nomeRuolo);
        JOptionPane.showMessageDialog(menu, "Ruolo aggiunto con successo!", "Ruolo aggiunto", JOptionPane.INFORMATION_MESSAGE);
    }

    private void rimuoviRuolo()
    {
        String ruoloDaRimuovere=JOptionPane.showInputDialog(menu, "Inserire il nome del ruolo da rimuovere:", "Rimuovi ruolo", JOptionPane.PLAIN_MESSAGE);
        if(stringaVuota(ruoloDaRimuovere))
        {
            JOptionPane.showMessageDialog(menu, "Non puoi rimuovere un ruolo senza nome, vuoi riprovare?", "Errore", JOptionPane.ERROR_MESSAGE);
            return;

        }
        MyRuolo checkRuolo= organigramma.getRuolo(ruoloDaRimuovere);
        if(checkRuolo==null)
        {
            JOptionPane.showMessageDialog(menu, "Il ruolo menzionato non è presente.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        organigramma.eliminaRuolo(checkRuolo);
        JOptionPane.showMessageDialog(menu, "Ruolo rimosso con successo!", "Ruolo rimosso", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostraRuolo()
    {
        String nomeRuolo = JOptionPane.showInputDialog(menu, "Inserisci il nome del ruolo che ti interessa.", "Mostra ruolo", JOptionPane.PLAIN_MESSAGE);
        if (stringaVuota(nomeRuolo))
        {
            JOptionPane.showMessageDialog(menu, "Non esiste un ruolo senza nome.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }


        StringBuilder testo = new StringBuilder();
        for (MyRuolo ruoloX : organigramma.getElencoRuoli().keySet())
        {
            if (ruoloX.getNome().equals(nomeRuolo))
            {
                testo.append(ruoloX.toString());
                testo.append("\n");
                boolean assegnato = organigramma.getElencoRuoli().get(ruoloX);
                if (assegnato)
                {
                    testo.append(" ASSEGNATO");
                    testo.append("\n");
                }
                else
                {
                    testo.append(" NON ASSEGNATO");
                    testo.append("\n");
                }
            }
        }

        if(testo.isEmpty())
        {
            testo.append("Non vi sono ruoli con questo nome.");
        }
        JFrame frame = new JFrame("Dettagli Unità");


        JPanel panel = new JPanel(new BorderLayout());
        JTextArea textArea = new JTextArea(testo.toString());
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        frame.add(panel);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }




    private void modificaNomeRuolo()
    {
        String nomeVecchio=JOptionPane.showInputDialog(menu, "Inserire il nome dell'unità a cui modificare il nome:", "Modifica nome ruolo", JOptionPane.PLAIN_MESSAGE);
        if(stringaVuota(nomeVecchio))
        {
            JOptionPane.showMessageDialog(menu, "Non puoi inseirire un nome vuoto.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        MyRuolo checkRuolo= organigramma.getRuolo(nomeVecchio);
        if(checkRuolo==null)
        {
            JOptionPane.showMessageDialog(menu, "Non esiste unità con questo nome.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nomeNuovo=JOptionPane.showInputDialog(menu, "Inserisci il nuovo nome da dare:", "Modifica nome ruolo", JOptionPane.PLAIN_MESSAGE);
        if(stringaVuota(nomeNuovo))
        {
            JOptionPane.showMessageDialog(menu, "Non puoi inseirire un nome vuoto.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(organigramma.getRuolo(nomeNuovo)!=null)
        {
            JOptionPane.showMessageDialog(menu, "Esiste già un ruolo con questo nome.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        organigramma.modificaNomeRuolo(nomeVecchio,nomeNuovo);
        JOptionPane.showMessageDialog(menu, "Nome ruolo modificato con successo!", "Nome ruolo modificato", JOptionPane.INFORMATION_MESSAGE);
    }


    private void assegnaRuolo()
    {
        String nomeRuolo= JOptionPane.showInputDialog(menu, "Inserisci il nome del ruolo che vuoi assegnare.", "Assegna ruolo", JOptionPane.PLAIN_MESSAGE);
        if(stringaVuota(nomeRuolo))
        {
            JOptionPane.showMessageDialog(menu, "Non puoi inserire un ruolo senza nome.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        HashMap<MyRuolo, Boolean> elencoRuoli= organigramma.getElencoRuoli();
        for(MyRuolo ruoloX: elencoRuoli.keySet())
        {
            if(ruoloX.getNome().equals(nomeRuolo)&& !elencoRuoli.get(ruoloX) )
            {
                String cf= JOptionPane.showInputDialog(menu, "Inserisci il codice fiscale del dipendente a cui vuoi assegnare il ruolo", "Assegna ruolo", JOptionPane.PLAIN_MESSAGE);
                if(stringaVuota(cf))
                {
                    JOptionPane.showMessageDialog(menu, "Non puoi inserire un codice fiscale vuoto.", "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if(cf.length()!=16)
                {
                    JOptionPane.showMessageDialog(menu, "Codice fiscale non conforme.", "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if(!organigramma.getAssegnamentiDipendenti().containsKey(cf))
                {
                    JOptionPane.showMessageDialog(menu, "Non esiste alcun dipendente con questo codice fiscale.", "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                HashSet<MyRuolo> ruoliDipendente= organigramma.getAssegnamentiDipendenti().get(cf);
                for(MyRuolo ruoloY: ruoliDipendente)
                {
                    if(ruoloY.getNome().equals(nomeRuolo))
                    {
                        JOptionPane.showMessageDialog(menu, "Il ruolo è già stato assegnato al dipendente", "Errore", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                MyDipendente dipendente=organigramma.getDipendente(cf);
                if(!organigramma.checkRuoliDipendente(ruoloX,ruoliDipendente))
                {
                    JOptionPane.showMessageDialog(menu, "Il nuovo ruolo non è compatibile con i ruoli del dipendente.", "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                organigramma.assegnaRuolo(dipendente, ruoloX);
                JOptionPane.showMessageDialog(menu, "Assegnamento ruolo eseguito con successo.", "Ruolo assegnato", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }
        JOptionPane.showMessageDialog(menu, "Non vi sono disponibili ruoli di questo tipo", "Errore", JOptionPane.ERROR_MESSAGE);
    }


    private void deassegnaRuolo()
    {
        String cf= JOptionPane.showInputDialog(menu, "Inserisci il codice fiscale del dipendente a cui vuoi assegnare il ruolo", "Assegna ruolo", JOptionPane.PLAIN_MESSAGE);
        if(stringaVuota(cf))
        {
            JOptionPane.showMessageDialog(menu, "Non puoi inserire un codice fiscale vuoto.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(cf.length()!=16)
        {
            JOptionPane.showMessageDialog(menu, "Codice fiscale non conforme.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(!organigramma.getAssegnamentiDipendenti().containsKey(cf))
        {
            JOptionPane.showMessageDialog(menu, "Non esiste alcun dipendente con questo codice fiscale.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nomeRuolo= JOptionPane.showInputDialog(menu, "Inserisci il nome del ruolo che vuoi assegnare.", "Assegna ruolo", JOptionPane.PLAIN_MESSAGE);
        if(stringaVuota(nomeRuolo))
        {
            JOptionPane.showMessageDialog(menu, "Non puoi inserire un ruolo senza nome.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        MyDipendente dipendente= organigramma.cercaDipendente(cf);
        HashSet<MyRuolo> ruoliDipendente= organigramma.getAssegnamentiDipendenti().get(cf);
        for(MyRuolo ruoloX: ruoliDipendente)
        {
            if(ruoloX.getNome().equals(nomeRuolo))
            {
                organigramma.deAssegnaRuolo(dipendente,ruoloX);
            JOptionPane.showMessageDialog(menu, "Il ruolo è stato deassegnato correttamente.", "Ruolo deassegnato", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }
        JOptionPane.showMessageDialog(menu, "Il dipendente non aveva questo tipo di ruolo tra quelli assegnati.", "Errore", JOptionPane.ERROR_MESSAGE);
    }


    private void mostraRuoliAncoraDisponibili()
    {
        StringBuilder testo=new StringBuilder();
        HashMap<MyRuolo, Boolean> elencoRuoli=organigramma.getElencoRuoli();
        JFrame frame = new JFrame("Ruoli disponibili");

        // Creazione del JPanel per visualizzare il toString dell'unità
        JPanel panel = new JPanel(new BorderLayout());
        if(!elencoRuoli.containsValue(false))
        {
            testo.append("Non vi sono unità disponibili");
        }

        if(elencoRuoli.containsValue(false))
        {
            for(MyRuolo ruoloX : elencoRuoli.keySet())
            {
                if(!elencoRuoli.get(ruoloX))
                {
                    testo.append(ruoloX.toString()).append("\n");
                }
            }
        }
        JTextArea textArea = new JTextArea(testo.toString());
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Aggiunta del JPanel al JFrame
        frame.add(panel);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void mostraTuttiIRuoli()
    {
        StringBuilder testo=new StringBuilder();
        HashMap<MyRuolo, Boolean> elencoRuoli=organigramma.getElencoRuoli();
        JFrame frame = new JFrame("Ruoli registrati");

        // Creazione del JPanel per visualizzare il toString dell'unità
        JPanel panel = new JPanel(new BorderLayout());

        for(MyRuolo ruoloX : elencoRuoli.keySet())
        {
            testo.append(ruoloX.toString());
            testo.append("\n");
            if (elencoRuoli.get(ruoloX)) {
                testo.append("ASSEGNATO");
                testo.append("\n");
            }
            else
            {
                    testo.append("NON ASSEGNATO");
                    testo.append("\n");
            }
        }

        if(testo.isEmpty())
        {
            testo.append("Non vi è alcun ruolo.");
        }
        JTextArea textArea = new JTextArea(testo.toString());
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Aggiunta del JPanel al JFrame
        frame.add(panel);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    private void resettaRuoliDipendente()
    {


        String CFDipendente=JOptionPane.showInputDialog(menu, "Inserisci il codice fiscale del dipendente:", "Resetta ruoli dipendente", JOptionPane.PLAIN_MESSAGE);
        if (stringaVuota(CFDipendente))
        {
            JOptionPane.showMessageDialog(menu, "Il codice fiscale non può essere vuoto.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (CFDipendente.length() != 16)
        {
            JOptionPane.showMessageDialog(menu, "Il codice fiscale deve essere di 16 cifre.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        MyDipendente checkDipendente=organigramma.cercaDipendente(CFDipendente);
        if(checkDipendente==null)
        {
            JOptionPane.showMessageDialog(menu, "Non vi è alcun dipendente con tale codice fiscale", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int resultResettaRuoliDipendete=JOptionPane.showConfirmDialog(menu, "Sei sicuro di voler resettare gli assegnamenti del dipendente?", "Resetta ruoli dipendente", JOptionPane.YES_NO_OPTION);
        if(resultResettaRuoliDipendete==JOptionPane.YES_OPTION);
        {
            HashSet<MyRuolo> daDeassegnare= organigramma.getAssegnamentiDipendenti().get(checkDipendente.getCodiceFiscale());
            organigramma.deAssegnaRuoliDipendente(checkDipendente, daDeassegnare);
        }
    }


    private void resettaAssegnamentoRuoli()
    {

        int resultResettaAssegnamentoRuoli =JOptionPane.showConfirmDialog(menu, "Sicuro di voler deassegnare tutti i ruoli?", "Deassegna tutti i ruoli", JOptionPane.YES_NO_OPTION);
        if(resultResettaAssegnamentoRuoli ==JOptionPane.YES_OPTION)
        {
            organigramma.deAssegnaTuttiIRuoli();
            JOptionPane.showMessageDialog(menu, "Ruoli deassegnati con successo!", "Ruoli deassegnati", JOptionPane.INFORMATION_MESSAGE);
        }

    }


    private void eliminaTuttiIRuoli()
    {
        int resultEliminaTuttiIRuoli = JOptionPane.NO_OPTION;
        resultEliminaTuttiIRuoli =JOptionPane.showConfirmDialog(menu, "Sicuro di voler eliminare tutti i ruoli?", "Deassegna tutti i ruoli", JOptionPane.YES_NO_OPTION);
        if(resultEliminaTuttiIRuoli ==JOptionPane.YES_OPTION)
        {
            Set<MyRuolo> daEliminare= organigramma.getElencoRuoli().keySet();
            organigramma.eliminaElencoRuoli(daEliminare);
            JOptionPane.showMessageDialog(menu, "Ruoli eliminati con successo!", "Ruoli eliminati", JOptionPane.INFORMATION_MESSAGE);
        }

    }
    private void aggiungiDipendente() {
        final boolean[] datiConformi = {false};


            JDialog dialog = new JDialog();
            dialog.setTitle("Aggiungi Dipendente");
            dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            JTextField nomeField = new JTextField(20);
            JTextField cognomeField = new JTextField(20);

            JPanel sessoPanel = new JPanel();
            JRadioButton maschioButton = new JRadioButton("Maschio");
            JRadioButton femminaButton = new JRadioButton("Femmina");
            ButtonGroup sessoGroup = new ButtonGroup();
            sessoGroup.add(maschioButton);
            sessoGroup.add(femminaButton);
            sessoPanel.add(maschioButton);
            sessoPanel.add(femminaButton);

            JPanel dataPanel = new JPanel();
            JTextField giornoField = new JTextField(2);
            JTextField meseField = new JTextField(2);
            JTextField annoField = new JTextField(4);
            dataPanel.add(new JLabel("Giorno:"));
            dataPanel.add(giornoField);
            dataPanel.add(new JLabel("Mese:"));
            dataPanel.add(meseField);
            dataPanel.add(new JLabel("Anno:"));
            dataPanel.add(annoField);

            JTextField luogoNascitaField = new JTextField(20);
            JTextField codiceFiscaleField = new JTextField(16);

            panel.add(new JLabel("Nome:"));
            panel.add(nomeField);
            panel.add(new JLabel("Cognome:"));
            panel.add(cognomeField);
            panel.add(new JLabel("Sesso:"));
            panel.add(sessoPanel);
            panel.add(new JLabel("Data di nascita:"));
            panel.add(dataPanel);
            panel.add(new JLabel("Luogo di nascita:"));
            panel.add(luogoNascitaField);
            panel.add(new JLabel("Codice Fiscale:"));
            panel.add(codiceFiscaleField);

            JButton confermaButton = new JButton("Conferma");
            confermaButton.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    String nome = nomeField.getText();
                    String cognome = cognomeField.getText();
                    char sesso = maschioButton.isSelected() ? 'M' : 'F';
                    int giorno, mese, anno;

                    //valida anno
                    try
                    {
                        giorno = Integer.parseInt(giornoField.getText());
                        mese = Integer.parseInt(meseField.getText());
                        anno = Integer.parseInt(annoField.getText());
                        LocalDate dataNascita = LocalDate.of(anno, mese, giorno);

                    }
                    catch (Exception ex) {
                        JOptionPane.showMessageDialog(dialog, "Inserire una data di nascita valida", "Errore", JOptionPane.ERROR_MESSAGE);
                        dialog.dispose();
                        return;
                    }
                    String luogoNascita = luogoNascitaField.getText();
                    String codiceFiscale = codiceFiscaleField.getText();

                    // Esegui le validazioni
                    if (stringaVuota(nome) || stringaVuota(cognome) || stringaVuota(luogoNascita) || stringaVuota(codiceFiscale)) {
                        JOptionPane.showMessageDialog(dialog, "Inserire una data di nascita valida", "Errore", JOptionPane.ERROR_MESSAGE);
                        dialog.dispose();
                        return;
                    }

                    // Verifica se la data di nascita è valida


                    //verfica se il codicefiscale è valido
                    if(codiceFiscale.length()!=16 || organigramma.getAssegnamentiDipendenti().containsKey(codiceFiscale))
                    {
                        JOptionPane.showMessageDialog(dialog, "Codice fiscale non valido o già presente.", "Errore", JOptionPane.ERROR_MESSAGE);
                        dialog.dispose();
                        return;

                    }

                    // Se tutti i controlli passano, crea il dipendente


                    organigramma.creaDipendente(nome,cognome, sesso, giorno, mese, anno, luogoNascita, codiceFiscale);
                    // Chiudi il dialogo
                    dialog.dispose();

                    // Imposta datiConformi su true se tutte le validazioni passano
                    datiConformi[0] = true;
                    JOptionPane.showMessageDialog(menu, "Dipendente aggiunto con successo!", "Dipendente aggiunto", JOptionPane.INFORMATION_MESSAGE);
                }
            });
            panel.add(confermaButton);

            dialog.add(panel);
            dialog.setSize(300, 400);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);


    }

    private void rimuoviDipendente()
    {
        String CFdipendente =JOptionPane.showInputDialog(menu, "Inserisci il codice fiscale del dipendente che vuoi rimuovere:", "Rimuovi dipendente", JOptionPane.PLAIN_MESSAGE);
        if(stringaVuota(CFdipendente))
        {
            JOptionPane.showMessageDialog(menu, "Hai inserito un codice fiscale vuoto.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(CFdipendente.length()!=16)
        {
            JOptionPane.showMessageDialog(menu, "Hai inserito un codice fiscale non conforme.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }
        MyDipendente checkDipendente= organigramma.cercaDipendente(CFdipendente);
        if(checkDipendente==null)
        {
            return;
        }
        organigramma.eliminaDipendente(checkDipendente);
        JOptionPane.showMessageDialog(menu, "Dipendente rimosso con successo!", "Dipendente rimosso", JOptionPane.INFORMATION_MESSAGE);
    }

    private void modificaCFDipendente()
    {
        int resultModificaDipendente=JOptionPane.YES_OPTION;



        String vecchioCF=JOptionPane.showInputDialog(menu, "Inserisci il codice fiscale del dipendente:", "Modifica codice fiscale", JOptionPane.PLAIN_MESSAGE);
        if(stringaVuota(vecchioCF))
        {
        JOptionPane.showMessageDialog(menu, "Non puoi inserire un codice fiscale vuoto.","Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if(vecchioCF.length()!=16)
        {
            JOptionPane.showMessageDialog(menu, "Codice fiscale non conforme.","Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(organigramma.getDipendente(vecchioCF)==null)
        {
            JOptionPane.showMessageDialog(menu, "Non esiste alcun dipendente con questo codice fiscale.","Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }


        String nuovoCF=JOptionPane.showInputDialog(menu, "Inserisci il nuovo codice fiscale del dipendente:", "Modifica codice fiscale", JOptionPane.PLAIN_MESSAGE);
        if(stringaVuota(vecchioCF))
        {
            JOptionPane.showMessageDialog(menu, "Non puoi inserire un codice fiscale vuoto.","Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if(nuovoCF.length()!=16)
        {
            JOptionPane.showMessageDialog(menu, "Codice fiscale non conforme.","Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(organigramma.getDipendente(nuovoCF)!=null)
        {
            JOptionPane.showMessageDialog(menu, "Codice fiscale già in uso, vuoi riprovare?","Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        organigramma.modificaCodiceFiscaleDipendente(vecchioCF,nuovoCF);
        JOptionPane.showMessageDialog(menu, "Modifica dipendente effettuata con successo!", "Dipendente modificato", JOptionPane.INFORMATION_MESSAGE);



    }


    private void modificaCognomeDipendente()
    {

            String CF=JOptionPane.showInputDialog(menu, "Inserisci il codice fiscale del dipendente:", "Modifica cognome", JOptionPane.PLAIN_MESSAGE);
            if(stringaVuota(CF))
            {
                JOptionPane.showMessageDialog(menu, "Non puoi inserire un codice fiscale vuoto.","Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(CF.length()!=16)
            {
                JOptionPane.showConfirmDialog(menu, "Codice fiscale non conforme.","Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(organigramma.getDipendente(CF)==null)
            {
                JOptionPane.showMessageDialog(menu, "Non esiste alcun dipendente con questo codice fiscale","Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String cognome=JOptionPane.showInputDialog(menu, "Inserisci il nuovo cognome del dipendente:", "Modifica cognome", JOptionPane.PLAIN_MESSAGE);
            if(stringaVuota(CF))
            {
                JOptionPane.showConfirmDialog(menu, "Non puoi inserire un cognome vuoto.", "Errore", JOptionPane.ERROR_MESSAGE);
                return;

            }
            organigramma.modificaCognomeDipendente(CF,cognome);
            JOptionPane.showMessageDialog(menu, "Modifica dipendente effettuata con successo!", "Dipendente modificato", JOptionPane.INFORMATION_MESSAGE);
    }

    private void modificaNomeDipendente()
    {


        String CF =JOptionPane.showInputDialog(menu, "Inserisci il codice fiscale del dipendente:", "Modifica nome", JOptionPane.PLAIN_MESSAGE);
        if(stringaVuota(CF))
        {
            JOptionPane.showMessageDialog(menu, "Non puoi inserire un codice fiscale vuoto.","Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(CF.length()!=16)
        {
            JOptionPane.showMessageDialog(menu, "Codice fiscale non conforme.","Errore", JOptionPane.ERROR_MESSAGE);
           return;
        }
        if(organigramma.getDipendente(CF)==null)
        {
            JOptionPane.showMessageDialog(menu, "Non esiste alcun dipendente con questo codice fiscale.","Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }



        String nome=JOptionPane.showInputDialog(menu, "Inserisci il nuovo nome del dipendente:", "Modifica nome", JOptionPane.PLAIN_MESSAGE);
        if(stringaVuota(nome)) {
            JOptionPane.showMessageDialog(menu, "Non puoi inserire un nome vuoto.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        organigramma.modificaNomeDipendente(CF,nome);
        JOptionPane.showMessageDialog(menu, "Modifica dipendente effettuata con successo!", "Dipendente modificato", JOptionPane.INFORMATION_MESSAGE);



    }


    private void modificaSessoDipendente() {
        String CF = JOptionPane.showInputDialog(menu, "Inserisci il codice fiscale del dipendente:", "Modifica sesso", JOptionPane.PLAIN_MESSAGE);
        if (stringaVuota(CF)) {
            JOptionPane.showMessageDialog(menu, "Non puoi inserire un codice fiscale vuoto.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (CF.length() != 16) {
            JOptionPane.showMessageDialog(menu, "Codice fiscale non conforme.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }
        MyDipendente daAggiornare = organigramma.getDipendente(CF);
        if (daAggiornare == null) {
            JOptionPane.showConfirmDialog(menu, "Non esiste alcun dipendente con questo codice fiscale.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }
        char sesso = daAggiornare.getSesso();
        if (sesso == 'M') {
            organigramma.modificaSessoDipendente(CF, 'F');
            JOptionPane.showMessageDialog(menu, "Modifica dipendente effettuata con successo!", "Dipendente modificato", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        organigramma.modificaSessoDipendente(CF, 'M');
        JOptionPane.showMessageDialog(menu, "Modifica dipendente effettuata con successo!", "Dipendente modificato", JOptionPane.INFORMATION_MESSAGE);
    }


    private void modificaDataNascitaDipendente()
    {

        String CF = JOptionPane.showInputDialog(menu, "Inserisci il codice fiscale del dipendente:", "Modifica sesso", JOptionPane.PLAIN_MESSAGE);
        if (stringaVuota(CF))
        {
            JOptionPane.showMessageDialog(menu, "Non puoi inserire un codice fiscale vuoto.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;

        }

        if (CF.length() != 16)
        {
            JOptionPane.showMessageDialog(menu, "Codice fiscale non conforme.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }
        MyDipendente daAggiornare = organigramma.getDipendente(CF);
        if (daAggiornare == null) {
            JOptionPane.showMessageDialog(menu, "Non esiste alcun dipendente con questo codice fiscale.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }


        JFrame frame = new JFrame("Modifica Dipendente");

        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));

        // Label e TextField per data di nascita
        JLabel label = new JLabel("Inserisci la nuova data di nascita (formato: dd/MM/yyyy):");
        panel.add(label);

        JTextField dataNascitaField = new JTextField();
        panel.add(dataNascitaField);

        JButton modificaButton = new JButton("Mostra Dipendenti");
        modificaButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dataNascitaStr = dataNascitaField.getText();
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate dataNascita = LocalDate.parse(dataNascitaStr, formatter);
                    organigramma.modificaDataNascitaDipendente(CF, dataNascita.getDayOfMonth(),dataNascita.getMonthValue(),dataNascita.getYear());
                    frame.dispose();
                    JOptionPane.showMessageDialog(frame, "Modifica dipendente effettuata con successo.", "Errore", JOptionPane.INFORMATION_MESSAGE);


                } catch (Exception ex)
                {
                    JOptionPane.showMessageDialog(frame, "Data non valida. Inserisci una data nel formato dd/MM/yyyy.", "Errore", JOptionPane.ERROR_MESSAGE);
                    frame.dispose();
                }
            }
        });
        panel.add(modificaButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void modificaLuogoNascitaDipendente()
    {

        String CF=JOptionPane.showInputDialog(menu, "Inserisci il codice fiscale del dipendente:", "Modifica luogo nascita", JOptionPane.PLAIN_MESSAGE);
        if(stringaVuota(CF))
        {
            JOptionPane.showMessageDialog(menu, "Non puoi inserire un codice fiscale vuoto.","Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(CF.length()!=16)
        {
            JOptionPane.showMessageDialog(menu, "Codice fiscale non conforme.","Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(organigramma.getDipendente(CF)==null)
        {
            JOptionPane.showMessageDialog(menu, "Non esiste alcun dipendente con questo codice fiscale.","Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(organigramma.getDipendente(CF)!=null) {

            String luogo = JOptionPane.showInputDialog(menu, "Inserisci il nuovo luogo nascita del dipendente:", "Modifica luogo nascita", JOptionPane.PLAIN_MESSAGE);
            if (stringaVuota(CF))
            {
                JOptionPane.showMessageDialog(menu, "Non puoi inserire un luogo di nascita vuoto.", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }
            organigramma.modificaLuogoNascitaDipendente(CF, luogo);
            JOptionPane.showMessageDialog(menu, "Modifica dipendente effettuata con successo!", "Dipendente modificato", JOptionPane.INFORMATION_MESSAGE);
        }
    }


    private void mostraDipendentePerCF()
    {


        String CFdipendente= JOptionPane.showInputDialog(menu, "Inserire il codice fiscale del dipendente:", "Mostra dipendente", JOptionPane.PLAIN_MESSAGE);
        if(stringaVuota(CFdipendente))
        {
            JOptionPane.showMessageDialog(menu, "Non puoi inseire un codice fiscale vuoto, vuoi riprovare?", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(CFdipendente.length()!=16)
        {
            JOptionPane.showMessageDialog(menu, "Codice fiscale non conforme, vuoi riprovare?", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }
        MyDipendente checkDipendente = organigramma.cercaDipendente(CFdipendente);
        if(checkDipendente==null)
        {
            JOptionPane.showMessageDialog(menu, "Non esiste alcun dipendente con questo codice fiscale, vuoi riprovare?", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JFrame frame = new JFrame("Dettagli Unità");

        JPanel panel = new JPanel(new BorderLayout());
        JTextArea textArea = new JTextArea(checkDipendente.toString());
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);


        frame.add(panel);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }


    private void mostraDipendentePerCognome()
    {

        String cognome=JOptionPane.showInputDialog(menu, "Inserisci il cognome dei dipendenti che vuoi vedere:", "Mostra dipendente", JOptionPane.PLAIN_MESSAGE);
        if(stringaVuota(cognome))
        {
            JOptionPane.showMessageDialog(menu, "Non puoi inserire un cognome vuoto.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        StringBuilder testo= new StringBuilder();
        HashSet<MyDipendente> dipendenti= organigramma.getElencoDipendenti();
        for(MyDipendente dipX: dipendenti)
        {
            if(dipX.getCognome().equals(cognome))
            {
                testo.append(dipX.toString());
                testo.append("\n");
            }
        }
        if(testo.isEmpty())
        {
            testo.append("Non esiste alcun dipendente con questo cognome.");
        }
        JFrame frame = new JFrame("Dettagli Unità");
        // Creazione del JPanel per visualizzare il toString dell'unità
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea textArea = new JTextArea(testo.toString());
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Aggiunta del JPanel al JFrame
        frame.add(panel);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void mostraDipendentePerNome()
    {

        String nome=JOptionPane.showInputDialog(menu, "Inserisci il nome dei dipendenti che vuoi vedere:", "Mostra dipendente", JOptionPane.PLAIN_MESSAGE);
        if(stringaVuota(nome))
        {
            JOptionPane.showMessageDialog(menu, "Non puoi inserire un nome vuoto.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        StringBuilder testo= new StringBuilder();
        HashSet<MyDipendente> dipendenti= organigramma.getElencoDipendenti();
        for(MyDipendente dipX: dipendenti)
        {
            if(dipX.getNome().equals(nome))
            {
                testo.append(dipX.toString());
                testo.append("\n");
            }
        }
        if(testo.isEmpty())
        {
            testo.append("Non esiste alcun dipendente con questo nome.");
        }
        JFrame frame = new JFrame("Dettagli Unità");

        JPanel panel = new JPanel(new BorderLayout());
        JTextArea textArea = new JTextArea(testo.toString());
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Aggiunta del JPanel al JFrame
        frame.add(panel);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void mostraDipendentePerSesso()
    {
        JFrame frame = new JFrame("Gestione Dipendenti");

        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));

        JLabel label = new JLabel("Seleziona il sesso dei dipendenti da mostrare:");
        panel.add(label);

        JRadioButton maschioButton = new JRadioButton("Maschio");
        JRadioButton femminaButton = new JRadioButton("Femmina");

        ButtonGroup group = new ButtonGroup();
        group.add(maschioButton);
        group.add(femminaButton);

        panel.add(maschioButton);
        panel.add(femminaButton);

        JButton mostraButton = new JButton("Mostra Dipendenti");
        mostraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                char sesso = maschioButton.isSelected() ? 'M' : 'F';
                stampaDipendetiPerSesso(sesso);
            }
        });
        panel.add(mostraButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void stampaDipendetiPerSesso(char sesso)
    {
        StringBuilder testo= new StringBuilder();

        HashSet<MyDipendente> dipendenti=organigramma.getElencoDipendenti();
        for(MyDipendente dipX:dipendenti)
        {
            if(dipX.getSesso()==sesso)
            {
                testo.append(dipX.toString());
                testo.append("\n");
            }
        }
        if(testo.isEmpty())
        {
            testo.append("Non ci sono dipendenti del sesso selezionato.");
        }
        JFrame frame = new JFrame("Gestione Dipendenti");

        frame.setSize(400, 300);
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea textArea = new JTextArea(testo.toString());
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Aggiunta del JPanel al JFrame
        frame.add(panel);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        return;

    }


    private void mostraDipendentePerDataNascita()
    {
        JFrame frame = new JFrame("Gestione Dipendenti");

        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));

        // Label e TextField per data di nascita
        JLabel label = new JLabel("Inserisci la data di nascita dei dipendenti da mostrare (formato: dd/MM/yyyy):");
        panel.add(label);

        JTextField dataNascitaField = new JTextField();
        panel.add(dataNascitaField);

        JButton mostraButton = new JButton("Mostra Dipendenti");
        mostraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dataNascitaStr = dataNascitaField.getText();
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate dataNascita = LocalDate.parse(dataNascitaStr, formatter);
                    mostraDipendentePerDataNascita(dataNascita);
                    frame.dispose();


                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(frame, "Data non valida. Inserisci una data nel formato dd/MM/yyyy.", "Errore", JOptionPane.ERROR_MESSAGE);
                    frame.dispose();
                }
            }
        });

        panel.add(mostraButton);

        frame.add(panel);
        frame.setVisible(true);

    }

    private void mostraDipendentePerDataNascita(LocalDate dataNascita)
    {
        JFrame resultFrame = new JFrame("Risultati");
        resultFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        resultFrame.setSize(400, 300);

        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new GridLayout(0, 1));

       StringBuilder testo= new StringBuilder();
       HashSet<MyDipendente> dipendenti= organigramma.getElencoDipendenti();
       for(MyDipendente dipX: dipendenti)
       {
           if(dipX.getDataNascita().equals(dataNascita))
           {
               testo.append(dipX.toString());
               testo.append("\n");
           }
       }
       if(testo.isEmpty())
       {
           testo.append("Nessun dipendente è nato in questa data.");
       }
        JFrame frame = new JFrame("Dettagli Unità");


        JPanel panel = new JPanel(new BorderLayout());
        JTextArea textArea = new JTextArea(testo.toString());
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Aggiunta del JPanel al JFrame
        frame.add(panel);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    private void mostraDipendentePerLuogoDiNascita()
    {

        String luogo =JOptionPane.showInputDialog(menu, "Inserisci il luogo di nascita dei dipendenti che vuoi vedere:", "Mostra dipendente", JOptionPane.PLAIN_MESSAGE);
        if(stringaVuota(luogo))
        {
            JOptionPane.showMessageDialog(menu, "Non puoi inserire un nome vuoto.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        StringBuilder testo= new StringBuilder();
        HashSet<MyDipendente> dipendenti= organigramma.getElencoDipendenti();
        for(MyDipendente dipX: dipendenti)
        {
            if(dipX.getLuogoNascita().equals(luogo))
            {
                testo.append(dipX.toString());
                testo.append("\n");
            }
        }
        if(testo.isEmpty())
        {
            testo.append("Non esiste alcun dipendente nato in questo luogo.");
        }
        JFrame frame = new JFrame("Dettagli Unità");

        // Creazione del JPanel per visualizzare il toString dell'unità
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea textArea = new JTextArea(testo.toString());
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);
        // Aggiunta del JPanel al JFrame
        frame.add(panel);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    private void mostraRuoliDipendente()
    {
        String cf=JOptionPane.showInputDialog(menu,"Inserisci il codice fiscale del dipendente", "Mostra ruoli dipendente", JOptionPane.PLAIN_MESSAGE);
        if(stringaVuota(cf))
        {
            JOptionPane.showMessageDialog(menu, "Non puoi inserire un codice fiscale vuoto." ,"Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(cf.length()!=16)
        {
            JOptionPane.showMessageDialog(menu, "Codice fiscale non conforme.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }
        MyDipendente checkDipedente= organigramma.cercaDipendente(cf);
        if(checkDipedente==null)
        {
            JOptionPane.showMessageDialog(menu, "Dipendente non presente.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }
        StringBuilder testo = new StringBuilder();
        HashSet<MyRuolo> ruoli= organigramma.getAssegnamentiDipendenti().get(cf);
        for(MyRuolo ruolo: ruoli)
        {
            testo.append(ruolo.toString());
            testo.append("\"");
        }

        if(testo.isEmpty())
        {
            testo.append("Il dipendente non ha alcun ruolo assegnato.");
        }

        JFrame frame = new JFrame("Dipendenti registrati");

        // Creazione del JPanel per visualizzare il toString dell'unità
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea textArea = new JTextArea(testo.toString());
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Aggiunta del JPanel al JFrame
        frame.add(panel);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    private void mostraTuttiIDipendenti()
    {
        StringBuilder testo=new StringBuilder();
        HashSet<MyDipendente> elenco=organigramma.getElencoDipendenti();
        JFrame frame = new JFrame("Dipendenti registrati");

        // Creazione del JPanel per visualizzare il toString dell'unità
        JPanel panel = new JPanel(new BorderLayout());
        for(MyDipendente dipX : elenco)
        {
            testo.append(dipX.toString());
            testo.append("\n");
        }
        if(testo.isEmpty())
        {
            testo.append("Non vi è alcun dipendente.");
        }
        JTextArea textArea = new JTextArea(testo.toString());
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Aggiunta del JPanel al JFrame
        frame.add(panel);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void eliminaTuttiIDipendenti()
    {
        int resultEliminaTuttiIDipendenti = JOptionPane.NO_OPTION;
        resultEliminaTuttiIDipendenti =JOptionPane.showConfirmDialog(menu, "Sicuro di voler eliminare tutti dipendenti?", "Rimuovi tutte le unità", JOptionPane.YES_NO_OPTION);
        if(resultEliminaTuttiIDipendenti ==JOptionPane.YES_OPTION)
        {
            HashSet<MyDipendente> daEliminare=organigramma.getElencoDipendenti();
            organigramma.eliminaElencoDipendenti(daEliminare);
            JOptionPane.showMessageDialog(menu, "Dipendenti rimossi con successo!", "Dipendenti rimossi", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private boolean consensoUscita()
    {
        int option = JOptionPane.showConfirmDialog(
                null,
                "Sei sicuro di voler uscire?",
                "Conferma Uscita",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
        return option == JOptionPane.YES_OPTION;
    }



    private boolean stringaVuota(String stringa)
    {
        return stringa==null||stringa.isEmpty();
    }

    public static void main(String[] args)
    {

        Menu menu=new Menu();
        AscoltatoreEventiAzione ascoltatoreEventiAzione=new AscoltatoreEventiAzione(menu);

    }
}

