from pyoink import *
from jpype import *

"""
python script for adaptive QM/MM partitioning

$ python cluster.py input
"""

pyoink=PYoink("../build/libs/Yoink-0.0.1.jar","./dori_qmmm.xml")
pyoink.partition()
print pyoink.get_qm_indices()
pyoink.write_result()
pyoink.update()
pyoink.update([2])
shutdownJVM()
