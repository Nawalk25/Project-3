
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
		return 0;
	}


	@Override
	public Rectangle findUSCorners() {
		Version1 ver = new Version1(input);
		return ver.findUSCorners();
	}
	

}
