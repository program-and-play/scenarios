import util.ActorPosition;

/**
 *
 */
public final class Rune extends Figur {

    private RunenTyp typ;

    /**
     *
     * @param typ
     */
    public Rune(RunenTyp typ, ActorPosition startPosition) {
        super(FigurTyp.Rune, startPosition);
        this.typ = typ;
    }

    /**
     *
     * @return
     */
    public RunenTyp gibTyp() {
        return typ;
    }
}

/**
 *
 */
enum RunenTyp {
    Schlueesel1,
    Schluessel2,
    Schluessel3,
    Klang
}
