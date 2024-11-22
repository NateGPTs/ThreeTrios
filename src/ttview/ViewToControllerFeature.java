package ttview;

import controller.PlayerFeatures;

public interface ViewToControllerFeature extends ViewFunctionFeatures {

  void addListener(PlayerFeatures feature);


  void showPanel(String message, String title);

  void setVisible(Boolean visible);
}
