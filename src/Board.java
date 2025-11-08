/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author luc
 */
public class Board {
    
    /*
        8 | 56 57 58 59 60 61 62 63
        7 | 48 49 50 51 52 53 54 55
        6 | 40 41 42 43 44 45 46 47
        5 | 32 33 34 35 36 37 38 39
        4 | 24 25 26 27 28 29 30 31
        3 | 16 17 18 19 20 21 22 23
        2 | 8  9  10 11 12 13 14 15
        1 | 0  1  2  3  4  5  6  7
          -------------------------
            a  b  c  d  e  f  g  h    
    */
    
    private long[] pieceBB = new long[12]; 
    //Bitboards for every type of piece (pawn, knight, bishop, rook, queen, king) 
    //for each color (white,black)
    
    private long whitePieces; 
    //Bitboard to see where white pieces are located
    
    private long blackPieces; 
    //Bitboard to see where black pieces are located
    
    private boolean whiteToMove = true; 
    //who's turn it is (true: white)
    
    private boolean[] castleRight = new boolean[4]; 
    // Castle Rights are saved here: (0: 0-0-0 w; 1: 0-0 w; 2: 0-0-0 b; 3: 0-0 b)
    
    public static final int whitePawns=0, whiteKnights=1, whiteBishops=2, whiteRooks=3, 
                            whiteQueens=4, whiteKing=5,
                            blackPawns=6, blackKnights=7, blackBishops=8, blackRooks=9,
                            blackQueens=10, blackKing=11, size=12;
    //indexes for each piece bitboard
    
    public Board(){
        initialize();
    }
    
    private void initialize(){
        pieceBB[whitePawns]   = 0x000000000000FF00L;
        pieceBB[whiteKnights] = (1L << 1) | (1L << 6);
        pieceBB[whiteBishops] = (1L << 2) | (1L << 5);
        pieceBB[whiteRooks]   = (1L << 0) | (1L << 7);
        pieceBB[whiteQueens]  = (1L << 3);
        pieceBB[whiteKing]    = (1L << 4);
        pieceBB[blackPawns]   = 0x00FF000000000000L;
        pieceBB[blackKnights] = (1L << 57) | (1L << 62);
        pieceBB[blackBishops] = (1L << 58) | (1L << 61);
        pieceBB[blackRooks]   = (1L << 56) | (1L << 63);
        pieceBB[blackQueens]  = (1L << 59);
        pieceBB[blackKing]    = (1L << 60);
    }
    
    public long occupied(){
        long allBB = 0L; //all bitboards combined
        for(long bb : pieceBB){
            allBB |= bb;
        }
        return allBB;
    }
    
    public boolean isOccupied(int square){
        long mask = 1L << square;
        return (mask & occupied()) != 0;
    }
    
    public void movePiece(int fromSquare, int toSquare){
        long fromMask = 1L << fromSquare;
        long toMask   = 1L << toSquare;
        for (int pieceIndex = 0; pieceIndex < size; pieceIndex++) {
            // searching the right Bitboard
            if((pieceBB[pieceIndex] & fromMask) != 0){
                pieceBB[pieceIndex] &= ~fromMask;
                pieceBB[pieceIndex] |= toMask;
            }
        }
    }
}
