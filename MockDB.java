public class MockDB implements Database {
    @Override
    public void insert(IMUData data) {
        // databse insertion here
        if(data != null){
            System.out.println("Insert data to DB: " + data.toString());
        }
    }
}