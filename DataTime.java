
public class DataTime {
	private static CensusData census;
	public static void main(String[] args){
		census = PopulationQuery.parse("CenPop2010.txt");
		version3PreProcessing();
	}
	
	public static void version2FindCorners(){
		System.out.println("Version 2");
		Version2 ver = new Version2(census,1);
		ver.findUSCorners();
		for(int i = 1; i < 80001; i = i + 2000){
			long startTime = System.nanoTime();
			ver = new Version2(census,i);
			Rectangle rec = ver.findUSCorners();
			//ver.calculateGrid(rec, 100, 500, 1, 1, 100, 500);
			long endTime = System.nanoTime();
	        long duration = endTime - startTime;
	        System.out.println("Time Needed: " + duration + " nanoseconds, cutoff " +i);
		}
	}
	
	public static void version3FindCorners(){
		Version3 ver = new Version3(census);
		ver.findUSCorners();
		System.out.println("Version 3");
		long startTime = System.nanoTime();
		ver = new Version3(census);
		ver.findUSCorners();
		long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println("Time Needed: " + duration + " nanoseconds");
	}
	
	public static void version1PreProcessing(){
		int j = 25;
		Version1 ver = new Version1(census);
		Rectangle rec = ver.findUSCorners();
		ver.calculateGrid(rec, 100, 500, 1, 1, 100, 500);
		for(int i = 5 ; i <= 100; i = i + 5){
			long startTime = System.nanoTime();
			ver = new Version1(census);
			rec = ver.findUSCorners();
			ver.calculateGrid(rec, 100, 500, 1, 1, i, j);
			j = j + 25;
			long endTime = System.nanoTime();
	        long duration = endTime - startTime;
	        System.out.println("Time Needed: " + duration + " nanoseconds");
		}
	}
	
	public static void version3PreProcessing(){
		int j = 25;
		Version3 ver = new Version3(census);
		Rectangle rec = ver.findUSCorners();
		ver.calculateGrid(rec, 100, 500, 1, 1, 100, 500);
		for(int i = 5 ; i <= 100; i = i + 5){
			long startTime = System.nanoTime();
			ver = new Version3(census);
			rec = ver.findUSCorners();
			ver.calculateGrid(rec, 100, 500, 1, 1, i, j);
			j = j + 25;
			long endTime = System.nanoTime();
	        long duration = endTime - startTime;
	        System.out.println("Time Needed: " + duration + " nanoseconds");
		}
	}
}
