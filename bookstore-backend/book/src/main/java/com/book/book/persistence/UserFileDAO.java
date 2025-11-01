package com.book.book.persistence;

import com.book.book.model.Book;
import com.book.book.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

@Component
public class UserFileDAO implements UserDAO {
    public File file;
    public ObjectMapper objectMapper;
    static int nextId;
    private TreeMap<Integer, User> userMap;

    public UserFileDAO(@Value("${users.file}") String filename, ObjectMapper objectMapper) {
        this.file = new File(filename);
        this.objectMapper = objectMapper;
        loadFromFile();
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

    /**
     ** {@inheritDoc}
     */
    @Override
    public User createUser(User user) throws IOException {
        synchronized (userMap) {
            user.setId(nextId());
            userMap.put(user.getId(), user);
            writeToFile(); // may throw an IOException
            return user;
        }
    }

    /**
     ** {@inheritDoc}
     */
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

    /**
     ** {@inheritDoc}
     */
    @Override
    public User deleteUser(int id) throws IOException {
        synchronized(userMap) {
            if (userMap.containsKey(id)) {
                User ret = userMap.get(id);
                userMap.remove(id);
                writeToFile();
                return ret;
            } else{
                return null;
            }
        }
    }

    @Override
    public Book addToCart(int id, int bookID) {
        return null;
    }

    @Override
    public Book removeFromCart(int id, int bookID) {
        return null;
    }

    @Override
    public Book addToBookshelf(int id, int bookID) {
        return null;
    }

    @Override
    public Book removeFromBookshelf(int id, int bookID) {
        return null;
    }
}
