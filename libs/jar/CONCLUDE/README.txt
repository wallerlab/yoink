CONCLUDE 0.1
************

USER GUIDE
**********

To launch the community detection algorithm type:

java -jar CONCLUDE.jar input-filename output-filename delimiter compute-weights-only

input-filename: the name of the file containing the edgelist representing the network

output-filename: the output filename

delimiter: default "\t" (tab-separated values); other options: "," (csv) or " " (space-separated values)

compute-weights-only: with this option the algorithm will compute just the weights of the edges, without clustering the network.

-----------------
By Emilio FERRARA
14 Nov. 2011