import org.mindrot.jbcrypt.BCrypt;

public class BCryptExample {

	public static void main(String[] args) {
		//加密密碼
		String rawPassword = "123";
		String hashedPassword = BCrypt.hashpw(rawPassword,BCrypt.gensalt());
		System.out.println("Hashed Password:　" + hashedPassword); //$2a$10$kF0GW1mzIdOD2tQqepiTq.qt37vs8IrYjtXvW432zRVyNaG0xQ1ny
	
		//驗證密碼
		String userPassword = "123"; //
		boolean isPasswordMatch = BCrypt.checkpw(userPassword, "$2a$10$SokCMdV9tI3F0bM3C6NnG.RpvH6RWzRB9eLkZjahmvm0NMr56aN42");
		System.out.println("PasswordMatch: "+isPasswordMatch);
	}
	

}
