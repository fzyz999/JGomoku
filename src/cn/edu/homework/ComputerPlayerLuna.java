package cn.edu.homework;

import cn.edu.homework.GomokuGlobal;

public class ComputerPlayerLuna extends AbstractComputerPlayer {

	private double[] w = new double[21];
	private int[][] x;
	private int cx, cy;
	private int currentPlayer;

	public ComputerPlayerLuna() {
		int i,tmp=1;
		for (i = 0; i < 10; i++) {
			w[i] = tmp;
			w[i + 10] = -25*tmp;
			tmp*=5;
		}
		w[8]=w[9]=0xfffffff;
		w[20] = 0;
	}

	private int getStateHelper(int x, int y) {
		if (x != cx || y != cy) {
			return getModel().getChessboardState(x, y);
		}
		return currentPlayer;
	}

	private void compute() {
		x = new int[2][11];

		int p, count;
		int flag;
		int i, j;
		for (i = 1; i <= GomokuGlobal.getChessboardx(); ++i) {
			p = getStateHelper(i, 1);
			count = 1;
			flag = 3;
			for (j = 2; j <= GomokuGlobal.getChessboardy(); ++j) {
				if (getStateHelper(i, j) == p) {
					count++;
				} else {
					if (p != 0 && (flag == 0 || getStateHelper(i, j) == 0)
							&& count <= 5) {
						if (flag > 0 || getStateHelper(i, j) > 0) {
							x[p - 1][count * 2 - 1]++;
						} else {
							x[p - 1][count * 2]++;
						}
					}
					count = 1;
					flag = p;
					p = getStateHelper(i, j);
				}
			}
			if (p != 0 && flag == 0 && count <= 5) {
				x[p - 1][count * 2 - 1]++;
			}
		}

		for (i = 1; i <= GomokuGlobal.getChessboardy(); ++i) {
			p = getStateHelper(1, i);
			count = 1;
			flag = 3;
			for (j = 2; j <= GomokuGlobal.getChessboardx(); ++j) {
				if (getStateHelper(j, i) == p) {
					count++;
				} else {
					if (p != 0 && (flag == 0 || getStateHelper(j, i) == 0)
							&& count <= 5) {
						if (flag > 0 || getStateHelper(j, i) > 0) {
							x[p - 1][count * 2 - 1]++;
						} else {
							x[p - 1][count * 2]++;
						}
					}
					count = 1;
					flag = p;
					p = getStateHelper(j, i);
				}
			}
			if (p != 0 && flag == 0 && count <= 5) {
				x[p - 1][count * 2 - 1]++;
			}
		}

		for (i = 14; i > -15; --i) {
			for (j = 1; i + j < 1 && j < 16; ++j)
				;
			p = getStateHelper(j, j + i);
			count = 1;
			flag = 3;
			for (++j; i + j <= 15 && j < 16; ++j) {
				if (getStateHelper(j, j + i) == p) {
					count++;
				} else {
					if (p != 0 && (flag == 0 || getStateHelper(j, j + i) == 0)
							&& count <= 5) {
						if (flag > 0 || getStateHelper(j, j + i) > 0) {
							x[p - 1][count * 2 - 1]++;
						} else {
							x[p - 1][count * 2]++;
						}
					}
					count = 1;
					flag = p;
					p = getStateHelper(j, j + i);
				}
			}
			if (p != 0 && flag == 0 && count <= 5) {
				x[p - 1][count * 2 - 1]++;
			}
		}

		for (i = 2; i < 31; ++i) {
			for (j = 1; i - j > 15 && j < 16; ++j)
				;
			p = getStateHelper(j, i - j);
			count = 1;
			flag = 3;
			for (++j; i - j > 0 && j < 16; ++j) {
				if (getStateHelper(j, i - j) == p) {
					count++;
				} else {
					if (p != 0 && (flag == 0 || getStateHelper(j, i - j) == 0)
							&& count <= 5) {
						if (flag > 0 || getStateHelper(j, i - j) > 0) {
							x[p - 1][count * 2 - 1]++;
						} else {
							x[p - 1][count * 2]++;
						}
					}
					count = 1;
					flag = p;
					p = getStateHelper(j, i - j);
				}
			}
			if (p != 0 && flag == 0 && count <= 5) {
				x[p - 1][count * 2 - 1]++;
			}
		}

		// fix data for x[][1] and x[][2];
		x[0][1] = x[0][2] = x[1][1] = x[1][2] = 0;
		for (i = 1; i <= GomokuGlobal.getChessboardx(); ++i) {
			for (j = 2; j <= GomokuGlobal.getChessboardy(); ++j) {
				if (getStateHelper(i, j) != 0) {
					if (fix_data_for_x(i, j)) {
						x[getStateHelper(i, j) - 1][1]++;
					} else {
						x[getStateHelper(i, j) - 1][2]++;
					}
				}
			}
		}
	}

	private boolean fix_data_for_x(int a, int b) {
		int i;
		for (i = 0; i < direction.length; i++) {
			if (getStateHelper(a + direction[i][0], b + direction[i][1]) != 0) {
				return true;
			}
		}
		return false;
	}

	static final int[][] direction = { { -1, -1 }, { -1, 0 }, { -1, 1 },
			{ 0, 1 }, { 1, 1 }, { 1, 0 }, { 1, -1 } };
	static final int fix[] = { 1, 3, 100, 1000, 8000, 78000 };

	@Override
	public void end(boolean isPlayerWin) {

	}

	private double computeValue(int[][] _x) {
		double value = 0;
		int i, j;
		for (i = 0; i < 2; i++) {
			for (j = 0; j < 10; j++) {
				if (i + 1 == currentPlayer) {
					value += w[j] * _x[ i ][ j + 1];
				} else {
					value += w[j + 10] * _x[i][ j + 1];
				}
			}
		}
		value += w[20];
		return value;
	}

	@Override
	protected Point AIAction(int currentPlayer) {
		cx = 0;
		cy = 0;
		compute();

		if (getModel().isEmpty()) {
			return new Point(GomokuGlobal.getChessboardx() / 2 + 1,
					GomokuGlobal.getChessboardy() / 2 + 1);
		}
		this.currentPlayer = currentPlayer;
		int i, j;
		double maxValue = -1000000.0;
		boolean hasMax = false;
		Point ret = new Point(0, 0);
		for (i = 1; i <= GomokuGlobal.getChessboardx(); i++) {
			for (j = 1; j <= GomokuGlobal.getChessboardy(); j++) {
				if (getModel().getChessboardState(i, j) == 0) {
					cx = i;
					cy = j;
					compute();
					double value = computeValue(x);
					if (hasMax == false || value > maxValue) {
						maxValue = value;
						ret.setX(i);
						ret.setY(j);
						hasMax = true;
					}
				}
			}
		}

		return ret;
	}

}
