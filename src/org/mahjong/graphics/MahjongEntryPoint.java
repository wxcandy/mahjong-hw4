package org.mahjong.graphics;

import org.mahjong.client.MahjongLogic;
import org.mahjong.client.MahjongPresenter;
import org.game_api.GameApi.Game;
import org.game_api.GameApi.ContainerConnector;
import org.game_api.GameApi.VerifyMove;
import org.game_api.GameApi.UpdateUI;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class MahjongEntryPoint implements EntryPoint{
  ContainerConnector container = null;
  MahjongPresenter mahjongPresenter;
	
  @Override
  public void onModuleLoad() {
    Game game = new Game() {
	  @Override
      public void sendVerifyMove(VerifyMove verifyMove) {
	    container.sendVerifyMoveDone(new MahjongLogic().verify(verifyMove));
	  }
	  @Override
	  public void sendUpdateUI(UpdateUI updateUI) {
        mahjongPresenter.updateUI(updateUI);
	  }
    };
    container = new ContainerConnector(game);
    MahjongGraphics mahjongGraphics = new MahjongGraphics();
    mahjongPresenter =
        new MahjongPresenter(mahjongGraphics, container);
  
    RootPanel.get("mainDiv").add(mahjongGraphics);
    container.sendGameReady();
    
  }
}
