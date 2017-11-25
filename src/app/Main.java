package app;

import javafx.application.Application;
import plot.KDChart;
import support.KDTree;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Agile-flow on 3/21/2017.
 */
public class Main {

    public static void main(String[] args) throws Exception {

//        Scanner get = new Scanner(System.in);
//        KDTree<String, Double> tree = null;
//        String option;
//        boolean sentinel = false;
//
//        display("***********************************************************************");
//        display("***********************************************************************");
//        display("**************  Welcome to K-D Tree Test interface  ********************");
//        display("**************         Implemented in Java          *******************");
//        display("**************                  By                  *******************");
//        display("**************              Abiodun Abel            *******************");
//        display("***********************************************************************");
//        display("***********************************************************************");
//
//
//        if(tree != null){
//            sentinel = true;
//            display("Operations:");
//        }
//
//        while(sentinel){
//
//            display("\t\t 0 - insert\n\t\t 1 - find\n\t\t 2 - find minimum\n\t\t 3 - find maximum\n\t\t 4 - Nearest Neighbour\n\t\t 5 - remove\n\t\t 6 - exit");
//            System.out.print("waiting: ");
//            option = get.next();
//
//            switch (option){
//                case "0":
//
//                    break;
//                case "1":
//
//                    break;
//                case "2":
//
//                    break;
//                case "3":
//
//                    break;
//                case "4":
//
//                    break;
//                case "5":
//
//                    break;
//                case "6":
//                    sentinel = false;
//                    break;
//                default:
//                    display("\fUnknown operation!\nTry again");
//                    break;
//            }
//        }
//
//        display("You have exited!\n \f\f Good Bye!");
//
//
//






//        KDTree<String, Integer> tree = new KDTree<>(2);
//        tree.insert(35,90);
//        tree.insert(70,80);
//        tree.insert(10,75);
//        tree.insert(80,40);
//        tree.insert(50,90);
//        tree.insert(70,30);
//        tree.insert(90,60);
//        tree.insert(50,25);
//        tree.insert(25,10);
//        tree.insert(20,50);
//        tree.insert(60,10);

        KDTree<String, Integer> tree = new KDTree<>(2);

        tree.insert("First", 30,10);
        tree.insert("First", 28,1);
        tree.insert("First", 80,13);
        tree.insert("First", 20,0);
        tree.insert("First", 0,-8);
        tree.insert("First", 31,30);
        tree.insert("First", 23,9);
        tree.insert("First", 79,12);
        tree.insert("First", 60,103);
        tree.insert("First", 2,2);
        tree.insert("First", 208,14);
        tree.insert("First", 58,60);
        tree.insert("First", 804,17);
        tree.insert("First", 801,10);

        display("PreOrder Traversal");
        tree.preOrder();
        display("InOrder Traversal");
        tree.inOrder();
        display("Size: " + tree.size());

    }

    public static void display(Object msg){
        System.out.println("\n" + msg);
    }
}
