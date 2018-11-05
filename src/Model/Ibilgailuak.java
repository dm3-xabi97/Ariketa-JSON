/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author DM3-2-11
 */
public class Ibilgailuak {

    public int id;
    public String modeloa, marka, matrikula;

    public Ibilgailuak() {

    }

    public Ibilgailuak(int id, String modeloa, String marka, String matrikula) { //derrigortuta nago, ezta? public jartzera beste pakete batetik sortuko dudalako?
        this.id = id;
        this.modeloa = modeloa;
        this.marka = marka;
        this.matrikula = matrikula;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModeloa() {
        return modeloa;
    }

    public void setModeloa(String modeloa) {
        this.modeloa = modeloa;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public String getMatrikula() {
        return matrikula;
    }

    public void setMatrikula(String matrikula) {
        this.matrikula = matrikula;
    }

}
