import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;
import java.util.Comparator;

// An immutable data type that represents an autocomplete term: a query string 
// and an associated real-valued weight.
public class Term implements Comparable<Term> {
    private final String query;
    private final long weight;

    // Construct a term given the associated query string, having weight 0.
    public Term(String query) {
        this(query, 0);
    }

    // Construct a term given the associated query string and weight.
    public Term(String query, long weight) {
        //exceptions
        if (query == null) {throw new java.lang.NullPointerException();}
        if (weight < 0) {throw new java.lang.IllegalArgumentException();}
        
        this.query = query;
        this.weight = weight;
    }

    // A reverse-weight comparator.
    public static Comparator<Term> byReverseWeightOrder() {
        return new ReverseWeightOrder();
    }

    // Helper reverse-weight comparator.
    private static class ReverseWeightOrder implements Comparator<Term> {
        public int compare(Term v, Term w) {
            //compare
            if (v.weight < w.weight) {
                return +1;
            } 
            else if (v.weight == w.weight) {
                return 0;
            }
            else if (v.weight > w.weight) {
                return -1;
            }
            return 0;
        }
    }

    // A prefix-order comparator.
    public static Comparator<Term> byPrefixOrder(int r) {
        return new PrefixOrder(r);
    }

    // Helper prefix-order comparator.
    private static class PrefixOrder implements Comparator<Term> {
        private int r;
        PrefixOrder(int r) {
            this.r = r; //initialize
        }

        public int compare(Term v, Term w) {
            //comapre v and w
            int minv = Math.min(r, v.query.length());
            int minw = Math.min(r, w.query.length());
            String sS1 = v.query.substring(0, minv);
            String sS2 = w.query.substring(0, minw);
            int cmp = sS1.compareTo(sS2);
            return cmp;
        }
    }

    // Compare this term to that in lexicographic order by query and 
    // return a negative, zero, or positive integer based on whether this 
    // term is smaller, equal to, or larger than that term.
    public int compareTo(Term that) {
        String sS1 = this.query;
        String sS2 = that.query;
        return sS1.compareTo(sS2);
    }

    // A string representation of this term.
    public String toString() {
        return weight + "\t" + query;
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        int k = Integer.parseInt(args[1]);
        In in = new In(filename);
        int N = in.readInt();
        Term[] terms = new Term[N];
        for (int i = 0; i < N; i++) {
            long weight = in.readLong(); 
            in.readChar(); 
            String query = in.readLine(); 
            terms[i] = new Term(query, weight); 
        }
        StdOut.printf("Top %d by lexicographic order:\n", k);
        Arrays.sort(terms);
        for (int i = 0; i < k; i++) {
            StdOut.println(terms[i]);
        }
        StdOut.printf("Top %d by reverse-weight order:\n", k);
        Arrays.sort(terms, Term.byReverseWeightOrder());
        for (int i = 0; i < k; i++) {
            StdOut.println(terms[i]);
        }
    }
}
