
public class Version1 {

	public static void makeGrid(String filename, int x, int y){
		CensusData all = PopulationQuery.parse(filename);
		CensusGroup[] allGroups = all.data;
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
	}
}
