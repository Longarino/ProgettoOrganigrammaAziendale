import java.io.Serializable;
import java.time.LocalDate;


public class MyDipendente implements Dipendente, Serializable
{
    private String nome, cognome;
    private char sesso;
    private LocalDate dataNascita;
    private String luogoNascita;
    private String CodiceFiscale;




    @Override
    public void setNome(String nome)
    {
        this.nome = nome;
    }



    @Override
    public void setCognome(String cognome)
    {
        this.cognome = cognome;
    }



    @Override
    public void setSesso(char sesso)
    {
        if(sesso!= 'M' && sesso!='F')
        {
            throw new IllegalArgumentException("Dato non valido. Digitare a caratteri cubitali M per maschio, F per femina.");
        }
        this.sesso=sesso;
    }

    @Override
    public void setDataNascita(int giorno, int mese, int anno)
    {
        try
        {
            this.dataNascita = LocalDate.of(anno, mese, giorno);
        }
        catch(Exception e)
        {

            throw new IllegalArgumentException("I dati inseriti per la data di nascita non sono validi.");
        }

    }



    @Override
    public void setLuogoNascita(String luogo)
    {
        this.luogoNascita=luogo;
    }



    @Override
    public void setCodiceFiscale(String CF)
    {
        if(!checkCF(CF))
        {
            throw new IllegalArgumentException("Codice fiscale non valido. Riprovare.");
        }
        this.CodiceFiscale=CF;

    }



    private static boolean checkCF(String cf)
    {
        return cf.length()==16;
    }



    @Override
    public String getNome()
    {
        return this.nome;
    }



    @Override
    public String getCognome() {
        return this.cognome;
    }



    @Override
    public char getSesso()
    {
        return this.sesso;
    }



    @Override
    public LocalDate getDataNascita()
    {
        return this.dataNascita;
    }



    @Override
    public String getLuogoNascita()
    {
        return this.luogoNascita;
    }



    @Override
    public String getCodiceFiscale()
    {
        return this.CodiceFiscale;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Nome: ").append(nome).append("\n");
        sb.append("Cognome: ").append(cognome).append("\n");
        sb.append("Sesso: ").append(sesso).append("\n");
        sb.append("Data di nascita: ").append(dataNascita.toString()).append("\n");
        sb.append("Luogo di nascita: ").append(luogoNascita).append("\n");
        sb.append("Codice Fiscale: ").append(CodiceFiscale).append("\n");
        return sb.toString();
    }


}
