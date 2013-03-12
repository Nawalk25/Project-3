
public class Version5 implements Processors{
	private CensusData data;
	
	public Version5(CensusData data){
		this.data = data;
	}
	
	@Override
	public int calculateGrid(Rectangle rec, int column, int row, int w, int s,
			int e, int n) {
		int[][] grid = new int[column][row];
		MakeGridFifthVersion makeGrid = new MakeGridFifthVersion(column, row, 0, data.data_size, data.data, rec, grid);
		makeGrid.run();
		Version3 ver = new Version3(data);
		return ver.queryRect(makeGrid.grid, column, row, w, s, e, n);
	}

	@Override
	public Rectangle findUSCorners() {
		Version2 ver = new Version2(data);
		return ver.findUSCorners();
	}

}
