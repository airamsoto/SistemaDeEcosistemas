package simulator.model;

public interface MapInfo extends JSONable, Iterable<MapInfo.RegionData>  	{
	
	public record RegionData(int row, int col, RegionInfo r) {
		public int get_row() {
			return row;
		}
		public int get_col() {
			return col;
		}
		public RegionInfo get_r() {
			return this.r;
		}
	}

	public int get_cols();

	public int get_rows();

	public int get_width();

	public int get_height();

	public int get_region_width();

	public int get_region_height();

	
}