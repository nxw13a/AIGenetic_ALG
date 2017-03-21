package implementation;

import java.awt.Graphics;
import java.awt.Color;

public class Genetic_pad
{
	public int x, y, height = 10, width = 50;
	public Genetic_pad(Genetic_pong pong)
	{
		this.x = pong.width / 2 - this.width / 2;
		this.y = pong.height - this.height;
	}
	public void render(Graphics g)
	{
		g.setColor(Color.WHITE);
		g.fillRect(x,y,width,height);
	}
	public void move(boolean dir)
	{
		int speed = 10;
		if(dir)
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
			if(x + width + speed < implementation.Genetic_pong.width)
			{
				x+=speed;
			}
			else
			{
				x = implementation.Genetic_pong.width - width;
			} 
		}
	}
}