
public class MakeGridFifthVersion extends java.lang.Thread{
	private int lo;
	private int hi;
	private CensusGroup[] data;
	public int[][] grid;
	private Object[][] lk;
	private int x;
	private int y;
	private Rectangle rect;
	
	public MakeGridFifthVersion(int x, int y, int lo, int hi, CensusGroup[] data, Rectangle rect, int[][] grid){
		this.lo = lo;
		this.hi = hi;
		this.data = data;
		lk = new Object[x][y];
		this.rect = rect;
		this.grid = grid;
	}
	
	private MakeGridFifthVersion(int lo, int hi){
		this.lo = lo;
		this.hi = hi;
	}
	
	public void update(int i, int j, int population){
		synchronized(lk[i][j]){
			grid[i][j] += population;
		}
	}
	
	public void run(){
		if(hi - lo < 10){
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
		}else{
			MakeGridFifthVersion first = new MakeGridFifthVersion(lo,(hi+lo)/4);
			MakeGridFifthVersion second = new MakeGridFifthVersion((hi+lo)/4,(hi+lo)/2);
			MakeGridFifthVersion third = new MakeGridFifthVersion((hi+lo)/2,(hi+lo)*3/4);
			MakeGridFifthVersion fourth = new MakeGridFifthVersion((hi+lo)*3/4,hi);
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
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			try {
				third.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
