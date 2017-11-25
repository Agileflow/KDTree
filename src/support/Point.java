package support;

import exception.IndexOutOfRangeException;
import exception.InvalidDimensionException;

import java.util.Arrays;

/**
 * Created by Agile-flow on 3/24/2017.
 */
class Point<E extends Comparable<E>> {

    private E[] coordinate;

    protected Point(E... coordinate){
        this.coordinate = coordinate;
    }

    protected E[] getCoordinate(){ return this.coordinate; }

    protected void setCoordinate(E... coordinate){ this.coordinate = coordinate; }

    private int getDimension(){
        return this.coordinate.length;
    }

    protected int compareTo(Point<E> node, int dimen) throws InvalidDimensionException, IndexOutOfRangeException {

        if(this.getDimension() != node.getDimension())
            throw new InvalidDimensionException("Incomplete dimension of data");

        if(dimen > this.getDimension() - 1)
            throw new IndexOutOfRangeException("Dimension is out of range");


        return this.getCoordinate()[dimen].compareTo(node.getCoordinate()[dimen]);
    }

    @Override
    public String toString() {
        return Arrays.toString(this.getCoordinate());
    }
}
