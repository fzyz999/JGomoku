package cn.edu.homework;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

import cn.edu.homework.GomokuGlobal;

public class DefaultJGomokuChessboardModel implements JGomokuChessboardModel {
	private int chessboard[][]=new int[GomokuGlobal.getChessboardx()+2][GomokuGlobal.getChessboardy()+2];
	private int check_helper_p,check_helper_count;
	private int status;
	private boolean empty=true;
	private EventListenerList changeListeners=new EventListenerList();
	
	public void addChangeListener(ChangeListener listener) {
		changeListeners.add(ChangeListener.class,listener);
	}
	
	private int checkHelper(boolean needReset,int x,int y)
	{
		if(needReset){
			check_helper_p=chessboard[x][y];
			check_helper_count=1;
			return 0;
		}
		
		if(check_helper_p==chessboard[x][y]){
			check_helper_count++;
		}
		else{
			if(check_helper_count==5 && check_helper_p!=0){					
				return check_helper_p;
			}
			check_helper_p=chessboard[x][y];
			check_helper_count=1;
		}
		
		return 0;
	}
	
	private int checkIfPlayerWin()
	{
		int i,j,x;
		for(i=1;i<=GomokuGlobal.getChessboardx();++i){
			checkHelper(true,i,1);
			for(j=2;j<=GomokuGlobal.getChessboardy();++j){
				x=checkHelper(false,i,j);
				if(x>0) return x;
			}
			x=checkHelper(false,j,i);
			if(x>0) return x;
		}
		
		for(i=1;i<=GomokuGlobal.getChessboardy();++i){
			checkHelper(true,1,i);
			for(j=2;j<=GomokuGlobal.getChessboardx();++j){
				x=checkHelper(false,j,i);
				if(x>0) return x;
			}
			x=checkHelper(false,j,i);
			if(x>0) return x;
		}

		for(i=14;i>-15;--i){
			for(j=1;i+j<1 && j<16;++j);
			checkHelper(true,j,j+i);
			for(++j;i+j<=15 && j<16;++j){
				x=checkHelper(false,j,j+i);
				if(x>0) return x;
			}
			x=checkHelper(false,j,j+i);
			if(x>0) return x;
		}

		for(i=2;i<31;++i){
			for(j=1;i-j>15 && j<16;++j);
			checkHelper(true,j,i-j);
			for(++j;i-j>0 && j<16;++j){
				x=checkHelper(false,j,i-j);
				if(x>0) return x;
			}
			x=checkHelper(false,j,i-j);
			if(x>0) return x;
		}
		
		return 0;
	}
	public void claer() {
		empty=true;
		status=0;
		chessboard=new int[GomokuGlobal.getChessboardx()+2][GomokuGlobal.getChessboardy()+2];
		fireChanged();
	}

	protected void fireChanged()
	{
		ChangeEvent changeEvent=null;
		// Guaranteed to return a non-null array
	     Object[] listeners = changeListeners.getListenerList();
	     // Process the listeners last to first, notifying
	     // those that are interested in this event
	     for (int i = listeners.length-2; i>=0; i-=2) {
	         if (listeners[i]==ChangeListener.class) {
	             // Lazily create the event:
	             if (changeEvent == null)
	            	 changeEvent = new ChangeEvent(this);
	             ((ChangeListener)listeners[i+1]).stateChanged(changeEvent);
	         }
	     }
	}

	public int getChessboardState(int x, int y) {
		return chessboard[x][y];
	}
	
	public int getStatus()
	{
		return status;
	}

	public void removeChangeEventListener(ChangeListener listener) {
		changeListeners.remove(ChangeListener.class,listener);
	}

	public synchronized void setChessboardState(int x, int y, boolean isFirstPlayer) {
		if(chessboard[x][y]==0){
			chessboard[x][y]=isFirstPlayer?1:2;
			empty=false;
			fireChanged();
			setStatus(checkIfPlayerWin());
		}
	}

	private synchronized void setStatus(int s)
	{
		if(status==s) return ;
		
		status=s;
		fireChanged();
	}

	@Override
	public boolean isEmpty() {
		return empty;
	}
}
