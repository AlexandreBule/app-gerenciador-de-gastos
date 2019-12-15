# Acessibilidade
No quesito Acessibilidade, tentamos seguir algumas boas práticas citadas no [Guia de Acessibilidade do Android Developers](https://developer.android.com/guide/topics/ui/accessibility/apps).

## Material Design
Buscamos seguir a UI do Android Material Design.

### Top navbar
[Docs](https://material.io/components/app-bars-top/)

![Top Navbar](https://i.imgur.com/ojwqKy8.png)

### Floating action button
[Docs](https://material.io/components/buttons-floating-action-button/)

![Floating action button](https://i.imgur.com/JpqaVyW.png)

### Inputs
[Docs](https://material.io/components/text-fields/)

![Inputs](https://i.imgur.com/XrYcJwJ.png)

### Buttons
[Docs](https://material.io/components/buttons/)

![Buttons](https://i.imgur.com/JHL6EFm.png?1)

## Descrever elementos da UI
Abaixo tem alguns exemplos de como fizemos isso em certos elementos da interface

<!-- Título, pequena descrição, exemplo de código -->
### Label
Como tem efeito apenas decorativo, o valor da descrição é null. `android:contentDescription="@null"`

```
<androidx.constraintlayout.widget.ConstraintLayout
        android:id="@+id/title"
        ...
        android:contentDescription="@null"
        >

        <View
            android:id="@+id/bar"
            ...
            android:contentDescription="@null"
            />

        <TextView
            android:id="@+id/textView"
            ...
            android:contentDescription="@null"
            />
        />

    </androidx.constraintlayout.widget.ConstraintLayout>
```

### Submit button
Como esse elemento está vinculado a uma ação, é importante ter uma descrição atrelada. `android:contentDescription="Adicionar gasto"`

```
<com.google.android.material.button.MaterialButton
        android:id="@+id/btn_Add"
        ...
        android:contentDescription="Adicionar gasto"
        />
```
