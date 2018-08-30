package file.json.pagefactory;

import com.google.gson.stream.MalformedJsonException;

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
	
	public static void checkValues(FieldByDetails detail) throws MalformedJsonException {
		if(detail.field == null || detail.field.length() == 0)
			throw new MalformedJsonException("field attribute is missing or misspelled.");
		if(detail.how == null || detail.how.length() == 0)
			throw new MalformedJsonException("how attribute is missing or misspelled.");
		if(detail.using == null || detail.using.length() == 0)
			throw new MalformedJsonException("using attribute is missing or misspelled.");
	}
	
	
}
