package cn.edu.homework;

import javax.swing.event.ChangeListener;

public interface JGomokuChessboardModel {
	public void claer();
	/**
	 * get the game status
	 * @return return 0 if no one wins. 1 if the first player wins the game,otherwise return 2.
	 */
	public abstract int getStatus();

	/**
	 * @return chessboard 返回
	 */
	public abstract boolean isEmpty();
	public abstract int getChessboardState(int x,int y);
	public abstract void setChessboardState(int x,int y,boolean isFirstPlayer);
	
	public abstract void addChangeListener(
			ChangeListener listener);

	public abstract void removeChangeEventListener(
			ChangeListener listener);
}