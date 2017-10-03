/**
 * Enum zur Repraesentation der Blickrichtung auf Basis von Himmelsrichtungen
 */
public enum Blickrichtung {
    NORDEN {
        public boolean istNorden() {
            return true;
        }
    },
    OSTEN {
        public boolean istOsten() {
            return true;
        }
    },
    SUEDEN {
        public boolean istSueden() {
            return true;
        }
    },
    WESTEN {
        public boolean istWesten() {
            return true;
        }
    };

    public boolean istNorden() {
        return false;
    }

    public boolean istOsten() {
        return false;
    }

    public boolean istSueden() {
        return false;
    }

    public boolean istWesten() {
        return false;
    }
}