JC=/usr/bin/javac
JFLAGS = -classpath
PATH = ".:../lib/mysql-connector-java-5.0.8-bin.jar:../lib/servlet-api.jar:../lib/javax.json-1.0.jar:../lib/json-simple-1.1.jar"
#sources = \
Search.java \
Browse.java \
MovieDisplay.java \
AddMovie.java \
AddStar.java \
BrowseMain.java \
CartMod.java \
EmployeeFilter.java \
getStar.java \
Movie.java \
ValidateEmployee.java \
CartControl.java \
CheckOut.java \
EmployeeLogin.java \
Validate.java \
AddStar.java \
Cart.java \
LoginFilter.java \
Browse.java \
Dashboard.java \
getMovie.java \
MovieDisplay.java \
Login.java \
Metadata.java \
LogFilter.java
sources= \
LogFilter.java \
Search.java \
executeSQL.java 
classes = $(sources:.java=.class)

all:$(classes)
%.class: %.java
	$(JC) -d ../classes $(JFLAGS) $(PATH) $<
