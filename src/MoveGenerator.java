/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author luc
 */
public final class MoveGenerator {
    
    private static int getFile(int square) {
        return square % 8;
    }

    private static int getRank(int square) {
        return square / 8;
    }

    private static int getSquare(int rank, int file) {
        return rank * 8 + file;
    }
    
    public static long getBishopAttacks(int square, long occupied, long myColor){
        long attacks = 0L;
        int rank = getRank(square);
        int file = getFile(square);
        int temp;
        for (int r = rank-1, f = file-1; r >= 0 && f >= 0; r--, f--){
            temp = getSquare(r,f);
            if(((occupied & (1L << temp)) != 0)){
                if(((myColor & (1L << temp)) == 0)){
                    attacks |= (1L << temp);
                }
                break;
            }
            else attacks |= (1L << temp);
        }
        for (int r = rank-1, f = file+1; r >= 0 && f < 8; r--, f++){
            temp = getSquare(r,f);
            if(((occupied & (1L << temp)) != 0)){
                if(((myColor & (1L << temp)) == 0)){
                    attacks |= (1L << temp);
                }
                break;
            }
            else attacks |= (1L << temp);
        }
        for (int r = rank+1, f = file+1; r < 8 && f < 8; r++, f++){
            temp = getSquare(r,f);
            if(((occupied & (1L << temp)) != 0)){
                if(((myColor & (1L << temp)) == 0)){
                    attacks |= (1L << temp);
                }
                break;
            }
            else attacks |= (1L << temp);
        }
        for (int r = rank+1, f = file-1; r < 8 && f >= 0; r++, f--){
            temp = getSquare(r,f);
            if(((occupied & (1L << temp)) != 0)){
                if(((myColor & (1L << temp)) == 0)){
                    attacks |= (1L << temp);
                }
                break;
            }
            else attacks |= (1L << temp);
        }
        return attacks;
    }
    
    public static long getRookAttacks(){
        long attacks = 0L;
        return attacks;
    }
    
    public static long getQueenAttacks(){
        long attacks = 0L;
        return attacks;
    }
    
    public static long getKnightAttacks(){
        long attacks = 0L;
        return attacks;
    }
    
    public static long getPawnAttacks(){
        long attacks = 0L;
        return attacks;
    }
    
    public static long getKingAttacks(){
        long attacks = 0L;
        return attacks;
    }
}
