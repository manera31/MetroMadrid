package lists;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import org.hibernate.*;

import inserts.HibernateUtil;
import models.*;

public class ConsultaLineas extends JFrame {

	private JPanel contentPane;
	private ArrayList<TLineas> lineas;
	private JTextField numLinea;
	private JLabel nombre;
	private JButton btnLanzarConsulta;
	private JButton btnCancelarConsulta;
	private JButton btnPrimer, btnUltimo, btnSiguiente, btnAnterior, btnIr;
	private int posicion = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConsultaLineas frame = new ConsultaLineas();
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
	public ConsultaLineas() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 536, 352);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblConusltaTrenes = new JLabel("CONUSLTA LINEAS");
		lblConusltaTrenes.setBounds(30, 40, 200, 15);
		contentPane.add(lblConusltaTrenes);
		
		JLabel lblCodTren = new JLabel("COD LINEA");
		lblCodTren.setBounds(30, 100, 88, 15);
		contentPane.add(lblCodTren);
		
		JLabel lblNombre = new JLabel("NOMBRE");
		lblNombre.setBounds(30, 130, 66, 15);
		contentPane.add(lblNombre);
		
		numLinea = new JTextField("");
		numLinea.setBounds(179, 100, 66, 20);
		contentPane.add(numLinea);
		
		nombre = new JLabel("");
		nombre.setBounds(179, 130, 272, 15);
		contentPane.add(nombre);
		
		//BOTONES
		btnPrimer = new JButton("PRIMER");
		btnPrimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				posicion = 0;
				llenarCampos(0);
			}
		});
		btnPrimer.setEnabled(false);
		btnPrimer.setBounds(10, 266, 114, 25);
		contentPane.add(btnPrimer);
		
		btnAnterior = new JButton("ANTERIOR");
		btnAnterior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if((posicion - 1) >= 0) {
					posicion--;
					llenarCampos(posicion);
				} else {
					JOptionPane.showMessageDialog(null, "Primer registro alcanzado");
				}
			}
		});
		btnAnterior.setEnabled(false);
		btnAnterior.setBounds(140, 266, 114, 25);
		contentPane.add(btnAnterior);
		
		btnSiguiente = new JButton("SIGUIENTE");
		btnSiguiente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if((posicion + 1) <= (lineas.size()-1)) {
					posicion++;
					llenarCampos(posicion);
				} else {
					JOptionPane.showMessageDialog(null, "Ultimo registro alcanzado");
				}
			}
		});
		btnSiguiente.setEnabled(false);
		btnSiguiente.setBounds(270, 266, 114, 25);
		contentPane.add(btnSiguiente);
		
		btnUltimo = new JButton("�LTIMO");
		btnUltimo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				llenarCampos((lineas.size() - 1));
				posicion = lineas.size() - 1;
			}
		});
		btnUltimo.setEnabled(false);
		btnUltimo.setBounds(400, 266, 114, 25);
		contentPane.add(btnUltimo);
		
		btnLanzarConsulta = new JButton("LANZAR CONSULTA");
		btnLanzarConsulta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Session session = HibernateUtil.getSessionFactory().openSession();
				Transaction transaction = session.beginTransaction();
				Query<TLineas> query = session.createQuery("from TLineas");
				lineas = (ArrayList<TLineas>) query.list();
				transaction.commit();
				btnSiguiente.setEnabled(true);
				btnAnterior.setEnabled(true);
				btnPrimer.setEnabled(true);
				btnUltimo.setEnabled(true);
				llenarCampos(0);
				btnIr.setEnabled(true);
				session.close();
			}
		});
		btnLanzarConsulta.setBounds(312, 0, 202, 25);
		contentPane.add(btnLanzarConsulta);
		
		btnCancelarConsulta = new JButton("CANCELAR CONSULTA");
		btnCancelarConsulta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnSiguiente.setEnabled(false);
				btnAnterior.setEnabled(false);
				btnPrimer.setEnabled(false);
				btnUltimo.setEnabled(false);
				btnIr.setEnabled(false);
			}
		});
		btnCancelarConsulta.setBounds(312, 52, 202, 25);
		contentPane.add(btnCancelarConsulta);
		
		btnIr = new JButton("IR");
		btnIr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean aux = false;
				for(int i = 0 ; i < lineas.size() ; i++) {
					if(Integer.parseInt(numLinea.getText()) == lineas.get(i).getCodLinea()) {
						llenarCampos(i);
						posicion = i;
						aux = true;
					}
				}
				if(!aux) {
					JOptionPane.showMessageDialog(null, "La ID no se ha encontrado");
				}
			}
		});
		btnIr.setEnabled(false);
		btnIr.setBounds(248, 95, 47, 25);
		contentPane.add(btnIr);
	}
	
	private void llenarCampos(int posicion) {
		numLinea.setText(String.valueOf(lineas.get(posicion).getCodLinea()));
		nombre.setText(lineas.get(posicion).getNombre());
	}

}
