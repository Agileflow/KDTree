package others;

/**
 * Created by Agile-flow on 3/22/2017.
 */
public class KDTreeDataNode<T extends Comparable<T>> {

    private KDTreeDataNode<T> next;
    private T data;

    public KDTreeDataNode(T data){

        this.data = data;
        this.next = null;
    }

    public T getData(){
        return this.data;
    }

    public void setNext(KDTreeDataNode<T> next){
        this.next = next;
    }

    public KDTreeDataNode<T> next(){
        return this.next;
    }
}
