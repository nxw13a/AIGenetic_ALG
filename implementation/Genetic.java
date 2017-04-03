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
	private ArrayList<String> breed_population = new ArrayList<String>();
	public int bounce = 10;
	public int generation = 0;
	private int total_fitness;
	private int total_fgeneration;
	public int hit = 0;
	private int same = -1;
	public boolean record = false;
	private int population_size =10000;
	public int number_of_moves = 100;
	public int highest = 0;
	//public int position;


	private int test_move(String id)
	{
		int temp = position.get(highest);
		//System.out.println(temp);
		for(int x = 0; x < number_of_moves; x++)
		{
			if(id.charAt(x) == '1')
			{
				temp -= 4;
			}
			else
			{
				temp += 4;
			}
		}
		return temp;
	}

	
	public Genetic()
	{
		for(int x = 0; x < bounce + 1; x++)
		{
			position.add(-1);
			distance.add(0);
			best_sgeneration.add("");
			move.add("");
		}

	}
	public void begin()
	{
		record = true;
		if(hit > highest)
		{
			//same = highest;
			highest = hit;

		}	
		//System.out.println(highest);
		//System.out.println(move);
		//System.out.println(population);
		//System.out.println(position);
		//this.best_sgeneration.clear();
		if(same != highest || same == -1)
		{
			//System.out.println("1");
			this.total_fitness = 0;
			this.total_fgeneration = 0;
			this.population.clear();
			this.createPopulation();
			this.testPopulation();
			//System.out.println(population);
			this.best_sgeneration.set(highest,this.population.get(this.getBestSolution()));
			move.set(highest, best_sgeneration.get(highest));
			same = highest;
		}
		else{
			//System.out.println("2");
			//System.out.println(best_sgeneration.size());
			makeGen();
			//System.out.println(population);
		}
		generation++;
		//System.out.println(move.get(highest));
		//record = false;
	}

	private void makeGen()
	{
		for(int x = 0; x < this.population_size / 2; x++){
			this.breedPopulation();
		}
		//System.out.println(fitness);
		this.fitness.clear();
		this.evalBreedPopulation();
		for(int k = 0; k < this.breed_population.size(); k++) {
                this.population.set(k, this.breed_population.get(k));
            }
        //System.out.println(population.get(0) + " " + population.get(1));

		 if(this.all_similar(this.population))
			{
				Random r = new SecureRandom();
				int n = r.nextInt(this.population.size());
				int number = (int) n;
				String hold = this.population.get(number);
				this.population.set(number, this.population.get(0));
				this.population.set(0,hold);
				for(int y = 1; y < this.population.size(); y++)
				{
					String mgene = population.get(y);
					String new_mgene;
					for(int x = 1; x < number_of_moves; x++)
					{
						Random a = new SecureRandom();
						int w_gene = a.nextInt(100);
						if(w_gene <= 20) 
						{
							char c = ((mgene.charAt(x) == '1') ? '0' : '1');
							new_mgene = mgene.substring(0,x) + c + mgene.substring(x+1);
							population.set(breed_population.size() - 1, new_mgene);
						}
					}
				}	
			}



		this.breed_population.clear();
		this.best_sgeneration.set(highest,this.population.get(this.getBestSolution()));
		move.set(highest, best_sgeneration.get(highest));
		int avg = 0;
		for(int x = 0; x < fitness.size(); x++)
			avg += fitness.get(x);
		avg /= fitness.size();
		/*
		if(avg <= 30)
		{
			this.population.clear();
			this.createPopulation();	
		}
		*/
	}	

	private void evalBreedPopulation() {
		//fitness_count += 1;
		total_fgeneration = 0;
		for(int i = 0; i < breed_population.size() ; i++) {
			int temp_f = evalGene(breed_population.get(i));
			fitness.add(temp_f);
			total_fgeneration = total_fgeneration + temp_f;
		}
	}

	private void breedPopulation() {
		int gene1;
		int gene2;

		//generation_counter = generation_counter + 1;

		if(population_size % 2 == 1) {
			breed_population.add(best_sgeneration.get(highest - 1));
		}

		/*
		double n = Math.random()*(breed_population.size()); //static population size
		int number = (int) n;
		//System.out.println(number);
		if((number > 0) && (number < breed_population.size()))
		{
			//System.out.println(number + " " + breed_population.size());
			breed_population.remove(number);
		}
		*/

		
		gene1 = selectGene();
		gene2 = selectGene();

		crossoverGenes(gene1,gene2);

	}


	private void crossoverGenes(int gene1, int gene2) {
		String new_gene1;
		String new_gene2;

		//crossover_c = crossover_c + 1;
		Random generator = new SecureRandom();
		int cross_point = generator.nextInt(number_of_moves) + 1;
		new_gene1 = population.get(gene1).substring(0, cross_point) + population.get(gene2).substring(cross_point);
		
		cross_point = generator.nextInt(number_of_moves) + 1;
		new_gene2 = population.get(gene2).substring(0, cross_point) + population.get(gene1).substring(cross_point);
		
		breed_population.add(new_gene1);
		breed_population.add(new_gene2);

		mutateGene(1);
		mutateGene(2);
	}


	private int selectGene() {
		
		//double r = Math.random() * (distance.get);
		//int rand = (int) r;
		//System.out.println(rand);
		Random r = new SecureRandom();
		int rand = r.nextInt(population.size());
		return rand;

	}

	private void mutateGene(int number) {

		String mgene = breed_population.get(breed_population.size() - number);
		String new_mgene;
		for(int x = 0; x < number_of_moves; x++)
		{
			Random a = new SecureRandom();
			int w_gene = a.nextInt(1000);
			if(w_gene <= 5) 
			{
				char c = ((mgene.charAt(x) == '1') ? '0' : '1');
				new_mgene = mgene.substring(0,x) + c + mgene.substring(x+1);
				breed_population.set(breed_population.size() - 1, new_mgene);
			}
		}	

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
		//System.out.println(distance.get(highest)+" - "+hold+" = "+cool );
		return ((cool < 0) ? cool * -1 : cool);
	}
	private String makeGene() {

		StringBuilder gene = new StringBuilder(number_of_moves);

		char c;
		for(int x = 0; x < number_of_moves; x++) {
			Random r = new SecureRandom();
			c = ((r.nextInt(100) > 50) ? '0' : '1');
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
	private boolean all_similar(ArrayList<String> item)
	{
		String hold = item.get(0);
		for(int x = 1; x < item.size(); x++)
		{
			if(item.get(x).equals(hold) == false)
				return false;
		}
		return true;
	}


}
