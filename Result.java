/**
 * CSE 332, Section AD, Project 3
 * Lokita Metta Yaputra, Maria Angela Suhardi
 * 
 * result class that holds most of informations that used in making grid
 * 
 */

public class Result {
	CensusGroup[] data; //input data saved in array
	int x; // total column 
	int y; // total row
	Rectangle corners; // corner of the bigger rectangle
	Result(CensusGroup[] inputArray, int column, int row, Rectangle usRec) {
		data = inputArray;
		x = column; y = row;
		corners = usRec;
	}
}