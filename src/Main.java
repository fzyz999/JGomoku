import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import cn.edu.homework.*;

public class Main extends JFrame {

	private JMenuBar mainMenuBar;
	private Gomoku gomoku;
	private JGomokuChessboard chessboard;
	private Player computer,computer2;

	public javax.swing.JMenuBar getMainMenuBar() {
		if (mainMenuBar == null) {
			mainMenuBar = new JMenuBar();

			// setup main menu bar
			JMenu jgomokuMenu = new JMenu(), newGame = new JMenu();
			jgomokuMenu.setText("game");
			newGame.setText("newGame");
			jgomokuMenu.add(newGame);

			JMenuItem pvp = new JMenuItem();
			newGame.add(pvp);
			pvp.setText("1P vs 2P");
			pvp.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					gomoku.start(chessboard.getChessboardListener(),
							chessboard.getChessboardListener());
				}
			});

			JMenu pvc = new JMenu();
			newGame.add(pvc);
			pvc.setText("1P vs Computer");
			JMenuItem pvcLuna=new JMenuItem();
			pvcLuna.setText("Computer Luna");
			pvcLuna.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					gomoku.start(chessboard.getChessboardListener(), computer);
				}
			});
			pvc.add(pvcLuna);
			JMenuItem pvcGG=new JMenuItem();
			pvcGG.setText("Computer GG");
			pvcGG.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					gomoku.start(chessboard.getChessboardListener(), computer2);
				}
			});
			pvc.add(pvcGG);

			JMenu cvp = new JMenu();
			newGame.add(cvp);
			cvp.setText("Computer vs 2P");
			JMenuItem cvpLuna=new JMenuItem();
			cvpLuna.setText("Computer Luna");
			cvpLuna.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					gomoku.start(computer, chessboard.getChessboardListener());
				}
			});
			cvp.add(cvpLuna);
			JMenuItem cvpGG=new JMenuItem();
			cvpGG.setText("Computer GG");
			cvpGG.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					gomoku.start(computer2, chessboard.getChessboardListener());
				}
			});
			cvp.add(cvpGG);
			
			mainMenuBar.add(jgomokuMenu);
		}
		return mainMenuBar;
	}

	public Main() {
		super();
		this.setTitle("JGomoku");
		this.setSize(500, 500);
		this.setJMenuBar(getMainMenuBar());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		chessboard = new JGomokuChessboard();
		gomoku = new Gomoku(chessboard.getModel());
		computer = new ComputerPlayerLuna();
		computer.setModel(chessboard.getModel());
		computer2 = new ComputerPlayerGG();
		computer2.setModel(chessboard.getModel());
		this.getContentPane().add(chessboard);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Main().setVisible(true);
			}
		});
	}
}
