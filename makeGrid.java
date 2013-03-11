import java.util.concurrent.*;

@SuppressWarnings("serial")
public class makeGrid extends RecursiveAction {
	
	int lo; int hi; int[][]rec; float xInt; float yInt; CensusGroup[] input;
	int column; int row; Rectangle wholeRec;
	// range of 'l' until 'h' of array 'a' wanted to mapped into bit vector
	// bitsArray is the array of bit vector of the input array
	makeGrid(int l, int h, int x, int y, Rectangle bigRec, CensusGroup[] popData, int[][] gridRec) {
		rec = gridRec; lo = l; hi = h;
		yInt = (bigRec.top - bigRec.bottom)/y;
		xInt = (bigRec.right - bigRec.left)/x;
		input = popData;
		column = x; row = y;
		wholeRec = bigRec;
	}

	
	@Override
	protected void compute() {
		if(hi-lo < input.length) {
			for(int i=lo; i < hi; i++) {
				CensusGroup point = input[i];
				int y = (int) ((point.latitude - wholeRec.bottom) / yInt);
				int x = (int) ((point.longitude - wholeRec.left) / xInt);
				if(x == column)
					x--;
				if(y == row)
					y--;
				rec[row - y - 1][x] += point.population;
	
			}
 		} else {
 			makeGrid left = new makeGrid(lo, (hi+lo)/2, column, row, wholeRec, input, rec);
 			makeGrid right = new makeGrid((hi+lo)/2, hi, column, row, wholeRec, input, rec);
 			left.fork();
 			right.compute();
 			left.join();
 		}
		
	}
	
	
	

}
