public interface AbstractFactoryOrganigramma
{
    public abstract MyUnita creaUnita(String nome);// crea un unita padre

    public abstract boolean aggiungiSottoUnita(MyUnita unita,MyUnita sottoUnita);

    public abstract MyRuolo creaRuolo(MyUnita unita, String nome);

    public abstract MyDipendente creaDipendente(String nome, String cognome, char sesso, int giorno, int mese, int anno, String luogoNascita, String CF);
}
