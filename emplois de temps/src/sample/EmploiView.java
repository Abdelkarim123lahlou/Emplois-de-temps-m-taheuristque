package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;
import model.Solution;

import java.util.ArrayList;
import java.util.List;


public class EmploiView extends Stage {
    private Solution emploi;
    private int groupe;
    private long tempsExecution;
    private long fonctionObjectif;
    private String algoUtiliser;
    private List<String> jours = new ArrayList<>();
    private List<String> temps = new ArrayList<>();
    private GridPane gridPane = new GridPane();
    private Label titre = new Label();
    private Label valeurFnOj = new Label();
    private Label algoUsed = new Label();
    private VBox header = new VBox();

    EmploiView(Solution s, int groupe, long tempsExecution, long fonctionObjectif, String Algo) {
        this.emploi = s;
        this.groupe = groupe;
        this.tempsExecution = tempsExecution;
        this.fonctionObjectif = fonctionObjectif;
        this.algoUtiliser = Algo;
        BorderPane root = new BorderPane();
        header();
        addJours();
        addTemps();
        initGrid();
        root.setTop(header);
        root.setCenter(gridPane);
        setTitle("Emploi du groupe " + this.groupe);
        Scene scene = new Scene(root, 1500, 500);
        this.setScene(scene);
        this.show();
    }

    private void addTemps() {
        temps.add("8->10");
        temps.add("10->12");
        temps.add("14->16");
        temps.add("16->18");
    }

    private void addJours() {
        jours.add("jour/Heure");
        jours.add("Lundi");
        jours.add("Mardi");
        jours.add("Mercredi");
        jours.add("Jeudi");
        jours.add("Vendredi");
        jours.add("Samedi");
    }

    private void header() {
        algoUsed.setText(algoUtiliser);
        algoUsed.setStyle("-fx-padding: 10;" +
                "-fx-font-size: 20;"+
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: aliceblue;");
        titre.setText("Temps d'execution en milli : " + this.tempsExecution / 1000000);
        titre.setFont(Font.font("Verdana", FontPosture.ITALIC, 20));
        valeurFnOj.setText("La valeur du fonction objectif :" + this.fonctionObjectif);
        valeurFnOj.setFont(Font.font("Verdana", FontPosture.ITALIC, 20));
        header.getChildren().addAll(algoUsed, titre, valeurFnOj);
        header.setAlignment(Pos.CENTER);
        header.setSpacing(30);
        header.setPadding(new Insets(30));
        header.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    }

    private void initGrid() {
        Label lblJour;
        Label lblSeance;
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane.setGridLinesVisible(true);

        for (int jour = 0; jour < 7; jour++) {
            lblJour = new Label(jours.get(jour));
            lblJour.setPadding(new Insets(10));
            gridPane.add(lblJour, 0, jour);
            if (jour == 0) {
                for (int i = 0; i < 4; i++) {
                    int column = i + 1;
                    Label label = new Label(temps.get(i));
                    label.setAlignment(Pos.CENTER);
                    label.setPadding(new Insets(15));
                    gridPane.add(label, column, 0);
                }
            }

            int i = 1;
            if (jour != 6) {
                for (int seance = (jour) * 4; seance < (jour) * 4 + 4; seance++) {
                    lblSeance = new Label(emploi.printSeance(seance, groupe));
                    lblSeance.setPadding(new Insets(10));
                    int row = jour + 1;
                    gridPane.add(lblSeance, i, row);
                    i++;
                }
            }
        }
    }

}


