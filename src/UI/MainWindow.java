package UI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import MapReduce.Main_MapReduce;
import javax.swing.JButton;

/**
 * Esta clase es la encargada de generar la UI
 * @author: Jaime Pina Cambero
 * @version: 1/09/2017
*/
public class MainWindow extends JFrame {

	private JPanel contentPane;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		setTitle("TFG Jaime - MapReduce y Weka");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 498, 356);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		final Rutas rutas = new Rutas();
		contentPane.add(rutas, BorderLayout.NORTH);

		JButton btnIniciar = new JButton("Iniciar ");
		contentPane.add(btnIniciar, BorderLayout.SOUTH);

		final SeleccionManual seleccionManual = new SeleccionManual();
		contentPane.add(seleccionManual, BorderLayout.CENTER);

		btnIniciar.addActionListener(new ActionListener() {
			private String[] args;

      @Override
			public void actionPerformed(ActionEvent arg0) {
        args = new String[3];
        args[0]=rutas.getCsv();
        args[1]=rutas.getSalida();
        Main_MapReduce main =new Main_MapReduce();
        int resultado=2;
       
				try {
					if (!seleccionManual.seleccionado()) { // Archivo configuracion
					 
					  args[2]=seleccionManual.getConf();
            try {
             resultado=main.ejecutar(args);
            } catch (Exception e) {

              e.printStackTrace();
            }

          } else { // Seleccion manual

            seleccionManual.escribirFichero();
            args[2] = "Configuracion.txt";
            try {
              resultado=main.ejecutar(args);
            } catch (Exception e) {
              e.printStackTrace();
            }
					  
					}
					
					String mensaje = null;
					if(resultado==0){
					  mensaje ="Operación realizada con exito, mire el directorio de salida";
					}else if(resultado == 1){
					  mensaje ="Se ha producido algún error durante la ejecución";
					}					
					JOptionPane.showMessageDialog(null, mensaje);


				} catch (NumberFormatException | IOException e) {

					e.printStackTrace();
				}

			}
		});

	}



}
