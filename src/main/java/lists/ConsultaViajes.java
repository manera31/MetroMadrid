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
import javax.swing.border.EmptyBorder;

import org.hibernate.*;
import org.hibernate.query.Query;

import inserts.HibernateUtil;
import models.*;

public class ConsultaViajes extends JFrame {

	private JPanel contentPane;
	private ArrayList<TViajes> viajes;
	private JTextField numViaje;
	private JLabel nombre;
	private JLabel estacionOrigen;
	private JLabel estacionDestino;
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
					ConsultaViajes frame = new ConsultaViajes();
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
	public ConsultaViajes() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 536, 352);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblConusltaTrenes = new JLabel("CONUSLTA VIAJES");
		lblConusltaTrenes.setBounds(30, 40, 200, 15);
		contentPane.add(lblConusltaTrenes);
		
		JLabel lblCodTren = new JLabel("COD LINEA");
		lblCodTren.setBounds(30, 100, 88, 15);
		contentPane.add(lblCodTren);
		
		JLabel lblNombre = new JLabel("NOMBRE");
		lblNombre.setBounds(30, 130, 66, 15);
		contentPane.add(lblNombre);
		
		JLabel lblTipo = new JLabel("EST ORIGEN");
		lblTipo.setBounds(30, 160, 100, 15);
		contentPane.add(lblTipo);
		
		JLabel lblLinea = new JLabel("EST DESTINO");
		lblLinea.setBounds(30, 190, 100, 15);
		contentPane.add(lblLinea);
		
		numViaje = new JTextField("");
		numViaje.setBounds(186, 89, 66, 20);
		contentPane.add(numViaje);
		
		nombre = new JLabel("");
		nombre.setBounds(186, 119, 139, 15);
		contentPane.add(nombre);
		
		estacionOrigen = new JLabel("");
		estacionOrigen.setBounds(186, 149, 139, 15);
		contentPane.add(estacionOrigen);
		
		estacionDestino = new JLabel("");
		estacionDestino.setBounds(186, 179, 66, 15);
		contentPane.add(estacionDestino);
		
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
				if((posicion + 1) <= (viajes.size()-1)) {
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
				llenarCampos((viajes.size() - 1));
				posicion = viajes.size() - 1;
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
				Query<TViajes> query = session.createQuery("from TViajes");
				viajes = (ArrayList<TViajes>) query.list();
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
				for(int i = 0 ; i < viajes.size() ; i++) {
					if(Integer.parseInt(numViaje.getText()) == viajes.get(i).getCodViaje()) {
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
		numViaje.setText(String.valueOf(viajes.get(posicion).getCodViaje()));
		nombre.setText(viajes.get(posicion).getNombre());
		estacionOrigen.setText(String.valueOf(viajes.get(posicion).getTEstacionesByEstacionorigen().getCodEstacion()));
		estacionDestino.setText(String.valueOf(viajes.get(posicion).getTEstacionesByEstaciondestino().getCodEstacion()));
	}

}
