package MapReduce;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Esta clase reprensenta un Punto .
 *
 * @author Jaime Pina Cambero
 * @version: 25/10/2017
 */
public class Punto {

 
  private Integer satelite;  
  private Double julianaDay;  
  private Double latitud;  
  private Double longitud;  
  private Double velocidad_viento;  
  private Double direccion_viento; 
  private Double velocidad_viento_media;
  private Integer nVelocidades;

  /**
   * Incrementar velocidad viento media.
   * Este metodo recibe una velocidad y la suma a la media (acumulador)
   * Tambien suma 1 a nvelocidades (contador)
   * @param vmedia the vmedia
   */
  public void incrementarVelocidadVientoMedia(Double vmedia) {
    velocidad_viento_media += vmedia;
    nVelocidades += 1;
  }
  

  @Override
  public String toString() {
    //Elimina el .0 a la fecha Juliana
    String date = julianaDay.toString();
    date = date.replace(".0", "");

    //Crea un calendar para transformar de fecha Juliana a Gregoriana
    Calendar cal = new GregorianCalendar();
    cal.set(Calendar.YEAR, Integer.parseInt(date.substring(0, 4)));
    cal.set(Calendar.DAY_OF_YEAR, Integer.parseInt(date.substring(4)));

    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(satelite);
    stringBuilder.append(",");
    stringBuilder.append(cal.get(Calendar.YEAR));
    stringBuilder.append("-");
    stringBuilder.append(cal.get(Calendar.MONTH)+1);
    stringBuilder.append("-");
    stringBuilder.append(cal.get(Calendar.DAY_OF_MONTH));
    stringBuilder.append(",");
    stringBuilder.append(date);
    stringBuilder.append(",");
    stringBuilder.append(latitud);
    stringBuilder.append(",");
    stringBuilder.append(longitud);
    stringBuilder.append(",");
    stringBuilder.append(direccion_viento);
    stringBuilder.append(",");
    stringBuilder.append(velocidad_viento);
    stringBuilder.append(",");
    stringBuilder.append(String.format("%.2f",velocidad_viento_media / nVelocidades));
    return stringBuilder.toString();
  }
  
  /**--------------SETS Y GETS-------------- */

  /**
   * Gets the satelite.
   * @return the satelite
   */
  public Integer getSatelite() {
    return satelite;
  }

  /**
   * Sets the satelite.
   * @param satelite the new satelite
   */
  public void setSatelite(Integer satelite) {
    this.satelite = satelite;
  }

  /**
   * Gets the juliana day.
   * @return the juliana day
   */
  public Double getJulianaDay() {
    return julianaDay;
  }

  /**
   * Sets the juliana day.
   * @param julianaDay the new juliana day
   */
  public void setJulianaDay(Double julianaDay) {
    this.julianaDay = julianaDay;
  }

  /**
   * Gets the latitud.
   * @return the latitud
   */
  public Double getLatitud() {
    return latitud;
  }

  /**
   * Sets the latitud.
   * @param latitud the new latitud
   */
  public void setLatitud(Double latitud) {
    this.latitud = latitud;
  }

  /**
   * Gets the longitud.
   * @return the longitud
   */
  public Double getLongitud() {
    return longitud;
  }

  /**
   * Sets the longitud.
   * @param longitud the new longitud
   */
  public void setLongitud(Double longitud) {
    this.longitud = longitud;
  }

  /**
   * Gets the velocidad viento.
   * @return the velocidad viento
   */
  public Double getVelocidad_viento() {
    return velocidad_viento;
  }

  /**
   * Sets the velocidad viento.
   * @param velocidad_viento the new velocidad viento
   */
  public void setVelocidad_viento(Double velocidad_viento) {
    this.velocidad_viento = velocidad_viento;
  }

  /**
   * Gets the direccion viento.
   * @return the direccion viento
   */
  public Double getDireccion_viento() {
    return direccion_viento;
  }

  /**
   * Sets the direccion viento.
   * @param direccion_viento the new direccion viento
   */
  public void setDireccion_viento(Double direccion_viento) {
    this.direccion_viento = direccion_viento;
  }


  /**
   * Gets the velocidad viento media.
   * @return the velocidad viento media
   */
  public Double getVelocidad_viento_media() {
    return velocidad_viento_media;
  }

  /**
   * Sets the velocidad viento media.
   * @param velocidad_viento_media the new velocidad viento media
   */
  public void setVelocidad_viento_media(Double velocidad_viento_media) {
    this.velocidad_viento_media = velocidad_viento_media;
  }

  /**
   * Gets the n velocidades.
   * @return the n velocidades
   */
  public Integer getnVelocidades() {
    return nVelocidades;
  }

  /**
   * Sets the n velocidades.
   * @param nVelocidades the new n velocidades
   */
  public void setnVelocidades(Integer nVelocidades) {
    this.nVelocidades = nVelocidades;
  }

}
