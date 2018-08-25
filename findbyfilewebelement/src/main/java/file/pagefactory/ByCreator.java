package file.pagefactory;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ByIdOrName;
import org.openqa.selenium.support.How;

public class ByCreator {

	public static  By createBy(String howStr, String using) {
		How how = How.valueOf(howStr);
		switch (how) {
		  case CLASS_NAME:
	        return By.className(using);
	      case CSS:
	        return By.cssSelector(using);
	      case ID:
	      case UNSET:
	        return By.id(using);
	      case ID_OR_NAME:
	        return new ByIdOrName(using);
	      case LINK_TEXT:
	        return By.linkText(using);
	      case NAME:
	        return By.name(using);
	      case PARTIAL_LINK_TEXT:
	        return By.partialLinkText(using);
	      case TAG_NAME:
	        return By.tagName(using);
	      case XPATH:
	        return By.xpath(using);
	      default:
	        // Note that this shouldn't happen (eg, the above matches all
	        // possible values for the How enum)
	        throw new IllegalArgumentException("Cannot determine how to locate element ");
		}
	}
}
