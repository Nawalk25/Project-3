import java.util.concurrent.*;

public class Version2 implements Processors {

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
	
	public int calculateGrid(Rectangle usRectangle, int west, int east, int north
							,int bottom){
		return 0;
	}
}
