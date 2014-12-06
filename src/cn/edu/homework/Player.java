/**
 * Player interface defines the operations that a player should have.
 * @author wlm
 */
package cn.edu.homework;

import cn.edu.homework.JGomokuChessboardModel;

public interface Player {
	public abstract void active(boolean isFirstPlayer);
	public abstract void addPlayerActionEventListener(
			PlayerActionEventListener listener);
	public abstract void removePlayerActionEventListener(
			PlayerActionEventListener listener);
	public abstract JGomokuChessboardModel getModel();
	public abstract void setModel(JGomokuChessboardModel model);
	/**
	 * end() will be called when a round end.
	 * @param isPlayerWin indicates whether the current player win.
	 */
	public void end(boolean isPlayerWin);
}
