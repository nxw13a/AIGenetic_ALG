package implementation;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Genetic_pong implements KeyListener
{

	public static Genetic_pong pong;
	public static int width = 300 + 15, height = 300;
	public Genetic_render g_render;
	public static Genetic_pad pad;
	public boolean left, right;
	public Genetic_pong()
	{
		JFrame jframe = new JFrame("Genetic");
		g_render = new Genetic_render();
		jframe.setSize(width,height);
		jframe.setVisible(true);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.add(g_render);
		jframe.addKeyListener(this);

		start();
	}
	
	public void start()
	{
		pad = new Genetic_pad(this);
	}
	public void update()
	{
		//System.out.println(left);
		if(left)
		{
			pad.move(true);
		}
		if(right)
		{
			pad.move(false);
		}
	}
	public static void render(Graphics g)
	{
		g.setColor(Color.BLACK);
		g.fillRect(0,0,width,height);
		pad.render(g);
	}
	public static void main(String[] args)
	{
 		pong = new Genetic_pong();
	}

	public void actionPerformed()
	{
		 update();
		 g_render.repaint();
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		int id = e.getKeyCode();
		if(id == KeyEvent.VK_LEFT)
		{
			//System.out.println("LEFT");
			left = true;
			actionPerformed();
		}
		else if(id == KeyEvent.VK_RIGHT)
		{
			//System.out.println("RIGHT");
			right = true;
			actionPerformed();
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		int id = e.getKeyCode();
		if(id == KeyEvent.VK_LEFT)
		{
			left = false;
			actionPerformed();
		}
		else if(id == KeyEvent.VK_RIGHT)
		{
			right = false;
			actionPerformed();
		}
	}


	@Override
	public void keyTyped(KeyEvent e)
	{

	}
}