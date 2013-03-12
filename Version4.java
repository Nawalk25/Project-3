/**
 * CSE 332, Section AD, Project 3
 * Lokita Metta Yaputra, Maria Angela Suhardi
 * 
 * This is the Version 1 (sequential computing) of computing total population & percentage of 
 * the US population of certain area.
 * 
 */

import java.util.concurrent.*;

public class Version4 implements Processors {
	
	// file being read is parsed then saved in usData 
	public CensusGroup[] usData;
	public CensusData census;
	// the size of the file
	public int size;
	
	public Version4(CensusData fileInput){
		census = fileInput;
		usData = fileInput.data;
		size = fileInput.data_size;
	}
	
	/**
	 * Make the Rectangle grid of all USA (find 4 corners of the USA rectangle)
	 * @return Rectangle of all USA
	 */
	@Override
	public Rectangle findUSCorners(){
		Version1 ver = new Version1(census);
		return ver.findUSCorners();
	}
	
	/**
	 * calculate the total population asked in the query (query is determined by 4 arguments
	 * given by user : west, south, east, and north)
	 * @param west-most column, south-most row, east-most column, north-most row of the area being asked
	 * @return total population in the query rectangle
	 */
	
	
	@Override
	public int calculateGrid(Rectangle rec, int x, int y, int west, int south, int east, int north) {
		final ForkJoinPool fjPool = new ForkJoinPool(); 
		Result param = new Result(usData, x, y, findUSCorners());
		int[][] ans = fjPool.invoke(new makeGrid(0, size, param));
		
		Version3 ver = new Version3(census);
		return ver.queryRect(ans, x, y, west, south, east, north);
	}


	

	
	
}
