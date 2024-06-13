import java.awt.*;

public class Linea implements GraficaOrganigramma
{
    private BoxUnita da, a;

    public Linea(BoxUnita da, BoxUnita a) {
        this.da = da;
        this.a = a;
    }

    public void disegna(Graphics g) {
        int x1 = da.getX() + da.getLarghezza() / 2;
        int y1 = da.getY() + da.getAltezza();
        int x2 = a.getX() + a.getLarghezza() / 2;
        int y2 = a.getY();
        g.drawLine(x1, y1, x2, y2);
    }
}
