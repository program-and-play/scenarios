public class Hand {
    private Object inhalt;

    public boolean hatStein() {
        if (inhalt == null)
            return false;
        return inhalt instanceof Stein;
    }

    public Object gibInhalt() {
        return inhalt;
    }

    public Stein gibStein() {
        if (hatStein()){
            return (Stein) inhalt;
        }
        return null;
    }

    public void nimmStein(Stein stein) {
        inhalt = stein;
    }

    public void leeren(){
        inhalt = null;
    }

}
