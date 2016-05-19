from pyoink import *
from jpype import *
import louvain
import igraph as ig

pyoink=PYoink("../build/libs/Yoink-0.0.1.jar","./dori_cluster.xml")
interaction_list=pyoink.get_interaction_list()
print interaction_list
G=ig.Graph(interaction_list)


part = louvain.find_partition(G, method='Modularity')
print part._membership
print part.cluster_graph()
print dir(part)
print ("giant", part.giant())
print ("_lens", part._len)
print ("sizes",part.sizes())

#print pyoink.get_clusters()
shutdownJVM()
