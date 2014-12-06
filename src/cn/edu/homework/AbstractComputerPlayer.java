package cn.edu.homework;

import javax.swing.event.EventListenerList;

import cn.edu.homework.JGomokuChessboardModel;
import cn.edu.homework.GomokuGlobal;

abstract class AbstractComputerPlayer implements Player,Runnable{
	private JGomokuChessboardModel chessboardModel;
	private EventListenerList playerActionEventListeners=new EventListenerList();
	private boolean isFirstPlayer;
	
	public void active(boolean isFirstPlayer)
	{
		this.isFirstPlayer=isFirstPlayer;
		Thread t=new Thread(this,"Computer");
		t.start();
	}
	public JGomokuChessboardModel getModel(){
		return chessboardModel;
	}
	public void setModel(JGomokuChessboardModel model)
	{
		chessboardModel=model;
	}
	@Override
	public void run() {
		Point result=AIAction(this.isFirstPlayer?GomokuGlobal.black():GomokuGlobal.white());
		firePlayerAction(new PlayerActionEvent(this,result));
	}
	
	protected void firePlayerAction(PlayerActionEvent event)
	{
		// Guaranteed to return a non-null array
	     Object[] listeners = playerActionEventListeners.getListenerList();
	     // Process the listeners last to first, notifying
	     // those that are interested in this event
	     for (int i = listeners.length-2; i>=0; i-=2) {
	         if (listeners[i]==PlayerActionEventListener.class) {
	             ((PlayerActionEventListener)listeners[i+1]).playerAction(event);
	         }
	     }
	}

	@Override
	public void addPlayerActionEventListener(PlayerActionEventListener listener) {
		playerActionEventListeners.add(PlayerActionEventListener.class,listener);
	}

	@Override
	public void removePlayerActionEventListener(
			PlayerActionEventListener listener) {
		playerActionEventListeners.remove(PlayerActionEventListener.class,listener);
	}
	
	protected abstract Point AIAction(int currentPlayer);
}
