package org.wallerlab.yoink.density.data;


public class WfcReader {


    public void readWFC(Map<JobParameter, Object> parameters, Region adaptiveSearchRegion) {
        if ((boolean) parameters.get(DGRID) == true) {

            List<Atom> atoms = adaptiveSearchRegion.getAtoms();

            atoms.parallelStream()
                    .forEach(atom -> {
                        if (atom.getRadialGrid() == null) {
                            RadialGrid grid = new SimpleRadialGrid();
                            String wfc_name = atom.getElementType().toString().toLowerCase();
                            if (wfc_name.length() == 1) {
                                wfc_name = wfc_name + "_";
                            }
                            String wfc_file = parameters
                                    .get(JobParameter.WFC_PATH)
                                    + "/"
                                    + wfc_name + "_lda.wfc";
                            grid = radialGridReader.read(wfc_file, grid);
                            atom.setRadialGrid(grid);
                        }
                    });

        }
    }

}
