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
        
        int[][] directions = {{-1,-1}, {-1,1}, {1,1}, {1,-1}};
        
        for(int[] dir : directions){
            int r = rank + dir[0];
            int f = file + dir[1];
            while(r >= 0 && r < 8 && f >= 0 && f < 8){
                temp = getSquare(r,f);
                if((occupied & (1L << temp)) != 0){
                    if((myColor & (1L << temp)) != 0) attacks |= (1L << temp);
                    break;
                }
                attacks |= (1L << temp);
                r += dir[0];
                f += dir[1];
            }
        }
        return attacks;
    }
    
    public static long getRookAttacks(int square, long occupied, long myColor){
        long attacks = 0L;
        int rank = getRank(square);
        int file = getFile(square);
        int temp;
        
        int[][] directions = {{0,1}, {1,0}, {0,-1}, {-1,0}};
        
        for(int[] dir : directions){
            int r = rank + dir[0];
            int f = file + dir[1];
            while(r >= 0 && r < 8 && f >= 0 && f < 8){
                temp = getSquare(r,f);
                if((occupied & (1L << temp)) != 0){
                    if((myColor & (1L << temp)) != 0) attacks |= (1L << temp);
                    break;
                }
                attacks |= (1L << temp);
                r += dir[0];
                f += dir[1];
            }
        }
        return attacks;
    }
    
    public static long getQueenAttacks(int square, long occupied, long myColor){
        return getRookAttacks(square,occupied,myColor) | getBishopAttacks(square,occupied,myColor);
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
