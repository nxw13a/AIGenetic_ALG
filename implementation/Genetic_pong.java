package implementation;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.Timer;

public class Genetic_pong implements ActionListener//, KeyListener
{

	public static Genetic_pong pong;
	public int width = 400 + 15, height = 400;
	public Genetic_render g_render;
	public Genetic_pad pad;
	public Genetic_ball ball;
	public Random random;
	public boolean left, right;
	public boolean pong1 = false;
	public JFrame jframe;
	public Genetic gen;
	public int x = 0;
	public Genetic_pong()
	{

		Timer timer = new Timer(20, this);
		random = new Random();
		jframe = new JFrame("Genetic");
		g_render = new Genetic_render();
		gen = new Genetic();
		jframe.setSize(width,height);
		jframe.setVisible(true);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.add(g_render);
		//jframe.addKeyListener(this);
		pad = new Genetic_pad(this, gen);
		ball = new Genetic_ball(this, gen);
		timer.start();
	}
	
	public void start()
	{
		pad = new Genetic_pad(this, gen);
		ball = new Genetic_ball(this, gen);
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
		ball.update(pad);
		
	}
	public void render(Graphics2D g)
	{
		g.setColor(Color.BLACK);
		g.fillRect(0,0,width,height);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		pad.render(g);
		g.setFont(new Font("Arial", 1, 12));
		g.drawString("Generation: "+gen.generation +"                                                                                 HIT: "+gen.hit+"/"+gen.bounce, 0, 10);
		ball.render(g);

	}
	public static void main(String[] args)
	{
 		pong = new Genetic_pong();
	}

	//@Override
	public void actionPerformed(ActionEvent e)
	{
		//System.out.println(gen.hit);
		if(gen.record && (x < gen.number_of_moves) && gen.move.get(gen.hit) != "" && gen.hit < gen.bounce)
		{
			//gen.begin();
			char hold = gen.move.get(gen.hit).charAt(x);
			//char hold = example.get(gen.hit).charAt(x);
			
			//System.out.println(gen.hit);
			//System.out.println(gen.move.get(gen.hit));
			
			//System.out.println(hold);
			if(hold == '1')
			{
				left = true;
				right = false;
				update();
		 		g_render.repaint();
			}
			else{
			    right = true;
			    left = false;
			    update();
		 		g_render.repaint();
			}
		
			x++;
		}
		else
		{
			//System.out.println("MOCE");
			if(gen.hit < gen.bounce)
			{
				right = false;
				left = false;

				update();
			 	g_render.repaint();
			 	if(this.pong1)
				{
					//gen.begin();
					x = 0;
					this.pong1 = false;
				}
			}
			//x++;
		}
	}

	/*
	@Override
	public void keyPressed(KeyEvent e)
	{
		int id = e.getKeyCode();
		if(id == KeyEvent.VK_LEFT)
		{
			//System.out.println("LEFT");
			left = true;
		}
		else if(id == KeyEvent.VK_RIGHT)
		{
			//System.out.println("RIGHT");
			right = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		int id = e.getKeyCode();
		if(id == KeyEvent.VK_LEFT)
		{
			left = false;
		}
		else if(id == KeyEvent.VK_RIGHT)
		{
			right = false;
		}
	}


	@Override
	public void keyTyped(KeyEvent e)
	{

	}
	*/
}