package testi;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import simu.framework.Moottori;
import simu.framework.Trace;
import simu.model.OmaMoottori;

import javax.swing.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args); // GUI, e.g. start(Stage primaryStage)
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            primaryStage.setTitle("Bussiterminaali simulaatio"); // Asetetaan ohjelmalle title nimi

            // Simulointi ajan teksti
            Text simulointiAikaText = new Text("Aseta simouloinnin ajan");
            // Simulointi ajan fieldi
            TextField setSimulointiaika = new TextField("1000");
            setSimulointiaika.setMaxWidth(80);

            // Bussien määrä teksti
            Text bussienMaaraText = new Text("Aseta bussien määrän");
            // Bussien määrä fieldi
            TextField setBussienMaara = new TextField("5");
            setBussienMaara.setMaxWidth(80);

            // Textarea outputille
            TextArea ta = new TextArea();
            ta.setEditable(false);
            ta.setMaxWidth(350);
            ta.setMaxHeight(200);

            // Käynnistä simulaatio nappi
            Button buttonStart = new Button("Käynnistä simulaatio");

            // onclick eventti napille
            buttonStart.setOnAction(actionEvent ->  {
                Trace.setTraceLevel(Trace.Level.INFO); // https://docs.oracle.com/javase/7/docs/technotes/guides/jweb/jcp/tracing_logging.html#tracing
                OmaMoottori m = new OmaMoottori(Integer.parseInt(setBussienMaara.getText())); // Luodaan uusi Omamoottori olio
                m.setSimulointiaika(Double.parseDouble(setSimulointiaika.getText())); // Asetetaan aika, kuinka kauan simulaatio juoksee
                m.aja(); // OmaMoottori.aja
                ta.setText(m.tuloksetGUI());
            });

            StackPane stack = new StackPane(simulointiAikaText, setSimulointiaika, bussienMaaraText, setBussienMaara, ta, buttonStart);

            // Alignments
            StackPane.setAlignment(buttonStart, Pos.BOTTOM_RIGHT);
            StackPane.setAlignment(simulointiAikaText, Pos.TOP_LEFT);
            StackPane.setAlignment(setSimulointiaika, Pos.TOP_LEFT);
            StackPane.setAlignment(bussienMaaraText, Pos.TOP_LEFT);
            StackPane.setAlignment(setBussienMaara, Pos.TOP_LEFT);
            StackPane.setAlignment(ta, Pos.TOP_RIGHT);
            // Margins
            StackPane.setMargin(buttonStart, new Insets(0, 10, 10, 0));
            StackPane.setMargin(simulointiAikaText, new Insets(10, 10, 10, 10));
            StackPane.setMargin(setSimulointiaika, new Insets(30, 10, 10, 10));
            StackPane.setMargin(bussienMaaraText, new Insets(70, 10, 10, 10));
            StackPane.setMargin(setBussienMaara, new Insets(90, 10, 10, 10));
            StackPane.setMargin(ta, new Insets(10, 10, 10, 10));

            Scene scene = new Scene(stack, 700, 300); // Luo uuden scene jonka parametri on HBox nimeltään root ja asettaa leveyden ja korkeuden
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            // Jos jokin ei mene läpi, niin heittää stacktrace, eli virheet consoleen
            e.printStackTrace();
        }
    }
}