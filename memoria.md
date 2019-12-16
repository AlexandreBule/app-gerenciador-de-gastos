# Memória
## Ações
### Adicionar gasto

Percebe-se que o uso da memória se mantém constante ao:
- Clicar no (+) para adicionar um novo gasto
- Preencher os inputs
- Salvar o gasto

Porém o uso da memória apenas diminui se altera ao:
- Tirar a foto pela câmera (único momento que acontece fora da aplicação)

![Adicionar gasto](https://i.imgur.com/4Qf2DZp.png?1)

### Editar gasto

Percebe-se que, de modo geral, o uso da memória se mantém constante durante todos os passos para edição de um gasto:
- Clicar no item na lista da tela inicial
- Preencher os inputs
- Salvar o gasto

Obs.: Houve apenas edição dos campos de texto, e não troca da imagem (momento de sair do aplicativo seja para tirar foto da câmera, seja para escolher uma da galeria). Por isso, não houve uma alteração na curva de uso da memória.

![Editar gasto](https://i.imgur.com/WHh4rwq.png?1)

### Deletar gasto

Percebe-se que, de modo geral, o uso da memória se mantém constante durante todos os passos para deletar o gasto:
- Clicar no item na lista da tela inicial
- Scrollar a tela de detalhe do gasto até visualizar o botão de deletar
- Deletar o gasto

![Deletar gasto](https://i.imgur.com/46mO0Pi.png?1)

## Leak Canary
**Memory Leak** é uma falha na liberação de objetos não utilizados da memória. É um erro de programação que faz com que um aplicativo mantenha uma referência a um objeto que não é mais necessário. Como resultado, a memória alocada para esse objeto não pode ser recuperada, levando a uma falha no OutOfMemoryError.

**LeakCanary** é uma biblioteca de detecção de vazamento de memória para Android.

Para adicionar ao projeto, basta adicionar no `build.gradle`:
```
dependencies {
  // debugImplementation because LeakCanary should only run in debug builds.
  debugImplementation 'com.squareup.leakcanary:leakcanary-android:2.0-beta-3'
}
```

Ao adicionarmos ao aplicativo, tivemos os resultados abaixo:

![](https://i.imgur.com/UipdBAz.jpg)

![](https://i.imgur.com/55a8rIi.jpg)
