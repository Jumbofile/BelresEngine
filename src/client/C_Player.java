package client;

public class C_Player {
	private String name;
	public String otherStuff;
	private int id, x, y;
	private int map;

	public C_Player(String name){
        this.name = name;
	}

	public void setCord(int x, int y){
	    this.x = x;
	    this.y = y;
    }

    public int getX(){
	    return x;
    }
    public int getY(){
        return y;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setMap(int map){
        this.map = map;
    }

    public int getMap(){
        return map;
    }
}
