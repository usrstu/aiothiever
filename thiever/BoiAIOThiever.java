package scripts.boi.thiever;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.swing.SwingUtilities;

import main.hooks.autops.Category;
import main.hooks.autops.ChatMessage;
import main.hooks.filters.AutoInventory;
import main.hooks.wrappers.AutoItem;
import main.hooks.wrappers.AutoNpc;
import main.robot.script.Script;
import net.runelite.api.Skill;
import scripts.boi.thiever.tasks.ClearInventory;
import scripts.boi.thiever.tasks.Pickpocket;
import scripts.boi.thiever.tasks.Task;

public class BoiAIOThiever extends Script {

	public static GUI gui;
	public static double version = 1.42D;

	private boolean dev = false;

	
	public static List<Task> taskList = new ArrayList<>();
	
	@Override
	public void onChatMessage(ChatMessage cm) {

		
	}

	@Override
	public void onExecute() {
		System.out.println("Running version: " + scriptDefinitions().getVersion());
		System.out.println("starting gui!");
		add(new Pickpocket(auto), new ClearInventory(auto));
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				gui = new GUI();
			}

		});
		
		//gui = new GUI();
	}

	@SuppressWarnings("static-access")
	@Override
	public void onProcess() {
		if (gui.start()) {
			for (Task t : taskList) {
				if (t.condition()) {
					t.run();
					System.out.println("l");
				}
			}
		}
	}
	
	public void add(Task... t) {
		System.out.println("added " + t);
		taskList.addAll(Arrays.asList(t));
	}
	
	public void removeAll() {
		taskList.clear();
	}

	@Override
	public void onTerminate() {
		System.out.println("Terminated");
		
	}

	@Override
	public Category scriptDefinitions() {
		Category cat = new Category();
		cat.setTitle("Boi AIO Thiever");
		cat.setAuthor("Boi");
		cat.setVersion(version);
		cat.setDiscord("xz#2821");
		cat.setDescription("Leave food blank for no food. Left click tick is for left click pickpocketing, click this if you want left click pp'ing(NOT RECCOMMENDED MISCLICKS A LOT). Start near chosen npc preferably in a very open area with no obstacles. Alchs diamond if its in inv (for zenyte)");
		return cat;
	}

}
