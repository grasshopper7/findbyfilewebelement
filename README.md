Refer here for repository details - [![Maven Central](https://maven-badges.herokuapp.com/maven-central/tech.grasshopper/findbyfilewebelement/badge.svg)](https://maven-badges.herokuapp.com/maven-central/tech.grasshopper/findbyfilewebelement)

Refer here for a detailed explanation of the framework - http://grasshopper.tech/220/

Refer here for a sample implementation - https://github.com/grasshopper7/SOfilelocator

When @FindBy annotation is used in the Selenium Page Object Model the value of its attributes how, using etc need to be available in the code. There is no mechanism to store and access these locator data from outside the source code, say in an excel file. This mechanism requires Java-8.

This mechanism seamlessly integrates with the current Page Object Model and PageFactory implementations. More importantly this supports existing @FindBy, @FindBys and @FindAll annotations alongside new development in the same PageObject.

	<dependency>
    	<groupId>tech.grasshopper</groupId>
    	<artifactId>findbyfilewebelement</artifactId>
    	<version>1.0.0</version>
	</dependency>
