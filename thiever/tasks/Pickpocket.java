package scripts.boi.thiever.tasks;

import main.hooks.filters.AutoInventory;
import main.hooks.wrappers.AutoNpc;
import main.robot.api.ClientContext;
import scripts.boi.thiever.BoiAIOThiever;

public class Pickpocket extends Task {
	
	public Pickpocket(ClientContext auto) {
		super(auto);
	}

	public String npcName;
	
	@Override
	public void init() {
		System.out.println("initing");
		npcName = BoiAIOThiever.gui.getNames();
	}
	
	@Override
	public boolean condition() {
		return !auto.getNpcs().populate().filter(npcName).isEmpty();
	}

	@Override
	public void run() {
		AutoNpc npc = null;
		
		if (auto.getPlayers().getLocal().getHealth() <= BoiAIOThiever.gui.eatValue() && !BoiAIOThiever.gui.getFoodText().equalsIgnoreCase("")) { //needs to eat
			AutoInventory food = auto.getInventory().populate().filter(BoiAIOThiever.gui.getFoodText());
			food.next().click(1);
			auto.sleep((int) (Math.random() + 1000));
		}
		
		if (auto.getInventory().populate().filter("Coin pouch").population(true) >= 28) {//opens all coins cuz full af
			auto.getInventory().next().click(1);
			auto.sleep((int) (Math.random() + 1000));
		}else if (!auto.getNpcs().populate().filter(npcName).isEmpty()) {	//otherwise it isnt full and there is man queried.
			npc = auto.getNpcs().nextNearest();
			 
			// interact if npc obstacle free and has space for gold.
			if (npc.validateInteractable() && auto.getInventory().populate().population() <= 27) {
				System.out.println("Can click");
				if (BoiAIOThiever.gui.leftClick()) {
					npc.click(1);
				} else {
					npc.click("Pickpocket");
					System.out.println("sent click");
				}
				auto.sleep((int) (Math.random() + 1000));
			} //else the npc is not obstacle free but it shouldnt be cuz its man/woman are everywhere.
		}else {
			System.out.println("No condition is true.");
		}
	}
}
