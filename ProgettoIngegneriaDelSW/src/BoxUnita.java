import java.awt.*;

public class BoxUnita implements GraficaOrganigramma
{
    private Font font;
    private String nome;
    private int x,y, larghezza,altezza;
    private int padding = 10;

    public BoxUnita(String nome, int x, int y, Graphics g)
    {

        this.nome = nome;
        this.font=new Font("Serif", Font.BOLD, 14);
        g.setFont(font);
        FontMetrics fm=g.getFontMetrics(font);
        this.larghezza=fm.stringWidth(nome)+20;// Padding di 10 px a sinistra e a destra
        this.altezza=fm.getHeight()+10;// Padding di 5 px in alto e in basso
        this.x=x- this.larghezza/2;// Centra il rettangolo orizzontalmente rispetto a x
        this.y=y;
    }


    public void disegna(Graphics g)
    {
       g.drawRect(x,y,larghezza,altezza);
       FontMetrics fm=g.getFontMetrics();
       int larghezzaTesto= fm.stringWidth(this.nome);
       int altezzaTesto=fm.getAscent();
       int xTesto=x+(this.larghezza-larghezzaTesto)/2;
       int yTesto=y+(this.altezza-altezzaTesto)/2+fm.getAscent();// Aggiustamento per centrare verticalmente
        Font f= new Font("Serif", Font.PLAIN, 14);
        g.setFont(f);
        g.drawString(nome, xTesto, yTesto);

    }

    public int getX()
    {
        return this.x;
    }

    public int getY()
    {
        return this.y;
    }

    public int getLarghezza()
    {
        return this.larghezza;
    }

    public int getAltezza()
    {
        return this.altezza;
    }

}
