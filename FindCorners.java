import java.util.concurrent.*;

@SuppressWarnings("serial")
public class FindCorners extends RecursiveTask<Rectangle> {
	
	CensusGroup[] usData;
	int hi; int lo;
	float top; float bottom; float left; float right;
	FindCorners(CensusGroup[] fileInput, int l, int h) {
		usData = fileInput;
		hi = h; lo = l;
		top = usData[0].latitude; bottom = usData[0].latitude;
		left = usData[0].longitude; right = usData[0].longitude;
	}
	
	
	protected Rectangle compute() {
		if(hi-lo < 12) {
			for(int i = lo ; i < hi; i++){
				top = Math.max(usData[i].latitude, top);
				bottom = Math.min(usData[i].latitude, bottom);
				right = Math.max(usData[i].longitude, right);
				left = Math.min(usData[i].longitude, left);
			}
			return new Rectangle(left, right, top, bottom);
		} else {
			FindCorners left = new FindCorners(usData, lo, (hi+lo)/2);
			FindCorners right = new FindCorners(usData, (hi+lo)/2, hi);
			left.fork();
			Rectangle rightAns = right.compute();
			Rectangle leftAns = left.join();
			
			float leftTemp = Math.min(rightAns.left, leftAns.left);
			float rightTemp = Math.max(rightAns.right, leftAns.right);
			float topTemp = Math.max(rightAns.top, leftAns.top);
			float bottomTemp = Math.min(rightAns.bottom, leftAns.bottom);
			
			return new Rectangle(leftTemp, rightTemp, topTemp, bottomTemp);

		}
	}

}