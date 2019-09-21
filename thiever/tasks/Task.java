package scripts.boi.thiever.tasks;

import main.robot.api.ClientContext;

public abstract class Task{
	
	public ClientContext auto;
	public Task(ClientContext auto) {
		this.auto = auto;
	}
	
	public abstract boolean condition();
	public abstract void run();
	public abstract void init();
}
