public class Connection {
    Node in;
    Node out;
    double weight;
    boolean enabled;

    public void perturb(){
        //i think slightly modify weight here?
    }

    public long getId(){
        return (((long)in.id) << Integer.SIZE) | out.id;
    }

    @Override
    public boolean equals(Object o) {
        if(o == this)
            return true;
        if(!(o instanceof Connection))
            return false;

        Connection other = (Connection) o;
        return this.getId() == other.getId();
        //effectively equal if it connects the same nodes
    }

    @Override
    public int hashCode() {
        return Long.hashCode(getId());
    }
}
