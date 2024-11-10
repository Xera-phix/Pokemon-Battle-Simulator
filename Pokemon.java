public class Pokemon {
   String name;
   int hp, atk, def, spAtk, spDef, speed;
   boolean fainted;
   Moves [] moves; 
   
   //constructor
   public Pokemon(String name, int hp, int atk, int def, int spAtk, int spDef, int speed, Moves[] moves, boolean fainted) {
      this.name = name;
      this.hp = hp;
      this.atk = atk;
      this.def = def;
      this.spAtk = spAtk;
      this.spDef = spDef;
      this.speed = speed;
      this.moves = new Moves[4];
      this.moves[0] = moves[0];
      this.moves[1] = moves[1];
      this.moves[2] = moves[2];
      this.moves[3] = moves[3];
      this.fainted = fainted;
   }  
}