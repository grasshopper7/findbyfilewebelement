package file.json.pagefactory;

import java.util.List;

import com.google.gson.stream.MalformedJsonException;

public class ClassDetails {

	private String className;
	private List<FieldByDetails> fieldBy;
	
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public List<FieldByDetails> getFieldBy() {
		return fieldBy;
	}
	public void setFieldBy(List<FieldByDetails> fieldBy) {
		this.fieldBy = fieldBy;
	}

	@Override
	public String toString() {
		return "ClassDetails [className=" + className + ", fieldBy=" + fieldBy + "]";
	}
	
	public static void checkValues(ClassDetails detail) throws MalformedJsonException {
		if(detail.className == null || detail.className.length() == 0)
			throw new MalformedJsonException("className attribute is missing or misspelled.");
		if(detail.fieldBy == null || detail.fieldBy.size() == 0)
			throw new MalformedJsonException("fieldBy attribute is missing or misspelled.");		
	}
}
