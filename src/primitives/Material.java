package primitives;

/**
 * A class representing a material in a ray tracer.
 */
public class Material {
    private Double3 Kd = Double3.ZERO;

    private Double3 Ks = Double3.ZERO;

    private int nShininess = 0;

    /**
     * Sets the diffuse color of the material.
     *
     * @param Kd the diffuse color
     * @return this material
     */
    public Material setKd(double Kd) {
        this.Kd = new Double3(Kd);
        return this;
    }

    /**
     * Sets the diffuse color of the material.
     *
     * @param Kd the diffuse color
     * @return this material
     */
    public Material setKd(Double3 Kd) {
        this.Kd = Kd;
        return this;
    }

    /**
     * Gets the diffuse color of the material.
     *
     * @return the diffuse color
     */
    public Double3 getKd() {
        return this.Kd;
    }

    /**
     * Sets the specular color of the material.
     *
     * @param Ks the specular color
     * @return this material
     */
    public Material setKs(double Ks) {
        this.Ks = new Double3(Ks);
        return this;
    }

    /**
     * Sets the specular color of the material.
     *
     * @param Ks the specular color
     * @return this material
     */
    public Material setKs(Double3 Ks) {
        this.Ks = Ks;
        return this;
    }

    /**
     * Gets the specular color of the material.
     *
     * @return the specular color
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
}