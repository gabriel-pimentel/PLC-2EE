import java.util.*;
import java.util.concurrent.*;

public class Colmeia {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numOperarios = scanner.nextInt();
        int numTarefas = scanner.nextInt();
        scanner.nextLine();

        List<Tarefa> tarefas = new ArrayList<>();
        for (int i = 0; i < numTarefas; i++) {
            String[] entrada = scanner.nextLine().split(" ");
            int id = Integer.parseInt(entrada[0]);
            int tempo = Integer.parseInt(entrada[1]);
            List<Integer> dependencias = new ArrayList<>();
            for (int j = 2; j < entrada.length; j++) {
                dependencias.add(Integer.parseInt(entrada[j]));
            }
            tarefas.add(new Tarefa(id, tempo, dependencias));
        }

        scanner.close();
        
        // começa o siviçu
        ExecutorService executor = Executors.newFixedThreadPool(numOperarios);
        Queue<Tarefa> fila = new LinkedList<>(tarefas);

        while (!fila.isEmpty()) {
            Tarefa tarefa = fila.poll();
            if (tarefa.podeIniciar(tarefas)) {
                executor.submit(() -> {
                    tarefa.executar();
                    tarefas.remove(tarefa);
                });
            } else {
                fila.add(tarefa);
            }
        }

        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Tarefa {
    private int id;
    private int tempo;
    private List<Integer> dependencias;

    public Tarefa(int id, int tempo, List<Integer> dependencias) {
        this.id = id;
        this.tempo = tempo;
        this.dependencias = dependencias;
    }

    // Verifica se a tarefa possui alguma dependencia a ser executada
    public boolean podeIniciar(List<Tarefa> tarefas) {
        return tarefas.stream().noneMatch(t -> dependencias.contains(t.id));
    }

    public void executar() {
        try {
            Thread.sleep(tempo);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("tarefa " + id + " feita");
    }
}


/*
 * 1 4 
1 200 2 4 
4 30  
3 50 2 
2 100

 */