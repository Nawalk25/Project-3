
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
		float spacingX = (big.right - big.left)/(x);
		float spacingY = (big.top - big.bottom)/(y);
		for(int m = 0 ; m < size; m++){
			int j = 0;
			int i = 0;
			if(data[m].latitude == big.top){
				j = y-1;
			}else{
				j = (int) Math.floor((data[m].latitude-big.bottom)/spacingY);
			}
			if(data[m].longitude == big.right){
				i = x-1;;
			}else{
				i = (int) Math.floor((data[m].longitude-big.left)/spacingX);
			}
			grid[i][j] += data[m].population;
		}
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
		return grid[east-1][south-1]- topLeft - bottomLeft + topRight;
	}


	@Override
	public Rectangle findUSCorners() {
		Version1 ver = new Version1(input);
		return ver.findUSCorners();
	}
	

}
