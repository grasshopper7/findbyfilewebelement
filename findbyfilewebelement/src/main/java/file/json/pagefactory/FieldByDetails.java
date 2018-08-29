package file.json.pagefactory;

public class FieldByDetails {

	private String field;
	private String how;
	private String using;
	
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getHow() {
		return how.toUpperCase();
	}
	public void setHow(String how) {
		this.how = how;
	}
	public String getUsing() {
		return using;
	}
	public void setUsing(String using) {
		this.using = using;
	}
	@Override
	public String toString() {
		return "FieldByDetails [field=" + field + ", how=" + how + ", using=" + using + "]";
	}
	
	
}
