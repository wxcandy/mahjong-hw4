package org.mahjong.graphics;

import com.google.gwt.i18n.client.Constants;
import com.google.gwt.core.client.GWT;

public interface LanguageIngredients extends Constants{
	
  public static LanguageIngredients languageIngredients = GWT.create(LanguageIngredients.class);

  @DefaultStringValue("Hu")
  String hu();
  
  @DefaultStringValue("Chi")
  String chi();
  
  @DefaultStringValue("Peng")
  String peng();
  
  @DefaultStringValue("Skip")
  String skip();
  
  @DefaultStringValue("Player")
  String player();
  
  @DefaultStringValue(" wins the match!")
  String wins();
}
