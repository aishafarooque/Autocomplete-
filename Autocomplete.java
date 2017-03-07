import edu.princeton.cs.algs4.In;

import edu.princeton.cs.algs4.StdIn;

import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

import java.util.Comparator;

// A data type that provides autocomplete functionality for a given set of 

// string and weights, using Term and BinarySearchDeluxe. 

public class Autocomplete {
    private Term[] terms; //array of terms
    // Initialize the data structure from the given array of terms.
    public Autocomplete(Term[] terms) {
        //constructor exception
        if (terms == null) { throw new NullPointerException(); }

        this.terms = terms; //make a defensive copy 
        Arrays.sort(terms); //sort into lexographic order
    }

    // All terms that start with the given prefix, in descending order of
    // weight.

    public Term[] allMatches(String prefix) {
        //method exception
        if (prefix == null) { throw new java.lang.NullPointerException(); }
        Term prfx = new Term(prefix, 0); 
        Comparator<Term> prefixOrder = Term.byPrefixOrder(prefix.length());
        
        //obain the first index of i and occurence of prefix
        int i = BinarySearchDeluxe.firstIndexOf(terms, prfx,
                Term.byPrefixOrder(prefix.length()));
        if (i == -1) {
            throw new java.lang.NullPointerException();
        }
        
        int j = BinarySearchDeluxe.lastIndexOf(terms, prfx,
                Term.byPrefixOrder(prefix.length()));
        if (j == -1) {
            throw new java.lang.NullPointerException();
        }

        Term[] matches = new Term[j - i + 1];
        //copy 
        matches = Arrays.copyOfRange(terms, i, j);
        //construct an array containing n elements from terms
        Arrays.sort(matches, Term.byReverseWeightOrder()); //sort
        return matches; //return sorted array
    }
    // The number of terms that start with the given prefix.
    public int numberOfMatches(String prefix) {
        //method exception
        if (prefix.isEmpty()) {throw new java.lang.NullPointerException();}
        Term prfx = new Term(prefix, 0);
        Comparator<Term> prefixOrder = Term.byPrefixOrder(prefix.length());
        //obtain first
        
        int i = BinarySearchDeluxe.firstIndexOf(terms, prfx,
                Term.byPrefixOrder(prefix.length()));
        //obtain last
        int j = BinarySearchDeluxe.lastIndexOf(terms, prfx,
                Term.byPrefixOrder(prefix.length()));
        
        //compute and return 
        int found = j - i + 1;
        return found;
    }
    // Entry point. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Term[] terms = new Term[N];
        for (int i = 0; i < N; i++) {
            long weight = in.readLong(); 
            in.readChar(); 
            String query = in.readLine(); 
            terms[i] = new Term(query, weight); 
        }
        int k = Integer.parseInt(args[1]);
        Autocomplete autocomplete = new Autocomplete(terms);
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            Term[] results = autocomplete.allMatches(prefix);
            for (int i = 0; i < Math.min(k, results.length); i++) {
                StdOut.println(results[i]);
            }
        }
    }
}