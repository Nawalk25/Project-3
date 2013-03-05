import java.util.concurrent.*;
/**
 * This class find the corners of the rectangle
 * 
 */
@SuppressWarnings("serial")
public class FindCornersSecondVersion extends RecursiveTask<Rectangle> {

	private CensusGroup[] usData;
	private int hi; 
	private int lo;
	private float top; 
	private float bottom; 
	private float left; 
	private float right;

	/**
	 * Construct a new FindCornersSecondVersion
	 * @param fileInput file that contains census-group-block data
	 * @param l lower bound
	 * @param h upper bound
	 */
	public FindCornersSecondVersion(CensusGroup[] fileInput, int l, int h) {
		usData = fileInput;
		hi = h; lo = l;
		top = usData[0].latitude; bottom = usData[0].latitude;
		left = usData[0].longitude; right = usData[0].longitude;
	}
	/**
	 * Compute the corners of the rectangle
	 */
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
			FindCornersSecondVersion left = new FindCornersSecondVersion(usData, lo, (hi+lo)/2);
			FindCornersSecondVersion right = new FindCornersSecondVersion(usData, (hi+lo)/2, hi);
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