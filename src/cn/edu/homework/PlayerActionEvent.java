package cn.edu.homework;

import java.util.EventObject;
import cn.edu.homework.Point;

public class PlayerActionEvent extends EventObject {
	
	private Point playerAct;
	private static int timestamp;

	public PlayerActionEvent(Object source,Point pos) {
		super(source);
		setPlayerAction(pos);
		timestamp++;
	}

	/**
	 * @return the point where player set.
	 */
	public Point getPlayerAction() {
		return playerAct;
	}

	/**
	 * @param playerAct 要设置的 playerAct
	 */
	public void setPlayerAction(Point playerAct) {
		this.playerAct = playerAct;
	}

	/**
	 * @return timestamp
	 */
	public int getTimestamp() {
		return timestamp;
	}

}
