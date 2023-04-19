public class Aviao implements Comparable<Aviao> {
    int id;
    long horarioEsperado;
    String tipo;

    public Aviao(int id, long horarioEsperado, String tipo) {
        this.id = id;
        this.horarioEsperado = horarioEsperado;
        this.tipo = tipo;
    }

    @Override
    public int compareTo(Aviao o) {
        return Long.compare(this.horarioEsperado, o.horarioEsperado);
    }
}