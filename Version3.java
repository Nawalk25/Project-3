
public class Version3 implements Processors{
	public CensusData input;
	public int size;
	
	public Version3(CensusData input){
		this.input = input;
		size = input.data_size;
	}


	@Override
	public int calculateGrid(Rectangle big, int x, int y, int west, int south, int east, int north) {
		int[][] grid = new int[x][y];
		CensusGroup[] data = input.data;
		float spacingX = (big.right - big.left)/(x-1);
		float spacingY = (big.top - big.bottom)/(y-1);
		for(int m = 0 ; m < size; m++){
			int j = (int) Math.round((data[m].latitude-big.bottom)/spacingY);
			int i = (int) Math.round((data[m].longitude-big.left)/spacingX);
			grid[i][j] += data[m].population;
		}
		for(int i = 0 ; i < grid.length ; i++){
			for(int j = 0 ; j < grid[i].length ; j++){
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
		return grid[east-1][south-1]- grid[east-1][north-1] - grid[west-1][south-1] +
				grid[west-1][north-1];
	}


	@Override
	public Rectangle findUSCorners() {
		Version1 ver = new Version1(input);
		return ver.findUSCorners();
	}
	

}
