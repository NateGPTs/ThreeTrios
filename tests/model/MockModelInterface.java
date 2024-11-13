package model;

import java.util.List;
import java.util.Map;

public interface MockModelInterface extends ReadOnlyThreeThriosModel {

  void setFlipValue(int row, int col, int value);

  void setValidMove(int row, int col, boolean isValid);

  List<Map<String, Integer>> getInspectedMoves();

  void clearInspectionLog();

  void playCorners();

  void playMostFlips();

}
