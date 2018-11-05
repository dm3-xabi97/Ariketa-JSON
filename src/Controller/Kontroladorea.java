/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Ibilgailuak;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.json.*;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;
import org.json.simple.parser.ParseException;

public class Kontroladorea {

    String modeloa, marka, matrikula;
    int id;

    public ObservableList<Ibilgailuak> jsonIrakurriObjetuarekin(File fitxategia) throws FileNotFoundException, IOException, ParseException {
        ObservableList<Ibilgailuak> ibilg = FXCollections.observableArrayList();
        JsonReader reader = Json.createReader(new FileInputStream(fitxategia));
        JsonArray arrIbilgailua = reader.readArray();
        reader.close();
        for (int i = 0; i < arrIbilgailua.size(); i++) {
            JsonObject ibilBakoitza = (JsonObject) arrIbilgailua.getJsonObject(i);
            Ibilgailuak ibil = new Ibilgailuak();
            ibil.setId(Integer.parseInt(ibilBakoitza.getString("id")));
            ibil.setModeloa(ibilBakoitza.getString("modeloa"));
            ibil.setMarka(ibilBakoitza.getString("marka"));
            ibil.setMatrikula(ibilBakoitza.getString("matrikula"));
            ibilg.add(ibil);
        }
        return ibilg;
    }

    public static void GordeObjetuarekin(ObservableList<Ibilgailuak> lista, File fitx) throws FileNotFoundException {
        JsonWriter jsonWriter = null;
        try {
            JsonArrayBuilder arraya = Json.createArrayBuilder();
            JsonObjectBuilder obj1 = Json.createObjectBuilder();
            for (Ibilgailuak ibil : lista) {
                obj1.add("id", String.valueOf(ibil.getId()));
                obj1.add("modeloa", ibil.getModeloa());
                obj1.add("marka", ibil.getMarka());
                obj1.add("matrikula", ibil.getMatrikula());

                JsonObject jsonObjIbil = obj1.build();
                arraya.add(jsonObjIbil);
            }
            JsonArray jsonArrayIbil = arraya.build();
            jsonWriter = Json.createWriter(new FileOutputStream(fitx, false));
            jsonWriter.write(jsonArrayIbil);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Ibilgailuak> irakurriStreamJSON(File fitx) throws FileNotFoundException {
        String key = null, marka = null, modeloa = null, matrikula = null;
        int id = 0;
        JsonParser parser = Json.createParser(new FileReader(fitx));
        ObservableList<Ibilgailuak> ibilg = FXCollections.observableArrayList();
        while (parser.hasNext()) {
            JsonParser.Event event = parser.next();
            switch (event) {
                case START_ARRAY:
                case END_ARRAY:
                case START_OBJECT:
                    break;
                case END_OBJECT:
                    Ibilgailuak ibil = new Ibilgailuak(id, modeloa, marka, matrikula);
                    ibilg.add(ibil);
                    break;
                case VALUE_FALSE:
                case VALUE_NULL:
                case VALUE_TRUE:
                case KEY_NAME:
                    key = parser.getString();
                    break;
                case VALUE_STRING:
                case VALUE_NUMBER:
                    switch (key) {
                        case "id":
                            id = Integer.parseInt(parser.getString());
                        case "modeloa":
                            modeloa = parser.getString();
                            break;
                        case "marka":
                            marka = parser.getString();
                            break;
                        case "matrikula":
                            matrikula = parser.getString();
                            break;
                    }
                    break;
            }
        }
        return ibilg;
    }

    public static void datuakFitxategianGorde(ObservableList<Ibilgailuak> data, File fitx) throws IOException {

        JsonGenerator gen = Json.createGenerator(new FileWriter(fitx));
        gen.writeStartArray();
        for (Ibilgailuak ibil : data) {
            gen.writeStartObject()
                    .write("id", ibil.getId())
                    .write("modeloa", ibil.getModeloa())
                    .write("marka", ibil.getMarka())
                    .write("matrikula", ibil.getMatrikula())
                    .writeEnd();
        }
        gen.writeEnd();
        gen.close();
    }
}
