import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;


public class SistemaGestaoAeroporto {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);

// FILA DE AVIOES
        PriorityBlockingQueue<Aviao> filaAvioes = new PriorityBlockingQueue<>();
// DECOLAGEM
        System.out.println("Digite o número de aviões que vão decolar:");
        int N = scanner.nextInt();
        
        for (int i = 0; i < N; i++) {
            System.out.println("Digite a hora de saída do avião " + (i + 1) + " (em milissegundos):");
            long horarioEsperado = scanner.nextLong();
            filaAvioes.add(new Aviao(i + 1, horarioEsperado, "Decolar"));
        }

// ATERRISAGEM
        System.out.println("Digite o número de aviões que vão aterrissar:");
        int M = scanner.nextInt();

        for (int i = 0; i < M; i++) {
            System.out.println("Digite a hora de chegada do avião " + (i + 1) + " (em milissegundos):");
            long horarioEsperado = scanner.nextLong();
            filaAvioes.add(new Aviao(N + i + 1, horarioEsperado, "Aterrissar"));
        }

// PISTAS
        System.out.println("Digite o número de pistas disponíveis no aeroporto:");
        int K = scanner.nextInt();
        scanner.close();



        Aeroporto aeroporto = new Aeroporto(K);
        ExecutorService executorService = Executors.newFixedThreadPool(K);

// INICIO DAS OPERAÇÕES
        long inicio = System.currentTimeMillis();
        while (!(filaAvioes.isEmpty())) {
            Aviao aviaoOperacao = filaAvioes.peek();
            Aviao proximoAviao = null;

            if (aviaoOperacao != null) {     
                proximoAviao = filaAvioes.poll();
            }

            if (proximoAviao != null) {
                long horarioAtual = System.currentTimeMillis() - inicio;
                if (horarioAtual >= proximoAviao.horarioEsperado) {
                    final Aviao aviao = proximoAviao;
                    executorService.submit(() -> {
                        try {
                            aeroporto.acaoAviao(aviao, horarioAtual);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                } 
                else {
                    if (aviaoOperacao != null) {
                        filaAvioes.add(proximoAviao);
                    }
                }

            }
        }

        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        
    }

}

/*
 * 1 4 
1 200 2 4 
4 30  
3 50 2 
2 100

 */