import java.util.concurrent.locks.ReentrantLock;


public class MakeGridFifthVersion extends java.lang.Thread{
	private int lo;
	private int hi;
	private CensusGroup[] data;
	public int[][] grid;
	private ReentrantLock[][] lk;
	private int x;
	private int y;
	private Rectangle rect;
	
	public MakeGridFifthVersion(int x, int y, int lo, int hi, CensusGroup[] data, Rectangle rect, int[][] grid, ReentrantLock[][] lock){
		this.x = x; this.y = y; this.lo = lo; this.hi = hi; this.data = data; this.rect = rect;
		this.grid = grid; lk = lock;
		for(int i = 0; i < x; i++){
			for(int j = 0; j < y; j++){
				lk[i][j] = new ReentrantLock();
			}
		}
	}

	
	public void update(int i, int j, int population){
		synchronized(lk[i][j]){
			grid[i][j] += population;
		}
	}
	
	public void run(){
		float spacingX = (rect.right - rect.left)/(x);
		float spacingY = (rect.top - rect.bottom)/(y);
		for(int m = lo ; m < hi; m++){
			int j = 0;
			int i = 0;
			if(data[m].latitude == rect.top){
				j = y-1;
			}else{
				j = (int) Math.floor((data[m].latitude-rect.bottom)/spacingY);
			}
			if(data[m].longitude == rect.right){
				i = x-1;;
			}else{
				i = (int) Math.floor((data[m].longitude-rect.left)/spacingX);
			}
			update(i,j,data[m].population);
		}
	}
}
