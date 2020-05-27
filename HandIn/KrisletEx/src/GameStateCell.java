import java.util.ArrayList;

import objects.ObjectInfo;

public class GameStateCell {

    public ArrayList<ObjectInfo> objects = new ArrayList<>();

    public void add(ObjectInfo objToAdd){
        objects.add(objToAdd);
    }
}
