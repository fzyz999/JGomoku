package cn.edu.homework;

import java.awt.event.MouseEvent;
import javax.swing.event.EventListenerList;

import cn.edu.homework.PlayerActionEvent;
import cn.edu.homework.PlayerActionEventListener;
import cn.edu.homework.JGomokuChessboardListener;
import cn.edu.homework.JGomokuChessboard;
import cn.edu.homework.GomokuGlobal;

public class BasicJGomokuChessboardListener implements JGomokuChessboardListener {

	private EventListenerList playerActionEventListeners=new EventListenerList();
	private JGomokuChessboardModel chessboardModel;
	
	/* （非 Javadoc）
	 * @see cn.edu.homework.JGomokuChessboardListener#addPlayerActionEventListener(cn.edu.homework.PlayerActionEventListener)
	 */
	@Override
	public void addPlayerActionEventListener(PlayerActionEventListener listener)
	{
		playerActionEventListeners.add(PlayerActionEventListener.class,listener);
	}
	@Override
	public void end(boolean isPlayerWin) {
		//nothing to do
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
	public void mouseClicked(MouseEvent event)
	{
		JGomokuChessboard p=(JGomokuChessboard)event.getSource();
		int stepx=p.getWidth()/(GomokuGlobal.getChessboardx()+1),
		    stepy=p.getHeight()/(GomokuGlobal.getChessboardy()+1);
		int basex=stepx/2,basey=stepy/2;
		if(event.getPoint().getX()>=basex && 
			event.getPoint().getY()>=basey &&
			event.getPoint().getX()<=basex+GomokuGlobal.getChessboardx()*stepx &&
			event.getPoint().getY()<=basey+GomokuGlobal.getChessboardy()*stepy){
			firePlayerAction(new PlayerActionEvent(this,
					new Point((int)((event.getPoint().getX()*1.0/stepx+0.5)),
								(int)((event.getPoint().getY()*1.0/stepy+0.5))))
			);
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
	/* （非 Javadoc）
	 * @see cn.edu.homework.JGomokuChessboardListener#removePlayerActionEventListener(cn.edu.homework.PlayerActionEventListener)
	 */
	@Override
	public void removePlayerActionEventListener(PlayerActionEventListener listener)
	{
		playerActionEventListeners.remove(PlayerActionEventListener.class,listener);
	}
	/**
	 * @return chessboardModel
	 */
	@Override
	public JGomokuChessboardModel getModel() {
		return chessboardModel;
	}

	/**
	 * @param chessboardModel 要设置的 chessboardModel
	 */
	@Override
	public void setModel(JGomokuChessboardModel chessboardModel) {
		this.chessboardModel = chessboardModel;
	}
	@Override
	public void active(boolean isFirstPlayer) {
		//nothing to do
	}
}
