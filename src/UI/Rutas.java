package UI;

import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.GridLayout;

/**
 * Esta clase es un panel que forma parte de la UI
 * @author: Jaime Pina Cambero
 * @version: 1/09/2017
*/
public class Rutas extends JPanel {
  private JTextField ficherosCsv;
  private JPanel contentPane;
  private JTextField salida;
  
  public String getCsv() {
    return ficherosCsv.getText();
  }

  public String getSalida() {
    return salida.getText();
  }


  /**
   * Create the panel.
   */
  public Rutas() {
    setLayout(new GridLayout(2, 4, 5, 5));
    
    JLabel lblNewLabel = new JLabel("Direc. ficheros .csv");
    add(lblNewLabel);
    
    ficherosCsv = new JTextField();
    add(ficherosCsv);
    ficherosCsv.setColumns(10);
    
    JButton btnBuscarCSV = new JButton("Buscar");
    add(btnBuscarCSV);
    
    JLabel lblDirectorioDeSalida = new JLabel("Directorio de salida");
    add(lblDirectorioDeSalida);
    
    salida = new JTextField();
    salida.setColumns(10);
    add(salida);
    
    JButton btnSalida = new JButton("Buscar");
    add(btnSalida);
    
    //Creamos el objeto JFileChooser
    final JFileChooser fc=new JFileChooser();
    
    //Indicamos lo que podemos seleccionar
    fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
     
    btnBuscarCSV.addActionListener(new ActionListener(){
      @Override
      public void actionPerformed(ActionEvent arg0) {
        
        //Abrimos la ventana, guardamos la opcion seleccionada por el usuario
        int seleccion=fc.showOpenDialog(contentPane);
         
        //Si el usuario, pincha en aceptar
        if(seleccion==JFileChooser.APPROVE_OPTION){
         
            //Seleccionamos el fichero
            File fichero=fc.getSelectedFile();

            //Ecribe la ruta del fichero seleccionado en el campo de texto
            ficherosCsv.setText(fichero.getAbsolutePath());

        }
      }
        });
    
    //Creamos el objeto JFileChooser
    final JFileChooser fc2=new JFileChooser();
    
    fc2.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    
    btnSalida.addActionListener(new ActionListener(){
      @Override
      public void actionPerformed(ActionEvent arg0) {
        
        //Abrimos la ventana, guardamos la opcion seleccionada por el usuario
        int seleccion=fc2.showOpenDialog(contentPane);
         
        //Si el usuario, pincha en aceptar
        if(seleccion==JFileChooser.APPROVE_OPTION){
         
            //Seleccionamos el fichero
            File fichero=fc2.getSelectedFile();

            //Ecribe la ruta del fichero seleccionado en el campo de texto
            salida.setText(fichero.getAbsolutePath());

        }
      }
        });

  }
}
