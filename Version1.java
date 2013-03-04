
public class Version1 {
	
	CensusGroup[] usData;
	int size;
	// usRectangle.left = smaller longitude (horizontal)
	// usRectangle.bottom = smaller latitude (vertical)
	Rectangle usRectangle;
	public Version1(CensusData fileInput){
		usData = fileInput.data;
		size = fileInput.data_size;
	}
	
	/**
	 * Make the Rectangle grid of all USA
	 * @return Rectangle of all USA
	 */
	public void findUSCorners(){
		usRectangle.bottom = usData[0].latitude; usRectangle.top = usData[0].latitude;
		usRectangle.left = usData[0].longitude; usRectangle.right = usData[0].longitude;
		for(int i = 0 ; i < size; i++){
			usRectangle.top = Math.max(usData[i].latitude, usRectangle.top);
			usRectangle.bottom = Math.min(usData[i].latitude, usRectangle.bottom);
			usRectangle.right = Math.max(usData[i].longitude, usRectangle.right);
			usRectangle.left = Math.min(usData[i].longitude, usRectangle.left);
		}
	}
	
	
}
