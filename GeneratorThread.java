import java.util.Random;

public class GeneratorThread extends Thread {
	
    private Counter mCounter ;
    private int mId =-1 ;

    public GeneratorThread(int id, Counter counter){
        mCounter = counter ;
        mId = id ;
    }
    public void run() {
        try {
            // Displaying the thread that is running
            int generatedNumber = generateRandomNumber();
			String generatedLine = String.format(" Thread %d - generated number: %d\n", mId, generatedNumber) ;
			System.out.println(generatedLine);	
            synchronized (mCounter){
			            mCounter.increment(generatedNumber);
            }
        } catch (Exception e) {
            // Throwing an exception
            System.out.println("Exception is caught -- Details : "+e);
        }
    }


    private int generateRandomNumber() {
        Random rand = new Random();
        return rand.nextInt(100);
    }
}
