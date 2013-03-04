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
	
	public void findUSCorners() {
		Rectangle temp = fjPool.invoke(new FindCorners(usData, 0, size));
		usRectangle = temp;
	}
	

}
