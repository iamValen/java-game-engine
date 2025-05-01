public class pudinsMain {
    public static void main(String[] args){

        //inicializar a engine pode ser feito a qualquer altura
        GameEngine ge = GameEngine.GameEngineConstructor();

        //primeiro crias os membro todos de GameObject
        Circle cir = new Circle(new Point(1, 2), 4d);
        Collider col = new Collider(cir);
        Transform tr = new Transform(3, 5, 0, 0, 0);
        blicaBehaviour behav = new blicaBehaviour(new Point(1, 0), 0, 0, 0);

        //depois inicializas o GameObject so com o nome
        GameObject go = new GameObject("circulo");

        //depos espetas os membro la pa dentro
        //como podes ver isto trata logo das referencias
        //collider -> transform e behaviour -> GameObject
        //assim como move logo o collider para a posiçao do transform
        go.insertElements(tr, col, null, behav);

        //depois adicionas a engine
        ge.addEnabled(go);


        ge.run();
    }
}
