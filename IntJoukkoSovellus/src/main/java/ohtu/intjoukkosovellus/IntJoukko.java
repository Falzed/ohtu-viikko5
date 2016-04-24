package ohtu.intjoukkosovellus;

public class IntJoukko {

    public final static int KAPASITEETTI = 5, // aloitustalukon koko
            OLETUSKASVATUS = 5;  // luotava uusi taulukko on 
    // näin paljon isompi kuin vanha
    private int kasvatuskoko;     // Uusi taulukko on tämän verran vanhaa suurempi.
    private int[] lukujono;      // Joukon luvut säilytetään taulukon alkupäässä. 
    private int alkioidenLkm;    // Tyhjässä joukossa alkioiden_määrä on nolla. 

    public IntJoukko() {
        this(KAPASITEETTI, OLETUSKASVATUS);
    }

    public IntJoukko(int kapasiteetti) {
        this(kapasiteetti, OLETUSKASVATUS);
    }

    public IntJoukko(int kapasiteetti, int kasvatuskoko) {
        if (kapasiteetti < 0 || kasvatuskoko < 0) {
            throw new IndexOutOfBoundsException("Kapasitteetin ja kasvatuskoon täytyy olla positiivisia");
        }
        initTable(kapasiteetti);
        alkioidenLkm = 0;
        this.kasvatuskoko = kasvatuskoko;
    }

    private void initTable(int kapasiteetti) {
        lukujono = new int[kapasiteetti];
        for (int i = 0; i < lukujono.length; i++) {
            lukujono[i] = 0;
        }
    }

    public boolean lisaa(int luku) {
        if (alkioidenLkm == 0) {
            setIndex(luku, 0);
        } else if (!sisaltaa(luku)) {
            lisaaApuEiSisalla(luku);
        } else {
            return false;
        }
        return true;
    }

    private void lisaaApuEiSisalla(int luku) {
        if (alkioidenLkm % lukujono.length == 0) {
            kasvataTaulukkoa();
        }
        setIndex(luku, alkioidenLkm);
    }

    private void setIndex(int luku, int index) {
        lukujono[index] = luku;
        alkioidenLkm++;
    }

    public void kasvataTaulukkoa() {
        int[] taulukkoOld = lukujono;
        lukujono = new int[alkioidenLkm + kasvatuskoko];
        kopioiTaulukko(taulukkoOld, lukujono);
    }

    public boolean sisaltaa(int luku) {
        boolean sisaltaa = false;
        for (int i = 0; i < alkioidenLkm; i++) {
            if (luku == lukujono[i]) {
                sisaltaa = true;
                break;
            }
        }
        return sisaltaa;
    }

    public boolean poista(int luku) {
        int poistetunIndeksi = loyda(luku);
        if (poistetunIndeksi != -1) {
            siirraPoistetunJalkeiset(poistetunIndeksi);
            return true;
        }

        return false;
    }

    private void siirraPoistetunJalkeiset(int poistetunIndeksi) {
        lukujono[poistetunIndeksi] = 0;
        int apu = poistetunIndeksi;
        for (int j = poistetunIndeksi; j < alkioidenLkm - 1; j++) {
            lukujono[j] = lukujono[j + 1];
            lukujono[j + 1] = apu;
        }
        alkioidenLkm--;
    }

    private int loyda(int luku) {
        int kohta = -1;
        for (int i = 0; i < alkioidenLkm; i++) {
            if (luku == lukujono[i]) {
                kohta = i;
                return kohta;
            }
        }
        return kohta;
    }

    private void kopioiTaulukko(int[] vanha, int[] uusi) {
        for (int i = 0; i < vanha.length; i++) {
            uusi[i] = vanha[i];
        }

    }

    public int mahtavuus() {
        return alkioidenLkm;
    }

    @Override
    public String toString() {
        String merkkijono = "{" + stringPilkkuLopussa();
        if (alkioidenLkm > 0) {
            merkkijono += lukujono[alkioidenLkm - 1];
        }
        merkkijono += "}";
        return merkkijono;
    }

    private String stringPilkkuLopussa() {
        String merkkijono = "";
        for (int i = 0; i < alkioidenLkm - 1; i++) {
            merkkijono += lukujono[i];
            merkkijono += ", ";
        }
        return merkkijono;
    }

    public int[] toIntArray() {
        int[] taulu = new int[alkioidenLkm];
        for (int i = 0; i < taulu.length; i++) {
            taulu[i] = lukujono[i];
        }
        return taulu;
    }

    public static IntJoukko yhdiste(IntJoukko a, IntJoukko b) {
        IntJoukko x = new IntJoukko();
        for (int i = 0; i < a.toIntArray().length; i++) {
            x.lisaa(a.toIntArray()[i]);
        }
        for (int i = 0; i < b.toIntArray().length; i++) {
            x.lisaa(b.toIntArray()[i]);
        }
        return x;
    }

    public static IntJoukko leikkaus(IntJoukko a, IntJoukko b) {
        IntJoukko y = new IntJoukko();
        for (int i = 0; i < a.toIntArray().length; i++) {
            y=leikkausApu(a, b, y, i);
        }
        return y;
    }

    private static IntJoukko leikkausApu(IntJoukko a, IntJoukko b, IntJoukko y, int i) {
        for (int j = 0; j < b.toIntArray().length; j++) {
            if (a.toIntArray()[i] == b.toIntArray()[j]) {
                y.lisaa(b.toIntArray()[j]);
            }
        }
        return y;
    }

    public static IntJoukko erotus(IntJoukko a, IntJoukko b) {
        IntJoukko z = new IntJoukko();
        for (int i = 0; i < a.toIntArray().length; i++) {
            z.lisaa(a.toIntArray()[i]);
        }
        for (int i = 0; i < b.toIntArray().length; i++) {
            z.poista(i);
        }
        return z;
    }

}
