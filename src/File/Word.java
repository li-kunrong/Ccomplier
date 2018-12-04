package File;

public class Word {
    private int typenum;
    private String world;
    private int linecount;

    public Word(int typenum, String world,int linecount) {
        super();
        this.typenum = typenum;
        this.world = world;
        this.linecount = linecount;
    }

    /**
     * @return the typenum
     */
    public int getTypenum() {
        return typenum;
    }

    public int getLinecount() {
        return linecount;
    }

    public void setLinecount(int linecount) {
        this.linecount = linecount;
    }

    /**
     * @param typenum
     *            the typenum to set
     */
    public void setTypenum(int typenum) {
        this.typenum = typenum;
    }

    /**
     * @return the world
     */
    public String getWorld() {
        return world;

    }

    /**
     * @param world
     *            the world to set
     */
    public void setWorld(String world) {
        this.world = world;
    }

    public String toString() {
        return "(" + this.typenum + "," + this.world + ")";
    }
}
