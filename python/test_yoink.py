from pyoink import *
from jpype import *

pyoink=PYoink("/tmp1/m_zhen01/yoink/build/libs/Yoink-0.0.1.jar","./dori_abrupt.xml")
pyoink.partition()
print pyoink.get_qm_indices()
pyoink.write_result()
shutdownJVM()
