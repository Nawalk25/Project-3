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
			processData(parse(args[0]),args[1],args[2],args[3]);
        }else {
        	System.err.println("Usage: filename of document to analyze");
        	System.exit(1);
        }
	}
	/**
	 * Check the if the user input for border query valid
	 * @param x columns
	 * @param y rows
	 * @param west left of the query rectangle
	 * @param south bottom of the query rectangle
	 * @param east right of the query rectangle 
	 * @param north upper of the query rectangle
	 */
	public static void checkInput(int x, int y, int west, int south, int east, int north){
		if(west < 1 || west > x || south < 1 || south > y || east < west || east > x
			|| north < south || north > y){
			throw new IllegalArgumentException();
		}
	}
	/**
	 * Process the data using the version that user input
	 * @param data CensusData that requires
	 * @param x columns
	 * @param y rows
	 * @param version that user input
	 */
	public static void processData(CensusData data, String x, String y, String version) {
		int column = Integer.parseInt(x);
		int row = Integer.parseInt(y);
		Processors processor = null;
		if(version.equals("-v1")) {
			processor = new Version1(data);
		} else if(version.equals("-v2")) {
			processor = new Version2(data);
		} else if(version.equals("-v3")) {
			processor = new Version3(data);
		} else if(version.equals("-v4")) {
			System.err.println("Version not implemented yet");
			System.exit(1);
		} else {
			System.err.println("Error version not found");
			System.exit(1);
		}
    	
		Rectangle rec = processor.findUSCorners();
		
		// Ask for query
	    Scanner scan = new Scanner(System.in);
        String[] input = null;
        String line = "";
        while(!line.equals("exit")) {
        	System.out.println("Please give west, south, east, north coordinates of your query rectangle:");
            line = scan.nextLine();
            input = line.split(" ");
           
            if (input.length != 4) {
            	System.exit(1);
            }
            
            int w = Integer.parseInt(input[0]);	int s = Integer.parseInt(input[1]);
            int e = Integer.parseInt(input[2]); int n = Integer.parseInt(input[3]);
            
            checkInput(column, row, w, s, e, n);
            
            int totalPopulation = processor.calculateGrid(rec,column,row,w,s,e,n);
            System.out.println("population of rectangle: " + totalPopulation);
            
            float percentPopulation = ((float)totalPopulation/(float) processor.calculateGrid(rec,column,row,1,1, column,row))*100;
            System.out.println("percent of total population: " + (float)Math.round(percentPopulation * 100)/100);
        }
        scan.close();	
		
	}
}