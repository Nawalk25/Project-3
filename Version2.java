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
	
	public int calculateGrid(Rectangle big, int x, int y, int west, int east, int north
							,int south){
		float spacingX = (big.right - big.left)/x;
		float spacingY = (big.top - big.bottom)/y;
		Rectangle specific =  new Rectangle((west-1)*spacingX,(east-1)*spacingX,(north-1)*spacingY,
		(south-1)*spacingY);
		int population = fjPool.invoke(new CalculateGridSecondVersion(0,usData.length,usData,specific));
		return population;
	}
}
