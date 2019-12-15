# [2019.2] [IF710] Programação para Dispositivos Móveis com Android
# :money_with_wings: Gerenciador de Gastos :money_with_wings:

## Introdução
O objetivo deste documento é apresentar a proposta e especificação técnica do projeto de desenvolvimento de um aplicativo Android para a disciplina IF710 - Programação para Dispositivos Móveis com Android, ofertada no CIn, UFPE, no semestre 2019.2.

### Demo
[APK](drive)

### Para saber mais detalhes sobre:
| Relatório |
| --- |
| [CPU & Performance](cpu.md) |
| [Consumo de Rede](bandwidth.md) |
| [Memória](memoria.md) | 
| [Bateria](bateria.md) | 
| [Acessibilidade](acessibilidade.md) | 

### Equipe
- [Pamella Bezerra](https://github.com/pamella/)
- [Victor Aguiar](https://github.com/victoraguiarguedes)

## Gerenciador de Gastos
Já se foi o tempo em que o gerenciamento dos gastos pessoais era feito em um caderninho. Nos dias de hoje, os aplicativos de celular são capazes de suprir necessidades de organização, inclusive a financeira.
  
Muitas pessoas não fazem ideia do quanto gastam por mês, simplesmente porque não têm o hábito de acompanhar suas finanças, e isso inclui o sorvete que você toma todos os dias depois do almoço até a conta de água da sua casa. Não importa qual for o valor, todos esses gastos devem ser sempre trackeados e um aplicativo de controle de gastos é essencial para ter um panorama real e completo da sua situação financeira.

Por isso, decidimos criar um gerenciador de gastos pessoais cotidianos, onde o usuário adiciona o seu gasto podendo explicitar uma breve descrição, o valor, uma categoria, o local, a data e imagens relacionadas ao gasto. Além de adicionar, pode-se editar e remover o gasto; e, também, criar categorias (exemplo: alimentação, transporte). Para gerar insights e fornecer uma visão geral, é possível visualizar os dados de formas diferentes, como:
- Gastos agrupados por mês.
- Gastos agrupados por categoria.
- Relatórios
    - Gasto total do mês.
    - Diferença de gasto em relação ao mês anterior.
    - Categoria que mais se gasta.
    - Local que mais se gasta.
    - Gráfico de porcentagem de gasto por categoria.

## Público alvo
O aplicativo é voltado para pessoas que buscam ter maior entendimento dos seus gastos cotidianos através de visualizações, como, por exemplo, o que mais se gasta, onde mais se gasta, qual mês mais se gasta e, a partir disso, tomar ações que considere pertinentes dentro de sua realidade e de seus objetivos para administrar suas finanças de maneira mais lucrativa.

## Aplicativos similares
Abaixo está uma lista de aplicativos de ideia similar, ou seja, aplicativos de finanças que se propõem a gerenciar gastos pessoais.

- Organizze Finanças
    - Registra todos os gastos, receitas e transferências, cria metas para despesas e acompanha as contas e cartões.
    - Gera relatórios de despesas, receitas, categorias e contas durante o período que escolher.
    - Na versão Android, o sistema lê SMS de alguns bancos e lança a transação diretamente no aplicativo.
    - No aplicativo para Android, o sistema lê SMS de alguns bancos e lança a transação diretamente.
    - Importa dos dados da conta bancária, como o histórico bancário para o aplicativo.

- Wisecash
    - Registro de gastos e visualização focados em agilidade e simplicidade.
    - Possibilidade de lançar gastos recorrentes, não sendo necessário fazer o mesmo procedimento todos os meses.
    - Organiza gastos por grupos e oferece consulta em forma de relatórios e gráficos.
    - Notifica as contas a pagar para evitar juros de pagamentos com atraso.

- Guia Bolso Controle Financeiro
    - Sincroniza todos os gastos e receitas com contas bancárias e cartões de créditos, assim, todas as transações são feitas automaticamente.
    - Consulta o CPF e busca a melhor opção de empréstimos de acordo com o perfil, indicando a opção com a taxa de juros mais baixa.
    
## Mockup
O mockup com as telas e o fluxo de navegação foram feitos pela ferramenta do Adobe XD e estão disponíveis para exploração [neste link](https://xd.adobe.com/view/3eb7e8a4-b05f-402e-7956-637834c0ecc8-4886/).
  
## Detalhes técnicos
  Modelagem de Gasto:
- Descrição
- Valor
- Categoria
- Local
- Data
- Imagem

A proposta é que os seguintes campos sejam preenchidos através de Processamento de Linguagem Natural, PLN, a partir do que for digitado pelo usuário (podendo também ser manualmente alterado):
- Descrição
- Valor
- Categoria
- Data

A proposta é que o seguinte campo seja preenchido automaticamente através da localização do smartphone:
- Local

A proposta é que o seguinte campo seja preenchido a partir de arquivos de imagem já existentes ou da câmera do smartphone:
- Imagem
 
## Divisão de atividades
 
| Atividade | Pessoa responsável |
| --- | --- |
| Elaboração do mockup | Victor |
| Elaboração da proposta técnica | Pamella |
| Desenvolvimento front-end | Pamella/Victor | 
| Desenvolvimento back-end | Pamella/Victor | 
