import java.util.concurrent.*;
/**
 * CSE 332, Section AD, Project 3
 * Lokita Metta Yaputra, Maria Angela Suhardi
 * This is the second version for counting census population in some rectangle
 * in the map. This version using parallelism
 *
 */
public class Version2 implements Processors {

	private CensusGroup[] usData;
	private int size;
	private final ForkJoinPool fjPool = new ForkJoinPool();

	/**
	 * Construct a new Version2
	 * @param fileInput file that contains the census data
	 */
	public Version2(CensusData fileInput){
		usData = fileInput.data;
		size = fileInput.data_size;
	}

	/**
	 * Find the four corners and make a rectangle
	 * @return rectangle that include the four corners
	 */
	public Rectangle findUSCorners() {
		Rectangle temp = fjPool.invoke(new FindCornersSecondVersion(usData, 0, size));
		return temp;
	}

	/**
	 * Calculate the desired query rectangle
	 * @param big base rectangle
	 * @param x how many columns
	 * @param y how many rows
	 * @param west left border of the query rectangle
	 * @param east right border of the query rectangle
	 * @param south bottom border of the query rectangle
	 * @param north upper border of the query rectangle
	 * @return population in the query rectangle
	 */
	public int calculateGrid(Rectangle big, int x, int y, int west, int south, int east,int north){
		float spacingX = (big.right - big.left)/x;
		float spacingY = (big.top - big.bottom)/y;
		float left = big.left + (west-1)*spacingX;
		float right = big.right - (x-east)*spacingX;
		float bottom = big.bottom + (south-1)*spacingY;
		float top = big.top - (y-north)*spacingY;
		Rectangle specific =  new Rectangle(left,right,top,bottom);
		
		int population = fjPool.invoke(new CalculateGridSecondVersion(0,size,usData,specific));
		return population;
	}
}
