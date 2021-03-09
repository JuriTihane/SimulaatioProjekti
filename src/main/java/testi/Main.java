package testi;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import simu.framework.Moottori;
import simu.framework.Trace;
import simu.model.OmaMoottori;

public class Main extends Application {

    public static void main(String[] args) {
        Trace.setTraceLevel(Trace.Level.INFO); // https://docs.oracle.com/javase/7/docs/technotes/guides/jweb/jcp/tracing_logging.html#tracing
        Moottori m = new OmaMoottori(); // Luodaan uusi Omamoottori olio
        m.setSimulointiaika(1000); // Asetetaan aika, kuinka kauan simulaatio juoksee
        m.aja(); // OmaMoottori.aja
        launch(args); // GUI, e.g. start(Stage primaryStage)
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            primaryStage.setTitle("Bussiterminaali simulaatio"); // Asetetaan ohjelmalle title nimi

            HBox root = createHBox(); // Kutsuu metodin createHBox() ja palautuksen nimi on sit root
            Scene scene = new Scene(root, 500, 500); // Luo uuden scene jonka parametri on HBox nimeltään root ja asettaa leveyden ja korkeuden
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            // Jos jokin ei mene läpi, niin heittää stacktrace, eli virheet consoleen
            e.printStackTrace();
        }
    }

    private HBox createHBox() {
        HBox hBox = new HBox(); // Luodaan uusi hbox https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/HBox.html
        hBox.setPadding(new Insets(15, 12, 15, 12)); // marginaalit ylä, oikea, ala, vasen
        hBox.setSpacing(10); // väliiin 10 pikseliä

        return hBox;
    }
}