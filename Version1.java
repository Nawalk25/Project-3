
public class Version1 implements Processors {
	
	public CensusGroup[] usData;
	public int size;
	// usRectangle.left = smaller longitude (horizontal)
	// usRectangle.bottom = smaller latitude (vertical)
	Rectangle usRectangle; int column; int row;
	float xInterval; float yInterval;
	int allPopulation;
	public Version1(CensusData fileInput){
		usData = fileInput.data;
		size = fileInput.data_size;
		allPopulation = 0;
	}
	
	/**
	 * Make the Rectangle grid of all USA
	 * @return Rectangle of all USA
	 */
	public void findUSCorners(){
		usRectangle = new Rectangle(usData[0].longitude, usData[0].longitude, usData[0].latitude,
				usData[0].latitude);
		for(int i = 1 ; i < size; i++){
			allPopulation += usData[i].population;
			usRectangle.top = Math.max(usData[i].latitude, usRectangle.top);
			usRectangle.bottom = Math.min(usData[i].latitude, usRectangle.bottom);
			usRectangle.right = Math.max(usData[i].longitude, usRectangle.right);
			usRectangle.left = Math.min(usData[i].longitude, usRectangle.left);
		}
	}
	
	public void divideRecToGrid(int x, int y) {
		column = x;
		row = y;
		xInterval = (usRectangle.right - usRectangle.left)/x;
		yInterval = (usRectangle.top - usRectangle.bottom)/y;
	}
	
	public int totalPopulation(int w, int s, int e, int n) {
		int totalPopulation = 0;
		if(w < 1 || w > column ||
		   s < 1 || s > row ||
		   e < w || e > column ||
		   n < s || n > row) {
			System.err.println("input exceeding the limit of the rectangle");
			System.exit(1);
		} else {
			float minLatitude = usRectangle.bottom + (s-1)* yInterval;
			float maxLatitude = usRectangle.bottom + n* yInterval;
			float minLongitude = usRectangle.left + (w-1)* xInterval;
			float maxLongitude = usRectangle.left + e* xInterval;
			for(int i=0; i < size; i++) {
				CensusGroup point = usData[i];
				if(point.latitude >= minLatitude && point.latitude < maxLatitude &&
				   point.longitude >= minLongitude && point.longitude < maxLongitude) {
					totalPopulation += point.population;
				} else if((point.latitude == maxLatitude && maxLatitude == usRectangle.top) ||
					   (point.longitude == maxLongitude && maxLongitude == usRectangle.right)) {
						totalPopulation += point.population;
					
				}
				
			}
		}
		return totalPopulation;
	}
	
	public float percentage(int w, int s, int e, int n) {
		return ((float)totalPopulation(w,s,e,n)/(float)totalPopulation(1,1,20,25))*100;
	}
	
	
}
