/**
 *
 */
public final class Rune extends Figur {

    private RunenTyp typ;

    /**
     *
     * @param typ
     */
    public Rune(RunenTyp typ) {
        super(FigurTyp.Rune);
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
