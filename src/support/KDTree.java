package support;

import exception.*;
import plot.KDChart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Agile-flow on 3/22/2017.
 */

public class KDTree<T extends Comparable<T>, E extends Comparable<E>>{

    private KDTreeNode<T, E> root;
    private List<E[]> points;
    private int size;
    private final int DIMENSION;
    public int max_value;

    public KDTree(int dimen){
        this.DIMENSION = dimen;
        this.size = 0;
        this.root = null;
        this.points = new ArrayList<>();
    }

    private KDTreeNode<T, E> root(){
        return this.root;
    }

    private void createRoot(T data, E... coordinate) throws InvalidDimensionException { this.root = createNewNode(data, coordinate); }

    public List<E[]> getPoints(){ return this.points; }

    private KDTreeNode<T, E> createNewNode(T data, E... coord) throws InvalidDimensionException {

        if(coord.length != this.DIMENSION){
            throw new InvalidDimensionException("Incorrect coordinate");
        }

        return new KDTreeNode<T, E>(data, coord);
    }

    public int size(){
        return this.size;
    }

    public void insert(T data, E... coord) throws InvalidDimensionException, CoordinateDuplicateException, IndexOutOfRangeException{

        KDTreeNode<T, E> root = root();
        KDTreeNode<T, E> current = root;
        KDTreeNode<T, E> newNode = createNewNode(data, coord);
        int dimen = 0x0, limit = DIMENSION-1;

        if(root == null) {
            createRoot(data, coord);
            this.points.add(coord);
            this.size++;
            return;
        }

        while (current != null) {

            if(dimen > limit)
                dimen = 0;

            if (root.getPoint().compareTo(newNode.getPoint(), dimen) > 0) {
                if (root.getLeft() != null)
                    root = root.getLeft();
                else {
                    root.setLeft(newNode);
                    this.points.add(coord);
                    this.size++;
                    current = null;
                }
            } else if (root.getPoint().compareTo(newNode.getPoint(), dimen) < 0) {
                if (root.getRight() != null)
                    root = root.getRight();
                else {
                    root.setRight(newNode);
                    this.points.add(coord);
                    this.size++;
                    current = null;
                }
            } else {
                throw new CoordinateDuplicateException(newNode.getPoint().getCoordinate()[dimen] + " already exist on dimension {" + (dimen + 1) + "}.");
            }
            dimen++;
        }
    }

    public KDTreeNode<T, E> find(E... point) throws CoordinateDuplicateException, IndexOutOfRangeException, InvalidDimensionException, EmptyKDTreeException, CoordinateNotFoundException {

        if (root() == null) {
            throw new EmptyKDTreeException("Tree is empty.");
        }

        return find(root(), createNewNode(null, point), 0, DIMENSION-1);
    }

    private KDTreeNode<T, E> find(KDTreeNode<T, E> current, KDTreeNode<T, E> find, int dimen, int limit) throws IndexOutOfRangeException, InvalidDimensionException, CoordinateNotFoundException {

        if(current == null)
            return current;

        if(dimen > limit)
            dimen = 0;

        if(current.getPoint().compareTo(find.getPoint(), dimen) < 0){
            current = find(current.getRight(), find, dimen++, limit);
        }else if(current.getPoint().compareTo(find.getPoint(), dimen) > 0){
            current = find(current.getLeft(), find, dimen++, limit);
        }else{
            if(Arrays.equals(current.getPoint().getCoordinate(), find.getPoint().getCoordinate())){
                return current;
            }else {
                throw new CoordinateNotFoundException(find.getPoint().getCoordinate() + " does not exist in the tree.");
            }
        }
        return current;
    }

    public KDTreeNode<T, E> NN(E... coord) throws InvalidDimensionException, CoordinateDuplicateException, IndexOutOfRangeException{

        return NN(root(), root(), createNewNode(null, coord), 0, DIMENSION-1 );
    }

    private KDTreeNode<T, E> NN(KDTreeNode<T,E> current, KDTreeNode<T,E> currentNearest, KDTreeNode<T,E> point, int dimen, int limit) throws IndexOutOfRangeException, InvalidDimensionException {

        if(current == null)
            return currentNearest;

        if(findDistance(currentNearest.getPoint(), point.getPoint()) > findDistance(current.getPoint(), current.getPoint())){
            currentNearest = current;
        }

        if(dimen > limit)
            dimen = 0;

        if(current.getPoint().compareTo(point.getPoint(), dimen) < 0){

            currentNearest = NN(current.getRight(), currentNearest, point, dimen++, limit);

        }else if(current.getPoint().compareTo(point.getPoint(), dimen) > 0){

            currentNearest = NN(current.getLeft(), currentNearest, point, dimen++, limit);

        }else if (current.getPoint().compareTo(point.getPoint(), dimen) == 0){

            if(current.getPoint().compareTo(point.getPoint(), dimen+1) < 0){

                currentNearest = NN(current.getRight(), currentNearest, point, dimen++, limit);

            }else if(current.getPoint().compareTo(point.getPoint(), dimen+1) > 0){

                currentNearest = NN(current.getLeft(), currentNearest, point, dimen++, limit);

            }else if(current.getPoint().compareTo(point.getPoint(), dimen+1) == 0){

                return current;
            }
        }
        return currentNearest;
    }

    public KDTreeNode<T, E> findMin(int dimension) throws IndexOutOfRangeException, InvalidDimensionException {

        if(dimension - DIMENSION < 0){
            return findMin(root().getLeft(), root(), dimension-1); // branch left to get the min of X
        }else{
            return findMin(root(), root(), dimension-1);    // Recursively traverse the tree to get the min of Y
        }
    }

    private KDTreeNode<T, E> findMin(KDTreeNode<T, E> root, KDTreeNode<T, E> curMin, int dimen) throws IndexOutOfRangeException, InvalidDimensionException {

        if(root == null)
            return curMin;


        curMin = findMin(root.getLeft(), curMin, dimen);

        if(root.getPoint().compareTo(curMin.getPoint(), dimen) < 0){
            curMin = root;
        }

        curMin = findMin(root.getRight(), curMin, dimen);

        return curMin;
    }

    public KDTreeNode<T, E> findMax(int dimension) throws IndexOutOfRangeException, InvalidDimensionException {

        if(dimension - DIMENSION < 0){
            return findMax(root().getRight(), root(), dimension-1); // branch right to get the max of X
        }else{
            return findMax(root(), root(), dimension-1); // branch left to get the max of Y
        }
    }

    private KDTreeNode<T, E> findMax(KDTreeNode<T, E> root, KDTreeNode<T, E> curMin, int dimen) throws IndexOutOfRangeException, InvalidDimensionException {

        if(root == null)
            return curMin;


        curMin = findMax(root.getLeft(), curMin, dimen);

        if(root.getPoint().compareTo(curMin.getPoint(), dimen) > 0){
            curMin = root;
        }

        curMin = findMax(root.getRight(), curMin, dimen);

        return curMin;
    }

    public KDTreeNode<T, E> remove(E... coord) throws IndexOutOfRangeException, InvalidDimensionException, EmptyKDTreeException, CoordinateNotFoundException, CoordinateDuplicateException {

        return remove(root(), createNewNode(null, coord), null, 0, DIMENSION-1);
    }

    private KDTreeNode<T, E> remove(KDTreeNode<T, E> current, KDTreeNode<T, E> del, KDTreeNode<T, E> prev, int dimen, int limit) throws IndexOutOfRangeException, InvalidDimensionException, CoordinateNotFoundException {
        if(current == null)
            return del;

        if(dimen > limit)
            dimen = 0;

        if(current.getPoint().compareTo(del.getPoint(), dimen) < 0){
            prev = current;
            current = remove(current.getRight(), del, prev, dimen++, limit);
        }else if(current.getPoint().compareTo(del.getPoint(), dimen) > 0){
            prev = current;
            current = remove(current.getLeft(), del, prev, dimen++, limit);
        }else{
            if(Arrays.equals(current.getPoint().getCoordinate(), del.getPoint().getCoordinate())){

                KDTreeNode<T, E> replace;

                if(current.getLeft() != null && current.getRight() != null){
                    // has both left and right successors
                    replace = findMin(current.getRight(), current.getRight(), dimen);
                    current.getPoint().setCoordinate(replace.getPoint().getCoordinate());
                    current.setData(replace.getData());

                }else if(current.getLeft() != null) {
                    // has only left successor
                    replace = findMax(current.getLeft(), current.getLeft(), dimen);
                    current.getPoint().setCoordinate(replace.getPoint().getCoordinate());
                    current.setData(replace.getData());

                }else if(current.getRight() != null){
                    // has only right successor
                    replace = findMin(current.getRight(), current.getRight(), dimen);
                    current.getPoint().setCoordinate(replace.getPoint().getCoordinate());
                    current.setData(replace.getData());

                }else{
                    // has no successor
                    if(prev.getLeft() == current)
                        prev.setLeft(null);
                    else
                        prev.setRight(null);
                    this.size--;
                    return current;
                }

                this.points.remove(current.getPoint().getCoordinate());
                this.size--;
            }else {
                throw new CoordinateNotFoundException(del.getPoint().getCoordinate() + " does not exist in the tree.");
            }
        }
        return current;
    }

    private double findDistance(Point<E> A, Point<E> B){

        E[]  a = A.getCoordinate();
        E[]  b = B.getCoordinate();

        // check if both points are of the same dimension?
        // seems it has been checked previously

        int len = (a.length == b.length)?a.length: 0;
        double x = 0, y = 0;

        for(int i = 0; i < len; i++){
           x = convertEtoDouble(a[i]) - x;
           y = convertEtoDouble(b[i]) - y;
        }

        x = Math.pow(x, 2);
        y = Math.pow(y, 2);

        return Math.sqrt(x+y);
    }

    private double convertEtoDouble(E value){
        return Double.parseDouble(value.toString());
    }

    public void inOrder(){
        inOrder(root(), 'v');
    }

    public void preOrder() { preOrder(root()); }

    private void inOrder(KDTreeNode<T, E> root, Character t){
        if(root == null)
            return;

        inOrder(root.getLeft(), 'l');
        System.out.print(root.getPoint() + " --- ");
        inOrder(root.getRight(), 'r');
    }

    private void preOrder(KDTreeNode<T, E> root){
        if(root == null)
            return;

        System.out.print(root.getPoint() + " --- ");

        preOrder(root.getLeft());

        preOrder(root.getRight());
    }
}
