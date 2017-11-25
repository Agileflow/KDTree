package support;

import java.util.Arrays;

/**
 * Created by Agile-flow on 3/21/2017. 3MMBXW9
 */
class KDTreeNode<T extends Comparable<T>, E extends Comparable<E>> {

    private KDTreeNode<T, E> left;
    private KDTreeNode<T, E> right;

    private Point<E> point;

    private T data;

    protected KDTreeNode(T data, E... coordinate){

        this.point = new Point(coordinate);
        this.data = data;
        this.left = null;
        this.right = null;
    }

    protected void setLeft(KDTreeNode<T, E> left){
        this.left = left;
    }

    protected void setRight(KDTreeNode<T, E> right){
        this.right = right;
    }

    protected KDTreeNode<T, E> getLeft(){
        return this.left;
    }

    protected KDTreeNode<T, E> getRight(){
        return this.right;
    }

    protected T getData(){
        return this.data;
    }

    protected void setData(T data){
        this.data = data;
    }

    protected Point<E> getPoint(){ return this.point; }

    protected int compareTo(KDTreeNode<T, E> node){
        return this.getData().compareTo(node.getData());
    }

    @Override
    public String toString() {
        return this.getData().toString() + " " + this.getPoint();
    }
}
