package MapReduce;

import java.io.File;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 * Esta es la clase principal del proceso MapReduce, se encarga de configurarlo y lanzarlo.
 * @version: 25/10/2017
 * @author Jaime Pina Cambero
 */
public class Main_MapReduce extends Configured implements Tool{
  
  /** Guarda la ruta del archivo de configuracion. */
  public static String CONF;
  
  /**Guarda la ruta del archivo de salida. */
  public static String SALIDA;
  

  
  /**
   * Ejecuta el proceso MapReduce
   *
   * @param args los argumentos
   * @return int salida de ejecucion
   * @throws Exception the exception
   */
  public int ejecutar(String[] args) throws Exception{
    //Guarda las rutas de los archivos de configuracion
    CONF=args[2];
    SALIDA=args[1];
    
    int exitCode = ToolRunner.run(new Main_MapReduce(), args);
    //System.exit(exitCode);
    return exitCode;
  }
 
  /**
   * Run method which schedules the Hadoop Job.
   *
   * @param args Arguments passed in main function
   * @return int salida de ejecucion
   * @throws Exception the exception
   */
  public int run(String[] args) throws Exception {
    
    if (args.length != 3) {
      System.err.printf("Usage: %s needs three arguments <RutaCSV> <RutaSalida> <RutaConfiguracion> files\n",
          getClass().getSimpleName());
      return 1;
    }
  
    //Inicialica el trabajo de Hadoop y configura el jar y el nombre del trabajo
    Job job = new Job();
    job.setJarByClass(Main_MapReduce.class);
    job.setJobName("TFG_Jaime");
    
    
    File f = new File("output");
    eliminarCarpeta(f);
    
    
    //Añade el input y el output
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path("output"));

    //Configura el tipo de las entradas/salidas de Map y Reduce
    job.setMapOutputKeyClass(Text.class);
    job.setMapOutputValueClass(Text.class);

    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(Text.class);
    
    job.setOutputFormatClass(TextOutputFormat.class);
    
    //Indica qué clase ejecuta el Map y cuál el Reduce
    job.setMapperClass(MapClass.class);
    job.setReducerClass(ReduceClass.class);
  
    //Espera a que la ejecucion termine
    int returnValue = job.waitForCompletion(true) ? 0 : 1;

    if (job.isSuccessful()) {
      System.out.println("Job was successful");
    } else if (!job.isSuccessful()) {
      System.out.println("Job was not successful");
    }

    return returnValue;
  }

  /**
   * Eliminar carpeta.
   *
   * @param pArchivo el archivo a eliminar
   */
  private static void eliminarCarpeta(File pArchivo) {
    if (!pArchivo.exists()) {
      return;
    }

    if (pArchivo.isDirectory()) {
      for (File f : pArchivo.listFiles()) {
        eliminarCarpeta(f);
      }
    }
    pArchivo.delete();
  }
}
