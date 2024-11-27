package TelExpress_SA;

public class Client {
    String nombre;
    String telefonoHash; // Número de teléfono almacenado con Hash + SALT
    String barrio;
    double monto;

    public Client(String nombre, String telefonoHash, String barrio, double monto) {
        this.nombre = nombre;
        this.telefonoHash = telefonoHash;
        this.barrio = barrio;
        this.monto = monto;
    }
}
