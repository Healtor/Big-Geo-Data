package MapReduce;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.TreeMap;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * Esta clase ejecuta el proceso Reduce .
 *
 * @author Jaime Pina Cambero
 * @version: 25/10/2017
 */
public class ReduceClass extends Reducer<Text, Text, Text, Text> {

  /**
   * Metodo reduce.
   * Recibe un conjunto de puntos de una unica region.
   * Calcula la velocidad maxima y la media por cada dia
   * Por ultimo, genera un archivo .arff y llama a weka
   *
   * @param key la clave (una region)
   * @param values los valores (todos los puntos de la region)
   * @param context the context
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws InterruptedException the interrupted exception
   */
  @Override
  protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
    
    String rutaDest = Main_MapReduce.SALIDA + "/Region" + key.toString() + ".arff";
    TreeMap<Double, Punto> tmapMax = new TreeMap<Double, Punto>();

    FileWriter ficheroMax = new FileWriter(rutaDest);  
    PrintWriter pwMax = new PrintWriter(ficheroMax); 
    
    insertHeadARFF(pwMax);

    Iterator<Text> valuesIt = values.iterator();    

    //Recorre todos los puntos de la region
    while (valuesIt.hasNext()) {
      
    //Transformacion del value a punto
      Punto p=new Punto();
      String line = valuesIt.next().toString();
      StringTokenizer st = new StringTokenizer(line,",");    
      
      p.setSatelite(Integer.parseInt((String) st.nextElement()));
      p.setJulianaDay(Double.parseDouble(st.nextToken()));
      p.setLatitud(Double.parseDouble(st.nextToken()));
      p.setLongitud(Double.parseDouble(st.nextToken()));
      p.setVelocidad_viento(Double.parseDouble(st.nextToken()));
      p.setDireccion_viento(Double.parseDouble(st.nextToken()));
      
      //Inserta en el arbol       
      if(tmapMax.get(p.getJulianaDay()) == null){
        p.setVelocidad_viento_media(p.getVelocidad_viento());
        p.setnVelocidades(1);
        
        tmapMax.put(p.getJulianaDay(), p);
      }else{
        if(tmapMax.get(p.getJulianaDay()).getVelocidad_viento()<p.getVelocidad_viento()){
          //Actualiza la maxima
          tmapMax.get(p.getJulianaDay()).setVelocidad_viento(p.getVelocidad_viento());      
        }
        //actualiza el contador y suma 1 a nVelocidades
        tmapMax.get(p.getJulianaDay()).incrementarVelocidadVientoMedia(p.getVelocidad_viento());
        
      }
      
    }
       
    //Recorre el arbol y lo imprime
    Iterator it1 = tmapMax.keySet().iterator();
    while(it1.hasNext()){
      Double key1 =  (Double) it1.next();
      pwMax.println(tmapMax.get(key1).toString());
    }

    ficheroMax.close();
    
    runWeka(rutaDest);

  }

  /**
   * Inserta la cabecera en los ficheros arff.
   *
   * @param pw es el printwrite sobre el que se inserta la cabecera
   */
  private void insertHeadARFF(PrintWriter pw) {
    pw.println("% ARFF");
    pw.println("% Fichero para Weka con los datos del RS de 2016 y AS de 2007");

    pw.println("@RELATION rapidscat_ascat");
    pw.println("@ATTRIBUTE satelite numeric");
    pw.println("@ATTRIBUTE fechaGregorian date \"yyyy-MM-dd\"");
    pw.println("@ATTRIBUTE fechaJuliana numeric");
    pw.println("@ATTRIBUTE latitud numeric");
    pw.println("@ATTRIBUTE longitud numeric");
    pw.println("@ATTRIBUTE dirección_viento numeric");
    pw.println("@ATTRIBUTE velocidad_vientoMax numeric");
    pw.println("@ATTRIBUTE velocidad_vientoMed numeric");
    pw.println("@DATA");
  }

  /**
   * Este metodo se encarga de llamar llamar a Weka y ejecutar el algoritmo
   * 
   * @param rutaFuente the ruta fuente
   */
  private static void runWeka(String rutaFuente) {

    String command = "java -classpath $CLASSPATH:/home/shadoop/weka-3-8-1/weka.jar weka.Run WekaForecaster -W \"weka.classifiers.functions.LinearRegression -S 0 -R 1.0E-8 -num-decimal-places 4\" -t " +rutaFuente + " -F velocidad_vientoMax -L 1 -M 2 -G fechaGregorian -prime 2";
    String ruta = rutaFuente.substring(0, rutaFuente.length() - 5);
    System.out.println(command);
    String rutaFinal = ruta + ".txt";
    FileWriter fichero = null;
    try {
      fichero = new FileWriter(rutaFinal);
      String output = executeCommand(command);
      fichero.write(output);
    } catch (IOException e1) {
      e1.printStackTrace();
    }

    try {
      fichero.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  /**
   * Ejecuta el comando
   *
   * @param command the command
   * @return the string
   */
  private static String executeCommand(String command) {

    StringBuffer output = new StringBuffer();

    Process p;
    try {
      p = Runtime.getRuntime().exec(new String[]{"bash","-c", command});
      p.waitFor();
      BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

      String line = "";
      while ((line = reader.readLine()) != null) {
        output.append(line + "\n");
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    return output.toString();

  }
}