package cn.edu.homework;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class ComputerPlayerGG extends AbstractComputerPlayer {

	private class Node {
		private int x, y, state;

		public Node() {
			x = 0;
			y = 0;
			state = -0xfffffff;
		}

		public Node(int x, int y, int state) {
			this.x = x;
			this.y = y;
			this.state = state;
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

		public int getState() {
			return state;
		}

		public void setState(int state) {
			this.state = state;
		}

	}

	private Deque<Node> additional_x;

	private int getStateHelper(int x, int y) {
		for (Iterator<Node> i = additional_x.iterator(); i.hasNext();) {
			Node ele = i.next();
			if (x == ele.getX() && y == ele.getY()) {
				return ele.getState();
			}
		}

		return getModel().getChessboardState(x, y);
	}

	private int compute(int currentPlayer) {
		int[][] x = new int[2][11];

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
		
		int value=0;
		//System.out.println("-----------------------");
		for (i = 0; i < 2; i++) {
			for (j = 0; j < 10; j++) {
				if (i + 1 == currentPlayer) {
					value += v[j] * x[i][j+1];
					//System.out.format("%d ", v[j] * x[i][j+1]);
				} else {
					value += v[j+10] * x[i][j+1];
					//System.out.format("%d ", v[j+10] * x[i][j+1]);
				}
			}
			//System.out.println();
		}

		return value;
	}
	
	static final int[] v={1,5,25,125,625,3125,15625,0xfffffff,0xfffffff,0xfffffff,
						  -25,-125,-625,-3125,-15625,-78125,-390625,-0xfffff,-0xfffff,-0xfffff};
	static final int[][] direction = { { -1, -1 }, { -1, 0 }, { -1, 1 },
		{ 0, 1 }, { 1, 1 }, { 1, 0 }, { 1, -1 } };
	private boolean fix_data_for_x(int a, int b) {
		int i,p=getStateHelper(a,b);
		for (i = 0; i < direction.length; i++) {
			if (getStateHelper(a + direction[i][0], b + direction[i][1]) != 0 &&
				getStateHelper(a + direction[i][0], b + direction[i][1]) != p) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void end(boolean isPlayerWin) {
		// TODO 自动生成的方法存根

	}

	private Node dfs(Set<Point> set, int deep, int currentPlayer) {
		if (deep == 0) {
			return new Node(
					0,
					0,
					compute(currentPlayer));
		}

		Node ret = new Node(0, 0, (deep & 1) == 0 ? -0xfffffff : 0xfffffff);
		Iterator<Point> it = set.iterator();
		while (it.hasNext()) {
			Point p = it.next();
			if (getModel().getChessboardState(p.getX(), p.getY()) == 0) {
				additional_x.push(new Node(p.getX(), p.getY(), currentPlayer));
				Set<Point> poss = new HashSet<Point>(set);
				poss.addAll(computePosHelper(p.getX(), p.getY()));
				Node tmp = dfs(
						poss,
						deep - 1,
						currentPlayer == GomokuGlobal.black() ? GomokuGlobal
								.white() : GomokuGlobal.black());
				if ((deep & 1) == 0 && tmp.getState() > ret.getState()) {
					ret.setX(p.getX());
					ret.setY(p.getY());
					ret.setState(tmp.getState());
				}
				if ((deep & 1) != 0 && tmp.getState() < ret.getState()) {
					ret.setX(p.getX());
					ret.setY(p.getY());
					ret.setState(tmp.getState());
				}
				additional_x.pop();
			}
		}
		return ret;
	}

	private Collection<Point> computePosHelper(int a, int b) {
		Collection<Point> ret = new ArrayList<Point>();
		int i, j, l = a - 4 > 0 ? a - 4 : 1;
		int r = a + 4 <= GomokuGlobal.getChessboardx() ? a + 4 : GomokuGlobal
				.getChessboardx();
		int top = b - 4 > 0 ? b - 4 : 1;
		int bottom = b + 4 <= GomokuGlobal.getChessboardy() ? b + 4
				: GomokuGlobal.getChessboardy();
		for (i = l; i <= r; i++) {
			if (getStateHelper(i, b) == 0) {
				ret.add(new Point(i, b));
			}
		}
		for (i = top; i <= bottom; i++) {
			if (getStateHelper(a, i) == 0) {
				ret.add(new Point(a, i));
			}
		}
		for (i = l, j = top; i <= r && j <= bottom; i++, j++) {
			if (getStateHelper(i, j) == 0) {
				ret.add(new Point(i, j));
			}
		}
		for (i = r, j = top; i >= l && j <= bottom; i--, j++) {
			if (getStateHelper(i, j) == 0) {
				ret.add(new Point(i, j));
			}
		}

		return ret;
	}

	private Set<Point> computePossiblePos() {
		Set<Point> ret = new HashSet<Point>();
		int i, j;
		for (i = 1; i <= GomokuGlobal.getChessboardx(); i++) {
			for (j = 1; j <= GomokuGlobal.getChessboardy(); j++) {
				if (getModel().getChessboardState(i, j) != 0) {
					ret.addAll(computePosHelper(i, j));
				}
			}
		}
		return ret;
	}

	@Override
	protected Point AIAction(int currentPlayer) {
		additional_x = new ArrayDeque<Node>();

		if (getModel().isEmpty()) {
			return new Point(GomokuGlobal.getChessboardx() / 2 + 1,
					GomokuGlobal.getChessboardy() / 2 + 1);
		}

		Set<Point> set = computePossiblePos();
		Node action = dfs(set, 2, currentPlayer == GomokuGlobal.black() ? GomokuGlobal
						.white() : GomokuGlobal.black());
		Point ret = new Point(action.getX(), action.getY());

		return ret;
	}

}
