package primitives;

/**
 * A class representing a material in a ray tracer.
 */
public class Material {
    private Double3 Kd = Double3.ZERO;

    private Double3 Ks = Double3.ZERO;

    /**
     * The attenuation coefficient of transparencys
     */
    public Double3 kT = Double3.ZERO;

    /**
     * Attenuation coefficient of reflection
     */
    public Double3 kR = Double3.ZERO;

    private int nShininess = 0;

    /**
     * 
     *
     * @param Kd
     * @return this material
     */
    public Material setKd(double Kd) {
        this.Kd = new Double3(Kd);
        return this;
    }

    /**
     * 
     *
     * @param Kd 
     * @return this material
     */
    public Material setKd(Double3 Kd) {
        this.Kd = Kd;
        return this;
    }

    /**
     * 
     *
     * @return
     */
    public Double3 getKd() {
        return this.Kd;
    }

    /**
     * 
     *
     * @param Ks 
     * @return this material
     */
    public Material setKs(double Ks) {
        this.Ks = new Double3(Ks);
        return this;
    }

    /**
     * 
     *
     * @param Ks 
     * @return this material
     */
    public Material setKs(Double3 Ks) {
        this.Ks = Ks;
        return this;
    }

    /**
     * 
     *
     * @return 
     */
    public Double3 getKs() {
        return this.Ks;
    }

    /**
     * Sets the shininess of the material.
     *
     * @param nShininess the shininess
     * @return this material
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }

    /**
     * Returns the shininess of the material.
     *
     * @return the shininess
     */
    public int getnShininess() {
        return this.nShininess;
    }

    /**
     * 
     * @param kt
     * @return this object
     */
    public Material setkT(double kt) {
        this.kT = new Double3(kt);
        return this;
    }

    /**
     *
     * @param kt
     * @return this object
     */
    public Material setkT(Double3 kt) {
        this.kT = kt;
        return this;
    }

    /**
     * 
     * @param kr
     * @return this object
     */
    public Material setkR(double kr) {
        this.kR = new Double3(kr);
        return this;
    }

    /**
     * 
     * @param kr
     * @return this object
     */
    public Material setkR(Double3 kr) {
        this.kR = kr;
        return this;
    }


}