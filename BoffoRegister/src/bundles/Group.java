package bundles;

/**
 * A container that maps an identifier element to a list of corresponding
 * contents.
 *
 * @author Michael Resnik
 * @author Travis Cox
 * @param <T> A class type that must implement the TicketElement interface.
 * Delineates what type of elements are stored in the contents.
 * @lastEdited 04/18/2017
 */
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Group<T extends TicketElement> {

    /**
     * Compares groups based on the name of the first element.
     */
    public static final Comparators.NameComparator BYNAME = new Comparators.NameComparator();
    /**
     * Compares groups based on the sku of the first element.
     */
    public static final Comparators.SkuComparator BYSKU = new Comparators.SkuComparator();
    /**
     * Compares groups based on the sum of all prices of contents.
     */
    public static final Comparators.PriceComparator BYPRICE = new Comparators.PriceComparator();

    private final T element;
    private final List<T> contents;


    /**
     * Constructor initializes the head element and populates the contents list
     * using clones.
     *
     * @param _element The head element of a list which represents its contents.
     * @param _number The number of _element clones to populate the contents
     * list.
     */
    public Group(T _element, int _number) {
        this.element = _element;
        this.contents = new ArrayList();
        add(_number);
    }


    /**
     *
     * Constructor initializes the head element and sets the contents list.
     *
     * @param _element The head element of a list which represents its contents.
     * @param _contents The elements that match with the head element.
     */
    public Group(T _element, List<T> _contents) {
        this.element = _element;
        this.contents = _contents;
    }


    /**
     * Adds clones of the first element to the contents list.
     *
     * @param _numberToAdd The number of head elements to clone and add to the
     * content list.
     * @return The new number of elements in the contents list.
     */
    public int add(int _numberToAdd) {
        for (int i = 0; i < _numberToAdd; i++) {
            this.contents.add(this.element);
        }
        return size();
    }


    /**
     * Adds the passed element to the contents list.
     *
     * @param _element The element to add to the contents list.
     * @return The new number of elements in the contents list.
     */
    public int add(T _element) {
        this.contents.add(_element);
        return size();
    }


    /**
     * Adds all elements of the passed list to the contents list.
     *
     * @param _list List of elements to append to the contents list.
     * @return The new number of elements in the contents list.
     */
    public int add(List<T> _list) {
        for (T obj : _list) {
            this.contents.add(obj);
        }
        return size();
    }


    /**
     * Clones the current object so that Groups can be immutable in different
     * contexts.
     *
     * @return A copy of the Group object with a copy of a list.
     */
    @Override
    public Group<T> clone() {
        List<T> cloneContents = new ArrayList();
        for (T obj : this.contents) {
            cloneContents.add(obj);
        }
        return new Group(this.element.clone(), cloneContents);
    }


    // TODO
    @Override
    public boolean equals(Object _obj) {
        return false;
    }


    /**
     * Removes one element off the tail of the list.
     *
     * @return The removed element.
     */
    public T decrement() {
        List<T> subtracted = subtract(1);
        if (subtracted.isEmpty()) {
            return null;
        }
        return subtracted.get(0);
    }


    /**
     * Returns a copy of the current contents list.
     *
     * @return A copy of the current contents list.
     */
    public List<T> getContents() {
        List<T> retList = new ArrayList();
        for (T obj : this.contents) {
            retList.add(obj);
        }
        return retList;
    }


    /**
     * Returns the head element of the group that describes the contents list.
     *
     * @return An element representation of the Group object / its contents.
     */
    public T getElement() {
        return this.element;
    }


    /**
     * Returns the price of the group based on the sum of the elements.
     *
     * @return The sum of all contained prices.
     */
    public double getPrice() {
        double sum = 0.0;
        for (T obj : this.contents) {
            sum += obj.getPrice();
        }
        return sum;
    }


    /**
     * Adds a copy of the head element to the tail of the list.
     *
     * @return The size of the new contents list.
     */
    public int increment() {
        return add(+1);
    }


    /**
     * Returns the size of the contents list.
     *
     * @return The size of the contents list.
     */
    public int size() {
        return this.contents.size();
    }


    /**
     * Removes a set number of elements from the tail of the list.
     *
     * @param _numberToSubtract The size of the elements to remove.
     * @return A list of elements that have been removed from the end.
     */
    public List<T> subtract(int _numberToSubtract) {
        List<T> retList = new ArrayList();
        for (int i = 0; i < _numberToSubtract; i++) {
            if (this.contents.isEmpty()) {
                break;
            }
            T obj = this.contents.remove(this.contents.size() - 1);
            retList.add(obj);
        }
        return retList;
    }


    @Override
    public String toString() {
        return "(" + this.element + ", " + size() + ')';
    }

    // A private subclass that contains Group-based comparators.
    private static class Comparators {

        // Compares TicketElements based on the non case-sensitive names of groups.
        public static class NameComparator implements Comparator<Group<TicketElement>> {

            @Override
            public int compare(Group<TicketElement> _p1, Group<TicketElement> _p2) {
                return TicketElement.BYNAME.compare(_p1.getElement(), _p2.getElement());
            }

        }

        //  Compares TicketElements based on the non case-sensitive skus of groups.
        public static class SkuComparator implements Comparator<Group<TicketElement>> {

            @Override
            public int compare(Group<TicketElement> _p1, Group<TicketElement> _p2) {
                return TicketElement.BYSKU.compare(_p1.getElement(), _p2.getElement());
            }

        }

        // Compares TicketElements based on prices of the groups.
        public static class PriceComparator implements Comparator<Group<TicketElement>> {

            @Override
            public int compare(Group<TicketElement> _p1, Group<TicketElement> _p2) {
                return Double.compare(_p1.getPrice(), _p2.getPrice());
            }

        }
    }

}
