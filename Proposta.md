### Ideia
O aplicativo tem uma proposta de automatizar o serviço de venda de quentinhas, desde a divulgação do cardápio diário até o agendamento, entrega e pagamento das refeições, conectando o fornecedor do alimento e o comprador de uma forma mais rápida, segura e automática.

O serviço se mostra necessário baseando-se na demanda de compra e venda entre o único fornecedor e os alunos do Centro de Informática da UFPE, onde, atualmente, a comunicação é feita através de dois grupos de *whatsapp*, um deles lotado (mais de 200 integrantes).

### Público
Inicialmente, o aplicativo seria construído para o fornecedor "Restaurante e Self Service Vicente Refeições" e os alunos do Centro de Informática e prédios próximos, como Centro de Educação Física, CCEN, Área 2, CTG, entre outros. É importante ressaltar aqui a grande possibilidade de escalabilidade desse serviço, podendo adotar inúmeros fornecedores e locais de entrega em uma determinada região. Portanto, a ideia é fazê-lo de forma genérica para tornar possível sua expansão.

O público terá uma vantagem explícita com a implementação desse aplicativo. Do lado do fornecedor, ele poderá se integrar e alcançar um número maior de clientes, já que o grupo do *whatsapp* possui um limite de participantes. Além disso, o fornecedor terá um controle maior dos pedidos e não precisará ficar enviando os mesmos avisos todos os dias. Já do lado dos compradores – aqui, inicialmente, alunos –, a grande vantagem será a melhor experiência em realizar os pedidos destes alimentos, pois poderão visualizar cardápios e realizar seus pedidos de maneira mais direta, rápida e organizada.

### Concorrentes e diferenciais
Os aplicativos similares que foram encontrados são:
1. [Quentinhas Caseiras](https://play.google.com/store/apps/details?id=com.elderleaf.quentinhascaseiras&hl=pt_BR) 
2. [Quentinhas Já](https://play.google.com/store/apps/details?id=br.com.app.gpu1601205.gpu8ffaf7ef1587353591ca701090f92659)
3. [Hora Boa Quentinhas](https://play.google.com/store/apps/details?id=com.delivery.horaBoaQuentinhas)

Foi analisado que os concorrentes 1 e 2 foram descontinuados e os aplicativos sequer funcionam ao baixá-los.
Já o "Hora Boa Quentinhas" possui uma proposta bem parecida com o aplicativo aqui proposto, porém atende apenas à cidade do Rio de Janeiro, possui 1 (um) fornecedor e faz o *delivery* tradicional da refeição, indo entregar pedido por pedido. Enquanto isso, no aplicativo proposto, o fornecedor seleciona um local de entrega para todos os pedidos, para, assim, atender a uma demanda específica de pessoas, por exemplo alunos do CIn.

### Estrutura
A interação do usuário com a aplicação será a tela inicial, onde ele poderá fazer a escolha de se cadastrar ou seguir para o login. O cadastro será único para fornecedores e clientes. Portanto, na tela de login, o usuário será direcionado para seu fluxo específico. 
A parte dos fornecedores contará com telas de cadastro de cardápio, gerenciamento dos pedidos, liberação do cardápio do dia, configurações de perfil, entre outras.
A parte dos clientes contará com as telas de visualização do cardápio disponível, da montagem da refeição, métodos de pagamento, configurações de perfil, entre outras.

### Componentes
Sobre os componentes de Android iremos utilizá-los da seguinte forma:
* **Activities** para funcionalidades das telas.
* **Content Provider** para armazenamento/recuperação de cardápios e de usuários.
* **Service** para análise em *background* da localização, de forma a ativar o aviso de chegada das quentinhas no momento correto.
* **Broadcast Receivers** para os avisos de início e encerramento do horário de pedidos, além de chegada das quentinhas no local combinado.

Em relação ao uso de bibliotecas vamos utilizar o PagSeguro para o pagamento das refeições que o método de pagamento escolhido seja por meio do app.
Nosso design será baseado nos padrões propostos pelo [Material Design](https://material.io/) do próprio Android.

### Equipe
A ideia é que o trabalho seja dividido de maneira igualitária, de forma a permitir a mesma proporção de experiência e eventual aprendizado para ambas. Ou seja, toda a análise de projeto será feita em conjunto, desde o *mockup* inicial até a modelagem do banco de dados e eventual esquematização das classes e padrões utilizados, a fim de impedir dependências no desenvolvimento do *software*.
A parte do desenvolvimento, como envolve componentes que precisam de uma maior atenção e que podem ser modularizados, o trabalho será dividido igualmente.

