package Lab6.src;

import java.util.*;

/**
 * Головний клас.
 * Реалізація List на базі двозв'язного списку.
 */
public class Main {
    public static void main(String[] args) {
        try {
            // 1. Порожній конструктор
            MusicList myList = new MusicList();
            myList.add(new RockMusic("Numb", 187));
            myList.add(new PopMusic("Flowers", 200));

            System.out.println("--- List created (Empty constructor) ---");
            printList(myList);

            // 2. Конструктор з одним елементом
            MusicList singleItemList = new MusicList(new ClassicalMusic("Symphony 5", 420));
            System.out.println("\n--- List created (1 item constructor) ---");
            printList(singleItemList);

            // 3. Конструктор з іншої колекції
            List<MusicComposition> standardList = new ArrayList<>();
            standardList.add(new RockMusic("It's my life", 226));
            standardList.add(new PopMusic("Bad Guy", 194));
            
            MusicList collectionList = new MusicList(standardList);
            System.out.println("\n--- List created (Collection constructor) ---");
            printList(collectionList);

            System.out.println("\n--- Testing operations ---");
            
            collectionList.add(new RockMusic("New Rock Song", 300));
            System.out.println("Added 'New Rock Song'. Size: " + collectionList.size());

            MusicComposition toRemove = collectionList.get(0); // It's my life
            collectionList.remove(toRemove);
            System.out.println("Removed '" + toRemove + "'. Size: " + collectionList.size());

            System.out.println("Element at index 1: " + collectionList.get(1));

            boolean hasBadGuy = false;
            for(MusicComposition m : collectionList) {
                if(m.toString().contains("Bad Guy")) hasBadGuy = true;
            }
            System.out.println("Contains 'Bad Guy'? " + hasBadGuy);

            System.out.println("\n--- Final List Content ---");
            printList(collectionList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printList(MusicList list) {
        for (MusicComposition track : list) {
            System.out.println(track);
        }
    }
}

// --- Класи з Лаби 5  ---

abstract class MusicComposition {
    private String title;
    private int duration;
    private String style;

    public MusicComposition(String title, int duration, String style) {
        this.title = title;
        this.duration = duration;
        this.style = style;
    }
    @Override
    public String toString() {
        return String.format("%-15s (%s, %d sec)", title, style, duration);
    }
}

class RockMusic extends MusicComposition {
    public RockMusic(String title, int duration) { super(title, duration, "Rock"); }
}
class PopMusic extends MusicComposition {
    public PopMusic(String title, int duration) { super(title, duration, "Pop"); }
}
class ClassicalMusic extends MusicComposition {
    public ClassicalMusic(String title, int duration) { super(title, duration, "Classical"); }
}

// --- Власна Колекція (Лаба 6) ---

/**
 * Типізована колекція. Реалізує List.
 * Структура: Двозв'язний список.
 */
class MusicList implements List<MusicComposition> {
    
    // Вузол списку
    private static class Node {
        MusicComposition data;
        Node next;
        Node prev;

        Node(Node prev, MusicComposition data, Node next) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node first;
    private Node last;
    private int size = 0;

    // 1. Порожній конструктор
    public MusicList() {
    }

    // 2. Конструктор з 1 елементом
    public MusicList(MusicComposition element) {
        add(element);
    }

    // 3. Конструктор з колекції
    public MusicList(Collection<? extends MusicComposition> c) {
        this();
        addAll(c);
    }

    // Реалізація методів List

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean add(MusicComposition e) {
        linkLast(e);
        return true;
    }

    @Override
    public void add(int index, MusicComposition element) {
        if (index == size) linkLast(element);
        else linkBefore(element, node(index));
    }

    @Override
    public MusicComposition get(int index) {
        return node(index).data;
    }

    @Override
    public MusicComposition set(int index, MusicComposition element) {
        Node x = node(index);
        MusicComposition oldVal = x.data;
        x.data = element;
        return oldVal;
    }

    @Override
    public MusicComposition remove(int index) {
        return unlink(node(index));
    }

    @Override
    public boolean remove(Object o) {
        if (o == null) {
            for (Node x = first; x != null; x = x.next) {
                if (x.data == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            for (Node x = first; x != null; x = x.next) {
                if (o.equals(x.data)) {
                    unlink(x);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void clear() {
        for (Node x = first; x != null; ) {
            Node next = x.next;
            x.data = null;
            x.next = null;
            x.prev = null;
            x = next;
        }
        first = last = null;
        size = 0;
    }

    // Допоміжні методи для лінкування

    private void linkLast(MusicComposition e) {
        final Node l = last;
        final Node newNode = new Node(l, e, null);
        last = newNode;
        if (l == null) first = newNode;
        else l.next = newNode;
        size++;
    }

    private void linkBefore(MusicComposition e, Node succ) {
        final Node pred = succ.prev;
        final Node newNode = new Node(pred, e, succ);
        succ.prev = newNode;
        if (pred == null) first = newNode;
        else pred.next = newNode;
        size++;
    }

    private MusicComposition unlink(Node x) {
        final MusicComposition element = x.data;
        final Node next = x.next;
        final Node prev = x.prev;

        if (prev == null) first = next;
        else {
            prev.next = next;
            x.prev = null;
        }

        if (next == null) last = prev;
        else {
            next.prev = prev;
            x.next = null;
        }

        x.data = null;
        size--;
        return element;
    }

    Node node(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        if (index < (size >> 1)) {
            Node x = first;
            for (int i = 0; i < index; i++) x = x.next;
            return x;
        } else {
            Node x = last;
            for (int i = size - 1; i > index; i--) x = x.prev;
            return x;
        }
    }

    @Override
    public Iterator<MusicComposition> iterator() {
        return new Iterator<MusicComposition>() {
            private Node current = first;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public MusicComposition next() {
                if (current == null) throw new NoSuchElementException();
                MusicComposition data = current.data;
                current = current.next;
                return data;
            }
        };
    }

    // Boilerplate 
    @Override public boolean contains(Object o) { return indexOf(o) != -1; }
    @Override public Object[] toArray() { 
        Object[] result = new Object[size];
        int i = 0;
        for (Node x = first; x != null; x = x.next) result[i++] = x.data;
        return result;
    }
    @Override public <T> T[] toArray(T[] a) { throw new UnsupportedOperationException(); }
    @Override public boolean containsAll(Collection<?> c) { throw new UnsupportedOperationException(); }
    @Override public boolean addAll(Collection<? extends MusicComposition> c) {
        for(MusicComposition e : c) add(e);
        return true;
    }
    @Override public boolean addAll(int index, Collection<? extends MusicComposition> c) { throw new UnsupportedOperationException(); }
    @Override public boolean removeAll(Collection<?> c) { throw new UnsupportedOperationException(); }
    @Override public boolean retainAll(Collection<?> c) { throw new UnsupportedOperationException(); }
    @Override public int indexOf(Object o) { 
        int index = 0;
        for (Node x = first; x != null; x = x.next) {
            if (o == null ? x.data == null : o.equals(x.data)) return index;
            index++;
        }
        return -1;
    }
    @Override public int lastIndexOf(Object o) { throw new UnsupportedOperationException(); }
    @Override public ListIterator<MusicComposition> listIterator() { throw new UnsupportedOperationException(); }
    @Override public ListIterator<MusicComposition> listIterator(int index) { throw new UnsupportedOperationException(); }
    @Override public List<MusicComposition> subList(int fromIndex, int toIndex) { throw new UnsupportedOperationException(); }
}