/**
 * CSE 332, Section AD, Project 3
 * Lokita Metta Yaputra, Maria Angela Suhardi
 * 
 * Version 5 is exactly the same as version 4 but instead of adding the left grid to the right
 * grid, in version 5 we use 1 shared grid
 * 
 */

import java.util.concurrent.locks.ReentrantLock;


public class Version5 implements Processors{
	private CensusData data;
	public int size;
	
	public Version5(CensusData data){
		this.data = data;
		size = data.data_size;
	}
	
	/**
	 * Use 4 thread to make grid with population in each grid
	 * using shared grid & shared lock to keep the grid changed at the same time
	 * @return total Population inside the rectangle made by x,y,w,s 
	 */
	@Override
	public int calculateGrid(Rectangle rect, int x, int y, int w, int s,
			int e, int n) {
		int[][] grid = new int[x][y];
		ReentrantLock[][] lk = new ReentrantLock[x][y];
		for(int i = 0; i < x; i++){
			for(int j = 0; j < y; j++){
				lk[i][j] = new ReentrantLock();
			}
		}
		
		int lo = 0;
		int hi = size;
		Result param = new Result(data.data, x, y, findUSCorners());
		MakeGridFifthVersion first = new MakeGridFifthVersion(lo,(hi+lo)/4,param,grid, lk);
		MakeGridFifthVersion second = new MakeGridFifthVersion((hi+lo)/4,(hi+lo)/2,param,grid, lk);
		MakeGridFifthVersion third = new MakeGridFifthVersion((hi+lo)/2,(hi+lo)*3/4,param,grid, lk);
		MakeGridFifthVersion fourth = new MakeGridFifthVersion((hi+lo)*3/4,hi,param,grid, lk);
		first.start();
		second.start();
		third.start();
		fourth.run();
		try {
			first.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		try {
			second.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		try {
			third.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		Version3 ver = new Version3(data);
		return ver.queryRect(grid, x, y, w, s, e, n);
	}

	/**
	 * Make the Rectangle grid of all USA (find 4 corners of the USA rectangle)
	 * @return Rectangle of all USA
	 */
	@Override
	public Rectangle findUSCorners() {
		Version2 ver = new Version2(data);
		return ver.findUSCorners();
	}

}
