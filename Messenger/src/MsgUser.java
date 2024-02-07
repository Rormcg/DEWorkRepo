

/**
 * 
 * @author Rory McGuire
 */
public class MsgUser implements Comparable<MsgUser>{
	private String username, password;
	
	MsgUser(String u, String p) {
		username = u;
		password = p;
	}
	
	@Override
	public int compareTo(MsgUser m) {
		return username.compareToIgnoreCase(m.username);
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof MsgUser)) return false;
		MsgUser m = (MsgUser) o;
		return username.equalsIgnoreCase(m.username);
	}
	
	public String getUsername() {
		return username;
	}
}
