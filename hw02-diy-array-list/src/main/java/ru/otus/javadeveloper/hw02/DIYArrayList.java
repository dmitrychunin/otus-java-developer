package ru.otus.javadeveloper.hw02;

import java.util.*;


public class DIYArrayList<T> implements List<T> {
    private static final int INITIAL_CAPACITY = 16;
    private static final int SIZE_MULTIPLIER = 2;

    private Integer currentCapacity;
    private Integer size;
    private Object[] container;
    private ListIterator<T> listIterator = listIterator();

    DIYArrayList() {
        currentCapacity = INITIAL_CAPACITY;
        container = new Object[INITIAL_CAPACITY];
    }

    DIYArrayList(Object... objects) {
        currentCapacity = size = objects.length;
        container = Arrays.copyOf(objects, size);
        listIterator = listIterator(size - 1);
    }

    DIYArrayList(Integer initialCapacity) {
        currentCapacity = size = initialCapacity; //why size must be equals to initialCapacity?
        container = new Object[initialCapacity];
    }

    @Override
    public boolean add(T newElement) {
        listIterator.next();
        listIterator.add(newElement);
        return true;
    }

    @Override
    public String toString() {
        return Arrays.toString(container);
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(container, container.length);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public ListIterator<T> listIterator() {
        return new DIYListIterator<>(0);
    }

    @Override
    public ListIterator<T> listIterator(int nextElementIndex) {
        return new DIYListIterator<>(nextElementIndex);
    }

    private class DIYListIterator<T> implements ListIterator<T> {
        private boolean isCanBeModified;
        private int lastReturnedElementIndex;

        public DIYListIterator(int lastReturnedElementIndex) {
            this.lastReturnedElementIndex = lastReturnedElementIndex - 1;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            isCanBeModified = true;
            return (T) container[++lastReturnedElementIndex];
        }

        @Override
        public T previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            isCanBeModified = true;
            return (T) container[--lastReturnedElementIndex];
        }

        @Override
        public void remove() {
            if (!isCanBeModified) {
                throw new IllegalStateException();
            }
            System.arraycopy(container, lastReturnedElementIndex,
                    container, lastReturnedElementIndex - 1,
                    size - lastReturnedElementIndex);
            size--;
            isCanBeModified = false;
        }

        @Override
        public void add(T t) {
            if (!isCanBeModified) {
                throw new IllegalStateException();
            }
            if (!isContainerHaveFreeSpace()) {
                enlargeContainer();
            }
            if (lastReturnedElementIndex < size) {
                System.arraycopy(container, lastReturnedElementIndex,
                        container, lastReturnedElementIndex + 1,
                        size - lastReturnedElementIndex);
            }
            container[lastReturnedElementIndex] = t;
            size++;
            isCanBeModified = false;
        }

        private boolean isContainerHaveFreeSpace() {
            return size < currentCapacity;
        }

        private void enlargeContainer() {
            currentCapacity *= SIZE_MULTIPLIER;
            container = Arrays.copyOf(container, currentCapacity);
            listIterator = listIterator(size-1);
        }

        @Override
        public void set(T t) {
            if (!isCanBeModified) {
                throw new IllegalStateException();
            }
            container[lastReturnedElementIndex] = t;
        }

        @Override
        public boolean hasNext() {
            return lastReturnedElementIndex < size;
        }

        @Override
        public boolean hasPrevious() {
            return lastReturnedElementIndex > 0;
        }

        @Override
        public int nextIndex() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int previousIndex() {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public T get(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T set(int index, T element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, T element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }
}
