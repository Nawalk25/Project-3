public class Result {
	CensusGroup[] data;
	int x;
	int y;
	Rectangle corners;
	Result(CensusGroup[] inputArray, int column, int row, Rectangle usRec) {
		data = inputArray;
		x = column; y = row;
		corners = usRec;
	}
}