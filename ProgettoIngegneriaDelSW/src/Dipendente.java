import java.time.LocalDate;

public interface Dipendente

{
    public abstract void setNome(String nome);
    public abstract void setCognome(String cognome);
    public abstract void setSesso(char sesso);
    public abstract void setDataNascita(int giorno, int mese, int anno);
    public abstract void setLuogoNascita(String luogo);
    public void setCodiceFiscale(String CF);


    public abstract String getNome();
    public abstract String getCognome();
    public abstract char getSesso();
    public abstract LocalDate getDataNascita();
    public abstract String getLuogoNascita();
    public abstract String getCodiceFiscale();

}
