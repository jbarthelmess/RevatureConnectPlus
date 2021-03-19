package dev.group1.revatureconnectplus;

public class TestUserCount {
    private int idCount;
    private int lastId;

    public TestUserCount(int id) {
        this.lastId = id;
        this.idCount = id + 1;
    }

    public int getLastId() {
        return lastId;
    }

    private void setLastId(int id) {
        lastId = id;
    }

    public int getIdCount() {
        return idCount;
    }

    private void setIdCount(int id) {
        idCount = id;
    }

    public void next() {
        setLastId(idCount);
        setIdCount(idCount + 1);
    }

    @Override
    public String toString() {
        return "TestUserCount{" +
                "idCount=" + getIdCount() +
                ", lastId=" + getLastId() +
                '}';
    }
}
