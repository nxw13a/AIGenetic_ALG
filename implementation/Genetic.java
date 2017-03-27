package implementation;

import java.io.Console;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.lang.StringBuilder;
import java.util.ArrayList;
import java.util.Random;
import java.security.SecureRandom;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;


public class Genetic{

	public ArrayList<Integer>  distance = new ArrayList<Integer>();
	public ArrayList<Integer> position = new ArrayList<Integer>();
	public ArrayList<String> move = new ArrayList<String>();
	private ArrayList<String> population = new ArrayList<String>();
	private ArrayList<Integer> fitness = new ArrayList<Integer>();
	private ArrayList<String> best_sgeneration = new ArrayList<String>();
	public int bounce = 10;
	public int generation = 0;
	private int total_fitness = 0;
	public int hit = 0;
	public boolean record = false;
	private int population_size = 100;
	public int number_of_moves = 100;
	public int highest = 0;
	//public int position;



	private int test_move(String id)
	{
		int temp = position.get(hit);
		for(int x = 0; x < number_of_moves; x++)
		{
			if(id.charAt(x) == '1')
			{
				temp -= 6;
			}
			else
			{
				temp += 6;
			}
		}
		return temp;
	}

	
	public Genetic()
	{
		for(int x = 0; x < bounce + 1; x++)
		{
			position.add(-1);
			distance.add(-1);
			best_sgeneration.add("");
			move.add("");
		}
	}
	public void begin()
	{
		record = true;
		if(hit > highest)
		{
			highest = hit;
		}
		this.createPopulation();
		this.testPopulation();
		//System.out.println(population);
		this.best_sgeneration.set(highest,this.population.get(this.getBestSolution()));
		move.set(hit, best_sgeneration.get(highest));
		//System.out.println(highest);
		//System.out.println(move);
		//System.out.println(population);
		//System.out.println(position);
		this.population.clear();
		//this.best_sgeneration.clear();
		generation++;
		//System.out.println(generation);
		//record = false;
	}

	private void createPopulation() {
		for(int x = 0; x < population_size; x++)
		{
			//calls makeGene() one for each element position
			population.add(makeGene());
		}
	}

	private void testPopulation() {
		total_fitness = 0;
		for(int x = 0; x < population_size; x++) {

			int temp = evalGene(population.get(x));
			fitness.add(temp);
			total_fitness = total_fitness + temp;
		}
	}

	private int evalGene(String gene)
	{
		int hold = test_move(gene);
		int cool = distance.get(highest) - hold;

		return ((cool < 0) ? cool * -1 : cool);
	}
	private String makeGene() {

		StringBuilder gene = new StringBuilder(number_of_moves);

		char c;

		for(int x = 0; x < number_of_moves; x++) {
			Random r = new SecureRandom();
			c = ((r.nextInt(100) < 50) ? '0' : '1');
			gene.append(c);
		}
		return gene.toString();
	}
		private int getBestSolution() {
		int t_fitness = 0;
		int b_fitness = 1000;
		int best_pos = 0;

		for(int i = 0; i < population_size; i++) {
			t_fitness = evalGene(population.get(i));
			if(t_fitness <= b_fitness)
			{
				b_fitness = t_fitness;
				best_pos = i;
			}
			else if(t_fitness == 0)
			{
				return i;
			}

		}
		return best_pos;
	}


}
