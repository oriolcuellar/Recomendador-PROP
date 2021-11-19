JC = javac
FLAGS = -d ./EXE -classpath ./EXE

all: main drivers

main: Main.class

Main.class: CtrlDomini.class
	$(JC) $(FLAGS) ./FONTS/src/domini/model/Main.java

Atribute.class:
	$(JC) $(FLAGS) ./FONTS/src/domini/model/Atribute.java

Cluster.class: User.class
	$(JC) $(FLAGS) ./FONTS/src/domini/model/Cluster.java

Conjunt_Items.class: Item.class
	$(JC) $(FLAGS) ./FONTS/src/domini/model/Conjunt_Items.java

Item.class: TipusItem.class Atribute.class
	$(JC) $(FLAGS) ./FONTS/src/domini/model/Item.java


K_Neareast_Neightbour.class: Conjunt_Items.class Item.class
	$(JC) $(FLAGS) ./FONTS/src/domini/model/K_Neareast_Neightbour.java

Kmeans.class: Cluster.class User.class
	$(JC) $(FLAGS) ./FONTS/src/domini/model/Kmeans.java

LectorCSV2.class:
	$(JC) $(FLAGS) ./FONTS/src/domini/model/LectorCSV2.java

myPair.class:
	$(JC) $(FLAGS) ./FONTS/src/domini/model/myPair.java

Ranged_Atribute.class: Atribute.class
	$(JC) $(FLAGS) ./FONTS/src/domini/model/Ranged_Atribute.java

RateRecomendation.class: myPair.class
	$(JC) $(FLAGS) ./FONTS/src/domini/model/RateRecomendation.java

SlopeOne.class: User.class myPair.class
	$(JC) $(FLAGS) ./FONTS/src/domini/model/SlopeOne.java

TipusItem.class: Atribute.class
	$(JC) $(FLAGS) ./FONTS/src/domini/model/TipusItem.java

TipusRol.class:
	$(JC) $(FLAGS) ./FONTS/src/domini/model/TipusRol.java

User.class: TipusRol.class valoratedItem.class
	$(JC) $(FLAGS) ./FONTS/src/domini/model/User.java

valoratedItem.class: Item.class
	$(JC) $(FLAGS) ./FONTS/src/domini/model/valoratedItem.java

CtrlDomini.class: User.class Item.class Kmeans.class SlopeOne.class valoratedItem.class Conjunt_Items.class TipusRol.class Ranged_Atribute.class LectorCSV2.class K_Neareast_Neightbour.class
	$(JC) $(FLAGS) ./FONTS/src/domini/controladores/CtrlDomini.java

drivers: DriverCluster.class DriverKmeans.class DriverKND.class DriverLectorCSV2.class DriverRateRecomendation.class UserTest.class DriverValoratedItem.class DriverSlopeOne.class DriverMyPair.class

DriverCluster.class: User.class Cluster.class
	$(JC) $(FLAGS) ./FONTS/src/domini/Drivers/DriverCluster.java

DriverKmeans.class: User.class Kmeans.class
	$(JC) $(FLAGS) ./FONTS/src/domini/Drivers/DriverKmeans.java

DriverKND.class: Atribute.class Ranged_Atribute.class TipusItem.class Conjunt_Items.class K_Neareast_Neightbour.class
	$(JC) $(FLAGS) ./FONTS/src/domini/Drivers/DriverKND.java

DriverLectorCSV2.class: LectorCSV2.class
	$(JC) $(FLAGS) ./FONTS/src/domini/Drivers/DriverLectorCSV2.java

DriverMyPair.class: myPair.class
	$(JC) $(FLAGS) ./FONTS/src/domini/Drivers/DriverMyPair.java

DriverRateRecomendation.class: RateRecomendation.class Item.class User.class SlopeOne.class
	$(JC) $(FLAGS) ./FONTS/src/domini/Drivers/DriverRateRecomendation.java

DriverSlopeOne.class: User.class
	$(JC) $(FLAGS) ./FONTS/src/domini/Drivers/DriverSlopeOne.java

UserTest.class:
	$(JC) $(FLAGS) ./FONTS/src/domini/Drivers/UserTest.java

DriverValoratedItem.class: valoratedItem.class
	$(JC) $(FLAGS) ./FONTS/src/domini/Drivers/DriverValoratedItem.java

Main:
	java -cp ./EXE FONTS.src.domini.model.Main

DriverCluster:
	java -cp ./EXE FONTS.src.domini.drivers.DriverCluster

DriverKmeans:
	java -cp ./EXE FONTS.src.domini.drivers.DriverKmeans

DriverKND:
	java -cp ./EXE FONTS.src.domini.drivers.DriverKND

DriverLectorCSV2:
	java -cp ./EXE FONTS.src.domini.drivers.DriverLectorCSV2

DriverMyPair:
	java -cp ./EXE FONTS.src.domini.drivers.DriverMyPair

DriverRateRecomendation:
	java -cp ./EXE FONTS.src.domini.drivers.DriverRateRecomendation

DriverSlopeOne:
	java -cp ./EXE FONTS.src.domini.drivers.DriverSlopeOne


DriverValoratedItem:
	java -cp ./EXE FONTS.src.domini.drivers.DriverValoratedItem

klk:
	$(JC) -d $(CP_TESTS) -cp $(CP):$(TEST_JARS) ./FONTS/src/domini/Drivers/UserTest.java
	javac /libs/junit-4.12:libs/hamcrest-core-1.3
