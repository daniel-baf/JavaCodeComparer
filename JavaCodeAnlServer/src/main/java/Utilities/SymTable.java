package Utilities;

import java.util.ArrayList;

public class SymTable<T> {
    ArrayList<T> table;

    public SymTable() {
        table = new ArrayList<T>();
    }

    public void add(T t) {
        table.add(t);
    }

    public T get(int i) {
        return table.get(i);
    }

    public int size() {
        return table.size();
    }

    public void update(int i, T t) {
        table.set(i, t);
    }

    public void remove(int i) {
        table.remove(i);
    }

    public void remove(T t) {
        table.remove(t);
    }

    public void clear() {
        table.clear();
    }

    public boolean contains(T t) {
        return table.contains(t);
    }

    public int indexOf(T t) {
        return table.indexOf(t);
    }

    public void print() {
        for (T t : table) {
            System.out.println(t.toString());
        }
    }
}
