/**
 * CSE 332, Section AD, Project 3
 * Lokita Metta Yaputra, Maria Angela Suhardi
 * This class calculate and determine the index of x and y
 *
 */
public class GetIndex {
	/**
	 * Calculate the index of x
	 * @param big the rectangle that covers all area
	 * @param column number of columns
	 * @param longitude the current longitude
	 * @return the x-index
	 */
	public static int getIndexX(Rectangle big, int column, float longitude){
		float spacingX = (big.right - big.left)/(column);
		int i = 0;
		if(longitude == big.right){
			i = column-1;;
		}else{
			i = (int) Math.floor((longitude-big.left)/spacingX);
		}
		return i;
	}
	
	/**
	 * Calculate the index of y
	 * @param big the rectangle that covers all area
	 * @param row number of rows
	 * @param latitude the current latitude
	 * @return the y-index
	 */
	public static int getIndexY(Rectangle big, int row, float latitude){
		float spacingY = (big.top - big.bottom)/(row);
		int j = 0;
		if(latitude == big.top){
			j = row-1;
		}else{
			j = (int) Math.floor((latitude-big.bottom)/spacingY);
		}
		return j;
	}
}
