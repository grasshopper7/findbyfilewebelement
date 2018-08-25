package file.json.pagefactory;

import java.util.List;

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
}
