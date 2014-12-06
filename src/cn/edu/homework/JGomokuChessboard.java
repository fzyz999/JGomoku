package cn.edu.homework;

import java.awt.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import cn.edu.homework.JGomokuChessboardListener;
import cn.edu.homework.BasicJGomokuChessboardListener;
import cn.edu.homework.JGomokuChessboardModel;
import cn.edu.homework.DefaultJGomokuChessboardModel;
import cn.edu.homework.GomokuGlobal;

public class JGomokuChessboard extends JComponent{
	
	private JGomokuChessboardListener chessboardListener=new BasicJGomokuChessboardListener();
	public JGomokuChessboardListener getChessboardListener() {
		return chessboardListener;
	}

	public void setChessboardListener(JGomokuChessboardListener chessboardListener) {
		this.chessboardListener = chessboardListener;
	}
	
	private class ModelChangeListener implements ChangeListener{
		@Override
		public void stateChanged(ChangeEvent e) {
			repaint();
		}	
	}
	
	public JGomokuChessboard(){
		addMouseListener(chessboardListener);
		setModel(new DefaultJGomokuChessboardModel());
		init();
	}
	
	private void init()
	{
		setVisible(true);
		setOpaque(false);
		setMinimumSize(new Dimension(160,160));
		setPreferredSize(new Dimension(600,600));
		setBackground(Color.white);
		setForeground(Color.black);
	}
	
	protected void paintComponent(Graphics g)
	{
		JGomokuChessboardModel chessboardModel=chessboardListener.getModel();
		int w=getWidth(),h=getHeight();
		
		g.setColor(getBackground());
		g.fillRect(0, 0, w, h);
		g.setColor(getForeground());
		
		int stepx=w/(GomokuGlobal.getChessboardx()+1),
		    stepy=h/(GomokuGlobal.getChessboardy()+1);
		int i,j,end;
		for(i=stepx,end=stepy*GomokuGlobal.getChessboardx();i<w-stepx;i+=stepx){
			g.drawLine(i, stepy, i, end);
		}
		for(i=stepy,end=stepx*GomokuGlobal.getChessboardx();i<h-stepy;i+=stepy){
			g.drawLine(stepx, i, end, i);
		}
		
		int basex,basey;
		for(i=1,basex=stepx/2;i<=GomokuGlobal.getChessboardx();i++,basex+=stepx){
			for(j=1,basey=stepy/2;j<=GomokuGlobal.getChessboardy();j++,basey+=stepy){
				int state=chessboardModel.getChessboardState(i, j);
				if(state==1){
					g.setColor(getForeground());
					g.fillOval(basex, basey, stepx, stepy);
				}
				else if(state==2){
					g.setColor(getForeground());
					g.fillOval(basex, basey, stepx, stepy);
					g.setColor(getBackground());
					g.fillOval(basex+1, basey+1, stepx-2, stepy-2);
				}
			}
		}
	}

	/**
	 * @return chessboardModel
	 */
	public JGomokuChessboardModel getModel() {
		return chessboardListener.getModel();
	}

	/**
	 * @param chessboardModel 要设置的 chessboardModel
	 */
	public void setModel(JGomokuChessboardModel chessboardModel) {
		chessboardModel.addChangeListener(new ModelChangeListener());
		chessboardListener.setModel(chessboardModel);
	}
}
