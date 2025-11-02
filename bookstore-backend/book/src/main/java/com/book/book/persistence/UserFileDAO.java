package com.book.book.persistence;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.book.book.model.Book;
import com.book.book.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class UserFileDAO implements UserDAO {
    public File file;
    public ObjectMapper objectMapper;
    static int nextId;
    private TreeMap<Integer, User> userMap;
    private CatalogDAO catalogDAO;

    public UserFileDAO(@Value("${users.file}") String filename, ObjectMapper
            objectMapper, CatalogDAO catalogDAO) {
        this.file = new File(filename);
        this.objectMapper = objectMapper;
        loadFromFile();
        this.catalogDAO = catalogDAO;
    }

    private synchronized int nextId() {
        return nextId++;
    }

    private void loadFromFile() {
        this.userMap = new TreeMap<>();
        try {
            User[] usersArray = objectMapper.readValue(file, User[].class);

            for (User user : usersArray) {
                if (user.getId() > nextId) {
                    nextId = user.getId();
                }
                this.userMap.put(user.getId(), user);
            }
            nextId++;
        } catch (IOException e) {
            System.out.println("No users to load");
        }
    }

    private void writeToFile() throws IOException {
        objectMapper.writeValue(file, getUsersArray());
    }


    private User[] getUsersArray() throws IOException {
        ArrayList<User> tempList = new ArrayList<>(userMap.values());

        User[] usersArray = new User[tempList.size()];
        tempList.toArray(usersArray);
        return usersArray;
    }

    private User[] getUsersArray(String containsText) throws IOException {
        ArrayList<User> tempList = new ArrayList<>(userMap.values());

        for (User user : tempList) {
            if (!user.getName().contains(containsText)) {
                tempList.remove(user);
            }
        }
        User[] usersArray = new User[tempList.size()];
        tempList.toArray(usersArray);
        return usersArray;
    }

    @Override
    public User[] getUsers() throws IOException {
        return getUsersArray();
    }

    @Override
    public User[] searchUser(String name) throws IOException {
        return getUsersArray(name);
    }

    public User getUser(int id) throws IOException {
        synchronized (userMap) {
            if (userMap.containsKey(id)) {
                return userMap.get(id);
            }
            else {
                return null;
            }
        }
    }

    @Override
    public User createUser(User user) throws IOException {
        synchronized (userMap) {
            user.setId(nextId());
            userMap.put(user.getId(), user);
            writeToFile(); // may throw an IOException
            return user;
        }
    }

    @Override
    public User updateUser(int id, User user) throws IOException {
        synchronized (userMap) {
            if (!userMap.containsKey(id)) {
                return null;
            }
            user.setId(id);
            userMap.put(id, user);
            writeToFile(); // may throw an IOException
            return user;
        }
    }

    @Override
    public User deleteUser(int id) throws IOException {
        synchronized(userMap) {
            if (userMap.containsKey(id)) {
                User ret = userMap.get(id);
                userMap.remove(id);
                writeToFile();
                return ret;
            } else {
                return null;
            }
        }
    }

    @Override
    public Book addToCart(int id, String ISBN) throws IOException {
        Book toAdd = catalogDAO.getBook(ISBN);
        User user = getUser(id);
        if (toAdd == null || user == null) {
            return null;
        }
        Book added = user.addToShoppingCart(toAdd);
        writeToFile();
        return added;
    }

    @Override
    public Book removeFromCart(int id, String ISBN) throws IOException {
        Book toRemove = catalogDAO.getBook(ISBN);
        User user = getUser(id);
        if (toRemove == null || user == null) {
            return null;
        }
        Book removed = user.removeFromShoppingCart(toRemove);
        writeToFile();
        return removed;
    }

    @Override
    public Book addToBookshelf(int id, String ISBN) throws IOException {
        Book toAdd = catalogDAO.getBook(ISBN);
        User user = getUser(id);
        if (toAdd == null || user == null) {
            return null;
        }
        Book added = user.addToBookshelf(toAdd);
        writeToFile();
        return added;
    }

    @Override
    public Book removeFromBookshelf(int id, String ISBN) throws IOException {
        Book toRemove = catalogDAO.getBook(ISBN);
        User user = getUser(id);
        if (toRemove == null || user == null) {
            return null;
        }
        Book removed = user.removeFromBookshelf(toRemove);
        writeToFile();
        return removed;
    }

    @Override
    public Book[] getBookshelf(int id) throws IOException {
        if (!userMap.containsKey(id)) {
            return null;
        }
        List<Book> temp = userMap.get(id).getBookshelf();
        Book[] bookshelf = new Book[temp.size()];
        temp.toArray(bookshelf);
        return bookshelf;
    }

    @Override
    public Book[] getCart(int id) throws IOException {
        if (!userMap.containsKey(id)) {
            return null;
        }  
        List<Book> temp = userMap.get(id).getShoppingCart();
        Book[] cart = new Book[temp.size()];
        temp.toArray(cart);
        return cart;
    }
}
