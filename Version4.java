/**
 * CSE 332, Section AD, Project 3
 * Lokita Metta Yaputra, Maria Angela Suhardi
 * 
 * This is the Version 1 (sequential computing) of computing total population & percentage of 
 * the US population of certain area.
 * 
 */

import java.util.concurrent.*;

public class Version4 extends Processors {
	
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
		Rectangle usRectangle = new Rectangle(usData[0].longitude, usData[0].longitude, usData[0].latitude,
				usData[0].latitude);
		for(int i = 1 ; i < size; i++){
			usRectangle.top = Math.max(usData[i].latitude, usRectangle.top);
			usRectangle.bottom = Math.min(usData[i].latitude, usRectangle.bottom);
			usRectangle.right = Math.max(usData[i].longitude, usRectangle.right);
			usRectangle.left = Math.min(usData[i].longitude, usRectangle.left);
		}
		return usRectangle;
	}
	
	/**
	 * calculate the total population asked in the query (query is determined by 4 arguments
	 * given by user : west, south, east, and north)
	 * @param west-most column, south-most row, east-most column, north-most row of the area being asked
	 * @return total population in the query rectangle
	 */
	
	
	static final ForkJoinPool fjPool = new ForkJoinPool();
	
	@Override
	public int calculateGrid(Rectangle rec, int column, int row, int w, int s, int e, int n) {
		final ForkJoinPool fjPool = new ForkJoinPool(); 
		int[][] ans = new int[column][row];
		fjPool.invoke(new makeGrid(0, size, column, row, rec, usData, ans));
		
		Version3 ver = new Version3(census);
		return ver.queryRect(ans, column, row, w, s, e, n);
	}


	

	
	
}
