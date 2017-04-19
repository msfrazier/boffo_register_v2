package bundles;

/**
 * An auto-grouping data structure that arranges TicketElements.
 *
 * @author Michael Resnik
 * @author Travis Cox
 * @param <T> A class type that must implement the TicketElement interface.
 * Delineates what type of elements are stored in the Groups.
 * @lastEdited 04/18/2017
 */
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class GroupList<T extends TicketElement> implements Iterable {

    /**
     * Compares GroupLists based on the sum of all contained Group prices.
     */
    public static final Comparators.PriceComparator BYPRICE = new Comparators.PriceComparator();

    private List<Group<T>> listOfGroups;
    private Comparator<T> comparator;


    /**
     * A Constructor that creates an empty GroupList that will arrange values
     * based on the passed TicketElement Comparator.
     *
     * @param _comparator The Comparator that will help arrange the
     * TicketElements in their proper groups when added.
     */
    public GroupList(Comparator<T> _comparator) {
        this(new ArrayList(), _comparator);
    }


    /**
     * A Constructor that creates a GroupList that is populated by the passed
     * List of Groups, that will arrange values based on the passed
     * TicketElement Comparator.
     *
     * @param _listOfGroups A List of Groups that will populate the GroupList.
     * @param _comparator The Comparator that will help arrange the
     * TicketElements in their proper groups when added.
     */
    public GroupList(List<Group<T>> _listOfGroups, Comparator<T> _comparator) {
        this.listOfGroups = new ArrayList();
        this.comparator = _comparator;
        for (Group group : _listOfGroups) {
            add(group.getContents());
        }
    }

    // <editor-fold desc="Add methods.">

    /**
     * Adds all passed elements to their corresponding Group.
     *
     * @param _elementList Passed TicketElement objects to be added.
     * @return The number of elements in the GroupList.
     */
    public int add(List<T> _elementList) {
        for (T obj : _elementList) {
            add(obj);
        }
        int num = 0;
        for (Group group : this.listOfGroups) {
            num += group.size();
        }
        return num;
    }


    /**
     * Adds the passed element to its corresponding Group.
     *
     * @param _element Passed TicketElement to be added.
     * @return The number of elements in the GroupList.
     */
    public int add(TicketElement _element) {
        return add(_element, 1);
    }


    /**
     * Adds the passed element to its corresponding Group a number of times.
     *
     * @param _element The TicketElement to be added.
     * @param _number The number of the passed TicketElement to be added.
     * @return The number of elements in the GroupList.
     */
    public int add(TicketElement _element, int _number) {
        Group<T> group = new Group(_element, _number);
        return add(group);
    }


    /**
     * Adds either the Group itself or its components to its appropriate spot in
     * the list.
     *
     * @param _group The Group to be added to the list, or the components of the
     * Group to be added.
     * @return The number of elements in the GroupList.
     */
    public int add(Group<T> _group) {
        if (_group.size() == 0) {
            return 0;
        }
        if (this.contains(_group.getElement())) {
            this.listOfGroups.get(this.indexOf(_group.getElement())).add(_group.getContents());
        } else {
            this.listOfGroups.add(_group.clone());
        }
        int num = 0;
        for (Group group : this.listOfGroups) {
            num += group.size();
        }
        return num;
    }


    /**
     * Adds all contained Groups to the list or all components of all contained
     * Groups to their appropriate spots.
     *
     * @param _groupList GroupList of TicketElement Groups to be added.
     * @return The number of elements in the GroupList.
     */
    public int add(GroupList<T> _groupList) {
        for (Group<T> group : _groupList.toList()) {
            add(group);
        }
        int num = 0;
        for (Group group : this.listOfGroups) {
            num += group.size();
        }
        return num;
    }

    // </editor-fold>

    /**
     * Clears the contents of the GroupList and the containing List of Groups.
     */
    public void clear() {
        this.listOfGroups.clear();
    }


    /**
     * Clones the current object so that GroupLists can be immutable in
     * different contexts.
     *
     * @return A copy of the GroupList object with a copy of a List of Groups.
     */
    @Override
    public GroupList<T> clone() {
        List<Group<T>> tempList = new ArrayList();
        for (Group<T> group : this.listOfGroups) {
            tempList.add(group.clone());
        }
        return new GroupList(tempList, this.comparator);
    }


    /**
     * Clones the current object so that GroupLists can be immutable in
     * different contexts.
     *
     * @param _comparator The comparator to arrange the copy of the GroupList
     * object.
     * @return A copy of the GroupList object with a copy of a List of Groups.
     */
    private GroupList<T> clone(Comparator<T> _comparator) {
        List<Group<T>> tempList = new ArrayList();
        for (Group<T> group : this.listOfGroups) {
            tempList.add(group.clone());
        }
        return new GroupList(tempList, _comparator);
    }


    /**
     * Returns the Element or Arranging Comparator of the GroupList.
     *
     * @return The GroupLists Comparator.
     */
    public Comparator<T> comparator() {
        return this.comparator;
    }


    // <editor-fold desc="Contains methods.">
    /**
     * Returns a truth value based on whether the passed element is in the
     * GroupList.
     *
     * @param _element The item to be checked against all GroupList elements.
     * @return Whether the passed element is in the GroupList.
     */
    public boolean contains(T _element) {
        // iterate through all values
        return indexOf(_element) != -1;
    }


    /**
     * Returns a truth value based on whether the passed group is in the
     * GroupList, or if all the contents of that group are in the GroupList.
     *
     * @param _group The group to be checked against all GroupList groups.
     * @return Whether the passed Group is in the GroupList.
     */
    public boolean contains(Group<T> _group) {
        if (!contains(_group.getElement())) {
            return false;
        }
        if (_group.size() > this.listOfGroups.get(indexOf(_group.getElement())).size()) {
            return false;
        }
        return true;
    }


    /**
     * Returns a truth value based on whether the passed List of Groups is in
     * the Group List.
     *
     * @param _list The list of groups to be checked against all GroupList
     * groups.
     * @return Whether the passed groups are in the GroupList.
     */
    public boolean contains(List<Group<T>> _list) {
        if (_list.size() > listOfGroups.size()) {
            return false;
        }
        boolean running_truthValue = true;
        // use a forall loop
        for (Group<T> group : _list) {
            running_truthValue &= contains(group);
        }
        return running_truthValue;
    }


    /**
     * Returns a truth value based on whether the passed GroupList is in the
     * Group List by converting to a List of Group objects and passing into
     * another contains() method.
     *
     * @param _groupList The GroupList that will be converted into a List of
     * Group objects to be checked against all GroupList groups.
     * @return Whether the passed GroupList is in the GroupList.
     */
    public boolean contains(GroupList<T> _groupList) {
        return contains(_groupList.toList());
    }
    // </editor-fold>


    //TODO
    @Override
    public boolean equals(Object _object) {
        return false;
    }


    /**
     * Returns the Group which is held at the passed index.
     *
     * @param _index The location where the Group should be held.
     * @return The Group object held at the passed index. Null if the index
     * wasn't within range.
     */
    public Group<T> get(int _index) {
        return this.listOfGroups.get(_index);
    }


    /**
     * Returns the total price of the GroupList by summing the total price of
     * all Groups.
     *
     * @return The total price of the GroupList.
     */
    public double getTotal() {
        double sum = 0.0;
        for (Group<T> group : this.listOfGroups) {
            sum += group.getPrice();
        }
        return sum;
    }


    @Override
    public Iterator<Group<T>> iterator() {
        return this.toList().iterator();
    }


    /**
     * Returns the index of where the passed element is or where it should go.
     *
     * @param _element The element to check against all Groups in the GroupList.
     * @return The index of where the element is in the GroupList.
     */
    public int indexOf(T _element) {
        int retIndex = 0;
        for (Group<T> group : this.listOfGroups) {
            if (this.comparator.compare(_element, group.getElement()) == 0) {
                return retIndex;
            }
            retIndex++;
        }
        return -1;
    }


    /**
     * Returns true if the GroupList has no Groups (or Elements).
     *
     * @return True or False based on the size() of the List of Groups.
     */
    public boolean isEmpty() {
        return size() == 0;
    }


    /**
     * Returns a ListIterator for a clone of the List of Groups.
     *
     * @return A ListIterator that describes the set of Groups.
     */
    public ListIterator<Group<T>> listIterator() {
        return this.toList().listIterator();
    }


    /**
     * Counts how many of the passed element is in the GroupList based on the
     * GroupList's comparator.
     *
     * @param _element The element that is going to be found in the GroupList
     * some number of times.
     * @return The number of elements that are similar based on the GroupList's
     * comparator.
     */
    public int numberOf(T _element) {
        if (this.contains(_element)) {
            return this.listOfGroups.get(indexOf(_element)).size();
        }
        return 0;
    }


    // <editor-fold desc="Remove Methods">
    /**
     * Removes the element from the GroupList after finding the corresponding
     * group it's arranged in.
     *
     * @param _element The element to be removed from the GroupList.
     * @return The current amount of the element remaining in the GroupList.
     */
    public int remove(T _element) {
        int remaining = 0;
        if (this.contains(_element)) {
            int index = indexOf(_element);
            Group<T> groupAtIndex = this.listOfGroups.get(index);
            groupAtIndex.decrement();
            if (groupAtIndex.size() == 0) {
                this.listOfGroups.remove(index);
            }
            remaining = groupAtIndex.size();
        } else {
            return 0;
        }
        return remaining;
    }


    /**
     * Removes the element from the GroupList some number of times from the
     * GroupList.
     *
     * @param _element The element to be removed from the GroupList.
     * @param _number The current number of the passed element remaining in the
     * GroupList.
     * @return
     */
    public int remove(T _element, int _number) {
        if (!contains(_element)) {
            return 0;
        }
        int retval = Math.max(0, this.listOfGroups.get(indexOf(_element)).size() - _number);
        for (int i = 0; i < _number; i++) {
            remove(_element);
        }
        return retval;
    }


    /**
     * Removes some passed group from the GroupList by subtracting the size of
     * the passed group from where it is arranged.
     *
     * @param _group The Group that is to be removed from the GroupList, can
     * also be thought of as an element being removed some number of times.
     * @return The List of subtracted elements.
     */
    public List<T> remove(Group<T> _group) {
        int index = indexOf(_group.getElement());
        if (index == -1) {
            return new ArrayList();
        }
        Group<T> inGroupList = this.listOfGroups.get(index);
        List<T> newList = inGroupList.subtract(_group.size());
        if (inGroupList.size() <= 0) {
            this.listOfGroups.remove(index);
        }
        return newList;
    }


    /**
     * Removes some List of Groups from the GroupList.
     *
     * @see remove(Group<T> _group)
     * @param _list The List of Groups to be removed.
     */
    public void remove(List<Group<T>> _list) {
        for (Group<T> group : _list) {
            remove(group);
        }
    }

    // </editor-fold>

    /**
     * Returns the number of Groups contained in the GroupList
     *
     * @return The size of the GroupList.
     */
    public int size() {
        return this.listOfGroups.size();
    }


    /**
     * Sorts the Groups of the GroupList based on some Group Comparator.
     *
     * @param _groupComparator The Comparator to sort the GroupList by.
     */
    public void sort(Comparator<Group<T>> _groupComparator) {
        this.listOfGroups.sort(_groupComparator);
    }


    /**
     * Returns an absolute list of the contents of this GroupList's Groups.
     *
     * @return An ArrayList populated by all added non-group elements.
     */
    public List<T> toAbsoluteList() {
        List<T> retList = new ArrayList();
        for (Group<T> group : this.listOfGroups) {
            for (T obj : group.getContents()) {
                retList.add(obj);
            }
        }
        return retList;
    }


    /**
     * Returns a copy of the List of Groups.
     *
     * @return An ArrayList copy of the GroupList's List of Groups.
     */
    public List<Group<T>> toList() {
        List<Group<T>> copyOfList = new ArrayList();
        for (Group<T> group : this.listOfGroups) {
            copyOfList.add(group.clone());
        }
        return copyOfList;
    }


    @Override
    public String toString() {
        String retString = "{";
        retString += this.listOfGroups.toString();
        return retString + "}";
    }

    // Other classes shouldn't be allowed to create GroupList comparators.
    private static class Comparators {

        public static class PriceComparator implements Comparator<GroupList<TicketElement>> {

            @Override
            public int compare(GroupList<TicketElement> _pl1, GroupList<TicketElement> _pl2) {
                return Double.compare(_pl1.getTotal(), _pl2.getTotal());
            }

        }
    }

}
