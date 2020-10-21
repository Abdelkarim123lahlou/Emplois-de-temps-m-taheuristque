package algo;

import model.Solution;

import java.util.ArrayList;
import java.util.Random;

public class RecuitSimule {

    public static Solution algorithme() {
        Solution solActuelle = AlgoGlouton.algorithme();
        Solution meilleurSol = solActuelle.deepCopy();

        double T = 700;

        while (T > 1) {
            Solution solVoisine = select(Voisinage.genererEnsembleVoisins(solActuelle, 20));
            if (solVoisine.fonctionObjective() >= solActuelle.fonctionObjective()) {
                solActuelle = solVoisine;
            } else {
                Random aleatoire = new Random();
                double r = aleatoire.nextDouble();

                if (r < Boltzmann(solActuelle, solVoisine, T)) solActuelle = solVoisine;
            }

            if (solActuelle.fonctionObjective() > meilleurSol.fonctionObjective()) {
                meilleurSol = solActuelle.deepCopy();
            }
            T = T * 0.95d;
        }
        return meilleurSol;
    }

    public static Solution select(ArrayList<Solution> solVoisins) {
        Random aleatoire = new Random();

        int indexSelected = aleatoire.nextInt(solVoisins.size());
        return solVoisins.get(indexSelected);

    }

    private static double Boltzmann(Solution solActuelle, Solution solVoisin, double Temperature) {
        double x = (solVoisin.fonctionObjective() - solActuelle.fonctionObjective()) / Temperature;
        return Math.exp(x);
    }
}
