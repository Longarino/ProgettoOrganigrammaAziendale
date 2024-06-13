import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class GraficoAlbero extends JPanel
{
    private HashMap<String, MyUnita> elencoUnita;
    private MyUnita padre;
    private int gapVerticale=100;

    public GraficoAlbero(HashMap<String, MyUnita> elencoUnita, MyUnita padre)
    {
        this.elencoUnita = elencoUnita;
        this.padre=padre;
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if(elencoUnita != null && !elencoUnita.isEmpty())
        {
           disegnaGraficoAlbero(g, this.padre, getWidth()/2, 20, getWidth()/4);
        }
    }

    protected void disegnaGraficoAlbero(Graphics g, MyUnita unita, int x, int y,int  offsetOrizzontale)
    {
        if(unita==null)
        {
            return;
        }
        BoxUnita box=new BoxUnita(unita.getNome(), x, y, g);
        box.disegna(g);

        int yFiglio= y+box.getAltezza()+ gapVerticale;
        HashSet<String> figliHashSet= unita.getNomiSottoUnita();
        ArrayList<String> figli= new ArrayList<String>(figliHashSet);
        Collections.sort(figli);
        int numFigli= figli.size();
        int xFiglio = x-(numFigli-1)* offsetOrizzontale/2;


        for(String nomeFiglio:figli)
        {
            MyUnita figlio= elencoUnita.get(nomeFiglio);
            if(figlio==null)
            {
                continue;
            }
            BoxUnita boxFiglio= new BoxUnita(figlio.getNome(), xFiglio, yFiglio, g);
            Linea linea= new Linea(box, boxFiglio);
            linea.disegna(g);
            disegnaGraficoAlbero(g, figlio, xFiglio, yFiglio, offsetOrizzontale/2);
            xFiglio+= offsetOrizzontale;
        }
    }

    protected void setPadre(MyUnita padre)
    {
        this.padre=padre;
    }

    protected void setElencoUnita(HashMap<String, MyUnita> elencoUnita)
    {
        this.elencoUnita=elencoUnita;
    }

}
