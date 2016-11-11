from pyoink import *
from jpype import *

pyoink=PYoink("../build/libs/Yoink-0.0.3.jar","./dori_qmmm.xml")
print pyoink.get_qm_indices()
pyoink.update()
pyoink.update([2])
shutdownJVM()
