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
		TestUserCount testUserIds = new TestUserCount(usernameCount);
		testUserIds.next();
		String username = "TestUser" + testUserIds.getIdCount();
		User testUser = new User(0, username, "testPassword!", "Test User1");
		userRepo.save(testUser);
		testUserIds.setLastId(testUserIds.getIdCount());
		setCurrentId(testUser.getUserId());
		System.out.println("TestUser: " + testUser + "\n" + " ID: " + testUserIds.getIdCount() + "\n'");
	}



	@Test
	void get_user_by_id(){
		User user = userRepo.findUserByUserId(getCurrentId());
		System.out.println(user);
	}

}
