package implementation;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class Genetic_render extends JPanel
{

	private static final long serialVersionUID = 1L;

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Genetic_pong.pong.render((Graphics2D) g);
	}
}