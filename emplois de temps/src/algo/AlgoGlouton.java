package algo;

import model.Solution;

public class AlgoGlouton {

    public static Solution algorithme() {
        Solution s = new Solution();

        /**
         * Donner les souhaits des profs
         */
        for (int prof = 0; prof < Solution.nmbrProfs; prof++)
            for (int seance = 0; seance < Solution.nmbrSeances; seance++)
                if (Solution.souhaitsProfs[prof][seance] == 1) {
                    ajouterSouhait(s, prof, seance);
                }



    if(!(s.contrainteChargeTD() && s.contrainteChargeCours())){


            for (int module = 0; module < s.nmbrModules; module++) {

                for (int groupe = 0; groupe < s.nmbrGroupesCours; groupe++)
                    while (s.seancesCoursRestant(module, groupe) > 0)
                        if (! ajouterSeanceCours(module, groupe, s)) {
                            break;
                        }

                for (int groupe = 0; groupe < s.nmbrGroupesTD; groupe++)
                    while (s.seancesTDRestant(module, groupe) > 0)
                        if (ajouterSeanceTD(module, groupe, s)) {
                            break;
                        }
            }

        }


        return s;
    }

    /**
     * Affecte un module, un groupe, une salle ou amphi a un prof donne avec une seance donnee
     *
     * @param s
     * @param prof
     * @param seance
     * @return
     */
    private static boolean ajouterSouhait(Solution s, int prof, int seance) {
        for (int module = 0; module < s.nmbrModules; module++) {
            for (int groupe = 0; groupe < s.nmbrGroupesCours; groupe++)
                if (s.seancesCoursRestant(module, groupe) > 0)
                    for (int amphi = 0; amphi < s.nmbrAmphis; amphi++) {
                        s.Y[prof][groupe][seance][amphi][module] = 1;
                        if (s.contrainteAmphi() && s.contrainteProf() && s.contrainteGroupeCours()) {
                            eliminerSeancePourGroupesTD(groupe, seance, s);
                            return true;
                        } else s.Y[prof][groupe][seance][amphi][module] = 0;
                    }


            for (int groupe = 0; groupe < s.nmbrGroupesTD; groupe++)
                if (s.seancesTDRestant(module, groupe) > 0)
                    for (int salle = 0; salle < s.nmbrSalles; salle++) {

                        s.X[prof][groupe][seance][salle][module] = 1;
                        if (s.contrainteGroupeTD() && s.contrainteProf() && s.contrainteSalle()) {
                            eliminerSeancePourGroupeCours(groupe, seance, s);
                            return true;
                        } else s.X[prof][groupe][seance][salle][module] = 0;
                    }
        }
        return false;
    }


    /**
     * Etant donne un groupe de TD et un module, on ajoute une seance de TD
     *
     * @param module
     * @param groupeTD
     * @param s
     * @return
     */
    private static boolean ajouterSeanceTD(int module, int groupeTD, Solution s) {
        for (int prof = 0; prof < Solution.nmbrProfs; prof++)
            for (int seance = 0; seance < Solution.nmbrSeances; seance++)
                for (int salle = 0; salle < s.nmbrSalles; salle++) {
                    if (s.X[prof][groupeTD][seance][salle][module] == 0) {
                        s.X[prof][groupeTD][seance][salle][module] = 1;
                        if (s.contrainteGroupeTD() && s.contrainteProf() && s.contrainteSalle()) {
                            eliminerSeancePourGroupeCours(groupeTD, seance, s);
                            return true;
                        } else
                            s.X[prof][groupeTD][seance][salle][module] = 0;
                    }
                }
        return false;
    }

    /**
     * Etant donne un groupe de cours et un module on ajoute une seance
     *
     * @param module
     * @param groupeCours
     * @param s
     * @return
     */
    private static boolean ajouterSeanceCours(int module, int groupeCours, Solution s) {
        for (int prof = 0; prof < Solution.nmbrProfs; prof++)
            for (int seance = 0; seance < Solution.nmbrSeances; seance++)
                for (int amphi = 0; amphi < s.nmbrAmphis; amphi++) {
                    if (s.Y[prof][groupeCours][seance][amphi][module] == 0) {
                        s.Y[prof][groupeCours][seance][amphi][module] = 1;
                        if (s.contrainteAmphi() && s.contrainteProf() && s.contrainteGroupeCours()) {
                            eliminerSeancePourGroupesTD(groupeCours, seance, s);
                            return true;
                        } else
                            s.Y[prof][groupeCours][seance][amphi][module] = 0;

                    }
                }

        return false;

    }

    /**
     * Lorsqu'on affecte a un groupe de cours une seance, il faut �liminer la possibilt� de progrmmer dans cette s�ance pour
     * les groupes de TD.
     * On marque les cases par -1, qui signifie qu'il ne sont pas disponibles.
     *
     * @param groupeCours
     * @param seance
     * @param s
     */
    private static void eliminerSeancePourGroupesTD(int groupeCours, int seance, Solution s) {
        for (int groupeTD = 4 * groupeCours; groupeTD < 4 * groupeCours + 4; groupeTD++)
            for (int salle = 0; salle < s.nmbrSalles; salle++)
                for (int module = 0; module < s.nmbrModules; module++)
                    for (int prof = 0; prof < s.nmbrProfs; prof++)
                        s.X[prof][groupeTD][seance][salle][module] = - 1;

    }

    /**
     * Lorsqu'on affecte a un groupe de TD une seance, il faut �liminer la possibilt�  de prpgrammer dans cette s�ance pour
     * le groupe de cours correspondant
     * On marque les cases par -1, qui signifie qu'il ne sont pas disponibles.
     *
     * @param groupeTD
     * @param seance
     * @param s
     */
    private static void eliminerSeancePourGroupeCours(int groupeTD, int seance, Solution s) {
        if (groupeTD > 11) return;
        int groupeCours = - 1;
        if (groupeTD <= 3) groupeCours = 0;
        if (groupeTD >= 4 && groupeTD <= 7) groupeCours = 1;
        if (groupeTD >= 8 && groupeTD <= 11) groupeCours = 2;
        if (groupeCours < 0) return;

        for (int amphi = 0; amphi < s.nmbrAmphis; amphi++)
            for (int module = 0; module < s.nmbrModules; module++)
                for (int prof = 0; prof < s.nmbrProfs; prof++)
                    s.Y[prof][groupeCours][seance][amphi][module] = - 1;
    }

}

