/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.Kontroladorea;
import Model.Ibilgailuak;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.converter.IntegerStringConverter;
import javax.xml.parsers.ParserConfigurationException;
import org.json.simple.parser.ParseException;
import org.xml.sax.SAXException;

/**
 *
 * @author DM3-2-11
 */
public class MainWindow extends Application {

    private final TableView<Ibilgailuak> table = new TableView<>();
    final HBox hb = new HBox();
    ObservableList<Ibilgailuak> data;

    @Override
    public void start(Stage stage) throws IOException, ParserConfigurationException, SAXException, FileNotFoundException, ParseException {
        Scene scene = new Scene(new Group());

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Aukeratu fitxategia");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON file (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);
        File aukeratutakoa = fileChooser.showOpenDialog(stage);
        Kontroladorea kontrola = new Kontroladorea();
        int tamaina = (int) aukeratutakoa.length();
        if (tamaina < 1000) {
            data = kontrola.jsonIrakurriObjetuarekin(aukeratutakoa);
            System.out.println("Objetua");
        } else {
            data = kontrola.irakurriStreamJSON(aukeratutakoa);
            System.out.println("Stream");
        }

        stage.setTitle("Kontzesionarioko Datuen Taula");
        stage.setWidth(550);
        stage.setHeight(550);
        final Label label = new Label("Ibilgailuak");
        label.setFont(new Font("Arial", 20));

        table.setEditable(false);

        TableColumn<Ibilgailuak, Integer> IdZut = new TableColumn<>("Id");
        IdZut.setMinWidth(100);
        IdZut.setCellValueFactory(new PropertyValueFactory<Ibilgailuak, Integer>("id"));
        IdZut.setCellFactory(TextFieldTableCell.<Ibilgailuak, Integer>forTableColumn(new IntegerStringConverter()));
        IdZut.setOnEditCommit((TableColumn.CellEditEvent<Ibilgailuak, Integer> t) -> {
            ((Ibilgailuak) t.getTableView().getItems().get(t.getTablePosition().getRow())).setId(t.getNewValue());
        });

        TableColumn<Ibilgailuak, String> ModeloZut = new TableColumn<>("Izena");
        ModeloZut.setMinWidth(100);
        ModeloZut.setCellValueFactory(new PropertyValueFactory<>("modeloa"));
        ModeloZut.setCellFactory(TextFieldTableCell.<Ibilgailuak>forTableColumn());
        ModeloZut.setOnEditCommit((TableColumn.CellEditEvent<Ibilgailuak, String> t) -> {
            ((Ibilgailuak) t.getTableView().getItems().get(t.getTablePosition().getRow())).setModeloa(t.getNewValue());
        });

        TableColumn<Ibilgailuak, String> MarkaZut = new TableColumn<>("Marka");
        MarkaZut.setMinWidth(100);
        MarkaZut.setCellValueFactory(new PropertyValueFactory<>("marka"));
        MarkaZut.setCellFactory(TextFieldTableCell.<Ibilgailuak>forTableColumn());
        MarkaZut.setOnEditCommit((TableColumn.CellEditEvent<Ibilgailuak, String> t) -> {
            ((Ibilgailuak) t.getTableView().getItems().get(t.getTablePosition().getRow())).setMarka(t.getNewValue());
        });

        TableColumn<Ibilgailuak, String> MatrikulaZut = new TableColumn<>("Matrikula");
        MatrikulaZut.setMinWidth(200);
        MatrikulaZut.setCellValueFactory(
                new PropertyValueFactory<>("matrikula"));
        MatrikulaZut.setCellFactory(TextFieldTableCell.<Ibilgailuak>forTableColumn());
        MatrikulaZut.setOnEditCommit((TableColumn.CellEditEvent<Ibilgailuak, String> t) -> {
            ((Ibilgailuak) t.getTableView().getItems().get(t.getTablePosition().getRow())).setMatrikula(t.getNewValue());
        });

        table.setItems(data);
        table.getColumns().addAll(IdZut, ModeloZut, MarkaZut, MatrikulaZut);
        final TextField addId = new TextField();
        addId.setPromptText("Id");
        addId.setMaxWidth(ModeloZut.getPrefWidth());

        final TextField addModeloa = new TextField();
        addModeloa.setPromptText("Modeloa");
        addModeloa.setMaxWidth(ModeloZut.getPrefWidth());

        final TextField addMarka = new TextField();
        addMarka.setMaxWidth(MarkaZut.getPrefWidth());
        addMarka.setPromptText("Marka");

        final TextField addMatrikula = new TextField();
        addMatrikula.setMaxWidth(MatrikulaZut.getPrefWidth());
        addMatrikula.setPromptText("Matrikula");

        final Button addButton = new Button("Gehitu");
        addButton.setStyle("-fx-base: lightgreen;");        //kolorea aldatzeko botoiari
        addButton.setOnAction((ActionEvent e) -> {
            if (addId.getText().isEmpty() || addModeloa.getText().isEmpty() || addMarka.getText().isEmpty() || addMatrikula.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setHeaderText("Datuak");
                alert.setContentText("Datu guztiak sartu behar dituzu!");
                alert.showAndWait();
            } else {
                int id = Integer.parseInt(addId.getText());
                Ibilgailuak i = new Ibilgailuak(id, addModeloa.getText(), addMarka.getText(), addMatrikula.getText());
                data.add(i);

                addId.clear();
                addModeloa.clear();
                addMarka.clear();
                addMatrikula.clear();
                if (data.size() < 11) {
                    try {
                        Kontroladorea.GordeObjetuarekin(data, aukeratutakoa);
                        System.out.println("Sartuta objetua");
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    try {
                        Kontroladorea.datuakFitxategianGorde(data, aukeratutakoa);
                        System.out.println("Sartuta stream");
                    } catch (IOException ex) {
                        Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }

        });

        final Button removeButton = new Button("Ezabatu");
        removeButton.setStyle("-fx-base: red;");
        removeButton.setOnAction((ActionEvent e) -> {
            try {
                Ibilgailuak ibilgailu = table.getSelectionModel().getSelectedItem();
                data.remove(ibilgailu);
                if (data.size() < 11) {
                    Kontroladorea.GordeObjetuarekin(data, aukeratutakoa);
                    System.out.println("Ezabatuta objetua");
                } else {
                    Kontroladorea.datuakFitxategianGorde(data, aukeratutakoa);
                    System.out.println("Ezabatuta stream");
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        hb.getChildren().addAll(addId, addModeloa, addMarka, addMatrikula, addButton, removeButton);
        hb.setSpacing(3);
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table, hb);
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
        stage.setOnCloseRequest((WindowEvent event) -> {
            try {
                Kontroladorea.GordeObjetuarekin(data, aukeratutakoa);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
