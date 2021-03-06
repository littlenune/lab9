package lab9;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.InputMismatchException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
/*
 * @author Tunya Wittayasiripaiboon 5810546676
 *  The UI which will access the request from UnitConverter.
 */
public class ConverterUI extends JFrame{
	private JButton convertButton;
	private JButton clearButton;
	private UnitConverter unitconverter;
	private JPanel contentPane;
	private JTextField inputField1;
	private JTextField inputField2;
	private JComboBox unit1ComboBox;
	private JComboBox unit2ComboBox;
	private JLabel label;
	private JPanel pane;
	private JPanel secondPane;
	private JRadioButton leftToRight;
	private JRadioButton rightToLeft;
	private JRadioButton autoDetect;
	/*
	 * Constructor receives references to UnitConverter also set JFrame.
	 * @param UnitConverter 
	 */
	public ConverterUI ( UnitConverter uc){
		this.unitconverter = uc;
		this.setTitle("Length Converter");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.initComponents();
	}
	/*
	 * Create components for the UI and position them using a layout manager.
	 */
	private void initComponents(){
		pane = new JPanel();
		pane.setLayout(new BorderLayout());
		contentPane = new JPanel();
		secondPane = new JPanel();
		pane.add(contentPane, BorderLayout.CENTER);
		pane.add(secondPane , BorderLayout.SOUTH);
		setContentPane(pane);
		contentPane.setLayout(new FlowLayout());
		inputField1 = new JTextField();
		contentPane.add(inputField1);
		inputField1.setColumns(5);
		inputField1.addKeyListener(new ConvertButtonListener());
		unit1ComboBox = new JComboBox(unitconverter.getUnits());
		unit1ComboBox.addActionListener(new ConvertButtonListener());
		contentPane.add(unit1ComboBox);
		label = new JLabel("=");
		contentPane.add(label);
		inputField2 = new JTextField();
		contentPane.add(inputField2);
		inputField2.setColumns(5);
		inputField2.addKeyListener(new ConvertButtonListener());
		inputField2.setEditable(false);
		unit2ComboBox = new JComboBox(unitconverter.getUnits());
		unit2ComboBox.addActionListener(new ConvertButtonListener());
		contentPane.add(unit2ComboBox);
		convertButton = new JButton("Convert!");
		contentPane.add(convertButton);
		clearButton = new JButton("Clear");
		contentPane.add(clearButton);
		leftToRight = new JRadioButton("Left->Right");
		rightToLeft = new JRadioButton("Right->Left");
		leftToRight.setSelected(true);
		autoDetect = new JRadioButton("auto-detect");
		secondPane.setLayout(new FlowLayout());
		secondPane.add(leftToRight);
		secondPane.add(rightToLeft);
		secondPane.add(autoDetect);
		convertButton.addActionListener(new ConvertButtonListener());
		clearButton.addActionListener(new ClearButtonListener());
		rightToLeft.addActionListener(new Conversion());
		leftToRight.addActionListener(new Conversion());
		autoDetect.addActionListener(new Conversion());
		this.pack();
	}
	/*
	 * Run JFrame 
	 */
	public static void main(String[] args) {
		UnitConverter uc = new UnitConverter();
		ConverterUI frame = new ConverterUI(uc);
		frame.setVisible(true);
	}
	/*
	 * ActionListener related to the button when pressed or clicked will perform an action which is unitConverter to convert and result 
	 * values in another textField.
	 * KeyListener which related to the key typing it will automatically perform an action which is unitConverter to convert and result
	 * values in another textField instantly. 
	 */
	class ConvertButtonListener implements ActionListener , KeyListener{
		/*
		 * Convert value from selected to another selected unit by using unitConverter also detected the direction 
		 * to convert and result the value. 
		 */
		public void conversion (){
			String s = inputField1.getText().trim();
			String s2 = inputField2.getText().trim();
			if ( s.length() > 0 || s2.length() > 0){
				try {
					Unit unit1 = (Unit) unit1ComboBox.getSelectedItem();
					Unit unit2 = (Unit) unit2ComboBox.getSelectedItem();
					if ( inputField2.isEditable() == false || inputField1.hasFocus()  ){
						double value = Double.valueOf(s);
						inputField2.setText(String.format("%.3f",unitconverter.convert(value,unit1,unit2)));
					}
					else if ( inputField1.isEditable() == false || inputField2.hasFocus() ) {
						double value = Double.valueOf(s2);
						inputField1.setText(String.format("%.3f",unitconverter.convert(value,unit2,unit1)));
					}
				}
				catch ( NumberFormatException illegalInput){
					System.out.println("Illegal Input");
				}
			}
		}
		/*
		 * Perform an action conversion when click on the button.
		 */
		public void actionPerformed( ActionEvent evt){
			this.conversion();
		}
		/*
		 * Perform an action when delete the text from the text field and also empty both of the text fields.
		 */
		@Override
		public void keyTyped(KeyEvent e) {
			if ( inputField1.hasFocus() && inputField1.getText().equals("")){
				inputField2.setText("");
			}
			else if ( inputField2.hasFocus() && inputField2.getText().equals("")){
				inputField1.setText("");
			}
		}
		/*
		 * Perform an action after pressed enter will convert the value.
		 */
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode()==KeyEvent.VK_ENTER){
				this.conversion();
			}
		}
		/*
		 * Perform an action after input the value in text field.
		 */
		@Override
		public void keyReleased(KeyEvent e) {
			if ( autoDetect.isSelected() == true){
				this.conversion();
			}
		}
	}
	/*
	 * Clear input in both text fields.
	 */
	class ClearButtonListener implements ActionListener {
		public void actionPerformed( ActionEvent evt){
			inputField1.setText("");
			inputField2.setText("");
		}
	}
	/*
	 * Set direction to convert from right text field to left text field. 
	 */
	class Conversion implements ActionListener {
		public void actionPerformed( ActionEvent evt){
			if ( rightToLeft.hasFocus() == true){
				this.inputRightTextField();
			}
			else if ( leftToRight.hasFocus() == true){
				this.inputLeftTextField();
			}
			else if ( autoDetect.hasFocus() == true){
				this.autoDectectMode();
			}
		}
		/*
		 * Input with right text field and set left text field to be not editable also perform result in the left text field
		 */
		public void inputRightTextField (){
			inputField1.setEditable(false);
			inputField2.setEditable(true);
			leftToRight.setSelected(false);
			autoDetect.setSelected(false);
		}
		/*
		 * Input with left text field and set right text field to be not editable also perform result in the right text field
		 */
		public void inputLeftTextField (){
			inputField1.setEditable(true);
			inputField2.setEditable(false);
			rightToLeft.setSelected(false);
			autoDetect.setSelected(false);
		}
		/*
		 * Can input in any text fields and the result will perform in one another.
		 */
		public void autoDectectMode (){
			inputField1.setEditable(true);
			inputField2.setEditable(true);
			rightToLeft.setSelected(false);
			leftToRight.setSelected(false);
		}
	}
}