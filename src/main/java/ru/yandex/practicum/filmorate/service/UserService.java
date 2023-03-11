package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStorage userStorage;

    public List<User> findAll() {
        return userStorage.findAll();
    }

    public User create(User user) {
        return userStorage.create(user);
    }

    public void update(User user) {
        if (getById(user.getId()) == null) {
            throw new ObjectNotFoundException("Update user: Incorrect ID");
        }
        userStorage.update(user);
    }

    public User getById(int id) {
        User user = userStorage.getById(id);
        if (user == null) {
            throw new ObjectNotFoundException("Get by ID: Incorrect ID");
        }
        return user;
    }

    public User deleteById(int id) {
        return userStorage.deleteById(id);
    }

    public Boolean addFriendship(Integer id, Integer friendId) {
        if (getById(id) == null) {
            throw new ObjectNotFoundException("Add friendship: ID not found");
        }
        User user = userStorage.getById(id);
        if (getById(friendId) == null) {
            throw new ObjectNotFoundException("Add friendship: ID not found");
        }
        User newFriend = userStorage.getById(friendId);
        if (id.equals(friendId)) {
            throw new ValidateException("Add friendship: IDs of both friends are equal");
        }
        if (user.getFriends() == null){
            user.setFriends(new HashSet<>());
        }

        user.getFriends().add(friendId);
        newFriend.getFriends().add(id);
        return true;
    }

    public Boolean removeFriendship(Integer id, Integer friendId) throws ObjectNotFoundException {
        if (getById(id) == null) {
            throw new ObjectNotFoundException("Remove friendship: ID not found");
        }
        User user = userStorage.getById(id);
        if (getById(friendId) == null) {
            throw new ObjectNotFoundException("Remove friendship: ID not found");
        }
        User removedFriend = userStorage.getById(friendId);
        if (!user.getFriends().contains(friendId) || !removedFriend.getFriends().contains(id)) {
            throw new ObjectNotFoundException("Users are not friends");
        }
        user.getFriends().remove(friendId);
        removedFriend.getFriends().remove(id);
        return true;
    }

    public List<User> getFriendsListById(int id) {
        User user = userStorage.getById(id);
        List<User> list = new ArrayList<>();
        for (Integer friendId: user.getFriends()) {
            list.add(userStorage.getById(friendId));
        }
        return list;
    }

    public List<User> getCommonFriendsList(int id, int otherId) {
        if (userStorage.getById(id)==null) {
            throw new ObjectNotFoundException("Common friends: User not found");
        }
        User user = userStorage.getById(id);
        if (userStorage.getById(otherId)==null) {
            throw new ObjectNotFoundException("Common friends: Second user not found");
        }
        User otherUser = userStorage.getById(otherId);
        List<User> list = new ArrayList<>();
        if (user.getFriends() == null || otherUser.getFriends() == null){
            return list;
        }
        for(Integer userId: user.getFriends()){
            if (otherUser.getFriends().contains(userId)) {
                list.add(userStorage.getById(userId));
            }
        }
        return list;
    }
}
