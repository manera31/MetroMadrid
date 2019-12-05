package updates;

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

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import inserts.HibernateUtil;
import models.TEstaciones;

public class ModificarEstaciones extends JFrame {

	private JPanel contentPane;
	private ArrayList<TEstaciones> estaciones;
	private JButton btnLanzarConsulta;
	private JButton btnCancelarConsulta;
	private JButton btnPrimer, btnUltimo, btnSiguiente, btnAnterior, btnGuardar;
	private JLabel nombre, direccion;
	private JTextField numAccesos, numLineas, numViajesDestino, numViajesProcedencia, codEstacion;
	private int posicion = 0;
	private JButton btnIr;
	private JLabel lblactualizarDespuesDe;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ModificarEstaciones frame = new ModificarEstaciones();
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
	public ModificarEstaciones() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 536, 421);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblConusltaTrenes = new JLabel("CONUSLTA ESTACIONES");
		lblConusltaTrenes.setBounds(30, 40, 189, 15);
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
		
		btnLanzarConsulta = new JButton("ACTUALIZAR TABLA");
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
				btnGuardar.setEnabled(true);
				btnIr.setEnabled(true);
				session.close();
				llenarCampos(0);
				posicion = 0;
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
				btnGuardar.setEnabled(false);
				btnIr.setEnabled(false);
			}
		});
		btnCancelarConsulta.setBounds(312, 52, 202, 25);
		contentPane.add(btnCancelarConsulta);
		
		codEstacion = new JTextField("");
		codEstacion.setBounds(165, 100, 66, 20);
		contentPane.add(codEstacion);
		
		nombre = new JLabel("");
		nombre.setBounds(165, 130, 200, 15);
		contentPane.add(nombre);
		
		direccion = new JLabel("");
		direccion.setBounds(165, 160, 200, 15);
		contentPane.add(direccion);
		
		numAccesos = new JTextField("");
		numAccesos.setBounds(165, 190, 66, 15);
		contentPane.add(numAccesos);
		
		numLineas = new JTextField("");
		numLineas.setBounds(165, 220, 66, 15);
		contentPane.add(numLineas);
		
		numViajesDestino = new JTextField("");
		numViajesDestino.setBounds(165, 250, 66, 15);
		contentPane.add(numViajesDestino);
		
		numViajesProcedencia = new JTextField("");
		numViajesProcedencia.setBounds(165, 280, 66, 15);
		contentPane.add(numViajesProcedencia);
		
		btnGuardar = new JButton("GUARDAR");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Session session = HibernateUtil.getSessionFactory().openSession();
					Transaction transaction = session.beginTransaction();
					TEstaciones estacion = new TEstaciones(estaciones.get(posicion).getCodEstacion(), estaciones.get(posicion).getNombre(), estaciones.get(posicion).getDireccion());
					estacion.setNumaccesos(Integer.valueOf(numAccesos.getText()));
					estacion.setNumlineas(Integer.valueOf(numLineas.getText()));
					estacion.setNumviajesdestino(Integer.valueOf(numViajesDestino.getText()));
					estacion.setNumviajesprocedencia(Integer.valueOf(numViajesProcedencia.getText()));
					session.update(estacion);
					transaction.commit();
					session.close();
				} catch(HibernateException he) {
					he.printStackTrace();
				}
			}
		});
		btnGuardar.setEnabled(false);
		btnGuardar.setBounds(371, 275, 114, 25);
		contentPane.add(btnGuardar);
		
		btnIr = new JButton("IR");
		btnIr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean aux = false;
				for(int i = 0 ; i < estaciones.size() ; i++) {
					if(Integer.parseInt(codEstacion.getText()) == estaciones.get(i).getCodEstacion()) {
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
		btnIr.setBounds(233, 95, 47, 25);
		contentPane.add(btnIr);
		
		lblactualizarDespuesDe = new JLabel("*Actualizar despues de guardar un dato");
		lblactualizarDespuesDe.setBounds(30, 371, 335, 15);
		contentPane.add(lblactualizarDespuesDe);
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
