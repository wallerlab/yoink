from pyoink import *
from jpype import *

pyoink=PYoink("../build/libs/Yoink-0.0.3.jar","./dori_interaction.xml")
interaction_list,weight=pyoink.get_interaction_list()
for item in interaction_list:
  print item[0],item[1]
print interaction_list
