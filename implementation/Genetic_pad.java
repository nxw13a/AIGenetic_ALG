package implementation;

import java.awt.Graphics;
import java.awt.Color;

public class Genetic_pad
{
	public int x, y, height = 10, width = 50;
	private Genetic gen;

	public Genetic_pad(Genetic_pong pong, Genetic gen)
	{
		this.gen = gen;
		this.x = pong.width / 2 - this.width / 2;
		this.y = pong.height - this.height;
		gen.position.add(this.x);
	}
	public void render(Graphics g)
	{
		g.setColor(Color.WHITE);
		g.fillRect(x,y,width,height);
	}
	public void move(boolean left)
	{
		int speed = 6;
		if(left)
		{
			if(x - speed > 0)
			{
				x -= speed;
			}
			else
			{
				x = 0;
			}
		}
		else
		{
			if(x + width + speed < Genetic_pong.pong.width)
			{
				x+=speed;
			}
			else
			{
				x = Genetic_pong.pong.width - width;
			} 
		}
	}
}