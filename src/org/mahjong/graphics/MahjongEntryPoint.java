package org.mahjong.graphics;

import org.mahjong.client.MahjongLogic;
import org.mahjong.client.MahjongPresenter;
import org.game_api.GameApi;
import org.game_api.GameApi.*;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;

public class MahjongEntryPoint implements EntryPoint{
	IteratingPlayerContainer container;
	//ContainerConnector container = null;
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
		container = new IteratingPlayerContainer(game,4);
		//container = new ContainerConnector(game);
		MahjongGraphics mahjongGraphics = new MahjongGraphics();
		mahjongPresenter =
				new MahjongPresenter(mahjongGraphics, container);

		final ListBox playerSelected = new ListBox();
		playerSelected.addItem("East");
		playerSelected.addItem("North"); // AI player
		playerSelected.addItem("West"); // AI player
		playerSelected.addItem("South"); // AI player
		playerSelected.addItem("Viewer");
		playerSelected.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				int selectedIndex = playerSelected.getSelectedIndex();
				//String playerId = selectedIndex == 4 ? GameApi.VIEWER_ID
				//	: container.getPlayerIds().get(selectedIndex);
				String playerId = null;
				if(selectedIndex == 4) {
			      playerId = GameApi.VIEWER_ID;
				}else if(selectedIndex == 0) {
				  playerId = container.getPlayerIds().get(selectedIndex);
				}else {
				  playerId = "10" + selectedIndex;
				}
				container.updateUi(playerId);
			}
		});

		FlowPanel flowPanel = new FlowPanel();
		flowPanel.add(mahjongGraphics);
		flowPanel.add(playerSelected);
		RootPanel.get("mainDiv").add(flowPanel);
		container.sendGameReady();
		container.updateUi(container.getPlayerIds().get(0));
	}
}
