import java.util.Random;
import java.util.concurrent.Semaphore;

public class Swapper implements Runnable{
    private Shelf shelfA;
    private Shelf shelfB;
    private String name;
    private Semaphore librarian;
    Swapper(String name, Shelf shelfA, Shelf shelfB, Semaphore librarian){
        this.name = name;
        this.shelfA = shelfA;
        this.shelfB = shelfB;
        this.librarian = librarian;
    }
    @Override
    public void run() {
        Random random = new Random();
        int randomInt = 0;
        int randomIndA = 0;
        int randomIndB = 0;
        while(true){
            try {
                randomInt = random.nextInt(1000); // upto 1 sec
                Thread.sleep(randomInt);
                randomIndA = random.nextInt(shelfA.getCapacity());
                randomIndB = random.nextInt(shelfB.getCapacity());
                System.out.println(name + " is trying to acquire " + shelfA.getId());
                if (checkAcPermit(shelfA, shelfB)) {
                    shelfA.acquire();
                }

                System.out.println(name + " is trying to acquire " + shelfB.getId());
                if (checkAcPermit(shelfB, shelfA)) {
                    shelfB.acquire();
                }
                shelfA.swap(shelfB, randomIndA, randomIndB);
                System.out.println(name + " completed swap.");
                checkRePermit();
                shelfA.release();
                checkRePermit();
                shelfB.release();
                System.out.println(name + " has released the shelves.");
            } catch (InterruptedException ex) {
                System.out.println("Thread Interrupted.");
                break;
            }
        }
    }

    public Boolean checkAcPermit(Shelf s, Shelf p) throws InterruptedException {
        if (s.isTaken()||p.isTaken()){
            wait();
            System.out.println("waiting");
            return true;
        }else {
            librarian.acquire();
            System.out.println("Permitted to acquire");
            return false;
        }
    }
    public void checkRePermit() {
        librarian.release();
        System.out.println("Permitted to release");
    }
}


