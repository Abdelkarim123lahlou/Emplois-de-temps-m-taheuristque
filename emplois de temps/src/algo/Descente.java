package algo;

import model.Solution;

import java.util.ArrayList;
import java.util.Random;

public class Descente {

    public static Solution algorithme() {
        Solution meilleur = AlgoGlouton.algorithme();

        boolean solAmelioree; //Utilisée dans la conditoon d'arret pour savoir si la solution a été améliorer après le choix du meilleur voisin

        do {
            Solution meilleurVoisin = select(Voisinage.genererEnsembleVoisins(meilleur, 40));
            if (meilleur.fonctionObjective() < meilleurVoisin.fonctionObjective()) {
                meilleur = meilleurVoisin;
                solAmelioree = true;
            } else
                solAmelioree = false;

        } while (solAmelioree);

        return meilleur;
    }

    public static Solution select(ArrayList<Solution> solVoisins) {
        Random aleatoire = new Random();

        int indexSelected = aleatoire.nextInt(solVoisins.size());
        return solVoisins.get(indexSelected);

    }

}
