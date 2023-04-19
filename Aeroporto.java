import java.util.concurrent.Semaphore;

public class Aeroporto {
    private final Semaphore semaphore;

    public Aeroporto(int numeroPistas) {
        this.semaphore = new Semaphore(numeroPistas);
    }

    public void acaoAviao(Aviao aviao, long horarioAtual) throws InterruptedException {
        semaphore.acquire();
        try {
            long atraso = horarioAtual - aviao.horarioEsperado;
            System.out.printf("Avião %d [%s] - Horário esperado: %d, Horário real: %d, Atraso: %d%n",
                    aviao.id, aviao.tipo, aviao.horarioEsperado, horarioAtual, atraso);
            Thread.sleep(500);
        } finally {
            semaphore.release();
        }
    }
}