package UI;

import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JToggleButton;
import java.awt.FlowLayout;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

/**
 * Esta clase es un panel que forma parte de la UI
 * 
 * @author: Jaime Pina Cambero
 * @version: 1/09/2017
 */
public class SeleccionManual extends JPanel {
  private JTextField conf;

  private JPanel contentPane;
  private JToggleButton select;
  private JPanel panel;
  private JPanel panel_1;
  private JLabel label_2;
  private JScrollPane scrollPane;
  private JTextArea txtManual;

  public String getConf() {
    return conf.getText();
  }

  /**
   * Create the panel.
   */
  public SeleccionManual() {

    panel = new JPanel();
    panel.setLayout(new GridLayout(0, 3, 0, 0));

    JLabel lblModoDeInsercin = new JLabel("Modo inserción:");
    panel.add(lblModoDeInsercin);

    select = new JToggleButton("Archivo / Manual");
    panel.add(select);

    label_2 = new JLabel("");
    panel.add(label_2);

    // Creamos el objeto JFileChooser
    final JFileChooser fc2 = new JFileChooser();

    JLabel lblArchivoDeConfiguracin = new JLabel("Fichero configuración:");
    panel.add(lblArchivoDeConfiguracin);

    conf = new JTextField();
    panel.add(conf);
    conf.setColumns(10);

    final JButton btnBuscarCnf = new JButton("Buscar");
    panel.add(btnBuscarCnf);

    panel_1 = new JPanel();

    scrollPane = new JScrollPane();
    panel_1.add(scrollPane);

    txtManual = new JTextArea();
    txtManual.setText("Ejemplo: \n-46.5,100.48,-57.9,96");
    txtManual.setEnabled(false);
    txtManual.setColumns(38);
    txtManual.setRows(10);
    scrollPane.setViewportView(txtManual);
    setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
    add(panel);
    add(panel_1);

    btnBuscarCnf.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        // Abrimos la ventana, guardamos la opcion seleccionada por el usuario
        int seleccion = fc2.showOpenDialog(contentPane);

        // Si el usuario, pincha en aceptar
        if (seleccion == JFileChooser.APPROVE_OPTION) {

          // Seleccionamos el fichero
          File fichero = fc2.getSelectedFile();

          // Ecribe la ruta del fichero seleccionado en el campo de texto
          conf.setText(fichero.getAbsolutePath());

        }
      }
    });

    select.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        // conf.setText("selected");

        if (select.isSelected()) {
          select.setText("Manual");
          conf.setEnabled(false);
          btnBuscarCnf.setEnabled(false);
          txtManual.setEnabled(true);

          // System.out.println(conf.getText());
        } else {
          select.setText("Archivo");
          conf.setEnabled(true);
          btnBuscarCnf.setEnabled(true);
          txtManual.setEnabled(false);

        }

      }
    });

    fc2.setFileSelectionMode(JFileChooser.FILES_ONLY);

  }

  public Boolean seleccionado() {
    return select.isSelected();
  }

  public void escribirFichero() throws IOException {

    String ruta = "Configuracion.txt";
    File archivo = new File(ruta);
    BufferedWriter bw;

    if (archivo.exists()) {
      archivo.delete();
    }
    bw = new BufferedWriter(new FileWriter(archivo));
    bw.write(txtManual.getText());
    bw.close();

  }
}
