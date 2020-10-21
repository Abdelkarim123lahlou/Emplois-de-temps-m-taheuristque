package sample;

import algo.AlgoGlouton;
import algo.Descente;
import algo.RecuitSimule;
import algo.Tabou;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Inputs;
import model.Solution;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("src/sample/file.json"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        Inputs input = gson.fromJson(br, new TypeToken<Inputs>() {
        }.getType());

        Solution.nmbrAmphis = input.nmbrAmphis;
        Solution.nmbrSalles = input.nmbrSalles;
        Solution.nmbrSeances = input.souhaits[0].length;
        Solution.nmbrProfs = input.souhaits.length;
        Solution.nmbrModules = input.chargeHoraireTD.length;
        Solution.nmbrGroupesTD = input.chargeHoraireTD[0].length;
        Solution.nmbrGroupesCours = input.chargeHoraireCours[0].length;
        Solution.chargeHoraireCours = input.chargeHoraireCours;
        Solution.chargeHoraireTD = input.chargeHoraireTD;
        Solution.souhaitsProfs = input.souhaits;


        long startTime = System.nanoTime();
        Solution s = Descente.algorithme();
        long endTime = System.nanoTime();
        long timeElapsed = endTime - startTime;

        new EmploiView(s, 0, timeElapsed, s.fonctionObjective(), "Algorithme Descente");

    }
}
