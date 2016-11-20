from pyoink import *
from jpype import *

pyoink=PYoink("../build/libs/Yoink-0.0.3.jar","./dori_qmmm.xml")
qm_atoms,qm_molecules = pyoink.get_qm_indices()
print qm_molecules
shutdownJVM()
