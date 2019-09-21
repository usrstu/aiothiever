package scripts.boi.thiever;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JCheckBox;
import java.awt.BorderLayout;
import javax.swing.JTextField;

import main.robot.script.ScriptExecuter;
import scripts.boi.thiever.tasks.Task;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class GUI {

	private JFrame frmBoimanpp;
	private JTextField foodTextField;
	
	private boolean start = false;
	private boolean leftClick = false;
	private JTextField eatValue;
	private JTextField npcNames;
	private JTextField dropItemsText;


	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmBoimanpp = new JFrame();
		frmBoimanpp.setTitle("BoiAIOThiever V" + BoiAIOThiever.version);
		frmBoimanpp.setBounds(100, 100, 450, 300);
		frmBoimanpp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmBoimanpp.getContentPane().setLayout(null);

		
		foodTextField = new JTextField();
		foodTextField.setBounds(75, 26, 86, 20);
		frmBoimanpp.getContentPane().add(foodTextField);
		foodTextField.setColumns(10);
		
		JLabel lblFoodName = new JLabel("Food name:");
		lblFoodName.setBounds(10, 27, 109, 14);
		frmBoimanpp.getContentPane().add(lblFoodName);
		
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Action performed");
				start = true;
				for (Task t : BoiAIOThiever.taskList) {
					t.init();
				}
				frmBoimanpp.setVisible(false);
				
			}
		});
		btnStart.setBounds(170, 140, 89, 23);
		frmBoimanpp.getContentPane().add(btnStart);
		
		JLabel lblLeftClick = new JLabel("Left click: ");
		lblLeftClick.setBounds(196, 27, 63, 14);
		frmBoimanpp.getContentPane().add(lblLeftClick);
		
		JCheckBox leftClickBox = new JCheckBox("");
		leftClickBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				leftClick = true;
			}
		});
		leftClickBox.setBounds(247, 23, 97, 23);
		frmBoimanpp.getContentPane().add(leftClickBox);
		
		eatValue = new JTextField();
		eatValue.setText("5");
		eatValue.setBounds(75, 57, 36, 20);
		frmBoimanpp.getContentPane().add(eatValue);
		eatValue.setColumns(10);
		
		JLabel lblEatHp = new JLabel("Eat HP:");
		lblEatHp.setBounds(10, 60, 46, 14);
		frmBoimanpp.getContentPane().add(lblEatHp);
		
		JLabel lblNpcName = new JLabel("npc name:");
		lblNpcName.setBounds(10, 85, 63, 14);
		frmBoimanpp.getContentPane().add(lblNpcName);
		
		npcNames = new JTextField();
		npcNames.setBounds(75, 82, 86, 20);
		frmBoimanpp.getContentPane().add(npcNames);
		npcNames.setColumns(10);
		
		JLabel itemsToDroplbl = new JLabel("drop items:");
		itemsToDroplbl.setBounds(196, 85, 63, 14);
		frmBoimanpp.getContentPane().add(itemsToDroplbl);
		
		dropItemsText = new JTextField();
		dropItemsText.setBounds(258, 82, 86, 20);
		frmBoimanpp.getContentPane().add(dropItemsText);
		dropItemsText.setColumns(10);
		
		if (ScriptExecuter.scriptSelected.isEnabled()) {
			frmBoimanpp.setVisible(true);
		}
	}
	
	public String getFoodText() {return foodTextField.getText();}
	public boolean start() {return start;}
	public boolean leftClick() {return leftClick;}
	public int eatValue() {return Integer.parseInt(eatValue.getText());}
	public String getNames() {return npcNames.getText();}
	public String getDropString() {return dropItemsText.getText();}
	
	public List<String> items (String s) {
		List<String> item = new ArrayList<String>();
		
		int charPos = 0;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			
			if (c == ',') {
				item.add(s.substring((charPos == 0) ? 0 : charPos + 1, i));
				charPos = i;
			}
		}
		if (charPos != 0) {
			item.add(s.substring(charPos + 1, s.length()));
		} else {
			item.add(s);
		}
		
		return item;
	}
}
