/**
 * CSE 332, Section AD, Project 3
 * Lokita Metta Yaputra, Maria Angela Suhardi
 * This is the interface of all versions. It has two methods, 
 * calculate and find the corners
 */
public interface Processors {
	/**
	 * Calculate the desired query rectangle
	 */
	public int calculateGrid(Rectangle rec, int column, int row, int w, int s,
			int e, int n);
	/**
	 * Find the four corners of US Maps
	 */
	public Rectangle findUSCorners();

}
