package cn.edu.homework;

import java.util.EventListener;
import cn.edu.homework.PlayerActionEvent;

public interface PlayerActionEventListener extends EventListener {
	public void playerAction(PlayerActionEvent event);
}
