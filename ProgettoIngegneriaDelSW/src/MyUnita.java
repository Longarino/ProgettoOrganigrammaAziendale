import java.awt.*;
import java.io.Serializable;
import java.util.*;

public class MyUnita implements Unita,GraficaOrganigramma, Serializable
{
    private String nome;
    private HashSet<MyRuolo> ruoli;
    private int livello;
    private HashSet<String> nomiSottoUnita;
    private int x, y;
    private int padding = 10;


    public MyUnita()
    {
        this.ruoli=new HashSet<MyRuolo>();
        this.livello=-1;
        this.nomiSottoUnita= new HashSet<String>();


    }

    @Override
    public void setNome(String nome)
    {
        this.nome=nome;
    }



    protected void setLivello(int livello)
    {
        if(livello<0)
        {
            throw new IllegalArgumentException("Livello con valore negativo non valido.");
        }
        this.livello=livello;
    }
    @Override
    public void setRuoli(HashSet<MyRuolo> ruoli)
    {
        this.ruoli=ruoli;
    }

    @Override
    public boolean aggiungiRuolo(MyRuolo ruolo)
    {
        return ruoli.add(ruolo);
    }

    @Override
    public boolean rimuoviRuolo(MyRuolo ruolo)
    {
         return this.ruoli.remove(ruolo);
    }

    @Override
    public boolean rimuoviTuttiRuoli()
    {
        return this.ruoli.removeAll(this.ruoli);
    }

    @Override
    public boolean aggiungiNomeSottoUnita(String nome)
    {
        if(nomiSottoUnita.contains(nome))
        {
            return false;
        }
        nomiSottoUnita.add(nome);
        return true;
    }

    @Override
    public boolean rimuoviSottoUnita(String nome)
    {
        if(!nomiSottoUnita.contains(nome))
        {
            return false;
        }
        nomiSottoUnita.remove(nome);
        return true;
    }

    @Override
    public String getNome()
    {
        return this.nome;
    }

    @Override
    public HashSet<MyRuolo> getRuoli()
    {
        return this.ruoli;
    }

    @Override
    public int getLivello()
    {
        return this.livello;
    }

    @Override
    public HashSet<String> getNomiSottoUnita()
    {
        return this.nomiSottoUnita;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof MyUnita))
        {
            return false;
        }
        MyUnita unita = (MyUnita) o;
        return this.nome.equals(unita.getNome()) && this.ruoli.equals(unita.getRuoli()) && this.livello==(unita.getLivello());
    }

    @Override
    public int hashCode()
    {
        int result=11;
        result=17*this.nome.hashCode();
        result=17*result+this.ruoli.hashCode();
        return result;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Nome: ").append(nome).append("\n");
        sb.append("Livello: ").append(livello).append("\n");
        sb.append("Ruoli:\n");
        for (MyRuolo ruolo : ruoli)
        {
            sb.append("  - ").append(ruolo).append("\n");
        }
        sb.append("Nomi delle sotto-unità:\n");
        for (String nomeSottoUnita : nomiSottoUnita) {
            sb.append("  - ").append(nomeSottoUnita).append("\n");
        }
        return sb.toString();
    }

    protected void setCoordinate(int x,int y)
    {
        this.x=x;this.y=y;
    }


    @Override
    public void disegna(Graphics g)
    {
        Font originalFont = g.getFont();
        Font font = new Font("Arial", Font.BOLD, 12);
        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics(font);

        // Calcola le dimensioni del testo
        int stringWidth = metrics.stringWidth(nome);
        int stringHeight = metrics.getHeight();

        // Imposta la larghezza e l'altezza del rettangolo in base alle dimensioni del testo
        int width = stringWidth + 2 * padding;
        int height = stringHeight + 2 * padding;

        // Disegna il rettangolo
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);

        // Disegna il testo centrato nel rettangolo
        int textX = x + (width - stringWidth) / 2;
        int textY = y + ((height - stringHeight) / 2) + metrics.getAscent();
        g.drawString(nome, textX, textY);

        g.setFont(originalFont);


    }

}
