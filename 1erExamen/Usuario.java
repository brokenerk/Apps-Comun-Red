public class Usuario{
	private int Id;
	private String nickname;
	private String password;
	private ImageIcon foto;

	public Usuario(int Id, String nickname, String password, String foto){
		this.Id = Id;
		this.nickname = nickname;
		this.password = password;
		this.foto = new ImageIcon(foto);
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

	public ImageIcon getFoto(){
		return this.foto;
	}
}