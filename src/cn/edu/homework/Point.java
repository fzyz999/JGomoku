package cn.edu.homework;

public class Point{
	int m_x,m_y;
	Point(int x,int y){
		m_x=x;
		m_y=y;
	}
	
	public int getX(){
		return m_x;
	}
	public void setX(int x){
		m_x=x;
	}
	
	public int getY(){
		return m_y;
	}
	public void setY(int y){
		m_y=y;
	}
}
