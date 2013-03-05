import java.util.concurrent.*;

@SuppressWarnings("serial")
public class CalculateGridSecondVersion extends RecursiveTask<Integer>{
	private int lo;
	private int hi;
	private CensusGroup[] data;
	private Rectangle rt;
	
	public CalculateGridSecondVersion(int lo, int hi, CensusGroup[] data, Rectangle rt){
		this.lo = lo;
		this.hi = hi;
		this.data = data;
		this.rt = rt;
	}
	protected Integer compute(){
		if(hi - lo <= 5){
			int ans = 0;
			for(int i = lo; i < hi ;i++){
				if(data[i].longitude >= rt.left && data[i].longitude <= rt.right &&
					data[i].latitude <= rt.top && data[i].latitude >= rt.bottom){
					ans+= data[i].population;
				}
			}
			return ans;
		}else{
			CalculateGridSecondVersion left = new CalculateGridSecondVersion(lo,(lo+hi)/2,data,rt);
			CalculateGridSecondVersion right = new CalculateGridSecondVersion((lo+hi)/2,hi,data,rt);
			left.fork();
			int rightAns = right.compute();
			int leftAns = left.join();
			return rightAns + leftAns;
		}
	}
	
}