package implementation;

import java.io.Console;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.lang.StringBuilder;
import java.util.ArrayList;
import java.util.Random;


class Genetic{

	private int population_size = 0;
	private int number_of_items = 0;
	private int generation_counter = 1;
	private int crossover_c = 0;
	private static int fitness_count = 0;
	private double total_fitness = 0;
	private double knapsack_capacity = 0;
	private double total_fgeneration = 0;

	private ArrayList<String> population = new ArrayList<String>();
	private ArrayList<Double> fitness = new ArrayList<Double>();
	private ArrayList<Double> cost_of_items = new ArrayList<Double>();
	private ArrayList<Double> value_of_items = new ArrayList<Double>();
	private ArrayList<String> best_sgeneration = new ArrayList<String>();
	private ArrayList<Double> mean_fgeneration = new ArrayList<Double>();
	private ArrayList<String> breed_population = new ArrayList<String>();

	public static void main(String[] args)
	{
		//System.out.println("Hello this James");
		Genetic gen = new Genetic();
		System.out.println("\nThe fitness function was used: " + return_fit_count());
	}

	public Genetic() {
		this.getInput();

		this.knapsack();
	}
	public void knapsack() {

		this.createPopulation();

		this.testPopulation(); //Eval fitness of initial population
		//Find best solution of generation
		this.best_sgeneration.add(this.population.get(this.getBestSolution()));

		//Find mean solution of generation
		this.mean_fgeneration.add(this.getMeanFitness());

		makeFutherGenerations();
	}

	private void getInput() {
		double[] value = {3,2,5,4,3,7,6,1,5,4,3,2,5,4,3,7,6,1,5,4};
		double[] weight = {2,3,1,1,4,5,3,5,1,2,2,3,1,1,4,5,3,5,1,2};
		number_of_items = 20;
		for(int x = 0; x < number_of_items; x++)
		{
			value_of_items.add(value[x]);
			cost_of_items.add(weight[x]);
		}
		knapsack_capacity = 20;
		population_size = 100;

	}
	private void makeFutherGenerations()
	{
		int i = 1;
		long startTime = System.currentTimeMillis(); //fetch starting time
		 while(false||(System.currentTimeMillis()-startTime)<600000) {

			if((i > 4)) {

				double a = this.mean_fgeneration.get(i-1);
				double b = this.mean_fgeneration.get(i-2);
				double c = this.mean_fgeneration.get(i-3);

				if(a==b && b==c) {
					System.out.println("\nGeneration: " + (i + 1));
					System.out.println("Best solution of "+(i+1)+" generation: " + this.best_sgeneration.get(0));
					System.out.println("Value: " + this.add_up(best_sgeneration.get(0), value_of_items));
					System.out.println("Cost: " + this.add_up(best_sgeneration.get(0), cost_of_items));
					System.out.println("Time: " + (((System.currentTimeMillis()-startTime))*.001));
					break;
				}
			}

			this.crossover_c = 0;

			for(int x = 0; x < this.population_size / 2; x++) {
				this.breedPopulation();
			}

			this.fitness.clear();

			this.evalBreedPopulation();

			 for(int k = 0; k < this.breed_population.size(); k++) {
                this.population.set(k, this.breed_population.get(k));
            }

            //convergence of organisms 20% chance of mutation
            if(this.all_similar(this.population))
			{
				double n = Math.random()*(double)this.population.size();
				int number = (int) n;
				String hold = this.population.get(number);
				this.population.set(number, this.population.get(0));
				this.population.set(0,hold);
				for(int y = 1; y < this.population.size(); y++)
				{
					String mgene = population.get(y);
					String new_mgene;
					for(int x = 1; x < number_of_items; x++)
					{
						double w_gene = Math.random() * 100;
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

			this.best_sgeneration.add(this.population.get(this.getBestSolution()));

			//Find mean solution of generation
			this.mean_fgeneration.add(this.getMeanFitness());


			i++;
		}
	}

	private void evalBreedPopulation() {
		fitness_count += 1;
		total_fgeneration = 0;
		for(int i = 0; i < breed_population.size() ; i++) {
			double temp_f = evalGene(breed_population.get(i));
			fitness.add(temp_f);
			total_fgeneration = total_fgeneration + temp_f;
		}
	}

	private void breedPopulation() {
		int gene1;
		int gene2;

		generation_counter = generation_counter + 1;

		if(population_size % 2 == 1) {
			breed_population.add(best_sgeneration.get(generation_counter - 1));
		}

		double n = Math.random()*(breed_population.size()); //static population size
		int number = (int) n;
		if((number > 0) && (number < breed_population.size()))
		{
			//System.out.println(number + " " + breed_population.size());
			breed_population.remove(number);
		}


		gene1 = selectGene();
		gene2 = selectGene();

		crossoverGenes(gene1,gene2);

	}

	private int selectGene() {

		double rand = Math.random() * total_fgeneration;

		for(int x = 0; x < population_size; x++) {
			if(rand <= fitness.get(x)) {
				return x;
			}
			rand = rand - fitness.get(x);
		}

		return 0;
	}

	private void mutateGene(int number) {

		String mgene = breed_population.get(breed_population.size() - number);
		String new_mgene;
		for(int x = 0; x < number_of_items; x++)
		{
			double w_gene = Math.random() * 1000;
			if(w_gene <= 5) 
			{
				char c = ((mgene.charAt(x) == '1') ? '0' : '1');
				new_mgene = mgene.substring(0,x) + c + mgene.substring(x+1);
				breed_population.set(breed_population.size() - 1, new_mgene);
			}
		}	

	}

	private void crossoverGenes(int gene1, int gene2) {
		String new_gene1;
		String new_gene2;

		double rand_crossover = Math.random();

		crossover_c = crossover_c + 1;
		Random generator = new Random();
		int cross_point = generator.nextInt(number_of_items) + 1;

		new_gene1 = population.get(gene1).substring(0, cross_point) + population.get(gene2).substring(cross_point);
		new_gene2 = population.get(gene2).substring(0, cross_point) + population.get(gene1).substring(cross_point);
		
		breed_population.add(new_gene1);
		breed_population.add(new_gene2);

		mutateGene(1);
		mutateGene(2);
	}

	private void createPopulation() {
		for(int x = 0; x < population_size; x++)
		{
			//calls makeGene() one for each element position
			population.add(makeGene());
		}
	}

	private String makeGene() {

		StringBuilder gene = new StringBuilder(number_of_items);

		char c;

		for(int x = 0; x < number_of_items; x++) {
			
			double rnd = Math.random();
			c = ((rnd > 0.5) ? '0' : '1');
			gene.append(c);

		}

		return gene.toString();
	}

	private void testPopulation() {
		total_fitness = 0;

		for(int x = 0; x < population_size; x++) {

			double temp = evalGene(population.get(x));
			fitness.add(temp);
			total_fitness = total_fitness + temp;
		}
	}

	private double evalGene(String gene) {
		double total_cost = 0;
		double total_value = 0;
		double fitness_value = 0;
		double diff = 0;
		char c = '0';

		for(int x = 0; x < number_of_items; x++) {
			c = gene.charAt(x);

			if(c == '1') {
				total_cost = total_cost + cost_of_items.get(x);
				total_value = total_value + value_of_items.get(x);
			}
		}

		diff = knapsack_capacity - total_cost;

		return ((diff < 0) ? fitness_value : total_value);

	}

	private int getBestSolution() {
		int best_pos = 0;
		double t_fitness = 0;
		double b_fitness = 0;

		for(int i = 0; i < population_size; i++) {
			t_fitness = evalGene(population.get(i));
			if(t_fitness > b_fitness) {
				b_fitness = t_fitness;
				best_pos = i;
			}
			else if (t_fitness == b_fitness)
			{
				if(add_up(population.get(best_pos), cost_of_items) > add_up(population.get(i),cost_of_items))
				{
					b_fitness = t_fitness;
					best_pos = i;
				}
			}
		}
		return best_pos;
	}

	private double getMeanFitness() {
		double t_fitness = 0;

		for(int x = 0; x < fitness.size(); x++) {
			t_fitness = t_fitness + fitness.get(x);
		}

		return (double)(t_fitness / population_size);
	}

	private double add_up(String bit, ArrayList<Double> item)
	{
		double total = 0;
		for(int x = 0; x < item.size(); x++)
		{
			total += ((bit.charAt(x) == '1') ? item.get(x) : 0);
		}
		return total;
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
	private static int return_fit_count()
	{
		return fitness_count;
	}

}
