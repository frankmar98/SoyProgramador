import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class SoyProgramador {

	private static File ficheroAbrir;

	public static void main(String[] args) {
		
		//todas las lineas de codigo
		ArrayList<String> codigo = new ArrayList<String>(); 

		//iniciar y configurar interfaz
		JFrame fr = new JFrame("Soy Programador: sientete Programador aporreando el teclado");
		JTextArea ta1 = new JTextArea();
		JScrollPane scroll1 = new JScrollPane(ta1);
		JTextArea ta2 = new JTextArea();
		JScrollPane scroll2 = new JScrollPane(ta2);
		JLabel lb1 = new JLabel("Abre uno o varios archivos de codigo:");
		JButton b1 = new JButton("Añadir archivo");
		JButton b2 = new JButton("Actualizar");
		JButton b3 = new JButton("Resetear");
		JScrollBar irAbajo = scroll1.getVerticalScrollBar(); //mantenerse abajo

		fr.setSize(800, 830);
		scroll1.setBounds(5, 155, 790, 640);
		lb1.setBounds(5, 5, 400, 10);
		scroll2.setBounds(5, 20, 640, 125);
		b1.setBounds(655, 20, 125, 35);
		b2.setBounds(655, 60, 125, 35);
		b3.setBounds(655, 100, 125, 35);

		scroll1.setFocusable(false);
		ta1.setFocusable(false);
		fr.setFocusable(true);
		fr.toFront();
		fr.requestFocus();
		
		ta1.setFont(new Font("monospaced", Font.BOLD, 16));
		ta1.setBackground(new Color(100, 0, 100));
		ta1.setForeground(new Color(204, 255, 51));
		ta1.setLineWrap(true);
		ta1.setWrapStyleWord(true);

		fr.add(scroll1);
		fr.add(scroll2);
		fr.add(lb1);
		fr.add(b1);
		fr.add(b2);
		fr.add(b3);

		fr.setLayout(null);
		fr.setVisible(true);

		//Abrir archivos de codigo exsitentes
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int seleccion = fileChooser.showOpenDialog(lb1);
				if (seleccion == JFileChooser.APPROVE_OPTION) {
					ficheroAbrir = fileChooser.getSelectedFile();
					ta2.append(ficheroAbrir.toString() + "\n");
				}
				fr.toFront();
				fr.requestFocus();
			}

		});

		//Extraer el codigo de todos los archivos abiertos
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String texto, ficherosParaAbrir[], linea;
				boolean esCorrecto=true;
				codigo.removeAll(codigo); //vaciar el codigo anterior
				ta1.setText("");
				texto = ta2.getText();
				ficherosParaAbrir = texto.split("\n"); //cada linea una ruta
				for (String ruta : ficherosParaAbrir) {
					BufferedReader br;
					br=null;
					try {
						br = new BufferedReader(new FileReader(ruta));
						while ((linea = br.readLine()) != null) {
							codigo.add(linea);
						}
						
					} catch (IOException ioe1) {
						System.out.println("Error al abrir fichero");
						esCorrecto = false;
					} finally {
						try {
							br.close();
						} catch (IOException ioe2) {
							System.out.println("Error al cerrar fichero");
							esCorrecto = false;
						} catch (NullPointerException npe1) {
							esCorrecto = false;
							System.out.println("No se ha podido abrir el fichero");
							JOptionPane.showMessageDialog(fr, "No se ha podido abrir el fichero");
						}
					}
				}
				if (esCorrecto) {
					JOptionPane.showMessageDialog(fr, "Codigo cargado correctamente");
				}
				fr.toFront();
				fr.requestFocus();
			}

		});
		
		//Resetear
		b3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				codigo.removeAll(codigo);
				ta1.setText("");
				ta2.setText("");
				fr.toFront();
				fr.requestFocus();
			}

		});

		
		
		//Escribir codigo sin sentido cuando se aporree el teclado
		fr.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				//estar siempre en la parte de abajo del scroll
				irAbajo.setValue(irAbajo.getMaximum());
				if (codigo.isEmpty()) {
					ta1.append("Abre uno o varios ficheros de codigo y pulsa actualizar\n");
				} else { //escrbir linea de codigo
					Collections.shuffle(codigo);
					ta1.append(codigo.get(0));
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}


			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

		});
	}

}
