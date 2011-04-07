//    Copyright (C) 2011  Petri Tuononen
//
//    This program is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    This program is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with this program.  If not, see <http://www.gnu.org/licenses/>.
package gui;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;

/**
 * Timer to count before recording starts.
 * 
 * @author Petri Tuononen
 *
 */
public class Timer extends JFrame {

	private static final long serialVersionUID = 252899392006111078L;
	private JPanel contentPane;
	JLabel label;

	/**
	 * Get timer count value.
	 * 
	 * @return JLabel
	 */
	public JLabel getLabel() {
		return label;
	}

	/**
	 * Set timer count value.
	 * @param label JLabel
	 */
	public void setLabel(JLabel label) {
		this.label = label;
	}

	/**
	 * Launch the application.
	 */
	public void launch() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Timer frame = new Timer();
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
	public Timer() {
		setResizable(false);
		setTitle("Timer");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 163, 148);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);

		label = new JLabel("3");
		label.setFont(new Font("Swiss921 BT", Font.PLAIN, 70));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(label);
	}

}
