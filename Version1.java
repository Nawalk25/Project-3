
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
	
	/**
	 * Calculate the desired query population and all population
	 * 
	 * @param allGroups all census groups data
	 * @param usa all USA rectangle
	 * @param west left boundary of the query rectangle
	 * @param east east boundary of the query rectangle
	 * @param south bottom boundary of the query rectangle
	 * @param north top boundary of the query rectangle
	 * @return array that contains queryPopulation(array[0]) and allPopulation(array[1])
	 */
	public static int[] desiredGrid(CensusGroup[] allGroups, Rectangle[][] usa, int west, int east,
									int south, int north){
		//Four corners rectangle
		float left = usa[west-1][south-1].left;
		float bottom = usa[east-1][south-1].bottom;
		float up = usa[west-1][north-1].top;
		float right = usa[east-1][north-1].right;
		int[] arrayPopulation = new int[2];
		for(int i = 0 ; i < allGroups.length; i++){
			if(allGroups[i].latitude >= bottom && allGroups[i].latitude <= up && 
				allGroups[i].longitude >= left && allGroups[i].longitude<= right){
				arrayPopulation[0] += allGroups[i].population;
			}
			arrayPopulation[1] += allGroups[i].population;
		}
		return arrayPopulation;
	}
}
