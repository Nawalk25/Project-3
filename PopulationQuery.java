
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class PopulationQuery {
	// next four constants are relevant to parsing
	public static final int TOKENS_PER_LINE  = 7;
	public static final int POPULATION_INDEX = 4; // zero-based indices
	public static final int LATITUDE_INDEX   = 5;
	public static final int LONGITUDE_INDEX  = 6;
	
	// parse the input file into a large array held in a CensusData object
	public static CensusData parse(String filename) {
		CensusData result = new CensusData();
		
        try {
            BufferedReader fileIn = new BufferedReader(new FileReader(filename));
            
            // Skip the first line of the file
            // After that each line has 7 comma-separated numbers (see constants above)
            // We want to skip the first 4, the 5th is the population (an int)
            // and the 6th and 7th are latitude and longitude (floats)
            // If the population is 0, then the line has latitude and longitude of +.,-.
            // which cannot be parsed as floats, so that's a special case
            //   (we could fix this, but noisy data is a fact of life, more fun
            //    to process the real data as provided by the government)
            
            String oneLine = fileIn.readLine(); // skip the first line

            // read each subsequent line and add relevant data to a big array
            while ((oneLine = fileIn.readLine()) != null) {
                String[] tokens = oneLine.split(",");
                if(tokens.length != TOKENS_PER_LINE)
                	throw new NumberFormatException();
                int population = Integer.parseInt(tokens[POPULATION_INDEX]);
                if(population != 0)
                	result.add(population,
                			   Float.parseFloat(tokens[LATITUDE_INDEX]),
                		       Float.parseFloat(tokens[LONGITUDE_INDEX]));
            }

            fileIn.close();
        } catch(IOException ioe) {
            System.err.println("Error opening/reading/writing input or output file.");
            System.exit(1);
        } catch(NumberFormatException nfe) {
            System.err.println(nfe.toString());
            System.err.println("Error in file format");
            System.exit(1);
        }
        return result;
	}

	// argument 1: file name for input data: pass this to parse
	// argument 2: number of x-dimension buckets
	// argument 3: number of y-dimension buckets
	// argument 4: -v1, -v2, -v3, -v4, or -v5
	public static void main(String[] args) {
		if (args.length == 4) {
            CensusData data = parse(args[0]);
            String line = "";
            Scanner scan = new Scanner(System.in);
            while(!line.equals("exit")){
                System.out.println("Please give west, south, east, north coordinates of your query rectangle:");
                line = scan.nextLine();
	            String[] input = line.split(" ");
	            if(input.length == 4){
		            choices(args[3],data, Integer.parseInt(args[1]),Integer.parseInt(args[2]),Integer.parseInt(input[0])
		            		,Integer.parseInt(input[1]),Integer.parseInt(input[2]),Integer.parseInt(input[3]));
	            }
            }
            scan.close();
        }else {
        	System.err.println("Usage: filename of document to analyze");
        	System.exit(1);
        }
	}
	
	public static void choices(String choice, CensusData data, int x, int y,int west, int south, int east, int north){
		checkInput(x,y,west,south,east,north);
		if(choice.equals("-v1")){
        	
        }else if(choice.equals("-v2")){
        	Version2 ver = new Version2(data);
        	Rectangle us = ver.findUSCorners();
        	int population = ver.calculateGrid(us, x, y, 1, x, 1, y);
        	int Qpopulation = ver.calculateGrid(us, x, y, west, east, south, north);
        	System.out.println("population of rectangle: " + Qpopulation);
        	double percentage = Qpopulation*100.0/population;
        	System.out.println("percent of total population: " + String.format("%.2f",percentage));
        }else if(choice.equals("-v3")){
        	System.out.println("not yet implemented");
        }else if(choice.equals("-v4")){
        	System.out.println("not yet implemented");
        }else {
        	System.out.println("invalid version");
        }
		
	}
	public static void checkInput(int x, int y, int west, int south, int east, int north){
		if(west < 1 || west > x || south < 1 || south > y || east < west || east > x
			|| north < south || north > y){
			throw new IllegalArgumentException();
		}
	}
	
}
