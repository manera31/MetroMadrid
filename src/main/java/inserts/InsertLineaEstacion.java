package inserts;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.internal.ExceptionMapperStandardImpl;

import models.*;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InsertLineaEstacion extends JFrame {

	private JPanel contentPane;
	private JTextField numLinea;
	private JTextField numEstacion;
	private JTextField numOrden;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InsertLineaEstacion frame = new InsertLineaEstacion();
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
	public InsertLineaEstacion() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Insertar t_linea_estacion");
		lblNewLabel.setBounds(30, 10, 188, 15);
		contentPane.add(lblNewLabel);
		
		JLabel lblNumeroDeLinea = new JLabel("Numero de linea");
		lblNumeroDeLinea.setBounds(40, 100, 200, 15);
		contentPane.add(lblNumeroDeLinea);
		
		JLabel lblNumeroDeEstacion = new JLabel("Numero de estacion");
		lblNumeroDeEstacion.setBounds(40, 130, 200, 15);
		contentPane.add(lblNumeroDeEstacion);
		
		JLabel lblOrgen = new JLabel("Orden");
		lblOrgen.setBounds(40, 160, 200, 15);
		contentPane.add(lblOrgen);
		
		numLinea = new JTextField();
		numLinea.setBounds(210, 100, 124, 19);
		contentPane.add(numLinea);
		numLinea.setColumns(10);
		
		numEstacion = new JTextField();
		numEstacion.setBounds(220, 130, 124, 19);
		contentPane.add(numEstacion);
		numEstacion.setColumns(10);
		
		numOrden = new JTextField();
		numOrden.setBounds(210, 160, 124, 19);
		contentPane.add(numOrden);
		numOrden.setColumns(10);
		
		JButton btnInsertar = new JButton("Insertar");
		btnInsertar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
				Session session = sessionFactory.openSession();
				Transaction transaction = session.beginTransaction();
				if(Integer.valueOf(numLinea.getText()) > 0 && Integer.valueOf(numEstacion.getText()) > 0 && Integer.valueOf(numOrden.getText()) > 0  ) {
					
					TLineas linea = session.load(TLineas.class, Integer.valueOf(numLinea.getText()));
					TEstaciones estaciones = session.load(TEstaciones.class, Integer.valueOf(numEstacion.getText()));
					
					TLineaEstacionId idLineaEstacion = new TLineaEstacionId(linea.getCodLinea(), estaciones.getCodEstacion());
					TLineaEstacion lineaEstacion = new TLineaEstacion(idLineaEstacion, null, null, Integer.valueOf(numOrden.getText()));
					try {
						session.save(lineaEstacion);
						transaction.commit();
						JOptionPane.showMessageDialog(null, "Insertada");
					} catch (Exception e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "Error");
					}
				} else {
				}
			}
		});
		btnInsertar.setBounds(289, 216, 114, 25);
		contentPane.add(btnInsertar);
	}
}

