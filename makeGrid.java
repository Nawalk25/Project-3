import java.util.concurrent.*;

@SuppressWarnings("serial")
public class makeGrid extends RecursiveTask<int[][]> {
	
	int lo; int hi; Result info;
	// range of 'l' until 'h' of array 'a' wanted to mapped into bit vector
	// bitsArray is the array of bit vector of the input array
	makeGrid(int l, int h, Result data) {
		lo = l;
		hi = h;
		info = data;
	}
	
	@Override
	protected int[][] compute() {
		if(hi-lo < 12) {
			int[][] grid = new int[info.x][info.y];
			CensusGroup[] data = info.data;
			float spacingX = (info.corners.right - info.corners.left)/(info.x);
			float spacingY = (info.corners.top - info.corners.bottom)/(info.y);
			for(int m = lo ; m < hi; m++){
				int j = 0;
				int i = 0;
				if(data[m].latitude == info.corners.top){
					j = info.y-1;
				}else{
					j = (int) Math.floor((data[m].latitude-info.corners.bottom)/spacingY);
				}
				if(data[m].longitude == info.corners.right){
					i = info.x-1;
				}else{
					i = (int) Math.floor((data[m].longitude-info.corners.left)/spacingX);
				}
				grid[i][j] += data[m].population;
			}
			return grid;
 		} else {
 			makeGrid left = new makeGrid(lo,(hi+lo)/2, info);
 			makeGrid right = new makeGrid((hi+lo)/2, hi, info);
 			left.fork();
 			int[][] rightAns = right.compute();
 			int[][] leftAns = left.join();
 			
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
