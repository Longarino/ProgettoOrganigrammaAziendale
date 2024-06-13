
import eccezioniProgramma.*;

import java.io.Serializable;
import java.util.*;

import static java.lang.Math.abs;

public class MyOrganigramma implements AbstractFactoryOrganigramma, Serializable
{
        private  HashMap<String, MyUnita> elencoUnita;
        private  HashMap<MyRuolo, Boolean> elencoRuoli;
        private  HashMap<String, HashSet<MyRuolo>> elencoAssegnamentiDipendenti; //hasmap con chiave il CF del dipendente e con valore i elencoRuoli svolti
        private  HashSet<MyDipendente> elencoDipendenti;
        private  MyUnita padre;


        public MyOrganigramma() {
            this.elencoUnita = new HashMap<String, MyUnita>();
            this.elencoRuoli = new HashMap<MyRuolo, Boolean>();
            this.elencoAssegnamentiDipendenti = new HashMap<String, HashSet<MyRuolo>>();
            this.elencoDipendenti = new HashSet<MyDipendente>();
        }

        @Override
        public MyUnita creaUnita(String nome) {
            MyUnita unita = new MyUnita();
            unita.setNome(nome);
            return unita;

        }

        public void setUnitaPadre(MyUnita unita)
        {
            eliminaTutteLeUnita();
            this.padre=unita;
            padre.setLivello(0);
            elencoUnita.put(unita.getNome(), unita);
        }

        @Override
        public boolean aggiungiSottoUnita(MyUnita unitaPadre, MyUnita sottoUnita)// l'unità passata per parametro è l'unità padre a cui dovrà essere associata la unità con il nome "nome"
        {


            try
            {
                if(!elencoUnita.containsKey(unitaPadre.getNome()))
                {
                    throw new NotDefinedElementException("L'unità passata per padre non è definita.");
                }

                if (elencoUnita.containsKey(sottoUnita.getNome())) {
                    throw new AlredyExistingElementException("Esiste già un unità con questo nome.");
                }
                MyUnita sottoUnitaConNuovoLivello= sottoUnita;
                sottoUnitaConNuovoLivello.setLivello(unitaPadre.getLivello()+1);
                elencoUnita.put(sottoUnita.getNome(),sottoUnita);
                MyUnita unitaPadreAggiornata= unitaPadre;
                unitaPadreAggiornata.aggiungiNomeSottoUnita(sottoUnita.getNome());
                elencoUnita.put(unitaPadreAggiornata.getNome(), unitaPadreAggiornata);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;//nel caso in cui l'unità sarà senza livello ciò significa che l'unità non è stata inserita nell'organigramma

        }


        public boolean modificaNomeUnita(String nome, String nomeNuovo)
        {
            try
            {
                if(elencoUnita.containsKey(nomeNuovo))
                {
                    throw new AlredyExistingElementException("Il nome nuovo che si vuole utilizzare è gia presente nell'elenco ");
                }
                if(!elencoUnita.containsKey(nome))
                {
                    throw new NotDefinedElementException("Il nome dell'unità inserito non è presente nell'elenco");
                }
                MyUnita salvaUnita= elencoUnita.get(nome);
                HashSet<MyRuolo> elencoRuoliUnita=salvaUnita.getRuoli();
                salvaUnita.setNome(nomeNuovo);
                for(MyRuolo ruolo: elencoRuoliUnita)
                {
                    boolean valore=elencoRuoli.get(ruolo);
                    MyRuolo ruoloAggiornato=ruolo;
                    ruoloAggiornato.setNomeUnita(nomeNuovo);
                    elencoRuoli.remove(ruolo);
                    elencoRuoli.put(ruoloAggiornato,valore);
                    salvaUnita.rimuoviRuolo(ruolo);
                    salvaUnita.aggiungiRuolo(ruoloAggiornato);
                    for(String CFdip: elencoAssegnamentiDipendenti.keySet())
                    {
                        HashSet<MyRuolo> elencoRuoliAssegnati= elencoAssegnamentiDipendenti.get(CFdip);
                        if(elencoAssegnamentiDipendenti.containsKey(ruolo))
                        {
                            elencoRuoliAssegnati.remove(ruolo);
                            elencoRuoliAssegnati.add(ruoloAggiornato);
                        }
                    }
                }
                this.elencoUnita.put(nomeNuovo, salvaUnita);
                MyUnita unitaPadre=cercaUnitaPadre(nome);
                if(unitaPadre!=null)
                {
                    unitaPadre.rimuoviSottoUnita(nome);
                    unitaPadre.aggiungiNomeSottoUnita(nomeNuovo);
                    this.elencoUnita.put(unitaPadre.getNome(), unitaPadre);
                }

                this.elencoUnita.remove(nome);
                return true;
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return false;
        }


        @Override
        public MyRuolo creaRuolo(MyUnita unita, String nome)//quando creo un ruolo devo necessariamente assegnarlo ad un unità
        {
            MyRuolo ruolo = new MyRuolo();
            ruolo.setNomeRuolo(nome);
            try {
                if (!elencoUnita.containsKey(unita.getNome())) {
                    throw new NotDefinedElementException("Unita non presente nell'organigramma");
                }
                if(!elencoRuoli.keySet().isEmpty())
                {
                    for (MyRuolo ruoloI : elencoRuoli.keySet()) {
                        if ((ruoloI.getNome().equals(nome)) && !ruoloI.getNomeUnita().equals(unita.getNome()))//non posso inserire un ruolo già presente in un altra unità organizzativa
                        {
                            throw new AlredyExistingElementException("Esiste un ruolo con questo nome in un'altra entità.");
                        }
                    }
                }


                ruolo.setNomeUnita(unita.getNome());
                ruolo.setLivello(unita.getLivello());
                elencoRuoli.put(ruolo, false);
                MyUnita unitaAggiornata = unita;
                unitaAggiornata.aggiungiRuolo(ruolo);
                elencoUnita.remove(unita.getNome());
                elencoUnita.put(unitaAggiornata.getNome(), unitaAggiornata);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return ruolo;//se il ruolo è senza livello significa che non è stato assegnato
        }

        public boolean modificaNomeRuolo(String nomeVecchio, String nomeNuovo)
        {
            MyRuolo salvaRuolo=getRuolo(nomeVecchio);
            MyRuolo checkRuolo=getRuolo(nomeNuovo);
            if(salvaRuolo==null||checkRuolo!=null)
            {
                return false;
            }
            MyDipendente dipendente=null;
            if(elencoRuoli.get(salvaRuolo))
            {
                for (String cfX : elencoAssegnamentiDipendenti.keySet()) {
                    if (elencoAssegnamentiDipendenti.get(cfX).contains(salvaRuolo)) {
                        dipendente = cercaDipendente(cfX);
                        break;
                    }
                }
            }
            String nomeUnita=salvaRuolo.getNomeUnita();
            eliminaRuolo(salvaRuolo);
            MyUnita unita=elencoUnita.get(nomeUnita);
            MyRuolo ruoloAggiornato=creaRuolo(unita, nomeNuovo);
            if(dipendente!=null)
            {
                assegnaRuolo(dipendente,ruoloAggiornato);

            }
            return true;
        }

        protected void modificaNomeUnitaDelRuolo(MyRuolo ruolo,String nomeUnita)
        {
            MyRuolo ruoloAggiornato= ruolo;
            ruoloAggiornato.setNomeUnita(nomeUnita);
            Boolean assegnatoRuolo=elencoRuoli.get(ruolo);
            elencoRuoli.put(ruoloAggiornato,assegnatoRuolo);
            elencoRuoli.remove(ruolo);
        }
        @Override
        public MyDipendente creaDipendente(String nome, String cognome, char sesso, int giorno, int mese, int anno, String luogoNascita, String CF) {
            MyDipendente dipendente = new MyDipendente();
            dipendente.setNome(nome);
            dipendente.setCognome(cognome);
            dipendente.setSesso(sesso);
            dipendente.setDataNascita(giorno, mese, anno);
            dipendente.setLuogoNascita(luogoNascita);
            dipendente.setCodiceFiscale(CF);
            if (!elencoAssegnamentiDipendenti.containsKey(dipendente.getCodiceFiscale()))
            {
                elencoAssegnamentiDipendenti.put(dipendente.getCodiceFiscale(), new HashSet<MyRuolo>());
                elencoDipendenti.add(dipendente);
            }
            return dipendente;
        }

        public MyDipendente cercaDipendente(String cf)
        {
            try
            {
                if(cf.length()!=16)
                {
                    throw new IllegalArgumentException("Il codice fiscale deve essere di 16 caratteri.");
                }

                if(!elencoAssegnamentiDipendenti.containsKey(cf))
                {
                    throw new NotDefinedElementException("Non esiste dipendente con questo codice fiscale.");
                }
                for(MyDipendente dipendentex: elencoDipendenti)
                {
                    if(dipendentex.getCodiceFiscale().equals(cf))
                    {
                        return dipendentex;
                    }
                }

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }


        public boolean modificaCodiceFiscaleDipendente(String cf, String nuovoCf)
        {
            try
            {
                if(cercaDipendente(cf)==null)
                {

                }
                if(cercaDipendente(nuovoCf)!=null)
                {
                    throw new AlredyExistingElementException("Codice fiscale già presente");

                }

                MyDipendente vecchiaVersione=cercaDipendente(cf);
                HashSet<MyRuolo> ruoli= this.elencoAssegnamentiDipendenti.get(cf);
                elencoAssegnamentiDipendenti.remove(cf);
                elencoDipendenti.remove(vecchiaVersione);
                vecchiaVersione.setCodiceFiscale(nuovoCf);
                elencoAssegnamentiDipendenti.put(nuovoCf,ruoli);
                elencoDipendenti.add(vecchiaVersione);
                return true;


            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return false;
        }

    public boolean modificaNomeDipendente(String cf, String nuovoNome)
    {
        if(cercaDipendente(cf)==null)
            {
                return false;
            }

            MyDipendente vecchiaVersione=cercaDipendente(cf);
            elencoDipendenti.remove(vecchiaVersione);
            vecchiaVersione.setNome(nuovoNome);
            elencoDipendenti.add(vecchiaVersione);
            return true;


    }

    public boolean modificaCognomeDipendente(String cf, String nuovoCognome)
    {
            if(cercaDipendente(cf)==null)
            {
                return true;
            }

            MyDipendente vecchiaVersione=cercaDipendente(cf);
            elencoDipendenti.remove(vecchiaVersione);
            vecchiaVersione.setCognome(nuovoCognome);
            elencoDipendenti.add(vecchiaVersione);
            return true;
    }

    public boolean modificaDataNascitaDipendente(String cf, int gg, int m, int a)
    {
        if(cercaDipendente(cf)==null)
        {
            return false;
        }

        MyDipendente vecchiaVersione=cercaDipendente(cf);
        elencoDipendenti.remove(vecchiaVersione);
        vecchiaVersione.setDataNascita(gg,m,a);
        elencoDipendenti.add(vecchiaVersione);
        return true;


    }

    public boolean modificaLuogoNascitaDipendente(String cf, String nuovoLuogo)
    {
        if(cercaDipendente(cf)==null)
        {
            return false;
        }

        MyDipendente vecchiaVersione=cercaDipendente(cf);
        elencoDipendenti.remove(vecchiaVersione);
        vecchiaVersione.setLuogoNascita(nuovoLuogo);
        elencoDipendenti.add(vecchiaVersione);
        return true;


    }

    public boolean modificaSessoDipendente(String cf, char Sesso)
    {
        if(cercaDipendente(cf)==null)
        {
            return false;
        }

        MyDipendente vecchiaVersione=cercaDipendente(cf);
        elencoDipendenti.remove(vecchiaVersione);
        vecchiaVersione.setSesso(Sesso);
        elencoDipendenti.add(vecchiaVersione);
        return true;


    }



    public void assegnaRuolo(MyDipendente dipendente, MyRuolo ruolo)
    {
            try {
                HashSet<MyRuolo> ruoliAggiornati = elencoAssegnamentiDipendenti.get(dipendente.getCodiceFiscale());
                int livelloRuoloNuovo = ruolo.getLivello();

                if (!elencoAssegnamentiDipendenti.containsKey(dipendente.getCodiceFiscale())) {
                    throw new NotDefinedElementException("Dipendente inesistente.");
                }

                if (!elencoRuoli.containsKey(ruolo)) {
                    throw new NotDefinedElementException("Ruolo non definito.");
                }

                if (elencoRuoli.get(ruolo))//se il booleano associato al ruolo è true significa che è stato già assegnato
                {
                    throw new AlredyAssignedElementException("Ruolo già assegnato.");
                }

                if (!checkRuoliDipendente(ruolo, ruoliAggiornati)) {
                    throw new IncompatibleElementException("Non è possibile assegnare questo ruolo al dipendente, ruolo già assegnato o non compatibilie a quelli già presenti");
                }

                ruoliAggiornati.add(ruolo);
                elencoAssegnamentiDipendenti.put(dipendente.getCodiceFiscale(), ruoliAggiornati);
                elencoRuoli.put(ruolo, true);
            } catch (Exception e) {
                e.printStackTrace();
            }

    }


    protected boolean checkRuoliDipendente(MyRuolo ruolo, Collection<MyRuolo> ruoli)//I ruoli assegnati al dipendente devono essere compatibili con quelli già svolti
        {
            if (ruoli.isEmpty()) {
                return true;
            }
            String nomeUnitaRuolo = ruolo.getNomeUnita();
            MyUnita unitaPadreRuolo = elencoUnita.get(nomeUnitaRuolo);
            for (MyRuolo ruoloX : ruoli) {
                if (ruoloX.getNome().equals(ruolo.getNome()))// non ha senso inserire un ruolo già presente
                {
                    return false;
                }
                boolean stessaUnita = ruoloX.getNomeUnita().equals(nomeUnitaRuolo);
                boolean unitaSuperiore = unitaPadreRuolo.getNomiSottoUnita().contains(ruoloX.getNomeUnita());
                if (!stessaUnita && !unitaSuperiore) {
                    return false;
                }
            }
            return true;


        }

    public MyUnita cercaUnitaPadre(String nomeUnita)
    {

            MyUnita unitaFiglia = elencoUnita.get(nomeUnita);
            try {
                if (!elencoUnita.containsKey(nomeUnita)) {
                    throw new NotDefinedElementException("L'unità che stai cercando non è stata ancora definita");
                }
                for (MyUnita unita : elencoUnita.values()) {
                    if (unitaFiglia.getLivello() - unita.getLivello() == 1 && unita.getNomiSottoUnita().contains(nomeUnita)) {
                        return unita;
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;//un unità null significa che l'unità non è figlia di nessuno e/o trattasi di unità padre all'apice
    }

    public void deAssegnaRuolo(MyDipendente dipendente, MyRuolo ruolo)
    {
            try {
                if (!elencoAssegnamentiDipendenti.containsKey(dipendente.getCodiceFiscale()))
                {
                    throw new NoSuchElementException("Dipendente inesistente.");
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            HashSet<MyRuolo> elencoRuoliDipendenteX = elencoAssegnamentiDipendenti.get(dipendente.getCodiceFiscale());
            if (!elencoRuoliDipendenteX.contains(ruolo))
            {
                return;
            }
            elencoRuoliDipendenteX.remove(ruolo);
            elencoAssegnamentiDipendenti.put(dipendente.getCodiceFiscale(), elencoRuoliDipendenteX);
            elencoRuoli.put(ruolo, false);
    }


    public void deAssegnaRuoliDipendente(MyDipendente dipendente, Collection<MyRuolo> ruoli)
    {
            HashSet<MyRuolo> elencoRuoliDipendenteX = elencoAssegnamentiDipendenti.get(dipendente.getCodiceFiscale());
            elencoRuoliDipendenteX.removeAll(ruoli);
            for (MyRuolo ruolo : ruoli) {
                if (elencoRuoli.containsKey(ruolo))    //devo effettuare questo controllo perchè altrimenti mi potrebbe creare altri ruoli inassegnati
                {
                    elencoRuoli.put(ruolo, false);
                }
            }
    }


    public void deassegnaTuttiRuoliDipendenti(HashSet<MyDipendente> dipendenti)
    {
            if (elencoRuoli.isEmpty() || !(elencoRuoli.containsValue(true))) {
                return;
            }
            for (MyDipendente dipendente : dipendenti) {
                HashSet<MyRuolo> elencoRuoliDipendenteX = elencoAssegnamentiDipendenti.get(dipendente.getCodiceFiscale());
                deAssegnaRuoliDipendente(dipendente, elencoRuoliDipendenteX);
            }

    }


    public void deAssegnaTuttiIRuoli()
    {
        for(String CFx: elencoAssegnamentiDipendenti.keySet())
        {
            elencoAssegnamentiDipendenti.put(CFx, new HashSet<MyRuolo>());
        }
        for(MyRuolo ruoloX: elencoRuoli.keySet())
        {
            elencoRuoli.put(ruoloX,false);
        }
    }


    public void eliminaUnita(MyUnita unita)
    {
            try
            {
                if(!elencoUnita.containsKey(unita.getNome())) {
                    return;
                }
                if(unita.getNome().equals(this.padre.getNome()))
                {
                    eliminaTutteLeUnita();
                    return;
                }
                HashSet<String> sottoUnita=unita.getNomiSottoUnita();
                eliminaElencoRuoli(unita.getRuoli());
                elencoUnita.remove(unita.getNome());


                if(!sottoUnita.isEmpty())
                {
                    for(String nomeSottoUnitaX: sottoUnita)
                    {
                        MyUnita sottoUnitaX=elencoUnita.get(nomeSottoUnitaX);
                        eliminaUnita(sottoUnitaX);
                    }
                }

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

    }

    public void eliminaTutteLeUnita()
    {
            this.padre=null;
            Set<String> elencoDipendenti=this.elencoAssegnamentiDipendenti.keySet();
            for(String cf:elencoDipendenti)
            {
                elencoAssegnamentiDipendenti.put(cf,new HashSet<>());
            }//deassegna tutti i ruoli dai dipendenti

            this.elencoUnita=new HashMap<>();
            this.elencoRuoli=new HashMap<>();

    }

    public void eliminaRuolo(MyRuolo ruolo)
    {
            if (elencoRuoli.isEmpty()) {
                return;
            }
            if (!elencoRuoli.containsKey(ruolo)) {
                return;
            }
            for (String cf : elencoAssegnamentiDipendenti.keySet()) {

                if (elencoAssegnamentiDipendenti.get(cf).contains(ruolo)) {
                    HashSet<MyRuolo> ruoliAggiornati = elencoAssegnamentiDipendenti.get(cf);
                    ruoliAggiornati.remove(ruolo);
                    elencoAssegnamentiDipendenti.put(cf, ruoliAggiornati);
                    break;
                }
            }
            for (String nomeUnita : elencoUnita.keySet()) {
                if (elencoUnita.get(nomeUnita).getLivello() == ruolo.getLivello())
                {
                    if (elencoUnita.get(nomeUnita).getRuoli().contains(ruolo)) {
                        MyUnita unitaAggiornata = elencoUnita.get(nomeUnita);
                        unitaAggiornata.rimuoviRuolo(ruolo);
                        elencoUnita.put(nomeUnita, unitaAggiornata);
                        elencoRuoli.remove(ruolo);
                        return;
                    }
                }
            }

        }


        public void eliminaElencoRuoli(Set <MyRuolo> ruoli)
        {
            for (MyRuolo ruolo : ruoli) {
                eliminaRuolo(ruolo);
            }
        }


        public void eliminaDipendente(MyDipendente dipendente) {
            if (!elencoDipendenti.contains(dipendente)) {
                return;
            }

            HashSet<MyRuolo> ruoliDaDeassegnare = elencoAssegnamentiDipendenti.get(dipendente.getCodiceFiscale());
            elencoAssegnamentiDipendenti.remove(dipendente.getCodiceFiscale());
            for (MyRuolo ruolo : ruoliDaDeassegnare) {
                elencoRuoli.put(ruolo, false);
            }
            elencoDipendenti.remove(dipendente);
        }


        public void eliminaElencoDipendenti(Collection<MyDipendente> dipendenti) {

            for (MyDipendente dipendenteX : dipendenti) {
                eliminaDipendente(dipendenteX);
            }
        }

        public HashSet<MyDipendente> getElencoDipendenti() {
            return elencoDipendenti;
        }

        public HashMap<MyRuolo, Boolean> getElencoRuoli() {
            return this.elencoRuoli;
        }

        public HashMap<String, HashSet<MyRuolo>> getAssegnamentiDipendenti() {
            return this.elencoAssegnamentiDipendenti;
        }

        public MyUnita getUnita(String nomeUnita)
        {
            if(elencoUnita.containsKey(nomeUnita))
            {
                return elencoUnita.get(nomeUnita);
            }
            return null;
        }

        public MyUnita getUnitaPadre()
        {
            return this.padre;
        }

        public HashMap<String,MyUnita> getElencoUnita()
        {
            return this.elencoUnita;
        }
        public MyRuolo getRuolo(String nomeRuolo)
        {

            for(MyRuolo ruolo: elencoRuoli.keySet())
            {
                if(ruolo.getNome().equals(nomeRuolo))
                {
                    return ruolo;
                }

            }

           return null;
        }


        public MyDipendente getDipendente(String CF)
        {
            try
            {
                if (CF.length() != 16) {
                    throw new IllegalArgumentException("Il codice fiscale fornito non è conforme.");
                }
                StringBuilder sb = new StringBuilder();

                for (MyDipendente dipendenteX : elencoDipendenti)
                {
                    if (dipendenteX.getCodiceFiscale().equals(CF))
                    {
                        return dipendenteX;

                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }



}

