import java.util.*;

public interface Unita
{


    //setter
    public abstract void setNome(String nome);
    public abstract void setRuoli(HashSet<MyRuolo> ruoli);
    public abstract boolean aggiungiRuolo(MyRuolo ruolo);
    public abstract boolean rimuoviRuolo(MyRuolo ruolo);
    public abstract boolean rimuoviTuttiRuoli();
    public abstract boolean aggiungiNomeSottoUnita(String nome);
    public abstract boolean rimuoviSottoUnita(String nome);

    //getter
    public abstract String getNome();
    public abstract HashSet<MyRuolo> getRuoli();
    public abstract int getLivello();
    public abstract HashSet<String> getNomiSottoUnita();




}
