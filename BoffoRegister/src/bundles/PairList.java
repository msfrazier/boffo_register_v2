package bundles;

import java.util.ArrayList;
import java.util.List;

// Should be contained in either utility or transaction.
// Basically a hashmap with different functionality.
public class PairList<T> {

    private List<Pair<T>> listOfPairs;

    PairList() {
        this.listOfPairs = new ArrayList();
    }

    PairList(List<Pair<T>> listOfPairs) {
        this.listOfPairs = listOfPairs;
    }

    // <editor-fold desc="Add / Remove Methods">
    public int add(T element) {
        return add(element, 1);
    }
    
    public int add(T element, int number) {
        return add(new Pair(element, number));
    }

    public int add(Pair<T> pair) {
        if (this.contains(pair.getElement())) {
            listOfPairs.get(this.indexOf(pair.getElement())).add(pair.getNumber());
        } else {
            listOfPairs.add(pair.clone());
        }
        return numberOf(pair.getElement());
    }

    public void add(List<Pair<T>> list) {
        for (Pair<T> pair : list) {
            add(pair);
        }
    }

    public void add(PairList<T> pairList){
        add(pairList.toList());
    }
    
    public int remove(T element) {
        if (this.contains(element)) {
            listOfPairs.get(indexOf(element)).decrement();
        }else{
            return 0;
        }
        return listOfPairs.get(indexOf(element)).getNumber();
    }

    public int remove(T element, int number){
        return remove(new Pair(element, number));
    }
    
    public int remove(Pair<T> pair) {
        if (!contains(pair.getElement())) {
            return 0;
        }
        int newValue = listOfPairs.get(indexOf(pair.getElement())).subtract(pair.getNumber());
        if (newValue == 0) {
            listOfPairs.remove(indexOf(pair.getElement()));
        }
        return newValue;
    }

    public void remove(List<Pair<T>> list) {
        for (Pair<T> pair : list) {
            remove(pair);
        }
    }

    // </editor-fold>
    
    // <editor-fold desc="Contains Operations">
    public int numberOf(T element) {
        if (this.contains(element)) {
            return listOfPairs.get(indexOf(element)).getNumber();
        }
        return 0;
    }

    public int indexOf(T element) {
        int retIndex = 0;
        for (Pair<T> pair : listOfPairs) {
            if (pair.getElement().equals(element)) {
                return retIndex;
            }
            retIndex++;
        }
        return -1;
    }

    public boolean contains(T element) {
        // iterate through all values
        return indexOf(element) != -1;
    }

    public boolean contains(T element, int number){
        return contains(new Pair(element, number));
    }
    
    public boolean contains(Pair<T> pair) {
        // if pair number <= contained number
        if (!contains(pair.getElement())) {
            return false;
        }
        if (pair.getNumber() > listOfPairs.get(indexOf(pair.getElement())).getNumber()) {
            return false;
        }
        return true;

    }

    public boolean contains(List<Pair<T>> list) {
        boolean running_truthValue = true;
        // use a forall loop
        for (Pair<T> pair : list) {
            running_truthValue &= contains(pair);
        }
        return running_truthValue;
    }
    // </editor-fold>
    
    public List<Pair<T>> toList() {
        List<Pair<T>> copyOfList = new ArrayList();
        for (Pair<T> pair : listOfPairs) {
            copyOfList.add(pair.clone());
        }
        return copyOfList;
    }

    public PairList<T> clone() {
        List<Pair<T>> tempList = new ArrayList();
        for (Pair<T> pair : listOfPairs) {
            tempList.add(pair.clone());
        }
        return new PairList(tempList);
    }

    @Override
    public String toString() {
        String retString = "{ ";
        int index = 0;
        for (Pair<T> pair : listOfPairs) {
            retString +=  pair.toString();
            if(index!= listOfPairs.size()){
                retString += ",";
            }
            index++;
        }
        return retString + "}";
    }

    
}
