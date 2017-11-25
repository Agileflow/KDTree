package others;

import exception.IndexOutOfRangeException;

/**
 * Created by Agile-flow on 3/22/2017.
 */
public class KDTreeData<T extends Comparable<T>>{

    private KDTreeDataNode<T> head;
    private int size;

    public KDTreeData(){
        this.head = null;
        this.size = 0;
    }

    private KDTreeDataNode<T> Head(){
        return this.head;
    }

    public int add(T data){

        KDTreeDataNode<T> current = Head();
        int index = -1;

        while(current != null){
            current = current.next();
            index++;
        }

        current.setNext(new KDTreeDataNode<T>(data));
        this.size++;

        return (index + 1);
    }

    public T get(int index) throws IndexOutOfRangeException {

        KDTreeDataNode<T> current = Head();
        int counter = 0;

        while(counter != index){

            current = current.next();

            if(counter > index)
                throw new IndexOutOfRangeException("Index is out of range");
            counter++;
        }

        return current.getData();
    }

    public int size(){
        return this.size;
    }
}
