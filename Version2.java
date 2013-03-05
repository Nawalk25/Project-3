import java.util.concurrent.*;

public class Version2 {

	CensusGroup[] usData;
	int size;
	// west = smaller longitude (horizontal)
	// south = smaller latitude (vertical)
	Rectangle usRectangle;
	
	public Version2(CensusData fileInput){
		usData = fileInput.data;
		size = fileInput.data_size;
	}
	
	final ForkJoinPool fjPool = new ForkJoinPool();
	
	public Rectangle findUSCorners() {
		Rectangle temp = fjPool.invoke(new FindCorners(usData, 0, size));
		return temp;
	}
	
	public int calculateGrid(Rectangle big, int x, int y, int west, int east, int south
							,int north){
		float spacingX = (big.right - big.left)/x;
		float spacingY = (big.top - big.bottom)/y;
		float left = big.left + (west-1)*spacingX;
		float right = left + (east-west-1)*spacingX;
		float bottom = big.bottom + (south-1)*spacingY;
		float top = bottom + (north-south-1)*spacingY;
		Rectangle specific =  new Rectangle(left,right,top,bottom);
		int population = fjPool.invoke(new CalculateGridSecondVersion(0,size,usData,specific));
		return population;
	}
}
