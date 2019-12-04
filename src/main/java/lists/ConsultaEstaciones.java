package lists;

import java.awt.BorderLayout;
import models.*;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import inserts.HibernateUtil;

public class ConsultaEstaciones extends JFrame {

	private JPanel contentPane;
	private ArrayList<TEstaciones> estaciones;
	private JLabel codEstacion, nombre, direccion, numAccesos, numLineas, numViajesDestino, numViajesProcedencia;
	private JButton btnLanzarConsulta;
	private JButton btnCancelarConsulta;
	private JButton btnPrimer, btnUltimo, btnSiguiente, btnAnterior;
	private int posicion = 0;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConsultaEstaciones frame = new ConsultaEstaciones();
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
	public ConsultaEstaciones() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 536, 421);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblConusltaTrenes = new JLabel("CONUSLTA ESTACIONES");
		lblConusltaTrenes.setBounds(30, 40, 160, 15);
		contentPane.add(lblConusltaTrenes);
		
		JLabel lblCodTren = new JLabel("COD ESTACION");
		lblCodTren.setBounds(30, 100, 100, 15);
		contentPane.add(lblCodTren);
		
		JLabel lblNombre = new JLabel("NOMBRE");
		lblNombre.setBounds(30, 130, 100, 15);
		contentPane.add(lblNombre);
		
		JLabel lblTipo = new JLabel("DIRECCION");
		lblTipo.setBounds(30, 160, 100, 15);
		contentPane.add(lblTipo);
		
		JLabel lblLinea = new JLabel("NUM ACCESOS");
		lblLinea.setBounds(30, 190, 100, 15);
		contentPane.add(lblLinea);
		
		JLabel lblCochera = new JLabel("NUM LINEAS");
		lblCochera.setBounds(30, 220, 100, 15);
		contentPane.add(lblCochera);
		
		JLabel lblCochera2 = new JLabel("NUM VIAJ DEST");
		lblCochera2.setBounds(30, 250, 120, 15);
		contentPane.add(lblCochera2);
		
		JLabel lblCochera3 = new JLabel("NUM VIAJ PROC");
		lblCochera3.setBounds(30, 280, 120, 15);
		contentPane.add(lblCochera3);
		
		//BOTONES
		btnPrimer = new JButton("PRIMER");
		btnPrimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				posicion = 0;
				llenarCampos(0);
			}
		});
		btnPrimer.setEnabled(false);
		btnPrimer.setBounds(10, 334, 114, 25);
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
		btnAnterior.setBounds(140, 334, 114, 25);
		contentPane.add(btnAnterior);
		
		btnSiguiente = new JButton("SIGUIENTE");
		btnSiguiente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if((posicion + 1) <= (estaciones.size()-1)) {
					posicion++;
					llenarCampos(posicion);
				} else {
					JOptionPane.showMessageDialog(null, "Ultimo registro alcanzado");
				}
			}
		});
		btnSiguiente.setEnabled(false);
		btnSiguiente.setBounds(270, 334, 114, 25);
		contentPane.add(btnSiguiente);
		
		btnUltimo = new JButton("ÚLTIMO");
		btnUltimo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				llenarCampos((estaciones.size() - 1));
				posicion = estaciones.size() - 1;
			}
		});
		btnUltimo.setEnabled(false);
		btnUltimo.setBounds(400, 334, 114, 25);
		contentPane.add(btnUltimo);
		
		btnLanzarConsulta = new JButton("LANZAR CONSULTA");
		btnLanzarConsulta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Session session = HibernateUtil.getSessionFactory().openSession();
				Transaction transaction = session.beginTransaction();
				Query<TEstaciones> query = session.createQuery("from TEstaciones");
				estaciones = (ArrayList<TEstaciones>) query.list();
				transaction.commit();
				btnSiguiente.setEnabled(true);
				btnAnterior.setEnabled(true);
				btnPrimer.setEnabled(true);
				btnUltimo.setEnabled(true);
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
			}
		});
		btnCancelarConsulta.setBounds(312, 52, 202, 25);
		contentPane.add(btnCancelarConsulta);
		
		codEstacion = new JLabel("");
		codEstacion.setBounds(165, 100, 66, 15);
		contentPane.add(codEstacion);
		
		nombre = new JLabel("");
		nombre.setBounds(165, 130, 200, 15);
		contentPane.add(nombre);
		
		direccion = new JLabel("");
		direccion.setBounds(165, 160, 200, 15);
		contentPane.add(direccion);
		
		numAccesos = new JLabel("");
		numAccesos.setBounds(165, 190, 66, 15);
		contentPane.add(numAccesos);
		
		numLineas = new JLabel("");
		numLineas.setBounds(165, 220, 66, 15);
		contentPane.add(numLineas);
		
		numViajesDestino = new JLabel("");
		numViajesDestino.setBounds(165, 250, 66, 15);
		contentPane.add(numViajesDestino);
		
		numViajesProcedencia = new JLabel("");
		numViajesProcedencia.setBounds(165, 280, 66, 15);
		contentPane.add(numViajesProcedencia);
		
		JButton btnGuardar = new JButton("GUARDAR");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Session session = HibernateUtil.getSessionFactory().openSession();
				Transaction transaction = session.beginTransaction();
				TEstaciones estacion = new TEstaciones();
			}
		});
		btnGuardar.setBounds(371, 275, 114, 25);
		contentPane.add(btnGuardar);
	}
	private void llenarCampos(int posicion) {
		codEstacion.setText(String.valueOf(estaciones.get(posicion).getCodEstacion()));
		nombre.setText(String.valueOf(estaciones.get(posicion).getNombre()));
		direccion.setText(String.valueOf(estaciones.get(posicion).getDireccion()));
		numAccesos.setText(String.valueOf(estaciones.get(posicion).getNumaccesos()));
		numLineas.setText(String.valueOf(estaciones.get(posicion).getNumlineas()));
		numViajesDestino.setText(String.valueOf(estaciones.get(posicion).getNumviajesdestino()));
		numViajesProcedencia.setText(String.valueOf(estaciones.get(posicion).getNumviajesprocedencia()));
	}
}
