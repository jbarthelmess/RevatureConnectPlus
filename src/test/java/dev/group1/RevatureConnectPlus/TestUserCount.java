package dev.group1.RevatureConnectPlus;

public class TestUserCount{
    private static int idCount;
    public int lastId;

    public TestUserCount(){
        idCount = 0;
        lastId = 0;
    }

    public TestUserCount(int id){
        lastId = id;
        idCount = lastId ++;
    }

    public int getLastId() {
        return lastId;
    }

    public void setLastId(int id) {
        lastId = id;
    }

    public int getIdCount() {
        return idCount;
    }

    public void setIdCount(int idCount) {
        idCount = idCount;
    }

    public void next(){
        lastId = idCount;
        idCount++;
    }


    public static void main(String[] args) {

    }

    @Override
    public String toString() {
        return "TestUserCount{" +
                "idCount=" + idCount +
                ", lastId=" + lastId +
                '}';
    }
}
