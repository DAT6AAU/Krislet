import objects.ObjectInfo;

import utilities.PairGeneric;

public class GameState {

    private final int field_size_width = 100;
    private final int field_size_height = 100;
    private final int grid_vertial_count = 5;
    private final int grid_horizontal_count = 5;

    private GameStateCell[][] grid;


    public GameState() {
        cleanAndInitializeGrid();
    }

    public void cleanAndInitializeGrid(){
        grid = new GameStateCell[grid_vertial_count][grid_horizontal_count];
    }

    public boolean addObjectToGrid(double fieldCoordinateX, double fieldCoordinateY, ObjectInfo objectInfo){
        PairGeneric<Integer, Integer> cellCoordinates = fieldCoordinateToCellIndex(fieldCoordinateX, fieldCoordinateY);
        return addObjectToGrid(cellCoordinates.getFirst(), cellCoordinates.getSecond(), objectInfo);
    }

    public boolean addObjectToGrid(int cellX, int cellY, ObjectInfo objectInfo){
        if (isCoordinateOutOfBounds(cellX, cellY)) {
            return false;
        }

        grid[cellY][cellY].add(objectInfo);

        return true;
    }

    public PairGeneric<Integer, Integer> fieldCoordinateToCellIndex(double fieldCoordinateX, double fieldCoordinateY){
        if (isCoordinateOutOfBounds(fieldCoordinateX, fieldCoordinateY)){
            throw new IllegalArgumentException(); //TODO Handle?
        }

        return new PairGeneric<Integer, Integer>((int)(fieldCoordinateX/grid_horizontal_count), (int)(fieldCoordinateY/grid_vertial_count));
    }

    /*
    public Pair<Integer, Integer> CellIndexToFieldCoordinate(int cellX, int cellY){
       //TODO not needed
    }*/

    private boolean isCoordinateOutOfBounds(double fieldCoordinateX, double fieldCoordinateY){
        PairGeneric<Integer, Integer> cellCoordinates = fieldCoordinateToCellIndex(fieldCoordinateX, fieldCoordinateY);
        return isCoordinateOutOfBounds(cellCoordinates.getFirst(), cellCoordinates.getSecond());
    }

    // TODO: skal de to iffer ikke returne true?
    private boolean isCoordinateOutOfBounds(int cellX, int cellY){
        if (cellX < 0 || cellY < 0) {
            return false;
        }

        if (cellX > grid_vertial_count || cellY > grid_horizontal_count) {
            return false;
        }

        return false;
    }
}
