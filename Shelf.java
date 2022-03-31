import org.multiverse.api.StmUtils;
import org.multiverse.api.references.TxnInteger;

public class Shelf {
    private Book[] bookArray;
    private TxnInteger capacity;
    private TxnInteger id;

    Shelf(int firstCap, int firstId) {
        id = StmUtils.newTxnInteger(firstId);
        capacity = StmUtils.newTxnInteger(firstCap);
        bookArray = new Book[capacity.atomicGet()];
        Book book = bookArray[0];
        for(int i=0; i<capacity.atomicGet(); i++){
                bookArray[i] = new Book();
    }
        }
    public int getCapacity(){
        return capacity.atomicGet();
    }
    public int getId(){
        return id.atomicGet();
    }

    public Book getBookAtIndex(int index){
        return bookArray[index];
    }
    public void setBookAtIndex(int index, Book book){
        bookArray[index] = book;
    }
    public void swap(Shelf other, int originIndex,
                                  int destIndex){
        TxnInteger originIn = StmUtils.newTxnInteger(originIndex);
        TxnInteger destIn = StmUtils.newTxnInteger(destIndex);
        Book origin = getBookAtIndex(originIn.atomicGet());
        Book dest = other.getBookAtIndex(destIn.atomicGet());
        setBookAtIndex(originIn.atomicGet(), dest);
        other.setBookAtIndex(destIn.atomicGet(), origin);
    }
    public void atomicSwap(Shelf other, int origin, int dest){
        StmUtils.atomic(() -> {
            swap(other, origin, dest);
        });
    }

}
