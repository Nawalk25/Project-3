/**
 * CSE 332, Section AD, Project 3
 * Lokita Metta Yaputra, Maria Angela Suhardi
 * 
 * A helper class for version 5 that make thread and sum up population in each grid
 * inside the big rectangle
 * 
 */

import java.util.concurrent.locks.ReentrantLock;


public class MakeGridFifthVersion extends java.lang.Thread{
	private int lo;
	private int hi;
	public int[][] grid;
	private ReentrantLock[][] lk;
	public Result info;
	
	public MakeGridFifthVersion(int lo, int hi, Result data, int[][] grid, ReentrantLock[][] lock){
		this.lo = lo; this.hi = hi; 
		this.grid = grid; lk = lock;
		info = data;
	}

	/**
	 * to prevent changing the data of the grid at the same time
	 * @param i (column)
	 * @param j (row)
	 * @param population
	 */
	public void update(int i, int j, int population){
		synchronized(lk[i][j]){
			grid[i][j] += population;
		}
	}
	
	/**
	 * rules for putting total population inside the shared grid & also adding the
	 * population to the grid without changing the grid all at the same time
	 */
	public void run(){
		Rectangle rect = info.corners;
		CensusGroup[] data = info.data;
		for(int m = lo ; m < hi; m++){
			int j = GetIndex.getIndexY(rect, info.y, data[m].latitude);
			int i = GetIndex.getIndexX(rect, info.x, data[m].longitude);
			update(i,j,data[m].population);
		}
	}
}
