import java.io.Serializable;
import javax.swing.ImageIcon;

public class Usuario implements Serializable{
	private int Id;
	private String nickname;
	private String password;
	private ImageIcon avatar;

	public Usuario(int Id, String nickname, String password){
		this.Id = Id;
		this.nickname = nickname;
		this.password = password;
	}

	public void setAvatar(String avatar){
		this.avatar = new ImageIcon(avatar);
	}

	public int getId(){
		return this.Id;
	}

	public String getNickname(){
		return this.nickname;
	}

	public String getPassword(){
		return this.password;
	}

	public ImageIcon getAvatar(){
		return this.avatar;
	}
}