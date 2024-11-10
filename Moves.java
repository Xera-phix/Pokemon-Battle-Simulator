public class Moves {
   String moveName,moveEffect,moveType,moveKind;
   int movePower,movePP;
   double moveAccuracy; 


   //constructor
   public Moves(String moveName, String moveEffect, String moveType, String moveKind, int movePower, int movePP, double moveAccuracy) {
      this.moveName = moveName;
      this.moveEffect = moveEffect;
      this.moveType = moveType;
      this.moveKind = moveKind;
      this.movePower = movePower;
      this.movePP = movePP;
      this.moveAccuracy = moveAccuracy;
   }  
}