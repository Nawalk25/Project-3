import java.util.concurrent.*;

public class Version2 {

	CensusGroup[] usData;
	int size;
	
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
		float right = big.right - (x-east)*spacingX;
		float bottom = big.bottom + (south-1)*spacingY;
		float top = big.top - (y-north)*spacingY;
		Rectangle specific =  new Rectangle(left,right,top,bottom);
		int population = fjPool.invoke(new CalculateGridSecondVersion(0,size,usData,specific));
		return population;
	}
}
