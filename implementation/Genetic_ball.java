package implementation;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Genetic_ball
{

    public int x,y,width = 20,height = 20;
    public Random random;
    public int motionX, motionY;
    public int miss = 1;
    private Genetic_pong pong;
    private Genetic gen = new Genetic();

	public Genetic_ball(Genetic_pong pong, Genetic gen)
	{
		this.gen = gen;
		this.pong = pong;
		random = new Random();
		spawn();
	}

	public void update(Genetic_pad pad)
	{
		int speed = 3;

		this.x += motionX * speed;
		this.y += motionY * speed;
		if(this.x - width < pong.width || this.x + motionX > 0 )
		{
			if(this.x < 0)
			{
				this.x = 0;
				this.motionX = 1;
				if(motionX == 0)
				{
					motionX = 1;
				}

			}
			else if(this.x >= pong.width)
			{
				this.motionX = -1;
				this.x = pong.width - width;
				if(motionY == 0)
				{
					motionY = -1;
				}

			}
		}
		if(this.y + motionY < 0)
		{
			if (this.motionY < 0)
			{
				this.y = 0;
				this.motionY = 1;

				if (motionY == 0)
				{
					motionY = 1;
				}
			}
		}
		if((this.y + height - motionY) > pong.height)
		{
			if(gen.distance.size() < miss)
			{

				gen.distance.add(this.x);
				System.out.print(miss);
				gen.begin();
			}
			pad.x = pong.width / 2 - pad.width / 2;
			pad.y = pong.height - pad.height;
			spawn();
		}
		if(checkCollision(pad) == 1)
		{
			this.motionX = 1;
			this.motionY = -1;
			if (motionY == 0)
			{
				this.motionY = -1;
				miss++;

			}

		}

	}

public void spawn()
	{
		motionY = 0;
		this.x = 0;
		this.y = 0;

		this.motionX = -2 + 2;

		if (motionY == 0)
		{
			motionY = 1;
		}

		motionX = 1;
	}
	public int checkCollision(Genetic_pad pad)
	{
		if (this.y < pad.y + pad.height && this.y + height > pad.y && this.x < pad.x + pad.width && this.x + width > pad.x)
		{

			return 1; //bounce
		}
		return 0;
	}

	public void render(Graphics g)
	{
		g.setColor(Color.BLUE);
		g.fillOval(x,y,width,height);
	}

}