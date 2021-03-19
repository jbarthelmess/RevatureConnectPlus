package dev.group1.revatureconnectplus;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RevatureConnectPlusApplicationTests {
/*
	private int userCountTest;

	@Autowired
	UserRepo userRepo;

	private void setUserCount(int userCount){
		this.userCountTest = userCount;
	}

	public int getUserCount(){
		return this.userCountTest;
	}

	@Test
	void test_count(){
		setUserCount((int) userRepo.count());
		System.out.println(getUserCount());
	}

	private static TestUserCount testUserIds;

	@Test
	void create_user() {
		setUserCount((int) userRepo.count());
		testUserIds = new TestUserCount(getUserCount());

		// sets username and display name strings based on count...
		String userName = "TestUser" + testUserIds.getIdCount();
		String displayName = "Test CreateUser" + testUserIds.getIdCount();

		// create user object then pass in to UserRepo ... magic!
		User testUser = new User(0, userName, "testPassword!", displayName);
		userRepo.save(testUser);

		testUserIds.next();

		// to save username count for duplicate testing and set id of recently created user...
		System.out.println("TestUser: " + testUser + "\n" + testUserIds.toString());
	}

	@Test
	void create_users() {
		this.create_user();
		this.create_user();
		this.create_user();
	}

	@Test
	void get_user_by_userId(){
		int id = testUserIds.getLastId();
		User user = userRepo.findById(id).get();
		System.out.println(user);
	}

	@Test
	void delete_user_by_userId(){
		int id = testUserIds.getLastId();
		userRepo.deleteById(id);
		System.out.println("Deleted user " + id);
	}
*/
}
