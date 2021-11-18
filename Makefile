JC = javac
FLAGS = -d ./exe -classpath ./exe

all: Main

Main: Main.class

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