/**
 * CSE 332, Section AD, Project 3
 * Lokita Metta Yaputra, Maria Angela Suhardi
 * This is the third version for counting census population in some rectangle
 * in the map. This version using sequential and grid calculation
 *
 */
public class Version3 implements Processors{
	public CensusData input;
	public int size;
	
	public Version3(CensusData input){
		this.input = input;
		size = input.data_size;
	}
	
	/**
	 * Make the grid and calculate the grid
	 * @param big the rectangle that covers all area
	 * @param x number of columns
	 * @param y number of rows
	 * @param west the left border of the query rectangle
	 * @param south the bottom border of the query rectangle
	 * @param east the right border of the query rectangle
	 * @param north the top border of the query rectangle
	 * @return the population of the query rectangle
	 */
	@Override
	public int calculateGrid(Rectangle big, int x, int y, int west, int south, int east, int north) {
		int[][] grid = new int[x][y];
		CensusGroup[] data = input.data;
		for(int m = 0 ; m < size; m++){
			int j = GetIndex.getIndexY(big, y, data[m].latitude);
			int i = GetIndex.getIndexX(big, x, data[m].longitude);
			grid[i][j] += data[m].population;
		}
		return queryRect(grid,x, y, west,south,east,north);
	}
	
	/**
	 * Calculate the population of the query rectangle
	 * @param grid the array of population
	 * @param x number of columns
	 * @param y number of rows
	 * @param west the left border of the query rectangle
	 * @param south the bottom border of the query rectangle
	 * @param east the right border of the query rectangle
	 * @param north the top border of the query rectangle
	 * @return the population of the query rectangle
	 */
	public int queryRect(int[][] grid,int x, int y, int west, int south, int east, int north){
		//update the grid so every grid hold the total data of the population for that position
		for(int i = 0 ; i < grid.length ; i++){
			for(int j = grid[i].length-1 ; j >= 0 ; j--){
				int up = 0;
				int left = 0;
				int diag = 0;
				if(j + 1 <= grid[i].length-1){
					up = grid[i][j+1];
				}
				if(i - 1 >= 0){
					left = grid[i-1][j];
				}
				if((j + 1 <= grid[i].length -1) && (i - 1 >= 0)){
					diag = grid[i-1][j+1];
				}
				grid[i][j] += up + left - diag;
			}
		}
		//Calculate the total population in the query rectangle
		int topLeft = 0;
		int bottomLeft = 0;
		int topRight = 0;
		if(north < y){
			topRight = grid[east-1][north];
		}
		if(west - 2 >= 0){
			bottomLeft = grid[west-2][south-1];
		}
		if((west-2>=0)&&(north < y)){
			topLeft = grid[west-2][north];
		}
		return grid[east-1][south-1]- topRight - bottomLeft + topLeft;
	}

	/**
	 * Find the four corners and make a rectangle
	 * @return rectangle that include the four corners
	 */
	@Override
	public Rectangle findUSCorners() {
		Version1 ver = new Version1(input);
		return ver.findUSCorners();
	}
	

}
