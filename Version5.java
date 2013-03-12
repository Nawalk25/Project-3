import java.util.concurrent.locks.ReentrantLock;


public class Version5 extends Processors{
	private CensusData data;
	
	public Version5(CensusData data){
		this.data = data;
	}
	
	@Override
	public int calculateGrid(Rectangle rect, int x, int y, int w, int s,
			int e, int n) {
		int[][] grid = new int[x][y];
		ReentrantLock[][] lk = new ReentrantLock[x][y];
		int lo = 0;
		int hi = data.data_size;
		MakeGridFifthVersion first = new MakeGridFifthVersion(x,y,lo,(hi+lo)/4,data.data,rect,grid, lk);
		MakeGridFifthVersion second = new MakeGridFifthVersion(x,y,(hi+lo)/4,(hi+lo)/2,data.data,rect,grid, lk);
		MakeGridFifthVersion third = new MakeGridFifthVersion(x,y,(hi+lo)/2,(hi+lo)*3/4,data.data,rect,grid, lk);
		MakeGridFifthVersion fourth = new MakeGridFifthVersion(x,y,(hi+lo)*3/4,hi,data.data,rect,grid, lk);
		first.start();
		second.start();
		third.start();
		fourth.run();
		try {
			first.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		try {
			second.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		try {
			third.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		Version3 ver = new Version3(data);
		return ver.queryRect(grid, x, y, w, s, e, n);
	}

	@Override
	public Rectangle findUSCorners() {
		Version2 ver = new Version2(data);
		return ver.findUSCorners();
	}

}
