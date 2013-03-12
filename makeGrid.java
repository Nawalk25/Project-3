/**
 * CSE 332, Section AD, Project 3
 * Lokita Metta Yaputra, Maria Angela Suhardi
 * 
 * This is makeGrid class that help version 4 parallelize making grid
 * of the big rectangle
 * 
 */

import java.util.concurrent.*;

@SuppressWarnings("serial")
public class makeGrid extends RecursiveTask<int[][]> {
	
	int lo; int hi; Result info;

	makeGrid(int l, int h, Result data) {
		lo = l;
		hi = h;
		info = data;
	}
	
	@Override
	// making the overall grid with each grid contain population on that certain range of 
	// longitude & latitude with parallel method
	protected int[][] compute() {
		if(hi-lo < 100) {
			int[][] grid = new int[info.x][info.y];
			CensusGroup[] data = info.data;
			for(int m = lo ; m < hi; m++){
				int j = GetIndex.getIndexY(info.corners, info.y, data[m].latitude);
				int i = GetIndex.getIndexX(info.corners, info.x, data[m].longitude);
				grid[i][j] += data[m].population;
			}
			return grid;
 		} else {
 			makeGrid left = new makeGrid(lo,(hi+lo)/2, info);
 			makeGrid right = new makeGrid((hi+lo)/2, hi, info);
 			left.fork();
 			int[][] rightAns = right.compute();
 			int[][] leftAns = left.join();
 			
 			// added the population of the left grid and the right grid
 			int[][] combineAns = new int[info.x][info.y];
 			for(int row=0; row < info.y; row++) {
 				for(int column=0; column < info.x; column++) {
 					combineAns[column][row] = rightAns[column][row] + leftAns[column][row];
 				}
 			}
 			return combineAns;
 		}
		
	}
	
	
	

}
