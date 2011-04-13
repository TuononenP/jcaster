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
import java.util.Timer;
import java.util.TimerTask;

/**
 * Timer to count before recording starts.
 * 
 * @author Petri Tuononen
 *
 */
public class Countdown extends JFrame {

	//global variables
	private static final long serialVersionUID = 252899392006111078L;
	private JPanel contentPane;
	private JLabel label;
	private int time;
	private boolean toggle = true;

	/**
	 * Get time.
	 * 
	 * @return int
	 */
	public int getTime() {
		return time;
	}

	/**
	 * Set time.
	 * 
	 * @param time Time in seconds.
	 */
	public void setTime(int time) {
		this.time = time;
	}

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
	 * Launch the frame.
	 */
	public void launch() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Start to countdown.
	 */
	public void start() {
		setVisible(true);
		final int time_s = Integer.parseInt((getLabel().getText()));
		final int time_ms = time_s*1000;
		int i = 0;
		while (i<time_ms) {
			(new Timer()).schedule(new TimerTask() {
				public void run () {
					int newValue;
					if (toggle==true) { //first run
						//no need to change label
						toggle=false;
					} else {
						newValue = Integer.parseInt((getLabel().getText()))-1;
						getLabel().setText(Integer.toString(newValue));
					}
				}
			}, i);
			i += 1000;
		}
		(new Timer()).schedule(new TimerTask() {
			public void run () {
				//get rid of the frame when countdown reaches zero
				setVisible(false);
				dispose();
			}
		}, getTime());
	}

	/**
	 * Create the frame.
	 * 
	 * @param time Time in seconds.
	 */
	public Countdown(int time) {
		setResizable(false);
		setAlwaysOnTop(true);
		setTitle("Timer");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 163, 148);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);

		label = new JLabel(Integer.toString(time/1000));
		label.setFont(new Font("Swiss921 BT", Font.PLAIN, 70));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(label);
		
		setLocationRelativeTo(null); //center the frame
		setTime(time);
	}

}
