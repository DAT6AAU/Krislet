package objects;

/** Base class for visual object classes. */
public class ObjectInfo {

    private ObjectType m_type;
    public float m_distance;
    public float m_direction;
    public float m_distChange;
    public float m_dirChange;

    public ObjectInfo(ObjectType type) {
        m_type = type;
    }

    public float getDistance() {
        return m_distance;
    }

    public float getDirection() {
        return m_direction;
    }

    public float getDistChange() {
        return m_distChange;
    }

    public float getDirChange() {
        return m_dirChange;
    }

    public ObjectType getType() {
        return m_type;
    }
}
