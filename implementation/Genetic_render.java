package implementation;

import java.awt.Graphics;
import javax.swing.JPanel;

public class Genetic_render extends JPanel
{

	private static final long serialVersionUID = 1L;

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		implementation.Genetic_pong.render(g);
	}
}