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

public class ConsultaLineaEstaciones extends JFrame {

	private JPanel contentPane;
	private ArrayList<TLineaEstacion> lineaEstaciones;
	private JTextField numLinea;
	private JTextField numEstacion;
	private JLabel orden;
	private JButton btnLanzarConsulta;
	private JButton btnCancelarConsulta;
	private JButton btnPrimer, btnUltimo, btnSiguiente, btnAnterior;
	private int posicion = 0;
	private JButton btnIr;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConsultaLineaEstaciones frame = new ConsultaLineaEstaciones();
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
	public ConsultaLineaEstaciones() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 536, 352);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblConusltaTrenes = new JLabel("CONUSLTA LINEA ESTACIONES");
		lblConusltaTrenes.setBounds(30, 40, 250, 15);
		contentPane.add(lblConusltaTrenes);
		
		JLabel lblCodTren = new JLabel("COD LINEA");
		lblCodTren.setBounds(30, 100, 88, 15);
		contentPane.add(lblCodTren);
		
		JLabel lblNombre = new JLabel("COD ESTACION");
		lblNombre.setBounds(30, 130, 100, 15);
		contentPane.add(lblNombre);
		
		JLabel lblTipo = new JLabel("ORDEN");
		lblTipo.setBounds(30, 160, 66, 15);
		contentPane.add(lblTipo);
		
		numLinea = new JTextField("");
		numLinea.setBounds(188, 100, 66, 20);
		contentPane.add(numLinea);
		
		numEstacion = new JTextField("");
		numEstacion.setBounds(188, 130, 139, 20);
		contentPane.add(numEstacion);
		
		orden = new JLabel("");
		orden.setBounds(188, 160, 139, 15);
		contentPane.add(orden);
		
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
				if((posicion + 1) <= (lineaEstaciones.size()-1)) {
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
		
		btnUltimo = new JButton("ÚLTIMO");
		btnUltimo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				llenarCampos((lineaEstaciones.size() - 1));
				posicion = lineaEstaciones.size() - 1;
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
				Query<TLineaEstacion> query = session.createQuery("from TLineaEstacion");
				lineaEstaciones = (ArrayList<TLineaEstacion>) query.list();
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
				for(int i = 0 ; i < lineaEstaciones.size() ; i++) {
					if(Integer.parseInt(numEstacion.getText()) == lineaEstaciones.get(i).getTEstaciones().getCodEstacion() &&
							Integer.parseInt(numLinea.getText()) == lineaEstaciones.get(i).getTLineas().getCodLinea()) {
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
		btnIr.setBounds(260, 95, 47, 25);
		contentPane.add(btnIr);
	}
	
	private void llenarCampos(int posicion) {
		numLinea.setText(String.valueOf(lineaEstaciones.get(posicion).getTLineas().getCodLinea()));
		numEstacion.setText(String.valueOf(lineaEstaciones.get(posicion).getTEstaciones().getCodEstacion()));
		orden.setText(String.valueOf(lineaEstaciones.get(posicion).getOrden()));
	}

}