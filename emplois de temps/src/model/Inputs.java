package model;


public class Inputs {
    public int nmbrModules; //Pour chaque année
    public int nmbrGroupesTD;
    public int nmbrGroupesCours;
    public int nmbrProfs;
    public int nmbrSeances;
    public int nmbrSalles;
    public int nmbrAmphis;

    public int[][] chargeHoraireTD;
    public int[][] chargeHoraireCours;
    public int[][] souhaits;


    public Inputs(int nmbrSalles, int nmbrAmphis, int[][] chargeHoraireTD, int[][] chargeHoraireCours, int[][] souhaits) {
        super();
        this.chargeHoraireTD = chargeHoraireTD;
        this.chargeHoraireCours = chargeHoraireCours;
        this.souhaits = souhaits;

        nmbrSeances = souhaits[0].length;
        nmbrProfs = souhaits.length;
        nmbrModules = chargeHoraireTD.length;
        nmbrGroupesTD = chargeHoraireTD[0].length;
        nmbrGroupesCours = chargeHoraireCours[0].length;
        this.nmbrAmphis = nmbrAmphis;
        this.nmbrSalles = nmbrSalles;

    }

}
