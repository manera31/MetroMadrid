package models;
// Generated 3 dic. 2019 20:13:33 by Hibernate Tools 5.2.12.Final

/**
 * TViajes generated by hbm2java
 */
public class TViajes implements java.io.Serializable {

	private int codViaje;
	private TEstaciones TEstacionesByEstacionorigen;
	private TEstaciones TEstacionesByEstaciondestino;
	private String nombre;

	public TViajes() {
	}

	public TViajes(int codViaje, String nombre) {
		this.codViaje = codViaje;
		this.nombre = nombre;
	}

	public TViajes(int codViaje, TEstaciones TEstacionesByEstacionorigen, TEstaciones TEstacionesByEstaciondestino,
			String nombre) {
		this.codViaje = codViaje;
		this.TEstacionesByEstacionorigen = TEstacionesByEstacionorigen;
		this.TEstacionesByEstaciondestino = TEstacionesByEstaciondestino;
		this.nombre = nombre;
	}

	public int getCodViaje() {
		return this.codViaje;
	}

	public void setCodViaje(int codViaje) {
		this.codViaje = codViaje;
	}

	public TEstaciones getTEstacionesByEstacionorigen() {
		return this.TEstacionesByEstacionorigen;
	}

	public void setTEstacionesByEstacionorigen(TEstaciones TEstacionesByEstacionorigen) {
		this.TEstacionesByEstacionorigen = TEstacionesByEstacionorigen;
	}

	public TEstaciones getTEstacionesByEstaciondestino() {
		return this.TEstacionesByEstaciondestino;
	}

	public void setTEstacionesByEstaciondestino(TEstaciones TEstacionesByEstaciondestino) {
		this.TEstacionesByEstaciondestino = TEstacionesByEstaciondestino;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
