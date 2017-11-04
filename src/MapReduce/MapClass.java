package MapReduce;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * Esta es la clase del proceso Map. 
 * @version: 25/10/2017
 * @author Jaime Pina Cambero
 */
public class MapClass extends Mapper<LongWritable, Text, Text, Text>{
   
  /** Text que representa a la clave del proceso Reduce */
    private Text clave = new Text();
    
  /**
   * Esta funcion es el proceso map
   * Recibe un punto y decide qué hacer con él.
   *
   * @param key la clave
   * @param value el valor, un Punto o una linea del csv
   * @param context el contexto
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws InterruptedException the interrupted exception
   */
  @Override
  protected void map(LongWritable key, Text value, Context context)
      throws IOException, InterruptedException {
    
    Double lat,lon;
    Punto p=new Punto();
    
    //Transformacion del value a punto
    String line = value.toString();
    StringTokenizer st = new StringTokenizer(line,",");    
    
    p.setSatelite(Integer.parseInt((String) st.nextElement()));
    p.setJulianaDay(Double.parseDouble(st.nextToken()));
    p.setLatitud(Double.parseDouble(st.nextToken()));
    p.setLongitud(Double.parseDouble(st.nextToken()));
    p.setVelocidad_viento(Double.parseDouble(st.nextToken()));
    p.setDireccion_viento(Double.parseDouble(st.nextToken()));
    lat = p.getLatitud();
    lon = p.getLongitud();
  //  System.out.println(p);
    
    //--Creacion de lista de regiones--
    List<Region> listaR = new ArrayList<Region>();

    BufferedReader br = new BufferedReader(new FileReader(Main_MapReduce.CONF));
    String line2 = br.readLine();

    //Lee el fichero y llena la lista de regiones
    while (line2 != null) {
      Region r = new Region();
      
      StringTokenizer st2 = new StringTokenizer(line2, ",");

      // Punto1
      r.setLatPunto1(Float.parseFloat((String) st2.nextElement()));
      r.setLonPunto1(Float.parseFloat((String) st2.nextElement()));
      // Punto2
      r.setLatPunto2(Float.parseFloat((String) st2.nextElement()));
      r.setLonPunto2(Float.parseFloat((String) st2.nextElement()));

      line2 = br.readLine();
      
      listaR.add(r);
    }
    br.close();
    //----

    // Llama al reduce con el numero de la region asociada (clave)
    for (int i = 0; i < listaR.size(); i++) {
      if (lat >= listaR.get(i).getLatPunto1() & lat <= listaR.get(i).getLatPunto2()
          & lon >= listaR.get(i).getLonPunto1() & lon <= listaR.get(i).getLonPunto2()) {
        clave.set(String.valueOf(i));//pone como clave el numero de la region
        context.write(clave, value);
      }
    }
    
  }
  
  
}
