import objects.ObjectInfo;

import java.util.ArrayList;

public class GameStateCell {

    public ArrayList<ObjectInfo> objects = new ArrayList<>();

    public void add(ObjectInfo objToAdd){
        objects.add(objToAdd);
    }
}
