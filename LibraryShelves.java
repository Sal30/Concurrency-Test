import java.util.concurrent.Semaphore;

public class LibraryShelves {
    public static void main(String[] args)
            throws InterruptedException {
        boolean check = true;
        Semaphore librarian = new Semaphore(2, check);
        Shelf shelfA= new Shelf(5, 98);
        Shelf shelfB= new Shelf(5, 54);
        Swapper sw1 = new Swapper("sw1", shelfA, shelfB, librarian);
        Swapper sw2 = new Swapper("sw2", shelfB, shelfA, librarian);
        Thread t1 = new Thread(sw1);
        Thread t2 = new Thread(sw2);
        t1.start();
        t2.start();
        Thread.sleep(10000);
        t1.interrupt();
        t2.interrupt();
        t1.join();
        t2.join();
    }
}

//A deadlock will occur in this program as it:
// 1. Shares resources under mutual exclusion - books
// 2. Needs two shelves in order to swap, so must wait for a shelf to become available
// 3. Shelf is responsible for releasing the book
// 4. A shelf must wait for another to release a book
// It doesn't deadlock all the time but it does occasionally