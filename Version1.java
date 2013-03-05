
public class Version1 implements Processors {
	
	public CensusGroup[] usData;
	public int size;
	
	public Version1(CensusData fileInput){
		usData = fileInput.data;
		size = fileInput.data_size;
	}
	
	/**
	 * Make the Rectangle grid of all USA
	 * @return Rectangle of all USA
	 */
	public Rectangle findUSCorners(){
		Rectangle usRectangle = new Rectangle(usData[0].longitude, usData[0].longitude, usData[0].latitude,
				usData[0].latitude);
		for(int i = 1 ; i < size; i++){
			usRectangle.top = Math.max(usData[i].latitude, usRectangle.top);
			usRectangle.bottom = Math.min(usData[i].latitude, usRectangle.bottom);
			usRectangle.right = Math.max(usData[i].longitude, usRectangle.right);
			usRectangle.left = Math.min(usData[i].longitude, usRectangle.left);
		}
		return usRectangle;
	}
	
	public int calculateGrid(Rectangle big, int x, int y, int west, int south, int east, int north) {
		float xInterval = (big.right - big.left)/x;
		float yInterval = (big.top - big.bottom)/y;
		
		int totalPopulation = 0;
		
		float minLatitude = big.bottom + (south-1)* yInterval;
		float maxLatitude = big.bottom + north* yInterval;
		float minLongitude = big.left + (west-1)* xInterval;
		float maxLongitude = big.left + east* xInterval;
		for(int i=0; i < size; i++) {
			CensusGroup point = usData[i];
			if(point.latitude >= minLatitude && point.latitude < maxLatitude &&
				point.longitude >= minLongitude && point.longitude < maxLongitude) {
					totalPopulation += point.population;
			} else if((point.latitude == maxLatitude && maxLatitude == big.top) ||
				(point.longitude == maxLongitude && maxLongitude == big.right)) {
					totalPopulation += point.population;
			}
				
		}
		return totalPopulation;
	}
	
	
}
