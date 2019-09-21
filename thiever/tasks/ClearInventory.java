package scripts.boi.thiever.tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;

import main.hooks.filters.AutoInventory;
import main.hooks.wrappers.AutoItem;
import main.robot.api.ClientContext;
import main.robot.util.Random;
import net.runelite.api.Skill;
import scripts.boi.thiever.BoiAIOThiever;

public class ClearInventory extends Task{
	
	private boolean drop = false;
	public List<String> itemsToDrop = new ArrayList<String>();
	public List<String> food = new ArrayList<String>();

	public ClearInventory(ClientContext auto) {
		super(auto);
	}

	@Override
	public void init() {
		System.out.println("initing in ");
		if (BoiAIOThiever.gui.getFoodText().length() > 0) {
			for (String s : BoiAIOThiever.gui.items(BoiAIOThiever.gui.getFoodText())) {
				food.add(s);
			}
			
		}
		if (BoiAIOThiever.gui.getDropString().length() > 0) {
			for (String s : BoiAIOThiever.gui.items(BoiAIOThiever.gui.getDropString())) {
				itemsToDrop.add(s);
			}
			for (String s : itemsToDrop) {
				System.out.println("u got: " + s);
			}
		}
		
	}
	@Override
	public boolean condition() {
		return auto.getInventory().inventoryFull();
	}

	@Override
	public void run() {
		System.out.println("Clearing inv");
		System.out.println(itemsToDrop);
		//TODO: add a custom command like sys for this function here \/
		if (auto.getInventory().populate().filter("Jug of Wine").population() >= 20) { //for clearing up inv in elvs to make it more efficient b/c wine tends to fill up inv.
			for (AutoItem invItem : auto.getInventory().getList()) {
				if (invItem.getName().equalsIgnoreCase("Jug of Wine")) {
					invItem.click(1);
					auto.sleep(Random.between(1000, 2000));
				}
			}
		}
		if (!auto.getInventory().populate().filter("Diamond").isEmpty()) {
			while (true) {
				AutoItem i = auto.getInventory().next();
				System.out.println(i);
				if (i == null) {
					break;
				}

				auto.getMagic().castSpellOnItem("High Level Alchemy", i.getId());
				auto.sleepCondition(new BooleanSupplier() {

					@Override
					public boolean getAsBoolean() {
						return i.validateInteractable();
					}
				});
			}

		}
		if (!auto.getInventory().populate().filter(itemsToDrop.toArray(new String[itemsToDrop.size()])).isEmpty()) { //when inv is full and it contains dorp items
			for (AutoItem i : auto.getInventory().getList()) {
				System.out.println(i);
				if (i == null) {
					return;
				} else {
					i.click("Drop");
					auto.sleep(Random.between(0, 1000));
				}

			}
		} else if (!auto.getInventory().populate().filter(food.toArray(new String[food.size()])).isEmpty()) { //we dont have drop items instead we have food we can eat to clear space
			AutoItem eatItem = auto.getInventory().next();
			eatItem.click(1);
			auto.sleep((int) (Math.random() + 1000));
		}
		
		/*if (drop) { // clear inv
			System.out.println("Clearing inv");
			if (auto.getInventory().populate().filter(itemsToDrop).isEmpty()) {
				if (itemsToDrop[4].equalsIgnoreCase("Jug of Wine")) {
					itemsToDrop[4] = "";
				}
				drop = false;
			}
			for (AutoItem i : auto.getInventory().populate().getList()) {
				System.out.println(i);
				for (String dropItem : itemsToDrop) {
					if (i.getName().equalsIgnoreCase(dropItem)) {
						i.click("Drop");
					}
				}
			}
				
				/*if (i.getName().equalsIgnoreCase("Gold ore") || i.getName().equalsIgnoreCase("Fire orb")) {
					i.click("Drop");
					auto.sleep((int) (Math.random() + 1000));
					return;
				}
				if (i.getName().equalsIgnoreCase("Jug of Wine") && auto.getPlayers().getLocal().getHealth() != auto.getContext().getSkillLevel(Skill.HITPOINTS)) {
					//eat
					if (itemsToDrop[4].equalsIgnoreCase("Jug of wine")) {
						i.click("Drop");
					} else {
						i.click(1);
					}
					auto.sleep((int) (Math.random() + 1000));
					return;
				}
				if (i.getName().equalsIgnoreCase("Coin pouch")) {
					i.click(1);
					auto.sleep((int) (Math.random() + 1000));
					return;
				}
			}
			for (AutoItem jug : auto.getInventory().populate().getList()) {
				if (jug.getName().equalsIgnoreCase("Jug")) {
					jug.click("Drop");
					auto.sleep((int) (Math.random() + 1000));
				}
			}
		}*/
	}

}
