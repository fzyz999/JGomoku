/**
 * Gomoku controls the whole game.
 * @author wlm
 */
package cn.edu.homework;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import cn.edu.homework.Player;
import cn.edu.homework.PlayerActionEventListener;
import cn.edu.homework.JGomokuChessboardModel;

public class Gomoku{
	private JGomokuChessboardModel chessboardModel;
	private Player playerA,playerB;
	private PlayerListener listenerA=new PlayerListener(true),
							  listenerB=new PlayerListener(false);
	private boolean firstPlayerTurn;
	private int lastActionTimestamp=-1;
	private boolean isGameRunning = false;
	
	private class StatusChecker implements ChangeListener{
		@Override
		public void stateChanged(ChangeEvent e) {
			if(chessboardModel.getStatus()>0){
				end();
			}
		}
	}
	
	/**
	 * @return isGameRunning
	 */
	public synchronized boolean isGameRunning() {
		return isGameRunning;
	}

	/**
	 * @param isGameRunning 要设置的 isGameRunning
	 */
	public synchronized void setGameRunning(boolean isGameRunning) {
		this.isGameRunning = isGameRunning;
	}

	public Gomoku(JGomokuChessboardModel model){
		chessboardModel=model;
		chessboardModel.addChangeListener(new StatusChecker());
	}
	
	private class PlayerListener implements PlayerActionEventListener {
		private boolean isFirstPlayer;
		PlayerListener(boolean isFirstPlayer){
			this.isFirstPlayer=isFirstPlayer;
		}
		@Override
		public void playerAction(PlayerActionEvent event) {
			if(chessboardModel.getStatus()==0 && 
				firstPlayerTurn==isFirstPlayer && 
				lastActionTimestamp!=event.getTimestamp()){
				lastActionTimestamp=event.getTimestamp();
				act(event.getPlayerAction());
			}
		}
	}
	
	public void start(Player first,Player second)
	{
		if(playerA!=first){
			if(playerA!=null){
				playerA.removePlayerActionEventListener(listenerA);
			}
			playerA=first;
			playerA.addPlayerActionEventListener(listenerA);
		}
		if(playerB!=second){
			if(playerB!=null){
				playerB.removePlayerActionEventListener(listenerB);
			}
			playerB=second;
			playerB.addPlayerActionEventListener(listenerB);
		}
		
		firstPlayerTurn=true;
		chessboardModel.claer();
		setGameRunning(true);
		playerA.active(true);
	}
	
	public void end()
	{
		if(playerA==null) return ;
		playerA.end(chessboardModel.getStatus()==1);
		
		if(playerB==null) return ;
		playerB.end(chessboardModel.getStatus()==2);
		setGameRunning(false);
	}
	
	public void setModel(JGomokuChessboardModel model)
	{
		chessboardModel=model;
	}
	public JGomokuChessboardModel getModel()
	{
		return chessboardModel;
	}
	
	private void act(Point point)
	{
		chessboardModel.setChessboardState(point.getX(), point.getY(), firstPlayerTurn);
		firstPlayerTurn=!firstPlayerTurn;
		if(firstPlayerTurn){
			playerA.active(true);
		}
		else{
			playerB.active(false);
		}
	}
}
