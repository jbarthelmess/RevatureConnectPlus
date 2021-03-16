package dev.group1.RevatureConnectPlus;

import dev.group1.entities.User;
import dev.group1.repos.UserRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RevatureConnectPlusApplicationTests {

	private static int currentId;

	@Autowired
	UserRepo userRepo;

	public static void setCurrentId(int id) {
		currentId = id;
	}

	public static int getCurrentId(){
		return currentId;
	}

	@Test
	void create_user() {
		int usernameCount;
		// to adjust for the username unique constraint;
		if(userRepo.count()>0){
			usernameCount = (int) userRepo.count();
		}else{
			usernameCount = 0;
		}
		// starts count at 0
		TestUserCount testUserIds = new TestUserCount(usernameCount);
		testUserIds.next(); // iterate idCount and set lastId to previous idCount

		// sets username and display name strings based on count...
		String userName = "TestUser" + testUserIds.getIdCount();
		String displayName = "Test CreateUser" + testUserIds.getIdCount();

		// create user object then pass in to UserRepo ... magic!
		User testUser = new User(0, userName, "testPassword!", displayName);
		userRepo.save(testUser);

		// to save username count for duplicate testing and set id of recently created user...
		testUserIds.setLastId(testUserIds.getIdCount());
		setCurrentId(testUser.getUserId());

		// logging...
		System.out.println("TestUser: " + testUser + "\n" + "ID from counter: " + testUserIds.getIdCount());
	}

	@Test
	void get_user_by_userId(){
		User user = userRepo.findUserByUserId(getCurrentId());
		System.out.println(user);
	}

	@Test
	void delete_user_by_userId(){
		int id = getCurrentId();
		userRepo.deleteById(id);
		System.out.println("Deleted user " + id);
	}

}
