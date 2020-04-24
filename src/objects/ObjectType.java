package objects;

public enum ObjectType {
    BALL("ball"), FLAG("flag"), GOAL("goal"), LINE("line"), PLAYER("player");

    private String identifier;

    ObjectType(String identifier) {
        this.identifier = identifier;
    }

    public static ObjectType getTypeFromIdentifier(String identifier) {
        for (ObjectType type : ObjectType.values()) {
            if (type.identifier == identifier)
                return type;
        }

        throw new IllegalArgumentException("Did not receive a valid identifier!");
    }
}
