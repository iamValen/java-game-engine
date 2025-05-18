# Game Engine in Java

## TO TALK ABT


# TODO 
- [X] dar uma ref do HUD para o player e no onDestroy destruir tudo. -
    - não funciona mesmo assim
- [ ] não passar nada para o update do observer e ter uma referência para ele
    - Fazer o Observer abstract para ter logo nele o playerBehaviour?
- [ ] melhorar a chamada de update dos observers
- [ ] Animation "hurt" aparece mesmo quando ainda está no cooldown de invencível
    - Já tentei passar a lógica do cooldown de Health para PlayerBehaviour mas funciona mal 


# PDF
- singleton
- observers

# Validação
- double jump
- crouch
- power ups





# AI prompt (???)
Prompt: Estou a fazer um game em java com um game engine criado por mim que usa POO. Como aplicar sprites aos meus GameObjects em que so tenho uma imagem png e tenho ai todas os frames do personagem?
Explica també m o que é um aseprite?
Answer: Perfeito! Estás a criar um jogo em Java com uma game engine feita por ti usando Programação Orientada a Objetos (POO) — ótimo desafio. Vamos por partes:

---

## 🎮 **1. O que é um Sprite Sheet?**

Um **sprite sheet** é uma imagem `.png` (como a que tens) que contém **vários frames de animação** de um personagem ou objeto do jogo, dispostos em grelha (geralmente horizontal ou em linhas e colunas). Cada frame representa um estado do personagem (como "andar", "pular", etc.).

---

## 🧩 **2. O que é o Aseprite?**

O **Aseprite** é um programa profissional (muito usado por indies) para criar **pixel art** e **sprites animados**. Com ele podes:

* Desenhar sprites frame a frame;
* Exportar **sprite sheets** (como a que tens);
* Exportar dados JSON que contêm as coordenadas dos frames;
* Ver as animações em tempo real.

Podes criar a imagem inteira no Aseprite e exportar para usar na tua engine.

---

## 🧠 **3. Como aplicar Sprite Sheets aos teus GameObjects?**

### A. **Carregar a Sprite Sheet**

```java
BufferedImage spriteSheet = ImageIO.read(new File("spritesheet.png"));
```

### B. **Extrair os frames**

Assumindo que cada frame tem largura `frameWidth` e altura `frameHeight`, e que os frames estão lado a lado (horizontalmente):

```java
public BufferedImage getFrame(BufferedImage sheet, int frameIndex, int frameWidth, int frameHeight) {
    return sheet.getSubimage(frameIndex * frameWidth, 0, frameWidth, frameHeight);
}
```

> Se tiveres várias linhas (ex: para animações diferentes), podes adicionar `rowIndex` como argumento e fazer:

```java
sheet.getSubimage(frameIndex * frameWidth, rowIndex * frameHeight, frameWidth, frameHeight);
```

### C. **Exemplo de classe `SpriteAnimator`**

```java
public class SpriteAnimator {
    private BufferedImage[] frames;
    private int currentFrame = 0;
    private int tick = 0;
    private int speed;

    public SpriteAnimator(BufferedImage spriteSheet, int totalFrames, int frameWidth, int frameHeight, int speed) {
        this.frames = new BufferedImage[totalFrames];
        for (int i = 0; i < totalFrames; i++) {
            frames[i] = spriteSheet.getSubimage(i * frameWidth, 0, frameWidth, frameHeight);
        }
        this.speed = speed;
    }

    public void update() {
        tick++;
        if (tick >= speed) {
            tick = 0;
            currentFrame = (currentFrame + 1) % frames.length;
        }
    }

    public BufferedImage getCurrentFrame() {
        return frames[currentFrame];
    }
}
```

### D. **Dentro do teu GameObject**

```java
public class Player extends GameObject {
    private SpriteAnimator animator;

    public Player() {
        BufferedImage sheet = ImageIO.read(new File("sprites/player.png"));
        animator = new SpriteAnimator(sheet, 4, 32, 32, 10);
    }

    public void update() {
        animator.update();
    }

    public void render(Graphics g) {
        g.drawImage(animator.getCurrentFrame(), x, y, null);
    }
}
```

---

## ✅ Resumo

* **Sprite sheet**: imagem com todos os frames da animação.
* **Aseprite**: ferramenta para criar e exportar sprite sheets (muito boa para pixel art).
* **Na tua engine**: divides a imagem em frames usando `getSubimage()`, e depois fazes um sistema simples de animação para iterar por esses frames.

Se quiseres, posso ajudar a criar um `SpriteManager` mais robusto ou importar dados do Aseprite em JSON.

Queres que te mostre como ler JSON exportado pelo Aseprite com Java?
