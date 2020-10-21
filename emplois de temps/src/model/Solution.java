package model;

public class Solution {
    public static int nmbrModules; //Pour chaque année
    public static int nmbrGroupesTD;
    public static int nmbrGroupesCours;
    public static int nmbrProfs;
    public static int nmbrSeances;
    public static int nmbrSalles;
    public static int nmbrAmphis;
    public static int[][] chargeHoraireTD = new int[nmbrModules][nmbrGroupesTD];
    public static int[][] chargeHoraireCours = new int[nmbrModules][nmbrGroupesCours];
    public static int[][] souhaitsProfs = new int[nmbrProfs][nmbrSeances];
    /**
     * X pour les groupes de TD
     * Y pour les groupe de Cours
     */
    public int[][][][][] X;
    public int[][][][][] Y;

    //int pr, int gr, int se, int sa, int am, int mo
    public Solution() {
        X = new int[nmbrProfs][nmbrGroupesTD][nmbrSeances][nmbrSalles][nmbrModules];
        Y = new int[nmbrProfs][nmbrGroupesCours][nmbrSeances][nmbrAmphis][nmbrModules];
    }

    /**
     * Faire une copie de la matrice (deep copie)
     *
     * @param mat
     * @return
     */
    private static int[][][][][] copy(int[][][][][] mat) {
        int size1 = mat.length;
        int size2 = mat[0].length;
        int size3 = mat[0][0].length;
        int size4 = mat[0][0][0].length;
        int size5 = mat[0][0][0][0].length;

        int[][][][][] copy = new int[size1][size2][size3][size4][size5];

        for (int i = 0; i < size1; i++)
            for (int j = 0; j < size2; j++)
                for (int k = 0; k < size3; k++)
                    for (int l = 0; l < size4; l++)
                        for (int m = 0; m < size5; m++)
                            copy[i][j][k][l][m] = mat[i][j][k][l][m];
        return copy;
    }

    /*
     * Une salle ne peut contenir deux seance de TD en meme temps
     */
    public boolean contrainteSalle() {
        for (int salle = 0; salle < nmbrSalles; salle++)
            for (int seance = 0; seance < nmbrSeances; seance++) {
                int somme = 0;
                for (int prof = 0; prof < nmbrProfs; prof++)
                    for (int groupe = 0; groupe < nmbrGroupesTD; groupe++)
                        for (int module = 0; module < nmbrModules; module++) {
                            if (X[prof][groupe][seance][salle][module] != - 1)
                                somme += X[prof][groupe][seance][salle][module];
                            if (somme > 1) return false;
                        }
            }
        return true;
    }

    /*
     * Un amphi ne peut contenir deux cours en meme temps
     */
    public boolean contrainteAmphi() {
        for (int amphi = 0; amphi < nmbrAmphis; amphi++)
            for (int seance = 0; seance < nmbrSeances; seance++) {
                int somme = 0;
                for (int prof = 0; prof < nmbrProfs; prof++)
                    for (int groupe = 0; groupe < nmbrGroupesCours; groupe++)
                        for (int module = 0; module < nmbrModules; module++) {
                            if (Y[prof][groupe][seance][amphi][module] != - 1)
                                somme += Y[prof][groupe][seance][amphi][module];
                            if (somme > 1) return false;
                        }
            }
        return true;
    }

    /*
     * Un prof ne peut enseigner deux fois en meme seance
     */
    public boolean contrainteProf() {
        for (int prof = 0; prof < nmbrProfs; prof++)
            for (int seance = 0; seance < nmbrSeances; seance++) {
                int somme = 0;
                for (int module = 0; module < nmbrModules; module++) {

                    for (int salle = 0; salle < nmbrSalles; salle++)
                        for (int groupe = 0; groupe < nmbrGroupesTD; groupe++)
                            if (X[prof][groupe][seance][salle][module] != - 1)
                                somme += X[prof][groupe][seance][salle][module];

                    if (somme > 1) return false;

                    for (int amphi = 0; amphi < nmbrAmphis; amphi++)
                        for (int groupe = 0; groupe < nmbrGroupesCours; groupe++)
                            if (Y[prof][groupe][seance][amphi][module] != - 1)
                                somme += Y[prof][groupe][seance][amphi][module];

                    if (somme > 1) return false;

                }
            }

        return true;
    }

    /*
     * Contrainte de charge horaire du TD de chaque module
     */
    public boolean contrainteChargeTD() {
        for (int groupe = 0; groupe < nmbrGroupesTD; groupe++)
            for (int module = 0; module < nmbrModules; module++) {
                int chargeTD = 0;
                for (int prof = 0; prof < nmbrProfs; prof++)
                    for (int seance = 0; seance < nmbrSeances; seance++)
                        for (int salle = 0; salle < nmbrSalles; salle++)
                            if (X[prof][groupe][seance][salle][module] != - 1)
                                chargeTD += X[prof][groupe][seance][salle][module];

                if (chargeTD != chargeHoraireTD[module][groupe]) return false;
            }

        return true;

    }

    /*
     * Contrainte de charge horaire du cours de chaque module
     */
    public boolean contrainteChargeCours() {
        for (int groupe = 0; groupe < nmbrGroupesCours; groupe++)
            for (int module = 0; module < nmbrModules; module++) {
                int chargeCours = 0;
                for (int prof = 0; prof < nmbrProfs; prof++)
                    for (int seance = 0; seance < nmbrSeances; seance++)
                        for (int amphi = 0; amphi < nmbrAmphis; amphi++)
                            if (Y[prof][groupe][seance][amphi][module] != - 1)
                                chargeCours += Y[prof][groupe][seance][amphi][module];

                if (chargeCours != chargeHoraireCours[module][groupe]) return false;
            }

        return true;
    }

    /*
     * Un groupe de TD ne peut avoir deux seances en meme temps
     */
    public boolean contrainteGroupeTD() {
        for (int groupe = 0; groupe < nmbrGroupesTD; groupe++)
            for (int seance = 0; seance < nmbrSeances; seance++) {
                int somme = 0;
                for (int prof = 0; prof < nmbrProfs; prof++)
                    for (int module = 0; module < nmbrModules; module++)
                        for (int salle = 0; salle < nmbrSalles; salle++)
                            if (X[prof][groupe][seance][salle][module] != - 1)
                                somme += X[prof][groupe][seance][salle][module];

                if (somme > 1) return false;
            }
        return true;
    }

    /*
     * Un groupe de Cours ne peut avoir deux seances en meme tmps
     */
    public boolean contrainteGroupeCours() {
        for (int groupe = 0; groupe < nmbrGroupesCours; groupe++)
            for (int seance = 0; seance < nmbrSeances; seance++) {
                int somme = 0;
                for (int prof = 0; prof < nmbrProfs; prof++)
                    for (int module = 0; module < nmbrModules; module++)
                        for (int amphi = 0; amphi < nmbrAmphis; amphi++)
                            if (Y[prof][groupe][seance][amphi][module] != - 1)
                                somme += Y[prof][groupe][seance][amphi][module];

                if (somme > 1) return false;
            }
        return true;
    }

    /*
     * Fonction qui calcule le nombre de seance restant pour completer la charge TD d'un module
     */
    public int seancesTDRestant(int module, int groupeTD) {
        int nbrSeancesActuelle = 0;
        for (int seance = 0; seance < nmbrSeances; seance++)
            for (int prof = 0; prof < nmbrProfs; prof++)
                for (int salle = 0; salle < nmbrSalles; salle++) {
                    if (X[prof][groupeTD][seance][salle][module] != - 1)
                        nbrSeancesActuelle += X[prof][groupeTD][seance][salle][module];
                }
        return chargeHoraireTD[module][groupeTD] - nbrSeancesActuelle;

    }

    /*
     * Fonction qui calcule le nombre de seance restant pour completer la charge Cours d'un module
     */
    public int seancesCoursRestant(int module, int groupeCours) {
        int nbrSeancesActuelle = 0;
        for (int seance = 0; seance < nmbrSeances; seance++)
            for (int prof = 0; prof < nmbrProfs; prof++)
                for (int amphi = 0; amphi < nmbrAmphis; amphi++) {
                    if (Y[prof][groupeCours][seance][amphi][module] != - 1)
                        nbrSeancesActuelle += Y[prof][groupeCours][seance][amphi][module];
                }

        return chargeHoraireCours[module][groupeCours] - nbrSeancesActuelle;

    }

    /**
     * Fonction objective a le meme principe que la derniere avec une difference:
     * Pour chaque prof et chaque seance, on cherche s'il l'enseigne, d�s que l'on trouve cette seance on voit si elle
     * fait partie des souhaits et on ajoute au fct obj, puis on passe au seance prochaine sans continuer d'it�rer
     * puisque c'est garantie qu'un prof enseigne une seule fois en une seance donn�e
     *
     * @return
     */
    public int fonctionObjective() {
        int valeur = 0;

        for (int p = 0; p < nmbrProfs; p++)
            for (int se = 0; se < nmbrSeances; se++) {
                boolean souhaitTrouve = false;

                for (int g = 0; g < nmbrGroupesTD; g++) {
                    for (int mo = 0; mo < nmbrModules; mo++) {
                        for (int sa = 0; sa < nmbrSalles; sa++) {

                            if (X[p][g][se][sa][mo] == 1) {
                                valeur += (X[p][g][se][sa][mo] * souhaitsProfs[p][se]);
                                souhaitTrouve = true;
                                break;
                            }
                        }
                        if (souhaitTrouve) break;
                    }
                    if (souhaitTrouve) break;
                }

                if (souhaitTrouve) continue;


                for (int gr = 0; gr < nmbrGroupesCours; gr++) {
                    for (int mo = 0; mo < nmbrModules; mo++) {
                        for (int am = 0; am < nmbrAmphis; am++) {
                            if (Y[p][gr][se][am][mo] == 1) {
                                valeur += (Y[p][gr][se][am][mo] * souhaitsProfs[p][se]);
                                souhaitTrouve = true;
                                break;
                            }
                        }
                        if (souhaitTrouve) break;
                    }
                    if (souhaitTrouve) break;
                }
            }

        return valeur;

    }

    /**
     * Retourne le groupe du cours correcpondant a un groupe de TD, si le groupe de TD est cycle ingenieur il retourne -1
     *
     * @param groupeTD
     * @return
     */
    public int getGroupeCoursFromGroupeTD(int groupeTD) {
        int groupeCours = - 1;
        if (groupeTD <= 3) groupeCours = 0;
        if (groupeTD >= 4 && groupeTD <= 7) groupeCours = 1;
        if (groupeTD >= 8 && groupeTD <= 11) groupeCours = 2;

        return groupeCours;
    }

    /**
     * Afficher les details une seance pour un groupe de TD
     *
     * @param seance
     * @param groupeTD
     * @return
     */
    public String printSeance(int seance, int groupeTD) {
        for (int module = 0; module < nmbrModules; module++) {

            for (int prof = 0; prof < nmbrProfs; prof++) {

                for (int salle = 0; salle < nmbrSalles; salle++)
                    if (X[prof][groupeTD][seance][salle][module] == 1) {
                        return "TD " + "Module " + module + " ,Prof " + prof + " , Salle " + salle;
                    }
                if (groupeTD >= 12) break;

                for (int amphi = 0; amphi < nmbrAmphis; amphi++) {
                    if (Y[prof][getGroupeCoursFromGroupeTD(groupeTD)][seance][amphi][module] == 1) {
                        return "Cours " + "Module " + module + " ,Prof " + prof + " , Amphi " + amphi;
                    }
                }


            }
        }
        return "";
    }

    /**
     * Faire une copie de l'objet
     *
     * @return
     */
    public Solution deepCopy() {
        Solution s = new Solution();
        s.X = copy(this.X);
        s.Y = copy(this.Y);
        return s;
    }

}
