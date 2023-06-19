run: 
	javac StudentMap.java
	java StudentMap

runTests:
	javac -cp .:junit5.jar FrontendDeveloperTests.java
	javac -cp .:junit5.jar BackendDeveloperTests.java
	javac -cp .:junit5.jar AlgorithmEngineerTests.java
	javac -cp .:junit5.jar DataWranglerTests.java
	java -jar junit5.jar -cp . --select-class=FrontendDeveloperTests
	java -jar junit5.jar -cp . --select-class=BackendDeveloperTests
	java -jar junit5.jar -cp . --select-class=AlgorithmEngineerTests
	java -jar junit5.jar -cp . --select-class=DataWranglerTests

clean:
	rm -f *.class
	rm -f *~
