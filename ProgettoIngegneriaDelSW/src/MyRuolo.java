import java.io.Serializable;

public class MyRuolo implements Ruolo, Serializable
{
    private String nome;
    private int livello;//default
    private String nomeUnita;




    @Override
    public void setNomeRuolo(String nome)
    {
        this.nome=nome;
    }



    protected void setLivello(int livello)//Il settaggio del livello del ruolo può essere gestito solo alla classe unità a cui è assegnato
    {
        this.livello=livello;
    }//DA VALUTARE

    protected void setNomeUnita(String nome)//Il settaggio del livello del nomeUnita può essere gestito solo alla classe unità a cui è assegnato
    {
        this.nomeUnita=nome;
    }


    @Override
    public String getNome()
    {
        return this.nome;
    }

    @Override
    public int getLivello()
    {
        return this.livello;
    }

    @Override
    public String getNomeUnita()
    {
        return this.nomeUnita;
    }


    @Override
    public int hashCode()
    {
        int result = 11;
        result = 31 * result + this.nome.hashCode();
        result = 31 * result + this.livello;
        result = 31 * result + this.nomeUnita.hashCode();
        return result;
    }

    @Override
    public String toString()
    {
        return "Nome: " + nome + ", Livello: " + livello+" ,Unità di appartenenza: "+ nomeUnita;
    }

}
