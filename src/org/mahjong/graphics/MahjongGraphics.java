package org.mahjong.graphics;

import java.util.Collections;
import java.util.List;

import org.mahjong.client.Tile;
import org.mahjong.client.MahjongPresenter;
import org.mahjong.client.MahjongPresenter.MahjongMessage;
import org.mahjong.client.MahjongPresenter.View;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.Panel;
import com.google.common.collect.Iterables;

public class MahjongGraphics extends Composite implements View{
	public interface MahjongGraphicsUiBinder extends UiBinder<Widget, MahjongGraphics> {
		
	}
	

//for test
	@UiField
	HorizontalPanel testArea1;

//for test
	@UiField
	HorizontalPanel testArea2;
	
//for test
	@UiField
	HorizontalPanel testArea3;
	
//for test
	@UiField
	HorizontalPanel testArea4;

//for test
	@UiField
	HorizontalPanel testArea5;
	

	@UiField
	VerticalPanel leftHandArea;	
	@UiField
	VerticalPanel leftMeldArea;
	@UiField
	VerticalPanel leftWallArea;
	@UiField
	VerticalPanel leftCastArea;
	
	@UiField
	VerticalPanel rightHandArea;
	@UiField
	VerticalPanel rightMeldArea;
	@UiField
	VerticalPanel rightWallArea;
	@UiField
	VerticalPanel rightCastArea;
	
	@UiField
	HorizontalPanel upHandArea;
	@UiField
	HorizontalPanel upMeldArea;
	@UiField
	HorizontalPanel upWallArea;
	@UiField
	HorizontalPanel upCastArea;
	

	@UiField
	HorizontalPanel downHandArea;
	@UiField
	HorizontalPanel downMeldArea;
	@UiField
	HorizontalPanel downWallArea;
	@UiField
	HorizontalPanel downCastArea;
	@UiField
	HorizontalPanel selectedArea;
	@UiField
	Button chiButton;
	@UiField
	Button pengButton;
	@UiField
	Button huButton;
	@UiField
	Button skipButton;
	
	@UiField
	HorizontalPanel specialTileArea;
	

	
	private boolean enableClickForChi = false;
	private boolean enableClickForPeng = false;
	private boolean enableClickForGang = false;
	private boolean enableClickForCast = false;
	private boolean flagForSpecialTile = false;
	private final TileImageSupplier tileImageSupplier;
	private MahjongPresenter presenter;
	
	public MahjongGraphics() {
		TileImages tileImages = GWT.create(TileImages.class);
		this.tileImageSupplier = new TileImageSupplier(tileImages);
		MahjongGraphicsUiBinder uiBinder = GWT.create(MahjongGraphicsUiBinder.class);
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	public void setPresenter(MahjongPresenter mahjongPresenter) {
		this.presenter = mahjongPresenter;
	}
	
	public List<Image> createBackTiles(int tileNumber, int k) {
		List<TileImage> images = Lists.newArrayList();
		for(int i=0;i < tileNumber;i++) {
		if(k==1)
			images.add(TileImage.Factory.getNegativeVertical());
		if(k==2)
			images.add(TileImage.Factory.getNegativeHorizontal());
		}
		return createImages(images, false);
	}
	
	public List<Image> createWallTiles(List<Integer> wallNumber, int k) {
		List<TileImage> images = Lists.newArrayList();
		int wallSize = wallNumber.size();
		boolean flag = wallSize%2==0;
		int wallGrid = wallSize / 2;
		if(flag) {//wallSize is even
			if(wallSize != 0) {
				for(int i=0;i<wallGrid;i++) {
					if(k==1)
					images.add(TileImage.Factory.getNegativePileVertical());
					if(k==2)
					images.add(TileImage.Factory.getNegativePileHorizontal());
				}
			}
		}else {//wallSize is odd
			if(wallSize != 1) {
				for(int i=0;i<wallGrid;i++) {
					if(k==1) 
				    images.add(TileImage.Factory.getNegativePileVertical());
					if(k==2)
					images.add(TileImage.Factory.getNegativePileHorizontal());
				}
				if(k==1)
			    images.add(TileImage.Factory.getNegativeVertical());
				if(k==2)
				images.add(TileImage.Factory.getNegativeHorizontal());
			}
		}
		return createImages(images, false);
	}
	
	public List<Image> createMeldTiles(List<Tile> chiTiles, List<Tile> pengTiles, List<Tile> gangTiles, int k) {
		List<Tile> meldTiles = concat(concat(chiTiles,pengTiles), gangTiles);
		List<TileImage> images = Lists.newArrayList();
		for(Tile tile : meldTiles) {
		 switch (k) {
		 case 0:{
			 images.add(TileImage.Factory.getPositiveDown(tile));
			 break;
		 }
		 case 1:{
			 images.add(TileImage.Factory.getPositiveRight(tile));
			 break;
		 }
		 case 2:{
			 images.add(TileImage.Factory.getPositiveUp(tile));
			 break;
		 }
		 case 3:{
			 images.add(TileImage.Factory.getPositiveLeft(tile));
			 break;
		 }
		 default:
			 throw new RuntimeException("Forgot type: " + k);
		 }
		}
		return createImages(images, false);
	}
	
	private <T> List<T> concat(List<T> a, List<T> b){
		if(a==null) return b;
		if(b==null) return a;
		return Lists.newArrayList(Iterables.concat(a,b));
	}
	
	public List<Image> createCastTiles(List<Tile> castTiles, int k) {
		List<TileImage> images = Lists.newArrayList();
		for(Tile tile : castTiles) {
		 switch (k) {
		 case 0:{
			 images.add(TileImage.Factory.getPositiveDown(tile));
			 break;
		 }
		 case 1:{
			 images.add(TileImage.Factory.getPositiveRight(tile));
			 break;
		 }
		 case 2:{
			 images.add(TileImage.Factory.getPositiveUp(tile));
			 break;
		 }
		 case 3:{
			 images.add(TileImage.Factory.getPositiveLeft(tile));
			 break;
		 }
		 default:
			 throw new RuntimeException("Forgot type: " + k);
		 }
		}
		return createImages(images, false);	
	}
	
	public Image turnTileToImage(Tile tile) {
		TileImage tileImage = TileImage.Factory.getPositiveDown(tile);
		Image image = new Image(tileImageSupplier.getResource(tileImage));
		return image;
	}
	
	public List<Image> createTilesImages(List<Tile> myTiles) {
		List<TileImage> images = Lists.newArrayList();
		for(Tile tile : myTiles) {
			images.add(TileImage.Factory.getPositiveDown(tile));
		}
		return createImages(images, true);
	}
	
	private List<Image> createImages(List<TileImage> images, boolean withClick) {
		List<Image> res = Lists.newArrayList();
		for(TileImage img : images) {
			final TileImage imgFinal = img;
			Image image = new Image(tileImageSupplier.getResource(img));
			if(withClick) {
				image.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						if(enableClickForCast) {
							presenter.castTileSelected(imgFinal.tile, imgFinal.tile.getIndex());
						}
						if(enableClickForPeng) {
							presenter.pengTilesSelected(imgFinal.tile, imgFinal.tile.getIndex());
						}
						if(enableClickForGang) {
						}
						if(enableClickForChi) {
							presenter.chiTilesSelected(imgFinal.tile, imgFinal.tile.getIndex());
						}
					}
				});
			}
			res.add(image);
		}
		return res;
	}
	
	public void placeImages(Panel panel, List<Image> images) {
		panel.clear();
		for (Image image : images) {
			FlowPanel imageContainer = new FlowPanel();
			imageContainer.setWidth("80px");
			imageContainer.setHeight("100px");
			imageContainer.add(image);
			panel.add(imageContainer);
		}
	}
	
	public void placeImages1(Panel panel, List<Image> images) {
		panel.clear();
		for (Image image : images) {
			FlowPanel imageContainer = new FlowPanel();
			imageContainer.setWidth("80px");
			imageContainer.setHeight("100px");
			imageContainer.add(image);
			panel.add(imageContainer);
		}
	}
	
	public void placeImages2(Panel panel, List<Image> images) {
		panel.clear();
		for (Image image : images) {
			FlowPanel imageContainer = new FlowPanel();
			imageContainer.setWidth("100px");
			imageContainer.setHeight("80px");
			imageContainer.add(image);
			panel.add(imageContainer);
		}
	}
	
	public void placeSpecialTile(Panel panel, Image image) {
		if(flagForSpecialTile) return;	
		else {
			FlowPanel imgContainer = new FlowPanel();
			imgContainer.setWidth("120px");
			imgContainer.setHeight("140px");
			imgContainer.add(image);
			panel.add(imgContainer);
			flagForSpecialTile = true;
		}
	}
	
	@Override
	public void setViewerState(
			int tileNumberOfEast,
			int tileNumberOfNorth,
			int tileNumberOfWest,
			int tileNumberOfSouth,
			List<Integer> wallNumberOfEast,
			List<Integer> wallNumberOfNorth,
			List<Integer> wallNumberOfWest,
			List<Integer> wallNumberOfSouth,
			List<Tile> chiTilesOfEast,
	        List<Tile> pengTilesOfEast,
	        List<Tile> gangTilesOfEast,
	        List<Tile> chiTilesOfNorth,
            List<Tile> pengTilesOfNorth,
            List<Tile> gangTilesOfNorth,
	        List<Tile> chiTilesOfWest,
            List<Tile> pengTilesOfWest,
            List<Tile> gangTilesOfWest,
	        List<Tile> chiTilesOfSouth,
            List<Tile> pengTilesOfSouth,
            List<Tile> gangTilesOfSouth,
            Tile specialTile,
            List<Tile> castTilesOfEast,
            List<Tile> castTilesOfNorth,
            List<Tile> castTilesOfWest,
            List<Tile> castTilesOfSouth,
            MahjongMessage mahjongMessage) {

		placeImages1(downHandArea, createBackTiles(tileNumberOfEast,1));
		placeImages1(downWallArea, createWallTiles(wallNumberOfEast, 1));
		placeImages1(downMeldArea, createMeldTiles(chiTilesOfEast,pengTilesOfEast,gangTilesOfEast, 0));
		placeImages1(downCastArea, createCastTiles(castTilesOfEast, 0));
		
	    placeImages2(rightHandArea, createBackTiles(tileNumberOfNorth,2));
		placeImages2(rightWallArea, createWallTiles(wallNumberOfNorth, 2));
		placeImages2(rightMeldArea, createMeldTiles(chiTilesOfNorth,pengTilesOfNorth,gangTilesOfNorth, 1));
		placeImages2(rightCastArea, createCastTiles(castTilesOfNorth, 1));
		
	    placeImages1(upHandArea, createBackTiles(tileNumberOfWest,1));
		placeImages1(upWallArea, createWallTiles(wallNumberOfWest, 1));
		placeImages1(upMeldArea, createMeldTiles(chiTilesOfWest,pengTilesOfWest,gangTilesOfWest, 2));
		placeImages1(upCastArea, createCastTiles(castTilesOfWest, 2));
		
	    placeImages2(leftHandArea, createBackTiles(tileNumberOfSouth,2));
		placeImages2(leftWallArea, createWallTiles(wallNumberOfSouth, 2));
		placeImages2(leftMeldArea, createMeldTiles(chiTilesOfSouth,pengTilesOfSouth,gangTilesOfSouth, 3));
		placeImages2(leftCastArea, createCastTiles(castTilesOfSouth, 3));
		
		if(specialTile!=null && !flagForSpecialTile)
		placeSpecialTile(specialTileArea, turnTileToImage(specialTile));
		
		alertMahjongMessage(mahjongMessage);

	}
	

	//for Test
	@Override
	public void testButton1(String str) { 
		//testArea1.clear();
		FlowPanel containerPanel = new FlowPanel();
		containerPanel.add(new Button("" + str));
		testArea1.add(containerPanel);
	}
	
	//for Test
	@Override
	public void testButton2() { 
		//testArea2.clear();
		FlowPanel containerPanel = new FlowPanel();
		containerPanel.add(new Button("Test2"));
		testArea2.add(containerPanel);
	}
	
	//for Test
	@Override
	public void testButton3() { 
		//testArea3.clear();
		FlowPanel containerPanel = new FlowPanel();
		containerPanel.add(new Button("Test3"));
		testArea3.add(containerPanel);
	}
	
	//for Test
	@Override
	public void testButton4() { 
		//testArea4.clear();
		FlowPanel containerPanel = new FlowPanel();
		containerPanel.add(new Button("Test4"));
		testArea4.add(containerPanel);
	}
	
	//for Test
	@Override
	public void testButton5() { 
		//testArea5.clear();
		FlowPanel containerPanel = new FlowPanel();
		containerPanel.add(new Button("Test5"));
		testArea5.add(containerPanel);
	}

	
	
	@Override
	public void setPlayerState(
			int tileNumberOfOpponent1, 
			List<Tile> chiTilesOfOpponent1,
			List<Tile> pengTilesOfOpponent1,
			List<Tile> gangTilesOfOpponent1,
			List<Tile> castTilesOfOpponent1,
			List<Integer> wallNumberOfOpponent1, 
			int tileNumberOfOpponent2, 
			List<Tile> chiTilesOfOpponent2,
			List<Tile> pengTilesOfOpponent2,
			List<Tile> gangTilesOfOpponent2,
			List<Tile> castTilesOfOpponent2,
		    List<Integer> wallNumberOfOpponent2, 
			int tileNumberOfOpponent3,
			List<Tile> chiTilesOfOpponent3,
			List<Tile> pengTilesOfOpponent3,
			List<Tile> gangTilesOfOpponent3,
			List<Tile> castTilesOfOpponent3,
		    List<Integer> wallNumberOfOpponent3,
			List<Tile> myTiles,
			List<Tile> myChiTiles,
			List<Tile> myPengTiles,
			List<Tile> myGangTiles,
			List<Tile> myCastTiles,
			List<Integer> myWallNumber,
			Tile specialTile,
			List<Integer> myTilesIndexes,
			MahjongMessage mahjongMessage) {
	
	    for(int i=0;i<myTilesIndexes.size();i++) {
	    	myTiles.get(i).setIndex(myTilesIndexes.get(i));
	    }
	    
	    
	    Collections.sort(myTiles);
		placeImages1(downHandArea, createTilesImages(myTiles));
		placeImages1(downWallArea, createWallTiles(myWallNumber, 1));
		placeImages1(downMeldArea, createMeldTiles(myChiTiles,myPengTiles,myGangTiles, 0));

		placeImages1(downCastArea, createCastTiles(myCastTiles, 0));

		placeImages1(selectedArea, ImmutableList.<Image>of());
		

		
	    placeImages2(rightHandArea, createBackTiles(tileNumberOfOpponent1,2));
		placeImages2(rightWallArea, createWallTiles(wallNumberOfOpponent1, 2));
		placeImages2(rightMeldArea, createMeldTiles(chiTilesOfOpponent1,pengTilesOfOpponent1,gangTilesOfOpponent1, 1));
		placeImages2(rightCastArea, createCastTiles(castTilesOfOpponent1, 1));
		
		
	    placeImages1(upHandArea, createBackTiles(tileNumberOfOpponent2,1));
		placeImages1(upWallArea, createWallTiles(wallNumberOfOpponent2, 1));
		placeImages1(upMeldArea, createMeldTiles(chiTilesOfOpponent2,pengTilesOfOpponent2,gangTilesOfOpponent2, 2));
		placeImages1(upCastArea, createCastTiles(castTilesOfOpponent2, 2));

		
	    placeImages2(leftHandArea, createBackTiles(tileNumberOfOpponent3,2));
		placeImages2(leftWallArea, createWallTiles(wallNumberOfOpponent3, 2));
		placeImages2(leftMeldArea, createMeldTiles(chiTilesOfOpponent3,pengTilesOfOpponent3,gangTilesOfOpponent3, 3));
		placeImages2(leftCastArea, createCastTiles(castTilesOfOpponent3, 3));
		
		if(specialTile!=null && !flagForSpecialTile)
		placeSpecialTile(specialTileArea, turnTileToImage(specialTile));	
		
		chiButton.setEnabled(false);
		pengButton.setEnabled(false);
		huButton.setEnabled(false);
		skipButton.setEnabled(false);
		
		
		alertMahjongMessage(mahjongMessage);
	}
	
	private void alertMahjongMessage(MahjongMessage mahjongMessage) {
		switch (mahjongMessage) {
		    case INVISIBLE_: {
		    	return;
		    }
		    case INI_CONTINUE_: {
		    	return;
		    }
		    case NO_MOVE_: {
		    	return;
		    } 
		    case AUTO_HU_CHECK_: {
		    	presenter.autoHuCheck();
		    	return;
		    }
		    case AUTO_PENG_CHECK_: {
		    	presenter.autoPengCheck();
		    	return;
		    }
		    case AUTO_CHI_CHECK_: {
		    	presenter.autoChiCheck();
		    	return;
		    }
		    case WAIT_HU_CHOICE_: {
		    	huButton.setEnabled(true);
		    	skipButton.setEnabled(true);
		    	return;
		    }
		    case WAIT_PENG_CHOICE_: {
		    	pengButton.setEnabled(true);
		    	skipButton.setEnabled(true);
		    	return;
		    }
		    case WAIT_CHI_CHOICE_: {
		    	chiButton.setEnabled(true);
		    	skipButton.setEnabled(true);
		    	return;
		    }
		    //for test
		    case TEST_: {
		    	return;
		    }
		    default:
		    	throw new RuntimeException("No such message:" + mahjongMessage);
		}
	}
	
	@UiHandler("chiButton")
	void onClickChiButton(ClickEvent e) {
		chiButton.setEnabled(false);
		skipButton.setEnabled(false);
		presenter.waitForChiChoice(true);
		return;
	}
	
	@UiHandler("pengButton")
	void onClickPengButton(ClickEvent e) {
		pengButton.setEnabled(false);
		skipButton.setEnabled(false);
		presenter.waitForPengChoice(true);
		return;
	}
	
	@UiHandler("huButton")
	void onClickHuButton(ClickEvent e) {
		huButton.setEnabled(false);
		skipButton.setEnabled(false);
		presenter.waitForHuChoice(true);
		return;
	}
	
	@UiHandler("skipButton")
	void onClickSkipButton(ClickEvent e) {
		skipButton.setEnabled(false);
		if(huButton.isEnabled()) {
			huButton.setEnabled(false);
			presenter.waitForHuChoice(false);
			return;
		}
		if(chiButton.isEnabled()) {
			chiButton.setEnabled(false);
			presenter.waitForChiChoice(false);
			return;
		}
		if(pengButton.isEnabled()) {
			pengButton.setEnabled(false);
			presenter.waitForPengChoice(false);
			return;
		}
	} 
	
	@Override
	public void chooseCastTile(List<Tile> selectedCastTile, List<Tile> remainingTiles,
			List<Integer> selectedCastTileIndex, List<Integer> remainingTileIndexes) {		
		for(int i=0;i<remainingTileIndexes.size();i++) {
			remainingTiles.get(i).setIndex(remainingTileIndexes.get(i));
		}
	    for(int i=0;i<remainingTiles.size();i++) {
	    	remainingTileIndexes.set(i, remainingTiles.get(i).getIndex());
	    }
	    if(selectedCastTile.size() != 0)
		selectedCastTile.get(0).setIndex(selectedCastTileIndex.get(0));
		Collections.sort(remainingTiles);
		enableClickForCast = true;
	    placeImages1(downHandArea, createTilesImages(remainingTiles));
        placeImages1(selectedArea, createTilesImages(selectedCastTile));
		if(selectedCastTile.size() == 1) {
			enableClickForCast = false;
			presenter.finishedSelectingCastTile();
		}
	}
	
	@Override
	public void choosePengTiles(List<Tile> selectedPengTiles, List<Tile> remainingTiles,
			List<Integer> selectedPengTileIndexes, List<Integer> remainingTileIndexes) {
		for(int i=0;i<remainingTileIndexes.size();i++) {
			remainingTiles.get(i).setIndex(remainingTileIndexes.get(i));
		}
		Collections.sort(remainingTiles);
		for(int i=0;i<remainingTiles.size();i++) {
			remainingTileIndexes.set(i, remainingTiles.get(i).getIndex());
		}
		for(int i=0;i<selectedPengTileIndexes.size();i++) {
			selectedPengTiles.get(i).setIndex(selectedPengTileIndexes.get(i));
		}
		Collections.sort(selectedPengTiles);
		for(int i=0;i<selectedPengTiles.size();i++) {
			selectedPengTileIndexes.set(i, selectedPengTiles.get(i).getIndex());
		}
		enableClickForPeng = true;
	    placeImages1(downHandArea, createTilesImages(remainingTiles));
	    placeImages1(selectedArea, createTilesImages(selectedPengTiles));
	    
		if(selectedPengTiles.size() == 2) {
			enableClickForPeng = false;
			presenter.finishedSelectingPengTiles();
		}
	}
	
	@Override
	public void chooseChiTiles(List<Tile> selectedChiTiles, List<Tile> remainingTiles,
			List<Integer> selectedChiTileIndexes, List<Integer> remainingTileIndexes) {
		for(int i=0;i<remainingTileIndexes.size();i++) {
			remainingTiles.get(i).setIndex(remainingTileIndexes.get(i));
		}
		Collections.sort(remainingTiles);
		for(int i=0;i<remainingTiles.size();i++) {
			remainingTileIndexes.set(i, remainingTiles.get(i).getIndex());
		}
		for(int i=0;i<selectedChiTileIndexes.size();i++) {
			selectedChiTiles.get(i).setIndex(selectedChiTileIndexes.get(i));
		}	
		Collections.sort(selectedChiTiles);
		for(int i=0;i<selectedChiTiles.size();i++) {
			selectedChiTileIndexes.set(i, selectedChiTiles.get(i).getIndex());
		}
		enableClickForChi = true;
	    placeImages1(downHandArea, createTilesImages(remainingTiles));
	    placeImages1(selectedArea, createTilesImages(selectedChiTiles));
		if(selectedChiTiles.size() == 2) {
			enableClickForChi = false;
			presenter.finishedSelectingChiTiles();
		}
	}
}
