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
		if(hi-lo < 12) {
			for(int m = lo ; m < hi; m++){
				int j = 0;
				int i = 0;
				if(input[m].latitude == wholeRec.top){
					j = row-1;
				}else{
					j = (int) Math.floor((input[m].latitude-wholeRec.bottom)/yInt);
				}
				if(input[m].longitude == wholeRec.right){
					i = column-1;;
				}else{
					i = (int) Math.floor((input[m].longitude-wholeRec.left)/xInt);
				}
				rec[i][j] += input[m].population;
			}
 		} else {
 			makeGrid left = new makeGrid(lo,(hi+lo)/2, column, row, wholeRec, input, rec);
 			makeGrid right = new makeGrid((hi+lo)/2, hi, column, row, wholeRec, input, rec);
 			left.fork();
 			right.compute();
 			left.join();
 		}
		
	}
	
	
	

}
