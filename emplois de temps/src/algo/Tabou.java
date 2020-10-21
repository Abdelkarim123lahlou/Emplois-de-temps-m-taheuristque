package algo;

import model.Solution;

import java.util.ArrayList;

public class Tabou {

    private static final int MAX_ITER = 20;
    private static ArrayList<Solution> listTabou = new ArrayList<>();

    public static Solution algoTabou() {
        Solution meilleur = AlgoGlouton.algorithme();
        int compteur = 0;
        do {
            Solution meilleurVoisin;
            do {
                meilleurVoisin = choisirMeilleuroisin(Voisinage.genererEnsembleVoisins(meilleur, 20));
            } while (listTabou.contains(meilleurVoisin));
            if (meilleur.fonctionObjective() < meilleurVoisin.fonctionObjective()) {
                meilleur = meilleurVoisin;
                listTabou.add(meilleur);
            }
            compteur++;
        } while (compteur < MAX_ITER);
        return meilleur;
    }


    private static Solution choisirMeilleuroisin(ArrayList<Solution> solutions) {
        Solution meilleur = solutions.get(0);
        for (Solution s : solutions) {
            if (s.fonctionObjective() > meilleur.fonctionObjective())
                meilleur = s;
        }
        return meilleur;
    }
}
