
public class Version1 {
	/**
	 * Parse census groups from the filename
	 * 
	 * @param filename name of the file
	 * @return array of census group
	 */
	public static CensusGroup[] parseCensusData(String filename){
		CensusData all = PopulationQuery.parse(filename);
		return all.data;
	}
	/**
	 * Make the Rectangle grid of all USA
	 * 
	 * @param allGroups all census groups data
	 * @param x number of rectangle from west to east
	 * @param y number of rectangle from south to north
	 * @return Rectangle of all USA
	 */
	public static Rectangle[][] makeGrid(CensusGroup[] allGroups, int x, int y){
		float maxLatitude = allGroups[0].latitude;
		float maxLongitude = allGroups[0].longitude;
		float minLatitude = allGroups[0].latitude;
		float minLongitude = allGroups[0].longitude;
		for(int i = 0 ; i < allGroups.length; i++){
			maxLatitude = Math.max(allGroups[i].latitude, maxLatitude);
			maxLongitude = Math.max(allGroups[i].longitude,maxLongitude);
			minLatitude = Math.min(allGroups[i].latitude, minLatitude);
			minLongitude = Math.min(allGroups[i].longitude, minLongitude);
		}
		Rectangle[][] usa = new Rectangle[x][y];
		float xGrid = (maxLongitude - minLongitude)/x;
		float yGrid = (maxLatitude - minLatitude)/y;
		for(int i = 0; i < x; i++){
			for(int j = 0; j < y ;j++){
				usa[i][j] = new Rectangle(minLongitude + i*xGrid, minLongitude +(i+1)*xGrid,
						minLatitude + j*yGrid, minLatitude + (j+1)*yGrid);
			}
		}
		return usa;
	}
	
	
}
