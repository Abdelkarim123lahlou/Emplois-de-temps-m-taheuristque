package algo;

import model.Solution;

import java.util.ArrayList;
import java.util.Random;

public class Voisinage {


    /**
     * Fonction qui genere un voisin de la solution s en permutant les seances affectées a deux profs
     * Il y a 4 cas : - Soit les deux seances a permuter sont des seances ou ils enseignent le cours
     * - Soit les deux seances a permuter sont des seances ou ils enseignent le TD
     * - Soit la premiere seance est de cours et la deuxieme est de TD (Ou l'inverse)
     * Une condition importante est que chaque prof a au enseigne moins une seance
     *
     * @param s
     * @return
     */
    public static Solution genererVoisinAleatoire(Solution s) {
        Solution voisin = s.deepCopy();

        Random aleatoire = new Random();

        int nmbrPermutation = 3;

        while (nmbrPermutation != 0) {
            int prof1, prof2;
            prof1 = aleatoire.nextInt(Solution.nmbrProfs);
            do {
                prof2 = aleatoire.nextInt(Solution.nmbrProfs);
            } while (prof1 == prof2);

            int seance1, salle1, amphi1, module1, groupeTD1, groupeCours1;
            int seance2, salle2, amphi2, module2, groupeTD2, groupeCours2;

            do {
                seance1 = aleatoire.nextInt(Solution.nmbrSeances);
                salle1 = aleatoire.nextInt(Solution.nmbrSalles);
                amphi1 = aleatoire.nextInt(Solution.nmbrAmphis);
                module1 = aleatoire.nextInt(Solution.nmbrModules);
                groupeTD1 = aleatoire.nextInt(Solution.nmbrGroupesTD);
                groupeCours1 = aleatoire.nextInt(Solution.nmbrGroupesCours);
            } while (voisin.X[prof1][groupeTD1][seance1][salle1][module1] == 1 || voisin.Y[prof1][groupeCours1][seance1][amphi1][module1] == 1);

            do {
                seance2 = aleatoire.nextInt(Solution.nmbrSeances);
                salle2 = aleatoire.nextInt(Solution.nmbrSalles);
                amphi2 = aleatoire.nextInt(Solution.nmbrAmphis);
                module2 = aleatoire.nextInt(Solution.nmbrModules);
                groupeTD2 = aleatoire.nextInt(Solution.nmbrGroupesTD);
                groupeCours2 = aleatoire.nextInt(Solution.nmbrGroupesCours);
            } while (voisin.X[prof2][groupeTD2][seance2][salle2][module2] == 1 || voisin.Y[prof2][groupeCours2][seance2][amphi2][module2] == 1);


            if (voisin.X[prof1][groupeTD1][seance1][salle1][module1] == 1) //La seance a retirer pour le prof1 est une seance de TD
            {

                voisin.X[prof1][groupeTD1][seance1][salle1][module1] = 0; //d�saffecter la seance de TD du prof1

                if (voisin.X[prof2][groupeTD2][seance2][salle2][module2] == 1) //La seance a retirer pour le prof 2 est une seance de TD
                {
                    voisin.X[prof2][groupeTD2][seance2][salle2][module2] = 0; //d�saffecter la seance de TD du prof2
                    voisin.X[prof1][groupeTD2][seance2][salle2][module2] = 1; //Donner au prof1 la seance de TD du prof2
                    voisin.X[prof2][groupeTD1][seance1][salle1][module1] = 1; //Donner au prof2 la seance de TD du prof1
                } else //La seance a retirer pour le prof 2 est une seance de cours
                {
                    voisin.Y[prof2][groupeCours2][seance2][amphi2][module2] = 0; //d�saffecter la seance de cours du prof2
                    voisin.Y[prof1][groupeCours2][seance2][amphi2][module2] = 1; //Affecter au prof1 la seance de cours du prof2
                    voisin.X[prof2][groupeTD1][seance1][salle1][module1] = 1; // Affecter au prof2 la seance du td du prof1
                }
            } else //Si la seance a retier pour le prof1 est une seance de cours
            {
                voisin.Y[prof1][groupeCours1][seance1][amphi1][module1] = 0; //Retirer la seance de cours du prof1

                if (voisin.X[prof2][groupeTD2][seance2][salle2][module2] == 1) // Si la seance a retirer pour le prof2 est une seance de td
                {
                    voisin.X[prof2][groupeTD2][seance2][salle2][module2] = 0; //retirer la seance de td du prof2

                    voisin.Y[prof2][groupeCours1][seance1][amphi1][module1] = 1; //Donner la seance de cours du prof1 au prof2
                    voisin.X[prof1][groupeTD2][seance2][salle2][module2] = 1; //Donner la seance de td du prof2 au prof1
                } else // Si la seance a retirer pour le prof2 est une seance de cours
                {
                    voisin.Y[prof2][groupeCours2][seance2][amphi2][module2] = 0; //retirer la seance de cours du prof2

                    voisin.Y[prof2][groupeCours1][seance1][amphi1][module1] = 1; //Donner la seance de cours du prof1 au prof2
                    voisin.Y[prof1][groupeCours2][seance2][amphi2][module2] = 1; //Donner la seance de cours du prof2 au prof1
                }

            }
            nmbrPermutation--;
        }
        return voisin;
    }

    /**
     * Generer les voisins de la solution s
     *
     * @param s
     * @param nbrVoisins nombre de voisins d�sir�
     * @return
     */
    public static ArrayList<Solution> genererEnsembleVoisins(Solution s, int nbrVoisins) {
        ArrayList<Solution> voisins = new ArrayList<Solution>();
        int compt = nbrVoisins;
        while (compt != 0) {
            voisins.add(genererVoisinAleatoire(s));
            compt--;
        }
        return voisins;
    }


}
