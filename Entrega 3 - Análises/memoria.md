Ao utilizar o aplicativo aproriadamente, não observamos pico de memória. O maior uso dela acontece quando o aplicativo está iniciando e abrindo sua página principal (MainActivity). 

Acreditamos que não há mais picos de memória pois todas as listas utilizadas nas telas foram criadas com o RecyclerView, no lugar de ListView. Com isso, a implementação fica muito mais eficiente por causa da sua flexibilidade de mudança de LayoutManager sem a necessidade de implementar mais códigos, facilitando o reuso do mesmo e a sua manutenção.

Obs: foi apurado um pico de memória que fez o aplicativo parar de rodar e fechar sozinho. Aconteceu ao mudarmos de abas rapidamente na área do Fornecedor. Não conseguimos identificar mais informações pois essa situação não voltou a ocorrer.
