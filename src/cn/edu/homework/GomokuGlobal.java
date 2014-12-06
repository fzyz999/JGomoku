package cn.edu.homework;

public class GomokuGlobal {
	private static final int chessboardX=15;
	private static final int chessboardY=15;
	private static final int BLACK=1;
	private static final int WHITE=2;
	/**
	 * @return chessboard size X
	 */
	public static int getChessboardx() {
		return chessboardX;
	}
	/**
	 * @return chessboard size Y
	 */
	public static int getChessboardy() {
		return chessboardY;
	}
	/**
	 * @return black
	 */
	public static int black() {
		return BLACK;
	}
	/**
	 * @return white
	 */
	public static int white() {
		return WHITE;
	}
}
